package com.miduchina.wrd.eventanalysis.controller.weibospread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.analysis.weiboanalysis.Weibo;
import com.miduchina.wrd.dto.analysis.weiboanalysis.WeiboAnalysisRes;
import com.miduchina.wrd.dto.analysis.weiboanalysis.WeiboTaskCreateRes;
import com.miduchina.wrd.dto.analysis.weiboanalysis.WeiboTaskCreateVO;
import com.miduchina.wrd.dto.keyword.Keywords;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.pay.ConfirmOrderDto;
import com.miduchina.wrd.dto.pay.ConfirmOrderPackageDto;
import com.miduchina.wrd.dto.pay.MyProductDto;
import com.miduchina.wrd.dto.pay.OrderFeeViewDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.dto.user.UserRightsRechargeRecord;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.log.model.UserRes;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;
import com.miduchina.wrd.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping(value = "/weiboAnalysis")
@Controller
@Slf4j
public class SingleWeiboAnalysisController extends BaseController{
    // 62进制字典
    private static String[] keys = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };



    /**
     * 我的分析记录(微博传播效果分析列表)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/history")
    public ModelAndView history(ModelAndView modelAndView,HttpServletRequest request) throws Exception {
        fetchSessionAdmin(request);
        if (admin != null) {
            BaseDto baseDto=apiInfoClient.queryWeiboAnalysisListByUserId(String.valueOf(admin.getUserId()));
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                List<WeiBoAnalysisTask> taskList= (List<WeiBoAnalysisTask>) baseDto.getData();
                if(taskList!=null && taskList.size()>0){
                    modelAndView.addObject("fenxiWeibos",taskList);
                }
            }
            modelAndView.setViewName("view/singleWeiboAnalysis/history");
        }
        return modelAndView;
    }
    /**
     * 删除分析
     *
     * @throws Exception
     */
    @RequestMapping(value = "/delWeibo")
    @ResponseBody
    public Boolean delWeibo(Integer markType,Integer taskId,HttpServletRequest request) throws Exception {
        fetchSessionAdmin(request);
        if(getNewWeiboVersion(markType)){
            if (admin != null) {
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("userEncode", getUserEncodeNew(Integer.valueOf(admin.getUserId())));
                map.put("taskId", taskId);
                map.put("platformTag", "wyq");
                String result= Utils.sendIntraBusinessAPIPOST(request,"wfxDeleteTask", map);
                if(!TextUtils.isEmpty(result)){
                    WeiboAnalysisRes weiboAnalysisRes = JSONObject.parseObject(result, WeiboAnalysisRes.class);
                    if(weiboAnalysisRes!=null && weiboAnalysisRes.getCode().equals("0000")){
                        System.out.println(result);
                        if (admin != null) {
                            WeiBoAnalysisTask fenxiWeibo = new WeiBoAnalysisTask();
                            fenxiWeibo.setTaskId(taskId);
                            fenxiWeibo.setStatus(BusinessConstant.STATUS_INVALID);
                            BaseDto baseDto=apiInfoClient.delWeiboAnalysis(String.valueOf(fenxiWeibo.getTaskId()));
                            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                                Boolean flag= (Boolean) baseDto.getData();
                                if(flag){
                                    return true;
                                }
                            }
                            Map<String,Object> objectMap = new HashMap<>();
                            objectMap.put("UserDto",admin);
                            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,objectMap);
                            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SWA,objectDto,  BusinessConstant.OPERATE_LOG_OPERATE_TYPE_D, null, null);
                            return false;

                        }

                    }
                }
            }
        }else{
            if (admin != null) {
                WeiBoAnalysisTask fenxiWeibo = new WeiBoAnalysisTask();
                fenxiWeibo.setTaskId(taskId);
                fenxiWeibo.setStatus(BusinessConstant.STATUS_INVALID);

                BaseDto baseDto=apiInfoClient.delWeiboAnalysis(String.valueOf(fenxiWeibo.getTaskId()));
                if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                    Boolean flag= (Boolean) baseDto.getData();
                    if(flag){
                        return true;
                    }
                }
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("UserDto",admin);
                OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,objectMap);
                CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SWA,objectDto,  BusinessConstant.OPERATE_LOG_OPERATE_TYPE_D, null, null);

                return false;
            }
        }
        return false;
    }
    private boolean getNewWeiboVersion(Integer markType){
        if(markType!=null && markType==1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 转到微博分析首页
     */
    @RequestMapping(value = "/weiboAnalysisIndex")
    public ModelAndView weiboAnalysisIndex(HttpServletRequest request,ModelAndView modelAndView) throws Exception {
        admin = fetchSessionAdmin(request);
        if (admin != null) {
            // 取出最新的一条分析
            WeiBoAnalysisTask tempWeibo = new WeiBoAnalysisTask();
            tempWeibo.setUserId(Integer.valueOf(admin.getUserId()));
            tempWeibo.setStatus(Integer.valueOf(BusinessConstant.STATUS_VALID));
            tempWeibo.setPayment(Integer.valueOf(BusinessConstant.PAY_STATUS_YES));
            BaseDto<WeiBoAnalysisTask> baseDto=apiInfoClient.queryLatestWeibo(Integer.valueOf(admin.getUserId()));
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                WeiBoAnalysisTask fenxiWeibo = baseDto.getData();
                if (fenxiWeibo != null) {
                    Boolean active = false;
                    int weiboId = fenxiWeibo.getTaskId();
                    String weiboURL = fenxiWeibo.getWeiboUrl();
                    String createTime=Utils.getStringFromDate(fenxiWeibo.getCreateTime().getTime(), DateUtils.FORMAT_SHORT_TIME);
                    modelAndView.addObject("active",active);
                    modelAndView.addObject("weiboId",weiboId);
                    modelAndView.addObject("createTime",createTime);
                    modelAndView.addObject("weiboURL",weiboURL);
                    modelAndView.addObject("retry",false);
                    modelAndView.addObject("demo",false);
                    if (fenxiWeibo.getPayment() == 1){
                        modelAndView.addObject("needPay",false);
                    }else {
                        modelAndView.addObject("needPay",true);
                    }

                    modelAndView.addObject("admin",admin);
                    modelAndView.addObject("fenxiWeibo",fenxiWeibo);
                    modelAndView.addObject("taskTicket",fenxiWeibo.getTaskId());
                    modelAndView.addObject("shareCode",fenxiWeibo.getShareCode());
                    modelAndView.addObject("createTime",fenxiWeibo.getCreateTime());
                    modelAndView.addObject("weiboId",fenxiWeibo.getWeiboUid());
                    modelAndView.addObject("userId",fenxiWeibo.getUserId());
                    modelAndView.setViewName("view/singleWeiboAnalysis/analysis");
                    return modelAndView;
                }
            }

            // 获取微博UID
            BaseDto<UserThirdpartyAuthInfo> baseDto1=apiInfoClient.queryOneUserThirdpartyAuth(String.valueOf(admin.getUserId()));
            if(baseDto1!=null && baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null){
                UserThirdpartyAuthInfo thirdpartyAuthInfo = baseDto1.getData();
                if (thirdpartyAuthInfo != null && thirdpartyAuthInfo.getPlatformType() == BusinessConstant.THIRDPARTY_TYPE_SINA){
                    String uid = thirdpartyAuthInfo.getThirdpartyUserId();
                    modelAndView.addObject("uid",uid);
                }

            }
            modelAndView.setViewName("view/singleWeiboAnalysis/index");
            return modelAndView;
        }
        modelAndView.setViewName(Flags.login_view);
        return modelAndView;
    }

    /**
     * 微博分析
     * @param createTime
     * @param modelAndView
     * @param active
     * @param request
     * @param weiboId
     * @param weiboURL
     * @param demo
     * @param retry
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/analysis")
    public ModelAndView analysis(String createTime,ModelAndView modelAndView,boolean active,HttpServletRequest request,Integer weiboId,String weiboURL,boolean demo,boolean retry) throws Exception {

        admin = fetchSessionAdmin(request);
        if(weiboId==null){
            weiboId=0;
        }
        Integer userId = Integer.valueOf(admin.getUserId());
        Boolean needPay = true;
        modelAndView.addObject("needPay",needPay);
        fetchProductlist(request,modelAndView,BusinessConstant.PRODUCT_PACKAGE_SINGLE_WEIBO_ANALYSIS);//加载产品列表
        String uid=null;
        WeiBoAnalysisTask fenxiWeibo=null;
        if (admin != null) {
            UserThirdpartyAuthInfo thirdpartyAuthInfo=null;
            BaseDto baseDto=apiInfoClient.queryOneUserThirdpartyAuth(String.valueOf(admin.getUserId()));
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                thirdpartyAuthInfo = (UserThirdpartyAuthInfo) baseDto.getData();
            }

            if (thirdpartyAuthInfo != null && thirdpartyAuthInfo.getPlatformType() == BusinessConstant.THIRDPARTY_TYPE_SINA){
                uid = thirdpartyAuthInfo.getThirdpartyUserId();
            }

            if ((weiboId!=null && weiboId > 0) || (weiboURL != null && !"".equals(weiboURL))) {
                if (weiboURL.indexOf("?") != -1)
                    weiboURL = weiboURL.substring(0, weiboURL.indexOf("?"));
                weiboURL = weiboURL.replace("www.", "");

                if (!demo) {
                    modelAndView.addObject("demo",demo);
                    // 检查该微博是否已存在
                    WeiBoAnalysisTask tempWeibo = new WeiBoAnalysisTask();
                    tempWeibo.setTaskId(weiboId);
                    tempWeibo.setWeiboUrl(weiboURL);
                    tempWeibo.setUserId(Integer.valueOf(admin.getUserId()));
                    BaseDto<WeiBoAnalysisTask> baseDto1= apiInfoClient.queryWeiboAnalysisList( weiboId,weiboURL,Integer.valueOf(admin.getUserId()));
                    if(baseDto1!=null && baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null){
                        fenxiWeibo=  baseDto1.getData();
                    }

                    //检查订单是否是因分析条数不够产生的"假死"订单
                    WeiBoAnalysisTask tempWeiboOld = new WeiBoAnalysisTask();
                    if(fenxiWeibo == null){
                        tempWeiboOld = null;
                    }else{
                        if(fenxiWeibo.getPayment()==0 && fenxiWeibo.getForwardCount()<=admin.getUserSingleWeiboAnalysisValidCount()){
                            //保存旧订单信息
                            tempWeiboOld = fenxiWeibo;
                            fenxiWeibo = null;
                        }else{
                            tempWeiboOld = null;
                        }
                    }
                    if (retry) { // 重新分析
                        fenxiWeibo = null;
                    }

                    if (fenxiWeibo == null) {
                        // 创建任务
                        Map<String, Object> params = new HashMap<String, Object>();
                        try {
                            params.put("weiboUrl", URLEncoder.encode(weiboURL, "UTF-8"));
                            if (uid != null && !"".equals(uid)){
                                params.put("userId", uid);
                            }
                            params.put("userTag", admin.getUserId());
                            if (retry){
                                params.put("retry", 1);
                            }
                        } catch (UnsupportedEncodingException e) {
                            log.error("SingleWeiboAnalysisAction.analysis() userId=" + admin.getUserId() + ", URLEncoder.encode error!", e);
                            modelAndView.setViewName(Flags.error_view);
                            return modelAndView;
                        }

                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("userEncode", getUserEncodeNew(Integer.valueOf(admin.getUserId())));
                        map.put("platformTag", "wyq");
                        map.put("weiboUrl", weiboURL);
                        String resultGetSingle= Utils.sendIntraBusinessAPIPOST(request, "swaAddTask", map);
                        String analysis_error;
                        if (resultGetSingle == null || "".equals(resultGetSingle.trim())) {
                            log.error("SingleWeiboAnalysisAction.analysis() userId=" + admin.getUserId() + ", create task result is null!");
                            analysis_error = "true";
                            modelAndView.addObject("analysis_error",analysis_error);
                            modelAndView.setViewName("view/singleWeiboAnalysis/analysis");
                            return modelAndView;
                        }
                        WeiboAnalysisRes weiboTaskRes=null;

                        if(!TextUtils.isEmpty(resultGetSingle)){
                            weiboTaskRes = JSONObject.parseObject(resultGetSingle, WeiboAnalysisRes.class);
                            if(weiboTaskRes!=null && weiboTaskRes.getTask()!=null){
//									weiboTask=weiboTaskRes.getTask();
                            }
                        }

                        WeiboTaskCreateRes weiboTaskCreateRes=new WeiboTaskCreateRes();
                        weiboTaskCreateRes.setCode(weiboTaskRes.getCode());
                        weiboTaskCreateRes.setMessage(weiboTaskRes.getMessage());
                        WeiboTaskCreateVO task=new WeiboTaskCreateVO();
                        if(weiboTaskRes.getTask()!=null){
                            task.setAnalysisTaskTicket(weiboTaskRes.getTask().getWeiboTaskTicket());
                            Weibo statusInfo=new Weibo();
                            statusInfo.setStatusCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(weiboTaskRes.getTask().getCreateTime()));
                            statusInfo.setStatusText(CommonUtils.replaceEmoji(weiboTaskRes.getTask().getWeiboContent()));
                            statusInfo.setStatusRepostsCount(weiboTaskRes.getTask().getWeiboForwardNumber().intValue());
                            statusInfo.setStatusCommentsCount(weiboTaskRes.getTask().getWeiboCommentsNumber());
                            statusInfo.setStatusAttitudesCount(weiboTaskRes.getTask().getWeiboPraiseNumber());

                            UserDto user=new UserDto();
                            user.setUserId(String.valueOf(weiboTaskRes.getTask().getUserId()));
                            user.setNickname(weiboTaskRes.getTask().getWeiboNickname());
                            user.setUserHead(weiboTaskRes.getTask().getWeiboUserhead());
                            user.setUserVerifiedType(Integer.parseInt(weiboTaskRes.getTask().getWeiboVerifiedType()));
                            user.setPageUrl(weiboTaskRes.getTask().getWeiboUrl());
                            statusInfo.setUser(user);
                            task.setStatusInfo(statusInfo);
                            task.setAnalysisStatus(weiboTaskRes.getTask().getAnalysisStatus());
                        }

                        weiboTaskCreateRes.setTask(task);
                        if (weiboTaskCreateRes == null || !CodeConstant.SUCCESS_CODE.equals(weiboTaskCreateRes.getCode()) || weiboTaskCreateRes.getTask() == null || weiboTaskCreateRes.getTask().getStatusInfo() == null) {
                            if (weiboTaskCreateRes == null){
                                System.out.println("SingleWeiboAnalysisAction.analysis() userId=" + admin.getUserId() + ", create task error weiboTaskCreateRes is null!");
                            } else{
                                System.out.println("SingleWeiboAnalysisAction.analysis() userId=" + admin.getUserId() + ", create task return code = '" + weiboTaskCreateRes.getCode() + "', message = '" + weiboTaskCreateRes.getMessage() + "'!");
                            }
                            analysis_error = "true";
                            modelAndView.addObject("analysis_error",analysis_error);
                            modelAndView.setViewName("view/singleWeiboAnalysis/analysis");
                            return modelAndView;
                        }else{
                            if(weiboTaskCreateRes.getTask().getStatusInfo().getStatusRepostsCount() == null || weiboTaskCreateRes.getTask().getStatusInfo().getStatusRepostsCount() == 0){
                                analysis_error = "0";
                                modelAndView.addObject("analysis_error",analysis_error);
                                modelAndView.setViewName("view/singleWeiboAnalysis/analysis");
                                return modelAndView;
                            }
                            if(weiboTaskCreateRes.getTask().getStatusInfo().getSensitiveFlag() != null && weiboTaskCreateRes.getTask().getStatusInfo().getSensitiveFlag() == 1){
                                analysis_error = "1";
                                modelAndView.addObject("analysis_error",analysis_error);
                                modelAndView.setViewName("view/singleWeiboAnalysis/analysis");
                                return modelAndView;
                            }
                        }
                        Integer repostsCount = weiboTaskCreateRes.getTask().getStatusInfo().getStatusRepostsCount(); // 转发数
                        modelAndView.addObject("repostsCount",repostsCount);

                        // 记录该微博
                        fenxiWeibo = new WeiBoAnalysisTask();
                        fenxiWeibo.setMarkType(1);
                        fenxiWeibo.setUserId(Integer.valueOf(admin.getUserId()));
                        fenxiWeibo.setWeiboUrl(weiboURL);
                        fenxiWeibo.setTaskTicket(weiboTaskCreateRes.getTask().getAnalysisTaskTicket());
                        fenxiWeibo.setStatus(BusinessConstant.STATUS_VALID);
                        fenxiWeibo.setAnalysisStatus(weiboTaskCreateRes.getTask().getAnalysisStatus());
                        fenxiWeibo.setForwardCount(repostsCount);
                        try {
                            String[] tickets = weiboTaskCreateRes.getTask().getAnalysisTaskTicket().split("wfxwyq");
                            int n = tickets[1].length()/8;
                            String mid = "";
                            for (int i = 0;i<n;i++){
                                String str = "";
                                if (i == 0){
                                    str = tickets[1].substring(0, tickets[1].length()/n);
                                }else if (i== n-1){
                                    str = tickets[1].substring(tickets[1].length()/n*(n-1), tickets[1].length());
                                }else {
                                    str = tickets[1].substring(tickets[1].length()/n*(n-2), tickets[1].length()/n*(n-1));
                                }
                                Double num = Double.valueOf(str);
                                if (mid.equals("")){
                                    mid =  str10ToStr62(num.longValue());
                                }else {
                                    mid = mid+str10ToStr62(num.longValue());
                                }
                            }
                            fenxiWeibo.setShareCode(mid);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        fenxiWeibo.setShareRedCount(0);
                        fenxiWeibo.setCreateTime(new Date());
                        fenxiWeibo.setUpdateTime(new Date());
                        fenxiWeibo.setPayment(BusinessConstant.PAY_STATUS_NO);
                        modelAndView.addObject("fenxiWeibo",fenxiWeibo);
                        apiInfoClient.doSaveWeibo(fenxiWeibo);

                        // 检测试用用户
                        int payment = BusinessConstant.PAY_STATUS_NO;

                        if (repostsCount > 0) {
                            String freeUids = CommonUtils.sendGet("http://wfx.51wyq.cn/api/getFreeUIDS", null);
                            if (freeUids != null && !"".equals(freeUids.trim()) && Arrays.asList(freeUids.trim().split(",")).contains(admin.getUsername())) {
                                payment = BusinessConstant.PAY_STATUS_YES;
                            } else {
                                if (admin.getUserSingleWeiboAnalysisValidCount() > 0) { // 存在免费微博分析次数
                                    Map<String, Object> userRightsModifyParams = new HashMap<String, Object>();
                                    userRightsModifyParams.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                                    userRightsModifyParams.put("item", BusinessConstant.USER_RIGHTS_RECHARGE_ITEM_SWA);
                                    userRightsModifyParams.put("type", BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_MIN);
                                    userRightsModifyParams.put("relationId", fenxiWeibo.getTaskId());
                                    userRightsModifyParams.put("desc", "单条微博分析");

                                    if (repostsCount > admin.getUserSingleWeiboAnalysisValidCount()) {
                                        userRightsModifyParams.put("count", admin.getUserSingleWeiboAnalysisValidCount());
                                        repostsCount = repostsCount - admin.getUserSingleWeiboAnalysisValidCount();
                                    } else {
                                        userRightsModifyParams.put("count", repostsCount);
                                        payment = BusinessConstant.PAY_STATUS_YES;
                                        fenxiWeibo.setPayment(payment);
                                    }

                                    String userRightsModifyRtnStr = Utils.sendWrdIntraBusinessAPIPOST(request,"userRightsModify", userRightsModifyParams);
                                    if (StringUtils.isBlank(userRightsModifyRtnStr)){
                                        log.error("SingleWeiboAnalysisAction.analysis() userId=" + admin.getUserId() + ", userRightsModifyRtnStr is null!");
                                        modelAndView.setViewName(Flags.error_view);
                                        return modelAndView;
                                    }

                                    UserRes userRes = JSONObject.parseObject(userRightsModifyRtnStr, UserRes.class);
                                    if (userRes == null || !CodeConstant.SUCCESS_CODE.equals(userRes.getCode())){
                                        log.error("SingleWeiboAnalysisAction.analysis() userId=" + admin.getUserId() + ", userRes is null or request error!");
                                        modelAndView.setViewName(Flags.error_view);
                                        return modelAndView;
                                    }
                                }
                            }
                        }
                        if (payment == BusinessConstant.PAY_STATUS_YES){
                            //确认任务
//								weiboTaskConfirm(weiboTaskCreateRes.getTask().getWeiboTaskTicket(), 0, admin.getUserId());
                            Map<String, Object> map2=new HashMap<String, Object>();
                            map2.put("userEncode", getUserEncodeNew(admin.getUserId()));
                            map2.put("ticket", weiboTaskRes.getTask().getWeiboTaskTicket());
                            map2.put("platformTag", "wyq");
                            String re=Utils.sendWrdIntraBusinessAPIPOST(request, "swaConfirmTask", map2);
                            WeiboAnalysisRes weiboAnalysisRes = JSONObject.parseObject(re, WeiboAnalysisRes.class);
                            if(weiboAnalysisRes!=null && weiboAnalysisRes.getCode().equals("0000")){
                                System.out.println("任务确认成功");
                            }
                            System.out.println("Message"+weiboAnalysisRes.getMessage());
                        }


                        //查询微博id,根据微博id更新支付状态
                        WeiBoAnalysisTask fxPayment = new WeiBoAnalysisTask();

                        fxPayment.setUserId(Integer.valueOf(admin.getUserId()));
                        fxPayment.setTaskId(0);
                        fxPayment.setWeiboUrl(weiboURL);
                        BaseDto baseDto2=apiInfoClient.queryWeiboAnalysisList(0,weiboURL,Integer.valueOf(admin.getUserId()));
                        if(baseDto2!=null && baseDto2.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto2.getData()!=null){
                            WeiBoAnalysisTask fxPayment1 = (WeiBoAnalysisTask) baseDto2.getData();
                            WeiBoAnalysisTask fxPayment2 = new WeiBoAnalysisTask();
                            fxPayment2.setTaskId(fxPayment1.getTaskId());
                            apiInfoClient.doModifyWeiboPayment(fxPayment2);

                            try {
                                // 更新微博内容
                                if (weiboTaskCreateRes.getTask().getStatusInfo() != null) {
                                    if (weiboTaskCreateRes.getTask().getStatusInfo().getStatusText() != null && !"".equals(weiboTaskCreateRes.getTask().getStatusInfo().getStatusText())) {
                                        weiboTaskCreateRes.getTask().getStatusInfo().setStatusText(CommonUtils.replaceEmoji(weiboTaskCreateRes.getTask().getStatusInfo().getStatusText()));
                                    }

                                    WeiBoAnalysisTask tempWeibo1 = new WeiBoAnalysisTask();
                                    tempWeibo1.setForwardCount(weiboTaskRes.getTask().getWeiboForwardNumber().intValue());
                                    tempWeibo1.setCommentCount(weiboTaskRes.getTask().getWeiboCommentsNumber());
                                    tempWeibo1.setPraiseCount(weiboTaskRes.getTask().getWeiboPraiseNumber());
                                    tempWeibo1.setPublishedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(weiboTaskCreateRes.getTask().getStatusInfo().getStatusCreatedAt()));
                                    tempWeibo1.setWeiboUid(String.valueOf(weiboTaskCreateRes.getTask().getStatusInfo().getUser().getUserId()));
                                    tempWeibo1.setWeiboNickname(weiboTaskRes.getTask().getWeiboNickname());
                                    tempWeibo1.setWeiboUserhead(weiboTaskRes.getTask().getWeiboUserhead());
                                    tempWeibo1.setVerifiedType(Integer.valueOf(weiboTaskRes.getTask().getWeiboVerifiedType()));
                                    tempWeibo1.setWeiboContent(weiboTaskRes.getTask().getWeiboContent());
                                    tempWeibo1.setTaskTicket(weiboTaskCreateRes.getTask().getAnalysisTaskTicket());
                                    tempWeibo1.setUserId(fenxiWeibo.getUserId());
                                    apiInfoClient.doModifyWeiboPayment(tempWeibo1);
                                    Map<String,Object> objectMap = new HashMap<>();
                                    objectMap.put("UserDto",admin);
                                    OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,objectMap);
                                    CommonUtils.opreateLog(request, null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SWA,objectDto, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null, null);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        //保存新的订单后  删除待支付的该订单
                        if(tempWeiboOld != null){
                            apiInfoClient.delWeiboAnalysis(String.valueOf(tempWeiboOld.getTaskId()));
                            // 还原条数
                            if (tempWeiboOld.getPayment() == BusinessConstant.PAY_STATUS_NO) {
                                UserRightsRechargeRecord tempRechargeRecord = new UserRightsRechargeRecord();
                                tempRechargeRecord.setUserId(tempWeiboOld.getUserId());
                                tempRechargeRecord.setItem(BusinessConstant.USER_RIGHTS_RECHARGE_ITEM_SWA);
                                tempRechargeRecord.setType(BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_MIN);
                                tempRechargeRecord.setRelationId(weiboId);
                                BaseDto baseDto3=apiInfoClient.queryUserRightsRechargeRecord(tempRechargeRecord);
                                if(baseDto3!=null && baseDto3.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto3.getData()!=null){
                                    UserRightsRechargeRecord useRechargeRecord=(UserRightsRechargeRecord)baseDto3.getData();
                                    if (useRechargeRecord != null) {
                                        Map<String, Object> userRightsModifyParams = new HashMap<String, Object>();
                                        userRightsModifyParams.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                                        userRightsModifyParams.put("item", BusinessConstant.USER_RIGHTS_RECHARGE_ITEM_SWA);
                                        userRightsModifyParams.put("type", BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_ADD);
                                        userRightsModifyParams.put("count", useRechargeRecord.getCount());
                                        userRightsModifyParams.put("relationId", tempWeiboOld.getTaskId());
                                        userRightsModifyParams.put("desc", "删除分析还原");

                                        String userRightsModifyRtnStr = Utils.sendWrdIntraBusinessAPIPOST(request, "userRightsModify", userRightsModifyParams);
                                        if (StringUtils.isBlank(userRightsModifyRtnStr)){
                                            log.error("SingleWeiboAnalysisAction.analysis() : userRightsModifyRtnStr is null!");
                                            modelAndView.setViewName(Flags.error_view);
                                            return modelAndView;
                                        }

                                        UserRes userRes = JSONObject.parseObject(userRightsModifyRtnStr, UserRes.class);
                                        if (userRes == null || !CodeConstant.SUCCESS_CODE.equals(userRes.getCode())){
                                            log.error("SingleWeiboAnalysisAction.analysis() : userRes is null! or request error");
                                            modelAndView.setViewName(Flags.error_view);
                                            return modelAndView;
                                        }
                                    }
                                }
                            }
                            Map<String,Object> objectMap = new HashMap<>();
                            objectMap.put("UserDto",admin);
                            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,objectMap);
                            CommonUtils.opreateLog(request, null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SWA,objectDto, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_D, null, null);
                        }

                    } else {
                        Integer repostsCount = fenxiWeibo.getForwardCount();
                        modelAndView.addObject("repostsCount",repostsCount);
                        Map<String,Object> objectMap = new HashMap<>();
                        objectMap.put("UserDto",admin);
                        OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,objectMap);
                        CommonUtils.opreateLog(request, null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SWA,objectDto, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_R, null, null);
                    }

                    if (fenxiWeibo.getPayment() == BusinessConstant.PAY_STATUS_NO){
                        needPay = true;
                        modelAndView.addObject("needPay",needPay);
                    }else {
                        needPay=false;
                        modelAndView.addObject("needPay",needPay);
                    }

                    // 更新微博状态
                    if (active) {
                        tempWeibo = new WeiBoAnalysisTask();
                        tempWeibo.setTaskId(fenxiWeibo.getTaskId());
                        tempWeibo.setStatus(BusinessConstant.STATUS_VALID);
                        apiInfoClient.doModifyWeiboPayment(tempWeibo);
                    }
                } else {
//                    userId = 1;
//                    // 创建任务
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    params.put("weiboUrl",weiboURL);
//                    params.put("userTag", userId);
//
//
//                    String url = SysConfig.cfgMap.get("API_SINGLE_WEIBO_ANALYSIS_URL") + IntraBusinessAPIConfig.getValue("weiboTaskCreate");
//                    params.put("format", IntraBusinessAPIConfig.getValue("format"));
//                    params.put("platformTag", 2);
//                    String result = com.miduchina.wrd.util.CommonUtils.sendGet(url, params);
//
//                    if (result == null || "".equals(result.trim())) {
//                        log.error("SingleWeiboAnalysisAction.analysis() : create task result is null!");
//                        modelAndView.setViewName(Flags.error_view);
//                        return modelAndView;
//                    }
//
//                    WeiboTaskCreateRes weiboTaskCreateRes = JSONObject.parseObject(result, WeiboTaskCreateRes.class);
//                    if (weiboTaskCreateRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskCreateRes.getCode())) {
//                        log.error("SingleWeiboAnalysisAction.analysis() : create task return code = '" + weiboTaskCreateRes.getCode() + "', message = '" + weiboTaskCreateRes.getMessage() + "'!");
//                        modelAndView.setViewName(Flags.error_view);
//                        return modelAndView;
//                    }

                    if (!"www".equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME") )){
                        userId = 1270;
                    }else {
                        userId = 14378;
                    }
                    admin.setUserId(String.valueOf(userId));
                    WeiBoAnalysisTask task = isExists(request,weiboURL);
                    fenxiWeibo = new WeiBoAnalysisTask();
                    fenxiWeibo.setTaskId(task.getTaskId());
                    fenxiWeibo.setWeiboUrl(weiboURL);
                    fenxiWeibo.setTaskTicket(task.getTaskTicket());
                    fenxiWeibo.setForwardCount(task.getForwardCount());
                    fenxiWeibo.setUserId(task.getUserId());
                    fenxiWeibo.setMarkType(task.getMarkType());
                    fenxiWeibo.setCreateTime(task.getCreateTime());
                }
            }
            modelAndView.addObject("admin",admin);
            modelAndView.addObject("fenxiWeibo",fenxiWeibo);
            modelAndView.addObject("taskTicket",fenxiWeibo.getTaskId());
            modelAndView.addObject("shareCode",fenxiWeibo.getShareCode());
            modelAndView.addObject("createTime",fenxiWeibo.getCreateTime());
            modelAndView.addObject("weiboId",fenxiWeibo.getWeiboUid());
            modelAndView.addObject("userId",userId);
            modelAndView.addObject("demo",demo);
            modelAndView.setViewName("view/singleWeiboAnalysis/analysis");
            return modelAndView;
        }
        modelAndView.setViewName("index_local");
        return modelAndView;
    }
    // 10进制转成62进制
    public static String str10ToStr62(long long10)
    {
        String str62 = "";
        int r = 0;
        while (long10 != 0)
        {
            r = (int) (long10 % 62);
            str62 = getStr62Character(r) + str62;
            long10 = long10 / 62;
        }
        return str62;
    }
    /**
     * 根据62进制游标获得62进制字符内容
     *
     * @param str62Cursor
     * @return
     */
    private static String getStr62Character(int str62Cursor)
    {
        String character = "";

        if (str62Cursor >= 0 && str62Cursor <= 61)
        {
            character = keys[str62Cursor];
        }

        return character;
    }

    /**
     * 查询微博是否已分析
     * @param request
     * @param weiboURL
     * @return
     */
    @RequestMapping(value = "/isExists")
    @ResponseBody
    public WeiBoAnalysisTask isExists(HttpServletRequest request,String weiboURL) {
        try {
            if (admin != null && weiboURL != null && !"".equals(weiboURL)) {
                if (weiboURL.indexOf("?") != -1)
                    weiboURL = weiboURL.substring(0, weiboURL.indexOf("?"));

                WeiBoAnalysisTask tempWeibo = new WeiBoAnalysisTask();
                tempWeibo.setUserId(Integer.valueOf(admin.getUserId()));
                tempWeibo.setWeiboUrl(weiboURL);
                tempWeibo.setPayment(BusinessConstant.PAY_STATUS_YES);
                tempWeibo.setStatus(BusinessConstant.STATUS_VALID);
                BaseDto<WeiBoAnalysisTask> baseDto=apiInfoClient.queryWeiboExistsByUrl(tempWeibo);
                if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                    WeiBoAnalysisTask fenxiWeibo = baseDto.getData();
                    return  fenxiWeibo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//		fetchSessionAdmin();
//		if (admin != null && weiboURL != null && !"".equals(weiboURL)) {
//			if (weiboURL.indexOf("?") != -1)
//				weiboURL = weiboURL.substring(0, weiboURL.indexOf("?"));
//
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("userEncode", getUserEncodeNew(admin.getUserId()));
//			map.put("platformTag", "wyq");
//			map.put("weiboUrl", weiboURL);
//			map.put("confirmType", "1");
//			String resultGetSingle=Utils.sendIntraBusinessAPIPOST(request, "swaGetTaskById", map);
//			WeiboAnalysisRes weiboTaskRes=null;
//
//			if(!TextUtils.isEmpty(resultGetSingle)){
//				weiboTaskRes = JSONObject.parseObject(resultGetSingle, WeiboAnalysisRes.class);
//				if(weiboTaskRes!=null && weiboTaskRes.getTask()!=null){
//					weiboTask=weiboTaskRes.getTask();
//				}
//			}
//			if(weiboTask!=null){
//				FenxiWeibo fenxiWeibo = new FenxiWeibo();
//				fenxiWeibo.setWeiboId(weiboTask.getWeiboId());
//				fenxiWeibo.setCreateTime(new Date(weiboTask.getCreateTime()));
//				CommonUtils.writeJSON(fenxiWeibo);
//			}
//		}
//		FenxiWeibo fenxiWeibo = new FenxiWeibo();
//		CommonUtils.writeJSON(fenxiWeibo);
    }

    @Autowired
    OrderClientService orderService;
    @InitBinder("myProduct")
    public void myProducts(WebDataBinder binder){
        binder.setFieldDefaultPrefix("myProduct.");
    }
    @RequestMapping(value = "/getOrderFeeV2")
    @ResponseBody
    public ModelDto getOrderFeeV2(String keywords, Integer heatReportId, String keywordIds, Integer fenxiWeiboId, String payType, boolean useCredit,
                                  HttpServletRequest request, @ModelAttribute("myProduct") MyProductDto myProductDto,
                                  Integer orderType, Integer productPackageId){
        fetchSessionAdmin(request);
        if (admin != null && myProductDto != null ) {
            List<MyProductDto> myProductDtoList = new ArrayList<MyProductDto>();
            if (myProductDto.getKeywordId() != null && myProductDto.getKeywordId() > 0 && myProductDto.getKeywordPackageNum() != null && myProductDto.getKeywordPackageNum() > 0) {
                myProductDtoList.add(myProductDto);
            }
            ConfirmOrderDto confirmOrderVO = new ConfirmOrderDto();
            confirmOrderVO.setOrderType(orderType);
            if(orderType == BusinessConstant.ORDER_TYPE_CONTINUE){
                confirmOrderVO.setRenewPackageId(productPackageId);
            }
            List<ConfirmOrderPackageDto> confirmOrderVOPackageElements = new ArrayList<ConfirmOrderPackageDto>();
            for (MyProductDto myProductDto1 : myProductDtoList) {
                ConfirmOrderPackageDto confirmOrderVOPackageElement = new ConfirmOrderPackageDto();
                confirmOrderVOPackageElement.setProductPackageId(myProductDto1.getKeywordId());
                confirmOrderVOPackageElement.setPackageCount(myProductDto1.getKeywordPackageNum());
                confirmOrderVOPackageElements.add(confirmOrderVOPackageElement);
            }
            confirmOrderVO.setPackages(confirmOrderVOPackageElements);
            confirmOrderVO.setUseCredit(useCredit);
            confirmOrderVO.setPayChannel(payType);
            confirmOrderVO.setFenxiWeiboId(fenxiWeiboId);
            confirmOrderVO.setKeywordIds(keywordIds);
            if(heatReportId != null){
                confirmOrderVO.setHeatReportIds(heatReportId.toString());
            }
            if(net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNotBlank(keywords)){
                Keywords ks = JSON.parseObject(keywords, Keywords.class);
                if(ks.getDate() == null)
                    ks.setDate(24);
                Calendar c = Calendar.getInstance();
                ks.setEndTime(c.getTime());
                c.add(Calendar.HOUR_OF_DAY, -ks.getDate());
                ks.setStartTime(c.getTime());

                confirmOrderVO.setKeywords(JSON.toJSONString(ks));
            }
            OrderFeeViewDto fee=orderService.findOrderFeeByUserId(request,confirmOrderVO, admin);
            return new ModelDto(1, fee);
        }
        return null;
    }



    @RequestMapping(value = "/demo")
    @ResponseBody
    public ModelAndView ModelAndView(HttpServletRequest request, String tickets, ModelAndView modelAndView) throws Exception {
        //www: https://weibo.com/1647688972/I6oBoiBX7 beta https://weibo.com/3900215081/I5loDc16b
         return analysis("",modelAndView,false,request,0,"https://weibo.com/1647688972/I6oBoiBX7",true,false);
    }


}
