package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import lee.won.hcv1.abs.Person;

/**
 * 
 * @author Won
 * @version 1.0 b230955
 * b230955: The class was created
 *
 */
public class ShowResultFrame extends JFrame {

	private JPanel contentPane;
	/**
	 * 
	 * @param title
	 * @param list suppose this constructor won't receive list that doesn't have content in it.
	 */
	public ShowResultFrame(String title, List<Person> list) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(new ListPersonMainPane(list,false));
		add(scrollPane);
		setTitle(title);
		pack();
		setVisible(true);
	}

}
