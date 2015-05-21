package us.codewalr.walrifier;

import us.codewalr.walrifier.feed.Feed;
import us.codewalr.walrifier.ui.WalrifierDrawer;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Walrifier extends AppCompatActivity
{
	static Walrifier instance;
	static final String TAG = "Walrifier";
	
	private Feed feed;
	private WalrifierDrawer walrusDrawer;
	private DisplayMetrics metrics;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		
		metrics = new DisplayMetrics();         
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		feed = new Feed(0);
		
		walrusDrawer = new WalrifierDrawer();
		walrusDrawer.setView(this);
	}
	
	public static Walrifier getInstance()
	{
		return instance;
	}
	
	public static Feed getFeed()
	{
		return getInstance().feed;
	}
	
	public static Context getContext()
	{
		return instance;
	}
	
	public static DisplayMetrics getMetrics()
	{
		return getInstance().metrics;
	}
	
	public static int getDP(int pixels)
	{
		return (pixels * getMetrics().densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}
	
	public static int getPixels(float dp)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getMetrics());
	}
	
	public static String tag()
	{
		return TAG;
	}
	
	public static Resources resources()
	{
		return getContext().getResources();
	}
	
	public static FragmentManager fragmentManager()
	{
		return getInstance().getSupportFragmentManager();
	}
}
