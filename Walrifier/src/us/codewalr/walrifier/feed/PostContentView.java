package us.codewalr.walrifier.feed;

import us.codewalr.walrifier.bb.FutureDrawable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class PostContentView extends TextView
{
	final int ms = 100;
	
	Handler handler;
	Runnable runnable;
	boolean isDrawing = true;
	
	public PostContentView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		handler = new Handler();
		
		runnable = new Runnable()
		{	
			@Override
			public void run()
			{
				if (isDrawing)
				{
					invalidate();
					handler.postDelayed(this, ms);
				}
			}
		};
		
		startDrawing();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		drawURLDrawables(canvas);
	}
	
	public void drawURLDrawables(Canvas canvas)
	{
		CharSequence text = getText();
		if (text instanceof Spanned)
		{
			Spanned span = (Spanned) text;
			ImageSpan[] spans = span.getSpans(0, span.length() - 1, ImageSpan.class);
			Drawable d;
			for (ImageSpan s : spans)
				if ((d = s.getDrawable()) instanceof FutureDrawable)
					((FutureDrawable) d).draw(canvas);
		}
	}
	
	public void startDrawing()
	{
		isDrawing = true;
		handler.postDelayed(runnable, ms);
	}
	
	public void stopDrawing()
	{
		isDrawing = false;
	}
	
	public boolean isDrawing()
	{
		return isDrawing;
	}
}
