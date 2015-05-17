package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.bb.BBParser;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WalrusAdapter extends RecyclerView.Adapter<WalrusAdapter.ViewHolder>
{
	private ArrayList<Post> posts;
	private BBParser bbParser;
	
	public WalrusAdapter(Resources res, ArrayList<Post> posts)
	{
		this.posts = posts;
		bbParser = new BBParser(res, this);
	}
	
	public void setPosts(ArrayList<Post> posts)
	{
		this.posts = posts;
	}
	
	@Override
	public WalrusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.walrifiercard, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		holder.set(posts.get(position), position);
	}

	@Override
	public int getItemCount()
	{
    	return posts.size();
    }
    
	public class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView title, time, poster;
		PostContentView content;
		
		public ViewHolder(View v)
		{
			super(v);
			title = (TextView) v.findViewById(R.id.title);
			time = (TextView) v.findViewById(R.id.time);
			content = (PostContentView) v.findViewById(R.id.content);
			poster = (TextView) v.findViewById(R.id.poster);
		}
		
		public void set(Post p, int post)
		{
			title.setText(p.subject);
			time.setText(p.getTime());
			content.setText(p.getContent(bbParser, post));
			content.setMovementMethod(LinkMovementMethod.getInstance());
			poster.setText(p.getPoster());
		}
	}
}
