package com.miduchina.wrd.eventanalysis.weixin;

import com.miduchina.wrd.eventanalysis.utils.HttpRequest;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

public class AccessTokenUtil {
	/**
	 * 服务号获取accessToken
	 * @param appID 微信公众号凭证
	 * @param appScret 微信公众号凭证秘钥
	 * @return
	 * @throws IOException
	 */
	public static AccessToken getServerNumberAccessToken() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("weixinconnectconfig.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 访问微信服务器
		String access_tokenURL = props.getProperty("accessTokenURL1").trim();
		AccessToken token = new AccessToken();
		// 获取access_token
		String access_tokenJson = HttpRequest.sendGet(access_tokenURL, null);
		System.out.println("access_token json------>>" + access_tokenJson);
		JSONObject access_token = JSONObject.fromObject(access_tokenJson);
		token.setAccess_token(access_token.getString("access_token"));
		token.setExpires_in(new Integer(access_token.getString("expires_in")));
		return token;
	}
	/**
	 * 订阅号获取accessToken
	 * @param appID 微信公众号凭证
	 * @param appScret 微信公众号凭证秘钥
	 * @return
	 * @throws IOException
	 */
	public static AccessToken getSubscriptionNumberAccessToken() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("weixinconnectconfig.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 访问微信服务器
		String access_tokenURL = props.getProperty("accessTokenURL2").trim();
		AccessToken token = new AccessToken();
		// 获取access_token
		String access_tokenJson = HttpRequest.sendGet(access_tokenURL, null);
		System.out.println("access_token json------>>" + access_tokenJson);
		JSONObject access_token = JSONObject.fromObject(access_tokenJson);
		token.setAccess_token(access_token.getString("access_token"));
		token.setExpires_in(new Integer(access_token.getString("expires_in")));
		return token;
	}
}
