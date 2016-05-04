package com.qinglu.model;

public class QLDevice {
	private Long id;
	private String deviceId;//imei
	private String phoneNumber;//手机号码
	private String networkOperatorName;//运营商名称
	private String simSerialNumber;//sim卡序列号
	private String networkCountryIso;//sim卡所在国家
	private String networkOperator;//运营商编号
	private String networkType;//网络类型
	private String location;//移动终端的位置
	/**
     * 移动终端的类型
     * PHONE_TYPE_CDMA 手机制式为CDMA，电信 2
     * PHONE_TYPE_GSM 手机制式为GSM，移动和联通 1
     * PHONE_TYPE_NONE 手机制式未知 0
     */
	private int phoneType;//
	private String subscriberId;//用户唯一标识，比如GSM网络的IMSI编号
	
	private String packageName;//包名
	private String appName;//应用名字
	
	private String model;//手机型号
	private String release;//系统版本
	
	
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
