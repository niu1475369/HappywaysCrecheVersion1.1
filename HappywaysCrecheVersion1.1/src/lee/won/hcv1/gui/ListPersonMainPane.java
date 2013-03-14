package lee.won.hcv1.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.abs.PersonType;
import lee.won.hcv1.events.*;
import lee.won.hcv1.listElements.*;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * 
 * @author Won
 * @version 1.0 b282100
 * 
 * b102055:	Listens ListElementSelectedEvent and accepts ListElementSelectedEvent Listener
 * b222245: New constructor was added and old constructor was modified
 * 			=> Keep the compatibility for classes that use 2 parameter constructor
 * 			=> and this class is capable of new constructor of ListElement
 * b282000: Adopt Comparator to sort ListElement now it can sort ListElements by Name and ID.
 * 			=> important point is that it never sort list of Person (= personList).
 * 			=> sorting that list cause a lot of problem to my program
 * 			=> e.g. binary search cannot be applied to the list.
 * b282100: Compatible ascending and descending order.
 * 
 */
public class ListPersonMainPane extends SubFrameMainPane{
//public class ListPersonMainPane extends SubFrameMainPane implements ListElementSelectedListener{
	private JPanel panel;
	private boolean needButton;
	private ListElementSelectedListener listener;
	private String phrase = "Select";
	private Comparator<ListElement> comparator = new ListElementComparatorWithId();
	private boolean descending = false;
	private List<PersonType> disables = new ArrayList<PersonType>();
	
	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	public ListPersonMainPane(List<Person> creche, boolean needButton, String phrase, List<PersonType> disables){
		super(creche);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		this.needButton = needButton;
		this.phrase = phrase;
		if(disables != null)
			this.disables = disables;
		
		JPanel optionPane = new JPanel();
		add(optionPane, BorderLayout.SOUTH);
		optionPane.setLayout(new BorderLayout(0, 0));
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox jcb = (JComboBox) arg0.getSource();
				ListElementComparatorType st = (ListElementComparatorType) jcb.getSelectedItem();
				if(st.equals(ListElementComparatorType.ID)){
					comparator = new ListElementComparatorWithId();
				}else if(st.equals(ListElementComparatorType.FirstName)){
					comparator = new ListElementComparatorWithFName();
				}else if(st.equals(ListElementComparatorType.SurName)){
					comparator = new ListElementComparatorWithSName();
				}else if(st.equals(ListElementComparatorType.Fee)){
					comparator = new ListElementComparatorWithFee();
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(ListElementComparatorType.values()));
		optionPane.add(comboBox);
		
		JButton btnOrder = new JButton("Ascending");
		btnOrder.setMnemonic('s');
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton button = (JButton) arg0.getSource();
				if(button.getText().equals("Ascending")){
					button.setText("Descending");
					descending = true;
				}else{
					button.setText("Ascending");
					descending = false;
				}
			}
		});
		optionPane.add(btnOrder, BorderLayout.EAST);
		
		JButton btnReorder = new JButton("Reorder");
		btnReorder.setMnemonic('r');
		btnReorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				action(personList);
			}
		});
		optionPane.add(btnReorder, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		action();
	}
	
	public ListPersonMainPane(List<Person> creche, boolean needButton, String phrase){
		this(creche,needButton, phrase, null);
	}

	public ListPersonMainPane(List<Person> creche, boolean needButton, List<PersonType> enabled){
		this(creche,needButton, "Select", enabled);
	}
	
	public ListPersonMainPane(List<Person> creche, boolean needButton){
		this(creche,needButton,"Select");
	}

	@Override
	public void action() {
		requestNewList();
		action(personList);
	}
	
	public void action(List<Person> persons){
		panel.removeAll();
		int row = 5;
		if(personList.size()>5){
			row = personList.size();
		}
		panel.setLayout(new GridLayout(row, 1, 0, 0));
		
		List<ListElement> elements = new ArrayList<ListElement>();
		for(Person ps: personList){
			ListElement le = new ListElement(ps, needButton, phrase);
			if(needButton){
				//le.addListElementSelectedListener(this);
				le.addListElementSelectedListener(new ListElementSelectedListener() {
					@Override
					public void listElementSelected(ListElementSelectedEvent arg0) {
						// TODO Auto-generated method stub
						listener.listElementSelected(arg0);
					}
				});
			}
			if(isItDisabled(ps)){
				le.setButtonEnable(false);
			}
			elements.add(le);
			if(descending){
				Collections.sort(elements, Collections.reverseOrder(comparator));
			}else{
				Collections.sort(elements, comparator);				
			}
		}
		for(ListElement le: elements){
			panel.add(le);
		}
		panel.revalidate();
		// by doing this, the display after deleting Persons looks ok
		// without this, there are duplication of the instances whose button doesn't work
		// the way doesn't look elegant anyway.
		actionResizeF(1,1);
		actionResizeB(1,1);
	}
	
	private boolean isItDisabled(Person ps){
		for(PersonType pt: disables){
			if(ps.getType().equals(pt))
				return true;
		}
		return false;
	}

	public void addListElementSelectedListener(ListElementSelectedListener listener){
		this.listener = listener;
	}
	
	/*
	private void elementSelected(ListElementSelectedEvent arg0){
		if(listener != null){
			System.out.println("Sending Event from ListPersonMainPane");
			listener.listElementSelected(arg0);
		}
	}

	@Override
	public void listElementSelected(ListElementSelectedEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Receive Event at ListPersonMainPane");
		elementSelected(arg0);
	}
	*/
}
