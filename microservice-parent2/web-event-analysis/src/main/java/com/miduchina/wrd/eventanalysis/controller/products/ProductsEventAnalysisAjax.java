package com.miduchina.wrd.eventanalysis.controller.products;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.analysis.weiboanalysis.StatView;
import com.miduchina.wrd.dto.echart.IGroupResult;
import com.miduchina.wrd.po.analysis.ProductsAnalysis;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.config.WyqDataConfig;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.echart.EChartsAdapter;
import com.miduchina.wrd.eventanalysis.echart.EChartsAdapter1;

import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.eventanalysis.compet.ProductsAnalysisKeyword;
import com.miduchina.wrd.po.eventanalysis.weiboevent.Stat;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.util.CommonUtils;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/compet")
public class ProductsEventAnalysisAjax extends BaseController {

    /**
     * 地图对比图
     * @param paId
     * @param userId
     * @param starttime
     * @param endtime
     * @param rand
     * @return
     */
    @RequestMapping("/getMapChartProductsAnalysis")
    public String getMapChartProductsAnalysis(HttpServletRequest request,int paId, int userId, String starttime, String endtime, String rand){
        String json = null;
        endtime = retEndTime(endtime);
        List<com.miduchina.wrd.dto.eventanalysis.products.IGroupResult > list = new ArrayList<com.miduchina.wrd.dto.eventanalysis.products.IGroupResult >();
        List<String> kwList  = new ArrayList<String>();
        List<String> nameList  = new ArrayList<String>();
        List<String> filterList  = new ArrayList<String>();
        ProductsAnalysis pa = null;
        try {

            if(paId>0){
//                pa = paBean.getPA(paId);
                Map<String,Object> objectMap=new HashMap<>();
                objectMap.put("id",paId);
                objectMap.put("platformTag","wyq");
                String stringJson=Utils.sendIntraBusinessAPIPOST(request,"getSingleAnalysisById",objectMap);
                if(pa!=null){
                    kwList = getKeyWordList(pa);
                    if(pa.getType()==3){
                        nameList = getNameList(pa);
                    }
                    if(pa.getType()==4){
                        nameList = getNameList(pa);
                        filterList = getFilterList(pa);
                    }
                }

            }

            for(int i = 0;i<kwList.size();i++){
                Map<String,Object> params = WyqDataConfig.getDataInitMap(userId);
                params.put("date", -1);
                params.put("num", 34);
                //params.put("type", "1,2");
                if(StringUtils.isNotBlank(starttime)){
                    params.put("startTime", starttime);
                }
                if(StringUtils.isNotBlank(endtime)){
                    params.put("endTime", endtime);
                }
                if ((pa.getType() == 1 || pa.getType() == 3) && StringUtils.isNotBlank(kwList.get(i))) {
                    params.put("keyword", kwList.get(i));
                } else if (pa.getType() == 2) {
                    BaseDto baseDto=apiInfoClient.quertyOneById(kwList.get(i));
                    if(baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                        KeyWord kw= (KeyWord) baseDto.getData();
                        params.put("keyword", kw.getKeyword());
                        params.put("filterKeyword", kw.getFilterKeyword());
                    }
                }else if(pa.getType() == 4 && StringUtils.isNotBlank(kwList.get(i))){
                    params.put("keyword", kwList.get(i));
                    if(filterList !=null &&filterList.size()!=0){
                        params.put("filterKeyword", filterList.get(i));
                    }
                }
                String jsonStr = HttpRequestUtils.sendGet(SysConfig.cfgMap.get("API_ALL_CHART_URL")+ IntraBusinessAPIConfig.getValue("ProductsAreaStatisticsURL"), params);
                System.out.print("---jsonStr---"+jsonStr+"--params---"+ params.toString());
                StatView sv = JSON.parseObject(jsonStr, StatView.class);
                if (sv != null && "0000".equals(sv.getCode()) && CollectionUtils.isNotEmpty(sv.getStatList())) {
                    com.miduchina.wrd.dto.eventanalysis.products.IGroupResult  ir = new com.miduchina.wrd.dto.eventanalysis.products.IGroupResult();
                    if(pa.getType()==1){
                        ir.setGroupValue(kwList.get(i));
                    }else if(pa.getType()==2){
                        BaseDto baseDto=apiInfoClient.quertyOneById(kwList.get(i));
                        if(baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                            KeyWord kw= (KeyWord) baseDto.getData();
                            ir.setGroupValue(kw.getKeywordName());
                        }

                    }else if(pa.getType()==3){
                        ir.setGroupValue(nameList.get(i));
                    }else if(pa.getType()==4){
                        ir.setGroupValue(nameList.get(i));
                    }

                    List<com.miduchina.wrd.dto.eventanalysis.products.IGroupResult > subGroupList = new ArrayList<com.miduchina.wrd.dto.eventanalysis.products.IGroupResult >();
                    for (Stat s : sv.getStatList()) {
                        if(s != null){
                            com.miduchina.wrd.dto.eventanalysis.products.IGroupResult  subGroup = new com.miduchina.wrd.dto.eventanalysis.products.IGroupResult ();
                            subGroup.setGroupValue(s.getName());
                            subGroup.setTotal(s.getNum().intValue());
                            subGroupList.add(subGroup);
                        }
                    }
                    ir.setSubGroups(subGroupList);
                    list.add(ir);
                }else{
                    return json;
                }
            }

            EChartsAdapter adapter = new EChartsAdapter(list, "map2", "信息地域分布");
            json = JSONObject.toJSONString(adapter.getChart());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("getMapChartProductsAnalysis"+JSONObject.toJSONString(e));
        }
        System.out.println(json);
        return json;
    }
    public static Map<String, Object> getDataInitMap(Integer userId){
        Map<String, Object> map = new HashMap<>();
        map.put("channel", WyqDataConfig.channel);
        map.put("platform", WyqDataConfig.platform);
        map.put("userTag", userId.toString());
        //map.put("format", WyqDataConfig.format);
        return map;
    }

