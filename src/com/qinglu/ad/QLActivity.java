package com.qinglu.ad;

import org.androidpn.client.Constants;
import org.androidpn.client.NotificationService;

import com.qinglu.QLAdController;
import com.qinglu.QLCommon;
import com.qinglu.ad.listener.QLSpotDialogListener;
import com.qinglu.ad.tools.QLNetTools;
import com.qinglu.tools.QLTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class QLActivity extends Activity{

	private Context context;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		context = this;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		LinearLayout.LayoutParams layoutGrayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		layoutGrayParams.gravity = Gravity.CENTER;		
		LinearLayout layoutGray = new LinearLayout(this);
		layoutGray.setLayoutParams(layoutGrayParams);
		
		this.setContentView(layoutGray);
		
		Intent intent = getIntent();
		String type = intent.getStringExtra(QLCommon.INTENT_TYPE);
		if(QLCommon.INTENT_PUSH_DOWNLOAD.equals(type))
		{
			pushDownload();
		}
		else if(QLCommon.INTENT_SPOT.equals(type))
		{
			spot();
		}				
	}
	
	private void spot()
	{
		QLSpotView view = new QLSpotView(this,2,1);
		view.setDialogListener(new MySpotDialogListener());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		//params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);
		this.addContentView(view, params);
	}
	
	private void pushDownload()
	{
		Intent intent = getIntent();
        String notificationUri = intent
                .getStringExtra(Constants.NOTIFICATION_URI);
        
		Context context = NotificationService.getInstanceService();
        
        Toast.makeText(context, "��ʼΪ������Ӧ��...", 0).show();
		try
		{
			QLNetTools.download(context, QLCommon.SERVER_ADDRESS + notificationUri,2,1);
			//�ϴ�ͳ����Ϣ
			String pushId = QLTools.getSharedPreferences(context).getString(QLCommon.SHARED_KEY_PUSHMESSAGE_BYID, "").split("&&&&&")[0];
			QLNetTools.uploadPushStatistics(2, pushId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.finish();
	}
	
	class MySpotDialogListener implements QLSpotDialogListener
	{

		@Override
		public void onShowSuccess() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onShowFailed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSpotClosed() {
			Activity act = (Activity) context;
			act.finish();
		}

		@Override
		public void onSpotClick(boolean isWebPath) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
