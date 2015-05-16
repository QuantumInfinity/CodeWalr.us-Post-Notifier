package us.codewalr.walrifier.bb;

import us.codewalr.walrifier.R;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class URLDrawable extends BitmapDrawable
{
	BitmapDrawable image;
	
	public URLDrawable(Resources res)
	{
		super(res, BitmapFactory.decodeResource(res, R.drawable.walrii_0)); // placeholder
		int w = (int) res.getDimension(R.dimen.placeholder_width);
		int h = (int) res.getDimension(R.dimen.placeholder_height);
		setBounds(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas)
	{
		if (image == null)
			super.draw(canvas);
		else
		{
			image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
			image.draw(canvas);
		}
	}
}
