package com.miduchina.wrd.eventanalysis.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.StringUtil;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.eventanalysis.compet.ProductAnalysis;
import com.miduchina.wrd.po.eventanalysis.compet.ProductAnalysisView;
import com.xd.tools.view.CtksStatsView;
import com.xd.tools.view.StatView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class Utils {
    public  static ServletContext getServletContext(HttpServletRequest request){
        return request.getSession().getServletContext();
    }
    public static String getUserEncode(int userId){
        return CommonUtils.buildUserEncode(String.valueOf(userId));
    }
    public static String  getBaseUrl(HttpServletRequest request){
        String appContext = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort() + appContext;
        return basePath;
    }

    /**
     * 发送POST请求
     * @param url
     * @param params
     * @return
     */
    public static String sendPost(String url, Map<String, Object> params) {
        long start = System.currentTimeMillis();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String urlWithParams = url;
        String rtnStr = null;
        try {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
            httpPost = new HttpPost(urlWithParams);
            httpPost.setHeader("Connection", "Close");
            if (MapUtils.isNotEmpty(params)) {
                List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() != null)
                        postParams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                urlWithParams += "?" + StringUtils.join(postParams.toArray(), "&");
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams, "UTF-8");
                httpPost.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = httpResponse.getEntity();
                rtnStr = EntityUtils.toString(resEntity, "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpPost != null)
                httpPost.releaseConnection();
        }
        System.out.println("Post [" + urlWithParams + "] 请求时长：" + (System.currentTimeMillis() - start) + "ms");
        return rtnStr;
    }
    /**
     * 发送GET请求
     * @param url
     * @param params
     * @return
     */
    public static String sendGet(String url, Map<String, Object> params) {
        long start = System.currentTimeMillis();
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String urlWithParams = url;
        String rtnStr = null;
        try {
            if (MapUtils.isNotEmpty(params)) {
                List<String> paramList = new ArrayList<String>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() != null)
                        paramList.add(
                                entry.getKey() + "=" + URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
                }
                urlWithParams += "?" + StringUtils.join(paramList.toArray(), "&");
            }
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
            httpGet = new HttpGet(urlWithParams);
            httpGet.setHeader("Connection", "Close");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity resultEntity = httpResponse.getEntity();
                rtnStr = EntityUtils.toString(resultEntity, "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpGet != null)
                httpGet.releaseConnection();
        }
        System.out.println("GET URL = [" + urlWithParams + "]  请求时长：" + (System.currentTimeMillis() - start) + "ms");
        return rtnStr;
    }
    /**
     * 发送GET请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String sendGet(String url, String params) {
        long start = System.currentTimeMillis();
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String urlWithParams = url;
        String rtnStr = null;
        try {
            if (StringUtils.isNotBlank(params)) {
                String paramArray[] = params.split("&");
                List<String> paramList = new ArrayList<>();
                for (String string : paramArray) {
                    String singleparamArray[] = string.split("=");
                    if (singleparamArray.length == 2) {
                        paramList.add(singleparamArray[0] + "=" + URLEncoder.encode(singleparamArray[1], "UTF-8"));
                    }
                }
                urlWithParams = urlWithParams + "?" + StringUtils.join(paramList.toArray(), "&");
            }
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
            httpGet = new HttpGet(urlWithParams);
            httpGet.setHeader("Connection", "Close");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity resultEntity = httpResponse.getEntity();
                rtnStr = EntityUtils.toString(resultEntity, "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpGet != null)
                httpGet.releaseConnection();
        }
        System.out.println("GET URL = [" + urlWithParams + "]  请求时长：" + (System.currentTimeMillis() - start) + "ms");
        return rtnStr;
    }
    public static String getStringFromDate(Date date,String format){
        if(TextUtils.isEmpty(format)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }
    public static ProductsAnalysisBrief getRequestSingleProductAnalysis(HashMap params, String url_flag){
        String rtnStr = Utils.sendIntraBusinessAPIPOST(Utils.getRequest(), url_flag, params);
        System.out.println(rtnStr);
        if(StringUtils.isNotBlank(rtnStr)){
            ProductAnalysisView productList = JSON.parseObject(rtnStr,ProductAnalysisView.class);
            if(productList !=null && CodeConstant.SUCCESS_CODE.equals(productList.getCode())){
                ProductAnalysis p = productList.getProductAnalysis();
                if(p!=null){
                    ProductsAnalysisBrief pab = new ProductsAnalysisBrief();
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
                    pab.setSensitiveDesc(p.getSensitiveDesc());
                    pab.setWordDesc(p.getWordDesc());
                    pab.setAreaDesc(p.getAreaDesc());
                    pab.setSourceTypeDesc(p.getSourceTypeDesc());
                    pab.setSourceMediaDesc(p.getSourceMediaDesc());
                    pab.setMonitoringDesc(p.getMonitoringDesc());
                    pab.setProductsAnalysisId(p.getProductAnalysisId());
                    return pab;
                }
            }
        }
        return null;
    }

    public static String sendWrdIntraBusinessAPIPOST(HttpServletRequest request, String apiString, Map<String,Object> params){
        params.put("platform", BusinessConstant.PLATFORM_H5);
        String urls=IntraBusinessAPIConfig.getValue(apiString);
        System.out.println("urls="+urls);

        String jsonS=BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, urls, params);
        return jsonS;
    }

    public static String sendIntraBusinessAPIPOST(HttpServletRequest request, String apiString, Map<String,Object> params){
        params.put("platform", BusinessConstant.PLATFORM_H5);
        String urls=IntraBusinessAPIConfig.getValue(apiString);
        System.out.println("urls="+urls);

        String jsonS=BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, urls, params);
        return jsonS;
    }
    public static HttpServletRequest  getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return request;
    }
    public static HttpServletResponse  getResponse(){
        HttpServletResponse resp = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
        return resp;
    }
    public final static String  formatDate="yyyy-MM-dd HH:mm:ss";
    public static String getStringFromDate(Long source,String format){
        if(!TextUtils.isEmpty(format)){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date(source);
            return sdf.format(date);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
            Date date = new Date(source);
            return sdf.format(date);
        }
    }
    /**
     * 微分析
     * @param fileName
     * @param filePath
     * @param className
     * @return
     */
    public static <T> T getWeiboFileDataToObject(String fileName, String filePath, Class<T> className) {
        return getWeiboFileDataToObject(fileName, ".txt", filePath, className);
    }
    public static <T> T getWeiboFileDataToObject(String fileName, String postfix, String filePath, Class<T> className) {
        T view = null;
        if (StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(postfix) && StringUtils.isNotBlank(filePath)) {
            // 获取存储文件目录
            if(!filePath.substring(filePath.length()-1).equals("\\")){
                if(filePath.indexOf("/") >= 0){
                    if(!filePath.substring(filePath.length()-1).equals("/")){
                        filePath += "/";
                    }
                }else{
                    filePath += "\\";
                }
            }

            filePath += fileName + postfix;
            for(int i=0;i<6;i++){
                File file = new File(filePath);
                if(!file.exists()){//不存在
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    break;
                }
            }
            StringBuilder data = new StringBuilder();
            try {
                FileInputStream fis = new FileInputStream(filePath);
                InputStreamReader isr = new InputStreamReader(fis);
//						BufferedReader br = new BufferedReader(isr);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
                String line = "";
                while ((line = br.readLine()) != null) {
                    data.append(line);
                }
                if (br != null)
                    br.close();
                if (isr != null)
                    isr.close();
                if (fis != null)
                    fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isNotBlank(data)) {
                String jsonString=data.toString();
//					System.out.println("jsonString="+jsonString);
                view = JSONObject.parseObject(getUTF8StringFromGBKString(jsonString), className);
            }
        }
        return view;
    }

    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }


    public static void setResultMsgStatView(StatView view, Map<String, Object> result){
        if(view!=null && "8888".equals(view.getCode().trim())){
            result.put("message", view.getMessage());
        }
        if(view!=null){
            result.put("code", view.getCode());
            result.put("msg", view.getCode()+"|"+view.getMessage()+view.getTransactionId());
        }
    }


    public static void setResultCtksStats(CtksStatsView view,Map<String, Object> result){
        if(view!=null){
            result.put("code", view.getCode());
            result.put("msg", view.getCode()+"|"+view.getMessage()+view.getTransactionId());
        }
        if(view!=null && "8888".equals(view.getCode().trim())){
            result.put("code", view.getCode());
            result.put("message", view.getMessage());
        }
    }

    public static void setResultMsg(com.xd.tools.view.StatsView view,Map<String, Object> result){
        if(view!=null && "8888".equals(view.getCode().trim())){
            result.put("message", view.getMessage());

        }
        if(view!=null){
            result.put("code", view.getCode());
            result.put("msg", view.getCode()+"|"+view.getMessage()+view.getTransactionId());
        }
    }


    public static String getJsonFromObject(Object object){
        return JSON.toJSONString(object);
    }


    public static void sendRedirect(HttpServletRequest request,HttpServletResponse response,String openUrl,String path) throws IOException {

        if (StringUtils.isBlank(path)){
            path = request.getRequestURI();
        }
        /**
         * 获取路径中的参数
         */
        String  url = openUrl+path;
        Map<String, Object> params = callBack(request);
        if (params != null && !params.isEmpty()) {
            List<String> paramList = new ArrayList<String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                paramList.add(entry.getKey() + "=" + entry.getValue());

//                if ("filterKeyword".equals(entry.getKey()) ||"keyword1".equals(entry.getKey()) || "searchKeyword".equals(entry.getKey())){
//                    paramList.add(StringUtil.urlEncode(entry.getKey()) + "=" + StringUtil.urlEncode((String) entry.getValue()));
//                }else {
//                    paramList.add(entry.getKey() + "=" + entry.getValue());
//                }
                paramList.add(entry.getKey() + "=" + StringUtil.urlEncode((String) entry.getValue()));
            }
            url +=  "?" + org.apache.commons.lang.StringUtils.join(paramList.toArray(), "&");
        }
        response.sendRedirect(url);
    }



    public static Map<String, Object> callBack(HttpServletRequest request) {
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
