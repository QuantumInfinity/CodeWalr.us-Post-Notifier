package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import us.codewalr.walrifier.util.RBuffer;

public class Feed
{
	RBuffer<Post> lastPosts;
	boolean hasChanged;
	int lastID;
	IFeed[] params;
	
	public Feed(int lastID)
	{
		this.lastID = lastID;
		lastPosts = new RBuffer<Post>(Walrifier.getContext().getResources().getInteger(R.integer.max_feed_length));
		lastPosts.fill(null);
		hasChanged = false;
	}
	
	public void load(IFeed onFinished)
	{
		new FeedLoader(onFinished, lastID).execute();
	}
	
	public ArrayList<Post> getPosts()
	{
		return lastPosts.getList();
	}
}
