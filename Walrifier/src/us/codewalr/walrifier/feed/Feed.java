package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.util.RBuffer;
import android.content.res.Resources;
import android.os.AsyncTask.Status;

public class Feed
{
	RBuffer<Post> lastPosts;
	boolean hasChanged;
	int lastID;
	IFeed onLoaded;
	FeedLoader currentTask;
	Resources res;
	
	public Feed(int lastID, Resources res)
	{
		this.lastID = lastID;
		this.res = res;
		lastPosts = new RBuffer<Post>(res.getInteger(R.integer.max_feed_length));
		lastPosts.fill(null);
		hasChanged = false;
	}
	
	public boolean load(IFeed onFinished)
	{
		if (currentTask == null || currentTask.getStatus() != Status.RUNNING)
		{
			currentTask = new FeedLoader(res, onFinished, lastID);
			currentTask.execute();
			return true;
		}
		return false;
	}
	
	public ArrayList<Post> push(ArrayList<Post> newPosts)
	{
		if (newPosts != null)
			for (int i = newPosts.size()-1; i >= 0; i--)
				lastPosts.push(newPosts.get(i));
		if (lastPosts.get(0) != null)
			lastID = lastPosts.get(0).postID;
		return lastPosts.getList();
	}
	
	public ArrayList<Post> getPosts()
	{
		return lastPosts.getList();
	}
}
