package us.codewalr.walrifier;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class WalriiLoadingAnimation
{
	private final int walriiSizeDP = 40;
	private final float speed = 2f;
	private Paint paint;
	private int frame = 0;
	private int[] colors = {0xFFF44336, 0xFFFFEB3B, 0xFF8BC34A, 0xFF2196F3};
	private Rect walriiOrig, walriiScaled;
	private Bitmap walrii_0, walrii_1;
	
	public WalriiLoadingAnimation(Resources res)
	{
		walrii_0 = BitmapFactory.decodeResource(res, R.drawable.walrii_0);
		walrii_1 = BitmapFactory.decodeResource(res, R.drawable.walrii_1);
		
		walriiOrig = new Rect(0, 0, walrii_0.getWidth(), walrii_0.getHeight());
		walriiScaled = new Rect(0, 0, Walrifier.getPixels(walriiSizeDP), Walrifier.getPixels(walriiSizeDP));
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setDither(false);
		paint.setFilterBitmap(false);
	}
	
	public void draw(Canvas c)
	{
		frame++;
		float r = (float) c.getWidth()/2f*1.1f;
		float fl = r/(float) colors.length;
		
		for (int i=colors.length-1; i>=0; i--)
		{
			float n = i/(float) colors.length;
			paint.setColor(colors[mod(i -  (int) ((frame*speed)/fl),colors.length)]);
			c.drawCircle(c.getWidth()/2, c.getHeight()/2, smooth((((frame*speed) % fl) + n*r)/r, 3)*r, paint);
		}
		
		c.translate(c.getWidth()/2 - walriiScaled.right/2, c.getHeight()/2 - walriiScaled.bottom/2);
		c.drawBitmap((int) ((System.currentTimeMillis() % 666) / 333) == 0 ? walrii_0 : walrii_1, walriiOrig, walriiScaled, paint);
	}
	
	public int mod(int x, int y)
	{
		while(x >= y) x -= y;
		while(x < 0) x += y;
		return x;
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
}
