package com.lieying.socialappstore.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 网络工具类
 * 
 * @author David_kun
 * 
 */
public class NetUtil {
	private static final String TAG = "NetUtil";

	/**
	 * 检测是否接入Internet
	 * @param path  测试路径
	 * @return   网络状态
	 */
	public static boolean isAccessInternet(String path)
	{
		try {
			URL url=new URL(path);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			if(conn.getResponseCode()!=0)
			{
				return true;
			}
			conn.connect();
		} catch (Exception e) {
			return false;	
		}
		return false;

	}

	/**
	 *@Title: detect
	 *@Description: TODO 判断用户是否联网
	 *@param act
	 *@return
	 */
	public static boolean canNetworkUseful(Context act) {
		ConnectivityManager manager = (ConnectivityManager) act
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);

		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();

		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	private static final int NETWORK_TYPE_UNAVAILABLE = -1;
	// private static final int NETWORK_TYPE_MOBILE = -100;
	private static final int NETWORK_TYPE_WIFI = -101;

	private static final int NETWORK_CLASS_WIFI = -101;
	private static final int NETWORK_CLASS_UNAVAILABLE = -1;
	/** Unknown network class. */
	private static final int NETWORK_CLASS_UNKNOWN = 0;
	/** Class of broadly defined "2G" networks. */
	private static final int NETWORK_CLASS_2_G = 1;
	/** Class of broadly defined "3G" networks. */
	private static final int NETWORK_CLASS_3_G = 2;
	/** Class of broadly defined "4G" networks. */
	private static final int NETWORK_CLASS_4_G = 3;

	// 适配低版本手机
	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	public static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B */
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0 */
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A */
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT */
	public static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B */
	public static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;

	/**
	 * 获取网络类型
	 *
	 * @return
	 */
	public static String getCurrentNetworkType(Context context) {
		int networkClass = getNetworkClass(context);
		String type = "未知";
		switch (networkClass) {
			case NETWORK_CLASS_UNAVAILABLE:
				type = "无";
				break;
			case NETWORK_CLASS_WIFI:
				type = "Wi-Fi";
				break;
			case NETWORK_CLASS_2_G:
				type = "2G";
				break;
			case NETWORK_CLASS_3_G:
				type = "3G";
				break;
			case NETWORK_CLASS_4_G:
				type = "4G";
				break;
			case NETWORK_CLASS_UNKNOWN:
				type = "未知";
				break;
		}
		return type;
	}

	private static int getNetworkClassByType(int networkType) {
		switch (networkType) {
			case NETWORK_TYPE_UNAVAILABLE:
				return NETWORK_CLASS_UNAVAILABLE;
			case NETWORK_TYPE_WIFI:
				return NETWORK_CLASS_WIFI;
			case NETWORK_TYPE_GPRS:
			case NETWORK_TYPE_EDGE:
			case NETWORK_TYPE_CDMA:
			case NETWORK_TYPE_1xRTT:
			case NETWORK_TYPE_IDEN:
				return NETWORK_CLASS_2_G;
			case NETWORK_TYPE_UMTS:
			case NETWORK_TYPE_EVDO_0:
			case NETWORK_TYPE_EVDO_A:
			case NETWORK_TYPE_HSDPA:
			case NETWORK_TYPE_HSUPA:
			case NETWORK_TYPE_HSPA:
			case NETWORK_TYPE_EVDO_B:
			case NETWORK_TYPE_EHRPD:
			case NETWORK_TYPE_HSPAP:
				return NETWORK_CLASS_3_G;
			case NETWORK_TYPE_LTE:
				return NETWORK_CLASS_4_G;
			default:
				return NETWORK_CLASS_UNKNOWN;
		}
	}

	private static int getNetworkClass(Context context) {
		int networkType = NETWORK_TYPE_UNKNOWN;
		try {
			final NetworkInfo network = ((ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE))
					.getActiveNetworkInfo();
			if (network != null && network.isAvailable()
					&& network.isConnected()) {
				int type = network.getType();
				if (type == ConnectivityManager.TYPE_WIFI) {
					networkType = NETWORK_TYPE_WIFI;
				} else if (type == ConnectivityManager.TYPE_MOBILE) {
					TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
									Context.TELEPHONY_SERVICE);
					networkType = telephonyManager.getNetworkType();
				}
			} else {
				networkType = NETWORK_TYPE_UNAVAILABLE;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return getNetworkClassByType(networkType);

	}

	/**
	 * 时间：2017/2/9 16:42
	 * 描述：获取wifi SSID
	 * 参数：
	 * 返回值：
	 */
	public static String getConnectedWifiSSID(Context context) {
		String ssid = "";
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			ssid = wifiInfo.getSSID();
		}

		return ssid;
	}

}
