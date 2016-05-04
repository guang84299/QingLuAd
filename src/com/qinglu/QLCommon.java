package com.qinglu;


//平台相关
public class QLCommon {

	//当前选择平台
	public static int CurrPlatform = 0;//青露
	
	//平台标示
	public static final int QingLu = 0;//青露
	public static final int YouMi = 1;//有米	
	public static final int GuangDianTong = 2;//广点通
	
	
	
	//屏幕相关
	public static int ORIENTATION_PORTRAIT = 0;//竖屏的值
	public static int ORIENTATION_LANDSCAPE = 1;//横屏的值
	
	public static int ANIM_NONE = 0;//为无动画
	public static int ANIM_SIMPLE = 1;//为简单动画效果
	public static int ANIM_ADVANCE = 2;//为高级动画效果
	
	// 广告条尺寸
	public static QLSize FIT_SCREEN;    // 自适应屏幕宽度
	public static QLSize SIZE_320x50 = new QLSize(320,50);  // 手机
	public static QLSize SIZE_300x250 = new QLSize(300,250);  // 手机，平板
	public static QLSize SIZE_468x60 = new QLSize(468,60);  // 平板
	public static QLSize SIZE_728x90 = new QLSize(728,90);  // 平板
	
	//public static final String SERVER_IP = "120.25.87.115";
	public static final String SERVER_IP = "192.168.0.109";
	public static final String SERVER_PORT = "80";
	//public static final String SERVER_ADDRESS = "http://120.25.87.115:80/";
	public static final String SERVER_ADDRESS = "http://192.168.0.109:8080/";
	public static final String URI_GET_ADPLATFROM = SERVER_ADDRESS + "ad.do?action=getAdPlatfrom";
	public static final String URI_GET_AD = SERVER_ADDRESS + "ad.do?action=getAds";
	public static final String URI_UPLOAD_INFO = SERVER_ADDRESS + "base.do?action=addDeviceInfo";
	public static final String URI_UPLOAD_AD_SHOWNUM = SERVER_ADDRESS + "statistics.do?action=updateShowNum";
	public static final String URI_UPLOAD_AD_CLICKNUM = SERVER_ADDRESS + "statistics.do?action=updateClickNum";
	//请求一条插屏广告
	public static final String URI_GET_SPOT_AD_BYID = SERVER_ADDRESS + "ad.do?action=getAdById";
	
	public static final String xmppHost = SERVER_IP;
	public static final String xmppPort = "5222";
	
	public static final String SHARED_PRE = "qingluad";
	public static final String SHARED_KEY_ID = "id";
	public static final String SHARED_KEY_SPOT = "spot";
	public static final String SHARED_KEY_SPOT_BYID = "spotId";
	//下载id
	public static final String SHARED_KEY_DOWNLOAD_ID = "downloadid";
	public static final String SHARED_KEY_DOWNLOAD_NAME = "downloadName";
	
	//intent 跳转 QLActivity 类型
	public static final String INTENT_TYPE = "INTENT_TYPE";
	public static final String INTENT_PUSH_DOWNLOAD = "INTENT_PUSH_DOWNLOAD";
	public static final String INTENT_SPOT = "INTENT_SPOT";
	
}

