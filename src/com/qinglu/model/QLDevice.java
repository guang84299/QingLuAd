package com.qinglu.model;

public class QLDevice {
	private Long id;
	private String deviceId;//imei
	private String phoneNumber;//�ֻ�����
	private String networkOperatorName;//��Ӫ������
	private String simSerialNumber;//sim�����к�
	private String networkCountryIso;//sim�����ڹ���
	private String networkOperator;//��Ӫ�̱��
	private String networkType;//��������
	private String location;//�ƶ��ն˵�λ��
	/**
     * �ƶ��ն˵�����
     * PHONE_TYPE_CDMA �ֻ���ʽΪCDMA������ 2
     * PHONE_TYPE_GSM �ֻ���ʽΪGSM���ƶ�����ͨ 1
     * PHONE_TYPE_NONE �ֻ���ʽδ֪ 0
     */
	private int phoneType;//
	private String subscriberId;//�û�Ψһ��ʶ������GSM�����IMSI���
	
	private String packageName;//����
	private String appName;//Ӧ������
	
	private String model;//�ֻ��ͺ�
	private String release;//ϵͳ�汾
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getNetworkOperatorName() {
		return networkOperatorName;
	}
	public void setNetworkOperatorName(String networkOperatorName) {
		this.networkOperatorName = networkOperatorName;
	}
	public String getSimSerialNumber() {
		return simSerialNumber;
	}
	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}
	public String getNetworkCountryIso() {
		return networkCountryIso;
	}
	public void setNetworkCountryIso(String networkCountryIso) {
		this.networkCountryIso = networkCountryIso;
	}
	public String getNetworkOperator() {
		return networkOperator;
	}
	public void setNetworkOperator(String networkOperator) {
		this.networkOperator = networkOperator;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	@Override
	public String toString() {
		return "QLDevice [deviceId=" + deviceId + ", phoneNumber="
				+ phoneNumber + ", networkOperatorName=" + networkOperatorName
				+ ", simSerialNumber=" + simSerialNumber 
				+ ", networkCountryIso=" + networkCountryIso
				+ ", networkOperator=" + networkOperator + ", networkType="
				+ networkType + ", location=" + location + ", phoneType="
				+ phoneType + ", subscriberId=" + subscriberId
				+ ", packageName=" + packageName + ", appName=" + appName
				+ ", model=" + model + ", release=" + release + "]";
	}
	
	
	
}
