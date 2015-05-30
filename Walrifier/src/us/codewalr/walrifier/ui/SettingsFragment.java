package us.codewalr.walrifier.ui;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.v4.preference.PreferenceFragment;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment
{	
	Activity main;
	
	public SettingsFragment(Activity main)
	{
		this.main = main;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
	{
		onPreferenceChange(preference.getKey());
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	public void onPreferenceChange(String key)
	{
		switch(key)
		{
		case "run_service":
			if (Walrifier.getSettings(main).getBoolean(key, true))
			{
				Log.v(Walrifier.TAG, "Starting service");
				Walrifier.startWalrusService(main);
			}else
			{
				Log.v(Walrifier.TAG, "Stopping service");
				Walrifier.stopWalrusService(main);
			}
			break;
		}
	}
}