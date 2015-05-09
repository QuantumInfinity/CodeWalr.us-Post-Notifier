package us.codewalr.walrifier;

public class Post
{
	String title, time, content, user;
	PostType type;
	
	public Post(String title, String user, String time, String content, PostType type)
	{
		this.title = title;
		this.time = time;
		this.content = content;
		this.user = user;
		this.type = type;
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