    /**
     * 获取竞品分析排除词
     * @param pa
     * @return
     */
    private List<String> getFilterList(ProductsAnalysis pa){
        List<String> filterList = new ArrayList<String>();
        if(pa!=null){
            if(pa.getType() == 1 || pa.getType() == 3 || pa.getType() == 4){
                String jsonData = pa.getJsonData();
                if(StringUtils.isNotBlank(jsonData)){
                    ProductsAnalysisKeyword pak = getWarningSearchCondition(jsonData);
                    if(pak!=null){
                        //if(pak.getFilterKeyword1()!=null&&!"".equals(pak.getFilterKeyword1())){
                        filterList.add(pak.getFilterKeyword1());
                        //}
                        //if(pak.getFilterKeyword2()!=null&&!"".equals(pak.getFilterKeyword2())){
                        filterList.add(pak.getFilterKeyword2());
                        //}
                        //if(pak.getFilterKeyword3()!=null&&!"".equals(pak.getFilterKeyword3())){
                        filterList.add(pak.getFilterKeyword3());
                        //}
                        //if(pak.getFilterKeyword4()!=null&&!"".equals(pak.getFilterKeyword4())){
                        filterList.add(pak.getFilterKeyword4());
                        //}
                        //if(pak.getFilterKeyword5()!=null&&!"".equals(pak.getFilterKeyword5())){
                        filterList.add(pak.getFilterKeyword5());
                        //}
                        //if(pak.getFilterKeyword6()!=null&&!"".equals(pak.getFilterKeyword6())){
                        filterList.add(pak.getFilterKeyword6());
                        //}
                    }
                }
            }
        }
        return filterList;
    }
    //json字符串转换为对象
    private ProductsAnalysisKeyword getWarningSearchCondition(String jsonData)
    {
        ProductsAnalysisKeyword products = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            products = mapper.readValue(jsonData, ProductsAnalysisKeyword.class);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return products;
    }

