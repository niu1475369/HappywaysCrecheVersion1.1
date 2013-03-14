package lee.won.hcv1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;

import lee.won.hcv1.abs.*;
import lee.won.hcv1.events.RequestNewListEvent;
import lee.won.hcv1.events.RequestNewListListener;
import lee.won.hcv1.events.SubFrameMainPaneEvent;
import lee.won.hcv1.events.SubFrameMainPaneListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * 
 * @author Won Lee
 * @version 1.0 b272355
 * 
 * b102055:	
 * b272355: add ActionOnSubFrameMainPane to revalidate this class.
 *
 */
public class SubFrame extends JFrame {

	protected JPanel contentPane;
	protected SubFrameMainPane mainPane;
	private String[] buttonLabel = new String[] {"OK","Cancel"};
	protected RequestNewListListener requestListListener;
	//protected List<Person> creche;

	
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public SubFrame(String[] buttonLabel, SubFrameMainPane ssmp, String title, Image icon) {
		if(buttonLabel != null){
			this.buttonLabel = buttonLabel;
		}
		setTitle(title);
		if(icon != null)
			setIconImage(icon);
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPane = new JPanel();
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		mainPane = ssmp;
		ssmp.addSubFrameMainPaneListener(new SubFrameMainPaneListener() {
			@Override
			public void actionedResizeF(SubFrameMainPaneEvent arg0) {
				// Make frame Larger by requested amount in ActionOnSubFrameMainPane
				SubFrameMainPane sfmp = (SubFrameMainPane) arg0.getSource();
				SubFrame sf = (SubFrame) sfmp.getParent().getParent().getParent().getParent();
				Dimension dm= sf.getSize();
				dm.setSize(dm.width+arg0.width, dm.height+arg0.height);
				sf.setSize(dm);
				sf.revalidate();				
			}

			@Override
			public void actionedResizeB(SubFrameMainPaneEvent arg0) {
				// Make frame Smaller by required amount in ActionOnSubFrameMainPane
				SubFrameMainPane sfmp = (SubFrameMainPane) arg0.getSource();
				SubFrame sf = (SubFrame) sfmp.getParent().getParent().getParent().getParent();
				Dimension dm= sf.getSize();
				dm.setSize(dm.width-arg0.width, dm.height-arg0.height);
				sf.setSize(dm);
				sf.revalidate();				
			}

			@Override
			public void requestNewList(
					SubFrameMainPaneEvent actionOnSubFrameMainPane) {
				// TODO Auto-generated method stub
				orderNewList();
			}		
		});
		contentPane.add(mainPane, BorderLayout.CENTER);
		
		contentPane.revalidate();

		JButton btnPositive = new JButton(buttonLabel[0]);
		btnPositive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainPane.action();
			}
		});
		btnPositive.setMnemonic('a');
		buttonPane.add(btnPositive);
		
		JButton btnNegative = new JButton(buttonLabel[1]);
		btnNegative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton jbt = (JButton) e.getSource();
				SubFrame frame = 
						(SubFrame) jbt.getParent().getParent().getParent().getParent().getParent();
				frame.dispose();
			}
		});
		btnNegative.setMnemonic('w');
		buttonPane.add(btnNegative);
		
		//pack();
	}

	public SubFrame(String[] buttonLabel, SubFrameMainPane ssmp, String title) {
		this(buttonLabel,ssmp,title,null);
	}
	
	public SubFrame(String[] buttonLabel, SubFrameMainPane ssmp) {
		this(buttonLabel,ssmp,"");
	}
	
	public void showErrorMessage(Exception e){
		JOptionPane.showMessageDialog(null,"Error: " + e);
	}
	
	public void addRequestNewListListener(RequestNewListListener requestListListener){
		this.requestListListener = requestListListener;
	}
	
	public void orderNewList(){
		if(requestListListener != null){
			requestListListener.RequestNewList(new RequestNewListEvent(this));
		}
	}
	
	public void setPersonList(List<Person> list){
		mainPane.setPersonList(list);
	}

}
