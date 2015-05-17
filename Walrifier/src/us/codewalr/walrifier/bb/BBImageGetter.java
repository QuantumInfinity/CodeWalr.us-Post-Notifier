package us.codewalr.walrifier.bb;

import us.codewalr.walrifier.Walrifier;
import us.codewalr.walrifier.feed.WalrusAdapter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;

import com.koushikdutta.ion.Ion;

public class BBImageGetter implements ImageGetter
{
	private WalrusAdapter adapter;
	private Resources res;
	private int post;
	
	public BBImageGetter(Resources res, WalrusAdapter adapter, int post)
	{
		this.adapter = adapter;
		this.res = res;
		this.post = post;
	}
	
	@Override
	public Drawable getDrawable(String url)
	{
		FutureDrawable image = new FutureDrawable(res, adapter, post);
		Ion.with(Walrifier.getContext()).load(url).asInputStream().setCallback(image);
		return image;
	}
}