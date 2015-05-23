package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class FeedView implements OnTouchListener
{	
	private RecyclerView recycler;
	private WalrusAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private IFeed onLoad;
	private FeedLoadingBar loadingAnim;
	private float startY = -1;
	private boolean loaded = false;
	private int activationDelta, scrollMinHeight;
	private FeedViewTouchListener touchListener;
	private Feed feed;
	
	public FeedView(final Context ctx)
	{
		feed = new Feed(0, ctx.getResources());
		
		onLoad = new IFeed()
		{
			@Override
			public void onFeedLoaded(ArrayList<Post> newPosts, boolean hasNewPosts, boolean failed)
			{
				adapter.setPosts(feed.push(newPosts));
				adapter.notifyDataSetChanged();
				loadingAnim.hide();
				if (failed)
					Toast.makeText(ctx, ctx.getString(R.string.feed_load_failed), Toast.LENGTH_SHORT).show();
			}
		};
		
		activationDelta = Walrifier.getPixels(ctx.getResources().getDisplayMetrics(), ctx.getResources().getInteger(R.integer.swipe_refresh_delta));
		scrollMinHeight = Walrifier.getPixels(ctx.getResources().getDisplayMetrics(), ctx.getResources().getInteger(R.integer.swipe_refresh_scroll_min_height));
	}
	
	@Deprecated
	public void setView(Activity a)
	{
		a.setContentView(R.layout.walrifierlayout);

		recycler = (RecyclerView) a.findViewById(R.id.recycler);
		recycler.setHasFixedSize(true);
		recycler.setOnTouchListener(this);
		
		layoutManager = new LinearLayoutManager(a);
		recycler.setLayoutManager(layoutManager);

		adapter = new WalrusAdapter(a, new ArrayList<Post>());
		recycler.setAdapter(adapter);
		
		loadingAnim = (FeedLoadingBar) a.findViewById(R.id.loading);
	}
	
	public View setFragment(ViewGroup container, LayoutInflater inflater)
	{
		View v = inflater.inflate(R.layout.walrifierlayout, container, false);
		
		recycler = (RecyclerView) v.findViewById(R.id.recycler);
		recycler.setHasFixedSize(true);
		recycler.setOnTouchListener(this);
		
		layoutManager = new LinearLayoutManager(container.getContext());
		recycler.setLayoutManager(layoutManager);

		adapter = new WalrusAdapter(container.getContext(), new ArrayList<Post>());
		recycler.setAdapter(adapter);
		
		touchListener = new FeedViewTouchListener(container.getContext());
		recycler.addOnItemTouchListener(touchListener);
		
		loadingAnim = (FeedLoadingBar) v.findViewById(R.id.loading); 
		
		return v;
	}
	
	public boolean updateFeed()
	{
		boolean success = feed.load(onLoad);
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
		else if (event.getAction() == MotionEvent.ACTION_MOVE && recycler.computeVerticalScrollOffset() < scrollMinHeight && delta > activationDelta && !loaded && startY + 1 > 0.001)
		{
			updateFeed();
			loaded = true;
		}else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			loaded = false;
			startY = -1;
		}
		
		return false;
	}
}
