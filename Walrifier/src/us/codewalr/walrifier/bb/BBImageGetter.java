package us.codewalr.walrifier.bb;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import us.codewalr.walrifier.feed.WalrusAdapter;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;

import com.nostra13.universalimageloader.core.ImageLoader;

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
		URLDrawable image = new URLDrawable(res);
		BBImageGetterTask task = new BBImageGetterTask(image);
		task.execute(url);
		return image;
	}
	
	public class BBImageGetterTask extends AsyncTask<String, Void, BitmapDrawable>
	{
		URLDrawable drawable;
		
		public BBImageGetterTask(URLDrawable d)
		{
			this.drawable = d;
		}

		@Override
		protected BitmapDrawable doInBackground(String... params)
		{
			return new BitmapDrawable(res, ImageLoader.getInstance().loadImageSync(params[0]));
		}
		
		@Override
		protected void onPostExecute(BitmapDrawable result)
		{
			super.onPostExecute(result);
			int width = Walrifier.getInstance().findViewById(R.id.walrifiercard).getWidth();
			drawable.update(result, width);
			adapter.notifyItemChanged(post);
		}
	}
}