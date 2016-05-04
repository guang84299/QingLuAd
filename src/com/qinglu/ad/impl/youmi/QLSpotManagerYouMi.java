package com.qinglu.ad.impl.youmi;

import net.youmi.android.spot.SpotManager;
import android.content.Context;

import com.qinglu.QLCommon;
import com.qinglu.ad.QLSpotManager;
import com.qinglu.ad.listener.QLSpotDialogListener;

public class QLSpotManagerYouMi implements QLSpotManager{

	private Context context;
	private int animationType;
	
	public QLSpotManagerYouMi(Context context)
	{
		this.context = context;
		
		QLCommon.ORIENTATION_PORTRAIT = SpotManager.ORIENTATION_PORTRAIT;
		QLCommon.ORIENTATION_LANDSCAPE = SpotManager.ORIENTATION_LANDSCAPE;
		
		QLCommon.ANIM_NONE = SpotManager.ANIM_NONE;
		QLCommon.ANIM_SIMPLE = SpotManager.ANIM_SIMPLE;
		QLCommon.ANIM_ADVANCE = SpotManager.ANIM_ADVANCE;
	}
	
	@Override
	public void loadSpotAds() {
		SpotManager.getInstance(context).loadSpotAds();
	}

	@Override
	public void setSpotOrientation(int orientation) {
		SpotManager.getInstance(context).setSpotOrientation(orientation);
	}

	@Override
	public void setAnimationType(int animationType) {
		this.animationType = animationType;
		SpotManager.getInstance(context).setAnimationType(animationType);
	}

	@Override
	public void showSpotAds(Context con) {
		SpotManager.getInstance(context).showSpotAds(con);
	}
	
	@Override
	public void showSpotAds(Context con,QLSpotDialogListener spotDialogListener) {
		QLSpotDialogListenerYouMi listener = new QLSpotDialogListenerYouMi();
		listener.setQLSpotDialogListener(spotDialogListener);
		SpotManager.getInstance(context).showSpotAds(con,listener);
	}

	@Override
	public boolean disMiss() {
		 return SpotManager.getInstance(context).disMiss();
	}

	@Override
	public void onDestroy() {
		SpotManager.getInstance(context).onDestroy();
	}

	@Override
	public void loadSplashSpotAds() {
		SpotManager.getInstance(context).loadSpotAds();
	}

	@Override
	public void showSplashSpotAds(Context con, Class<?> targetActivity) {
		SpotManager.getInstance(context).showSplashSpotAds(con, targetActivity);
	}

	@Override
	public int getAnimationType() {
		return this.animationType;
	}

	@Override
	public void showSpotAdById(String id) {
		// TODO Auto-generated method stub
		
	}

}
