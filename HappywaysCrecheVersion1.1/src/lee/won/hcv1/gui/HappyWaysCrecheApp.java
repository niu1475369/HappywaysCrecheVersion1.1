package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.events.RequestNewListEvent;
import lee.won.hcv1.events.RequestNewListListener;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.NoPrimaryKeyFoundException;
import lee.won.hcv1.img.ImageUtility;
import lee.won.hcv1.impl.Child;
import lee.won.hcv1.impl.ConfigFile;
import lee.won.hcv1.impl.IdBackup;
import lee.won.hcv1.impl.PersonID;
import lee.won.hcv1.impl.PersonList;
import lee.won.hcv1.io.FilenameFilterTest;
import lee.won.hcv1.io.ObjectFileHandler;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jdom2.JDOMException;

import com.alee.extended.filechooser.SelectionMode;
import com.alee.extended.filechooser.WebFileChooser;
import com.alee.laf.WebLookAndFeel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBoxMenuItem;
import java.awt.Color;

/**
 * 
 * @author Won Lee
 * @version 1.5 b3030835
 * b151339:	File Handling methods (read from File when launched & write when closing) were added
 * 			=> It is not completed, PersonID class has to be well adjusted to avoid duplicate ID
 * b230642:	Solved by adding if(pi != -1 && ...) and if((ci-pi) > 0 && ...)
 * b242015: implement method that convert id number from new id to old id.
 *			plus this can handle xml file export now
 * b251321:	xml import method is implemented
 * b279000: JFileChooser was adopted.
 * b271355: reaction about JFileChooser was improved by [int option = jFileChooser.showOpenDialog(new JFrame());]
 * 			and setMnemonic for all buttons
 * b280820: defrag ID can save custom name backup file
 * b3011525:Additional options were added for Look and Feel Setting
 * b3030835:Placed header image, this image locates inside the package and reads it using InputStream
 * 			Then resize the image to fit the width.
 * 
 * "seaglass" look and feel was found here (https://code.google.com/p/seaglass/)
 * "Web Look and Feel" look and feel was found here (http://weblookandfeel.com/)
 * "JGoodies Looks" look and feel was found here (http://www.jgoodies.com/freeware/libraries/looks/)
 */
