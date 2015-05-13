package us.codewalr.walrifier.feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.RBuffer;
import us.codewalr.walrifier.Walrifier;
import android.os.AsyncTask;
import android.util.Log;

public class Feed extends AsyncTask<Void, Void, RBuffer<Post>>
{
	RBuffer<Post> lastPosts;
	int lastID;
	
	public Feed(int lastID)
	{
		this.lastID = lastID;
		lastPosts = new RBuffer<Post>(Walrifier.getContext().getResources().getInteger(R.integer.max_feed_length));
		lastPosts.fill(null);
	}

	@Override
	protected RBuffer<Post> doInBackground(Void... params)
	{
		String url = Walrifier.getContext().getString(R.string.forum_url)+"/index.php?action="+Walrifier.getContext().getString(R.string.forum_action)+"&id="+lastID;
		HttpURLConnection urlConnection = null;
		try{
			urlConnection = (HttpURLConnection) new URL(url).openConnection();
			BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null)
			    total.append(line);
			return parse(total.toString());
		} catch (IOException e)
		{
			Log.w("Walrifier", e.getCause());
			return null;
		}finally
		{
			urlConnection.disconnect();
		}
	}
	
	 @Override
	 protected void onPostExecute(RBuffer<Post> result)
	 {
		 super.onPostExecute(result);
	 }
	 
	 public RBuffer<Post> parse(String str)
	 {
		 Log.v("Walrifier", str);
		 
		 return lastPosts;
	 }
}
