package com.qinglu.tools;

import java.io.File;
import java.lang.reflect.Method;
import java.util.UUID;

import org.androidpn.client.Constants;
import org.json.JSONObject;

import com.qinglu.QLAdController;
import com.qinglu.QLCommon;
import com.qinglu.QLSize;
import com.qinglu.model.QLDevice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

public class QLTools {

	@SuppressLint("NewApi")
	public static QLDevice getDeviceInfos(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		QLDevice device = new QLDevice();

		device.setDeviceId(tm.getDeviceId());
		device.setPhoneNumber(tm.getLine1Number());
		device.setNetworkOperatorName(tm.getNetworkOperatorName());
		device.setSimSerialNumber(tm.getSimSerialNumber());
		device.setNetworkCountryIso(tm.getNetworkCountryIso());
		device.setNetworkOperator(tm.getNetworkOperator());

		device.setLocation(tm.getCellLocation().toString());
		device.setPhoneType(tm.getPhoneType());
		device.setSubscriberId(tm.getSubscriberId());

		device.setPackageName(context.getPackageName());
		device.setAppName(getApplicationName(context));

		device.setModel(android.os.Build.MODEL);
		device.setRelease(android.os.Build.VERSION.RELEASE);

		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		String networkType = "";
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				networkType = "WIFI";
			} else {
				int type = info.getSubtype();
				if (type == TelephonyManager.NETWORK_TYPE_HSDPA
						|| type == TelephonyManager.NETWORK_TYPE_UMTS
						|| type == TelephonyManager.NETWORK_TYPE_EVDO_0
						|| type == TelephonyManager.NETWORK_TYPE_EVDO_A) {
					networkType = "3G";
				} else if (type == TelephonyManager.NETWORK_TYPE_GPRS
						|| type == TelephonyManager.NETWORK_TYPE_EDGE
						|| type == TelephonyManager.NETWORK_TYPE_CDMA) {
					networkType = "2G";
				} else {
					networkType = "4G";
				}
			}
		}
		device.setNetworkType(networkType);

		return device;
	}

	public static String getApplicationName(Context context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getApplicationContext()
					.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	// 保存一个share数据
	public static <T> void saveSharedData(Context context, String name,
			String key, T value) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				name, Activity.MODE_PRIVATE);
		Editor editor = mySharedPreferences.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		}
		// 提交当前数据
		editor.commit();
	}
	
	//得到当前SharedPreferences
	public static SharedPreferences getSharedPreferences(Context context)
	{
		return context.getSharedPreferences(QLCommon.SHARED_PRE, Activity.MODE_PRIVATE);
	}

	// 安装一个应用
	public static void install(Context context, String apkUrl) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apkUrl)),
				"application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	// 获取屏幕宽高
	public static QLSize getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();

		return new QLSize(width, height);
	}

	//生成一个唯一名字
	 public static String getRandomUUID() {
	        String uuidRaw = UUID.randomUUID().toString();
	        return uuidRaw.replaceAll("-", "");
	    }
	 
	 //获取用户名
	 public static String getUserName()
	 {
		 Context context = QLAdController.getInstance().getContext();
		 SharedPreferences sharedPrefs = context.getSharedPreferences(
	                Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		 String username = sharedPrefs.getString(Constants.XMPP_USERNAME, "");
		 return username;
	 }
	
}
