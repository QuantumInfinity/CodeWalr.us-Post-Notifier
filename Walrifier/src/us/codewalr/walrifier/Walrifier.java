package us.codewalr.walrifier;

import us.codewalr.walrifier.bb.BBParser;
import us.codewalr.walrifier.feed.Feed;
import us.codewalr.walrifier.feed.FeedView;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class Walrifier extends Activity
{
	static Walrifier instance;
	static final String TAG = "Walrifier";
	
	private Feed feed;
	private FeedView feedView;
	private DisplayMetrics metrics;
	private BBParser bbParser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		
		metrics = new DisplayMetrics();         
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		initImageLoader();
		
		feed = new Feed(0);
		feedView = new FeedView();
		feedView.setView();
		feedView.updateFeed();
		
		bbParser = new BBParser();
	}

	private static void initImageLoader()
	{
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		{{
			cacheOnDisk(false);
			cacheInMemory(true);
			imageScaleType(ImageScaleType.EXACTLY);
			displayer(new SimpleBitmapDisplayer());
		}}.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
		{{
			diskCacheSize(100 * 1024 * 1024);
			memoryCache(new WeakMemoryCache());
		}}.defaultDisplayImageOptions(defaultOptions).build();
		
		ImageLoader.getInstance().init(config);
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
	
	public static Spanned parseBB(View v, String text)
	{
		return getInstance().bbParser.parse(v, text);
	}
	
	public static Resources resources()
	{
		return getInstance().getResources();
	}
}
