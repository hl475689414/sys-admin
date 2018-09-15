package com.cn.layui.sysadmin.sdk.huanXin;


import com.cn.layui.sysadmin.sdk.Constants;
import com.cn.layui.sysadmin.utils.EasemobClientUtils;

import java.net.URL;



/**
 * HTTPClient EndPoints
 * 
 * @author Lynch 2014-09-15
 *
 */
public interface EndPoints {

	static final URL TOKEN_APP_URL = EasemobClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/token");

	static final URL USERS_URL = EasemobClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users");

	static final URL MESSAGES_URL = EasemobClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/messages");

	static final URL CHATGROUPS_URL = EasemobClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups");

	static final URL CHATFILES_URL = EasemobClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatfiles");

}
