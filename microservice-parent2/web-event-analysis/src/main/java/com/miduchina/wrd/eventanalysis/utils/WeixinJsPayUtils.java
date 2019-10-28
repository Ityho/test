package com.miduchina.wrd.eventanalysis.utils;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class WeixinJsPayUtils {
    public static void main(String[] args) throws Exception {
        String jsapi_ticket = getJsapiTicket();

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    };

    public static Map<String, String> sign(String jsapi_ticket, String url) throws Exception {


        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";


        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        log.info(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("appId", WeiXinPayConfig.APPID);
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    //生成随机字符串
    private static String create_nonce_str() {
        return RandomStringUtils.randomAlphanumeric(24);
        //return UUID.randomUUID().toString().substring(0, 24);
    }

    //生成1970年到现在的秒数.
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String getJsapiTicket() throws Exception{
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinconnectconfig.properties"));
        String access_tokenURL = props.getProperty("accessTokenURL1").trim();
        if (StringUtils.isBlank(access_tokenURL)){
            access_tokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WeiXinPayConfig.APP_ID+"&secret="+WeiXinPayConfig.APP_SECRET;
        }

        log.info("weixin APP_ID------>>"+WeiXinPayConfig.APP_ID);
        log.info("weixin APP_SECRET------>>{}"+WeiXinPayConfig.APP_SECRET);

//        access_tokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WeiXinPayConfig.APP_ID+"&secret="+WeiXinPayConfig.APP_SECRET;
//        log.info("access_tokenURL------>>"+access_tokenURL);

        //获取access_token
        String access_tokenJson = HttpRequest.sendGet(access_tokenURL, null);
        log.info("access_token json------>>"+access_tokenJson);

        JSONObject access_token = JSONObject.fromObject(access_tokenJson);
        log.info("access_token------>>"+access_token.getString("access_token"));

        String jsapi_ticketURL = props.getProperty("jsapiTicketURL1").trim();
        log.info("jsapi_ticketURL------>>"+jsapi_ticketURL);
        //获取jsapi_ticket
        String jsapi_ticketJson = HttpRequest.sendGet(jsapi_ticketURL.replaceFirst("ACCESS_TOKEN", access_token.getString("access_token")), null);
        log.info("jsapi_ticketJson------>>"+access_token.getString("access_token"));

        JSONObject jsapi_ticket = JSONObject.fromObject(jsapi_ticketJson);
        log.info("jsapi_ticket------>>"+jsapi_ticket.getString("ticket"));

        return jsapi_ticket.getString("ticket");
    }

    public static String getOpenId(String code){
        //获取openID
        String openParam = "appid="+WeiXinPayConfig.APPID+"&secret="+WeiXinPayConfig.APPSECRET+"&code="+code+"&grant_type=authorization_code";
        String open_idStr = HttpRequest.sendGet(WeiXinPayConfig.OPENID_URL,openParam);
        System.out.println("open_idStr------------>>>"+open_idStr);
        JSONObject open_id = JSONObject.fromObject(open_idStr);
        return open_id.getString("openid");
    }

    public static String getOut(HttpURLConnection conn) throws IOException{
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        // 获取响应内容体
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        String line = "";
        StringBuffer strBuf = new StringBuffer();
        while ((line = in.readLine()) != null) {
            strBuf.append(line).append("\n");
        }
        in.close();
        return  strBuf.toString().trim();
    }
}
