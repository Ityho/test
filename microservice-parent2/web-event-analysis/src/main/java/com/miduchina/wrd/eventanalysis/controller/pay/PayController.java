package com.miduchina.wrd.eventanalysis.controller.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.analysis.weiboanalysis.WeiboAnalysisRes;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.keyword.Keywords;
import com.miduchina.wrd.dto.log.LoginLog;
import com.miduchina.wrd.dto.pay.*;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.config.AlipayConfig;
import com.miduchina.wrd.eventanalysis.config.SinaPayConfig;
import com.miduchina.wrd.eventanalysis.config.WyqBusinessConfig;
import com.miduchina.wrd.eventanalysis.constant.Constants;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.log.model.OperateLogOrderInfo;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.*;
import com.miduchina.wrd.po.eventanalysis.BaseView;
import com.miduchina.wrd.po.order.PayRecord;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping(value = "/pay")
@Controller
public class PayController extends BaseController {
    @Autowired
    OrderClientService orderService;


    @InitBinder("myProduct")
    public void myProducts(WebDataBinder binder){
        binder.setFieldDefaultPrefix("myProduct.");
    }

    @RequestMapping(value = "/getOrderFeeV2")
    @ResponseBody
    public ModelDto getOrderFeeV2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        fetchSessionAdmin(request);

