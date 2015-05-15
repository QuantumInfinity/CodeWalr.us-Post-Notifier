package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class FeedView
{
	public static final int view = R.layout.walrifierlayout;
	
	private RecyclerView recycler;
	private WalrusAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private IFeed onLoad;
	private FeedLoadingView loadingAnim;
	
	public FeedView()
	{
		onLoad = new IFeed()
		{	
			@Override
			public void onFeedLoaded(ArrayList<Post> posts, boolean hasNewPosts, boolean failed)
			{
				adapter.setPosts(posts);
				adapter.notifyDataSetChanged();
				loadingAnim.hide();
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
		
		loadingAnim = (FeedLoadingView) Walrifier.getInstance().findViewById(R.id.loading);
	}
	
	public void updateFeed(Feed f)
	{
		loadingAnim.show();
		f.execute(onLoad);
	}
}
