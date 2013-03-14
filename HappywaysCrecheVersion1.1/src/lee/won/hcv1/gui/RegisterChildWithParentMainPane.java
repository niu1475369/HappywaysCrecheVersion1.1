package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import lee.won.hcv1.abs.*;
import lee.won.hcv1.events.*;
import lee.won.hcv1.impl.*;
import lee.won.hcv1.listElements.ListElement;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Won Lee
 * @version 1.0 b262130
 * 
 * b102055:	ListElementSelectedEvent Listener was added and the event changes suitable JTextFIeld to selected ID.
 * b142207: Action method was implemented.
 * b251623: showMessage was implemented to reduce amount of code;
 * b262130: showMessage was moved to SubFrameMainPane class;
 *
 */
public class RegisterChildWithParentMainPane extends SubFrameMainPane {
	private ListPersonMainPane lpmp;
	private JTextField txf_PID;
	private JTextField txf_CID;
	private List<Person> persons;
	private Seacher seacher;
	
	/**
	 * Create the panel.
	 */
	public RegisterChildWithParentMainPane(List<Person> creche) {
		super(creche);
		persons = creche;
		seacher = new Seacher(persons);
		
		lpmp = new ListPersonMainPane(creche,true);
		//lpmp.addListElementSelectedListener(this);
		lpmp.addListElementSelectedListener(new ListElementSelectedListener() {
			@Override
			public void listElementSelected(ListElementSelectedEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("Event Received at RegisterPersonMainPane");
				ListElement le = (ListElement) arg0.getSource();
				Person ps = le.getPerson();
				if(ps instanceof Parent){
					txf_PID.setText(""+ps.getID());
				}else if(ps instanceof Child){
					txf_CID.setText(""+ps.getID());
				}
			}
		});
		add(lpmp, BorderLayout.CENTER);
		
		JPanel optionPane = new JPanel();
		add(optionPane, BorderLayout.SOUTH);
		optionPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel parentPane = new JPanel();
		optionPane.add(parentPane);
		parentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblPID = new JLabel("Parent ID");
		parentPane.add(lblPID, BorderLayout.WEST);
		
		txf_PID = new JTextField();
		txf_PID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		parentPane.add(txf_PID, BorderLayout.CENTER);
		txf_PID.setColumns(10);
		
		JPanel childPane = new JPanel();
		optionPane.add(childPane);
		childPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCID = new JLabel("Child ID");
		childPane.add(lblCID, BorderLayout.WEST);
		
		txf_CID = new JTextField();
		txf_CID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		childPane.add(txf_CID, BorderLayout.CENTER);
		txf_CID.setColumns(10);

	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		try{
			int c_id = Integer.valueOf(txf_CID.getText());
			int p_id = Integer.valueOf(txf_PID.getText());
			Child child = (Child) seacher.searchId(c_id);
			seacher.searchId(p_id);
			child.setParentId(p_id);
			lpmp.action();
			txf_PID.setText("");
			txf_CID.setText("");
			showMessage("Child ID = "+c_id+" is assigned with Parent ID = "+p_id);;
		}catch (ClassCastException e){
			showMessage("Invalid child ID");
		}catch (NumberFormatException e){
			showMessage("Input was Invalid");
		}catch (Exception e){
			showMessage("Error: "+e.getMessage());
		}
	}

}
