package us.codewalr.walrifier.bb;

import us.codewalr.walrifier.Walrifier;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;

public class BBImageGetter implements ImageGetter
{
	View view;
	Resources res;
	
	public BBImageGetter(View v, Resources res)
	{
		this.view = v;
		this.res = res;
	}
	
	@Override
	public Drawable getDrawable(String url)
	{
		URLDrawable image = new URLDrawable(Walrifier.resources());
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
			drawable.image = result;
			drawable.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());
			drawable.image.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());
			view.invalidate();
		}
	}
}