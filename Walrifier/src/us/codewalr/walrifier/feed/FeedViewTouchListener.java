package us.codewalr.walrifier.feed;

import us.codewalr.walrifier.Walrifier;
import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class FeedViewTouchListener implements RecyclerView.OnItemTouchListener
{
	GestureDetector detector;
	Context ctx;
	
	public FeedViewTouchListener(Context ctx)
	{
		detector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener()
		{
			@Override
			public boolean onDoubleTap(MotionEvent e)
			{
				return true;
			}
		});
		this.ctx = ctx;
	}
	
	@Override
	public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e)
	{
		View card = view.findChildViewUnder(e.getX(), e.getY());
		int position = view.getChildAdapterPosition(card);
		
		if (detector.onTouchEvent(e))
		{
			WalrusAdapter adapter = (WalrusAdapter) view.getAdapter();
			ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getPostLink(position))));
		}
		
		return false;
	}

	@Override
	public void onTouchEvent(RecyclerView view, MotionEvent motionEvent){}
}