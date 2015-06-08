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
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class WalriiService extends Service{
	public Feed feed = null;
	public Handler handler;
	public Long delaytime = null;
	public Runnable updateChecker;
	public Boolean useData = null;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		handler = new Handler();
		
		updateChecker = new Runnable(){
			public void run(){
				feed.load(new IFeed(){
					@Override
					public void onFeedLoaded(ArrayList<Post> newPosts, boolean hasNewPosts, boolean failed, String reason){
						if(!failed && hasNewPosts){
							feed.push(newPosts);
							NotificationCompat.Builder notification = new NotificationCompat.Builder(WalriiService.this);
							notification.setSmallIcon(R.drawable.walrii_0);
							notification.setContentTitle("Walrifier");
							notification.setContentText(newPosts.get(0).subject);
							notification.setAutoCancel(true);
							notification.setTicker("Walrified post: " + newPosts.get(0).subject);
							notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
							Intent intent = new Intent(WalriiService.this, Walrifier.class);
							
							TaskStackBuilder stackBuilder = TaskStackBuilder.create(WalriiService.this);
							stackBuilder.addParentStack(Walrifier.class);
							stackBuilder.addNextIntent(intent);
							PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
							notification.setContentIntent(pIntent);
							
							NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
							notificationManager.notify(1337, notification.build());
						}
					}
				}, useData);
				
				handler.postDelayed(this, delaytime);
			}
		};
	}
	
	public int onStartCommand(Intent intent, int flags, int startID){		
		if (intent == null && useData != null && delaytime != null)
			return START_STICKY;
		
		delaytime = (intent == null ? 60 : intent.getIntExtra("service_check_time", 60)) * 1000L;
		useData = intent == null ? false : intent.getBooleanExtra("use_data", false);
		
		if (feed == null)
		{
			feed = new Feed(-1, this);
			feed.load(new IFeed(){
				public void onFeedLoaded(ArrayList<Post> newPosts, boolean hasNewPosts, boolean failed, String reason){
					feed.push(newPosts);
				}
			}, useData);
		}
		
		handler.removeCallbacks(updateChecker);
		handler.postDelayed(updateChecker, 1000);
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy()
	{
		handler.removeCallbacks(updateChecker);
	}
	
	public IBinder onBind(Intent intent){
		return null;
	}
}
