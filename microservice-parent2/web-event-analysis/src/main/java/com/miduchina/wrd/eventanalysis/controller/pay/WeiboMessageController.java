package com.miduchina.wrd.eventanalysis.controller.pay;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.weixin.MsgType;
import com.miduchina.wrd.eventanalysis.weixin.SHA1;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @auther yho
 * @vreate 2019-08 16:00
 */
@Controller
@Slf4j
public class WeiboMessageController {
    @Autowired
    private APIInfoClient apiInfoClient;

    private static String app_secret = "3eb9f89fbf2a52d847f8ee3bc0a13977";

    @RequestMapping(value = "/wbMessage/acceptWeiboMessage")
    public void acceptWeiboMessage() throws IOException {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        String returnContent = ""; //返回的响应结果。默认为空字符串。
        if(request.getMethod().toLowerCase().equals("get")){
            //认证
            access(request,response);
        }else{

            StringBuffer getString = new StringBuffer() ;
            InputStream is = request.getInputStream();

            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1;) {
                getString.append(new String(b, 0, n, "UTF-8"));
            }
            String str =getString.toString();
            System.out.println("str   "+str);
            if(StringUtils.isNotBlank(str)){

                JSONObject message = JSONObject.parseObject(str);
                String text = message.getString("text");//消息内容
                String type = message.getString("type");
                long servername = message.getLong("sender_id");// 服务端
                long custermname = message.getLong("receiver_id");// 客户端
                String create = message.getString("created_at");
                String display = text+ "今天有多热？快戳这里，立即查看";
                if (type.equals(MsgType.Text)) {
                    // 文本消息
                    System.out.println("开发者id：" + custermname);
                    System.out.println("发送方id：" + servername);
                    System.out.println("消息创建时间：" + create);
                    System.out.println("消息内容：" + text);


                    //returnContent = generateReplyMsg(textMsg(), "text", custermname, servername);//回复text类型消息
                    returnContent = generateReplyMsg(articleMsg(display,text), "articles", custermname, servername);//回复text类型消息
                    System.out.println("returnContent   "+returnContent);
                    response.getWriter().write(returnContent);

                }
            }

        }
    }

    /**
     * 生成回复的消息。（发送被动响应消息）
     * @param data  消息的内容。
     * @param type  消息的类型
     * @param custermname 回复消息的发送方uid。蓝v用户自己
     * @param servername 回复消息的接收方  蓝v用户的粉丝uid
     * @return
     */
    private static String generateReplyMsg(String data, String type, long custermname, long servername){
        JSONObject jo = new JSONObject();
        jo.put("result",true);
        jo.put("sender_id", custermname);
        jo.put("receiver_id", servername);
        jo.put("type", type);
        try {
            jo.put("data", URLEncoder.encode(data, "utf-8")); //data字段的内容需要进行utf8的urlencode
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jo.toString();
    }


    /**
     * 生成文本类型的消息data字段
     * @return
     * @throws
     */
    private static String articleMsg(String display,String text) throws UnsupportedEncodingException {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        String url= SysConfig.cfgMap.get("SYSTEM_H5_URL")+ "/view/heatsearch/searchHeatResult.shtml?isWx=1&searchKeyword="+text;
        String shortUrl = getShortUrl(URLEncoder.encode(url, "utf-8"));
        System.out.println("shortUrl = "+shortUrl);
        for(int i = 0;i < 1;i++){
            JSONObject temp = new JSONObject();
            temp.put("display_name", display);
            temp.put("summary", "感谢您关注微热点，微热点是新浪微博旗下的网络信息产品，应用旨在对全网信息进行监测和预警，对信息的热度、溯源和传播进行多维度的分析。欢迎访问微热点产品："+SysConfig.cfgMap.get("SYSTEM_H5_URL")+" 获取专业的信息监测分析服务，有问题可以随时私信联系我哦。");
            temp.put("image", "http://cdn.files.51wyq.cn/h5/images/wbmessage.jpg");
            temp.put("url", shortUrl);
            ja.add(temp);
        }
        jo.put("articles", ja);
        System.out.println("articles   "+jo.toString());
        return jo.toString();
    }


    /**
     * 验证URL真实性
     * @param request
     * @param response
     * @return
     */
    private String access(HttpServletRequest request, HttpServletResponse response) {
        // 验证URL真实性
        System.out.println("进入验证access");
        String signature = request.getParameter("signature");// 微博加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        List<String> params = new ArrayList<String>();
        params.add(app_secret);
        params.add(timestamp);
        params.add(nonce);
        // 1. 将Appkey、timestamp、nonce三个参数进行字典序排序
        Collections.sort(params, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
        System.out.println(temp);
        if (temp.equals(signature)) {
            try {
                response.getWriter().write(echostr);
                System.out.println("成功返回 echostr：" + echostr);
                return echostr;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("失败 认证");
        return null;
    }


    /**
     * 调用微博接口 将长连接转换成短链接
     * @param longUrl
     * @return
     */
    private static String getShortUrl(String longUrl){
        String source = "1180242279";
        String url = "http://api.t.sina.com.cn/short_url/shorten.json";
        StringBuilder params = new StringBuilder();
        String shortUrl="";
        params.append("url_long="+longUrl);
        params.append("&source="+source);
        String result = sendGet(url, params.toString());
        System.out.println("result   "+result);
        try{
            JSONArray getShort = JSONArray.parseArray(result);
            JSONObject getJsonObj = getShort.getJSONObject(0);
            shortUrl = getJsonObj.getString("url_short");
            System.out.println("shortUrl   = "+shortUrl);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return shortUrl;
    }


    /**
     * 发送GET请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    private static  String sendGet(String url, String params) {
        try {
            @SuppressWarnings("deprecation")
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url + "?" + params);
            System.out.println("httpGet   "+httpGet);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity resultEntity = httpResponse.getEntity();
            // System.out.println("resultEntity "+EntityUtils.toString(resultEntity, "GBK"));
            return EntityUtils.toString(resultEntity, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
