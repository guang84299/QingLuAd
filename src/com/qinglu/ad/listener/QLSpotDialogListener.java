package com.qinglu.ad.listener;

public interface QLSpotDialogListener {
	 /**
     * չʾ�ɹ�
     */
    void onShowSuccess();

    /**
     * չʾʧ��
     */
    void onShowFailed();

    /**
     * �������ر�
     */
    void onSpotClosed();

    /**
     * ���������
     *
     * @param isWebPath �Ƿ�����ҳ���
     */
    void onSpotClick(boolean isWebPath);
}
