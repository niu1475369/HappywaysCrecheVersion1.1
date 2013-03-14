package lee.won.hcv1.events;

import java.util.EventListener;

/**
 * 
 * @author Won
 * @version 1.0 b272322
 * 
 * b272322:	This listener was created
 */
public interface SubFrameMainPaneListener extends EventListener {

	public void actionedResizeF(SubFrameMainPaneEvent arg0);
	
	public void actionedResizeB(SubFrameMainPaneEvent arg0);

	public void requestNewList(SubFrameMainPaneEvent actionOnSubFrameMainPane);

}
