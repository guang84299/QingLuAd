package com.qinglu;


//ƽ̨���
public class QLCommon {

	//��ǰѡ��ƽ̨
	public static int CurrPlatform = 0;//��¶
	
	//ƽ̨��ʾ
	public static final int QingLu = 0;//��¶
	public static final int YouMi = 1;//����	
	public static final int GuangDianTong = 2;//���ͨ
	
	
	
	//��Ļ���
	public static int ORIENTATION_PORTRAIT = 0;//������ֵ
	public static int ORIENTATION_LANDSCAPE = 1;//������ֵ
	
	public static int ANIM_NONE = 0;//Ϊ�޶���
	public static int ANIM_SIMPLE = 1;//Ϊ�򵥶���Ч��
	public static int ANIM_ADVANCE = 2;//Ϊ�߼�����Ч��
	
	// ������ߴ�
	public static QLSize FIT_SCREEN;    // ����Ӧ��Ļ���
	public static QLSize SIZE_320x50 = new QLSize(320,50);  // �ֻ�
	public static QLSize SIZE_300x250 = new QLSize(300,250);  // �ֻ���ƽ��
	public static QLSize SIZE_468x60 = new QLSize(468,60);  // ƽ��
	public static QLSize SIZE_728x90 = new QLSize(728,90);  // ƽ��
	
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
	//����һ���������
	public static final String URI_GET_SPOT_AD_BYID = SERVER_ADDRESS + "ad.do?action=getAdById";
	
	public static final String xmppHost = SERVER_IP;
	public static final String xmppPort = "5222";
	
	public static final String SHARED_PRE = "qingluad";
	public static final String SHARED_KEY_ID = "id";
	public static final String SHARED_KEY_SPOT = "spot";
	public static final String SHARED_KEY_SPOT_BYID = "spotId";
	//����id
	public static final String SHARED_KEY_DOWNLOAD_ID = "downloadid";
	public static final String SHARED_KEY_DOWNLOAD_NAME = "downloadName";
	
	//intent ��ת QLActivity ����
	public static final String INTENT_TYPE = "INTENT_TYPE";
	public static final String INTENT_PUSH_DOWNLOAD = "INTENT_PUSH_DOWNLOAD";
	public static final String INTENT_SPOT = "INTENT_SPOT";
	
}

