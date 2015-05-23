package us.codewalr.walrifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import us.codewalr.walrifier.bb.BBParser;
import android.content.res.Resources;
import android.text.Spanned;
import android.view.View;

public class Post
{
	public String subject, body, user, link = null;
	public PostType type;
	public int postID, userID, topicID;
	public Date time;
	public Spanned content = null;
	
	public Post(int id_msg, int id_topic, long poster_time, String poster_name, int id_member, String subject, String body)
	{
		this.subject = subject;
		this.topicID = id_topic;
		this.body = body;
		this.user = poster_name;
		this.postID = id_msg;
		this.time = new Date(poster_time * 1000L);
		this.userID = id_member;
		this.type = subject.startsWith("Re:") ? PostType.POST : PostType.TOPIC;
	}
	
	public String getPoster()
	{
		return type.text + " by " + user;
	}
	
	public String getTime()
	{
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(time);
	    Calendar today = Calendar.getInstance();
	    Calendar yesterday = Calendar.getInstance();
	    yesterday.add(Calendar.DATE, -1);
	    DateFormat tf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	    tf.setTimeZone(TimeZone.getDefault());
	    
	    if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR))
	        return "Today at " + tf.format(time);
	    else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR))
	        return "Yesterday at " + tf.format(time);
	    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
	    df.setTimeZone(TimeZone.getDefault());
	    return df.format(time) + " " + tf.format(time);
	}
	
	public Spanned getContent(BBParser bbParser, int post, View container)
	{
		if (content == null)
			content = bbParser.parse(body, post, container);
		return content;
	}
	
	public String getLink(Resources res)
	{
		if (link == null)
			link = res.getString(R.string.forum_url)+res.getString(R.string.forum_msg_link_format).replace("%TOPIC%", topicID + "").replace("%POST%", postID + "");
		return link;
	}
	
	public static enum PostType
	{
		POST("Post"), TOPIC("Topic");
		
		String text;
		
		PostType(String text)
		{
			this.text = text;
		}
	}
}
