package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class FeedView implements OnTouchListener
{	
	private RecyclerView recycler;
	private WalrusAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private IFeed onLoad;
	private FeedLoadingBar loadingAnim;
	private float startY = 0;
	private boolean loaded = false;
	private int activationDelta;
	
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
					Toast.makeText(Walrifier.getContext(), Walrifier.getContext().getString(R.string.feed_load_failed), Toast.LENGTH_SHORT).show();
			}
		};
		
		activationDelta = Walrifier.getPixels(Walrifier.resources().getInteger(R.integer.swipe_refresh_delta));
	}
	
	public void setView()
	{
		Walrifier.getInstance().setContentView(R.layout.walrifierlayout);

		recycler = (RecyclerView) Walrifier.getInstance().findViewById(R.id.recycler);
		recycler.setHasFixedSize(true);
		recycler.setOnTouchListener(this);
		
		layoutManager = new LinearLayoutManager(Walrifier.getInstance());
		recycler.setLayoutManager(layoutManager);

		adapter = new WalrusAdapter(new ArrayList<Post>());
		recycler.setAdapter(adapter);
		
		loadingAnim = (FeedLoadingBar) Walrifier.getInstance().findViewById(R.id.loading);
	}
	
	public boolean updateFeed()
	{
		boolean success = Walrifier.getFeed().load(onLoad);
		if (success)
			loadingAnim.show();
		return success;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		v.performClick();
		
		float delta = event.getY() - startY;
		
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			startY = event.getY();
		else if (event.getAction() == MotionEvent.ACTION_MOVE && recycler.computeVerticalScrollOffset() == 0 && delta > activationDelta && !loaded)
		{
			updateFeed();
			loaded = true;
		}else if(event.getAction() == MotionEvent.ACTION_UP)
			loaded = false;
		
		return false;
	}
}
