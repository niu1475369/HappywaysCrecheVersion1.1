package lee.won.hcv1.listElements;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

public class ListElementCell extends JPanel {

	/**
	 * Create the panel.
	 */
	public ListElementCell(Component arg0, Component arg1) {
		setLayout(new BorderLayout(0, 0));
		this.setSize(40, 60);
		add(arg0, BorderLayout.NORTH);
		add(arg1, BorderLayout.CENTER);
	}

}
