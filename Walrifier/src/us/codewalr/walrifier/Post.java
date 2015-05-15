package us.codewalr.walrifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.text.Spanned;

public class Post
{
	public String subject, body, user;
	public PostType type;
	public int postID, userID;
	public Date time;
	public Spanned content = null;
	
	public Post(int id_msg, long poster_time, String poster_name, int id_member, String subject, String body)
	{
		this.subject = subject;
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
	
	public Spanned getContent()
	{
		if (content == null)
			content = Walrifier.parseBB(body);
		return content;
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
