package com.miduchina.wrd.webthermalquery.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.hotsportview.MessageRes;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 任务消息处理
 *
 * @version 1.0
 * @since 2016年12月20日 上午11:02:48
 * @author virgo
 */
@Slf4j
@Controller
@RequestMapping("task/message")
public class TaskMessageController extends BaseController{
	@Autowired
	private UserFeignClient userFeignClient;

//	private static final long serialVersionUID = 1L;
//	private int messageId;
//	private String url;

//	@Autowired
//	private TaskMessageBean taskMessageBean;
//	@Autowired
//	private KeyWordBean keyWordBean;
//	@Autowired
//	private ExportDataService exportDataService;

//	@Override
//	public String execute() throws Exception {
//		return super.execute();
//	}

	/**
	 * 获取消息列表--多条
	 * @param
	 * @since 2017年2月28日 下午5:02:07
	 * @author virgo
	 * @return
	 */
//	public void getTaskMessageList() throws Exception {
//		fetchSessionAdmin();
//		if (admin != null) {
//			HttpServletResponse response = ServletActionContext.getResponse();
//			response.setContentType("application/json;charset=GBK;");
//			PrintWriter printWriter = null;
//			try {
//				printWriter = response.getWriter();
//				Map<String, Object> messageParams = new HashMap<String, Object>();
//				messageParams.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
//				messageParams.put("startTime",
//						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-16 20:00:00").getTime());
//				String result = CommonUtils.sendIntraBusinessAPIPOST(ServletActionContext.getRequest(),
//						IntraBusinessAPIConfig.getValue("getMessageList"), messageParams);
//				if (StringUtils.isBlank(result))
//					log.info("get message result is null");
//				MessageRes messageRes = JSONObject.parseObject(result, MessageRes.class);
//				if (messageRes == null
//						|| !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(messageRes.getCode()))
//					log.info("MessageRes is null");
//				printWriter.print(JSON.toJSON(messageRes));
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error("get message list is faliure!");
//				String nullStr = "NULL";
//				printWriter.print(nullStr);
//			}
//		}
//	}

