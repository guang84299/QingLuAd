package com.qinglu.ad;

import com.qinglu.ad.listener.QLOffersWallDialogListener;

import android.app.Activity;

public interface QLOffersManager {
	//����ǽ���ܳ�ʼ��
	void onAppLaunch();
	//��Դ����
	void onAppExit();
	//չʾȫ������ǽ
	void showOffersWall();
	//չʾ������������ǽ
	void showOffersWallDialog(Activity activity);
	// ����Ի���رռ�����
	void showOffersWallDialog(Activity activity, QLOffersWallDialogListener listener);
	//����Ի���������
	void showOffersWallDialog(Activity activity, int width, int height);
	//����Ի����������Լ��رռ�����
	void showOffersWallDialog(Activity activity, int width, int height, QLOffersWallDialogListener listener);
	//����Ի�������ռ��Ļ������0~1��
	void showOffersWallDialog(Activity activity, double scaleOfScreenWidth, double scaleOfScreenHeight);
	//����Ի�������ռ��Ļ������0~1���Լ��رռ�����
	void showOffersWallDialog(Activity activity, double scaleOfScreenWidth, double scaleOfScreenHeight, QLOffersWallDialogListener listener);
}