        if (Constants.PACKAGE_LIST_ALL.size() == 0){
            fetchProductlistV2(new ModelAndView(),request,0);
        }
        if (admin != null) {

            boolean useCredit = Boolean.parseBoolean(request.getParameter("useCredit"));
            Integer orderType = Integer.valueOf(request.getParameter("orderType"));
            boolean buyCredit = Boolean.parseBoolean(request.getParameter("buyCredit"));
            Integer infoDataId = null;
            if (StringUtils.isNotBlank(request.getParameter("infoDataId"))){
                 infoDataId = Integer.valueOf(request.getParameter("infoDataId"));
            }
            /**
             * 产品价格表的ID
             */
            request.getParameter("productPackageId");
            Integer productPackageId= Integer.valueOf(org.apache.commons.lang3.StringUtils.isBlank(request.getParameter("productPackageId"))? "-1":request.getParameter("productPackageId"));
            String payType = "00";
            if (useCredit){
                payType= org.apache.commons.lang3.StringUtils.isBlank(request.getParameter("payType"))?"00":request.getParameter("payType");
            }else {
                payType= org.apache.commons.lang3.StringUtils.isBlank(request.getParameter("payType"))?"01":request.getParameter("payType");
            }

            Integer fenxiWeiboId = Integer.valueOf(org.apache.commons.lang3.StringUtils.isBlank(request.getParameter("fenxiWeiboId"))? "-1":request.getParameter("fenxiWeiboId"));
            String keywordIds = request.getParameter("keywordIds");
            /**
             * 热度日报ID
             */
            Integer heatReportId = Integer.valueOf(org.apache.commons.lang3.StringUtils.isBlank(request.getParameter("heatReportId"))? "-1":request.getParameter("heatReportId"));
            /**
             * 关键词
             */
            String keywords = request.getParameter("keywords");

            List<ConfirmOrderPackageDto> confirmOrderVOPackageElements = new ArrayList<ConfirmOrderPackageDto>();

            List<ProductPackage> list = new ArrayList<>();
            String str ="";
            String str1 = "";
            if (buyCredit){
                /**
                 * 购买微积分
                 */
                list.addAll(Constants.PACKAGE_LIST_CREDIT);
                str = "myProducts['product_";
                str1 ="'].keywordPackageNum";
            }else {
                /**
                 * 购买产品
                 */
                list.addAll(Constants.PACKAGE_TYPE_BIGDATA_REPORT_IDS);
                list.addAll(Constants.PACKAGE_LIST_ANALYSIS);
                list.addAll(Constants.PACKAGE_LIST_WEIBO_ANALYSIS);
                list.addAll(Constants.PACKAGE_LIST_BRIEF);
                list.addAll(Constants.PACKAGE_LIST_PRODUCT_ANALYSIS);
                list.addAll(Constants.PACKAGE_LIST_SINGLE_WEIBO_ANALYSIS);
                str = "myProducts['product";
                str1 ="'].keywordPackageNum";
            }

            log.info("getOrderFeeV2.list {}",JSONObject.toJSONString(list));


            for(int i=0;i<list.size();i++){
                ProductPackage productPackage = list.get(i);

                String numStr = request.getParameter(str+productPackage.getProductPackageId()+str1);
                if (org.apache.commons.lang3.StringUtils.isNotBlank(numStr)){
                    Integer num = Integer.valueOf(numStr);
                    if (num > 0){
                        ConfirmOrderPackageDto confirmOrderVOPackageElement = new ConfirmOrderPackageDto();
                        confirmOrderVOPackageElement.setProductPackageId(productPackage.getProductPackageId());
                        confirmOrderVOPackageElement.setPackageCount(Integer.valueOf(num));
                        confirmOrderVOPackageElements.add(confirmOrderVOPackageElement);
                    }
                }
            }
            ConfirmOrderDto confirmOrderDto = new ConfirmOrderDto();
            if(infoDataId!=null){
                confirmOrderDto.setBigReportId(infoDataId);
            }
            confirmOrderDto.setOrderType(orderType);
            if(orderType ==  BusinessConstant.ORDER_TYPE_CONTINUE && productPackageId != -1){
                confirmOrderDto.setRenewPackageId(productPackageId);
            }
            confirmOrderDto.setPackages(confirmOrderVOPackageElements);
            confirmOrderDto.setUseCredit(useCredit);
            confirmOrderDto.setPayChannel(payType);
            if (fenxiWeiboId != -1){
                confirmOrderDto.setFenxiWeiboId(fenxiWeiboId);
            }
            confirmOrderDto.setKeywordIds(keywordIds);

            if(heatReportId != -1){
                confirmOrderDto.setHeatReportIds(heatReportId.toString());
            }
            if(org.apache.commons.lang3.StringUtils.isNotBlank(keywords)){
                Keywords ks = JSON.parseObject(keywords, Keywords.class);
                if(ks.getDate() == null){
                    ks.setDate(24);
                }
                Calendar c = Calendar.getInstance();
                ks.setEndTime(c.getTime());
                c.add(Calendar.HOUR_OF_DAY, -ks.getDate());
                ks.setStartTime(c.getTime());

                confirmOrderDto.setKeywords(JSON.toJSONString(ks));
            }
            OrderFeeViewDto fee=orderService.findOrderFeeByUserId(request,confirmOrderDto, admin);
            return new ModelDto(1, fee);
        }
        return null;
    }
    /**
     * 订单确认
     *
     * @throws Exception
     */
    @RequestMapping(value = "/confirmOrderV3")
    @ResponseBody
    public Object confirmOrderV3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        admin = fetchSessionAdmin(request);
        if (Constants.PACKAGE_LIST_ALL.size() == 0){
            fetchProductlistV2(new ModelAndView(),request,0);
        }
        if (admin != null ) {
            boolean useCredit = Boolean.parseBoolean(request.getParameter("useCredit"));
            Integer orderType = Integer.valueOf(request.getParameter("orderType"));
            boolean buyCredit = Boolean.parseBoolean(request.getParameter("buyCredit"));
            Integer infoDataId = null;
            if (StringUtils.isNotBlank(request.getParameter("infoDataId"))){
                infoDataId = Integer.valueOf(request.getParameter("infoDataId"));
            }
            String payTaskTicket = request.getParameter("payTaskTicket");
            Long wfx_createTime = null;
            if (StringUtils.isNotBlank(request.getParameter("wfx_createTime"))){
                wfx_createTime = Long.valueOf(request.getParameter("wfx_createTime"));
            }
            if(wfx_createTime!=null){
                Long intervalTime=System.currentTimeMillis()-wfx_createTime;
                log.info("intervalTime="+intervalTime);
                if(intervalTime>(15*60*1000)){
                    return new ModelDto(6, "");
                }
            }
            /**
             * 产品价格表的ID
             */
            Integer productPackageId= Integer.valueOf(StringUtils.isBlank(request.getParameter("productPackageId"))? "-1":request.getParameter("productPackageId"));
            String payType = "00";
            if (useCredit){
                payType= org.apache.commons.lang3.StringUtils.isBlank(request.getParameter("payType"))?"00":request.getParameter("payType");
            }else {
                payType= org.apache.commons.lang3.StringUtils.isBlank(request.getParameter("payType"))?"01":request.getParameter("payType");
            }

            Integer fenxiWeiboId = Integer.valueOf(StringUtils.isBlank(request.getParameter("fenxiWeiboId"))? "-1":request.getParameter("fenxiWeiboId"));
            String keywordIds = request.getParameter("keywordIds");
            /**
             * 热度日报ID
             */
            Integer heatReportId = Integer.valueOf(StringUtils.isBlank(request.getParameter("heatReportId"))? "-1":request.getParameter("heatReportId"));
            /**
             * 关键词
             */
            String keywords = request.getParameter("keywords");
            List<ConfirmOrderPackageDto> confirmOrderVOPackageElements = new ArrayList<ConfirmOrderPackageDto>();

            List<ProductPackage> list = new ArrayList<>();

            String str ="";
            String str1 = "";
            if (buyCredit){
                /**
                 * 购买微积分
                 */
                list.addAll(Constants.PACKAGE_LIST_CREDIT);
                str = "myProducts['product_";
                str1 ="'].keywordPackageNum";
            }else {
                /**
                 * 购买产品
                 */
                list.addAll(Constants.PACKAGE_TYPE_BIGDATA_REPORT_IDS);
                list.addAll(Constants.PACKAGE_LIST_ANALYSIS);
                list.addAll(Constants.PACKAGE_LIST_WEIBO_ANALYSIS);
                list.addAll(Constants.PACKAGE_LIST_BRIEF);
                list.addAll(Constants.PACKAGE_LIST_PRODUCT_ANALYSIS);
                list.addAll(Constants.PACKAGE_LIST_SINGLE_WEIBO_ANALYSIS);
                str = "myProducts['product";
                str1 ="'].keywordPackageNum";
            }

            for(int i=0;i<list.size();i++){
                ProductPackage productPackage = list.get(i);
                String numStr =  request.getParameter(str+productPackage.getProductPackageId()+str1);
                if (org.apache.commons.lang3.StringUtils.isNotBlank(numStr)){
                    Integer num = Integer.valueOf(numStr);
                    if (num > 0){
                        ConfirmOrderPackageDto confirmOrderVOPackageElement = new ConfirmOrderPackageDto();
                        confirmOrderVOPackageElement.setProductPackageId(productPackage.getProductPackageId());
                        confirmOrderVOPackageElement.setPackageCount(Integer.valueOf(num));
                        confirmOrderVOPackageElements.add(confirmOrderVOPackageElement);
                    }
                }
            }

            ConfirmOrderDto confirmOrderDto = new ConfirmOrderDto();
            if(infoDataId!=null){
                confirmOrderDto.setBigReportId(infoDataId);
            }
            confirmOrderDto.setPackages(confirmOrderVOPackageElements);
            confirmOrderDto.setOrderType(orderType);
            //是否续费
            if(orderType == BusinessConstant.ORDER_TYPE_CONTINUE){
                confirmOrderDto.setRenewPackageId(productPackageId);
            }

            confirmOrderDto.setUseCredit(useCredit);
            confirmOrderDto.setPayChannel(payType);
            confirmOrderDto.setFenxiWeiboId(fenxiWeiboId);
            confirmOrderDto.setKeywordIds(keywordIds);
            //热度日报
            if(heatReportId != null){
                confirmOrderDto.setHeatReportIds(heatReportId.toString());
            }
            if(StringUtils.isNotBlank(keywords)){
                Keywords ks = JSON.parseObject(keywords, Keywords.class);
                if(ks.getDate() == null){
                    ks.setDate(24);
                }
                Calendar c = Calendar.getInstance();
                ks.setEndTime(c.getTime());
                c.add(Calendar.HOUR_OF_DAY, -ks.getDate());
                ks.setStartTime(c.getTime());

                confirmOrderDto.setKeywords(JSON.toJSONString(ks));
            }
            //创建订单
            Map<String, Object> map = orderService.doComfirmOrder(request,confirmOrderDto, admin);
            //去支付
            if (map != null) {
                if(CodeConstant.SUCCESS_CODE.equals(map.get("code"))){
                    PayRecord payRecord = (PayRecord) map.get("payRecord");
                    if(payRecord != null){
                        if(payRecord.getTotalFee()!= null && payRecord.getTotalFee()>0){
                            return  new ModelDto(1, payRecord.getPayRecordId());
                        }else{
                            ConfirmOrderViewDto orderview = orderService.doPayEnd(request,payRecord.getInnerTradeNo(), admin, null);
                            List<ConfirmOrderViewPackageElementDto> packagesInfo = orderview.getPackagesInfo();
                            int productId=0;
                            if (packagesInfo.size()==1) {
                                productId = orderview.getPackagesInfo().get(0).getProductPackageId();
                            }else {
                                productId = 128;
                            }

                            orderType = orderview.getOrderInfo().getOrderType();
                            if(orderType == BusinessConstant.ORDER_TYPE_CONTINUE){
                                return  new ModelDto(3, productId);
                            }else{
                                switch (productId) {
                                    case 22://微博传播效果分析
                                        Map<String, Object> map2=new HashMap<String, Object>();
                                        map2.put("userEncode", getUserEncodeNew(admin.getUserId()));
                                        map2.put("ticket", payTaskTicket);
                                        map2.put("platformTag", "wyq");
                                        String result= Utils.sendWrdIntraBusinessAPIPOST(request, "swaConfirmTask", map2);
                                        WeiboAnalysisRes weiboAnalysisRes = JSONObject.parseObject(result, WeiboAnalysisRes.class);
                                        if(weiboAnalysisRes!=null && weiboAnalysisRes.getCode().equals("0000")){
                                            log.info("任务确认成功");
                                        }
                                        log.info("Message"+weiboAnalysisRes.getMessage());
                                        break;

                                    default:
                                        break;
                                }
                                Map<String, Object> result = new HashMap<>();
                                result.put("status", 2);
                                result.put("obj", productId);
                                result.put("oid", orderview.getOrderInfo().getOrderRecordId());

                                //writeJSON(new ModelDto(2, productId));
                                return result;
                            }
                        }
                    }
                }else if(map.get("code").equals("30008")){
                    Integer payRecordId = orderService.findPayRecordByUserIdAndPackageId(Integer.valueOf(admin.getUserId()), productPackageId);
                    return new ModelDto(1, payRecordId);
                }else{
                    return new ModelDto(5, "");
                }
            }
        }
        return null;
    }
    /**
     * 查询订单页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "orderLisView")
    public ModelAndView orderLisView(ModelAndView modelAndView,HttpServletRequest request) throws Exception{
        fetchSessionAdmin(request);
        BaseDto<LoginLog> baseDto = apiInfoClient.quertyUserPlatform(Integer.valueOf(admin.getUserId()));
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
            int loginPlatform = baseDto.getData().getLoginType();
            modelAndView.addObject("loginPlatform",loginPlatform);
        }
        modelAndView.addObject("baseUrl",Utils.getBaseUrl(request));
        modelAndView.setViewName("view/pay/orderlist");
        return modelAndView;
    }
    /**
     * 查询订单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "orderList")
    @ResponseBody
    public void orderList(HttpServletRequest request) throws Exception{
        fetchSessionAdmin(request);
//        List<OrderListViewElement> orderlist = orderRecordBean.doOrderList(orderType, payStatus,startDate,endDate,null,null,admin);
//        if(!orderlist.isEmpty()){
//            writeJSON(new ModelDto(1,orderlist));
//        }else{
//            writeJSON(new ModelDto(0));
//        }

    }


    /**
     * 支付宝支付
     * @param request
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    @RequestMapping(value = "goPay")
    @ResponseBody
    public void goPay(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        try {
            fetchSessionAdmin(request);
            log.info("-------------------now goPay Start--------------");

            Integer recordId = null;
            try {
                recordId =  Integer.valueOf(request.getParameter("payRecordId"));
            }catch (Exception e){

            }
            if (recordId == null){
                return;
            }

            BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordById(recordId);
            if (!baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                return;
            }

            PayRecordDto payRecord = baseDto.getData();
            log.info("-------------------payRecord--------------"+payRecord);

            // 支付类型
            String payment_type = "1";
            // 服务器异步通知页面路径
            String notify_url =  SysConfig.cfgMap.get("H5_NOTIFY_URL").trim();
            // 页面跳转同步通知页面路径
            String return_url =  SysConfig.cfgMap.get("H5_RETURN_URL").trim();
            // 卖家支付宝帐户
            String seller_email = SysConfig.cfgMap.get("SELLER_EMAIL").trim();
            // 商户订单号
            //String out_trade_no = new String(payRecord.getInnerTradeNo().getBytes(UTF_8), "gbk");
            String out_trade_no = payRecord.getInnerTradeNo();
            // 订单名称
            String subject = payRecord.getPayName();
            // 付款金额
            //String total_fee = new String((payRecord.getTotalFee() + "").getBytes(UTF_8), "gbk");
            String total_fee = String.valueOf(payRecord.getTotalFee());
            // 商品展示地址
            String show_url = SysConfig.cfgMap.get("SHOW_URL").trim();

            // 商品描述
            String WIDbody = payRecord.getPayDescription();
            String anti_phishing_key = "";
            // 若要使用请调用类文件submit中的query_timestamp函数 //客户端的IP地址
            String exter_invoke_ip = "";

            // 把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", AlipayConfig.service);//"create_direct_pay_by_user"
            sParaTemp.put("partner", AlipayConfig.partner);
            sParaTemp.put("_input_charset", AlipayConfig.input_charset);
            sParaTemp.put("payment_type", payment_type);
            sParaTemp.put("notify_url", notify_url);
            sParaTemp.put("return_url", return_url);
            sParaTemp.put("seller_email", seller_email);
            sParaTemp.put("out_trade_no", out_trade_no);
            sParaTemp.put("subject", "No." + payRecord.getInnerTradeNo());
            if (!"www".equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME"))|| admin.getUserId().equals("2161010") || admin.getUserId().equals("27541") || admin.getUserId().equals("272")){
                total_fee = "0.01";
            }
            sParaTemp.put("total_fee", total_fee);
            sParaTemp.put("body", "No." + payRecord.getInnerTradeNo());
//            sParaTemp.put("show_url", show_url);
//            sParaTemp.put("anti_phishing_key", anti_phishing_key);
//            sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
//            sParaTemp.put("notify_type", "trade_status_sync");
            StringBuilder outHtml = new StringBuilder("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
            outHtml.append("<html>\r\n");
            outHtml.append("	<head>\r\n");
            outHtml.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset="+AlipayConfig.input_charset+"\">\r\n");
            outHtml.append("		<title>支付宝即时到账交易接口</title>\r\n");
            outHtml.append("	</head>\r\n");

            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "post", "确认");
            outHtml.append(sHtmlText);
            log.info("goPay : sHtmlText = [" + sHtmlText + "]");
            response.setContentType("text/html; charset="+AlipayConfig.input_charset);
            response.getWriter().println(outHtml.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 微博支付
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "goSinaPay")
    @ResponseBody
    public void goSinaPay(HttpServletRequest request,HttpServletResponse response) throws Exception{
        fetchSessionAdmin(request);
        Integer recordId = null;
        try {
            recordId =  Integer.valueOf(request.getParameter("payRecordId"));
        }catch (Exception e){

        }
        if (recordId == null){
            return;
        }

        BaseDto<PayRecordDto> baseDto = apiInfoClient.findPayRecordById(recordId);
        if (!baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return;
        }
        PayRecordDto payRecord = baseDto.getData();
        if (payRecord != null) {
            // 服务器异步通知页面路径
            String sina_notify_url = SysConfig.cfgMap.get("H5_SINA_NOTIFY_URL").trim(); // "http://h5.51wyq.cn/view/user/doNotify.action";
            // 服务器同步通知页面路径
            String sina_return_url = SysConfig.cfgMap.get("H5_SINA_RETURN_URL").trim(); // "http://h5.51wyq.cn/view/pay/doReturn.action";
            // 组装参数
            Map<String, String> params = new HashMap<String, String>();
            params.put("sign_type", SinaPayConfig.SIGN_TYPE);
            params.put("appkey", SinaPayConfig.APP_KEY);
            params.put("seller_id", SinaPayConfig.SELLER_ID);
            params.put("notify_url", sina_notify_url);
            params.put("return_url", sina_return_url);
            params.put("out_pay_id", payRecord.getInnerTradeNo());
            params.put("subject", "No." + payRecord.getInnerTradeNo());
            String totalAmount = String.valueOf((int)Math.rint(payRecord.getTotalFee() * 100));
            if (!"www".equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME"))|| admin.getUserId().equals("2161010") || admin.getUserId().equals("27541")){
                totalAmount = "0.01";
            }
            params.put("total_amount", totalAmount);

            // 生成签名
            params.put("sign", SinaPayConfig.generateRsaSign(params));
            // 生成请求URL
            StringBuilder outHtml = new StringBuilder("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
            outHtml.append("<html>\r\n");
            outHtml.append("	<head>\r\n");
            outHtml.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
            outHtml.append("		<title>微博支付收银台</title>\r\n");
            outHtml.append("	</head>\r\n");
            /**
             * 记录日志
             */
