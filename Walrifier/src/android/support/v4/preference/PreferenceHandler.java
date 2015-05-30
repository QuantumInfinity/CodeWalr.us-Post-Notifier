package android.support.v4.preference;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

public class PreferenceHandler extends Handler
{
	private final WeakReference<PreferenceFragment> target;
	
	public PreferenceHandler(PreferenceFragment target)
	{
		this.target = new WeakReference<PreferenceFragment>(target);
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
			case PreferenceFragment.MSG_BIND_PREFERENCES:
				target.get().bindPreferences();
				break;
		}
	}
}
