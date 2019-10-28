package com.miduchina.wrd.eventanalysis.controller.pay;

import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.pay.ConfirmOrderViewDto;
import com.miduchina.wrd.dto.pay.PayRecordDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.config.SinaPayConfig;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.log.model.OperateLogOrderInfo;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@RequestMapping(value = "/view")
@Controller
public class PayCallbackController extends BaseController {
    @Autowired
    OrderClientService orderService;

    private static final String UTF_8 = "UTF-8";

    /**
     * 异步跳转地址
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/user/doNotify.action")
    @ResponseBody
    public void doNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {

            admin = fetchSessionAdmin(request);
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
                log.info("doReturn --> [" + name + "] = [" + valueStr + "]");
            }

            // 获取支付宝的通知返回参数
            // 商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            // 支付宝交易号
            String trade_no = request.getParameter("trade_no");

            // 交易状态
            String trade_status = request.getParameter("trade_status");
            // 计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);

            log.info("PayAction verify_result=" + verify_result);


            BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordByInnerTradeNo(out_trade_no);
            if (baseDto.getData() == null){
                return ;
            }
            baseDto = apiInfoClient.findPayRecordById(baseDto.getData().getPayRecordId());
            if (!baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                return;
            }

            PayRecordDto p = baseDto.getData();
            BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(p.getUserId());

            if (verify_result) {// 验证成功
                // 请在这里加上商户的业务逻辑程序代码
                // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 如果有做过处理，不执行商户的业务程序
                    // PayRecord p =
                    // orderRecordBean.findPayRecordById(Integer.parseInt(out_trade_no.replace(AlipayConfig.orderId_pre,
                    // "")));
                    if (p != null && p.getPayStatus() != 1) {
                        log.info("PayAction --------do notify--------------");
                        orderService.doPayEnd(request,p.getInnerTradeNo(), userBaseDtoDto.getData(), null);
//                        saveUserOperateLog(0, Constants.OPERATE_LOG_TYPE_PAY, "支付宝NOTIFY：innerTradeNo = [" + out_trade_no + "] payResult = [SUCCESS]");
//                        CommonUtils.opreateLog(request, null, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_PAYBACK, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null,null);
                    }
                    log.info("PayAction do notify success!");
                    // 通知支付宝成功
                    response.getWriter().println("success");
                } else {
                    orderService.doPayRecordFail(out_trade_no,userBaseDtoDto.getData(), "trade_status=" + trade_status);
                }

            } else {
                orderService.doPayRecordFail(out_trade_no,userBaseDtoDto.getData(), "verify_result=" + verify_result);
                log.info("PayAction do notify fail!");
            }
            log.info("PayAction ---------------do notify end!--------------");
        } catch (Exception e) {
            log.info("PayAction ------------------------do notify Exception-----------------");
            e.printStackTrace();
        }
    }


    /**
     * 同步跳转地址
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pay/doReturn.action")
    public ModelAndView doReturn(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws Exception {
        try {
            log.info("----------------------------------Do Return------------------------------------");
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
//                if (name.equals("notify_id")){
////                    valueStr = StringUtil.urlEncode(valueStr);
//                    valueStr = URLDecoder.decode(valueStr, AlipayConfig.input_charset);
//                }
                params.put(name, valueStr);
                log.info("doReturn --> [" + name + "] = [" + valueStr + "]");
            }

            // 获取支付宝的通知返回参数
            // 商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            // 支付宝交易号
            String trade_no = request.getParameter("trade_no");

            // 交易状态
            String trade_status = request.getParameter("trade_status");
            // 计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);
            log.info("verify_result=" + verify_result);
            BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordByInnerTradeNo(out_trade_no);
            if (baseDto.getData() == null){
                 modelAndView.setViewName(Flags.error_view);
                return modelAndView;
            }
//            baseDto = apiInfoClient.findPayRecordById(baseDto.getData().getPayRecordId());
            PayRecordDto p = baseDto.getData();
            log.info("-------------------PayRecord p----------------"+p);
            if (verify_result) {// 验证成功
                // ////////////////////////////////////////////////////////////////////////////////////////
                // 请在这里加上商户的业务逻辑程序代码
                // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    int productId = -1;
                    int orderType = 1;
                    int productNum = -1;
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 如果有做过处理，不执行商户的业务程序
                    log.info("1");
                    // Order o=orderRecordBean.getOrderById(Integer.parseInt(out_trade_no.replace(AlipayConfig.orderId_pre, "")));
                    // PayRecord p = orderRecordBean.findPayRecordById(Integer.parseInt(out_trade_no.replace(AlipayConfig.orderId_pre, "")));
                    if (p != null && p.getPayStatus() != 1) {
                        fetchSessionAdmin(request);
                        // ServletActionContext.getRequest().getSession().setAttribute("order_"+admin.getUsername(), "1");
                        // UserAction.myOrderStatus.put("order_"+admin.getUsername(), "1");
                        log.info("---------------------------------Do PayEnd-----------------------------");


                        BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(p.getUserId());

                        ConfirmOrderViewDto orderview = orderService.doPayEnd(request,out_trade_no, userBaseDtoDto.getData() , trade_no);
                        productId = orderview.getPackagesInfo().get(0).getProductPackageId();
                        productNum = orderview.getPackagesInfo().get(0).getPackageCount();
                        orderType = orderview.getOrderInfo().getOrderType();
                        log.info("doReturn().productId="+productId);
                        log.info("--------__-------------------------productId-----------------------------"+productId);
                        OperateLogOrderInfo operateLogOrderInfo = new OperateLogOrderInfo();
                        operateLogOrderInfo.setInnerTradeNo(out_trade_no);
                        operateLogOrderInfo.setOutsideTradeNo(trade_no);
                        operateLogOrderInfo.setPayChannel(BusinessConstant.PAY_CHANNEL_ALIPAY);
//                        CommonUtils.opreateLog(request, admin, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_PAY_PAYBACK, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, operateLogOrderInfo, null, null);
                    }else if(p != null && p.getPayStatus() == 1){
                        ConfirmOrderViewDto orderview = orderService.findOrderByInnerTradeNo(request,p.getUserId(), out_trade_no);
                        productId = orderview.getPackagesInfo().get(0).getProductPackageId();
                        productNum = orderview.getPackagesInfo().get(0).getPackageCount();
                        orderType = orderview.getOrderInfo().getOrderType();
                    }

                    // 设置订单支付状态，页面会定时刷新取该状态
                    admin = fetchSessionAdmin(request);

                    log.info("do return success");

                    log.info("doReturn().productId="+productId+",no="+out_trade_no);
                    // 通知支付宝成功
                    response.getWriter().println("success");
                    if(productId == 18){
                        modelAndView.setViewName("redirect:/createAnalysis");
                    }else if(productId == 32){
                        modelAndView.setViewName("redirect:/weibo/createWeiBoAnalysis?createType=1");
                    }else if(productId == 21){
                        modelAndView.setViewName("redirect:/compet/productsAnalysis");
                    }else if(productId == 22){
                        modelAndView.setViewName("redirect:/weiboAnalysis/weiboAnalysisIndex");
                    }else if(productId >=56 && productId >=59){
                        modelAndView.setViewName("redirect:/userCenter/goBuy?type=0");
                    }else{
                        modelAndView.setViewName("redirect:/userCenter/goBuy?type=1");
                    }
                } else {
                    BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(p.getUserId());

                    orderService.doPayRecordFail(out_trade_no,userBaseDtoDto.getData(), "trade_status=" + trade_status);

                    modelAndView.setViewName("redirect:/userCenter/goBuy?type=1");
//					saveUserOperateLog(admin.getUserId(), Constants.OPERATE_LOG_TYPE_PAY, "支付宝RETURN：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + trade_status + "]");
                }
                // 该页面可做页面美工编辑
                // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                // ////////////////////////////////////////////////////////////////////////////////////////
            } else {
                // 该页面可做页面美工编辑
                BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(p.getUserId());
                orderService.doPayRecordFail(out_trade_no,userBaseDtoDto.getData() ,"verify_result=" + verify_result);
                modelAndView.setViewName("redirect:/userCenter/goBuy?type=1");
//				saveUserOperateLog(admin.getUserId(), Constants.OPERATE_LOG_TYPE_PAY, "支付宝RETURN：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + verify_result + "]");
                log.info("do return fail");
            }
            log.info("do return end");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:/userCenter/goBuy?type=1");
        }
        return modelAndView;
    }


    /**
     * 微博支付异步返回
     *
     * @throws Exception
     */
    @RequestMapping(value = "/user/doSinaNotify.action")
    public void doSinaNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        admin = fetchSessionAdmin(request);
        Map requestParams = request.getParameterMap();
        if (requestParams != null) {
            // 组装参数
            Map<String, String> newParams = new TreeMap<String, String>();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++)
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";

