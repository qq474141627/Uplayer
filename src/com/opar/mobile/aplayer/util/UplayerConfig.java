package com.opar.mobile.aplayer.util;

import com.youku.login.util.Youku;
import com.youku.login.util.YoukuUtil;


/**
 * 上传配置类
 */
public class UplayerConfig {

	// 上传设置 flag : 0任何网络、 1 仅wifi
	public final static int UPLOAD_ANY = 0;
	public final static int UPLOAD_WIFI = 1;
	private static String client_id = "426249cfc92d3b67";
	private static String client_secret = "be6f89bac1ba5edff6eb5bd1b3675f1d";
	private static String redirect_uri= "https://www.baidu.com/";
	// 正常解析xml
	public  static final int EXEC_NORMOL = 0x101;
	// http 状态码 非 200
	public  static final int HTTP_STATE_NOTOK = 0x102;
	// http IOEXCEPTION
	public  static final int HTTP_IOEXCEPTION = 0x103;
	// http 无网络
	public  static final int NONETWORK = 0x104;
	// 没有数据返回,可能相应失败
	public  static final int NO_DATA_RETURN = 0x105;
	// 数据为空
	public  static final int DATA_RETURN_ZERO = 0x106;
	// 添加一页数据
	public  static final int ADDPAGE_NORMOL = 0x107;
	// 删除数据数据
	public  static final int DELETE_NORMOL = 0x108;	
	//获取评论成功
	public  static final int EXEC_NORMOL_COMMENT = 0x109;
	//更新热门搜索词汇
	public  static final int EXEC_NORMOL_HOTKEY = 0x10e;
	

	/**
	 * Android Phone Key
	 */
	
    public final static String getClientId() {
		return client_id;
	}
    
    public final static String getClientSecret() {
		return client_secret;
	}
    
    public final static String getRedirectUrl() {
		return redirect_uri;
	}

	public static String getUserID() {
		return Youku.getPreference("uid");
	}

	public static String getUserName() {
		return Youku.getPreference("userName");
	}

	public static String getUserPassword() {
		return Youku.getPreference("loginPassword");
	}

	public static void saveAccessToken(String access_token) {
		Youku.savePreference("uploadAccessToken", access_token);
	}

	public static String getAccessToken() {
		return Youku.getPreference("uploadAccessToken");
	}

	public static void saveRefreshToken(String refresh_token) {
		Youku.savePreference("refresh_token", refresh_token);
	}

	public static String getRefreshToken() {
		return Youku.getPreference("refresh_token");
	}
	
	public static void saveExpires_in(String expires_in) {
		Youku.savePreference("expires_in", expires_in);
	}
	
	public static int getExpires_in() {
		try {
			return Integer.parseInt(Youku.getPreference("expires_in"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	public static void saveToken_type(String token_type) {
		Youku.savePreference("token_type", token_type);
	}
	
	public static String getToken_type() {
		return Youku.getPreference("token_type");
	}
	
	public void saveUconfig(String access_token,String expires_in,String refresh_token,String token_type){
		saveAccessToken(access_token);
		saveExpires_in(expires_in);
		saveRefreshToken(refresh_token);
		saveToken_type(token_type);
	}
	
	public static void showTips(String text) {
		YoukuUtil.showTips(text);
	}

	public static void showTips(int textId) {
		YoukuUtil.showTips(textId);
	}


	public static boolean hasInternet() {
		return YoukuUtil.hasInternet();
	}

	public static boolean isWifi() {
		return YoukuUtil.isWifi();
	}

	/**
	 * 当前网络是否满足上传设置
	 * 
	 * @Title: uploadSettingIsOk
	 * @return boolean
	 * @date 2012-7-25 下午3:19:53
	 */
	public static boolean uploadSettingIsOk() {
		// 上传设置 (0任何网络上传、1仅WIFI)
		int state = Youku.getPreferenceInt("uploadModeState");
		if (state != UPLOAD_ANY
				&& !(state == UPLOAD_WIFI && YoukuUtil.isWifi()))
			return false;
		return true;
	}

	
}