	/**
	 * 获取消息列表--单条弹出框
	 * @param
	 * @since 2017年2月28日 下午5:03:59
	 * @author virgo
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getTaskMessageList4Pop")
	@ResponseBody
	public void getTaskMessageList4Pop() throws Exception {
		UserDto admin = fetchSessionAdmin();
		if(admin != null) {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = servletRequestAttributes.getRequest();
			HttpServletResponse response = servletRequestAttributes.getResponse();
//			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset=GBK;");
			PrintWriter printWriter = null;
			try {
				// 查询支付未启动的数据导出任务
				if (admin.getUserId() > 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
//								searchAndBeginExportTask(admin.getUserId());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
				printWriter = response.getWriter();
				Map<String, Object> messagePopParams = new HashMap<String, Object>();
				messagePopParams.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
				String result = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,
						IntraBusinessAPIConfig.getValue("getMessagePopList"), messagePopParams);
				log.info(admin.getUserId() + "getMessagePopList:" + result);
				if (StringUtils.isBlank(result))
					log.info("get message result is null");
				MessageRes messagePopRes = JSONObject.parseObject(result, MessageRes.class);
				if (messagePopRes == null
						|| !BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(messagePopRes.getCode()))
					log.info("messagePopRes is null");
				printWriter.print(JSON.toJSON(messagePopRes));
			} catch (Exception e) {
				e.printStackTrace();
				log.error("get message list is faliure!");
				String nullStr = "NULL";
				printWriter.print(nullStr);
			}
		}
	}

//	private void searchAndBeginExportTask(int userId) throws Exception {
//		HashMap<String, Object> map = new HashMap<>();
//		map.put("userId",userId);
//		BaseDto orderExportStatus = userFeignClient.findOrderExportStatus(map);
//		List<OrderExportRelation> oerList = (List<OrderExportRelation>) orderExportStatus.getData();
////		List<OrderExportRelation> oerList = keyWordBean.findOrderExportStatus(userId); // 未创建任务的已支付订单
//		if (CollectionUtils.isNotEmpty(oerList)) {
//			for (OrderExportRelation oer : oerList) {
//				String errorMessage = null;
//				String flag = RedisUtils.getAttribute(RedisUtils.generateJedisKey(
//						SystemConstants.JEDIS_KEYS_EXPORT_DATA_TASK_START + userId + "_" + oer.getConditionId()));
//				// 避免任务同时处理出现多条记录
//				if (flag != null && flag.equals("1")) {
//					return;
//				} else {
//					RedisUtils.setAttribute(
//							RedisUtils.generateJedisKey(
//									SystemConstants.JEDIS_KEYS_EXPORT_DATA_TASK_START + userId + "_" + oer.getConditionId()),
//							"1", SystemConstants.TASK_START_TIME);
//				}
//				log.info(
//						"searchAndBeginExportTask========> userId:" + userId + " conditionId:" + oer.getConditionId());
////				ExportKeywordAnalysisCondition ekac = keyWordBean.findConditionId(oer.getConditionId());
//				HashMap<String, Object> map1 = new HashMap<>();
//				map1.put("conditionId",oer.getConditionId());
//				BaseDto conditionId = userFeignClient.findConditionId(map1);
//				ExportKeywordAnalysisCondition ekac = (ExportKeywordAnalysisCondition) conditionId.getData();
//				if (ekac != null) {
//					if (StringUtils.isNotBlank(ekac.getAnalysisTaskTicket())) { // 订单支付并创建任务
//						oer.setUpdateTime(new Date());
//						oer.setExportTaskStatus(BusinessConstant.EXPORT_TASK_STATUS_CREATE);
//						HashMap<String, Object> map2 = new HashMap<>();
//						map2.put("orderExportRelation",oer);
//						userFeignClient.saveOrUpdateNotLoginOperateRecord(map2);
////						keyWordBean.saveOrUpdateOrderExport(oer);
//					} else {
//						// 插入任务
////						HashMap<String, Object> map3 = new HashMap<>();
////						map3.put("orderExportRelation",oer);
////						userFeignClient.saveOrUpdateNotLoginOperateRecord(map3);
//						AnalysisTaskView analysisTaskView = exportDataService.insertExportTask(ekac, true);
//						if (null != analysisTaskView
//								&& ErrorCodeConstant.F_OPERATE_SUCCESS.equals(analysisTaskView.getCode())) {
//							// 确认任务
//							for (int i = 0; i < 3; i++) {
//								BaseView baseView = WyqWebDbMethodV1.analysisTaskConfirm(
//										String.valueOf(ekac.getUserId()),
//										analysisTaskView.getAnalysisTask().getAnalysisTaskTicket());
//								if (null != baseView) {
//									if (baseView.getCode().equals("2001")) {
//										oer.setUpdateTime(new Date());
//										oer.setExportTaskStatus(BusinessConstant.EXPORT_TASK_STATUS_CREATE);
//										HashMap<String, Object> map2 = new HashMap<>();
//										map2.put("orderExportRelation",oer);
//										userFeignClient.saveOrUpdateNotLoginOperateRecord(map2);
////										keyWordBean.saveOrUpdateOrderExport(oer);
//										break;
//									} else {
//										errorMessage = "ExportJobTask.autoAnalysisSolidify analysisTaskConfirm fail, because code["
//												+ baseView.getCode() + "] message[" + baseView.getMessage()
//												+ "] conditionId[" + ekac.getConditionId() + "] " + "taskTicket["
//												+ ekac.getAnalysisTaskTicket() + "] transactionId["
//												+ baseView.getTransactionId() + "] " + i;
//									}
//								} else {
//									errorMessage = "ExportJobTask.autoAnalysisSolidify analysisTaskConfirm fail, because baseView is null conditionId["
//											+ ekac.getConditionId() + "] " + "taskTicket["
//											+ ekac.getAnalysisTaskTicket() + "] " + i;
//								}
//							}
//						} else {
//							if (null != analysisTaskView) {
//								errorMessage = "ExportJobTask.autoAnalysisSolidify insertExportTask fail, because code["
//										+ analysisTaskView.getCode() + "] message[" + analysisTaskView.getMessage()
//										+ "] transactionId[" + analysisTaskView.getTransactionId() + "] conditionId["
//										+ ekac.getConditionId() + "] ";
//								// 特殊账号额度不够创建失败
//								if (ErrorCodeConstant.F_ANALYSISTASKWHITEUSER.equals(analysisTaskView.getCode())) {
//									oer.setUpdateTime(new Date());
//									oer.setStatus(BusinessConstant.STATUS_INVALID);
//									HashMap<String, Object> map2 = new HashMap<>();
//									map2.put("orderExportRelation",oer);
//									userFeignClient.saveOrUpdateNotLoginOperateRecord(map2);
////									keyWordBean.saveOrUpdateOrderExport(oer);
//									break;
//								}
//							} else {
//								errorMessage = "ExportJobTask.autoAnalysisSolidify insertExportTask fail, because analysisTaskView is null conditionId["
//										+ ekac.getConditionId() + "] ";
//							}
//						}
//						if (StringUtils.isNoneBlank(errorMessage)) {
//							errorMessage += "环境-" + SysConfig.memoCacheName;
//							log.error(errorMessage);
//							sendErrorEmailBean.sendErrorEmail(errorMessage, "微热点(微舆情)任务-数据导出出错");
//						}
//						Thread.sleep(15000);
//					}
//				}
//				// 清除缓存标记
//				RedisUtils.setAttribute(
//						RedisUtils.generateJedisKey(
//								SystemConstants.JEDIS_KEYS_EXPORT_DATA_TASK_START + userId + "_" + oer.getConditionId()),
//						"0", 0);
//			}
//		}
//	}

	/**
	 * 更改消息状态
	 * @param
	 * @since 2017年3月1日 下午4:35:48
	 * @author virgo
	 * @return
	 */
//	public void setMessageStatus() throws Exception {
//		fetchSessionAdmin();
//		if (admin != null) {
//			try {
//				taskMessageBean.updateTaskMessageStatus(admin.getUserId(), messageId);
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error("updata message status is faliure!");
//			}
//		}else {
//
//		}
//	}

	/**
	 * 微信分享参数
	 * @param
	 * @since 2017年4月13日 下午8:01:01
	 * @author virgo
	 * @return
	 */
//	public void getWeixinConfig() throws Exception {
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("application/json;charset=GBK;");
//		PrintWriter printWriter = null;
//		try {
//			printWriter = response.getWriter();
//			String ticket = WeChatInitJSUtils.getJsapiTicket();
//			Map<String, String> ret = WeChatInitJSUtils.sign(ticket, url);
//			WeChatConfigParams wccp = new WeChatConfigParams();
//			wccp.setAppId(ret.get("appId"));
//			wccp.setJsapi_ticket(ret.get("jsapi_ticket"));
//			wccp.setNonceStr(ret.get("nonceStr"));
//			wccp.setSignature(ret.get("signature"));
//			wccp.setTimestamp(ret.get("timestamp"));
//			wccp.setUrl(ret.get("url"));
//			printWriter.print(JSON.toJSON(wccp));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
