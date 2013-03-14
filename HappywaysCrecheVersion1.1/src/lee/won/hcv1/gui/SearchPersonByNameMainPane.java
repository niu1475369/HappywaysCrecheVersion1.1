package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.events.*;
import lee.won.hcv1.exceptions.PersonNotFoundException;
import lee.won.hcv1.impl.Seacher;

import java.awt.GridLayout;

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
 * @version 1.0 b230725
 * 
 * b230725:	This shows result in new Frame now.
 *
 */
public class SearchPersonByNameMainPane extends SubFrameMainPane {
	private ListPersonMainPane lpmp;
	private List<Person> creche;
	private Seacher seacher;
	private JTextField txf_FName;
	private JTextField txf_SName;
	
	/**
	 * Create the panel.
	 */
	public SearchPersonByNameMainPane(List<Person> creche) {
		super(creche);
		this.creche = creche;
		seacher = new Seacher(creche);

		lpmp = new ListPersonMainPane(creche,false);
		add(lpmp, BorderLayout.CENTER);
		
		JPanel namePane = new JPanel();
		add(namePane, BorderLayout.SOUTH);
		namePane.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblFName = new JLabel("FirstName");
		namePane.add(lblFName);
		
		JLabel lblSName = new JLabel("SurName");
		namePane.add(lblSName);
		
		txf_FName = new JTextField();
		txf_FName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		namePane.add(txf_FName);
		txf_FName.setColumns(10);
		
		txf_SName = new JTextField();
		txf_SName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		namePane.add(txf_SName);
		txf_SName.setColumns(10);

		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		List<Person> list;
		try {
			list = seacher.seachName(txf_FName.getText(), txf_SName.getText());
			if(list.size()>0){
				String fname = (txf_FName.getText().equals(""))? "": " First Name = " +txf_FName.getText();
				String sname = (txf_SName.getText().equals(""))? "": " Sur Name = " +txf_SName.getText();
				String title = "Search Result for" + fname + sname;
				ShowResultFrame frame = new ShowResultFrame(title,list);
			}
			revalidate();
		} catch (PersonNotFoundException e) {
			// Clear text field if no person found
			txf_FName.setText("");
			txf_SName.setText("");
			showMessage("Error: " + e.getMessage());
		}	
	}

}
