package com.miduchina.wrd.webthermalquery.util;



import com.miduchina.wrd.util.MD5Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  qiuqiu
 * @date 创建时间：2018年5月8日 下午5:03:35
 */
public class AbnormalAnalysisUtil {

    private static final Log logger = LogFactory.getLog(AbnormalAnalysisUtil.class);
    
//	public static final int ABLE_TYPE_REQUEST = 1;
//	public static final int ABLE_TYPE_OPERATE_LOG = 2;
	
	public static void setAblesc(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpServletResponse response = servletRequestAttributes.getResponse();
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
        Cookie ablesc = LoginUtils.getCookie(request, "ablesc");
        if(ablesc == null){
        	ablesc = new Cookie("ablesc", MD5Utils.MD5Encode(String.valueOf(System.currentTimeMillis())));
        	ablesc.setMaxAge(24*3600);
        }else{
        	ablesc.setValue(MD5Utils.MD5Encode(String.valueOf(System.currentTimeMillis())));
        	ablesc.setMaxAge(24*3600);
        }
		response.addCookie(ablesc);
	}
	
	
//	public static boolean checkAblesc(Integer userId){
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//
//		if(userId == null){
//			userId = LoginUtils.getUserIdFromSid(LoginUtils.getSidFromCookie(request));
//		}
//		if(SysConfig.KEYWORD_WRITE_USER_LIST.contains(userId.toString())){
//			return true;
//		}
//
//		String encryptAblescVal = null;
//		String ip = CommonUtils.getOriginalRequestIp(request);
//		Cookie ablescenCookie = LoginUtils.getCookie(request, "ablescen");
//		if(ablescenCookie == null || StringUtils.isBlank(ablescenCookie.getValue())){
//			logger.info("checkAblesc : request abnormal, ablescenCookie is null, ip=" + ip + ", userId=" + userId);
//			return false;
//		}else{
//			encryptAblescVal = ablescenCookie.getValue();
//		}
//		if(StringUtils.isBlank(encryptAblescVal)){
//			logger.info("checkAblesc : request abnormal, encryptAblescVal is null, ip=" + ip + ", userId=" + userId);
//			return false;
//		}else{
//			ablescenCookie.setValue("");
//			ablescenCookie.setMaxAge(0);
//			response.addCookie(ablescenCookie);
//	        Cookie ablescCookie = LoginUtils.getCookie(request, "ablesc");
//	        if(ablescCookie == null){
//				logger.info("checkAblesc : request abnormal, ablescCookie is null, ip=" + ip + ", userId=" + userId);
//	        	return false;
//	        }else{
//	        	String ablescVal = ablescCookie.getValue();
//	        	if(!encryptAblescVal.equals(MD5Util.md5(ablescVal))){
//	        		setAblesc();
//					logger.info("checkAblesc : request abnormal, ablescVal not equal encryptAblescVal, ip=" + ip + ", userId=" + userId);
//	        		return false;
//	        	}else{
//	        		setAblesc();
//	        		return true;
//	        	}
//	        }
//		}
//	}
//
//	public static boolean checkAbnormalRequest(int type, int pageCode, String pageName, Integer userId){
//		HttpServletRequest request = ServletActionContext.getRequest();
//
//		if(userId == null){
//			userId = LoginUtils.getUserIdFromSid(LoginUtils.getSidFromCookie(request));
//		}
//		if(userId != null && SysConfig.KEYWORD_WRITE_USER_LIST.contains(userId.toString())){
//			return true;
//		}
//
//		String ip = CommonUtils.getOriginalRequestIp(request);
//		String key = Constants.generateJedisKey(Constants.JEDIS_KEYS_ABNORMAL_DETAIL + pageCode + "_" + userId);
//		String json = JedisUtil.getAttribute(key);
//		if(StringUtils.isBlank(json)){
//			JSONObject ableDetail = new JSONObject();
//			if(type == ABLE_TYPE_REQUEST){
//				ableDetail.put("request", 1);
//				ableDetail.put("operateLog", 0);
//			}else if(type == ABLE_TYPE_OPERATE_LOG){
//				ableDetail.put("operateLog", 1);
//				ableDetail.put("request", 0);
//			}
//			JedisUtil.setAttribute(key, JSONObject.toJSONString(ableDetail), 10*60);
//		}else{
//			JSONObject ableDetail = JSONObject.parseObject(json);
//			Integer requestNum = ableDetail.getInteger("request");
//			Integer operateLogNum = ableDetail.getInteger("operateLog");
//			if(requestNum == null){
//				requestNum = 0;
//			}
//			if(operateLogNum == null){
//				operateLogNum = 0;
//			}
//			if(type == ABLE_TYPE_REQUEST){
//				requestNum++;
//			}else if(type == ABLE_TYPE_OPERATE_LOG){
//				operateLogNum++;
//			}
//			ableDetail.put("request", requestNum);
//			ableDetail.put("operateLog", operateLogNum);
//			JedisUtil.setAttribute(key, JSONObject.toJSONString(ableDetail), 10*60);
//			if(requestNum > 10 && operateLogNum.doubleValue()/requestNum.doubleValue() < 0.3){
//				logger.info("checkAbnormalRequest : request abnormal userId=" + userId + ", pageName=" + pageName + ", ip=" + ip + ", requestNum:" + requestNum + ", operateLogNum" + operateLogNum);
//				return false;
//			}
//		}
//		return true;
//	}
}


