package lee.won.hcv1.gui;

import java.util.List;

import javax.swing.JPanel;

import lee.won.hcv1.abs.*;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class About extends SubFrameMainPane {

	/**
	 * Create the panel.
	 */
	public About(List<Person> list) {
		super(list);
		
		
		String html = "Author: Won Lee\n\n" +
				"External JARs - \n" +
				"'JDOM 2.0.4' ... http://www.jdom.org/\n" +
				"'Seaglass Look and Feel' ... https://code.google.com/p/seaglass/\n" +
				"'Web Look and Feel' ... http://weblookandfeel.com/\n\n" +
				"You can access all method listed in specification from Main Window.\n" +
				"Plus, you can use shortcut (Alt + [number 1-8]) thanks to setMnemonic method of JButton.\n" +
				"Extra features are accessible from Menu Bar on Main Window.\n" +
				"XML imput/output, changing ID allocation, defrag ID to save ID and restore OLD ID.\n" +
				"Most sub windows are compatible with shortcut, \"Alt + w\" to cancel and \"Alt + a\" to action.\n\n" +
				"I have made a Custom Event \"ListElementSelected\" this is to enter ID into proper textField or deal with the Person instance selected.\n\n" + 
				"'Register Person' - If Parent ID is not valid, the app won't register the child.\n" +
				"It set Parent ID as 0 and keep other field remaining.\n" +
				"If Parent Id is 0, it is concerned as no parent Registered to the child.\n" +
				"When changing Parent config to Child config, First Name and Parent Name stay in the TextField and vise versa.\n\n" +
				"Import and Export - These functions output/imput XML file that records Persons.\n" +
				"It uses JFileChooser and only xml file can be chosen by defualt.\n" +
				"If invalid XML file chosen, XML reading method will return JDOMException and show error message.\n\n" +
				"ID Management - \n" +
				"ID Setting - change amount of ID for each subclass of Person" +
				"Defrag ID - refills the gap of ID. The order of instances remains the same and this produce ID_Backup.xml.\n" +
				"Restore Old ID - converts Defragged ID into old.";
		
		JTextArea textArea = new JTextArea(html);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
	}

}
