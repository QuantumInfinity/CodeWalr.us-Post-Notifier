package us.codewalr.walrifier;

import java.util.ArrayList;

import us.codewalr.walrifier.feed.Feed;
import us.codewalr.walrifier.feed.IFeed;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Walrifier extends Activity
{
	static Walrifier instance;
	
	private Feed feed;
	private RecyclerView recycler;
	private WalrusAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.walrifierlayout);
		recycler = (RecyclerView) findViewById(R.id.recycler);

		recycler.setHasFixedSize(true);

		layoutManager = new LinearLayoutManager(this);
		recycler.setLayoutManager(layoutManager);

		adapter = new WalrusAdapter(new ArrayList<Post>());
		recycler.setAdapter(adapter);
		
		feed = new Feed(0);
		
		updateFeed();
	}
	
	public void refreshRecycler(ArrayList<Post> posts)
	{
		adapter.setPosts(posts);
		adapter.notifyDataSetChanged();
	}
	
	public void updateFeed()
	{
		feed.execute(new IFeed()
		{
			@Override
			public void onFeedLoaded(ArrayList<Post> posts, boolean hasNewPosts, boolean failed)
			{
				refreshRecycler(posts);
			}
		});
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
