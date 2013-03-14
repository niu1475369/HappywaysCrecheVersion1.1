package lee.won.hcv1.gui;

import javax.swing.JPanel;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.abs.PersonType;
import lee.won.hcv1.events.*;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.InvalidInputException;
import lee.won.hcv1.exceptions.PersonNotFoundException;
import lee.won.hcv1.impl.Child;
import lee.won.hcv1.impl.Parent;
import lee.won.hcv1.impl.Seacher;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import lee.won.hcv1.listElements.ListElement;

import javax.swing.JRadioButton;
import java.awt.FlowLayout;

/**
 * 
 * @author Won Lee
 * @version 1.0 b3020730
 * 
 * b102055:	ListElementSelectedEvent Listener was added and it changes txf_PID text if it is adding child mode
 * b230645: minor bug fix
 * b262130: improved error handling and error message.
 * b3020730:Children in the list is not clickable now.
 * 
 */
public class RegisterPersonMainPane extends SubFrameMainPane{
//public class RegisterPersonMainPane extends SubFrameMainPane implements ListElementSelectedListener{
	private JTextField txf_fName;
	private JTextField txf_sName;
	private JTextField txf_Fee;
	private JTextField txf_PID;
	private JComboBox comboBox;
	private JPanel editPane;
	private ListPersonMainPane lpmp;
	private PersonType type = PersonType.Parent;
	
	private List<Person> creche;
	private JPanel genPane;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	
	private String gender = "Male";

	/**
	 * Create the panel.
	 */
	public RegisterPersonMainPane(List<Person> creche) {
		super(creche);
		this.creche = creche;
		
		setLayout(new BorderLayout(0, 0));

		List<PersonType> disables = new ArrayList();
		disables.add(PersonType.Child);
		lpmp = new ListPersonMainPane(creche,true,disables);
		//lpmp.addListElementSelectedListener(this);
		lpmp.addListElementSelectedListener(new ListElementSelectedListener() {
			@Override
			public void listElementSelected(ListElementSelectedEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("Event Received at RegisterPersonMainPane");
				ListElement le = (ListElement) arg0.getSource();
				int id = le.getId();
				//System.out.println(id);
				if(type.equals(PersonType.Child)){
					txf_PID.setText(""+id);
				}
			}
		});
		add(lpmp, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		editPane = new JPanel();
		panel.add(editPane, BorderLayout.NORTH);
		editPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		refreshEditPane("","");
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cmb = (JComboBox) e.getSource();
				if(!type.equals((PersonType) cmb.getSelectedItem())){
					type = (PersonType) cmb.getSelectedItem();
					refreshEditPane(txf_fName.getText(),txf_sName.getText());
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(PersonType.values()));
		panel.add(comboBox, BorderLayout.SOUTH);
	}
	
	private void refreshEditPane(String fName, String sName){
		editPane.removeAll();
		
		JLabel lblFirstName = new JLabel("First Name");
		editPane.add(lblFirstName);
		
		txf_fName = new JTextField();
		txf_fName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		txf_fName.setText(fName);
		editPane.add(txf_fName);
		txf_fName.setColumns(10);
		
		JLabel lblSurName = new JLabel("Sur Name");
		editPane.add(lblSurName);
		
		txf_sName = new JTextField();
		txf_sName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});

		txf_sName.setText(sName);
		editPane.add(txf_sName);
		txf_sName.setColumns(10);
		
		JLabel lblGen = new JLabel("Gender");
		editPane.add(lblGen);
		
		genPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) genPane.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		editPane.add(genPane);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setSelected(true);
		rdbtnMale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnFemale.setSelected(false);
			}
		});
		genPane.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnMale.setSelected(false);
			}
		});
		genPane.add(rdbtnFemale);
		
		if(type.equals(PersonType.Child)){
			JLabel lblFee = new JLabel("Fee");
			editPane.add(lblFee);
			
			txf_Fee = new JTextField();
			txf_Fee.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					action();
				}
			});

			editPane.add(txf_Fee);
			txf_Fee.setColumns(10);

			JLabel lblPID = new JLabel("Parent ID");
			editPane.add(lblPID);
			
			txf_PID = new JTextField();
			txf_PID.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					action();
				}
			});

			txf_PID.setText("0");
			editPane.add(txf_PID);
			txf_PID.setColumns(10);
			
			//When type is child, there requires more height for extra fields.
			//If it can't change window size, person list will get smaller.
			//The line below cause event (defined in SubFrameMainPane)
			//and the event is listened by SubFrame
			//By doing this, the size of person list stays same and get space for
			//extra fields for Children.
			actionResizeF(110,0);
		}else{
			actionResizeB(110,0);			
		}
		
		editPane.revalidate();
	}

	@Override
	public void action(){
		try{
			gender = (rdbtnMale.isSelected())? "Male": "Female";
			if(type.equals(PersonType.Parent)){
				Parent pr = new Parent(txf_fName.getText(), txf_sName.getText(), gender);
				creche.add(pr);
				JOptionPane.showMessageDialog(null,"Parent:\n"+pr.toString()+"\nwas added.");
			}else if(type.equals(PersonType.Child)){
				Seacher seacher = new Seacher(creche);
				if(!txf_PID.getText().equals("0")){
					Person ps = seacher.searchId(Integer.valueOf(txf_PID.getText()));
					if(ps instanceof Child){
						throw new PersonNotFoundException();
					}
				}
				Child ch = new Child(txf_fName.getText(), txf_sName.getText(), gender,
						Integer.valueOf(txf_Fee.getText()), Integer.valueOf(txf_PID.getText()));
				creche.add(ch);
				showMessage("Child:\n"+ch.toString()+"\nwas added.");
			}
			lpmp.action();
			clearAllTextField();
		}catch (InvalidInputException e){
			showMessage("Error: " + e.getMessage());
		}catch (IdOverflowException e){
			showMessage("There is no ID left.\n" +
					"Please defrag ID or change ID setting, or both.\n" + e);
		}catch (NumberFormatException e){
			showMessage("Error: " + "Fee and Parent ID has to be Number\n");
		} catch (PersonNotFoundException e) {
			showMessage("Error: " + "No 'Parent' ID = " + Integer.valueOf(txf_PID.getText()) + 
					" was found.\n" + "If you don't want to associate Child with Parent, enter 0.");
			txf_PID.setText("0");
		}
	}
	
	private void clearAllTextField(){
		txf_fName.setText("");
		txf_sName.setText("");
		if(type.equals(PersonType.Child)){
			txf_Fee.setText("");
			txf_PID.setText("");
		}
	}

	/*
	@Override
	public void listElementSelected(ListElementSelectedEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Event Received at RegisterPersonMainPane");
		ListElement le = (ListElement) arg0.getSource();
		int id = le.getId();
		System.out.println(id);
		if(type == 1){
			txf_PID.setText(""+id);
		}
	}
	*/
}
