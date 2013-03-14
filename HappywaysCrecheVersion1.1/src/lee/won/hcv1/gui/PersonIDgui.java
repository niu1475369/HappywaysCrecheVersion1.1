package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import lee.won.hcv1.abs.*;
import lee.won.hcv1.impl.PersonID;
import lee.won.hcv1.impl.PersonList;

/**
 * 
 * @author Won Lee
 * @version 1.0 b150807
 * b150807:	Basic method was implemented.
 * 			=> Check actual number of instances for each subclasses in the list
 * 			=> to prevent setting small number of IDs.
 *
 */
public class PersonIDgui extends JFrame {

	private JPanel contentPane;
	private JSlider parentSlider;
	private JSlider childSlider;
	private JLabel lblParentId;
	private JLabel lblChildId;
	private JProgressBar remainIdBar;
	private JLabel lblRemainId;
	private int numMaxID = 2146;
	private JPanel buttonPane;
	private JButton btnApply;
	private JButton btnCancel;
	private List<Person> persons;

	/**
	 * Create the frame.
	 */
	public PersonIDgui(List<Person> creche) {
		persons = creche;
		
		setTitle("Setting ID Length");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel mainPane = new JPanel();
		contentPane.add(mainPane, BorderLayout.CENTER);
		mainPane.setLayout(new BorderLayout(0, 0));
		
		JPanel controlPane = new JPanel();
		mainPane.add(controlPane, BorderLayout.CENTER);
		controlPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblParentId = new JLabel("Number of Parent ID ("+(PersonID.getParentLength()*1000000)+" IDs)");
		controlPane.add(lblParentId);
		
		parentSlider = new JSlider();
		parentSlider.setValue(PersonID.getParentLength());
		parentSlider.setMinimum(1);
		parentSlider.setMajorTickSpacing(10);
		parentSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblParentId.setText("Number of Parent ID ("+(parentSlider.getValue()*1000000)+" IDs)");
				int remainIdNum = numMaxID - parentSlider.getValue() - childSlider.getValue();
				remainIdBar.setValue(remainIdNum);
				lblRemainId.setText("Number of Remain ID ("+(remainIdNum*1000000)+" IDs)");
				btnApply.setEnabled(true);
				btnCancel.setEnabled(true);
			}
		});
		controlPane.add(parentSlider);
		
		lblChildId = new JLabel("Number of Child ID ("+(PersonID.getChildLength()*1000000)+" IDs)");
		controlPane.add(lblChildId);
		
		childSlider = new JSlider();
		childSlider.setMinimum(PersonID.getChildLength());
		childSlider.setValue(1);
		childSlider.setPaintLabels(true);
		childSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblChildId.setText("Number of Child ID ("+(childSlider.getValue()*1000000)+" IDs)");
				int remainIdNum = numMaxID - parentSlider.getValue() - childSlider.getValue();
				remainIdBar.setValue(remainIdNum);
				lblRemainId.setText("Number of Remain ID ("+(remainIdNum*1000000)+" IDs)");			
				btnApply.setEnabled(true);
				btnCancel.setEnabled(true);
			}
		});
		controlPane.add(childSlider);
		
		JPanel remainIdPane = new JPanel();
		mainPane.add(remainIdPane, BorderLayout.SOUTH);
		remainIdPane.setLayout(new BorderLayout(0, 0));
				
		remainIdBar = new JProgressBar();
		remainIdBar.setMaximum(numMaxID);
		int remainIdNum = numMaxID - parentSlider.getValue() - childSlider.getValue();
		remainIdBar.setValue(remainIdNum);
		remainIdPane.add(remainIdBar, BorderLayout.CENTER);
		
		lblRemainId = new JLabel("Number of Remain ID ("+(remainIdNum*1000000)+" IDs)");
		remainIdPane.add(lblRemainId, BorderLayout.NORTH);
		
		buttonPane = new JPanel();
		remainIdPane.add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new BorderLayout(0, 0));
		
		btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PersonID.changeParentLength(parentSlider.getValue());
				PersonID.changeChildLength(childSlider.getValue());
				btnApply.setEnabled(false);
				btnCancel.setEnabled(false);
				JOptionPane.showMessageDialog(null,"ID setting was changed." + "Parent Length:"
				+ PersonID.getParentLength()+"  Child Length:"+PersonID.getChildLength());
			}
		});
		btnApply.setEnabled(false);
		buttonPane.add(btnApply, BorderLayout.CENTER);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentSlider.setValue(PersonID.getParentLength());
				childSlider.setValue(PersonID.getChildLength());
				btnApply.setEnabled(false);
				btnCancel.setEnabled(false);
			}
		});
		btnCancel.setEnabled(false);
		buttonPane.add(btnCancel, BorderLayout.EAST);
	}
	
	private void changePersonID(){
		
	}
}
