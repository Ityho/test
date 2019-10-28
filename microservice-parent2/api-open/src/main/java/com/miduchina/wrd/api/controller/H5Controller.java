package com.miduchina.wrd.api.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.miduchina.wrd.api.pojo.SHA1;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.08.14
 */
@RestController
@RequestMapping
@Slf4j
public class H5Controller {


    public static  String openUrl = "http://m-beta1.51wyq.cn";

    /**
     * 登录用户，检查手机号
     * @throws Exception
     */
    @RequestMapping(value = "/toWxAuth")
    @ResponseBody
    public void  checkLoginMobile(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String url ="http://m-beta1.51wyq.cn";
        log.info("toWxAuth:{}",url);
        response.sendRedirect(url);
    }
    @RequestMapping(value = "/sinaAuth")
    @ResponseBody
    public void  sinaAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String signedRequest = request.getParameter("signed_request");
        String url ="http://m-beta1.51wyq.cn/sinaAuth?signed_request="+signedRequest;
        log.info("sinaAuthhurl:{}",url);
        response.sendRedirect(url);
    }
    @RequestMapping(value = "/wxAuth")
    @ResponseBody
    public void  wxAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String responseuri = request.getRequestURL().toString();
        log.info("wxAuthurl:{}",responseuri);
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String scope = request.getParameter("scope");
        log.info("wxAuth执行:{} code:{} state:{} scope:{}","seccess" ,code, state,scope);
        String url ="http://m-beta1.51wyq.cn/wxAuth?code="+code+"&state="+state+"&scope="+scope;
        log.info("wxAuthurl:{}",url);
        response.sendRedirect(url);
    }
    /**
     * 验证URL真实性
     * @author morning
     * @date 2015年2月17日 上午10:53:07
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/access")
    @ResponseBody
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

    /**
     * 验证测试平台的token
     */
    @RequestMapping(value = "/token",method = RequestMethod.GET)
    public void token(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String echostr = request.getParameter("echostr");
        PrintWriter print = response.getWriter();
        print.write(echostr);
        print.flush();
        log.info("open token:{}"+echostr);
    }


    @RequestMapping(value = "/pay/doNotify")
    private void doNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }



    @RequestMapping(value = "/pay/doReturn")
    private void doReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }



    @RequestMapping(value = "/pay/doSinaNotify")
    private void doSinaNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }



    @RequestMapping(value = "/pay/doSinaReturn")
    private void doSinaReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }


    @RequestMapping(value = "/pay/doWeixinNotify")
    private void doWeixinNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }


    @RequestMapping(value = "/pay/goWeiXinPay")
    private void goWeiXinPay(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }



    @RequestMapping(value = "/pay/doWeixinReturn")
    private void doWeixinReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendRedirect(request,response);
    }






    @RequestMapping(value = "/view/user/doNotify.action")
    private void doNotifyAction(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }



    @RequestMapping(value = "/view/pay/doReturn.action")
    private void doReturnAction(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }



    @RequestMapping(value = "/view/user/doSinaNotify.action")
    private void doSinaNotifyAction(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }



    @RequestMapping(value = "/view/pay/doSinaReturn.action")
    private void doSinaReturnAction(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sendRedirect(request,response);
    }


    @RequestMapping(value = "/view/user/doWeixinNotify.action")
    private void doWeixinNotifyAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendRedirect(request,response);
    }

    @RequestMapping(value = "/view/pay/doWeixinReturn.action")
    private void doWeixinReturnAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendRedirect(request,response);
    }


    void sendRedirect(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();
        /**
         * 获取路径中的参数
         */

        String  url = openUrl+path;
        Map<String, Object> params = callBack(request);
        if (params != null && !params.isEmpty()) {
            List<String> paramList = new ArrayList<String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                paramList.add(entry.getKey() + "=" + entry.getValue());
            }
            url +=  "?" + StringUtils.join(paramList.toArray(), "&");
        }
        response.sendRedirect(url);
    }



    public Map<String, Object> callBack(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String[]> resultParam = request.getParameterMap();
        Set keSet = resultParam.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if (ov instanceof String[]) {
                value = (String[]) ov;
            } else {
                value[0] = ov.toString();
            }

            for (int k = 0; k < value.length; k++) {
                log.info(ok + "=" + value[k]);
                result.put(ok.toString(), value[k]);
            }
        }
        log.info("Parame"+result.toString());
        return result;
    }




}
