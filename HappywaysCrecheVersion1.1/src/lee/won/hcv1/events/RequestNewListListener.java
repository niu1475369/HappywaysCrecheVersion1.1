package lee.won.hcv1.events;

import java.util.EventListener;

/**
 * 
 * @author Won Lee
 * @version b281040
 * b281040:	This class was created for refreshing list of SubFrameMainPane
 *
 */
public interface RequestNewListListener extends EventListener {

	public void RequestNewList(RequestNewListEvent arg0);
	
}
