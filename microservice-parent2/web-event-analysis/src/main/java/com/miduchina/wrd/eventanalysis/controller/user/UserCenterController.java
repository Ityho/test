package com.miduchina.wrd.eventanalysis.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.model.OrderListView;
import com.miduchina.wrd.eventanalysis.model.OrderListViewElement;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.CommonUtils;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.po.eventanalysis.UserExclusiveChannelRes;
import com.miduchina.wrd.po.eventanalysis.UserExclusiveChannelResElement;
import com.xd.tools.ownutils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/userCenter")
@Controller
public class UserCenterController extends BaseController {




    @Autowired
    OrderClientService orderService;

    /**
     * 分享计划
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doUserSharecode")
    public ModelAndView doUserSharecode(ModelAndView modelAndView,HttpServletRequest request) throws Exception{
        admin=fetchSessionAdmin(request);
        if (admin != null) {
            UserExclusiveChannelRes userExclusiveChannelRes = fetchUserExclusiveChannel(request,Integer.valueOf(admin.getUserId()));
            if (userExclusiveChannelRes != null) {
                UserExclusiveChannelResElement userExclusiveChannelResElement = userExclusiveChannelRes.getUserExclusiveChannel();
                String url = getQRCodeUrl(request ,userExclusiveChannelResElement);
                modelAndView.addObject("userExclusiveChannelResElement",userExclusiveChannelResElement);
                modelAndView.addObject("baseUrl",Utils.getBaseUrl(request));
                modelAndView.addObject("url",url);
                modelAndView.setViewName("view/userCenter/share_code");
                return modelAndView;
            }
        }
        modelAndView.setViewName(Flags.error_view);
        return modelAndView;
    }
    /**
     * 获取二维码URL
     *
     * @param request
     * @return
     */
    private String getQRCodeUrl(HttpServletRequest request,UserExclusiveChannelResElement userExclusiveChannelResElement) {
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        java.lang.String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL") + "/c?cid=" + userExclusiveChannelResElement.getUecCode();
        return url;
    }
    @InitBinder("userExclusiveChannelResElement")
    public void initBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("userExclusiveChannelResElement.");
    }
    /**
     * 生成分享计划二维码
     *
     * @throws Exception
     */
    @RequestMapping(value = "sharePlanQRCode")
    public void sharePlanQRCode(HttpServletRequest request,HttpServletResponse response,UserExclusiveChannelResElement userExclusiveChannelResElement) throws Exception {
        fetchSessionAdmin(request);
        if (admin != null && userExclusiveChannelResElement != null) {
            String url = getQRCodeUrl(request,userExclusiveChannelResElement);
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //hints.put(EncodeHintType.MARGIN, 1);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 220, 220, hints);
            BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);

            Graphics2D gs = bi.createGraphics();
            Image img = ImageIO.read(new URL(userExclusiveChannelResElement.getUecQrcodeLogoPath()));
            gs.drawImage(img, 95, 95, 30, 30, Color.WHITE, null);
            gs.dispose();
            img.flush();

            HttpServletResponse resp = response;
            resp.setContentType("image/png");
            resp.setHeader("Cache-Control", "No-cache");
            ImageIO.write(bi, "PNG", resp.getOutputStream());
        }
    }


    @RequestMapping(value = "/orderList")
    public ModelAndView orderList(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws Exception {
        admin=fetchSessionAdmin(request);
        if (admin == null){
            modelAndView.setViewName("userLogin");
            modelAndView.addObject("urlType","analysis");
            return modelAndView;
        }
        modelAndView.setViewName("view/pay/orderlist");
        modelAndView.addObject("loginPlatform", userPlatform);
        return  modelAndView ;
    }

    @RequestMapping(value = "/getOrderList")
    @ResponseBody
    public  Map<String,Object> getOrderList(HttpServletRequest request,HttpServletResponse response,Integer orderType,Integer payStatus,String startDate,String endDate) throws Exception {
        fetchSessionAdmin(request);
        Map<String,Object> resHashMap=new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("orderType", orderType);
        params.put("payStatus", payStatus);
        params.put("startTime", startDate);
        params.put("endTime", endDate);
        params.put("platform", BusinessConstant.PLATFORM_H5);

        String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request, "orderList", params);
        OrderListView view = new OrderListView();
        if (StringUtils.isNotBlank(jsonStr)){
            view = JSONObject.parseObject(jsonStr,OrderListView.class);
        }
        if (view != null && StringUtils.isNotBlank(view.getCode()) && view.getCode().equals("0000")){
            resHashMap.put("status",1);

            if (CollectionUtils.isNotEmpty(view.getOrderList())){
                for (OrderListViewElement element :view.getOrderList()){
                    if (element.getOrderInfo() != null){
                        Date date = element.getOrderInfo().getCreateTime();
                        element.getOrderInfo().setCreateTimeStr(DateUtils.format(date));
                    }
                }
                resHashMap.put("obj",view.getOrderList());
            }
        }else {
            resHashMap.put("status",0);
        }
        return  resHashMap;
    }


    /**
     * 跳转续费管理
     * @param request
     * @param response
     * @param modelAndView
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/renewProductPackage")
    public ModelAndView renewProductPackage(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws Exception {
        admin=fetchSessionAdmin(request);
        if (admin == null){
            modelAndView.setViewName("/userLogin");
            modelAndView.addObject("urlType","analysis");
            return modelAndView;
        }
        modelAndView.setViewName("view/pay/renewProductPackage");
        modelAndView.addObject("loginPlatform", userPlatform);
        modelAndView.addObject("admin", admin);
        return  modelAndView;
    }


    /**
     * 查询续费监测方案
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getContinuePayManage")
    @ResponseBody
    public  Map<String,Object> getContinuePayManage(HttpServletRequest request,HttpServletResponse response,Integer expiredCondition) throws Exception {
        admin = fetchSessionAdmin(request);
        if(expiredCondition == null){
            expiredCondition = 1;
        }
        Map<String,Object> resHashMap=new HashMap<>();
        BaseDto baseDto = apiInfoClient.getRenewKeyWord(Integer.valueOf(admin.getUserId()),expiredCondition);
        if (baseDto != null && StringUtils.isNotBlank(baseDto.getCode()) && baseDto.getCode().equals("0000")){
            resHashMap.put("status",1);
            resHashMap.put("obj",baseDto.getData());
        }else {
            resHashMap.put("status",1);
        }
        return  resHashMap;
    }



    /**
     * 产品选购
     * @param request
     * @param response
     * @param modelAndView
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goBuy")
    public ModelAndView gobuy(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView,Integer type) throws Exception {
        admin=fetchSessionAdmin(request);
        if (admin == null){
            modelAndView.setViewName("userLogin");
            modelAndView.addObject("urlType","home");
            return modelAndView;
        }
        modelAndView.setViewName("view/userCenter/buy_plan_v4");
        modelAndView.addObject("loginPlatform", userPlatform);
        modelAndView.addObject("admin", admin);
        modelAndView.addObject("userPlatform", userPlatform);

        fetchProductlistV2(modelAndView,request,0);
        fetchProductlistV2(modelAndView,request,3);
        fetchProductlistV2(modelAndView,request,4);
        fetchProductlistV2(modelAndView,request,5);
        fetchProductlistV2(modelAndView,request,6);
        fetchProductlistV2(modelAndView,request,7);
        fetchProductlistV2(modelAndView,request,20);

        /**
         * type 0 微积分充值 1 产品选购
         */
        Map<String, Object> params = new HashMap<String, Object>();
        if (type == 1){
            modelAndView.addObject("product_select", 1);
        }else {
            modelAndView.addObject("product_select", 0);
        }
        return  modelAndView;
    }

}
