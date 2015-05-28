package us.codewalr.walrifier.service;

import java.util.ArrayList;

import us.codewalr.walrifier.Post;
import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import us.codewalr.walrifier.feed.Feed;
import us.codewalr.walrifier.feed.IFeed;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class WalriiService extends Service{
	public WalriiService instance;
	public Feed feed;
	public Handler handler;
	
	public int onStartCommand(Intent intent, int flags, int startID){
		instance = this;
		handler = new Handler();
		feed = new Feed(-1, getResources());
		feed.load(new IFeed(){
			public void onFeedLoaded(ArrayList<Post> newPosts, boolean hasNewPosts, boolean failed){
				feed.push(newPosts);
			}
		});
		
		Runnable runnable = new Runnable(){
			public void run(){
				feed.load(new IFeed(){
					@Override
					public void onFeedLoaded(ArrayList<Post> newPosts, boolean hasNewPosts, boolean failed){
						if(!failed && hasNewPosts){
							feed.push(newPosts);
							NotificationCompat.Builder notification = new NotificationCompat.Builder(instance);
							notification.setSmallIcon(R.drawable.walrii_0);
							notification.setContentTitle("Walrifier");
							notification.setContentText(newPosts.get(0).subject);
							Intent intent = new Intent(instance, Walrifier.class);
							
							TaskStackBuilder stackBuilder = TaskStackBuilder.create(instance);
							stackBuilder.addParentStack(Walrifier.class);
							stackBuilder.addNextIntent(intent);
							PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
							notification.setContentIntent(pIntent);
							
							NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
							notificationManager.notify(1337, notification.build());
						}
					}
				});
				
				handler.postDelayed(this, 5000);
			}
		};
		
		handler.post(runnable);
		
		return START_STICKY;
	}
	
	public IBinder onBind(Intent intent){
		return null;
	}
}
