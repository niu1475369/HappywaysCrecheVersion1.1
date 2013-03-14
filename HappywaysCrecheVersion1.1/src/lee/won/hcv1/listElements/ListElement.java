package lee.won.hcv1.listElements;

import javax.swing.JPanel;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.events.ListElementSelectedEvent;
import lee.won.hcv1.events.ListElementSelectedListener;
import lee.won.hcv1.impl.Child;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextField;

/**
 * 
 * @author Won Lee
 * @version 1.1 b222245
 * 
 * b102055:	mechanism to cause event (ListElementSelectedEvent) was implemented.
 * 			ListElementSelectedEvent is to notify which id is selected.
 * b141633:	The looks was changed.
 * b222245: Now constructor requires String parameter to customise Label of Button
 *
 */
public class ListElement extends JPanel implements Comparable{
	
	private int id;
	private Person person;
	private ListElementSelectedListener listener;
	private JTextField txf_fname;
	private JTextField txf_sname;
	private JTextField txf_id;
	private JTextField txf_type;
	private JTextField txf_gen;
	private JTextField txf_fee;
	private JTextField txf_pid;
	private JButton btnSelect;

	/**
	 * Create the panel.
	 */
	public ListElement(Person ps, boolean needButton, String phrase) {
		person = ps;
		id = person.getID();
				
		boolean isChild = false;
		Child child = null;
		if(person instanceof Child){
			child = (Child) person;
			isChild = true;
		}
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 7, 0, 0));
		/*
		if(isChild){
			panel.setLayout(new GridLayout(1, 7, 0, 0));			
		}
		*/
		
		JLabel lblId = new JLabel("ID");
		txf_id = new JTextField(""+person.getID());
		txf_id.setEditable(false);
		txf_id.setColumns(9);
		
		panel.add(new ListElementCell(lblId,txf_id));
		
		JLabel lblFirstName = new JLabel("First Name");
		txf_fname = new JTextField(person.getFirstname());
		txf_fname.setEditable(false);
		txf_fname.setColumns(10);
		
		panel.add(new ListElementCell(lblFirstName,txf_fname));

		JLabel lblSecondName = new JLabel("Sur Name");
		txf_sname = new JTextField(person.getSurname());
		txf_sname.setEditable(false);
		txf_sname.setColumns(10);
		
		panel.add(new ListElementCell(lblSecondName,txf_sname));

		JLabel lblGen = new JLabel("Gender");
		txf_gen = new JTextField(person.getGender());
		txf_gen.setEditable(false);
		txf_gen.setColumns(6);
		
		panel.add(new ListElementCell(lblGen,txf_gen));

		JLabel lblType = new JLabel("Type");
		txf_type = new JTextField(person.getType().toString());
		txf_type.setEditable(false);
		txf_type.setColumns(6);

		panel.add(new ListElementCell(lblType,txf_type));

		if(isChild){
			JLabel lblFee = new JLabel("Fee");		
			txf_fee = new JTextField(""+child.getFee());
			txf_fee.setEditable(false);
			txf_fee.setColumns(5);
			
			panel.add(new ListElementCell(lblFee,txf_fee));

			JLabel lblPId = new JLabel("Parent ID");
			txf_pid = new JTextField(""+child.getParentId());
			txf_pid.setEditable(false);
			txf_pid.setColumns(9);

			panel.add(new ListElementCell(lblPId,txf_pid));

		}else{
			panel.add(new ListElementCell(new JPanel(),new JPanel()));
			panel.add(new ListElementCell(new JPanel(),new JPanel()));			
		}
		
		if(needButton){
			btnSelect = new JButton(phrase);
			btnSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					elementSelected();
				}
			});
			add(btnSelect, BorderLayout.WEST);
		}

	}
	
	public void setButtonEnable(boolean enable){
		if(btnSelect != null){
			btnSelect.setEnabled(enable);
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public ListElementSelectedListener getListener() {
		return listener;
	}

	public void setListener(ListElementSelectedListener listener) {
		this.listener = listener;
	}

	public String getFname() {
		return txf_fname.getText();
	}

	public String getSname() {
		return txf_sname.getText();
	}

	public String getType() {
		return txf_type.getText();
	}

	public String getGen() {
		return txf_gen.getText();
	}

	public double getFee() {
		return Double.valueOf(txf_fee.getText());
	}

	/**
	 * @return the txf_fee
	 */
	public JTextField getTxf_Fee() {
		return txf_fee;
	}
	
	public int getPid() {
		return Integer.valueOf(txf_pid.getText());
	}

	/**
	 * @return the txf_pid
	 */
	public JTextField getTxf_Pid() {
		return txf_pid;
	}

	public Person getPerson(){
		return person;
	}
	
	/**
	 * Add listener to the ListElement (from ListPersonMainPane)
	 * @param listener
	 */
	public void addListElementSelectedListener(ListElementSelectedListener listener){
		this.listener = listener;
	}
	
	/**
	 * 
	 */
	private void elementSelected(){
		if(listener != null){
			//System.out.println("Sending Event from List Element");
			listener.listElementSelected(new ListElementSelectedEvent(this));
		}
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
