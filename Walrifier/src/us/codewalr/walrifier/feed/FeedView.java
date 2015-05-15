package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class FeedView
{	
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
				if (failed)
					Toast.makeText(Walrifier.getContext(), "Failed to load feed", Toast.LENGTH_LONG).show();
			}
		};
	}
	
	public void setView()
	{
		Walrifier.getInstance().setContentView(R.layout.walrifierlayout);

		recycler = (RecyclerView) Walrifier.getInstance().findViewById(R.id.recycler);
		recycler.setHasFixedSize(true);

		layoutManager = new LinearLayoutManager(Walrifier.getInstance());
		recycler.setLayoutManager(layoutManager);

		adapter = new WalrusAdapter(new ArrayList<Post>());
		recycler.setAdapter(adapter);
		
		loadingAnim = (FeedLoadingView) Walrifier.getInstance().findViewById(R.id.loading);
	}
	
	public void updateFeed()
	{
		loadingAnim.show();
		Walrifier.getFeed().load(onLoad);
	}
}
