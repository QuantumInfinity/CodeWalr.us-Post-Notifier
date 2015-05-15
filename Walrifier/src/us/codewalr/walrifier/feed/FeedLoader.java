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
import us.codewalr.walrifier.Walrifier;
import android.os.AsyncTask;
import android.util.Log;

public class FeedLoader extends AsyncTask<Void, Void, ArrayList<Post>>
{
	IFeed onFinished;
	int since;
	
	public FeedLoader(IFeed onFinished, int since)
	{
		this.onFinished = onFinished;
		this.since = since;
	}
	
	@Override
	protected ArrayList<Post> doInBackground(Void... params)
	{
		String url = Walrifier.getContext().getString(R.string.forum_url)+"/index.php?action="+Walrifier.getContext().getString(R.string.forum_action)+"&id="+since;
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
	protected void onPostExecute(ArrayList<Post> result)
	{
		super.onPostExecute(result);
		onFinished.onFeedLoaded(Walrifier.getFeed().push(result), result.size() == 0, result == null);
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
		return new Post(Integer.parseInt(elem[0]), Integer.parseInt(elem[1]), elem[2], Integer.parseInt(elem[3]), elem[4], elem[5]);
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
