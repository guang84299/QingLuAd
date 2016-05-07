package com.qinglu.ad.impl.qinglu;

import android.content.Context;
import android.util.Log;

import com.qinglu.ad.QLAdManager;
import com.qinglu.ad.tools.QLNetTools;

public class QLAdManagerQingLu implements QLAdManager{
	private Context context;
	public QLAdManagerQingLu(Context context)
	{
		this.context = context;
	}
	@Override
	public void init(boolean isTestModel) {		
		// 下载必要资源
		QLNetTools.downloadInitRes(context);
		
	}

}
