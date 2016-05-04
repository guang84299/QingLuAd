package com.qinglu.ad.listener;

import android.view.View;

public interface QLAdViewListener {
	/**
     * ������ɹ�
     *
     * @param adView �����ʵ��
     */
    void onReceivedAd(View adView);

    /**
     * �л������
     *
     * @param adView �����ʵ��
     */
    void onSwitchedAd(View adView);

    /**
     * ������ʧ��
     *
     * @param adView �����ʵ��
     */
    void onFailedToReceivedAd(View adView);
}
