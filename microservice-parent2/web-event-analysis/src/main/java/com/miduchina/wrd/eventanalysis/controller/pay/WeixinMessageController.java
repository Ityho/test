package com.miduchina.wrd.eventanalysis.controller.pay;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.utils.HttpRequestUtils;
import com.miduchina.wrd.eventanalysis.weixin.*;
import com.miduchina.wrd.po.h5.WeiXinMaterial;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @auther yho
 * @vreate 2019-08 16:00
 */
@Controller
@Slf4j
public class WeixinMessageController {
    @Autowired
    private APIInfoClient apiInfoClient;

    /**
     * 服务号
     */
    @RequestMapping(value = "/wxMessage/doAcceptMessageServer")
    public void doAcceptMessageServer() throws IOException {
        log.info("doAcceptMessageServer:{}","doAcceptMessageServer Start!!");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if (request.getMethod().toLowerCase().equals("get")) {
            // 认证
            log.info("access:{}","认证access Start!!");
            access(request, response);
        } else {
            log.info("request.getMethod():{}","!get");
            // 处理接收消息
            ServletInputStream in = request.getInputStream();
            // 将POST流转换为XStream对象
            XStream xs = SerializeXmlUtil.createXstream();
            xs.processAnnotations(InputMessage.class);
            xs.processAnnotations(OutputMessage.class);
            // 将指定节点下的xml节点数据映射为对象
            xs.alias("xml", InputMessage.class);
            // 将流转换为字符串
            StringBuilder xmlMsg = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1;) {
                xmlMsg.append(new String(b, 0, n, "UTF-8"));
            }
            System.out.println(xmlMsg.toString());
            if (StringUtils.isNotBlank(xmlMsg.toString())) {
                // 将xml内容转换为InputMessage对象
                InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());
                String servername = inputMsg.getToUserName();// 服务端
                String custermname = inputMsg.getFromUserName();// 客户端
                long createTime = inputMsg.getCreateTime();// 接收时间
                Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
                // 取得消息类型
                String msgType = inputMsg.getMsgType();
                String eventType = inputMsg.getEvent();
                if (msgType.equals(MsgType.Text) && StringUtils.isNotBlank(inputMsg.getContent())
                        && inputMsg.getContent().length() > 20) {
                    String text = "输入内容不能超过20个字！";
                    String title = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                    StringBuffer str = new StringBuffer();
                    str.append("<xml>");
                    str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                    str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
                    str.append("<CreateTime>" + returnTime + "</CreateTime>");
                    str.append("<MsgType><![CDATA[text]]></MsgType>");
                    str.append("<Content><![CDATA[" + title + "]]></Content>");
                    str.append("</xml>");
                    System.out.println(str.toString());
                    response.getWriter().write(str.toString());
                } else if (msgType.equals(MsgType.Text) && StringUtils.isBlank(inputMsg.getContent())) {
                    String text = "输入内容不能空！";
                    String title = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                    StringBuffer str = new StringBuffer();
                    str.append("<xml>");
                    str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                    str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
                    str.append("<CreateTime>" + returnTime + "</CreateTime>");
                    str.append("<MsgType><![CDATA[text]]></MsgType>");
                    str.append("<Content><![CDATA[" + title + "]]></Content>");
                    str.append("</xml>");
                    System.out.println(str.toString());
                    response.getWriter().write(str.toString());
                }
                // 根据消息类型获取对应的消息内容
                else if (msgType.equals(MsgType.Text)) {
                    // 文本消息
                    System.out.println("开发者微信号：" + inputMsg.getToUserName());
                    System.out.println("发送方帐号：" + inputMsg.getFromUserName());
                    System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
                    System.out.println("消息内容：" + inputMsg.getContent());
                    System.out.println("消息Id：" + inputMsg.getMsgId());
                    Properties props = new Properties();
                    props.load(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("weixinconnectconfig.properties"));
                    String text = inputMsg.getContent() + "今天有多热？快戳这里，立即查看";
                    String title = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                    // String base64Title = Base64.getBase64(inputMsg.getContent());
                    String searchKeyword = new String(inputMsg.getContent().getBytes("UTF-8"), "ISO-8859-1");
                    // String state = Base64.getBase64("${"+inputMsg.getContent().trim()+"}");
                    // String codeURL = props.getProperty("authorizeURL").trim() + "?appid=" +
                    // props.getProperty("AppID1").trim() + "&redirect_uri=" +
                    // props.getProperty("redirectURI1").trim() +
                    // "&response_type=code&scope=snsapi_userinfo&state=" + state +
                    // "#wechat_redirect";
                    String description = new String(
                            "微热点是专业的网络信息工具，产品可对全网信息进行热度查询、监测和预警，还可以对热点事件的传播进行溯源、走势、对比等分析，点击底部菜单可立即体验产品。"
                                    .getBytes("UTF-8"),
                            "ISO-8859-1");
                    StringBuffer str = new StringBuffer();
                    str.append("<xml>");
                    str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                    str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
                    str.append("<CreateTime>" + returnTime + "</CreateTime>");
                    str.append("<MsgType><![CDATA[news]]></MsgType>");
                    str.append("<ArticleCount>1</ArticleCount>");
                    str.append("<Articles>");
                    str.append("<item>");
                    str.append("<Title><![CDATA[" + title + "]]></Title>");
                    str.append("<Description><![CDATA[" + description + "]]></Description>");
                    str.append("<PicUrl><![CDATA[http://cdn.files.51wyq.cn/h5/images/wxmessage.jpg]]></PicUrl>");
                    str.append("<Url><![CDATA[" + SysConfig.cfgMap.get("SYSTEM_H5_URL")
                            + "/view/hotSearch/goHotSearch.shtml?isWx=1&searchKeyword=" + searchKeyword
                            + "]]></Url>");
                    // str.append("<Url><![CDATA["+codeURL+"]]></Url>");
                    str.append("</item>");
                    str.append("</Articles>");
                    str.append("</xml>");
                    System.out.println(str.toString());
                    response.getWriter().write(str.toString());
                }
                // 获取并返回多图片消息
                else if (msgType.equals(MsgType.Image)) {
                    System.out.println("获取多媒体信息");
                    System.out.println("多媒体文件id：" + inputMsg.getMediaId());
                    System.out.println("图片链接：" + inputMsg.getPicUrl());
                    System.out.println("消息id，64位整型：" + inputMsg.getMsgId());
                    OutputMessage outputMsg = new OutputMessage();
                    outputMsg.setFromUserName(servername);
                    outputMsg.setToUserName(custermname);
                    outputMsg.setCreateTime(returnTime);
                    outputMsg.setMsgType(msgType);
                    ImageMessage images = new ImageMessage();
                    images.setMediaId(inputMsg.getMediaId());
                    outputMsg.setImage(images);
                    System.out.println("xml转换：/n" + xs.toXML(outputMsg));
                    response.getWriter().write(xs.toXML(outputMsg));
                } else if (msgType.equals(MsgType.event)) {
                    if (eventType.equals(Event.subscribe)) {
                        // String text =
                        // "感谢您关注微热点，微热点是新浪微博旗下的网络信息产品，应用旨在对全网信息进行监测和预警，对信息的热度、溯源和传播进行多维度的分析。欢迎访问微热点产品："
                        // + SysConfig.SYSTEM_H5_URL + " 获取专业的信息监测分析服务，有问题可以随时私信联系我哦。";
                        // text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                        // StringBuffer str = new StringBuffer();
                        // str.append("<xml>");
                        // str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                        // str.append("<FromUserName><![CDATA[" + servername +
                        // "]]></FromUserName>");
                        // str.append("<CreateTime>" + returnTime + "</CreateTime>");
                        // str.append("<MsgType><![CDATA[text]]></MsgType>");
                        // str.append("<Content><![CDATA[" + text + "]]></Content>");
                        // str.append("</xml>");
                        // System.out.println(str.toString());
                        // response.getWriter().write(str.toString());
                        // 文本消息
                        System.out.println("开发者微信号：" + inputMsg.getToUserName());
                        System.out.println("发送方帐号：" + inputMsg.getFromUserName());
                        System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
                        System.out.println("消息内容：" + inputMsg.getContent());
                        System.out.println("消息Id：" + inputMsg.getMsgId());
                        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
                        System.out.println(url);
                        StringBuilder params = new StringBuilder();
                        params.append("access_token=" + AccessTokenUtil.getServerNumberAccessToken().getAccess_token());
                        params.append("&openid=" + inputMsg.getFromUserName());
                        params.append("&lang=zh_CN");
                        System.out.println("----params----" + params.toString());
                        String result = HttpRequestUtils.sendGet(url, params.toString(), "UTF-8");
                        System.out.println("-----result:" + result);
                        String filePath = null;
                        try {
                            UserInfo userInfo = JSON.parseObject(result, UserInfo.class);
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("openId",inputMsg.getFromUserName());
                            map1.put("type",6);
                            BaseDto weiXinMaterialByOpenIdAndType = apiInfoClient.findWeiXinMaterialByOpenIdAndType(map1);
                            WeiXinMaterial wxm =null;
                            if(weiXinMaterialByOpenIdAndType.getData()!=null){
                                wxm= com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(weiXinMaterialByOpenIdAndType.getData()),WeiXinMaterial.class) ;
                            }
                            if (wxm == null) {
                                filePath = HotKeyAPI.getImgUrlByUserInfo4(userInfo, BusinessConstant.SERVERNUMBER);
                                WeiXinMaterial wx = new WeiXinMaterial();
                                wx.setOpenId(inputMsg.getFromUserName());
                                wx.setImgUrl(filePath);
                                wx.setStatus(BusinessConstant.STATUS_VALID);
                                wx.setCreateTime(new Date());
                                wx.setType(6);
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("wxMaterial",wx);
                                apiInfoClient.saveWeiXinMaterial(map);
                            } else {
                                filePath = wxm.getImgUrl();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String sendUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="
                                + AccessTokenUtil.getServerNumberAccessToken().getAccess_token() + "&type=image";
                        String resultImg = null;
                        FileUpload fileUpload = new FileUpload();
                        resultImg = fileUpload.send(sendUrl, filePath);
                        JSONObject jsonMsg = JSONObject.fromObject(resultImg);
                        System.out.println("media_id=" + jsonMsg.getString("media_id"));
                        String media_id = jsonMsg.getString("media_id");
                        String text = "感谢您关注微热点，微热点是新浪微博旗下的网络信息产品，应用旨在对全网信息进行监测和预警，对信息的热度、溯源和传播进行多维度的分析。欢迎访问微热点产品："
                                + SysConfig.cfgMap.get("SYSTEM_H5_URL") + "获取专业的信息监测分析服务，有问题可以随时私信联系我哦。";
                        text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                        StringBuffer str = new StringBuffer();
                        str.append("<xml>");
                        str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                        str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
                        str.append("<CreateTime>" + returnTime + "</CreateTime>");
                        str.append("<MsgType><![CDATA[image]]></MsgType>");
                        str.append("<Image><MediaId><![CDATA[" + media_id + "]]></MediaId></Image>");
                        str.append("</xml>");
                        System.out.println(str.toString());
                        response.getWriter().write(str.toString());
                    } else if (eventType.equals(Event.SCAN)) {
                        // String text =
                        // "感谢您关注微热点，微热点是新浪微博旗下的网络信息产品，应用旨在对全网信息进行监测和预警，对信息的热度、溯源和传播进行多维度的分析。欢迎访问微热点产品："
                        // + SysConfig.SYSTEM_H5_URL + " 获取专业的信息监测分析服务，有问题可以随时私信联系我哦。";
                        // text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                        // StringBuffer str = new StringBuffer();
                        // str.append("<xml>");
                        // str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                        // str.append("<FromUserName><![CDATA[" + servername +
                        // "]]></FromUserName>");
                        // str.append("<CreateTime>" + returnTime + "</CreateTime>");
                        // str.append("<MsgType><![CDATA[text]]></MsgType>");
                        // str.append("<Content><![CDATA[" + text + "]]></Content>");
                        // str.append("</xml>");
                        // System.out.println(str.toString());
                        // response.getWriter().write(str.toString());
                        // 文本消息
                        System.out.println("开发者微信号：" + inputMsg.getToUserName());
                        System.out.println("发送方帐号：" + inputMsg.getFromUserName());
                        System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
                        System.out.println("消息内容：" + inputMsg.getContent());
                        System.out.println("消息Id：" + inputMsg.getMsgId());
                        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
                        System.out.println(url);
                        StringBuilder params = new StringBuilder();
                        params.append("access_token=" + AccessTokenUtil.getServerNumberAccessToken().getAccess_token());
                        params.append("&openid=" + inputMsg.getFromUserName());
                        params.append("&lang=zh_CN");
                        System.out.println("----params----" + params.toString());
                        String result = HttpRequestUtils.sendGet(url, params.toString(), "UTF-8");
                        System.out.println("-----result:" + result);
                        String filePath = null;
                        try {
                            UserInfo userInfo = JSON.parseObject(result, UserInfo.class);
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("openId",inputMsg.getFromUserName());
                            map1.put("type",6);
                            BaseDto weiXinMaterialByOpenIdAndType = apiInfoClient.findWeiXinMaterialByOpenIdAndType(map1);
                            WeiXinMaterial wxm =null;
                            if(weiXinMaterialByOpenIdAndType.getData()!=null){
                                wxm= com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(weiXinMaterialByOpenIdAndType.getData()),WeiXinMaterial.class) ;
                            }
                            if (wxm == null) {
                                filePath = HotKeyAPI.getImgUrlByUserInfo4(userInfo,BusinessConstant.SERVERNUMBER);
                                WeiXinMaterial wx = new WeiXinMaterial();
                                wx.setOpenId(inputMsg.getFromUserName());
                                wx.setImgUrl(filePath);
                                wx.setStatus(BusinessConstant.STATUS_VALID);
                                wx.setCreateTime(new Date());
                                wx.setType(6);
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("wxMaterial",wx);
                                apiInfoClient.saveWeiXinMaterial(map);
                            } else {
                                filePath = wxm.getImgUrl();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String sendUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="
                                + AccessTokenUtil.getServerNumberAccessToken().getAccess_token() + "&type=image";
                        String resultImg = null;
                        FileUpload fileUpload = new FileUpload();
                        resultImg = fileUpload.send(sendUrl, filePath);
                        JSONObject jsonMsg = JSONObject.fromObject(resultImg);
                        System.out.println("media_id=" + jsonMsg.getString("media_id"));
                        String media_id = jsonMsg.getString("media_id");
                        String text = "感谢您关注微热点，微热点是新浪微博旗下的网络信息产品，应用旨在对全网信息进行监测和预警，对信息的热度、溯源和传播进行多维度的分析。欢迎访问微热点产品："
                                + SysConfig.cfgMap.get("SYSTEM_H5_URL") + "获取专业的信息监测分析服务，有问题可以随时私信联系我哦。";
                        text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                        StringBuffer str = new StringBuffer();
                        str.append("<xml>");
                        str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                        str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
                        str.append("<CreateTime>" + returnTime + "</CreateTime>");
                        str.append("<MsgType><![CDATA[image]]></MsgType>");
                        str.append("<Image><MediaId><![CDATA[" + media_id + "]]></MediaId></Image>");
                        str.append("</xml>");
                        System.out.println(str.toString());
                        response.getWriter().write(str.toString());
                    } else if (eventType.equals(Event.CLICK)) {
                        String eventKey = inputMsg.getEventKey();
                        System.out.println("开发者事件key：" + inputMsg.getEventKey());
                        if ("ynyx".equals(eventKey)) {
                            // 文本消息
                            System.out.println("开发者微信号：" + inputMsg.getToUserName());
                            System.out.println("发送方帐号：" + inputMsg.getFromUserName());
                            System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
                            System.out.println("消息内容：" + inputMsg.getContent());
                            System.out.println("消息Id：" + inputMsg.getMsgId());
                            String url = "https://api.weixin.qq.com/cgi-bin/user/info";
                            System.out.println(url);
                            StringBuilder params = new StringBuilder();
                            params.append("access_token=" + AccessTokenUtil.getServerNumberAccessToken().getAccess_token());
                            params.append("&openid=" + inputMsg.getFromUserName());
                            params.append("&lang=zh_CN");
                            System.out.println("----params----" + params.toString());
                            String result = HttpRequestUtils.sendGet(url, params.toString(), "UTF-8");
                            System.out.println("-----result:" + result);
                            UserInfo userInfo = JSON.parseObject(result, UserInfo.class);
                            String filePath = null;
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("openId",inputMsg.getFromUserName());
                            map1.put("type",6);
                            BaseDto weiXinMaterialByOpenIdAndType = apiInfoClient.findWeiXinMaterialByOpenIdAndType(map1);
                            WeiXinMaterial wxm =null;
                            if(weiXinMaterialByOpenIdAndType.getData()!=null){
                                wxm= com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(weiXinMaterialByOpenIdAndType.getData()),WeiXinMaterial.class) ;
                            }
                            if (wxm == null) {
                                filePath = HotKeyAPI.getImgUrlByUserInfo4(userInfo,BusinessConstant.SERVERNUMBER);
                                WeiXinMaterial wx = new WeiXinMaterial();
                                wx.setOpenId(inputMsg.getFromUserName());
                                wx.setImgUrl(filePath);
                                wx.setStatus(BusinessConstant.STATUS_VALID);
                                wx.setCreateTime(new Date());
                                wx.setType(6);
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("wxMaterial",wx);
                                apiInfoClient.saveWeiXinMaterial(map);
                            } else {
                                filePath = wxm.getImgUrl();
                            }
                            String sendUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="
                                    + AccessTokenUtil.getServerNumberAccessToken().getAccess_token() + "&type=image";
                            String resultImg = null;
                            FileUpload fileUpload = new FileUpload();
                            resultImg = fileUpload.send(sendUrl, filePath);
                            JSONObject jsonMsg = JSONObject.fromObject(resultImg);
                            System.out.println("media_id=" + jsonMsg.getString("media_id"));
                            String media_id = jsonMsg.getString("media_id");
                            String text = "感谢您关注微热点，微热点是新浪微博旗下的网络信息产品，应用旨在对全网信息进行监测和预警，对信息的热度、溯源和传播进行多维度的分析。欢迎访问微热点产品："
                                    + SysConfig.cfgMap.get("SYSTEM_H5_URL") + " 获取专业的信息监测分析服务，有问题可以随时私信联系我哦。";
                            text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                            StringBuffer str = new StringBuffer();
                            str.append("<xml>");
                            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
                            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
                            str.append("<CreateTime>" + returnTime + "</CreateTime>");
                            str.append("<MsgType><![CDATA[image]]></MsgType>");
                            str.append("<Image><MediaId><![CDATA[" + media_id + "]]></MediaId></Image>");
                            str.append("</xml>");
                            System.out.println(str.toString());
                            response.getWriter().write(str.toString());
                        }
                    }
                } else {
                    response.getWriter().write("success");
                }
            }
        }
    }


    /**
     * 验证URL真实性
     * @author morning
     * @date 2015年2月17日 上午10:53:07
     * @param request
     * @param response
     * @return String
     */
    private String access(HttpServletRequest request, HttpServletResponse response) {
        // 验证URL真实性
        System.out.println("进入验证access");
        log.info("access:{}","进入验证access");
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
        log.info("signature:{}",signature);
        log.info("timestamp:{}",timestamp);
        log.info("nonce:{}",nonce);
        log.info("echostr:{}",echostr);
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        List<String> params = new ArrayList<String>();
        params.add("miduchina2016");
        params.add(timestamp);
        params.add(nonce);
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        Collections.sort(params, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
        log.info("temp:{}",temp);
        log.info("signature:{}",signature);
        if (temp.equals(signature)) {
            try {
                response.getWriter().write(echostr);
                System.out.println("成功返回 echostr：" + echostr);
                log.info("endResult:{}",echostr);
                return echostr;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("失败 认证");
        log.info("endResult:{}","认证失败");
        return null;
    }
}
