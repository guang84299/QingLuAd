package com.qinglu.ad.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.androidpn.client.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;

import com.qinglu.QLAdController;
import com.qinglu.QLCommon;
import com.qinglu.ad.QLActivity;
import com.qinglu.model.QLDevice;
import com.qinglu.tools.QLTools;

public class QLNetTools {

	// �����ʼ�����ƽ̨
	public static void requestAdPlatfrom(final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				SharedPreferences sharedPrefs = context.getSharedPreferences(
						Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
				String newUsername = sharedPrefs.getString(
						Constants.XMPP_USERNAME, null);

				List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				if (newUsername == null) {
					newUsername = QLTools.getDeviceInfos(context).getDeviceId();
					NameValuePair pair1 = new BasicNameValuePair("data",
							newUsername);
					pairList.add(pair1);
				}

				int platform = 0;

				try {
					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
							pairList, "UTF-8");
					// URLʹ�û���URL���ɣ����в���Ҫ�Ӳ���
					HttpPost httpPost = new HttpPost(
							QLCommon.URI_GET_ADPLATFROM);
					// �����������ݼ���������
					httpPost.setEntity(requestHttpEntity);
					// ��Ҫ�ͻ��˶�������������
					HttpClient httpClient = new DefaultHttpClient();

					// ��������
					HttpResponse httpResponse = httpClient.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");// ��entity���е�����ת��Ϊ�ַ���

						if (response != null) {
							String[] res = response.split("&");
							platform = Integer.parseInt(res[0]);
							if (platform == -1)
								platform = 0;

							if (!"0".equals(res[1])) {
								Editor editor = sharedPrefs.edit();
								editor.putString(Constants.XMPP_USERNAME,
										newUsername);
								editor.putString(Constants.XMPP_PASSWORD,
										res[1]);
								editor.commit();
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				QLCommon.CurrPlatform = platform;
				QLAdController.getInstance().init();
			}
		}).start();
	}

