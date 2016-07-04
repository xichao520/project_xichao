package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{
	
    public static final int MAX_PROGRESS = 100;  
    private int progress = 0;
	
	private String Tag = "MyService";
	
	private MyBinder myBinder = new MyBinder();

	@Override
	public IBinder onBind(Intent arg0) {
		return myBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d(Tag, "onCreate executed");
		
		//开启一个前台service，不会因为内存不足而被销毁
//		Notification notification = new Notification(R.drawable.ic_launcher,  
//                "有通知到来", System.currentTimeMillis());  
//        Intent notificationIntent = new Intent(this, MainActivity.class);  
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,  
//                notificationIntent, 0);  
//        notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",  
//                pendingIntent);  
//        startForeground(1, notification);  
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(Tag, "onStartCommand executed");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(Tag, "onDestroy executed");
	}
	
	public int getProgress() {
		return progress;
	}

	/** 
     * 模拟下载任务，每秒钟更新一次 
     */  
    public void startDownLoad(){  
        new Thread(new Runnable() {  
              
            @Override  
            public void run() {  
                while(progress < MAX_PROGRESS){  
                    progress += 5;  
                    try {  
                        Thread.sleep(1000);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                      
                }  
            }  
        }).start();  
    }  
	
	class MyBinder extends Binder{
		
		public MyService getService() {
			
			return MyService.this;
		}
	}
}
