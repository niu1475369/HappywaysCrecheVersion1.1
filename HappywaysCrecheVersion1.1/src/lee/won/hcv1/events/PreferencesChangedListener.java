package lee.won.hcv1.events;

import java.util.EventListener;

public interface PreferencesChangedListener extends EventListener {

	public void PreferenceChanged(PreferencesChangedEvent evt);

}
