package com.miduchina.wrd.eventanalysis.controller.products;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Constants;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.DNSUtils;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.analysis.ProductsAnalysisShare;
import com.miduchina.wrd.po.eventanalysis.BaseRes;
import com.miduchina.wrd.po.eventanalysis.BaseView;
import com.miduchina.wrd.po.eventanalysis.compet.*;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.DateUtils;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.view.industry.productAnalysis.ProductAnalysisSolidifysView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@RestController
@RequestMapping(value = "/compet")
public class ProductsAnalysisController extends BaseController{

    @Autowired
    OrderClientService orderClientService;
    @RequestMapping(value = "/productsReport")
    public ModelAndView productsReport(ModelAndView modelAndView,HttpServletRequest request) throws Exception{
        fetchSessionAdmin(request);
        String viewPath = SysConfig.cfgMap.get("FILE_VIEW_PATH"); //访问配置的文件服务系统路径
        String contextPath = request.getRealPath("/"); //项目路径
        String viewUri = DNSUtils.getRequestUrl(request);
        List<ProductAnalysis> newPabList=null;
        List<ProductsAnalysisBrief> pabList=new ArrayList<ProductsAnalysisBrief>();
        if(admin!=null){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            params.put("platformTag", "wyq");
            params.put("page", page);
            params.put("pageSize", 20);
            String rtnStr = Utils.sendIntraBusinessAPIPOST(request, "productTaskList", params);
            System.out.println(rtnStr);
            if(StringUtils.isNotBlank(rtnStr)){
                ProductAnalysisPageView productList = JSON.parseObject(rtnStr,ProductAnalysisPageView.class);
                if(productList !=null && CodeConstant.SUCCESS_CODE.equals(productList.getCode())){
                    int  maxpage = productList.getMaxPage();
                    page = productList.getPage();
                    newPabList = productList.getProductAnalysisList();
                }
            }
            if(null!=newPabList && newPabList.size()>0){
                List<ProductsAnalysisBriefVo> pabListVo = new ArrayList<ProductsAnalysisBriefVo>();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String pabIds = "";
                for(int i = 0;i<newPabList.size();i++){
                    pabIds = pabIds+newPabList.get(i).getId()+",";
                }
                if(!"".equals(pabIds)){
                    pabIds = pabIds.substring(0, pabIds.length()-1);
                }

                System.out.println(pabIds.split(",").length);
                List<ProductsAnalysisShare> pasList = new ArrayList<>();
                for(int i = 0;i<newPabList.size();i++){
                    ProductAnalysis productAnalys=newPabList.get(i);
                    ProductsAnalysisBrief pBrief=new ProductsAnalysisBrief();
                    BeanUtils.copyProperties(pBrief,newPabList.get(i));
                    pabList.add(pBrief);
                    ProductsAnalysisBriefVo vo = new ProductsAnalysisBriefVo();
                    BeanUtils.copyProperties(vo,newPabList.get(i));
                    vo.setShareCode(newPabList.get(i).getCode());
                    DateFormat bf = new SimpleDateFormat("yyyyMMdd");//多态
                    String format = bf.format(newPabList.get(i).getCreateTime());//格式化 bf.format(date);
//			        vo.setFilePath(productAnalys.getMonitoringDesc());
                    String filePath="productsAnalysis/"+format+"/"+admin.getUserId()+"/"+newPabList.get(i).getCreateTime().getTime()+newPabList.get(i).getId();
                    vo.setFilePath(filePath);
                    productAnalys.setFilePath(filePath);
                    if(StringUtils.isNotEmpty(vo.getFilePath())){
                        if(newPabList.get(i).getCreateTime().getTime() <= df.parse("2016-06-1 18:00:00").getTime()){
                            vo.setHrefUrl("window.open('"+viewPath+vo.getFilePath()+".html')");
                            productAnalys.setHrefUrl("window.open('"+viewPath+vo.getFilePath()+".html')");
                        }else if(newPabList.get(i).getCreateTime().getTime() <= df.parse("2016-06-30 18:00:00").getTime()){
                            vo.setHrefUrl("window.open('"+viewPath+vo.getFilePath()+"_h5.html')");
                            productAnalys.setHrefUrl("window.open('"+viewPath+vo.getFilePath()+"_h5.html')");
                        }else{
                            if(StringUtils.isNotEmpty(vo.getShareCode())){
                                vo.setHrefUrl(viewUri+"compet/lookShareCodeReport?shareCode="+vo.getShareCode());
                                productAnalys.setHrefUrl(viewUri+"compet/lookShareCodeReport?shareCode="+productAnalys.getCode());
                            }
                        }
                    }
                    pabListVo.add(vo);
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",admin);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request,null, BusinessConstant.PLATFORM_H5,objectDto, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_PRODUCT_ANALYSIS , BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null);
        }
        modelAndView.addObject("admin",admin);
        modelAndView.addObject("userPlatform",userPlatform);
        modelAndView.addObject("pabList",pabList);
        modelAndView.addObject("newPabList",newPabList);
        modelAndView.setViewName("/view/productsAnalysis/productsReport");
        return modelAndView;
    }
    /**
     * 任务列表删除、更新状态
     * @throws Exception
     */
    @RequestMapping(value = "/delTaskStatusR")
    public void delTaskStatusR(String reportId,HttpServletRequest request,HttpServletResponse response){
        fetchSessionAdmin(request);
        resetPagesize();
        response.setContentType("application/json;charset=UTF-8;");
        PrintWriter printWriter = null;
        boolean result = true;

        try {
            printWriter = response.getWriter();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            params.put("productId", reportId);
            params.put("platformTag", "wyq");
            String rtnStr = Utils.sendIntraBusinessAPIPOST(request, "productDeleteTask", params);

            System.out.println("id = "+reportId);
            printWriter.print(result);
        } catch (Exception e) {
            e.printStackTrace();
            printWriter.print(result);
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
    /**
     * 通过短码获取竞品分析报告
     */
    @RequestMapping(value = "/lookShareCodeReport")
    public ModelAndView lookShareCodeReport(ModelAndView modelAndView,String shareCode,String sign,HttpServletRequest request) {
        fetchSessionAdmin(request);
        ProductsAnalysisBrief pab=null;
        if(TextUtils.isEmpty(sign)){
            sign="0";
        }
        try {
            if (shareCode != null && !"".equals(shareCode)) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("code", shareCode);
                params.put("platformTag", "wyq");
                String rtnStr = Utils.sendIntraBusinessAPIPOST(request,
                        "getSingleAnalysisByCode", params);
                System.out.println(rtnStr);
                if (StringUtils.isNotBlank(rtnStr)) {
                    ProductAnalysisView productList = JSON.parseObject(rtnStr, ProductAnalysisView.class);
                    if (productList != null
                            && CodeConstant.SUCCESS_CODE.equals(productList.getCode())) {
                        ProductAnalysis p = productList.getProductAnalysis();
                        if (p != null) {
                            pab = new ProductsAnalysisBrief();
                            pab.setId(p.getId());
                            pab.setTitle(p.getTitle());
                            pab.setStartTime(p.getStartTime());
                            pab.setEndTime(p.getEndTime());
                            pab.setCreateTime(p.getCreateTime());
                            pab.setCreateStr(new SimpleDateFormat("yyyyMMdd").format(p.getCreateTime()));
                            pab.setStartStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(p.getStartTime()));
                            pab.setEndStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(p.getEndTime()));
                            pab.setTicket(p.getTicket());
                            pab.setUserId(p.getUserId());
                            pab.setTendDesc(p.getTendDesc());
                            pab.setNegativeDesc(p.getNegativeDesc());
                            String title = p.getTitle();
                            pab.setSensitiveDesc(p.getSensitiveDesc());
                            pab.setWordDesc(p.getWordDesc());
                            pab.setAreaDesc(p.getAreaDesc());
                            pab.setSourceTypeDesc(p.getSourceTypeDesc());
                            pab.setSourceMediaDesc(p.getSourceMediaDesc());
                            pab.setMonitoringDesc(p.getMonitoringDesc());
                            pab.setProductsAnalysisId(p.getProductAnalysisId());
                        }
                    }
                }
                if (pab != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String starttime = sdf.format(pab.getStartTime());
                    String endtime = sdf.format(pab.getEndTime());
                    String viewUri = DNSUtils.getDNSUrl(request.getRequestURL().toString());
                    String title = pab.getTitle();
                    modelAndView.addObject("pab",pab);
                    modelAndView.addObject("endtime",endtime);
                    modelAndView.addObject("starttime",starttime);
                    modelAndView.addObject("viewUri",viewUri);
                    modelAndView.addObject("title",title);
                    modelAndView.addObject("shareCode",shareCode);
                    modelAndView.addObject("sign",sign);
                    modelAndView.addObject("admin",admin);
                    modelAndView.setViewName("/view/productsAnalysis/analysisChart");
                    return modelAndView;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.setViewName(Constants.ERROR_VIEW);
        return modelAndView;
    }
    @RequestMapping(value = "/productsAnalysis")
    public ModelAndView productsAnalysis(ProductsAnalysisKeyword pak,ModelAndView modelAndView,HttpServletRequest request) throws Exception {
        admin = fetchSessionAdmin(request);
        fetchProductlist(request,modelAndView,BusinessConstant.PRODUCT_PACKAGE_PRODUCT_ANALYSIS);
        String viewPath = SysConfig.cfgMap.get("FILE_VIEW_PATH"); //访问配置的文件服务系统路径
        String contextPath = request.getRealPath("/"); //项目路径
        String viewUri = DNSUtils.getRequestUrl(request);
        System.out.println("------------------------------"+viewUri);
        if(admin!=null){
            int orderCount = orderClientService.findOrderCountByProductPackageIds(admin, SysConfig.cfgMap.get("PACKAGE_TYPE_REPORT_IDS"));
            int buyType = orderCount > 0 ? 1 : 0;
            BaseDto<List<KeyWord>> baseDto=apiInfoClient.quertyByUserId(String.valueOf(admin.getUserId()));
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                List<KeyWord> kwList = baseDto.getData();
            }
            BaseDto<List<ProductsAnalysisBrief>> baseDto1=apiInfoClient.getPABList(Integer.valueOf(admin.getUserId()));
            if(baseDto1!=null && baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null){
                List<ProductsAnalysisBrief> pabList=baseDto1.getData();
                List<ProductsAnalysisBriefVo> pabListVo = new ArrayList<ProductsAnalysisBriefVo>();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String pabIds = "";
                for(int i = 0;i<pabList.size();i++){
                    pabIds = pabIds+pabList.get(i).getId()+",";
                }
                if(!"".equals(pabIds)){
                    pabIds = pabIds.substring(0, pabIds.length()-1);
                }
                System.out.println(pabIds.split(",").length);
                List<ProductsAnalysisShare> pasList = new ArrayList<>();
                BaseDto<List<ProductsAnalysisShare>> pasBase = new BaseDto<>();
                if(StringUtils.isNoneBlank(pabIds)){
                    pasBase=apiInfoClient.findisShareCodePabInId(pabIds);
                    pasList = pasBase.getData();
                }
                for(int i = 0;i<pabList.size();i++){
                    ProductsAnalysisShare pas = null;
                    for(int j = 0;j<pasList.size();j++){

                        if(pasList.get(j).getProductsAnalysisBriefId() == pabList.get(i).getId()){
                            pas = pasList.get(j);
                        }
                    }
                    ProductsAnalysisBriefVo vo = new ProductsAnalysisBriefVo();
                    /**
                     *  copyProperties dete 为空报错
                     */
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(vo,pabList.get(i));
                    if(pas != null){
                        vo.setShareCode(pas.getShareCode());
                    }
                    if(StringUtils.isNotEmpty(vo.getFilePath())){
                        if(pabList.get(i).getCreateTime().getTime() <= df.parse("2016-06-1 18:00:00").getTime()){
                            vo.setHrefUrl("window.open('"+viewPath+vo.getFilePath()+".html')");
                        }else if(pabList.get(i).getCreateTime().getTime() <= df.parse("2016-06-30 18:00:00").getTime()){
                            vo.setHrefUrl("window.open('"+viewPath+vo.getFilePath()+"_h5.html')");
                        }else{
                            if(StringUtils.isNotEmpty(vo.getShareCode())){
                                vo.setHrefUrl(viewUri+"view/analysis/products/lookShareCodeReport.action?shareCode="+vo.getShareCode());
                            }
                        }
                    }
                    pabListVo.add(vo);
                }
                modelAndView.addObject("pabListVo",pabListVo);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",admin);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request,null,  BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_PRODUCT_ANALYSIS,objectDto, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null, null);
        }
        //热搜词添加到竞品
        if(pak != null && pak.getKeyword1() != null){
            pak.setKeyword1(new String(StringUtils.trim(pak.getKeyword1()).getBytes("iso8859-1"), "GBK"));
        }
        Long token = System.currentTimeMillis();
        int sign=2;
        modelAndView.addObject("sign",sign);
        modelAndView.addObject("token",token);
        modelAndView.setViewName("/view/productsAnalysis/productsAnalysis");
        return modelAndView;
    }
    @InitBinder("pak")
    public void initBinder2(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("pak.");
    }
    @RequestMapping(value = "/doStartProductsAnalysis")
    @ResponseBody
    public BaseView doStartProductsAnalysis(@ModelAttribute("pak") ProductsAnalysisKeyword pak, HttpServletRequest request, String keywordIds, Integer timeDomain, String token)throws Exception{
        BaseView bv = new BaseView();
        if(keywordIds == null && pak == null && timeDomain == null && token == null){
            bv.setCode(CodeConstant.ERROR_CODE_1000);
            bv.setMessage("请输入必填项！");
            return bv;
        }

        fetchSessionAdmin(request);
        if(admin == null) {
            bv.setCode(CodeConstant.NOT_LOGIN_CDEO);
            bv.setMessage("登录已过期，请重新登录！");
            return bv;
        }
        List<ProductJsonView> keywordView = new ArrayList<>();
        if(StringUtils.isNotBlank(keywordIds)){	//选中的监测方案ID不为空
            String[] keywordIdArr = keywordIds.split(",");
            if(ArrayUtils.isNotEmpty(keywordIdArr)){
                for (String keywordId : keywordIdArr) {
                    BaseDto<KeyWord> baseDto=apiInfoClient.quertyOneById(keywordId);
                    if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                        KeyWord kw=baseDto.getData();
                        if(kw != null) {
                            ProductJsonView productJsonView = new ProductJsonView();
                            productJsonView.setName(kw.getKeywordName());
                            productJsonView.setKeyword(kw.getKeyword());
                            productJsonView.setFilterKeyword(kw.getFilterKeyword());
                            keywordView.add(productJsonView);
                        }
                    }
                }
            }
        }
        else if(pak != null){
            if(StringUtils.isNotBlank(pak.getKeyword1())){
                ProductJsonView productJsonView = new ProductJsonView();
                productJsonView.setName(pak.getKeyword1());
                productJsonView.setKeyword(pak.getKeyword1());
                keywordView.add(productJsonView);
            }
            if(StringUtils.isNotBlank(pak.getKeyword2())){
                ProductJsonView productJsonView = new ProductJsonView();
                productJsonView.setName(pak.getKeyword2());
                productJsonView.setKeyword(pak.getKeyword2());
                keywordView.add(productJsonView);
            }
            if(StringUtils.isNotBlank(pak.getKeyword3())){
                ProductJsonView productJsonView = new ProductJsonView();
                productJsonView.setName(pak.getKeyword3());
                productJsonView.setKeyword(pak.getKeyword3());
                keywordView.add(productJsonView);
            }
        }
        int sign=1;

        DateUtils.initDefaultTime(timeDomain, "", "", false);
        String starttime = DateUtils.format(DateUtils.startDate, DateUtils.FORMAT_LONG);
        String endtime = DateUtils.format(DateUtils.endDate, DateUtils.FORMAT_LONG);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("platformTag", "wyq");
        params.put("json", JSON.toJSONString(keywordView));
        params.put("startTime", starttime);
        params.put("endTime", endtime);
        params.put("createType", 1);
        String rtnStr = Utils.sendIntraBusinessAPIPOST(request,"productAddTask", params);
        System.out.println(rtnStr);
        if (StringUtils.isNotBlank(rtnStr)) {
            BaseRes baseRes = JSON.parseObject(rtnStr, BaseRes.class);
            if (baseRes != null && !CodeConstant.SUCCESS_CODE.equals(baseRes.getCode())) {
                bv.setCode(CodeConstant.ERROR_CODE_2001);
                if (baseRes.getCode().equals("2205")) {// 方案中包含敏感词，并且没有可查询的内容
                    bv.setMessage("方案中包含敏感词,请优化事件关键词");
                } else if (baseRes.getCode().equals("4026")) {
                    bv.setMessage("您的竞品分析数据量已超过限制,请优化事件关键词");
                } else if (baseRes.getCode().equals("4025")) {
                    bv.setMessage("您的竞品分析数据量过少,请优化事件关键词");
                } else {
                    bv.setMessage("分析失败，请稍后重试");
                }
            } else {
                bv.setCode(CodeConstant.SUCCESS_CODE);
            }
        }else {
            bv.setCode(CodeConstant.ERROR_CODE_2000);
            bv.setMessage("系统异常，请稍后再试！！！");
        }
        return bv;
    }


    @RequestMapping(value = "queryTaskInfo")
    @ResponseBody
    public String queryTaskInfo(HttpServletRequest request,ModelAndView modelAndView,String ticket) throws Exception {
        admin = fetchSessionAdmin(request);
        fetchRightNumber(request,modelAndView);
        if (ticket != null && !"".equals(ticket) && admin != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            params.put("ticket",ticket);
            String rtnStr = Utils.sendIntraBusinessAPIPOST(request,"productQuerySolidifySchedule", params);
            if(StringUtils.isNotBlank(rtnStr)){
                ProductAnalysisSolidifysView productSolidifys = JSON.parseObject(rtnStr,ProductAnalysisSolidifysView.class);
                if(productSolidifys !=null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(productSolidifys.getCode())){
                    return (com.alibaba.fastjson.JSONObject.toJSONString(productSolidifys));
                }
            }
        }
        return "";
    }

}
