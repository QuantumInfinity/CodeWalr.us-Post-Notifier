package us.codewalr.walrifier;

public class Post
{
	public String title, time, content, user, link;
	public PostType type;
	
	public Post(String title, String user, String time, String content, PostType type)
	{
		this.title = title;
		this.time = time;
		this.content = content;
		this.user = user;
		this.type = type;
		this.link = null;
	}
	
	public Post(String title, String user, String time, String content, PostType type, String link)
	{
		this.title = title;
		this.time = time;
		this.content = content;
		this.user = user;
		this.type = type;
		this.link = link;
	}
	
	public String getPoster()
	{
		return type.text + " by " + user;
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
