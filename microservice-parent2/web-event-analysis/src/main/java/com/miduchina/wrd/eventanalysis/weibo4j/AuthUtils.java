package com.miduchina.wrd.eventanalysis.weibo4j;

import com.miduchina.wrd.eventanalysis.config.WeiboConfig;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AuthUtils {

	/**
	 * 校验signed_request
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 */
	public static Map<String, String> parseSignedRequest(String signed_request) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		String[] t = signed_request.split("\\.", 2);
		// 为了和 url encode/decode 不冲突，base64url
		// 编码方式会将'+'，'/'转换成'-'，'_'，并且去掉结尾的'='。
		// 因此解码之前需要还原到默认的base64编码，结尾的'='可以用以下算法还原
		int padding = (4 - t[0].length() % 4);
		for (int i = 0; i < padding; i++)
			t[0] += "=";
		String part1 = t[0].replace("-", "+").replace("_", "/");

		SecretKey key = new SecretKeySpec(WeiboConfig.getValue("AppSecret").getBytes(), "hmacSHA256");
		Mac m;
		m = Mac.getInstance("hmacSHA256");
		m.init(key);
		m.update(t[1].getBytes());
		String part1Expect = BASE64Encoder.encode(m.doFinal());

		sun.misc.BASE64Decoder decode = new sun.misc.BASE64Decoder();
		String s = new String(decode.decodeBuffer(t[1]));
		System.out.println("sinaAuth login return json == >>"+s);
		if (part1.equals(part1Expect)) {
			return ts(s);
		} else {
			return null;
		}
	}

	/**
	 * 处理解析后的json解析
	 *
	 * @param json
	 * @return
	 */
	public static Map<String, String> ts(String json) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			String access_token = jsonObject.getString("oauth_token");
			String expires = jsonObject.getString("expires");
			String uid = jsonObject.getString("user_id");
			String referer = jsonObject.getString("referer");
			System.out.println("sinaAuth.ts() referer -- >>"+jsonObject.getString("referer"));

			if (access_token == null)
				return null;

			map.put("access_token", access_token);
			map.put("expires", expires);
			map.put("uid", uid);
			map.put("referer", referer);
			System.out.println("sinaAuth.ts() map.get() -- >>"+map.get("referer"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

}
