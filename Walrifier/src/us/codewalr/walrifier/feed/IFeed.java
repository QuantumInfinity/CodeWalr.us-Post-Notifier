package us.codewalr.walrifier.feed;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;

public interface IFeed
{
	 public void onFeedLoaded(ArrayList<Post> posts, boolean hasNewPosts, boolean failed);
}