	// ����������
	public static void httpRequestAd(final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// ��һ��������HttpClient����
				HttpClient httpCient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(QLCommon.URI_GET_AD);
				HttpResponse httpResponse;
				try {
					httpResponse = httpCient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");// ��entity���е�����ת��Ϊ�ַ���

						if (response != null) {
							JSONArray arr = new JSONArray(response);

							SharedPreferences mySharedPreferences = context
									.getSharedPreferences(QLCommon.SHARED_PRE,
											Activity.MODE_PRIVATE);
							Editor editor = mySharedPreferences.edit();
							editor.putString(QLCommon.SHARED_KEY_SPOT, response);
							// �����������
							editor.commit();

							// ����ͼƬ����
							for (int i = 0; i < arr.length(); i++) {
								JSONObject obj = arr.getJSONObject(i);
								String pic = obj.getString("picPath");

								// �ж�ͼƬ�Ƿ����
								String picRelPath = context.getFilesDir()
										.getPath() + "/" + pic;
								File file = new File(picRelPath);
								if (file.exists()) {
									continue;
								}
								// ����������ж��ļ����Ƿ���ڣ��������򴴽�
								File destDir = new File(
										context.getFilesDir().getPath()
												+ "/"
												+ pic.substring(0,
														pic.lastIndexOf("/")));
								if (!destDir.exists()) {
									destDir.mkdirs();
								}

								String address = QLCommon.SERVER_ADDRESS + pic;
								// ������������ͼƬ
								URLConnection openConnection = new URL(address)
										.openConnection();
								InputStream is = openConnection
										.getInputStream();
								byte[] buff = new byte[1024];
								int len;
								// Ȼ���Ǵ����ļ���
								FileOutputStream fos = new FileOutputStream(
										file);
								if (null != is) {
									while ((len = is.read(buff)) != -1) {
										fos.write(buff, 0, len);
									}
								}
								fos.close();
								is.close();
							}
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	// ���ݹ��idʵʱ����һ������
	public static void httpRequestSpotAdById(final Context context,
			final String id) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					
					NameValuePair pair1 = new BasicNameValuePair("data", id);
					List<NameValuePair> pairList = new ArrayList<NameValuePair>();
					pairList.add(pair1);

					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
							pairList, "UTF-8");
					// URLʹ�û���URL���ɣ����в���Ҫ�Ӳ���
					HttpPost httpPost = new HttpPost(
							QLCommon.URI_GET_SPOT_AD_BYID);
					// �����������ݼ���������
					httpPost.setEntity(requestHttpEntity);
					// ��Ҫ�ͻ��˶�������������
					HttpClient httpClient = new DefaultHttpClient();

					HttpResponse httpResponse;

					httpResponse = httpClient.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");// ��entity���е�����ת��Ϊ�ַ���

						if (response != null) {
							JSONObject arr = new JSONObject(response);
							// to do
							SharedPreferences mySharedPreferences = context
									.getSharedPreferences(QLCommon.SHARED_PRE,
											Activity.MODE_PRIVATE);
							Editor editor = mySharedPreferences.edit();
							editor.putString(QLCommon.SHARED_KEY_SPOT_BYID,
									response);
							// �����������
							editor.commit();

							// ����ͼƬ����
							JSONObject obj = arr;
							String pic = obj.getString("picPath");

							// �ж�ͼƬ�Ƿ����
							String picRelPath = context.getFilesDir().getPath()
									+ "/" + pic;
							File file = new File(picRelPath);
							if (!file.exists()) {
								// ����������ж��ļ����Ƿ���ڣ��������򴴽�
								File destDir = new File(
										context.getFilesDir().getPath()
												+ "/"
												+ pic.substring(0,
														pic.lastIndexOf("/")));
								if (!destDir.exists()) {
									destDir.mkdirs();
								}

								String address = QLCommon.SERVER_ADDRESS + pic;
								// ������������ͼƬ
								URLConnection openConnection = new URL(address)
										.openConnection();
								InputStream is = openConnection
										.getInputStream();
								byte[] buff = new byte[1024];
								int len;
								// Ȼ���Ǵ����ļ���
								FileOutputStream fos = new FileOutputStream(
										file);
								if (null != is) {
									while ((len = is.read(buff)) != -1) {
										fos.write(buff, 0, len);
									}
								}
								fos.close();
								is.close();
							}

						}
					}
					
					Intent intent = new Intent(context, QLActivity.class);
					intent.putExtra(QLCommon.INTENT_TYPE, QLCommon.INTENT_SPOT);
					context.startActivity(intent);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	// ����apk�ļ�
	@SuppressLint("NewApi")
	public static void download(final Context context, String fileUri) {
		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		Uri uri = Uri.parse(fileUri);
		Request request = new Request(uri);
		// ��������ʹ�õ��������ͣ��������ƶ������wifi������
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
				| DownloadManager.Request.NETWORK_WIFI);
		// ����ʾ���ؽ���
		request.setVisibleInDownloadsUi(true);
		String name = QLTools.getRandomUUID() + ".apk";
		request.setDestinationInExternalPublicDir("/download/", name);

		long id = downloadManager.enqueue(request);
		QLTools.saveSharedData(context, QLCommon.SHARED_PRE,
				QLCommon.SHARED_KEY_DOWNLOAD_ID, id);
		QLTools.saveSharedData(context, QLCommon.SHARED_PRE,
				QLCommon.SHARED_KEY_DOWNLOAD_NAME, name);
	}

