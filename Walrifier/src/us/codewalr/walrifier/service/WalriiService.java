package us.codewalr.walrifier.service;
import java.util.*;
import us.codewalr.walrifier.*;
import us.codewalr.walrifier.feed.*;
import android.app.*;
import android.content.*;
import android.os.*;

public class WalriiService extends Service{
	public Feed feed;
	
	public int onStartCommand(Intent intent, int flags, int startID){
		feed = new Feed(-1);
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
