package com.qinglu;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.client.ServiceManager;

import net.youmi.android.banner.AdSize;

import com.qinglu.ad.QLAdManager;
import com.qinglu.ad.QLAdView;
import com.qinglu.ad.QLOffersManager;
import com.qinglu.ad.QLSpotManager;
import com.qinglu.ad.impl.qinglu.QLAdManagerQingLu;
import com.qinglu.ad.impl.qinglu.QLSpotManagerQingLu;
import com.qinglu.ad.impl.youmi.QLAdManagerYouMi;
import com.qinglu.ad.impl.youmi.QLAdViewYouMi;
import com.qinglu.ad.impl.youmi.QLOffersManagerYouMi;
import com.qinglu.ad.impl.youmi.QLSpotManagerYouMi;
import com.qinglu.ad.tools.QLNetTools;

import android.content.Context;
import android.util.Log;

public class QLAdController {
	private static QLAdController controller;
	private static List<QLAdManager> managers;
	private static List<QLSpotManager> spotManagers;
	private static List<QLOffersManager> offersManagers;
	private static int CurrPlatform = 0;
	private static boolean testModel;
	private static int notifiIcon;
	private Context con;
	
	private QLAdController()
	{
		managers = new ArrayList<QLAdManager>();
		spotManagers = new ArrayList<QLSpotManager>();
		offersManagers = new ArrayList<QLOffersManager>();
	}
	
	public static QLAdController getInstance()
	{
		if(controller == null)
		{
			controller = new QLAdController();					
		}			
		return controller;
	}
	
	public void init(Context context,int notificationIcon,boolean isTestModel)
	{
		this.con = context;		
		testModel = isTestModel;
		
		notifiIcon = notificationIcon;
		QLNetTools.requestAdPlatfrom(context);		
	}
	
	public void init()
	{
		CurrPlatform = QLCommon.CurrPlatform;
		
		// Start the service
        ServiceManager serviceManager = new ServiceManager(con);
        serviceManager.setNotificationIcon(notifiIcon);
        serviceManager.startService();	
        
        QLAdController.getQLAdManager(con);
	}
	
	//获得管理器实例
	public static QLAdManager getQLAdManager(Context context)
	{
		if(managers.size() > 0)
		{
			for(QLAdManager manager : managers)
			{
				if(CurrPlatform == QLCommon.YouMi && manager instanceof QLAdManagerYouMi)
				{
					return manager;
				}
				else if(CurrPlatform == QLCommon.QingLu && manager instanceof QLAdManagerQingLu)
				{
					return manager;
				}
			}		
		}
		
		QLAdManager manager = null;		
		if(CurrPlatform == QLCommon.YouMi)
		{
			manager = new QLAdManagerYouMi(context);			
		}
		else if(CurrPlatform == QLCommon.QingLu)
		{
			manager = new QLAdManagerQingLu(context);
		}
		else
		{
			manager = new QLAdManagerQingLu(context);
		}
		
		manager.init(testModel);
		managers.add(manager);
		return manager;
	}
		
	//获得插屏管理器实例
	public static QLSpotManager getQLSpotManager(Context context)
	{
		if(spotManagers.size() > 0)
		{
			for(QLSpotManager spotManager : spotManagers)
			{
				if(CurrPlatform == QLCommon.YouMi && spotManager instanceof QLSpotManagerYouMi)
				{
					return spotManager;
				}
				else if(CurrPlatform == QLCommon.QingLu && spotManager instanceof QLSpotManagerQingLu)
				{
					return spotManager;
				}
			}		
		}
		QLSpotManager spotManager = null;		 
		if(CurrPlatform == QLCommon.YouMi)
		{
			spotManager = new QLSpotManagerYouMi(context);
		}
		else if(CurrPlatform == QLCommon.QingLu)
		{
			spotManager = new QLSpotManagerQingLu(context);
		}
		else
		{
			
		}
		spotManagers.add(spotManager);
		return spotManager;
	}
	
	
	//获得广告条管理器实例
	public static QLAdView getQLAdView(Context context,QLSize size)
	{
				
		CurrPlatform = QLCommon.CurrPlatform;
		
		QLAdView view = null;
		
		if(CurrPlatform == QLCommon.YouMi)
		{
			AdSize s = new AdSize(size.width, size.height);
			view = new QLAdViewYouMi(context,s);
		}
		else
		{
			AdSize s = new AdSize(size.width, size.height);
			view = new QLAdViewYouMi(context,s);
		}
		return view;
	}
	
	//获得积分墙管理器实例
	public static QLOffersManager getQLOffersManager(Context context)
	{
		if(offersManagers.size() > 0)
		{
			for(QLOffersManager offersManager : offersManagers)
			{
				if(CurrPlatform == QLCommon.YouMi && offersManager instanceof QLOffersManagerYouMi)
				{
					return offersManager;
				}
				else if(CurrPlatform == QLCommon.QingLu && offersManager instanceof QLOffersManagerYouMi)
				{
					return offersManager;
				}
			}		
		}
		QLOffersManager offersManager = null;		 
		if(CurrPlatform == QLCommon.YouMi)
		{
			offersManager = new QLOffersManagerYouMi(context);
		}
		else if(CurrPlatform == QLCommon.QingLu)
		{
			offersManager = new QLOffersManagerYouMi(context);
		}
		else
		{
			
		}
		offersManagers.add(offersManager);
		return offersManager;
	}
	
	//得到上下文
	public Context getContext()
	{
		return con;
	}
	
	//切换广告
	public void changeAdPlatform(int platform)
	{			
		if(CurrPlatform != platform)
		{
			int animationType = QLAdController.getQLSpotManager(con).getAnimationType();
			
			QLCommon.CurrPlatform = platform;
			CurrPlatform = platform;
			
			QLAdController.getQLAdManager(con);
			//插屏处理
			QLAdController.getQLSpotManager(con).loadSpotAds();
			QLAdController.getQLSpotManager(con).setAnimationType(animationType);
		}				
	}
}