	// ���س�ʼ����Դ
	public static void downloadInitRes(final Context context) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				String pic = "images/close.jpg";
				// �ж�ͼƬ�Ƿ����
				String picRelPath = context.getFilesDir().getPath() + "/" + pic;
				File file = new File(picRelPath);
				if (file.exists()) {
					return;
				}
				// ����������ж��ļ����Ƿ���ڣ��������򴴽�
				File destDir = new File(context.getFilesDir().getPath() + "/"
						+ pic.substring(0, pic.lastIndexOf("/")));
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				String address = QLCommon.SERVER_ADDRESS + pic;
				try {
					// ������������ͼƬ
					URLConnection openConnection = new URL(address)
							.openConnection();
					InputStream is = openConnection.getInputStream();
					byte[] buff = new byte[1024];
					int len;
					// Ȼ���Ǵ����ļ���
					FileOutputStream fos = new FileOutputStream(file);
					if (null != is) {
						while ((len = is.read(buff)) != -1) {
							fos.write(buff, 0, len);
						}
					}
					fos.close();
					is.close();
				} catch (Exception e) {

				}
			}
		}).start();

	}

	// �ϴ������豸��Ϣ
	public static void uploadUserInfo(final Context context) {

		String id = QLTools.getSharedPreferences(context).getString(
				QLCommon.SHARED_KEY_ID, "");
		if (id != null && !"".equals(id)) {
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					JSONObject obj = new JSONObject();
					QLDevice infos = QLTools.getDeviceInfos(context);
					obj = obj.put("id", null);
					obj = obj.put("deviceId", infos.getDeviceId());
					obj = obj.put("phoneNumber", infos.getPhoneNumber());
					obj = obj.put("networkOperatorName",
							infos.getNetworkOperatorName());
					obj = obj.put("simSerialNumber", infos.getSimSerialNumber());
					obj = obj.put("networkCountryIso",
							infos.getNetworkCountryIso());
					obj = obj.put("networkOperator", infos.getNetworkOperator());
					obj = obj.put("networkType", infos.getNetworkType());
					obj = obj.put("location", infos.getLocation());
					obj = obj.put("phoneType", infos.getPhoneType());
					obj = obj.put("subscriberId", infos.getSubscriberId());
					obj = obj.put("packageName", infos.getPackageName());
					obj = obj.put("appName", infos.getAppName());
					obj = obj.put("model", infos.getModel());
					obj = obj.put("release", infos.getRelease());

					NameValuePair pair1 = new BasicNameValuePair("data", obj
							.toString());
					List<NameValuePair> pairList = new ArrayList<NameValuePair>();
					pairList.add(pair1);

					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
							pairList, "UTF-8");
					// URLʹ�û���URL���ɣ����в���Ҫ�Ӳ���
					HttpPost httpPost = new HttpPost(QLCommon.URI_UPLOAD_INFO);
					// �����������ݼ���������
					httpPost.setEntity(requestHttpEntity);
					// ��Ҫ�ͻ��˶�������������
					HttpClient httpClient = new DefaultHttpClient();

					// ��������
					HttpResponse response = httpClient.execute(httpPost);
					// ��ʾ��Ӧ
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						String responseStr = EntityUtils.toString(entity,
								"utf-8");// ��entity���е�����ת��Ϊ�ַ���
						if (responseStr != null && "1".equals(responseStr)) {
							Log.e("======uploadUserInfo=======", "===�ϴ��ɹ�===");
							QLTools.saveSharedData(context,
									QLCommon.SHARED_PRE,
									QLCommon.SHARED_KEY_ID, infos.getDeviceId());
						}
					} else {
						Log.e("======uploadUserInfo=======", "===�ϴ�ʧ��===");
					}
				} catch (Exception e) {
					Log.e("======uploadUserInfo=======", "===�ϴ�ʧ��===");
					e.printStackTrace();
				}

			}
		}).start();

	}

	// �ϴ�ͳ����Ϣ type �ϴ����� 1:չʾ 2����� id ���id
	public static void uploadStatistics(final int type, final long id) {
		Thread t = new Thread() {
			public void run() {
				super.run();
				try {
					NameValuePair pair1 = new BasicNameValuePair("data", id
							+ "");
					List<NameValuePair> pairList = new ArrayList<NameValuePair>();
					pairList.add(pair1);

					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
							pairList, "UTF-8");
					// URLʹ�û���URL���ɣ����в���Ҫ�Ӳ���
					String url = QLCommon.URI_UPLOAD_AD_SHOWNUM;
					if (type == 2) {
						url = QLCommon.URI_UPLOAD_AD_CLICKNUM;
					}

					HttpPost httpPost = new HttpPost(url);
					// �����������ݼ���������
					httpPost.setEntity(requestHttpEntity);
					// ��Ҫ�ͻ��˶�������������
					HttpClient httpClient = new DefaultHttpClient();

					// ��������
					HttpResponse response = httpClient.execute(httpPost);
					// ��ʾ��Ӧ
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						String responseStr = EntityUtils.toString(entity,
								"utf-8");// ��entity���е�����ת��Ϊ�ַ���

						if (responseStr != null && "1".equals(responseStr)) {
							Log.e("======uploadStatistics=======", "===�ϴ��ɹ�===");
						}
					} else {
						Log.e("======uploadStatistics=======", "===�ϴ�ʧ��===");
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};

		t.start();
	}

}
