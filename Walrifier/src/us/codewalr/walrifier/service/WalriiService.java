package us.codewalr.walrifier.service;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.feed.Feed;
import us.codewalr.walrifier.feed.IFeed;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WalriiService extends Service{
	public Feed feed;
	
	public int onStartCommand(Intent intent, int flags, int startID){
		feed = new Feed(-1, getResources());
		feed.load(new IFeed(){
			@Override
			public void onFeedLoaded(ArrayList<Post> newPosts, boolean hasNewPosts, boolean failed){
				if(!failed && hasNewPosts){
					feed.push(newPosts);
				}
			}
		});
		return START_STICKY;
	}
	
	public IBinder onBind(Intent intent){
		return null;
	}
}
