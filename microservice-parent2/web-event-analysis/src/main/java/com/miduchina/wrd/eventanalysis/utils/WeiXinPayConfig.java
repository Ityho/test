package com.miduchina.wrd.eventanalysis.utils;


import com.miduchina.wrd.common.redis.util.SysConfig;

import java.io.IOException;
import java.util.Properties;

public class WeiXinPayConfig {

	private static Properties props = new Properties();
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinconnectconfig.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String APPID = props.getProperty("AppID1").trim();
	//受理商ID，身份标识
	public static String MCHID = props.getProperty("MCHID").trim();
	//商户支付密钥Key。审核通过后，在微信发送的邮件中查看
	public static String KEY = props.getProperty("KEY").trim();
	//JSAPI接口中获取openid，审核后在公众平台开启开发模式后可查看
	public static String APPSECRET = props.getProperty("AppSecret1").trim();
	//重定向地址
	public static String REDIRECT_URL = "http://XXXXXXXXXXXXXXXXXXX/callWeiXinPay";
	//异步回调地址
	public static String NOTIFY_URL =SysConfig.cfgMap.get("H5_WEIXIN_NOTIFY_URL");

	public static String OPENID_URL = props.getProperty("openIdURL").trim();

	// 公众账号ID
	public static  String APP_ID = "wxffe32990bd5bd9c9";//"wx30ce0a6d80b3a132";//"wxffe32990bd5bd9c9";

	// 商户号
	public static  String MCH_ID = "1236145602";

	// KEY
//	public static final String KEY = "";//"aQgg87ytREW79611AemgYY76mJ7629sQ";

	// 二维码规则
	public static  String QR_CODE_URL = "weixin://wxpay/bizpayurl?";

	// 统一下单API
	public static  String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// 支付回调地址
//	public static final String NOTIFY_URL = "https://www.wyq.cn/view/user/doWeixinNotify.action";

	// 交易类型
	public static  String TRADE_TYPE = "NATIVE";

	// 返回状态
	public static  String RETURN_CODE_SUCCESS = "SUCCESS"; // 成功
	public static  String RETURN_CODE_FAIL = "FAIL"; // 失败

	// 返回成功标识
	public static  String RETURN_MSG_OK = "OK";


	public static  String APP_SECRET = "8872f8cba9d23726753b8cd86df04f35";


	public static  String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+APP_SECRET;



}
