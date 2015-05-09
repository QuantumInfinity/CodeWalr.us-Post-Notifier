package us.codewalr.walrifier;

import java.util.ArrayList;

import us.codewalr.walrifier.Post.PostType;
import us.codewalr.walrifier.feed.RequestFeed;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Walrifier extends Activity
{
	static Walrifier instance;
	
	private RequestFeed feed;
	private RecyclerView recycler;
	private WalrusAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.walrifierlayout);
		recycler = (RecyclerView) findViewById(R.id.recycler);

		recycler.setHasFixedSize(true);

		layoutManager = new LinearLayoutManager(this);
		recycler.setLayoutManager(layoutManager);

		adapter = new WalrusAdapter(new ArrayList<Post>());
		recycler.setAdapter(adapter);
		
		feed = new RequestFeed();
		
		updateFeed();
	}
	
	public void refreshRecycler(ArrayList<Post> posts)
	{
		adapter.setPosts(posts);
		adapter.notifyDataSetChanged();
	}
	
	public void updateFeed()
	{
		feed.execute(10);
	}
	
	public static Walrifier getInstance()
	{
		return instance;
	}
	
	public static Context getContext()
	{
		return instance;
	}
	
	public static ArrayList<Post> getTempPostList()
	{
		ArrayList<Post> posts = new ArrayList<Post>();
		for (int i=0; i<10; i++)
			posts.add(new Post("Test post " + i, "Test_User_"+i, "Today at 66:69", "This is test text #"+i, Math.random() > 0.5 ? PostType.POST : PostType.TOPIC));
		
		return posts;
	}
}
