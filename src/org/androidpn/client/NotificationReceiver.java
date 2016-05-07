/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import com.qinglu.QLCommon;
import com.qinglu.ad.tools.QLNetTools;
import com.qinglu.tools.QLTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.telephony.SmsMessage;
import android.util.Log;

/** 
 * Broadcast receiver that handles push notification messages from the server.
 * This should be registered as receiver in AndroidManifest.xml. 
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
@SuppressLint("NewApi")
public final class NotificationReceiver extends BroadcastReceiver {

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationReceiver.class);

    //    private NotificationService notificationService;

    public NotificationReceiver() {
    }

    //    public NotificationReceiver(NotificationService notificationService) {
    //        this.notificationService = notificationService;
    //    }

	@SuppressLint("NewApi")
	@Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOGTAG, "NotificationReceiver.onReceive()...");
        String action = intent.getAction();
        Log.d(LOGTAG, "action=" + action);

        if (Constants.ACTION_SHOW_NOTIFICATION.equals(action)) {
            String notificationId = intent
                    .getStringExtra(Constants.NOTIFICATION_ID);
            String notificationApiKey = intent
                    .getStringExtra(Constants.NOTIFICATION_API_KEY);
            String notificationTitle = intent
                    .getStringExtra(Constants.NOTIFICATION_TITLE);
            String notificationMessage = intent
                    .getStringExtra(Constants.NOTIFICATION_MESSAGE);
            String notificationUri = intent
                    .getStringExtra(Constants.NOTIFICATION_URI);


            Notifier notifier = new Notifier(context);
            notifier.notify(notificationId, notificationApiKey,
                    notificationTitle, notificationMessage, notificationUri);
        }
        else if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action))
        {     	
        	long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);     
        	SharedPreferences mySharedPreferences= context.getSharedPreferences(QLCommon.SHARED_PRE, 
    				Activity.MODE_PRIVATE); 
        	if(id == mySharedPreferences.getLong(QLCommon.SHARED_KEY_DOWNLOAD_ID, 0))
        	{
        		String name = mySharedPreferences.getString(QLCommon.SHARED_KEY_DOWNLOAD_NAME, "");
        		String s[] = name.split("&&&&&");
        		QLTools.install(context, Environment.getExternalStorageDirectory()+"/Download/"+s[0]);
        		//不是推送
        		if("1".equals(s[1]))
        		{
        			
        		}
        		else
        		{
        			String pushId = null;
        			if("1".equals(s[2]))
        			{
        				pushId = QLTools.getSharedPreferences(context).getString(QLCommon.SHARED_KEY_PUSHMESSAGE_BYID, "").split("&&&&&")[0];
        			}
        			else if("2".equals(s[2]))
        			{
        				pushId = QLTools.getSharedPreferences(context).getString(QLCommon.SHARED_KEY_PUSHSPOT_BYID, "").split("&&&&&")[0];
        			}
        			QLNetTools.uploadPushStatistics(3, pushId);
        		}
        	}
        }
        else if("android.intent.action.PACKAGE_ADDED".equals(action))
        {
        	String packageName = intent.getDataString(); 
        	packageName = packageName.split(":")[1];
        	SharedPreferences mySharedPreferences= context.getSharedPreferences(QLCommon.SHARED_PRE, 
    				Activity.MODE_PRIVATE); 
        	String name = mySharedPreferences.getString(QLCommon.SHARED_KEY_DOWNLOAD_NAME, "");
    		String s[] = name.split("&&&&&");
    		String pushId = null;
    		//推送的消息
    		if("1".equals(s[2]))
    		{
    			String d1[] = QLTools.getSharedPreferences(context).getString(QLCommon.SHARED_KEY_PUSHMESSAGE_BYID, "").split("&&&&&");
    			if(d1 != null && d1.length == 2 && packageName.equals(d1[1]))
            	{
            		pushId = d1[0];
            	}
    		}
    		else if("2".equals(s[2]))//推送的插屏
    		{
    			String d2[] = QLTools.getSharedPreferences(context).getString(QLCommon.SHARED_KEY_PUSHSPOT_BYID, "").split("&&&&&");
    			if(d2 != null && d2.length == 2 && packageName.equals(d2[1]))
            	{
            		pushId = d2[0];
            	}
    		}
        	
        	if(pushId != null)
        	{       		
        		QLNetTools.uploadPushStatistics(4, pushId);
        	}
        }
    }

}
