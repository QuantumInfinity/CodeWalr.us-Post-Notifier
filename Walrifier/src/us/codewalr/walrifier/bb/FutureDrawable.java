package us.codewalr.walrifier.bb;

import java.io.InputStream;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.feed.WalrusAdapter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;

public class FutureDrawable extends BitmapDrawable implements FutureCallback<InputStream>
{
	BitmapDrawable image;
	WalrusAdapter adapter;
	Resources res;
	int post;
	View container;
	Paint alpha;
	
	public FutureDrawable(Resources res, WalrusAdapter adapter, int post, View container)
	{
		super(res, getPlaceholder(res));
		Bitmap image = BitmapFactory.decodeResource(res, R.drawable.omnomloading);
		int w = (int) getPlaceholderWidth(image, res);
		int h = (int) getPlaceholderHeight(image, res);
		setBounds(0, 0, w, h);
		image.recycle();
		this.res = res;
		this.adapter = adapter;
		this.post = post;
		this.container = container;
		this.alpha = new Paint();
		alpha.setAlpha(100);
	}
	
	private static Bitmap getPlaceholder(Resources res)
	{
		Bitmap image = BitmapFactory.decodeResource(res, R.drawable.omnomloading);
		float ratio = (float) image.getHeight() / (float) image.getWidth();
		float newWidth = Math.min(image.getWidth(), (int) res.getDimension(R.dimen.placeholder_width));
		float newHeight = newWidth * ratio;
		Bitmap imageScaled = Bitmap.createScaledBitmap(image, (int) newWidth, (int) newHeight, false);
		image.recycle();
		return imageScaled;
	}
	
	private static int getPlaceholderWidth(Bitmap image, Resources res)
	{
		return Math.min(image.getWidth(), (int) res.getDimension(R.dimen.placeholder_width));
	}
	
	private static int getPlaceholderHeight(Bitmap image, Resources res)
	{
		float ratio = (float) image.getHeight() / (float) image.getWidth();
		float newWidth = Math.min(image.getWidth(), (int) res.getDimension(R.dimen.placeholder_width));
		return (int) (newWidth * ratio);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if (image == null)
			super.draw(canvas);
		else
			canvas.drawBitmap(image.getBitmap(), 0, 0, null);
	}
	
	private BitmapDrawable fixSize(Bitmap image, int parentWidth)
	{
		float ratio = (float) image.getHeight() / (float) image.getWidth();
		float newWidth = Math.min(image.getWidth(), parentWidth);
		float newHeight = newWidth * ratio;
		Bitmap newImage = Bitmap.createScaledBitmap(image, (int) newWidth, (int) newHeight, true);

		return new BitmapDrawable(res, newImage);
	}
	
	@Override
	public void onCompleted(Exception e, InputStream result)
	{		
		Bitmap b = BitmapFactory.decodeStream(result);
		b.setDensity(Bitmap.DENSITY_NONE);
		image = fixSize(b, container.getWidth());
		updateBounds();
		adapter.notifyItemChanged(post);
	}

	private void updateBounds()
	{
		setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
		image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
	}
}
