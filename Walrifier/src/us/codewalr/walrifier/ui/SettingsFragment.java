package us.codewalr.walrifier.ui;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.v4.preference.PreferenceFragment;

import com.robobunny.SeekBarPreference;
import com.robobunny.SeekBarPreference.ProgressChangeListener;

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
		
		SeekBarPreference seekbar = (SeekBarPreference) findPreference("service_check_time");
		seekbar.setPCL(new ProgressChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBarPreference sbp)
			{
				onPreferenceChange("service_check_time");
			}
		});
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
				Walrifier.startWalrusService(main);
			}else
			{
				Walrifier.stopWalrusService(main);
			}
			break;
		case "service_check_time":
			Walrifier.restartService(main);
			break;
		case "use_data":
			Walrifier.restartService(main);
			break;
		}
	}
}