public class HappyWaysCrecheApp extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private List<Person> persons = new PersonList();
	private ConfigFile config;
	private JTextField txf_Com;
	private ObjectFileHandler ofh = new ObjectFileHandler();
	private JCheckBoxMenuItem chckbxmntmDefault;
	private JCheckBoxMenuItem chckbxmntmWindows;
	private JCheckBoxMenuItem chckbxmntmSeaglass;
	private JCheckBoxMenuItem chckbxmntmJavaMetal;
	private JCheckBoxMenuItem chckbxmntmWebLaf;
	private BufferedImage headerImage;
	private BufferedImage iconImage;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HappyWaysCrecheApp frame = new HappyWaysCrecheApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void setLookAndFeel(String os){
		if(os == null){
			os = System.getProperty("os.name");
		}
		os = os.trim().toLowerCase();
		try {
			if(os.contains("windows")){
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}else if(os.contains("mac")){
				UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			}else if(os.contains("javametal")){
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			}else if(os.contains("weblookandfeel")){
				WebLookAndFeel.install();
			}else{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}

		/**
	 * Create the frame.
	 */
	public HappyWaysCrecheApp() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					Object[] objs = {config,persons};
					ofh.writeIntoFile(objs);
					//ofh.writeIntoFile(persons);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
				
		/*
		 * Read default setting file and set current maximum id number for each subclass of person
		 * as currentID so that new instances get id after that.
		 */
		
		Object[] objs = null;
		try {
			//Create "Setting" Directory if it doesn't exist
			File file = new File("Setting");
			if(!file.exists() || !file.isDirectory()){
				file.mkdir();
			}
			//Get list of persons
			objs = ofh.readFromFile();
			persons = (List<Person>) objs[1];
			//persons = ofh.readFromFile();
			PersonList list = (PersonList) persons;
			//Set currentID (which is variable of PersonID) for each subclass of Person
			int pi = list.findBorder(0, 0, list.size()-1);
			int ci = list.findBorder(1, pi, list.size()-1);
			//System.out.println("pi = "+pi+" ci = "+ci);
			if(pi != -1 && list.get(pi) instanceof Person){
				PersonID.setParentCurrent(list.get(pi).getID());
			}
			if((ci-pi) > 0 && list.get(ci) instanceof Child){
				PersonID.setChildCurrent(list.get(ci).getID());
			}
		} catch (Exception e){
			System.err.println(e);
		}
		
		try{
			config = (ConfigFile) objs[0];			
		}catch (Exception e){
			e.printStackTrace();
			config = new ConfigFile("default");
		}
		
		setLookAndFeel(config.getOs());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		setTitle("HappyWaysCrecheApp");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('f');
		menuBar.add(mnFile);
		
		JMenuItem mntmImport = new JMenuItem("Import");
		mntmImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File file = showFileChooser(true,null);
					if(file != null){
						persons = ofh.readFromXML(file);
						showMessage("Import sucessful.");
					}
				} catch (Exception e) {
					showMessage(e.getMessage());
					//e.printStackTrace();
				}
				//System.out.println("Parent Current = " + PersonID.getParentCurrent() + 
						//" Child Current = " + PersonID.getChildCurrent());
			}
		});
		mnFile.add(mntmImport);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Date date = new Date();
					File fileName = new File("Persons_"+date.getTime()+".xml");
					File file = showFileChooser(false,fileName);
					if(file != null){
					    if(!file.getName().endsWith(".xml")){
					    	file = new File(file.getPath() + ".xml");
					    }
					    boolean stop = false;
					    if(file.exists()){
							int response = JOptionPane.showConfirmDialog(null,
									"The file (" + file.getPath() +
					    			") exists. " + "Would you like to overwrite it?","Overwritting File",
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null);
						    if(response == JOptionPane.CANCEL_OPTION){
						    	showMessage("Operation canceled");
						    	stop = true;
						    }
					    }
						if(!stop){
							ObjectFileHandler ofh = new ObjectFileHandler();
							ofh.writeIntoXML(persons, file);
							showMessage("Exported to " + file.getPath());
						}
				    }
				}catch (NullPointerException e){
					
				}catch (Exception e){
					showMessage(e.getMessage());
				}
			}
		});
		mnFile.add(mntmExport);
		
		JMenu mnOption = new JMenu("Option");
		mnOption.setMnemonic('o');
		menuBar.add(mnOption);
		
		JMenuItem mntmIdSetting = new JMenuItem("ID Setting");
		mntmIdSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PersonIDgui pidGui = new PersonIDgui(persons);
				pidGui.setIconImage(iconImage);
				pidGui.setVisible(true);
			}
		});
		mnOption.add(mntmIdSetting);
		
		JMenuItem mntmDefragId = new JMenuItem("Defrag ID");
		mntmDefragId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PersonList list = (PersonList) persons;
				try {
					int response = JOptionPane.showConfirmDialog(null,
							"Are you sure to defrag ID of Persons","Defragging ID",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null);
				    if(response == JOptionPane.CANCEL_OPTION){
				    	//System.exit(0);
				    }else{
				    	File file = new File("(|Not Saved|)");
						int response2 = JOptionPane.showConfirmDialog(null,
								"Do you want to save custom name ID_Backup file?\n" +
								"This file can be used when you need to restore old ID",
								"Saving backup option",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null);
					    if(response2 == JOptionPane.CANCEL_OPTION){
					    	List<IdBackup> idList = list.defragId();
					    	file = new File("Setting/ID_Backup.xml");
							ObjectFileHandler ofh = new ObjectFileHandler();
							ofh.writeIdBackupXML(idList, file);
					    }else{
							Date date = new Date();
							File fileName = new File("ID_Backup_"+date.getTime()+".xml");
							file = showFileChooser(false,fileName);
							if(!file.getName().equals("(|Not Saved|)")){
							    if(!file.getName().endsWith(".xml")){
							    	file = new File(file.getPath() + ".xml");
							    }
							    List<IdBackup> idList = null;
							    if(file.exists()){
									int response3 = JOptionPane.showConfirmDialog(null,
											"The file (" + file.getPath() +
							    			") exists. " + "Would you like to overwrite it?","Overwritting File",
											JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null);
								    if(response3 == JOptionPane.CANCEL_OPTION){
								    	showMessage("Operation canceled");
								    }else{
								    	idList = list.defragId();								    	
								    }
							    }else{
							    	idList = list.defragId();
							    }
							    if(idList != null){
									ObjectFileHandler ofh = new ObjectFileHandler();
									ofh.writeIdBackupXML(idList, file);
									showMessage("ID was defragged and backup file was saved to " + file.getPath());
							    }
						    }
					    }
					    showMessage("Defrag ID done. Backup file name is " + file.getName());
					}
				} catch (IdOverflowException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoPrimaryKeyFoundException e){
					String idList = "";
					for(Integer i: e.getErrorId()){
						idList += i+",";
					}
					JOptionPane.showMessageDialog(null,
							"Parent ID could not be found for Children ID = "+idList+
							"\nTherefore Parent ID was set to 0 for these Children");
				}
			}
		});
		mnOption.add(mntmDefragId);
		
		JMenuItem mntmRestoreOldId = new JMenuItem("Restore Old ID");
		mntmRestoreOldId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog(null,
						"Are you sure to restore ID of Persons to old ID?\n" +
						"This doesn't restore the length of ID\n" +
						"so please check old length of ID and configure it later.","Defragging ID",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null);
			    if(response == JOptionPane.CANCEL_OPTION){
			    	
			    }else{
				    try {
						PersonList list = (PersonList) persons;
					    List<IdBackup> idList;
					    File file = showFileChooser(true,null);
					    if(file != null){
							idList = ofh.readIdBackupXML(file);
							list.restorePrimaryKey(idList);
							showMessage("ID was restored to OLD IDs");
					    }
					} catch (JDOMException e) {
						showMessage(e.getMessage());
					} catch (IOException e) {
						showMessage(e.getMessage());
					} catch (NoPrimaryKeyFoundException e) {
						showMessage(e.getMessage());
					} catch (IdOverflowException e) {
						showMessage(e.getMessage());
					} catch (NullPointerException e) {
						
					}
			    }
			}
		});
		mnOption.add(mntmRestoreOldId);
		
		JMenu mnLookAndFeel = new JMenu("Look and Feel");
		mnOption.add(mnLookAndFeel);
		
		chckbxmntmDefault = new JCheckBoxMenuItem("Default");
		chckbxmntmDefault.setSelected(true);
		chckbxmntmDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectOneCheckBoxMenu(arg0);
				setOSinConfig("default");
			}
		});
		mnLookAndFeel.add(chckbxmntmDefault);
		
		chckbxmntmWindows = new JCheckBoxMenuItem("Windows");
		chckbxmntmWindows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectOneCheckBoxMenu(e);
				setOSinConfig("windows");
			}
		});
		mnLookAndFeel.add(chckbxmntmWindows);
		
		chckbxmntmSeaglass = new JCheckBoxMenuItem("Seaglass");
		chckbxmntmSeaglass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectOneCheckBoxMenu(e);
				setOSinConfig("mac");
			}
		});
		mnLookAndFeel.add(chckbxmntmSeaglass);
		
		chckbxmntmJavaMetal = new JCheckBoxMenuItem("Java Metal");
		chckbxmntmJavaMetal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectOneCheckBoxMenu(e);
				setOSinConfig("javametal");
			}
		});
		mnLookAndFeel.add(chckbxmntmJavaMetal);
		
		chckbxmntmWebLaf = new JCheckBoxMenuItem("Web LaF");
		chckbxmntmWebLaf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectOneCheckBoxMenu(arg0);
				setOSinConfig("weblookandfeel");
			}
		});
		mnLookAndFeel.add(chckbxmntmWebLaf);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('h');
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SubFrame frame = new SubFrame(new String[]{"OK","Cancel"},
						new About(persons), "About this APP", iconImage);
				frame.setVisible(true);
				}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel menuPane = new JPanel();
		contentPane.add(menuPane, BorderLayout.CENTER);
		menuPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnRegPerson = new JButton("1. Register a Person");
		btnRegPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchCommand(1);
			}
		});
		btnRegPerson.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegPerson.setMnemonic('1');
		menuPane.add(btnRegPerson);
		
		JButton btnRegChild = new JButton("2. Register a Child with a Parent");
		btnRegChild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(2);
			}
		});
		btnRegChild.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegChild.setMnemonic('2');
		menuPane.add(btnRegChild);
		
		JButton btnListAll = new JButton("3. List All Parents & Children");
		btnListAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(3);
			}
		});
		btnListAll.setHorizontalAlignment(SwingConstants.LEFT);
		btnListAll.setMnemonic('3');
		menuPane.add(btnListAll);
		
		JButton btnListChild = new JButton("4. List All Children of Parent (based on ID)");
		btnListChild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(4);
			}
		});
		btnListChild.setHorizontalAlignment(SwingConstants.LEFT);
		btnListChild.setMnemonic('4');
		menuPane.add(btnListChild);
		
		JButton btnCalIncome = new JButton("5. Calculate Weekly Income for All Children");
		btnCalIncome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(5);
			}
		});
		btnCalIncome.setHorizontalAlignment(SwingConstants.LEFT);
		btnCalIncome.setMnemonic('5');
		menuPane.add(btnCalIncome);
		
		JButton btnSeach = new JButton("6. Search for a Person (based on First Name & Last Name)");
		btnSeach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(6);
			}
		});
		btnSeach.setHorizontalAlignment(SwingConstants.LEFT);
		btnSeach.setMnemonic('6');
		menuPane.add(btnSeach);
		
		JButton btnRemPerson = new JButton("7. Remove Person(s) (based on ID)");
		btnRemPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(7);
			}
		});
		btnRemPerson.setHorizontalAlignment(SwingConstants.LEFT);
		btnRemPerson.setMnemonic('7');
		menuPane.add(btnRemPerson);
		
		JButton btnExitSystem = new JButton("8. Exit System");
		btnExitSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(8);
			}
		});
		btnExitSystem.setHorizontalAlignment(SwingConstants.LEFT);
		btnExitSystem.setMnemonic('8');
		menuPane.add(btnExitSystem);
		
		JPanel commandPane = new JPanel();
		contentPane.add(commandPane, BorderLayout.SOUTH);
		commandPane.setLayout(new BorderLayout(0, 0));
		
		txf_Com = new JTextField();
		txf_Com.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCommand(txf_Com.getText());
			}
		});
		commandPane.add(txf_Com, BorderLayout.CENTER);
		txf_Com.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchCommand(txf_Com.getText());
			}
		});
		commandPane.add(btnOk, BorderLayout.EAST);
		
		/**
		 * Create headerImage and Icon
		 */
		try{
			iconImage = ImageUtility.retrieveImageWithResize("iconLogo.png", 128, 128);
			this.setIconImage(iconImage);
			headerImage = ImageUtility.retrieveImage("largeLogo.jpg");
			headerImage = ImageUtility.resizeImageKeepRatioWithW(headerImage, this.getSize().width-40);
		}catch (Exception e){
			e.printStackTrace();
		}

		if(headerImage != null){
			JLabel lblHead = new JLabel(new ImageIcon(headerImage));
			contentPane.add(lblHead, BorderLayout.NORTH);
		}

}
	
	private void selectOneCheckBoxMenu(ActionEvent obj){
		JCheckBoxMenuItem jcbmi = (JCheckBoxMenuItem) obj.getSource();
		chckbxmntmDefault.setSelected(false);
		chckbxmntmWindows.setSelected(false);
		chckbxmntmSeaglass.setSelected(false);
		chckbxmntmJavaMetal.setSelected(false);
		chckbxmntmWebLaf.setSelected(false);
		jcbmi.setSelected(true);
	}
	
	private void setOSinConfig(String st){
		config.setOs(st);
		showMessage("The change affects from next time you launch.");
	}
	
	private void switchCommand(String command){
		try{
			int i = Integer.valueOf(command);
			switchCommand(i);
		}catch(Exception e){
			
		}
	}
	
	private void switchCommand(int command){
		txf_Com.setText("");
		switch(command){
		case 1: registerAPerson(persons);
		break;
		case 2: registerChildWithParent(persons);
		break;
		case 3: listPersons(persons);
		break;
		case 4: listChildrenOfParents(persons);
		break;
		case 5: calcTotalWeeklyFee(persons);
		break;
		case 6: findPerson(persons);
		break;
		case 7: removeAPerson(persons);
		break;
		case 8: System.exit(0);
		break;
		default:;
		}
	}
	
	/*
	 * Thanks to RequestNewListEvent, List All Person window and Calculate Income window
	 * can be opened all the time and by refreshing window after importing XML file,
	 * it is capable of new list.
	 * 1. Turn test into "true" then open List All Person window.
	 * 2. Remove some persons then refresh List All Person window.
	 * 3. Import Perons.xml them refresh List All Person window.
	 * 4. You can't see any changes.
	 * 5. Turn test into "false" then repeat 2 and 3.
	 * 6. Now you can see changes.
	 */
	
	private boolean test = false;

	public void registerAPerson(List<Person> creche){
		SubFrame frame = new SubFrame(new String[]{"Register","Cancel"},
				new RegisterPersonMainPane(persons), "Register a Person", iconImage);
		frame.setVisible(true);
	}
	
	public void listPersons(List<Person> creche){
		SubFrame frame = new SubFrame(new String[]{"Refresh","Cancel"},
				new ListPersonMainPane(persons,false), "List All Persons", iconImage);
		if(!test){
			frame.addRequestNewListListener(new RequestNewListListener(){
	
				@Override
				public void RequestNewList(RequestNewListEvent arg0) {
					SubFrame sf = (SubFrame) arg0.getSource();
					sf.setPersonList(persons);
				}
				
			});
		}
		frame.setVisible(true);		
	}
	
	public void findPerson(List<Person> creche){
		SubFrame frame = new SubFrame(new String[]{"Search","Cancel"},
				new SearchPersonByNameMainPane(persons), "Seach for Person(s) (based on name)", iconImage);
		frame.setVisible(true);		
	}
	
	public void removeAPerson(List<Person> creche){
		SubFrame frame = new SubFrame(new String[]{"Remove","Cancel"},
				new RemovePersonMainPane(persons), "Remove Person(s)", iconImage);
		frame.setVisible(true);
	}
	
	public void registerChildWithParent(List<Person> creche){
		SubFrame frame = new SubFrame(new String[]{"Register","Cancel"},
				new RegisterChildWithParentMainPane(persons), "Register a Child with a Parent", iconImage);
		frame.setVisible(true);		
	}
	
	public void listChildrenOfParents(List<Person> creche){
		SubFrame frame = new SubFrame(new String[]{"Show","Cancel"},
				new ListAllChildrenOfParentMainPane(persons), "List all Children of Parent (based on ID)", iconImage);
		frame.setVisible(true);		
	}
	
	public void calcTotalWeeklyFee(List<Person> creche){
		SubFrame frame = new SubFrame(new String[]{"Recalculate","Cancel"},
				new CalcTotalFee(persons), "Calculate Weekly Income for All Children", iconImage);
		frame.addRequestNewListListener(new RequestNewListListener(){
			@Override
			public void RequestNewList(RequestNewListEvent arg0) {
				SubFrame sf = (SubFrame) arg0.getSource();
				sf.setPersonList(persons);
			}
		});
		frame.setBounds(100, 100, 300, 105);
		frame.setVisible(true);				
	}
	
	private void showMessage(String title){
		JOptionPane.showMessageDialog(null,title);
	}
	
	private File showFileChooser(boolean isOpen, File fileName){
		File file = null;
		if(!config.getOs().trim().toLowerCase().equals("weblookandfeel")){
		    JFileChooser jFileChooser = new JFileChooser();
		    jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    jFileChooser.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
		    jFileChooser.setCurrentDirectory(new File("Setting"));
		    if(fileName != null){
		    	jFileChooser.setSelectedFile(fileName);
		    }
		    int option;
		    JFrame fileFrame = new JFrame();
		    fileFrame.setIconImage(iconImage);
		    if(isOpen){
		    	option = jFileChooser.showOpenDialog(fileFrame);
		    }else{
		    	option = jFileChooser.showSaveDialog(fileFrame);
		    }
		    if(option == JFileChooser.APPROVE_OPTION){
			    file = jFileChooser.getSelectedFile();
			    if(file.isDirectory()){
			    	File[] files = file.listFiles(new FilenameFilterTest());
			    	System.out.println(files.length);
			    	for(File fl: files){
			    		System.out.println(fl.getName());
			    	}
			    }
		    }
		}else{
			boolean ok = false;
		    WebFileChooser wfc = new WebFileChooser(new JFrame());
		    wfc.setCurrentDirectory(new File("Setting"));
		    wfc.setChooseFilter(new FileExtensionFilter("xml"));
		    wfc.setSelectionMode(SelectionMode.SINGLE_SELECTION);
		    wfc.setAvailableFilter(new FileExtensionFilter("xml"));
		    wfc.setIconImage(iconImage);
		    wfc.setOkListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(arg0.getSource());
				}
		    	
		    });
		    wfc.setVisible(true);
	    	file = wfc.getSelectedFile();
		}
		if(file == null){
			showMessage("Operation was canceled.");
		}
		return file;
	}

}