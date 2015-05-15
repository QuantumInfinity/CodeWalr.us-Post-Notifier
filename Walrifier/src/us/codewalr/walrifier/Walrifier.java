package us.codewalr.walrifier;

import us.codewalr.walrifier.feed.Feed;
import us.codewalr.walrifier.feed.FeedView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Walrifier extends Activity
{
	static Walrifier instance;
	static final String TAG = "Walrifier";
	
	private Feed feed;
	private FeedView feedView;
	private DisplayMetrics metrics;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		
		metrics = new DisplayMetrics();         
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		feed = new Feed(0);
		feedView = new FeedView();
		feedView.setView();
		feedView.updateFeed();
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
}
