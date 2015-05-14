package us.codewalr.walrifier;

import java.util.ArrayList;

import us.codewalr.walrifier.feed.Feed;
import us.codewalr.walrifier.feed.IFeed;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class FeedView
{
	public static final int view = R.layout.walrifierlayout;
	
	private RecyclerView recycler;
	private WalrusAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private IFeed onLoad;
	
	public FeedView()
	{
		onLoad = new IFeed()
		{	
			@Override
			public void onFeedLoaded(ArrayList<Post> posts, boolean hasNewPosts, boolean failed)
			{
				adapter.setPosts(posts);
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	public void setView()
	{
		Walrifier.getInstance().setContentView(view);
		
		recycler = (RecyclerView) Walrifier.getInstance().findViewById(R.id.recycler);
		recycler.setHasFixedSize(true);

		layoutManager = new LinearLayoutManager(Walrifier.getInstance());
		recycler.setLayoutManager(layoutManager);

		adapter = new WalrusAdapter(new ArrayList<Post>());
		recycler.setAdapter(adapter);
	}
	
	public void updateFeed(Feed f)
	{
		f.execute(onLoad);
	}
}
