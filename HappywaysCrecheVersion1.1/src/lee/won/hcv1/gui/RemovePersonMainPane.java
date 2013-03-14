package lee.won.hcv1.gui;

import javax.swing.JPanel;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.events.*;
import lee.won.hcv1.exceptions.PersonNotFoundException;
import lee.won.hcv1.impl.Seacher;
import lee.won.hcv1.impl.Utilities;
import lee.won.hcv1.listElements.ListElement;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Won Lee
 * @version 1.0 b3020807
 * 
 * b102055:	ListElementSelectedEvent Listener was added and it changes text of txf_ID to selected ID
 * b222245: Action method is modified so that it can remove multiple instances
 * 			and user can delete a instance of Person by just clicking "Remove" button.
 * 			=> Label "Remove" on the button is achieved by modifying ListPersonMainPane and ListElement
 * b251655: try block in the try block to catch PersonNotFoundExceptoin for every attempt
 * 			seacher was introduced to find delete target by id.
 * b3020807:It is possible to enter multiple ranges of IDs. (e.g. "1-5,15,17")
 */
public class RemovePersonMainPane extends SubFrameMainPane {
	private JTextField txf_ID;
	private ListPersonMainPane lpmp;
	private JLabel lblToEnterMultiple;

	/**
	 * Create the panel.
	 */
	public RemovePersonMainPane(List<Person> creche) {
		super(creche);
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		txf_ID = new JTextField();
		txf_ID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				action();
			}
		});
		panel.add(txf_ID);
		txf_ID.setColumns(10);
		
		JLabel lbl_ID = new JLabel("ID: ");
		panel.add(lbl_ID, BorderLayout.WEST);
		
		lblToEnterMultiple = new JLabel("To enter multiple IDs, separate them with \",\". " +
				"To enter range of IDs, use \"-\". You can use both at the same time.");
		panel.add(lblToEnterMultiple, BorderLayout.NORTH);
		
		lpmp = new ListPersonMainPane(creche,true,"Remove");
		lpmp.addListElementSelectedListener(new ListElementSelectedListener() {
			@Override
			public void listElementSelected(ListElementSelectedEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("Event Received at RegisterPersonMainPane");
				ListElement le = (ListElement) arg0.getSource();
				int id = le.getId();
				//System.out.println(id);
				txf_ID.setText(""+id);
				action();
			}
		});
		add(lpmp, BorderLayout.CENTER);

	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		try{
			List<Integer> ids = Utilities.convertStringToInts(txf_ID.getText());
			String stId = (ids.size() == 1)? ""+ids.get(0): "Multiple IDs Entered";
			int response = JOptionPane.showConfirmDialog(null,
					"Are you sure to DELETE Persons (ID = "+stId+")","DELETING Person",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null);
		    if(response == JOptionPane.CANCEL_OPTION){
		    	//System.exit(0);
		    }else{
		    	Seacher seacher = new Seacher(personList);
		    	boolean notFound = false;
		    	Stack<Integer> stack = new Stack<Integer>();
		    	for(int id: ids){
		    		try{
				    	Person ps = seacher.searchId(id);
				    	personList.remove(ps);
		    		}catch (PersonNotFoundException e){
		    			notFound = true;
		    			//The exception holds id and push the id into the stack
		    			//so that the IDs can be shown later
		    			stack.push(Integer.valueOf(e.getId()));
		    		}
		    	}
				lpmp.action();
				if(notFound){
					String st = "These Person couldn't be found:\nID = ";
					int size = stack.size();
					for(int i=0 ; i <size; i++){
						st += ""+stack.pop()+",";
						if(i%10==0){
							st+="\n";
						}
						if(i>=100){
							st+="and so on, so on...";
							break;
						}
					}
					st+="\n rest of Person were deleted.";
					showMessage(st);
				}
				txf_ID.setText("");
			}
		} catch (Exception e){
			showMessage("Error: " + e.getMessage());
		}
		
		// by doing this, the display after deleting Persons looks ok
		// without this, there are duplication of the instances whose button doesn't work
		// the way doesn't look elegant anyway.
		actionResizeF(1,1);
		actionResizeB(1,1);
	}

}
