package com.qinglu.ad.impl.qinglu;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RelativeLayout;

import com.qinglu.QLCommon;
import com.qinglu.ad.QLActivity;
import com.qinglu.ad.QLSpotManager;
import com.qinglu.ad.QLSpotView;
import com.qinglu.ad.listener.QLSpotDialogListener;
import com.qinglu.ad.tools.QLNetTools;
import com.qinglu.tools.QLTools;

public class QLSpotManagerQingLu implements QLSpotManager{
	private Context context;
	private int animationType;
	
	public QLSpotManagerQingLu(Context context)
	{
		this.context = context;
		
		QLCommon.ORIENTATION_PORTRAIT = 0;
		QLCommon.ORIENTATION_LANDSCAPE = 1;
		
		QLCommon.ANIM_NONE = 0;
		QLCommon.ANIM_SIMPLE = 1;
		QLCommon.ANIM_ADVANCE = 2;
	}
	@Override
	public void loadSpotAds() {
		
		QLNetTools.httpRequestAd(context);
				     
	}

	@Override
	public void setSpotOrientation(int orientation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAnimationType(int animationType) {
		this.animationType = animationType;
	}

	@SuppressLint("NewApi")
	@Override
	public void showSpotAds(final Context con) {
		QLSpotView view = new QLSpotView(con,this.animationType);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		//params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);
		Activity ac = (Activity)con;
		ac.addContentView(view, params);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void showSpotAdById(String id) {
		QLNetTools.httpRequestSpotAdById(context, id);					
	}

	@Override
	public void showSpotAds(Context con, QLSpotDialogListener spotDialogListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSplashSpotAds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSplashSpotAds(Context context, Class<?> targetActivity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean disMiss() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getAnimationType() {
		return this.animationType;
	}
	

}