                if ("sign".equals(name) || "sign_type".equals(name) || valueStr == null || "".equals(valueStr))
                    continue;

                newParams.put(name, valueStr);
            }

            List<String> pairs = new ArrayList<String>();
            for (String key : newParams.keySet())
                pairs.add(key + "=" + newParams.get(key));
            String sign_data = org.apache.commons.lang3.StringUtils.join(pairs.toArray(), "&");
            String sign = request.getParameter("sign").toString();

            // 验证
            boolean result = SinaPayConfig.checkRSA(sign_data, sign, SinaPayConfig.WEIBO_RSA_PUBLIC_KEY);
            String status = request.getParameter("status").toString();
            String out_pay_id = request.getParameter("out_pay_id").toString();
            String pay_id = request.getParameter("pay_id").toString();

            BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordByInnerTradeNo(out_pay_id);
            if (baseDto.getData() == null){
                return;
            }
            baseDto = apiInfoClient.findPayRecordById(baseDto.getData().getPayRecordId());
            if (!baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                return;
            }
            PayRecordDto payRecord = baseDto.getData();
            BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(payRecord.getUserId());
            if (result) { // 签名验证通过
                if (SinaPayConfig.PAY_STATUS_SUCCESS.equals(status)) { // 支付成功
                    if (payRecord != null && payRecord.getPayStatus() != BusinessConstant.PAY_STATUS_YES) {

                        ConfirmOrderViewDto orderview = orderService.doPayEnd(request,out_pay_id, userBaseDtoDto.getData() , pay_id);

//                        CommonUtils.opreateLog(request, null, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_PAYBACK, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null,null);
                        response.getWriter().print("success");
                    }
                } else if (SinaPayConfig.PAY_STATUS_CLOSED.equals(status)) { // 支付失败
                    orderService.doPayEnd(request,out_pay_id, userBaseDtoDto.getData() , status);
                }
            }else{
                orderService.doPayEnd(request,out_pay_id, userBaseDtoDto.getData() , status);
            }
        }
    }

    /**
     * 微博支付同步返回
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pay/doSinaReturn.action")
    public ModelAndView doSinaReturn(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws Exception {
        fetchSessionAdmin(request);
        Map requestParams = request.getParameterMap();
        if (requestParams != null) {
            // 组装参数
            Map<String, String> newParams = new TreeMap<String, String>();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++){
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                if ("sign".equals(name) || "sign_type".equals(name) || valueStr == null || "".equals(valueStr)){
                    continue;
                }
                newParams.put(name, valueStr);
            }

            List<String> pairs = new ArrayList<String>();
            for (String key : newParams.keySet()){
                pairs.add(key + "=" + newParams.get(key));
            }
            String sign_data = org.apache.commons.lang3.StringUtils.join(pairs.toArray(), "&");
            String sign = request.getParameter("sign").toString();

            // 验证
            boolean result = SinaPayConfig.checkRSA(sign_data, sign, SinaPayConfig.WEIBO_RSA_PUBLIC_KEY);
            int productId = -1;
            int orderType = 1;
            int productNum = -1;
            if (result) { // 签名验证通过
                String status = request.getParameter("status").toString();
                String out_pay_id = request.getParameter("out_pay_id").toString();
                String pay_id = request.getParameter("pay_id").toString();

                BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordByInnerTradeNo(out_pay_id);
                if (baseDto.getData() == null){
                     modelAndView.setViewName(Flags.error_view);
                    return modelAndView;
                }
//                baseDto = apiInfoClient.findPayRecordById(baseDto.getData().getPayRecordId());
                PayRecordDto payRecord =baseDto.getData();

                BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(payRecord.getUserId());
                admin = userBaseDtoDto.getData();
                if (SinaPayConfig.PAY_STATUS_SUCCESS.equals(status)) { // 支付成功
                    if (payRecord != null && payRecord.getPayStatus() == BusinessConstant.PAY_STATUS_NO) {
                        ConfirmOrderViewDto orderview = orderService.doPayEnd(request,out_pay_id, admin, pay_id);
                        productId = orderview.getPackagesInfo().get(0).getProductPackageId();
                        productNum = orderview.getPackagesInfo().get(0).getPackageCount();
                        orderType = orderview.getOrderInfo().getOrderType();
//                        CommonUtils.opreateLog(request, admin, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_PAYBACK, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null,null);
                    }else if(payRecord != null && payRecord.getPayStatus() == BusinessConstant.PAY_STATUS_YES){
                        ConfirmOrderViewDto orderview = orderService.findOrderByInnerTradeNo(request,payRecord.getUserId(), out_pay_id);
                        productId = orderview.getPackagesInfo().get(0).getProductPackageId();
                        productNum = orderview.getPackagesInfo().get(0).getPackageCount();
                        orderType = orderview.getOrderInfo().getOrderType();
                    }
                    response.getWriter().print("success");
                } else if (SinaPayConfig.PAY_STATUS_CLOSED.equals(status)) { // 支付失败
                    orderService.doPayRecordFail(out_pay_id,admin, status);
//					saveUserOperateLog(admin.getUserId(), Constants.OPERATE_LOG_TYPE_PAY, "微博支付RETURN：innerTradeNo = [" + out_pay_id + "] payResult = [FAILED] reason = [" + status + "]");
                }
                if(productId == 18){
                    modelAndView.setViewName("redirect:/createAnalysis");
                }else if(productId == 32){
                    modelAndView.setViewName("redirect:/weibo/createWeiBoAnalysis?createType=1");
                }else if(productId == 21){
                    modelAndView.setViewName("redirect:/compet/productsAnalysis");
                }else if(productId == 22){
                    modelAndView.setViewName("redirect:/weiboAnalysis/weiboAnalysisIndex");
                } else{
                    modelAndView.setViewName("redirect:/home");
                }
            }
            return modelAndView;
        }
        return modelAndView;
    }



    /**
     * 微信支付异步返回
     */
    @RequestMapping(value = "/user/doWeixinNotify.action")
    @ResponseBody
    public void doWeixinNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        admin = fetchSessionAdmin(request);
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String resultStr = new String(outSteam.toByteArray(), "utf-8");
            Map<String, String> resultMap = XMLUtil.doXMLParse(resultStr);
            //通知参数
            String return_code =  resultMap.get("return_code");
            String result_code =  resultMap.get("result_code");
            log.info("return_code------------>>"+return_code);
            log.info("result_code------------>>"+result_code);

            //订单号
            String out_trade_no =  resultMap.get("out_trade_no");
            log.info("out_trade_no------------>>"+out_trade_no);
            //微信交易号
            String transaction_id =  resultMap.get("transaction_id");
            log.info("transaction_id------------>>"+transaction_id);
            String sign = resultMap.get("sign");
            log.info("sign------------>>"+sign);



            BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordByInnerTradeNo(out_trade_no);
            if (baseDto.getData() == null){
                return;
            }
            baseDto = apiInfoClient.findPayRecordById(baseDto.getData().getPayRecordId());
            if (!baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                return;
            }
            PayRecordDto payRecord = baseDto.getData();
            log.info(payRecord.toString());
            BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(payRecord.getUserId());

            // 签名验证
            if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
                if (payRecord != null && payRecord.getPayStatus() != BusinessConstant.PAY_STATUS_YES) {
                    orderService.doPayEnd(request,out_trade_no, userBaseDtoDto.getData() , return_code);
//                    CommonUtils.opreateLog(request, null, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_PAYBACK, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null,null);
//                    response.getWriter().print("success");
                }else{
                    orderService.doPayEnd(request,out_trade_no, userBaseDtoDto.getData() , return_code);
                }
            }else{
                orderService.doPayEnd(request,out_trade_no, userBaseDtoDto.getData() , return_code);
            }
            request.setAttribute("out_trade_no", out_trade_no);
            // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
            response.getWriter().write(RequestHandler.setXML("SUCCESS", ""));
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (JDOMException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 微信支付同步返回（）
     */
    @RequestMapping(value = "/pay/doWeixinReturn.action")
    public void doWeixinReturn(HttpServletRequest request,HttpServletResponse response) throws Exception {
        admin = fetchSessionAdmin(request);
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String resultStr = new String(outSteam.toByteArray(), "utf-8");
            Map<String, String> resultMap = XMLUtil.doXMLParse(resultStr);
            //通知参数
            String return_code =  resultMap.get("return_code");
            String result_code =  resultMap.get("result_code");
            log.info("return_code------------>>"+return_code);
            log.info("result_code------------>>"+result_code);

            //订单号
            String out_trade_no =  resultMap.get("out_trade_no");
            log.info("out_trade_no------------>>"+out_trade_no);
            //微信交易号
            String transaction_id =  resultMap.get("transaction_id");
            log.info("transaction_id------------>>"+transaction_id);
            String sign = resultMap.get("sign");
            log.info("sign------------>>"+sign);



            BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordByInnerTradeNo(out_trade_no);
            if (baseDto.getData() == null){
                return;
            }
            baseDto = apiInfoClient.findPayRecordById(baseDto.getData().getPayRecordId());
            if (!baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                return;
            }
            PayRecordDto payRecord = baseDto.getData();
            log.info(payRecord.toString());
            BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(payRecord.getUserId());

            // 签名验证
            if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
                if (payRecord != null && payRecord.getPayStatus() != BusinessConstant.PAY_STATUS_YES) {
                    orderService.doPayEnd(request,out_trade_no, userBaseDtoDto.getData() , return_code);
//                    CommonUtils.opreateLog(request, null, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_PAYBACK, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null,null);
//                    response.getWriter().print("success");
                }else{
                    orderService.doPayEnd(request,out_trade_no, userBaseDtoDto.getData() , return_code);
                }
            }else{
                orderService.doPayEnd(request,out_trade_no, userBaseDtoDto.getData() , return_code);
            }
            request.setAttribute("out_trade_no", out_trade_no);
            // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
            response.getWriter().write(RequestHandler.setXML("SUCCESS", ""));
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (JDOMException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "pay/goWeiXinPay")
    public ModelAndView goWeiXinPay(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws Exception{
        fetchSessionAdmin(request);
        Integer recordId = null;
        try {
            recordId =  Integer.valueOf(request.getParameter("payRecordId"));
        }catch (Exception e){

        }

        if (modelAndView == null){
            modelAndView = new ModelAndView();
        }

        if (recordId == null){
             modelAndView.setViewName(Flags.error_view);
        }

        BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordById(recordId);
        if (!baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
             modelAndView.setViewName(Flags.error_view);
        }
        PayRecordDto payRecord = baseDto.getData();

        if (payRecord != null) {
            String ticket = null;
            String ticketObj = RedisUtils.getAttribute(RedisUtils.generateJedisKey("_"+SystemConstants.WEIXIN_TICKET_KEY));
            if (ticketObj != null){
                ticket = ticketObj;
            }
            if (ticket == null || "".equals(ticket.trim())) {
                //获取jsapi_ticket
                ticket = WeixinJsPayUtils.getJsapiTicket();
                //SessionUtil.setAttribute(SysConfig.memoCacheName + "_" + Constants.WEIXIN_TICKET_KEY, Constants.WEIXIN_TICKET_KEY, ticket, new Date().getTime() + Long.parseLong("7200"));
                RedisUtils.setAttribute(RedisUtils.generateJedisKey("_"+SystemConstants.WEIXIN_TICKET_KEY), ticket, 8);
            }

            if (ticket != null && !"".equals(ticket.trim())) {
                //订单号
                String out_trade_no = new String(payRecord.getInnerTradeNo().getBytes("ISO-8859-1"), "utf-8");


                String code = request.getParameter("code");
                String state = request.getParameter("state");
                log.info("code-======"+code+"===========state======"+state);
//                String url ="http://api-open-beta.wrd.cn/pay/goWeiXinPay?payRecordId="+payRecord.getPayRecordId()+"&code="+code+"&state="+state;
                String url ="http://m.wrd.cn/view/pay/goWeiXinPay.action?payid="+payRecord.getPayRecordId()+"&code="+code+"&state="+state;

                // 注意 URL 一定要动态获取，不能 hardcode
                Map<String, String> ret = WeixinJsPayUtils.sign(ticket, url);
                ret.put("package", out_trade_no);
                for (Map.Entry entry : ret.entrySet()) {
                    log.info(entry.getKey() + ", " + entry.getValue());
                }
                log.info("ret==========================="+ret);

                String noncestr = ret.get("nonceStr");//生成随机字符串
                String timestamp = ret.get("timestamp");//生成1970年到现在的秒数.
                //state 可以传递你的订单号.然后根据订单号 查询付款金额.我就不详细写了.
                String totalAmount = String.valueOf((int)Math.rint(payRecord.getTotalFee() * 100));//订单金额
                //微信金额 以分为单位.这是第二坑.如果不注意.页面的报错.你基本看不出来.因为他提示系统升级,正在维护.扯淡.....
                String product_name="No." + payRecord.getInnerTradeNo();//订单名称
                //获取jsapi_ticket.此参数是为了生成 js api  加载时候的签名用.必须.jsapi_ticket只会存在7200秒.并且有时间限制,(好像一年还只能调用两万次.所以一定要缓存.)这是第三坑.
                //可以在java中模拟url请求.就能获取access_token 然后根据access_token 取得 jsapi_ticket,但一定要缓存起来..这个代码.我只提供获取.缓存你们自己处理.
                //SendGET方法我会在后面放出

                //下面就到了获取openid,这个代表用户id.
                //获取openID
                String openid = WeixinJsPayUtils.getOpenId(code);
                RequestHandler reqHandler = new RequestHandler(request, response);
                //初始化     RequestHandler  类可以在微信的文档中找到.还有相关工具类
                reqHandler.init();
                reqHandler.init(WeiXinPayConfig.APPID, WeiXinPayConfig.APPSECRET, WeiXinPayConfig.KEY, "");
                //执行统一下单接口 获得预支付id
                reqHandler.setParameter("appid",WeiXinPayConfig.APPID);
                reqHandler.setParameter("mch_id", WeiXinPayConfig.MCHID);  //商户号
                reqHandler.setParameter("nonce_str", noncestr);            //随机字符串
                reqHandler.setParameter("body", product_name);             //商品描述(必填.如果不填.也会提示系统升级.正在维护我艹.)
                reqHandler.setParameter("out_trade_no", out_trade_no);     //商家订单号
                // 测试金额控制
                if (!"www".equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME"))|| admin.getUserId().equals("2161010") || admin.getUserId().equals("27541")){
                    totalAmount = "1";
                }
                reqHandler.setParameter("total_fee", totalAmount);         //商品金额,以分为单位
                reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());   //用户的公网ip  IpAddressUtil.getIpAddr(request)
                //下面的notify_url是用户支付成功后为微信调用的action  异步回调.
                reqHandler.setParameter("notify_url", WeiXinPayConfig.NOTIFY_URL);
                reqHandler.setParameter("trade_type", "JSAPI");
                //------------需要进行用户授权获取用户openid-------------
                reqHandler.setParameter("openid", openid);   //这个必填.
                //这里只是在组装数据.还没到执行到统一下单接口.因为统一下单接口的数据传递格式是xml的.所以才需要组装.
                String requestUrl = reqHandler.getRequestURL();
                // requestUrl 例子:
					 /*
					<xml><appid>wx2421b1c4370ec43b</appid><attach>支付测试</attach><body>JSAPI支付测试</body><mch_id>10000100</mch_id><nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str><notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url><openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid><out_trade_no>1415659990</out_trade_no><spbill_create_ip>14.23.150.211</spbill_create_ip><total_fee>1</total_fee><trade_type>JSAPI</trade_type><sign>0CB01533B8C1EF103065174F50BCA001</sign></xml>
					*/
                log.info("requestUrl==================="+requestUrl);
                log.info("requestUrl==================="+requestUrl);
                //统一下单接口提交  xml格式
                URL orderUrl = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
                HttpURLConnection conn = (HttpURLConnection) orderUrl.openConnection();
                conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
                conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
                conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
                conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
                conn.setUseCaches(false); // Post 请求不能使用缓存
                // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
                conn.setRequestProperty("Content-Length",requestUrl.length()+"");
                String encode = "utf-8";
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), encode);
                out.write(requestUrl.toString());
                out.flush();
                out.close();
                String result = WeixinJsPayUtils.getOut(conn);
                log.info("result=========返回的xml============="+result);
                log.info("result=========返回的xml============="+result);
                Map<String, String> orderMap = XMLUtil.doXMLParse(result);
                log.info("orderMap==========================="+orderMap);
                log.info("orderMap==========================="+orderMap);
                //得到的预支付id
                String prepay_id = orderMap.get("prepay_id");
                SortedMap<String,String> params = new TreeMap<String,String>();
                params.put("appId", WeiXinPayConfig.APPID);
                params.put("timeStamp",timestamp);
                params.put("nonceStr", noncestr);
                params.put("package", "prepay_id="+prepay_id);
                params.put("signType", "MD5");
                //生成支付签名,这个签名 给 微信支付的调用使用
                String paySign =  reqHandler.createSign(params);
//                request.setAttribute("paySign", paySign);
//                request.setAttribute("appId", WeiXinPayConfig.APPID);
//                request.setAttribute("timeStamp", timestamp);   			//时间戳
//                request.setAttribute("nonceStr", noncestr);     			//随机字符串
//                request.setAttribute("signType", "MD5");        			//加密格式
//                request.setAttribute("out_trade_no", out_trade_no);          //订单号
//                request.setAttribute("package", "prepay_id="+prepay_id);//预支付id ,就这样的格式.
//                //这个签名.主要是给加载微信js使用.别和上面的搞混了.
//                request.setAttribute("signature", ret.get("signature"));

                modelAndView.addObject("paySign",paySign);
                modelAndView.addObject("appId",WeiXinPayConfig.APPID);
                modelAndView.addObject("timeStamp",timestamp);
                modelAndView.addObject("nonceStr",noncestr);
                modelAndView.addObject("signType","MD5");
                modelAndView.addObject("package","prepay_id="+prepay_id);
                modelAndView.addObject("out_trade_no",out_trade_no);
                modelAndView.addObject("signature",ret.get("signature"));

                ConfirmOrderViewDto order = orderService.findOrderByInnerTradeNo(request,payRecord.getUserId(), out_trade_no);
                if(order != null){
//                    request.setAttribute("packageId", order.getPackagesInfo().get(0).getProductPackageId());
//                    request.setAttribute("packageNum", order.getPackagesInfo().get(0).getPackageCount());
//                    request.setAttribute("orderType", order.getOrderInfo().getOrderType());
                    modelAndView.addObject("packageId",order.getPackagesInfo().get(0).getProductPackageId());
                    modelAndView.addObject("packageNum",order.getPackagesInfo().get(0).getPackageCount());
                    modelAndView.addObject("orderType",order.getOrderInfo().getOrderType());
                }
                log.info("modelAndView==========================="+modelAndView.getModel().toString());
                modelAndView.setViewName("view/pay/wxpay");
            }
        }
//        CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_GOPAY, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null,null);
        return modelAndView;

    }




}
