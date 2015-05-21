package us.codewalr.walrifier.feed;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import us.codewalr.walrifier.util.AnimationQueue;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class FeedLoadingBar extends View
{
	Animation showAnim, hideAnim;
	private final int height;
	private boolean drawing = false;
	private AnimationQueue animQueue;
	private WalriiLoadingAnimation anim;
	
	public FeedLoadingBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		height = (int) context.getResources().getDimension(R.dimen.loading_bar_height);
		
		showAnim = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t)
			{
				setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (height * smoothstep(interpolatedTime))));
			}
		};
		showAnim.setDuration(500);
		
		hideAnim = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t)
			{
				setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (height * smoothstep(1-interpolatedTime))));
			}
		};
		hideAnim.setDuration(500);
		
		animQueue = new AnimationQueue(this);
		anim = new WalriiLoadingAnimation(Walrifier.resources());
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		anim.draw(canvas);
		
		if (drawing)
			invalidate();
	}

	public float smoothstep(float x)
	{
		return x*x*(3 - 2*x);
	}
	
	public void show()
	{
		animQueue.add(showAnim);
		drawing = true;
	}
	
	public void hide()
	{
		animQueue.add(hideAnim);
		drawing = false;
	}
}
