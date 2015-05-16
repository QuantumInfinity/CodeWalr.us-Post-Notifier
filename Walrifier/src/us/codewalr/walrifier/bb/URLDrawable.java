package us.codewalr.walrifier.bb;

import us.codewalr.walrifier.R;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class URLDrawable extends BitmapDrawable
{
	public URLDrawable(Resources res, String url)
	{
		super(res, BitmapFactory.decodeResource(res, R.drawable.walrii_0));
		setBounds(0, 0, getIntrinsicWidth(), getIntrinsicHeight());
	}

	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);
	}
}
