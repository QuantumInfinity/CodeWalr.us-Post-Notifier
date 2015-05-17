package us.codewalr.walrifier.bb;

import us.codewalr.walrifier.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class URLDrawable extends BitmapDrawable
{
	BitmapDrawable image;
	Resources res;
	
	public URLDrawable(Resources res)
	{
		super(res, BitmapFactory.decodeResource(res, R.drawable.walrii_0)); // placeholder
		int w = (int) res.getDimension(R.dimen.placeholder_width);
		int h = (int) res.getDimension(R.dimen.placeholder_height);
		setBounds(0, 0, w, h);
		this.res = res;
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (image == null)
			super.draw(canvas);
		else
			image.draw(canvas);
	}
	
	private void fixSize(int parentWidth)
	{
		float ratio = (float) image.getIntrinsicHeight() / (float) image.getIntrinsicWidth();
		float newWidth = Math.min(image.getIntrinsicWidth(), parentWidth);
		float newHeight = newWidth * ratio;
		Bitmap newImage = Bitmap.createScaledBitmap(image.getBitmap(), (int) newWidth, (int) newHeight, false);
		image = new BitmapDrawable(res, newImage);
		updateBounds();
	}
	
	public void update(BitmapDrawable newImage, int width)
	{
		this.image = newImage;
		if (image.getIntrinsicWidth() > width)
			fixSize(width);
		updateBounds();
	}
	
	private void updateBounds()
	{
		setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
		image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
	}
}