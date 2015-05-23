package us.codewalr.walrifier.bb;

import us.codewalr.walrifier.feed.WalrusAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.view.View;

import com.koushikdutta.ion.Ion;

public class BBImageGetter implements ImageGetter
{
	private WalrusAdapter adapter;
	private Context ctx;
	private int post;
	private View container;
	
	public BBImageGetter(Context ctx, WalrusAdapter adapter, int post, View container)
	{
		this.adapter = adapter;
		this.ctx = ctx;
		this.post = post;
		this.container = container;
	}
	
	@Override
	public Drawable getDrawable(String url)
	{
		FutureDrawable image = new FutureDrawable(ctx.getResources(), adapter, post, container);
		Ion.with(ctx).load(url).asInputStream().setCallback(image);
		return image;
	}
}