    /**
     * 获取竞品分析名称
     * @param pa
     * @return
     */
    private List<String> getNameList(ProductsAnalysis pa){
        List<String> nameList = new ArrayList<String>();
        if(pa!=null){
            if(pa.getType()==1 || pa.getType()==3 || pa.getType()==4){
                String jsonData = pa.getJsonData();
                if(StringUtils.isNotBlank(jsonData)){
                    ProductsAnalysisKeyword pak = JSON.parseObject(jsonData, ProductsAnalysisKeyword.class);
                    if(pak!=null){
                        if(StringUtils.isNotBlank(pak.getName1())){
                            nameList.add(pak.getName1());
                        }
                        if(StringUtils.isNotBlank(pak.getName2())){
                            nameList.add(pak.getName2());
                        }
                        if(StringUtils.isNotBlank(pak.getName3())){
                            nameList.add(pak.getName3());
                        }
                        if(StringUtils.isNotBlank(pak.getName4())){
                            nameList.add(pak.getName4());
                        }
                        if(StringUtils.isNotBlank(pak.getName5())){
                            nameList.add(pak.getName5());
                        }
                        if(StringUtils.isNotBlank(pak.getName6())){
                            nameList.add(pak.getName6());
                        }
                    }
                }
            }
        }
        return nameList;
    }

