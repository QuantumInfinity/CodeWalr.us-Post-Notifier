package us.codewalr.walrifier.feed;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.util.AnimationQueue;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class FeedLoadingView extends View
{
	Animation showAnim, hideAnim, currAnim;
	private final int height;
	private final float speed = 2f;
	private boolean drawing = false;
	private Paint paint;
	private int frame = 0;
	private int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
	AnimationQueue animQueue;
	
	public FeedLoadingView(Context context, AttributeSet attrs)
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
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		frame++;
		float r = (float) getWidth()/2f*1.1f;
		float fl = r/(float) colors.length;
		
		for (int i=colors.length-1; i>=0; i--)
		{
			float n = i/(float) colors.length;
			paint.setColor(colors[mod(i -  (int) ((frame*speed)/fl),colors.length)]);
			canvas.drawCircle(getWidth()/2, getHeight()/2, smooth((((frame*speed) % fl) + n*r)/r, 3)*r, paint);
		}

		if (drawing)
			invalidate();
	}
	
	public int mod(int x, int y)
	{
		while(x >= y) x -= y;
		while(x < 0) x += y;
		return x;
	}
	
	public float sqr(float x)
	{
		return x*x;
	}
	
	public float smoothstep(float x)
	{
		return x*x*(3 - 2*x);
	}
	
	public float smooth(float x, int n)
	{
		for (int i=0; i<n; i++)
			x = smoothstep(x);
		return x;
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
