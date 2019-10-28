package com.miduchina.wrd.eventanalysis.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.utils.LoginUtils;
import com.miduchina.wrd.eventanalysis.utils.ShortUrlGenerator;
import com.miduchina.wrd.po.h5.H5ShortUrl;
import com.xd.tools.pojo.db.mysql.wyq.weiyuqing.H5SearchShare;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AutoAuthInterceptor implements HandlerInterceptor {
    private static final long serialVersionUID = 1L;
    @Autowired
    protected APIInfoClient apiInfoClient;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String uri = request.getRequestURL().toString();
        String url2=request.getScheme()+"://"+ request.getServerName();//+request.getRequestURI();
        log.info("AutoAuthInterceptor Start:{}",JSONObject.toJSONString(uri));
        if(uri.startsWith("http://localhost") || uri.startsWith("http://localhost:8080")){
            Flags.local_flag=true;
        }
        Map<String, Object> parma = new HashMap<>();
        String obj = LoginUtils.getSidFromCookie(request);
        log.info("    SidFromCookie:{}",JSONObject.toJSONString(obj));
        if (obj == null && (request.getRequestURL().toString().contains("m.wrd.cn") || request.getRequestURL().toString().contains("h5.wyq.cn") || request.getRequestURL().toString().contains("h5.51wyq.cn")||request.getRequestURL().toString().contains("m-beta1.51wyq.cn"))) {
//            log.info("contains:{}",request.getRequestURL().toString().contains("m-beta1.51wyq.cn"));
            String userAgent = request.getHeader("user-agent");
            log.info("userAgent:{}"+userAgent);
            if (StringUtils.isNotBlank(userAgent)) {
                if (userAgent.toLowerCase().contains("micromessenger")) { // 微信客户端
                    String url = request.getRequestURL().toString();
                    String params = request.getQueryString();
                    log.info("weixin auth url ----->>"+url);
                    log.info("weixin auth params ----->>"+params);

                    if(StringUtils.isNotBlank(url)){
                        if(StringUtils.isNotBlank(params)){
                            params = params.replaceAll("&from=(timeline|groupmessage|singlemessage)(&isappinstalled=\\d)?", "");
                            url = url + "?" +params;
                        }
                        if(url.contains("search/goSearch")){
                            String searchShareCode = request.getParameter("searchShareCode");
                            if(StringUtils.isNotBlank(searchShareCode)){
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("shareCode",searchShareCode);
                                BaseDto hs1 = apiInfoClient.findHs(map);
                                log.info("shareCodenBto:{}",hs1);
                                H5SearchShare hs =null;
                                if(hs1.getData()!=null){
                                    String data = JSON.toJSONString(hs1.getData());
                                    hs = JSONObject.parseObject(data, H5SearchShare.class);
                                }
                                if(hs != null && StringUtils.isNotBlank(hs.getTabIndex())){
                                    url = url+hs.getTabIndex();
                                }
                            }
                        }
                        String sourceUrl = url;
                        String shortUrl = ShortUrlGenerator.shortUrl(sourceUrl);
                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("shortUrl",SysConfig.cfgMap.get("SYSTEM_H5_URL")+shortUrl);
                        log.info("H5_TESHOME_URL:{}",SysConfig.cfgMap.get("SYSTEM_H5_URL"));
                        map.put("shortUrl",SysConfig.cfgMap.get("SYSTEM_H5_URL")+shortUrl);
                        H5ShortUrl h5ShortUrl =null;
                        BaseDto bs = apiInfoClient.findH5ShortUrlByShortUrl(map);
                        log.info("H5_TESHOME_URL:{}",bs);
                        if(bs.getData()!=null){
                            h5ShortUrl=JSONObject.parseObject(JSONObject.toJSONString(bs.getData()),H5ShortUrl.class) ;
                        }
                        if(h5ShortUrl == null){
                            h5ShortUrl = new H5ShortUrl();
                            h5ShortUrl.setSourceUrl(sourceUrl);
//                            h5ShortUrl.setShortUrl(SysConfig.cfgMap.get("SYSTEM_H5_URL")+shortUrl);
                            h5ShortUrl.setShortUrl(SysConfig.cfgMap.get("SYSTEM_H5_URL")+shortUrl);
                            h5ShortUrl.setCreateTime(new Date());
                            h5ShortUrl.setStatus(BusinessConstant.STATUS_VALID);
                            try {
                                HashMap<String, Object> map1 = new HashMap<>();
                                map1.put("h5ShortUrl",h5ShortUrl);
                                log.info("h5ShortUrl:{}",JSONObject.toJSONString(h5ShortUrl));
                                apiInfoClient.saveH5ShortUrl(map1);
                            } catch (Exception e) {
                                log.error("h5ShortUrl catch json ==>>"+ JSON.toJSONString(h5ShortUrl));
                            }
                        }

//                        parma.put("wxUrl",h5ShortUrl.getShortUrl());
                        String shortUrl1 = h5ShortUrl.getShortUrl();
                        shortUrl1=shortUrl1.replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)","");
                        uri=url2+"/toWxAuth?wxUrl="+shortUrl1;
                    }
                    response.sendRedirect(uri);
                }
            }
        }
//        HttpRequestUtils.sendPost(uri,parma);
        return true;
    }

    public static void main(String[] args) {
        String  str="http://m-beta1.51wyq.cn/wxAuth";
        boolean contains = str.contains("m-beta1.51wyq.cn");
        System.out.println(contains);
    }
}
