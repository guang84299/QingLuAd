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

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

import com.qinglu.QLAdController;
import com.qinglu.QLCommon;
import com.qinglu.QLProtocolKey;
import com.qinglu.ad.tools.QLNetTools;
import com.qinglu.tools.QLTools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/** 
 * This class notifies the receiver of incoming notifcation packets asynchronously.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationPacketListener implements PacketListener {

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationPacketListener.class);

    private final XmppManager xmppManager;

    public NotificationPacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    @Override
    public void processPacket(Packet packet) {
        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());

        if (packet instanceof NotificationIQ) {
            NotificationIQ notification = (NotificationIQ) packet;

            if (notification.getChildElementXML().contains(
                    "androidpn:iq:notification")) {
            	String notificationApiKey = notification.getApiKey();
            	
            	if(QLProtocolKey.CHANGE_AD.equals(notificationApiKey))
            	{
            		changeAdPlatform(notification);
            	}
            	else if(QLProtocolKey.PUSH_SPOT.equals(notificationApiKey))
            	{
            		pushSpot(notification);
            	}
            	else
            	{           		
            		sendMessage(notification);
            	}
            }
        }

    }
    //请求广告
    public void changeAdPlatform(NotificationIQ notification)
    {
    	String platform = notification.getMessage();
    	int ad_platfrom = Integer.parseInt(platform);
    	QLAdController.getInstance().changeAdPlatform(ad_platfrom);
    }
    
    //推送一个插屏
    public void pushSpot(NotificationIQ notification)
    {
    	String []message = notification.getMessage().split("&&&&&");
    	String adId = message[0];
    	String pushId = message[1];
    	String packageName = message[2];
    	if(adId == null || "".equals(adId))
    		return;
    	QLTools.saveSharedData(NotificationService.getInstanceService(), QLCommon.SHARED_PRE, QLCommon.SHARED_KEY_PUSHSPOT_BYID, pushId+ "&&&&&" + packageName);
    	QLAdController.getQLSpotManager(NotificationService.getInstanceService()).showSpotAdById(adId);		
    }

    public void sendMessage(NotificationIQ notification)
    {
    	 String notificationId = notification.getId();
         String notificationApiKey = notification.getApiKey();
         String notificationTitle = notification.getTitle();
         String notificationMessage = notification.getMessage();
        //消息体 ： pushid ：包名
         String pushId = notificationMessage.split("&&&&&")[1];
         String packageName = notificationMessage.split("&&&&&")[2];
         notificationMessage = notificationMessage.split("&&&&&")[0];
         //                String notificationTicker = notification.getTicker();
         String notificationUri = notification.getUri();

         Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
         intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
         intent.putExtra(Constants.NOTIFICATION_API_KEY,
                 notificationApiKey);
         intent
                 .putExtra(Constants.NOTIFICATION_TITLE,
                         notificationTitle);
         intent.putExtra(Constants.NOTIFICATION_MESSAGE,
                 notificationMessage);
         intent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
         //                intent.setData(Uri.parse((new StringBuilder(
         //                        "notif://notification.androidpn.org/")).append(
         //                        notificationApiKey).append("/").append(
         //                        System.currentTimeMillis()).toString()));

         xmppManager.getContext().sendBroadcast(intent);
         
		 QLTools.saveSharedData(NotificationService.getInstanceService(), QLCommon.SHARED_PRE, QLCommon.SHARED_KEY_PUSHMESSAGE_BYID, pushId + "&&&&&" + packageName);
         QLNetTools.uploadPushStatistics(1, pushId);
    }
}
