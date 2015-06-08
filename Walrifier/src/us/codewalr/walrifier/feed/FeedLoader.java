package us.codewalr.walrifier.feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class FeedLoader extends AsyncTask<Boolean, Void, ArrayList<Post>>
{
	Context ctx;
	IFeed onFinished;
	int since;
	String fail = null;
	
	public FeedLoader(Context ctx, IFeed onFinished, int since)
	{
		this.onFinished = onFinished;
		this.since = since;
		this.ctx = ctx;
	}
	
	@Override
	protected ArrayList<Post> doInBackground(Boolean... useData)
	{
		ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Activity.CONNECTIVITY_SERVICE);
	    NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    NetworkInfo mobileInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		
	    if (wifiInfo.isConnected() == false && mobileInfo.isConnected() == false)
	    {
	    	fail = "No WiFi/Mobile data connection";
	    	return null;
	    }
	    else if (wifiInfo.isConnected() == false && mobileInfo.isConnected() == true && !useData[0])
	    {
	    	fail = "No WiFi connection";
	    	return null;
	    }
		
		String url = ctx.getResources().getString(R.string.forum_url)+ctx.getResources().getString(R.string.forum_walrifier_action).replace("%ID%", since + "");
		HttpURLConnection urlConnection = null;
		try{
			urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setConnectTimeout(5000);
			BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null)
			    total.append(line);
			return parse(total.toString());
		} catch (IOException e)
		{
			fail = e.getLocalizedMessage();
			return null;
		}finally
		{
			urlConnection.disconnect();
		}
	}
	
	@Override
	protected void onPostExecute(ArrayList<Post> result)
	{
		super.onPostExecute(result);
		onFinished.onFeedLoaded(result, result != null && result.size() != 0, result == null, fail == null ? "Unknown error" : fail);
	}
	
	public ArrayList<Post> parse(String str)
	{
		ArrayList<Post> ret = new ArrayList<Post>();
		if (!str.equals(""))
		{
			ArrayList<String> rawPosts = splitPosts(str);
			for (int i = 0; i < rawPosts.size(); i++)
				ret.add(parseRawPost(rawPosts.get(i)));
		}
		return ret;
	}
	
	public static Post parseRawPost(String str)
	{
		String[] elem = str.replaceAll("(^\\{)|(\\}$)", "").split("(?<!%):");
		for (int i=0; i<elem.length; i++)
			elem[i] = elem[i].replaceAll("%\\{", "\\{").replaceAll("%\\}", "}").replaceAll("%:", ":").replaceAll("%%", "%");
		return new Post(Integer.parseInt(elem[0]), Integer.parseInt(elem[1]), Long.parseLong(elem[2]), elem[3], Integer.parseInt(elem[4]), elem[5], elem[6]);
	}
	
	public static ArrayList<String> splitPosts(String str)
	{
		ArrayList<String> posts = new ArrayList<String>();
		Pattern p = Pattern.compile("^((?<!%)\\{(.*?)(?<!%)\\})");
		Matcher m;
		while (!str.equals(""))
		{
			m = p.matcher(str);
			if (m.find())
			{	
				posts.add(m.group().trim());
				str = m.replaceFirst("").trim();
			}
		}
		return posts;
	}
}
