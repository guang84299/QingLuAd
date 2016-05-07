package com.qinglu.ad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qinglu.QLCommon;
import com.qinglu.QLSize;
import com.qinglu.ad.listener.QLSpotDialogListener;
import com.qinglu.ad.tools.QLNetTools;
import com.qinglu.tools.QLTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class QLSpotView extends RelativeLayout{
	private int id;
	private ImageView close;
	private Bitmap viewBm;
	private Bitmap closeBm;
	private QLSpotDialogListener dialogListener;
	private QLSize size;
	private int type;//0���������Ͳ��� 1������һ������
	
	public QLSpotView(Context context) {
		super(context);
	}
	
	public QLSpotView(Context context,int animationType) {
		super(context);
		this.type = 0;
		this.init(context, animationType);
	}
	
	public QLSpotView(Context context,int animationType,int type) {
		super(context);
		this.type = type;
		this.init(context, animationType);
	}
	
	

	public QLSize getSize() {
		return size;
	}

	public void setSize(QLSize size) {
		this.size = size;
	}

	public QLSpotDialogListener getDialogListener() {
		return dialogListener;
	}

	public void setDialogListener(QLSpotDialogListener dialogListener) {
		this.dialogListener = dialogListener;
	}
	
	private void init(final Context context,int animationType)
	{
		SharedPreferences mySharedPreferences= context.getSharedPreferences(QLCommon.SHARED_PRE, 
				Activity.MODE_PRIVATE); 	
		try {
			JSONObject obj = null;
			if(this.type == 0)
			{
				JSONArray arr = new JSONArray(mySharedPreferences.getString(QLCommon.SHARED_KEY_SPOT, ""));
				obj = arr.getJSONObject((int)(Math.random()*10%arr.length()));
			}
			else
			{
				obj = new JSONObject(mySharedPreferences.getString(QLCommon.SHARED_KEY_SPOT_BYID, ""));
			}
			getSpotView(context,animationType,obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}

	@SuppressLint("NewApi")
	private void getSpotView(final Context context,int animationType,final JSONObject obj)
	{
		ImageView view = new ImageView(context);
		try {			
			viewBm = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+ obj.getString("picPath")) ;
			view.setImageBitmap(viewBm);
			//�ײ�����			
			final QLSpotView layout = this;
			
			//����
			LinearLayout.LayoutParams layoutGrayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			layoutGrayParams.gravity = Gravity.CENTER;
			
			LinearLayout layoutGray = new LinearLayout(context);
			layoutGray.setBackgroundColor(Color.BLACK);
			layoutGray.setAlpha(0.6f);
			layoutGray.setLayoutParams(layoutGrayParams);
			layout.addView(layoutGray);	
			//���
			QLSize ss = QLTools.getScreenSize(context);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			// ���ù����������λ��
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			float width = viewBm.getWidth();
			float height = viewBm.getHeight();
			layoutParams.width = (int) (ss.width*0.9);
			layoutParams.height = (int) (ss.width*0.9/width*height);
			this.setSize(new QLSize(layoutParams.width, layoutParams.height));
			view.setId(1);
			view.setScaleType(ScaleType.FIT_XY);

			layout.addView(view, layoutParams);		
			
			//�رհ�ť		
			RelativeLayout.LayoutParams paramsClose = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			close = new ImageView(context);
			closeBm = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/images/close.jpg");
			close.setImageBitmap(closeBm);
			close.setVisibility(View.GONE);
						
			paramsClose.width = (int) (ss.width*0.1);
			paramsClose.height = (int) (ss.width*0.1);
			paramsClose.addRule(RelativeLayout.ALIGN_TOP, 1);
			paramsClose.addRule(RelativeLayout.ALIGN_RIGHT, 1);
			
			layout.addView(close,paramsClose);
			
			//���ö���
			if(animationType == QLCommon.ANIM_SIMPLE)
			{
				AnimationSet animaSet = new AnimationSet(true);
				AlphaAnimation anima = new AlphaAnimation((float) 0.5, 1);
				anima.setDuration(500);
				animaSet.addAnimation(anima);
				animaSet.setAnimationListener(new QLAnimationListener());
				view.startAnimation(animaSet);
			}
			else if(animationType == QLCommon.ANIM_ADVANCE)
			{
				AnimationSet animaSet = new AnimationSet(true);
																
				ScaleAnimation sca1 = new ScaleAnimation(-0.8f, 1.f, 1.f, 1.f, layoutParams.width/2, layoutParams.height/2);
				sca1.setDuration(600);
				ScaleAnimation sca2 = new ScaleAnimation(1.2f, 1.f, 1.f, 1.f, layoutParams.width/2, layoutParams.height/2);
				sca2.setDuration(400);
							
				animaSet.addAnimation(sca1);
				animaSet.addAnimation(sca2);
				animaSet.setAnimationListener(new QLAnimationListener());
				view.startAnimation(animaSet);
			}
			else
			{
				close.setVisibility(View.VISIBLE);
			}
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					layout.removeAllViews();
					ViewGroup parent = ( ViewGroup )layout.getParent();
					parent.removeView(layout);
					Toast.makeText(context, "��ʼΪ������Ӧ��...", 0).show();
					try {
						if(type == 1)
						{				
							QLNetTools.download(context, QLCommon.SERVER_ADDRESS + obj.getString("downloadPath"),2,2);
							//�ϴ�ͳ����Ϣ
							String pushId = QLTools.getSharedPreferences(context).getString(QLCommon.SHARED_KEY_PUSHSPOT_BYID, "").split("&&&&&")[0];
							QLNetTools.uploadPushStatistics(2, pushId);
						}
						else
						{
							QLNetTools.download(context, QLCommon.SERVER_ADDRESS + obj.getString("downloadPath"),1,0);
							//�ϴ�ͳ����Ϣ
							QLNetTools.uploadStatistics(2, obj.getLong("id"));
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(viewBm != null && !viewBm.isRecycled())
					{
						viewBm.recycle();
						viewBm = null;
					}
					if(closeBm != null && !closeBm.isRecycled())
					{
						closeBm.recycle();
						closeBm = null;
					}
					System.gc();
					if(dialogListener != null)
					{
						dialogListener.onSpotClosed();
					}
				}
			});
			
			close.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					layout.removeAllViews();
					ViewGroup parent = ( ViewGroup )layout.getParent();
					parent.removeView(layout);
					
					if(viewBm != null && !viewBm.isRecycled())
					{
						viewBm.recycle();
						viewBm = null;
					}
					if(closeBm != null && !closeBm.isRecycled())
					{
						closeBm.recycle();
						closeBm = null;
					}
					System.gc();
					
					if(dialogListener != null)
					{
						dialogListener.onSpotClosed();
					}
				}
			});
			//�ϴ�ͳ����Ϣ
			if(this.type == 1)
			{				
				String pushId = QLTools.getSharedPreferences(context).getString(QLCommon.SHARED_KEY_PUSHSPOT_BYID, "").split("&&&&&")[0];
				QLNetTools.uploadPushStatistics(1, pushId);
			}
			else
			{
				QLNetTools.uploadStatistics(1, obj.getLong("id"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	class QLAnimationListener implements AnimationListener
	{

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if(close != null)
			{
				close.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
