package com.project.utils;

public class WeixinConfig {

	// 微信分配的公众账号ID（企业号corpid即为此appId）
	public final static String appid="wxeabd913bc8960c0a";
	
	// MD5密钥，安全检验码，由数字和字母组成的32位字符串组成
	public final static String key = "23eoiu5g1g9ji9j3hbyda5u43ih0ww2r";

	// APPSECRET：公众帐号secert（仅JSAPI支付的时候需要配置）
	public final static String APPSECRET = "571189eb9a7f287b6968777ee31749c6";
	
	// 微信登录验证请求接口
	public final static String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize";

	// 微信连接获取openId接口
	public final static String openIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	// 微信连接获取用户信息接口
	public final static String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo";

	// 获得微信登录验证路径
	public static String getAuthorizeUrl(String redirect_uri) {
		return authorizeUrl + "?appid=" + appid + "&redirect_uri=" + redirect_uri
				+ "&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
	}

	// 获得微信登录连接路径以获得openId
	public static String getOpenIdUrl(String code) {
		return openIdUrl+ "?appid=" + appid + "&secret=" + APPSECRET + "&code=" + code
				+ "&grant_type=authorization_code";
	}

	// 利用openId获得微信用户信息路径
	public static String getUserInfoUrl(String access_token, String openid) {
		return userInfoUrl + "?access_token=" + access_token + "&openid="+ openid + "&lang=zh_CN";
	}
}
