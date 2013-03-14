package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import lee.won.hcv1.abs.*;
import lee.won.hcv1.impl.Child;
import lee.won.hcv1.impl.PersonList;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Won Lee
 * @version 0.3 b151547
 * b151547:	Implementing the way to gather all child instances using findBorder method
 * 			=> the action method throws IndexOutOfBoundsException
 * 			=> when there is no instance or only Parent instances.
 *
 */
public class CalcTotalFee extends SubFrameMainPane {
	private JTextField txf_Total;
	
	/**
	 * Create the panel.
	 */
	public CalcTotalFee(List<Person> creche) {
		super(creche);
		
		JPanel feePane = new JPanel();
		add(feePane, BorderLayout.CENTER);
		feePane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTotalIncome = new JLabel("Total Income = ");
		feePane.add(lblTotalIncome, BorderLayout.WEST);
		
		txf_Total = new JTextField();
		txf_Total.setEditable(false);
		feePane.add(txf_Total, BorderLayout.CENTER);
		txf_Total.setColumns(10);
		
		//lpmp = new ListPersonMainPane(creche,true);
		//add(lpmp, BorderLayout.CENTER);
		
		action();
	}

	@Override
	public void action() {
		requestNewList();
		// TODO Auto-generated method stub
		PersonList persons = (PersonList) personList;
		int min = persons.findBorder(0, 0, persons.size()-1)+1;
		int max = persons.findBorder(1, min, persons.size()-1)+1;
		double total = 0.00;
		for(int i = min; i < max; i++){
			Child child = (Child) persons.get(i);
			total += child.getFee();
		}
		txf_Total.setText(""+total);
	}

}