    /**
     * 获取竞品分析关键词
     * @param pa
     * @return
     */
    private List<String> getKeyWordList(ProductsAnalysis pa){
        List<String> kwList = new ArrayList<String>();
        if(pa!=null){
            if(pa.getType() == 1 || pa.getType() == 3 || pa.getType() == 4){
                String jsonData = pa.getJsonData();
                if(StringUtils.isNotBlank(jsonData)){
                    ProductsAnalysisKeyword pak = JSON.parseObject(jsonData, ProductsAnalysisKeyword.class);
                    if(pak!=null){
                        if(StringUtils.isNotBlank(pak.getKeyword1())){
                            kwList.add(pak.getKeyword1());
                        }
                        if(StringUtils.isNotBlank(pak.getKeyword2())){
                            kwList.add(pak.getKeyword2());
                        }
                        if(StringUtils.isNotBlank(pak.getKeyword3())){
                            kwList.add(pak.getKeyword3());
                        }
                        if(StringUtils.isNotBlank(pak.getKeyword4())){
                            kwList.add(pak.getKeyword4());
                        }
                        if(StringUtils.isNotBlank(pak.getKeyword5())){
                            kwList.add(pak.getKeyword5());
                        }
                        if(StringUtils.isNotBlank(pak.getKeyword6())){
                            kwList.add(pak.getKeyword6());
                        }
                    }
                }
            }else if(pa.getType() == 2){
                if(StringUtils.isNotBlank( pa.getKeywordIds())){
                    String[] keywordIdArr = pa.getKeywordIds().split(",");
                    if(ArrayUtils.isNotEmpty(keywordIdArr)){
                        for(String kwId:keywordIdArr){
                            kwList.add(kwId);
                        }
                    }
                }
            }
        }
        return kwList;
    }
    /**
     * 判断时间是否大于现在
     *
     * @param endTime
     * @return
     */
    private String retEndTime(String endTime) {
        if (DateUtils.parse(endTime, DateUtils.FORMAT_LONG).getTime() > new Date().getTime()) {
            return DateUtils.format(new Date(), DateUtils.FORMAT_LONG);
        }
        return endTime;
    }
    //传播走势
    @RequestMapping(value = "/spreadTrend")
    public void spreadTrend(HttpServletResponse response,String pabId,String fileName,String starttime,String endtime){
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            //根据ID获取竞品分析报告
//            ProductsAnalysisBrief pab = productsAnalysisBean.getPAB(pabId);
            HashMap<String,Object> params=new HashMap<String, Object>();
            params.put("id", pabId);
            params.put("platformTag", "wyq");
            ProductsAnalysisBrief pab=Utils.getRequestSingleProductAnalysis(params,"getSingleAnalysisById");

            StringBuilder siteResult = new StringBuilder();

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            String filePath="";
            if(Flags.local_flag){
                if(pab.getProductsAnalysisId()>0){
                    filePath= Flags.filePath +"productsAnalysis/"+DateUtils.format(pab.getCreateTime(),DateUtils.FORMAT_SHORT_TIME)+ "/" +pab.getUserId()+"/"+ pab.getProductsAnalysisId() + "/"+fileName+".txt";

                }else{
                    filePath = Flags.filePath + "productsAnalysis/" + DateUtils.format(pab.getCreateTime(),DateUtils.FORMAT_SHORT_TIME) + "/" + pab.getTicket() + "/"+fileName+".txt";

                }
                System.out.println(filePath);

            }else{
                if(pab.getProductsAnalysisId()>0){
                    filePath= SysConfig.cfgMap.get("FILE_UPLOAD_PATH")+"productsAnalysis/"+DateUtils.format(pab.getCreateTime(),DateUtils.FORMAT_SHORT_TIME)+ "/" +pab.getUserId()+"/"+ pab.getProductsAnalysisId() + "/"+fileName+".txt";
                }else{
                    filePath =SysConfig.cfgMap.get("FILE_UPLOAD_PATH")+"productsAnalysis/"+DateUtils.format(pab.getCreateTime(),DateUtils.FORMAT_SHORT_TIME) + "/" + pab.getTicket() + "/"+fileName+".txt";
                }
            }

//            String filePath=systemConfig.getUploadPath()+"productsAnalysis/"+DateUtils.format(pab.getCreateTime(),DateUtils.FORMAT_SHORT_TIME)+ "/" +pab.getUserId()+"/"+ pab.getProductsAnalysisId() + "/spreadTrend.txt";
            log.info("spreadTrend"+filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"));
            String line = null;
            while ((line = reader.readLine())!=null){
                siteResult.append(line);
            }

            if(pab.getProductsAnalysisId()<=0){
                String[] json = new String[1];
                List<IGroupResult> iGroupResults = JSON.parseObject(siteResult.toString(), new com.alibaba.fastjson.TypeReference<List<IGroupResult>>(){});
                if(CollectionUtils.isNotEmpty(iGroupResults)){
                    if(Objects.equals("spreadTrend", fileName) || Objects.equals("negativeTrend", fileName)){
                        supplementData(iGroupResults, DateUtils.parse(starttime),DateUtils.parse(endtime));
                    }
                    if(Objects.equals("spreadTrend", fileName)){
                        EChartsAdapter1 adapter = new EChartsAdapter1(iGroupResults, "line", "对比趋势图");
                        json[0] = JSON.toJSONString(adapter.getChart());
                    }else if(Objects.equals("negativeTrend", fileName)){
                        EChartsAdapter1 adapter = new EChartsAdapter1(iGroupResults, "line3", "对比趋势图");
                        json[0] = JSON.toJSONString(adapter.getChart());
                    }else if(Objects.equals("sensitiveStatistics", fileName)){//情感对比
                        EChartsAdapter1 adapter = new EChartsAdapter1(iGroupResults, "bar", "对比趋势图");
                        json[0] = JSON.toJSONString(adapter.getChart());
                    }else if(Objects.equals("positiveWord", fileName)){
                        json[0] = JSON.toJSONString(iGroupResults);
                    }else if(Objects.equals("negativeWord", fileName)){
                        json[0] = JSON.toJSONString(iGroupResults);
                    }else if(Objects.equals("wordCloud", fileName)){
                        json = new String[iGroupResults.size()];
                        int i = 0;
                        for(IGroupResult iGroupResult:iGroupResults){
                            json[i] = joinWordCloudJson(iGroupResult.getSubGroups());
                            i++;
                        }
                    }else if(Objects.equals("areaStatistics", fileName)){
                        EChartsAdapter1 adapter = new EChartsAdapter1(iGroupResults, "map2", "地域分布图");
                        json[0] = JSON.toJSONString(adapter.getChart());
                    }else if(Objects.equals("sourceType", fileName)){
                        EChartsAdapter1 adapter = new EChartsAdapter1(iGroupResults, "bar2", "媒体来源对比");
                        json[0] = JSON.toJSONString(adapter.getChart());
                    }else if(Objects.equals("sourceMedia", fileName)){
                        json[0] = JSON.toJSONString(iGroupResults);
                    }else if(Objects.equals("mediaMonitoring", fileName)){//重点媒体监测
                        EChartsAdapter1 adapter = new EChartsAdapter1(iGroupResults, "bar2", "媒体来源对比");
                        json[0] = JSON.toJSONString(adapter.getChart());
//	                	printWriter.print(JSON.toJSONString(siteResult));
//	                	return;
                    }
                    printWriter.print(JSON.toJSON(json));
                    return;
                }
            }else{
                printWriter.print(JSON.toJSONString(siteResult));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("spreadTrend"+JSONObject.toJSONString(e));
        }

    }
    public String joinWordCloudJson(List<IGroupResult> iGroupResultList){
        String json = "";
        String[] colors = { "#72c1be", "#f29300", "#a05623", "#277bc0" };
        StringBuilder sb = new StringBuilder("[{data:[");
        for (IGroupResult iGroupResult : iGroupResultList) {
            int i = (int) (Math.random() * 4);
            sb.append("{name:'" + iGroupResult.getGroupValue() + "',");
            sb.append("value:" + (iGroupResult.getTotal() * 10));
            sb.append(",itemStyle: {normal: {color: '");
            sb.append(colors[i]);
            sb.append("'}}},");
        }
        json = sb.substring(0, sb.length() - 1) + "]}]";

        return json;
    }
    public void supplementData(List<IGroupResult> iGroupResults, Date startTime, Date endTime){
        if(CollectionUtils.isEmpty(iGroupResults)){
            return;
        }

        String timeNode = iGroupResults.get(0).getSubGroups().get(0).getGroupValue();
        Integer timeNodeType = CommonUtils.getTimeNodeType(timeNode);

        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        int isHour = 2; //1-С? 2-?? 3-??
        long miunsL = 1*24*60*60;

        if(timeNodeType == 1){
            isHour = 1;
            miunsL = 3600;
            sdf  = new SimpleDateFormat("yyyy-MM-dd HH");
        }else if(timeNodeType == 2){
            isHour = 2;
            miunsL = 24*60*60;
            sdf  = new SimpleDateFormat("yyyy-MM-dd");
        }
        else if(timeNodeType == 3){
            isHour = 3;
            miunsL = 30*24*60*60;
            sdf  = new SimpleDateFormat("yyyy-MM");
        }
        if(isHour!=1){
            endTime.setHours(23);
            startTime.setHours(0);
        }
        endTime.setMinutes(59);
        endTime.setSeconds(59);
        startTime.setMinutes(0);
        startTime.setSeconds(0);
        long days =  (endTime.getTime()-startTime.getTime())/1000/miunsL +1;
        long startTimeIndex = startTime.getTime()/1000;
        Map<String,Integer> dateMap = new HashMap<String,Integer>();
        List<IGroupResult> nullList = new ArrayList<IGroupResult>();
        for(int j=0;j<days;j++){
            Date date = new Date(startTimeIndex*1000);
            String gv = sdf.format(date);
            dateMap.put(gv,j);

            IGroupResult igr = new IGroupResult();
            igr.setGroupValue(gv);
            igr.setTotal(0);
            nullList.add(igr);

            startTimeIndex += miunsL;
        }

        for(IGroupResult iGroupResult:iGroupResults){
            List<IGroupResult> iGroupResultsSon = iGroupResult.getSubGroups();
            if(CollectionUtils.isEmpty(iGroupResultsSon)){
                iGroupResult.setSubGroups(nullList);
            }else if(iGroupResultsSon.size() != days){
                List<IGroupResult> tempList = new ArrayList<>();
                startTimeIndex = startTime.getTime()/1000;
                for(int j=0;j<days;j++){
                    Date date = new Date(startTimeIndex*1000);
                    String gv = sdf.format(date);
                    IGroupResult igrt = new IGroupResult();
                    igrt.setGroupValue(gv);
                    igrt.setTotal(0);
                    tempList.add(igrt);
                    startTimeIndex += miunsL;
                }
                for(IGroupResult iGroupResult1:iGroupResultsSon){
                    if(null != iGroupResult1){
                        if(dateMap.containsKey(iGroupResult1.getGroupValue())){
                            int j = dateMap.get(iGroupResult1.getGroupValue());
                            tempList.get(j).setTotal(iGroupResult1.getTotal());
                        }
                    }
                }
                iGroupResult.setSubGroups(tempList);
            }
        }
    }
}
