package us.codewalr.walrifier;

import us.codewalr.walrifier.feed.Feed;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class Walrifier extends Activity
{
	static Walrifier instance;
	
	private Feed feed;
	private FeedView feedView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		
		feedView = new FeedView();
		feedView.setView();
		
		feed = new Feed(0);
		
		feedView.updateFeed(feed);
	}
	
	public static Walrifier getInstance()
	{
		return instance;
	}
	
	public static Context getContext()
	{
		return instance;
	}
}
