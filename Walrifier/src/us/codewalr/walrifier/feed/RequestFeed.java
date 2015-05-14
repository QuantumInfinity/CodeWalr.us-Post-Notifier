package us.codewalr.walrifier.feed;

/*
@Deprecated
public class RequestFeed extends AsyncTask<Integer, Void, ArrayList<Post>>
{
	@Override
	protected ArrayList<Post> doInBackground(Integer... params)
	{
		String url = Walrifier.getContext().getString(R.string.feed_url).replace("[LIM]", params[0]+"");
		HttpURLConnection urlConnection = null;
		try{
			urlConnection = (HttpURLConnection) new URL(url).openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			return parse(in);
		} catch (IOException | XmlPullParserException e)
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
        Walrifier.getInstance().refreshRecycler(result);
    }
    
	public ArrayList<Post> parse(InputStream in) throws XmlPullParserException, IOException
	{
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		}finally
		{
			in.close();
		}
	}

	private  ArrayList<Post> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		 ArrayList<Post> posts = new ArrayList<Post>();

		parser.require(XmlPullParser.START_TAG, null, "smf:xml-feed");
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			if (name.equals("recent-post"))
				posts.add(readRecentPost(parser));
			else
				skip(parser);
		}  
		return posts;
    }
	
	private Post readRecentPost(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		Post post = new Post(null, null, null, null, null);
		parser.require(XmlPullParser.START_TAG, null, "recent-post");
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			if (name.equals("subject"))
				post.title = stripCData(readTag(parser, "subject"));
			else if (name.equals("body"))
	        	post.content = stripCData(readTag(parser, "body"));
			else if (name.equals("poster"))
	        	post.user = stripCData(readPoster(parser));
			else if (name.equals("time"))
	        	post.time = readTag(parser, "time");
			else if (name.equals("link"))
				post.link = readTag(parser, "link");
			else
				skip(parser);
	    }
		post.type = post.title.startsWith("Re:") ? PostType.POST : PostType.TOPIC;
		return post;
	}
	
	private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, null, tag);
		String text = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, tag);
		return text;
	}
	
	private String readPoster(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		String text = "";
		parser.require(XmlPullParser.START_TAG, null, "poster");
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			if (name.equals("name"))
				text = readTag(parser, "name");
			else
				skip(parser);
		}
		return text;
	}
	
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		String result = "";
		if (parser.next() == XmlPullParser.TEXT)
		{
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		if (parser.getEventType() != XmlPullParser.START_TAG)
			throw new IllegalStateException();
		int depth = 1;
	    while (depth != 0)
	    {
	    	switch (parser.next())
	        {
	        case XmlPullParser.END_TAG:
	        	depth--;
	        	break;
	        case XmlPullParser.START_TAG:
	        	depth++;
	        	break;
	        }
	    }
	}
	
	private String stripCData(String data)
	{
		if (!(data.startsWith("<!CDATA[") && data.endsWith("]]>")))
			return data;
		data = data.substring(7, -3);
		return data.trim();
	}
}
*/