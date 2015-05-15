package us.codewalr.walrifier.feed;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import us.codewalr.walrifier.util.AnimationQueue;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class FeedLoadingView extends View
{
	Animation showAnim, hideAnim;
	private final int height, walriiSizeDP = 40;
	private final float speed = 2f;
	private boolean drawing = false;
	private Paint paint;
	private int frame = 0;
	private int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
	private AnimationQueue animQueue;
	private Rect walriiOrig, walriiScaled;
	private Bitmap walrii_0, walrii_1;
	
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
		
		walrii_0 = BitmapFactory.decodeResource(Walrifier.getContext().getResources(), R.drawable.walrii_0);
		walrii_1 = BitmapFactory.decodeResource(Walrifier.getContext().getResources(), R.drawable.walrii_1);
		
		walriiOrig = new Rect(0, 0, walrii_0.getWidth(), walrii_0.getHeight());
		walriiScaled = new Rect(0, 0, Walrifier.getPixels(walriiSizeDP), Walrifier.getPixels(walriiSizeDP));
		
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
		
		canvas.translate(getWidth()/2 - walriiScaled.right/2, getHeight()/2 - walriiScaled.bottom/2);
		canvas.drawBitmap((int) ((System.currentTimeMillis() % 666) / 333) == 0 ? walrii_0 : walrii_1, walriiOrig, walriiScaled, paint);
		
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
