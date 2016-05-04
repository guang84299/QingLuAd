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
        		QLTools.install(context, Environment.getExternalStorageDirectory()+"/Download/"+name);
        	}
        }

    }

}