//            CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_GOPAY, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null,null);

            String sHtmlText = SinaPayConfig.buildRequest(params, "post");
            log.info("goSinaPay params==="+params.toString());
            outHtml.append(sHtmlText);
            response.setContentType("text/html; charset=utf-8");
            log.info("goSinaPay outHtml==="+outHtml.toString());
            response.getWriter().println(outHtml.toString());
        }
    }

    /**
     * 微信支付
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "goWeiXinPay")
    @ResponseBody
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
                if (!"www".equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME")) || admin.getUserId().equals("2161010") || admin.getUserId().equals("27541")){
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

    private static final String UTF_8 = "UTF-8";
    /**
     * 支付完成后的同步处理方法
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "doReturn")
    @ResponseBody
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
//                    valueStr = URLDecoder.decode(valueStr, AlipayConfig.input_charset);
//                }
                params.put(name, valueStr);
                log.info("doReturn --> [" + name + "] = [" + valueStr + "]");
            }

            // 获取支付宝的通知返回参数
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(UTF_8), "GBK");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes(UTF_8), "GBK");

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes(UTF_8), "GBK");
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
//					saveUserOperateLog(admin.getUserId(), Constants.OPERATE_LOG_TYPE_PAY, "支付宝RETURN：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + trade_status + "]");
                }

                // 该页面可做页面美工编辑
                // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                // ////////////////////////////////////////////////////////////////////////////////////////
            } else {
                // 该页面可做页面美工编辑
                BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(p.getUserId());
                orderService.doPayRecordFail(out_trade_no,userBaseDtoDto.getData() ,"verify_result=" + verify_result);
//				saveUserOperateLog(admin.getUserId(), Constants.OPERATE_LOG_TYPE_PAY, "支付宝RETURN：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + verify_result + "]");
                log.info("do return fail");
            }
            log.info("do return end");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    /**
     * 微博支付同步返回
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "doSinaReturn")
    @ResponseBody
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
     * 线下支付
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "goOffLinePay")
    @ResponseBody
    public String goOffLinePay(HttpServletRequest request,HttpServletResponse response) throws Exception {
        fetchSessionAdmin(request);


        Integer recordId = null;
        try {
            recordId =  Integer.valueOf(request.getParameter("payRecordId"));
        }catch (Exception e){

        }
        if (recordId == null){
            return "";
        }
        String orderNo = request.getParameter("orderNo");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("orderNo", orderNo);
        String rtnStr = Utils.sendWrdIntraBusinessAPIPOST(request, "offLineOrderInfo", params);
        log.info("goOffLinePay rtnStr = [" + rtnStr + "]");
        if (!StringUtils.isBlank(rtnStr)) {
//            OffLineOrderInfoRes offLineOrderInfoRes = JSONObject.parseObject(rtnStr, OffLineOrderInfoRes.class);
//            if (offLineOrderInfoRes != null && Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(offLineOrderInfoRes.getCode())) {
//                offLineOrderInfoResElement = offLineOrderInfoRes.getOffLineInfo();
//                if (offLineOrderInfoResElement != null) {
//                    Map<String,Object> dataMap = new HashMap<String, Object>();
//                    String userName = admin.getNickname();
//                    if(userName==null||"".equals(userName)){
//                        userName = admin.getUsername();
//                    }
//                    dataMap.put("username", userName);
//                    dataMap.put("kc", offLineOrderInfoResElement.getKeywordCount()); // 监测方案
//                    dataMap.put("ca", offLineOrderInfoResElement.getCreditAmount()); // 微积分
//                    dataMap.put("an", offLineOrderInfoResElement.getAnalysisCount() == -1 ? "无限制" : offLineOrderInfoResElement.getAnalysisCount()); // 全网事件分析
//                    dataMap.put("wa", offLineOrderInfoResElement.getWeiboAnalysisCount() == -1 ? "无限制" : offLineOrderInfoResElement.getWeiboAnalysisCount()); // 微博事件分析
//                    dataMap.put("br", offLineOrderInfoResElement.getBriefCount() == -1 ? "无限制" : offLineOrderInfoResElement.getBriefCount()); // 简报制作
//                    dataMap.put("pa", offLineOrderInfoResElement.getProductAnalysisCount() == -1 ? "无限制" : offLineOrderInfoResElement.getProductAnalysisCount()); // 竞品分析
//                    dataMap.put("swa", offLineOrderInfoResElement.getSingleWeiboAnalysisCount()); // 单条微博分析
//                    dataMap.put("ed", offLineOrderInfoResElement.getExportDataCount()); // 导出数据
//                    dataMap.put("cc", offLineOrderInfoResElement.getCommentsCount()); // 评论分析
//                    dataMap.put("price", "￥ " + order.getTotalFee());
//                    dataMap.put("orderno", order.getOrderNo());
//
//                    Date date = new Date();
//                    String ftlDir = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/") + "/template/word/";
//                    String filePath = systemConfig.getUploadPath() + "offLine/" + new SimpleDateFormat("yyyyMM").format(date) + "/" + new SimpleDateFormat("dd").format(date) + "/" + new SimpleDateFormat("HH").format(date) + "/";
//                    String fileName = String.valueOf(System.currentTimeMillis()) + ".doc";
//                    offLineFtl = CommonUtils.exportWord(ftlDir, "offLine_v2.ftl", filePath, fileName, dataMap);
//                    offLineFtl = offLineFtl.replace(systemConfig.getUploadPath(), systemConfig.getFileviewPath());

//                    OperateLogOrderInfo operateLogOrderInfo = new OperateLogOrderInfo();
//                    operateLogOrderInfo.setPayChannel(Constants.PAY_CHANNEL_OFFLINE);
//                    CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_PAY_GOPAY, Constants.OPERATE_LOG_OPERATE_TYPE_C, operateLogOrderInfo, null, null);

//                    return "goOffLinePay";
//                }
//            }
        }
        return "error";
    }


    /**
     * 取消订单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "cancelOrder")
    @ResponseBody
    public ModelDto cancelOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
        admin = fetchSessionAdmin(request);
        ModelDto dto = new ModelDto();
        if (admin != null){

            String orderRecordId = request.getParameter("order.orderRecordId");

            Map<String, Object> params = WyqBusinessConfig.getDataInitMap();
            params.put("userEncode", WyqBusinessConfig.encryptString(admin.getUserId().toString()));
            params.put("orderRecordId", orderRecordId);
            String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,"orderCancel",params);
            BaseView baseView = JSON.parseObject(jsonStr, BaseView.class);
            log.info("doPayEnd params = code" + baseView.getCode() + "message=" + baseView.getMessage());

            if (baseView != null && baseView.getCode().equals(CodeConstant.SUCCESS_CODE)){
                dto.setMsg(baseView.getMessage());
                dto.setStatus(1);
            }else {
                dto.setStatus(0);
            }
        }else {
            dto.setStatus(0);
        }
        return dto;
    }


    /**
     * 微博支付异步返回
     *
     * @throws Exception
     */
    @RequestMapping(value = "doSinaNotify")
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
     * 微信支付异步返回
     */
    @RequestMapping(value = "doWeixinNotify")
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


    @RequestMapping(value = "doNotify")
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
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(UTF_8), "GBK");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes(UTF_8), "GBK");

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes(UTF_8), "GBK");
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
}
