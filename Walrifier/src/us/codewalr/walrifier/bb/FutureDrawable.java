package us.codewalr.walrifier.bb;

import java.io.InputStream;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import us.codewalr.walrifier.feed.WalrusAdapter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import com.koushikdutta.async.future.FutureCallback;

public class FutureDrawable extends BitmapDrawable implements FutureCallback<InputStream>
{
	BitmapDrawable image;
	WalrusAdapter adapter;
	Resources res;
	int post;
	
	public FutureDrawable(Resources res, WalrusAdapter adapter, int post)
	{
		super(res, getPlaceholder(res));
		int w = (int) res.getDimension(R.dimen.placeholder_width);
		int h = (int) res.getDimension(R.dimen.placeholder_height);
		setBounds(0, 0, w, h);
		this.res = res;
		this.adapter = adapter;
		this.post = post;
	}
	
	private static Bitmap getPlaceholder(Resources res)
	{
		Bitmap image = BitmapFactory.decodeResource(res, R.drawable.walrii_0);
		float ratio = (float) image.getHeight() / (float) image.getWidth();
		float newWidth = Math.min(image.getWidth(), (int) res.getDimension(R.dimen.placeholder_width));
		float newHeight = newWidth * ratio;
		return Bitmap.createScaledBitmap(image, (int) newWidth, (int) newHeight, false);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if (image == null)
			super.draw(canvas);
		else
			image.draw(canvas);
	}
	
	private BitmapDrawable fixSize(Bitmap image, int parentWidth)
	{
		float ratio = (float) image.getHeight() / (float) image.getWidth();
		float newWidth = Math.min(image.getWidth(), parentWidth);
		float newHeight = newWidth * ratio;
		Bitmap newImage = Bitmap.createScaledBitmap(image, (int) newWidth, (int) newHeight, false);
		return new BitmapDrawable(res, newImage);
	}
	
	@Override
	public void onCompleted(Exception e, InputStream result)
	{	
		int width = Walrifier.getInstance().findViewById(R.id.walrifiercard).getWidth();
		
		Bitmap b = BitmapFactory.decodeStream(result);
		b.setDensity(Bitmap.DENSITY_NONE);
		image = fixSize(b, width);
		updateBounds();
		adapter.notifyItemChanged(post);
	}

	private void updateBounds()
	{
		setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
		image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
	}
}
