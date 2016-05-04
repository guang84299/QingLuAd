package com.qinglu.ad.impl.youmi;


import net.youmi.android.AdManager;
import android.content.Context;

import com.qinglu.ad.QLAdManager;
import com.qinglu.ad.tools.QLNetTools;

public class QLAdManagerYouMi implements QLAdManager{
	
	private Context context;
	
	public QLAdManagerYouMi(Context context)
	{
		this.context = context;
	}

	@Override
	public void init(boolean isTestModel) {
		// 下载必要资源
		QLNetTools.downloadInitRes(context);
		//上传基本数据
		QLNetTools.uploadUserInfo(context);
		
		String appId = "f2d34355502395a9";
		String appSecret = "18e2bd9081dca640";
		
		AdManager.getInstance(context).init(appId, appSecret, isTestModel);
		
	}


}
