package lee.won.hcv1.events;

import java.util.EventObject;

/**
 * 
 * @author Won
 * @version 1.0 b272322
 * 
 * b272322:	This event was created
 */
public class SubFrameMainPaneEvent extends EventObject {
	public int height = 0;
	public int width = 0;

	public SubFrameMainPaneEvent(Object arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public SubFrameMainPaneEvent(Object arg0, int height, int width) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.height = height;
		this.width = width;
	}
}
