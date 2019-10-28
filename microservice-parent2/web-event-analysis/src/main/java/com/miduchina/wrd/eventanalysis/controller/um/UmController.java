package com.miduchina.wrd.eventanalysis.controller.um;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.utils.HttpRequestUtils;
import com.miduchina.wrd.eventanalysis.utils.LoginUtils;
import com.miduchina.wrd.vo.BaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther yho
 * @vreate 2019-08 14:02
 */
@Slf4j
@Controller
public class UmController extends BaseController {
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "/uMedia")
    public String gowUMedia(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map) throws UnsupportedEncodingException {
        fetchSessionAdmin(request);
//        Boolean umBlueVUser=true;
        Boolean umBlueVUser=false;
        if(admin==null){
            map.put("sid",null);
            map.put("umBlueVUser",umBlueVUser);
            map.put("nickname",null);
        }else{
//            if(admin.getUserChannel()==2){
                String nickname=admin.getNickname();
                log.info("name:{}",nickname);
//                String nickname="中国消防";
                String sid = LoginUtils.getSidFromCookie(request);
                Map<String, Object> params=new HashMap<>();
                params.put("userId",admin.getUserId());
                String str =  URLEncoder.encode(nickname, "UTF-8");
                params.put("name",str);
                String url="http://api.data.u-mei.com/api/v1/wb/industry/quota_by_name";
                String s1 = HttpRequestUtils.sendPost(url,params);
                JSONObject jsonObject1 = JSONObject.parseObject(s1);
                log.info("umResult:{}",JSONObject.toJSONString(s1));
                String code = jsonObject1.getString("code");
                if(code.equals("200")){
                    umBlueVUser = true;
                }else{
                    umBlueVUser = false;
                }
                map.put("sid",sid);
                map.put("umBlueVUser",umBlueVUser);
                map.put("nickname",nickname);
//            }else{
//                map.put("sid",null);
//                map.put("umBlueVUser",umBlueVUser);
//                map.put("nickname",null);
//            }
        }
        log.info("gowUMediamap:{}",map);
        log.info("admin:{}",admin);


        return "/view/um/uMedia";
    }

    //um接口查询
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST },value = "/getUmResult")
    @ResponseBody
    public BaseVo getUmResult() throws UnsupportedEncodingException {
        BaseVo vo = new BaseVo();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        fetchSessionAdmin(request);
        if(admin==null){
            vo.setCode("0401");
            log.info("admin:{}",admin);
            return vo;
        }
        String type = request.getParameter("type");
        log.info("type:{}",type);
        Map<String, Object> params=new HashMap<>();
        String url=null;
        String nickname=null;
        if(Integer.parseInt(type)==1){
//            params.put("userId",admin.getNickname());
            nickname = admin.getNickname();
//           nickname="中国消防";
            String str =  URLEncoder.encode(nickname, "UTF-8");
            params.put("name",str);
//            url="http://api.data.u-mei.com/api/v1/wb/industry/quota_by_id";
            url="http://api.data.u-mei.com/api/v1/wb/industry/quota_by_name";
        }else{
//            nickname="中国消防";
            nickname = request.getParameter("nickname");
            String str =  URLEncoder.encode(nickname, "UTF-8");
            params.put("name",str);
            url="http://api.data.u-mei.com/api/v1/wb/industry/quota_by_name";
        }
        log.info("getUmResultnickname:{}",nickname);
        String s = HttpRequestUtils.sendPost(url,params);
        log.info("getUmResult:{}",JSONObject.toJSONString(s));
        JSONObject jsonObject = JSON.parseObject(s);
        vo.setCode(jsonObject.getString("code"));
        vo.setMessage(jsonObject.getString("msg"));
        vo.setData(jsonObject.getJSONObject("data"));
        return vo;
    }
}
