package us.codewalr.walrifier;

import us.codewalr.walrifier.service.WalriiService;
import us.codewalr.walrifier.ui.WalrifierDrawer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Walrifier extends AppCompatActivity
{
	public static final String TAG = "Walrifier";

	private WalrifierDrawer walrusDrawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		startWalrusService(this);
		
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.drawable.walrii_0);
		
		walrusDrawer = new WalrifierDrawer(getSupportFragmentManager(), getSupportActionBar());
		walrusDrawer.setView(this);
	}
	
	public static SharedPreferences getSettings(Context ctx)
	{
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}	
	
	public static void restartService(Activity a)
	{
		stopWalrusService(a);
		startWalrusService(a);
	}
	
	public static void startWalrusService(Activity a)
	{
		if (!isServiceRunning(a, WalriiService.class) && Walrifier.getSettings(a).getBoolean("run_service", true))
		{
			Intent service = new Intent(a, WalriiService.class);
			service.putExtra("service_check_time", Walrifier.getSettings(a).getInt("service_check_time", 60));
			service.putExtra("use_data", Walrifier.getSettings(a).getBoolean("use_data", false));
			a.startService(service);
		}
		if (isServiceRunning(a, WalriiService.class) && !Walrifier.getSettings(a).getBoolean("run_service", true))
			stopWalrusService(a);
	}
	
	public static void stopWalrusService(Activity a)
	{
		a.stopService(new Intent(a, WalriiService.class));
	}
	
	public static boolean isServiceRunning(Activity a, Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) a.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
			if (serviceClass.getName().equals(service.service.getClassName()))
				return true;
		return false;
	}
	
	public static int getDP(DisplayMetrics m, int pixels)
	{
		return (pixels * m.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}
	
	public static int getPixels(DisplayMetrics m, float dp)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, m);
	}
}
