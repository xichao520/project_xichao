package com.example.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.test.MyService.MyBinder;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button startService;  
	private Button stopService; 
	private Button bindService;  
    private Button unbindService; 
    
    private int progress = 0;  
    private ProgressBar mProgressBar; 
    
    private MyBinder myBinder;
    private MyService myService;
    private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			myService = ((MyService.MyBinder) service).getService();  
			myService.startDownLoad();  
			
			listenProgress();
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		} 
    	
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService = (Button) findViewById(R.id.start_service);  
        stopService = (Button) findViewById(R.id.stop_service);  
        bindService = (Button) findViewById(R.id.bind_service);  
        unbindService = (Button) findViewById(R.id.unbind_service);  
        startService.setOnClickListener(this);  
        stopService.setOnClickListener(this);  
        bindService.setOnClickListener(this);  
        unbindService.setOnClickListener(this); 
        
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        
        Button secondActivity = (Button) findViewById(R.id.second_activity);
        secondActivity.setOnClickListener(this);
	}

	@Override  
    public void onClick(View v) {  
        switch (v.getId()) {  
        case R.id.start_service:  
            Intent startIntent = new Intent(this, MyService.class);  
            startService(startIntent);  
            break;  
        case R.id.stop_service:  
            Intent stopIntent = new Intent(this, MyService.class);  
            stopService(stopIntent);  
            break;  
        case R.id.bind_service:  
            Intent bindIntent = new Intent(this, MyService.class);  
            bindService(bindIntent, connection, BIND_AUTO_CREATE);  
            break;  
        case R.id.unbind_service:  
            unbindService(connection);  
            break;
        case R.id.second_activity:
        	Intent intent = new Intent(this, SecondActivity.class);
        	startActivity(intent);
        	break;
        }  
    }  
	
	/** 
     * 监听进度，每秒钟获取调用MsgService的getProgress()方法来获取进度，更新UI 
     */  
    public void listenProgress(){  
        new Thread(new Runnable() {  
              
            @Override  
            public void run() {  
                while(progress < MyService.MAX_PROGRESS){  
                    progress = myService.getProgress();  
                    mProgressBar.setProgress(progress);  
                    try {  
                        Thread.sleep(1000);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                  
            }  
        }).start();  
    }  

}