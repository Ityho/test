package com.miduchina.wrd.eventanalysis.config;

import com.miduchina.wrd.eventanalysis.weibo4j.BASE64Encoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 微博支付
 *
 * @author liym
 */
public class SinaPayConfig {
	// 微博支付统一收银台接入地址
	public static String WBPAY_CASHIER_URL = "https://pay.sc.weibo.com/api/merchant/pay/cashier";

	// 商户微博ID
	public static String SELLER_ID = "3960037780";

	// 商户APPKEY
	public static String APP_KEY = "1122263428";

	// 私钥
	public static String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL2rEJ1QTe1ffsgOpmSxlc5JxmXLcdtz3lfeborDURHQYnM3ZCPJ/jCDjYa90CE8PjJwKP7sm+t0OTmxkAjpqOcespEsq0EXO4jw+kRrSHebX0YejpnPCiui1iNUpas1dD1kZOfv7txf9LJRyZw3qjx/ekls8lOFp7Vc0zpAJzw1AgMBAAECgYBIKdj1ccNkiEvthB1s/GA6D7nLqz9Ttt8m4Xt/kla7B2Ud3zpbn5P2E2d6l6ejrY8gk6oVbCLaz+qh99wMeBKJzWEWP5F6/0X4kRVkdiGA1TfJoDQ5ZmnUpL1iPpJQcLa71ViLpTpI+tXCZ5rFmWzlZfbIem8INXd+BGcegZ1hIQJBAOA31RXTFMCpyOm+esfbYnvFAKFgIT4SnVebxvX+6uvIW3YhZMdExWfIckjJy+GwB6HMdFR9ilJWih8wPqL5q20CQQDYjYbPAts2mWPiVeFG9vGs6DJ+5cluM52LV9tw6Ps/xcT/lGn6Gw1RR7iqQaRKKmPRJDvPw0Spn4zWR8uHC07pAkEAs4ehA6+Wz0ljSN2uu/YKniW4gn5RP71x5KMjpPavLYmoyiak5fVHxvi1oViK+jOTeNcXUkuPBKysPZrInIAi5QJAPw4+wcp4IFbb0czl8u73Ajz+bxrj41XeOvPTLvZ4KmKg8Ta7ARudbhxvNgw1FW9wbzaYv++A789IiCCRr/nn+QJBAIHvZkZ0f6K7lOwo8Yf9dSuygOZjSlcfEg2uVN876sPkcuFhPoVOTeMAzr40ESB0ud9KDyLtXjitfbrqf3QumBk=";

	// 微博公钥
	public static String WEIBO_RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtZIYGv5q/MTxFg7BscFFssLujaRHryHNYQpfz4rND1pS11fcggT9AdP8K7XkOERoG/2IG1DBt3fvrpmD4fHH0iXMxilIJ1gAX6msHBdlhXjmQ9iq6emxNdrg5x0wHEmoF8pUmQvtXbqlQIUDqmTcbYSZ2gPndrlCOaFfX87qVqwIDAQAB";

	// 加密方式
	public static String SIGN_TYPE = "RSA";
	public static String SIGN_ALGORITHMS = "SHA1WithRSA";

	public static String PAY_STATUS_SUCCESS = "PAY_STATUS_SUCCESS"; // 支付成功
	public static String PAY_STATUS_CLOSED = "PAY_STATUS_CLOSED"; // 支付失败

	/**
	 * 生成签名
	 *
	 * @param params
	 * @return
	 */
	public static String generateRsaSign(Map<String, String> params) {
		Map<String, String> newParams = new TreeMap<String, String>();
		for (String key : params.keySet()) {
			if ("sign" == key || "sign_type" == key || params.get(key) == null || "".equals(params.get(key))) {
				continue;
			}
			newParams.put(key, params.get(key));
		}
		List<String> pairs = new ArrayList<String>();
		for (String key : newParams.keySet()) {
			pairs.add(key + "=" + newParams.get(key));
		}
		String sign_data = StringUtils.join(pairs.toArray(), "&");
		System.out.println(sign_data);
		try {
			//byte[] buffer = Base64.decodeBase64(RSA_PRIVATE_KEY);
			byte[] buffer = Base64.decodeBase64(RSA_PRIVATE_KEY.getBytes());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE);
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(rsaPrivateKey);
			signature.update(sign_data.getBytes());

			byte[] signed = signature.sign();
			//return Base64.encodeBase64String(signed);
			return BASE64Encoder.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 构建请求表单
	 *
	 * @param sPara
	 * @param strMethod
	 * @return
	 */
	public static String buildRequest(Map<String, String> sPara, String strMethod) {
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form  id=\"sinaPayForm\" name=\"sinaPayForm\" action=\"" + SinaPayConfig.WBPAY_CASHIER_URL + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sPara.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		sbHtml.append("<input type=\"submit\" value=\"提交\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['sinaPayForm'].submit();</script>");

		return sbHtml.toString();
	}

	/**
	 * 验证RSA签名
	 *
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	public static boolean checkRSA(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE);
			byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes());
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes());

			boolean bverify = signature.verify(Base64.decodeBase64(sign.getBytes()));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
