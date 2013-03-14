package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.abs.PersonType;
import lee.won.hcv1.events.*;
import lee.won.hcv1.exceptions.PersonNotFoundException;
import lee.won.hcv1.impl.Child;
import lee.won.hcv1.impl.PersonID;
import lee.won.hcv1.impl.PersonList;
import lee.won.hcv1.impl.Seacher;
import lee.won.hcv1.impl.Utilities;
import lee.won.hcv1.listElements.ListElement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Won Lee
 * @version 1.0 b3020730
 * 
 * b102055:	ListElementSelectedEvent Listener was added and it changes text of txf_ID to selected ID
 * b181634: action method uses findBorder method of PersonList to find min and max index value
 * 			=> 	findBorder method has to be fixed that throwing IndexOutBoundException when
 * 				there is no child instance
 * b220715:	Capable of multiple ID and shows the result in new frame.
 * b262130: improved listElementSelectedEvent handling
 * 			=> it action only when parent was clicked.
 * b3020730:Children in the list is not clickable now.
 * 			And it is possible to enter multiple ranges of IDs. (e.g. "1-5,15,17")
 *
 */
public class ListAllChildrenOfParentMainPane extends SubFrameMainPane {
	private ListPersonMainPane lpmp;
	private JTextField txf_ID;
	private Seacher seacher;
	private JLabel lblToEnterMultiple;
	
	/**
	 * Create the panel.
	 */
	public ListAllChildrenOfParentMainPane(List<Person> creche) {
		super(creche);
		seacher = new Seacher(creche);

		List<PersonType> disables = new ArrayList();
		disables.add(PersonType.Child);
		lpmp = new ListPersonMainPane(personList,true,disables);
		lpmp.addListElementSelectedListener(new ListElementSelectedListener() {
			@Override
			public void listElementSelected(ListElementSelectedEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("Event Received at RegisterPersonMainPane");
				ListElement le = (ListElement) arg0.getSource();
				if(le.getType().equals("Parent")){
					int id = le.getId();
					//System.out.println(id);
					txf_ID.setText(""+id);
					action();
				}
			}
		});
		add(lpmp, BorderLayout.CENTER);
		
		JPanel idPane = new JPanel();
		add(idPane, BorderLayout.SOUTH);
		idPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblId = new JLabel("ID: ");
		idPane.add(lblId, BorderLayout.WEST);
		
		txf_ID = new JTextField();
		txf_ID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				action();
			}
		});
		idPane.add(txf_ID, BorderLayout.CENTER);
		txf_ID.setColumns(10);
		
		lblToEnterMultiple = new JLabel("To enter multiple IDs, separate them with \",\". " +
				"To enter range of IDs, use \"-\". You can use both at the same time.");
		idPane.add(lblToEnterMultiple, BorderLayout.NORTH);
	}

	@Override
	public void action() {
		if(txf_ID.getText() == null || txf_ID.getText().equals("")){
			lpmp.action();
		}else{
			try{
				List<Integer> ids = Utilities.convertStringToInts(txf_ID.getText());
				PersonList list = (PersonList) personList;
				int min = list.findBorder(0, 0, personList.size()-1)+1;
				int max = list.findBorder(1, min, personList.size()-1)+1;
				
		    	for(int id: ids){
					List<Person> children = new ArrayList<Person>();
					for(int i = min; i < max; i++){
						Child child = (Child) personList.get(i);
						if(child.getParentId() == id){
							//System.out.println(child.toString());
							children.add(child);
						}
					}
					
					if(children.size()>0){
						String noChil = (children.size()>1)? "Children ": "Child ";
						String title = noChil + "of Parent ID = " + id;
						ShowResultFrame frame = new ShowResultFrame(title,children);
					}else{
						showMessage("No children found from the Person ID = "+id);
					}
		    	}
			}catch (NumberFormatException e){
				showMessage("Please Enter Number\n" +
						"To enter multiple number, use \",\" to split number");
			}catch (Exception e){
				showMessage("Error: " + e);
			}
			txf_ID.setText("");
		}
	}

}
