package lee.won.hcv1.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.events.SubFrameMainPaneEvent;
import lee.won.hcv1.events.SubFrameMainPaneListener;

/**
 * 
 * @author Won Lee
 * @version 1.0 b262130
 * 
 * b102055:	abstract method, action(), is added.
 * b262130:	showMessage was moved from subclass.
 *
 */
public abstract class SubFrameMainPane extends JPanel {
	private SubFrameMainPaneListener subFrameListener;
	protected List<Person> personList;
	

	/**
	 * Create the panel.
	 */
	public SubFrameMainPane(List<Person> creche) {
		setLayout(new BorderLayout(0, 0));
		personList = creche;
	}

	public abstract void action();

	protected void showMessage(String title){
		JOptionPane.showMessageDialog(null,title);
	}
	
	public void addSubFrameMainPaneListener(SubFrameMainPaneListener listener){
		this.subFrameListener = listener;
	}
	
	protected void actionResizeF(int h, int w){
		if(subFrameListener != null){
			subFrameListener.actionedResizeF(new SubFrameMainPaneEvent(this,h,w));
		}		
	}
	
	protected void actionResizeB(int h, int w){
		if(subFrameListener != null){
			subFrameListener.actionedResizeB(new SubFrameMainPaneEvent(this,h,w));
		}		
	}
	
	protected void requestNewList(){
		if(subFrameListener != null){
			subFrameListener.requestNewList(new SubFrameMainPaneEvent(this,0,0));
		}				
	}
	
	protected void setPersonList(List<Person> personList){
		this.personList = personList;
	}
}
