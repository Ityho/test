package com.miduchina.wrd.eventanalysis.utils;

import com.miduchina.wrd.util.MD5Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * 支付工具类
 *
 * @author liym
 */
public class PayUtil {
    /**
     * 获取一定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成签名
     *
     * @param params
     * @return
     */
    public static String generateSign(Map<String, String> params, String key) {
        // 去除请求参数中的空值
        Map<String, String> sPara = PayUtil.paraFilter(params);

        // 排序参数
        String param = PayUtil.createLinkString(sPara);
        param = param + "&key=" + key;

        // MD5加密
        String sign = MD5Utils.MD5Encode(param);

        return sign.toUpperCase();
    }

    /**
     * 去除请求参数中的空值
     *
     * @param params
     * @return
     */
    public static Map<String, String> paraFilter(Map<String, String> params) {
        Map<String, String> result = new HashMap<String, String>();

        if (params == null || params.size() <= 0)
            return result;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null || "".equals(entry.getValue()) || "sign".equalsIgnoreCase(entry.getKey()))
                continue;

            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @return
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) { // 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param postDataXML
     * @return
     */
    public static String sendPost(String url, String postDataXML) {
        String result = null;
        HttpPost httpPost = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);

            StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(postEntity);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resultEntity = response.getEntity();

            result = EntityUtils.toString(resultEntity, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpPost != null)
                httpPost.abort();
        }

        return result;
    }
}
