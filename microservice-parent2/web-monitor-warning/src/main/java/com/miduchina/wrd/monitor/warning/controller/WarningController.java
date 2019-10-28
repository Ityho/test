/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: WarningController.java 
 * @Prject: wyq-warning-view
 * @Package: com.xd.warning.controller 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年5月15日 下午2:28:07 
 * @version: V1.0   
 */
package com.miduchina.wrd.monitor.warning.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.miduchina.wrd.monitor.warning.model.WarningSearchCondition;
import com.miduchina.wrd.monitor.warning.operatelog.util.OperateLogUtils;
import com.miduchina.wrd.po.keyword.Warning;
import com.miduchina.wrd.po.keyword.WarningTimerSearchResult;
import com.miduchina.wrd.po.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xd.tools.method.wyq.web.WyqWebMethodV1;
import com.xd.tools.pojo.IContentCommonNet;
import com.xd.tools.pojo.Params;
import com.xd.tools.view.IContentCommonNetView;
import com.miduchina.wrd.monitor.warning.constant.BusinessConstant;
import com.miduchina.wrd.monitor.warning.operatelog.model.OperateLogUserInfo;
import com.miduchina.wrd.monitor.warning.operatelog.model.OperateLogWarningInfo;
import com.miduchina.wrd.monitor.warning.service.WarningService;
import com.miduchina.wrd.monitor.warning.util.HTMLPARSE;

import static com.miduchina.wrd.common.redis.util.SysConfig.cfgMap;

/**
 * @ClassName: WarningController
 * @Description: TODO
 * @author: 许双龙
 * @date: 2017年5月15日 下午2:28:07
 */
@Controller
@RequestMapping("warningCenter")
public class WarningController {
	@Autowired
	private WarningService warningService;

	@RequestMapping("warningDetail")
	public String warningDetail(String reviewCode, Integer channelTag, String paltform, HttpServletRequest request,
			Model model) throws Exception {
		WarningTimerSearchResult wtsr = warningService.getWTSRByCode(reviewCode);
		// 过期
		if (wtsr == null) {
			return "wap/waplist0";
		}
		if (wtsr.getIsRead() == 0) {
			wtsr.setIsRead(1); // 标记为已读
			warningService.updateWarningTimerSearchResult(wtsr.getWarningReviewCode());
		}
		if (wtsr.getUserId() == 0) {
			Warning  warning = warningService.findWarningById(wtsr.getWarningId());
			wtsr.setUserId(warning.getUserId());
		}
		User user = warningService.findUserById(wtsr.getUserId());
		if(user != null && user.getStatus() == 1 
				&& (user.getStopEndTime() == null || user.getStopEndTime().getTime() < System.currentTimeMillis())){
			OperateLogUserInfo operateLogUserInfo = new OperateLogUserInfo();
			OperateLogUtils.userToUserInfo(user, operateLogUserInfo);
			if (channelTag != null && "www".equals(cfgMap.get("WEBID_COOKIE_NAME"))) {
				OperateLogWarningInfo warningInfo = new OperateLogWarningInfo();
				warningInfo.setUserId(user.getUserId());
				warningInfo.setWarningId(wtsr.getWarningId());
				warningInfo.setReviewCode(reviewCode);
				warningInfo.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(wtsr.getCreateTime()));
				try {
					OperateLogUtils.opreateLog(request, operateLogUserInfo, 10005, 1, warningInfo, null, channelTag);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			WarningSearchCondition wc = JSON.parseObject(wtsr.getWarningSearchConditionJson(), WarningSearchCondition.class);
			int mgNum = 0;
			String mgNumStr = "0";
			int fmgNum = 0;
			String fmgNumStr = "0";
			int total = 0;
			String totalStr = "0";
			total = wtsr.getIncreaseCount();
			mgNum = wtsr.getSensitiveCount();
			fmgNum = total - mgNum;
			
			mgNumStr = String.valueOf(mgNum);
			if (mgNum > BusinessConstant.MAX_WARNING_K) {
				mgNumStr = (mgNum / 1000) + "k";
			}
			model.addAttribute("mgNum", mgNum);
			model.addAttribute("mgNumStr", mgNumStr);
			fmgNumStr = String.valueOf(fmgNum);
			if (fmgNum > BusinessConstant.MAX_WARNING_K) {
				fmgNumStr = (fmgNum / 1000) + "k";
			}
			model.addAttribute("fmgNum", fmgNum);
			model.addAttribute("fmgNumStr", fmgNumStr);
			totalStr = String.valueOf(total);
			if (total > BusinessConstant.MAX_WARNING_K) {
				totalStr = (total / 1000) + "k";
			}
			model.addAttribute("total", total);
			model.addAttribute("totalStr", totalStr);
			model.addAttribute("reviewCode", reviewCode);
			model.addAttribute("channelTag", channelTag);
			if(paltform==null){
				paltform="";
			}
			model.addAttribute("paltform", paltform);
			model.addAttribute("wr", wtsr);
			model.addAttribute("userId", user.getUserId());
			if(StringUtils.isBlank(user.getNickname()) && StringUtils.isNotBlank(user.getMobile())){
				String mobile = user.getMobile();
				user.setNickname(mobile.substring(0,3)+"****"+user.getMobile().substring(mobile.length()-4,mobile.length()));
			}
			model.addAttribute("userName", user.getNickname());
			model.addAttribute("page", 1);
			model.addAttribute("similarCondition", wc.getSameContentMergeSwitch());
			model.addAttribute("titleHs", "");
		}
		return "wap/wap_list_pages";
	}
	
	@RequestMapping("getMoreWarningDetail")
	@ResponseBody
	IContentCommonNetView getMoreWarningDetail(String reviewCode, Integer tendencyCondition, Integer page,
			Integer pagesize) {
		WarningTimerSearchResult wtsr = warningService.getWTSRByCode(reviewCode);

		User user = warningService.findUserById(wtsr.getUserId());
		if(user == null || user.getStatus() != 1 
				|| (user.getStopEndTime() != null && user.getStopEndTime().getTime() > System.currentTimeMillis())){
			return null;
		}
		
		IContentCommonNetView ps = null;
		if (wtsr != null) {
			WarningSearchCondition warningSearchCondition = JSON.parseObject(wtsr.getWarningSearchConditionJson(),
					WarningSearchCondition.class);
			if (tendencyCondition != null && warningSearchCondition != null) {
				if (warningSearchCondition.getWarningContentTendency() == 0) {
					warningSearchCondition.setWarningContentTendency(tendencyCondition);
				} else if (warningSearchCondition.getWarningContentTendency() == 3 && tendencyCondition == 4) {
					ps = new IContentCommonNetView();
					ps.setPage(1);
					ps.setPagesize(10);
					ps.setTotalCount(0);
					return ps;
				} else if (warningSearchCondition.getWarningContentTendency() == 4 && tendencyCondition == 3) {
					ps = new IContentCommonNetView();
					ps.setPage(1);
					ps.setPagesize(10);
					ps.setTotalCount(0);
					return ps;
				}
			}
			ps = doSearch(wtsr, warningSearchCondition, page, pagesize);

		}
		return ps;
	}
	/**
	 * @param wtsr
	 */
	private IContentCommonNetView doSearch(WarningTimerSearchResult wtsr, WarningSearchCondition wc, Integer page,
			Integer pagesize) {
		IContentCommonNetView ps = null;
		try {
			// 内容属性
			int tendencyCondition = wc.getWarningContentTendency();
			if (tendencyCondition == 0) {
				tendencyCondition = 1;
			} else if (tendencyCondition == 3) {
				tendencyCondition = 2;
			} else if (tendencyCondition == 4) {
				tendencyCondition = 3;
			}
			// 定向省份
			List<String> provincesList = null;
			if (StringUtils.isNotBlank(wc.getProvince())) {
				provincesList = new ArrayList<>();
				provincesList.add(wc.getProvince());
			}
			// 排除来源
			List<String> excludeCaptureWebsiteNameList = null;
			if (StringUtils.isNotBlank(wc.getFilterRangWebsiteCodes())) {
				excludeCaptureWebsiteNameList = new ArrayList<>();
				excludeCaptureWebsiteNameList.add(wc.getFilterRangWebsiteCodes());
			}
			try {
				Params p = JSON.parseObject(wtsr.getParamsJson(), Params.class);
				p.setPage(page);
				if(StringUtils.isNotBlank(p.getOptions()) && p.getOptions().equals(Params.OPTIONS_SENSITIVE)){
					tendencyCondition = 2;
				}
				if (wtsr.getIncreaseCount() > 100 || StringUtils.isBlank(wtsr.getHbaseIds())) {
					p.setPagesize(pagesize);
					p.setOptions(String.valueOf(tendencyCondition));
					System.out.println("warn-Params" + JSON.toJSONString(p));
					ps = WyqWebMethodV1.searchListV1_001(String.valueOf(wc.getUserId()), p);
					System.out.println("doSearch userId=" + wc.getUserId() + ", code=" + ps.getCode()
							+ ", message" + ps.getMessage() + ", transactionId=" + ps.getTransactionId());
				} else {
					if (StringUtils.isNotBlank(wtsr.getHbaseIds())) {
						p.setIdList(Arrays.asList(wtsr.getHbaseIds().split(",")));
					}
					p.setEnableHighlightedContent(1);
					if(StringUtils.isNotBlank(p.getKeyword())) {
						//需要标红的词语
						p.setReferenceKeyword(p.getKeyword().replace("(", "").replace(")", "").replace("+", ",").replace("|", ","));
					}
					p.setPagesize(100);
					p.setOptions(String.valueOf(tendencyCondition));
					p.setSummarySwitch(1);//标红开关
					System.out.println("warn-Params" + JSON.toJSONString(p));
					ps = WyqWebMethodV1.searchDetailV1_001(String.valueOf(wc.getUserId()), p);
					System.out.println("doSearch userId=" + wc.getUserId() + ", code=" + ps.getCode()
							+ ", message" + ps.getMessage() + ", transactionId=" + ps.getTransactionId());
					if (Params.OPTIONS_NONSENSITIVE.equals(String.valueOf(tendencyCondition))) {
						List<IContentCommonNet> list = ps.getIContentCommonNetList();
						List<IContentCommonNet> notSensitiveList = new ArrayList<>();
						for (IContentCommonNet iContentCommonNet : list) {
							if (BusinessConstant.SEARCH_CONDITION_CONTENT_TENDENCY_NON_NEGATIVE
									.equals(iContentCommonNet.getCustomFlag1())) {
								notSensitiveList.add(iContentCommonNet);
							}
						}
						ps.setIContentCommonNetList(notSensitiveList);
						ps.setTotalCount(notSensitiveList.size());
					} else if (Params.OPTIONS_SENSITIVE.equals(String.valueOf(tendencyCondition))) {
						List<IContentCommonNet> list = ps.getIContentCommonNetList();
						List<IContentCommonNet> sensitiveList = new ArrayList<>();
						for (IContentCommonNet iContentCommonNet : list) {
							if (!BusinessConstant.SEARCH_CONDITION_CONTENT_TENDENCY_NON_NEGATIVE
									.equals(iContentCommonNet.getCustomFlag1())) {
								sensitiveList.add(iContentCommonNet);
							}
						}
						ps.setIContentCommonNetList(sensitiveList);
						ps.setTotalCount(sensitiveList.size());
					}
					ps.setMaxpage(1);
					ps.setPagesize(100);
					ps.setPage(page);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (ps != null) {
				if (page > 1 && CollectionUtils.isEmpty(ps.getIContentCommonNetList())) {
					// 如果没有记录，再回到首页，因为总页数不准确
					page = 1;
					Params p = JSON.parseObject(wtsr.getParamsJson(), Params.class);
					p.setPage(page);
					if (wtsr.getIncreaseCount() > 100 || StringUtils.isBlank(wtsr.getHbaseIds())) {
						p.setPagesize(pagesize);
						p.setOptions(String.valueOf(tendencyCondition));
						System.out.println("warn-Params" + JSON.toJSONString(p));
						ps = WyqWebMethodV1.searchListV1_001(String.valueOf(wc.getUserId()), p);
						System.out.println("doSearch userId=" + wc.getUserId() + ", code=" + ps.getCode()
								+ ", message" + ps.getMessage() + ", transactionId=" + ps.getTransactionId());
					} else {
						if (StringUtils.isNotBlank(wtsr.getHbaseIds())) {
							p.setIdList(Arrays.asList(wtsr.getHbaseIds().split(",")));
						}
						p.setPagesize(100);
						p.setOptions(String.valueOf(tendencyCondition));
						System.out.println("warn-Params" + JSON.toJSONString(p));
						ps = WyqWebMethodV1.searchDetailV1_001(String.valueOf(wc.getUserId()), p);
						System.out.println("doSearch userId=" + wc.getUserId() + ", code=" + ps.getCode()
								+ ", message" + ps.getMessage() + ", transactionId=" + ps.getTransactionId());
						if (Params.OPTIONS_NONSENSITIVE.equals(String.valueOf(tendencyCondition))) {
							List<IContentCommonNet> list = ps.getIContentCommonNetList();
							List<IContentCommonNet> notSensitiveList = new ArrayList<>();
							for (IContentCommonNet iContentCommonNet : list) {
								if (BusinessConstant.SEARCH_CONDITION_CONTENT_TENDENCY_NON_NEGATIVE
										.equals(iContentCommonNet.getCustomFlag1())) {
									notSensitiveList.add(iContentCommonNet);
								}
							}
							ps.setIContentCommonNetList(notSensitiveList);
							ps.setTotalCount(notSensitiveList.size());
						} else if (Params.OPTIONS_SENSITIVE.equals(String.valueOf(tendencyCondition))) {
							List<IContentCommonNet> list = ps.getIContentCommonNetList();
							List<IContentCommonNet> sensitiveList = new ArrayList<>();
							for (IContentCommonNet iContentCommonNet : list) {
								if (!BusinessConstant.SEARCH_CONDITION_CONTENT_TENDENCY_NON_NEGATIVE
										.equals(iContentCommonNet.getCustomFlag1())) {
									sensitiveList.add(iContentCommonNet);
								}
							}
							ps.setIContentCommonNetList(sensitiveList);
							ps.setTotalCount(sensitiveList.size());
						}
						ps.setMaxpage(1);
						ps.setPagesize(100);
						ps.setPage(page);
					}
				}
				// 去除content原微博信息
				if (!CollectionUtils.isEmpty(ps.getIContentCommonNetList())) {
					int titleLength = BusinessConstant.TITLE_LENGTH_DEFAULT;
					for (com.xd.tools.pojo.IContentCommonNet obj : ps.getIContentCommonNetList()) {
						String summary = null;
						if (com.xd.tools.pojo.IContentCommonNet.ORIGINTYPE_WB.equals(obj.getOriginType())) {
							summary = obj.getTitle();
						} else {
							summary = obj.getContent();
						}
						// 接口中设置的内容的长度为150
						int contentLength = BusinessConstant.WBTT_MAX_SIZE;
						if (summary != null) {
							summary = HTMLPARSE.filterSummary(summary);
							obj.setSummary(summary.trim());
						} else {
							obj.setSummary(obj.getTitle());
						}
						// 索引中微薄的标题也已有处理，已经标红
						String content = obj.getContent().length() > obj.getTitle().length() ? obj.getContent()
								: obj.getTitle();
						content = HTMLPARSE.parseChar(content);
						// content = HTMLPARSE.urlToLink(content);
						obj.setContent(content);
						// 标题处理
						// 索引中微薄没有标题，所以要重新获取标题
						if (obj.getTitle() == null || obj.getTitle().equals("")) {
							String temp = HTMLPARSE.getTitle(content);
							obj.setTitle(temp);
						}
						String title = obj.getTitle();
						content = HTMLPARSE.removeForwardContent(content);// 去除原微博信息的转发内容
						if (content.indexOf("【转发】") != -1) {
							content = content.replaceAll("【转发】", "");
						}
						if (obj.getForwarderId() != null && !obj.getForwarderId().equals("")) {
							String forwarderContent = obj.getForwarderContent();
							if (forwarderContent != null) {
								if (forwarderContent.indexOf("【转发】") != -1) {
									forwarderContent = forwarderContent.replaceAll("【转发】", "");
								}
								obj.setForwarderContent(forwarderContent);
							}
						}
						String hcontent = content;
						if (content.length() == 0) {
							content = "转发";
						} else {
							if (content.indexOf("<font color=\"red") >= 0) {
								String regex = "<font color=\"red\"\\s*>(.*?)</font>";
								Pattern pattern = Pattern.compile(regex);
								Matcher matcher = pattern.matcher(content);
								String[] hl = new String[64];
								int hll = 0;
								while (matcher.find()) {
									hl[hll] = matcher.group(1);
									hll = hll + 1;
								}
								hcontent = content.replaceAll("<font color=\"red\"\\s*>", "");
								hcontent = hcontent.replaceAll("</font>", "");
								if (hcontent.length() > contentLength) {
									content = hcontent.substring(0, contentLength)
											+ BusinessConstant.TITLE_TAIL_DEFAULT;
								}
								for (int j = 0; j < hll; j++) {
									content = HTMLPARSE.parseHighLight(content, hl[j]);
								}
							} else {
								if (hcontent.length() > contentLength) {
									content = hcontent.substring(0, contentLength)
											+ BusinessConstant.TITLE_TAIL_DEFAULT;
								}
							}
						}
						obj.setContent(content);
						title = HTMLPARSE.parseTitle(title);
						if (title.indexOf("<font color=\"red") >= 0) {
							String regex = "<font color=\"red\"\\s*>(.*?)</font>";
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher(title);
							String[] hl = new String[64];
							int hll = 0;
							while (matcher.find()) {
								hl[hll] = matcher.group(1);
								hll = hll + 1;
							}
							String sTitle = title.replaceAll("<font color=\"red\"\\s*>", "");
							sTitle = sTitle.replaceAll("</font>", "");
							if (sTitle.length() > titleLength) {
								int maxlength = titleLength;
								if (maxlength > 0) {
									sTitle = sTitle.substring(0, maxlength) + BusinessConstant.TITLE_TAIL_DEFAULT;
								}
							}
							title = sTitle;
							for (int j = 0; j < hll; j++) {
								title = HTMLPARSE.parseHighLight(title, hl[j]);
							}
						} else if (title.length() > titleLength) {
							int maxlength = titleLength;
							if (maxlength > 0) {
								title = title.substring(0, maxlength) + BusinessConstant.TITLE_TAIL_DEFAULT;
							}
						}
						obj.setTitle(title);
						summary = obj.getSummary();
						// * 摘要本身带高亮，单字高亮； 如果分析后再去套用，会把无关的单字也高亮；
						if (summary.indexOf("<font color=\"red") >= 0) {
							String regex = "<font color=\"red\"\\s*>(.*?)</font>";
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher(summary);
							String[] hl = new String[50];
							int hll = 0;
							while (matcher.find()) {
								hl[hll] = matcher.group(1);
								hll = hll + 1;
							}
							String sSummary = HTMLPARSE.removeHTMLTag(summary);
							if (sSummary.length() > contentLength) {
								sSummary = sSummary.substring(0, contentLength) + BusinessConstant.TITLE_TAIL_DEFAULT;
							}
							for (int j = 0; j < hll; j++) {
								summary = HTMLPARSE.parseHighLight(sSummary, hl[j]);
							}
						} else {
							summary = HTMLPARSE.removeHTMLTag(summary);
							if (summary.length() > contentLength) {
								summary = summary.substring(0, contentLength) + BusinessConstant.TITLE_TAIL_DEFAULT;
							}
						}
						obj.setSummary(summary);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ps != null) {
			ps.setRequestUrl("0000");
			ps.setSearchQuery("0000");
			ps.setServerInfo("0000");
		}
		return ps;
	}

	@RequestMapping("recordSign")
	@ResponseBody
	void recordSign(Integer userId, HttpServletRequest request) throws Exception {
		try {
			if (cfgMap.get("FILE_VIEW_PATH").indexOf("files.wyq.cn") != -1) {
				User user = warningService.findUserById(userId);
				OperateLogUserInfo operateLogUserInfo = new OperateLogUserInfo();
				userToUserInfo(user, operateLogUserInfo);
				OperateLogUtils.operatePageLog(request, operateLogUserInfo, 10006, "预警登录", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("recordDownApp")
	public void recordDownApp(Integer userId, HttpServletRequest request) throws Exception {
		try {
			if (cfgMap.get("FILE_VIEW_PATH").indexOf("files.wyq.cn") != -1) {
				User user = warningService.findUserById(userId);
				OperateLogUserInfo operateLogUserInfo = new OperateLogUserInfo();
				userToUserInfo(user, operateLogUserInfo);
				OperateLogUtils.operatePageLog(request, operateLogUserInfo, 10007, "预警下载APP", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void userToUserInfo(User user, OperateLogUserInfo operateLogUserInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		operateLogUserInfo.setUserId(user.getUserId());
		operateLogUserInfo.setUsername(user.getUsername());
		operateLogUserInfo.setNickname(user.getNickname());
		operateLogUserInfo.setEmail(user.getEmail());
		operateLogUserInfo.setMobile(user.getMobile());
		operateLogUserInfo.setPassword(user.getPassword());
		operateLogUserInfo.setUserHead(user.getUserHead());
		operateLogUserInfo.setAppUserStatus(user.getAppUserStatus());
		operateLogUserInfo.setUserChannel(user.getUserChannel());
		operateLogUserInfo.setUsreType(user.getUserType());
		operateLogUserInfo.setPlatform(user.getPlatform());
		operateLogUserInfo.setUserAnalysisValidCount(user.getUserAnalysisValidCount());
		operateLogUserInfo.setUserBriefValidCount(user.getUserBriefValidCount());
		operateLogUserInfo.setUserReportValidCount(user.getUserReportValidCount());
		operateLogUserInfo.setUserProductAnalysisValidCount(user.getUserProductAnalysisValidCount());
		operateLogUserInfo.setUserSingleWeiboAnalysisValidCount(user.getUserSingleWeiboAnalysisValidCount());
		operateLogUserInfo.setStatus(user.getStatus());
		operateLogUserInfo.setCreateTime(sdf.format(user.getCreateTime()));
		if (user.getProUserValidEndTime() != null) {
			operateLogUserInfo.setProUserValidEndTime(sdf.format(user.getProUserValidEndTime()));
		}
		if (user.getLastLoginTime() != null) {
			operateLogUserInfo.setLastLoginTime(sdf.format(user.getLastLoginTime()));
		}
		if (user.getUpdateTime() != null) {
			operateLogUserInfo.setUpdateTime(sdf.format(user.getUpdateTime()));
		}
	}
	public static void main(String[] args) throws Exception {
		/*int page = 1;
		String hbaseIds = "01512956122242478298001,91512957548201470473261,61512954485638521150853,315129555338651175900326,815129560785709291632226,715129542751393608134811,715129573583196651170326,215129561208346478132426,51512954868087576285581,51512956394107135126981,91512954054781865889355,715129561030797167262226,715129575224592548140426,41512954371266001706651,615129551785288278467611,815129553563603126662711,61512957459438460542974,71512955353669046328151,31512956122235457601801,41512954596616176409241,11512955995833640710224,915129559569914326272126,015129556952424907782126,915129555977614491970326,815129565768802617921026,615129565934088079301426,11512955565013493935001,615129548639383380391811,415129545967469948476610,415129543765563067580211,515129557656779241412126,115129575521435336874410,31512955999465513348411,31512955514845885752701,41512954997706400356815,71512956236859961789276,41512957552106180875691,515129574919376002330426,91512956559341415765961,41512955644926808789671,015129559340327534550226,11512955997413514801211,115129574285731921349711,815129552024006506322026,11512955027883878907746,21512956380554343080611,41512955648026348945865,21512954376624381504361,115129556688334936680326,81512955809030114201225,115129575536338828959310,11512957352883063130935,01512955168982410328021,515129546272678261671426,815129559155000325531426,515129556668072847550226,815129568635204035087311,21512955519254361835531,31512957553685086038981,015129550202629006470411,21512954096394210064771,415129541284663789472326,415129573593864383241326,615129572403581320652126,615129556364980233381511,015129555073428672122026,715129561868599323170126,31512955165685427842301,71512955168919227136991,815129549840608590840226,71512955644949775216251,51512956368867124258841,21512955641914552837121,815129545536402980912026,415129545045467245482226,015129562697425387581126,415129575536469419724711,915129574325717820173611,415129570080066588848411,21512954371334412160741,71512954484489485133851,41512955514726095701311,315129542490656428972126,21512956154714220640474,21512956763644578714834,41512954371366636134451,415129560025562713704711,215129573628745421582126,61512957161038026876752,615129543765584477722026,81512954729230894654221,31512954863975278036231,215129551302748259512126,015129547438225880071026,115129549660933245182226,815129554393509014242026";
		String str = "{\"comblineflg\":1,\"date\":-1,\"enableShowImgForList\":1,\"endTime\":\"2017-12-13 23:01:30\",\"filterKeyword\":\"你最期待的待播剧是|日湖南|日湖北|神女峰|巫峡大江|听声音辨脏腑疾病|女子超市买土鸡蛋|仙女山|乌江|石宝寨|巧叠精雕十二楼|江北北北北|非诚勿扰|民谣茉莉花|招聘全职|年糕#|尹庄桥|熙川|@俏江北|江北四大才子|大圩古镇|漓江北岸|@江北公子哥|#宁波兼职栏#|我参与了@娱乐全盘点|@辣手湘江北去|东北虎林园|松花江北|望霞峰|美人峰|巫山十二峰|宜宾江北|登龙桥|观音桥|梅江北街|临港|黑龙江北|九龙坡|珠江北岸|清川|湘江北去|墨江北畔|汉江北岸|瓯江北|重庆江北|重庆身边事|日湖州|江南江北|王俊凯|美食攻略|浙江北部|江北新区|段朵|三峡纤夫|兴华路|江北国际机场|南渡江|镇江北路|怒江北|晋江北岸|南京江北|屈原祠|长江北岸|地下大峡谷|龙江北岸|瑞云塔|棠湖西路|盛大开业|辛庄桥|劲爆试营业|江北白栀|布里亚郡|黄姚古镇|#宁波兼职|四顾屏|阳江北津|四月一日湖水|宁波租房在线|#团员招聘#|刘庄桥|网易重庆|南京|重庆|云南|苏州|#哈尔滨|夏至未至#|四平园|三國論壇|内江北|松原|金华|爱美高大厦|家庄桥|探城记|探店|吃货秀|详情戳图|三墩|上海曙光|宁波吃货|东阳|转发必戳|江北机场|租房#|转发抽奖|美食地图|微博电视|机构动向|游资封板|宜宾|滏漳|耿庄|张庄桥|上海曙腾|涪陵|@江北交巡警|马鞍山|连江北路|湖北省|威海\",\"indexDate\":-1,\"indexEndTime\":\"2017-12-13 23:01:30\",\"indexStartTime\":\"2017-12-13 22:00:29\",\"keyword\":\"((宁波|NBTV|海曙|镇海|慈溪|奉化)+(第一医院|一院|第九医院|九院))|江北|甬江|庄桥|洪塘|中马街|白沙街|文教街|孔浦|慈城|慈湖|北岸琴森|范江岸|双东坊|和塘雅苑|洪都花园|颐和名郡|铂悦府|亲亲家园|逸嘉新园|奥林80|宁沁家园|长岛花园|乐筑良品|铂翠湾|广厦怡庭|怡江新村|草马新路|奥丽赛|天沁家园|孙家丽园|维拉小镇|梅竹路|兴甬路|风华路|梅堰路|中官西路|中官新路|宁慈东路|康庄北路|康庄南路|冯塘线|骆观线|骆慈线|朱五线|裘市|云飞路|青林渡|洪都路|长兴东路|宁沁路|清湖路|湖西路|榭嘉路|大庆南路|宝庆路|桃渡路|庄浦线|草马路|生宝路|路林|南塘|慈江|青林湾|姚江|五婆湖|湾头|华辰大桥|宁波北站|三官堂|大通桥|压赛堰|倪家堰|日湖|保国寺|莼湖|邵余|海曙|古林|联丰|宁镇公路|宁镇路|国医街|开明街|江厦|段塘|集士港|集仕港|集市港|鄞江|章水|龙观乡|石碶|石契|南门街道|西门街道|望春街道|高桥镇|月湖街道|鼓楼街道|横街镇|洞桥|启运路|丽园南路|丽园北路|夏禹路|新星路|灵桥|狮子街|镇明路|乌含巷|尹江路|西湾路|白云街|泰丰街|鄞奉路|药行街|柳汀|广济街|仓基街|碶闸街|华楼街|泥沙街|永明路|澄波华庭|西成后孙|汇嘉新园|湖东路|百梁桥|横山岙|蕙江村|明州环境能源|裴岙|三李村|王家桥|宣裴|芸峰|蓉峰|悬慈|天一广场|天一商圈|栎社|翠柏路|中华纸业|亚洲纸业|亚洲纸管|亚洲纸箱|亚洲纸浆|亚洲浆纸|和义大道|王阿根|半浦|奉化|浙甬市场|东恩\",\"options\":\"2\",\"order\":\"Published\",\"origin\":\"2,7,5,12,10,3,6\",\"page\":1,\"pagesize\":20,\"provincesList\":[\"浙江\"],\"sort\":\"2\",\"startTime\":\"2017-12-13 15:01:30\"}";
		Params p = JSON.parseObject(str, Params.class);
		//p.setIdList(Arrays.asList(hbaseIds.split(",")));
		//p.setEnableHighlightedContent(1);
		p.setPage(page);
		p.setPagesize(10);
		p.setOptions(String.valueOf(2));
		IContentCommonNetView ps = null;
		if(true) {
			ps = WyqWarningMethodV1.searchWarningListV1_001(String.valueOf("194063"), p);
		}else {
			ps = WyqWebMethodV1.searchDetailV1_001(String.valueOf("194063"), p);
		}
		System.out.println(JSON.toJSONString(p));
		ps.setMaxpage(1);
		ps.setPagesize(10);
		ps.setPage(page);
		ps.setIContentCommonNetList(null);
		System.out.println(JSON.toJSONString(ps));*/
//		String json = "{\"date\":-1,\"enableShowImgForList\":1,\"endTime\":\"2018-05-24 23:40:41\",\"indexDate\":-1,\"indexEndTime\":\"2018-05-24 23:40:41\",\"indexStartTime\":\"2018-05-24 23:40:00\",\"keyword\":\"王俊凯\",\"options\":\"2\",\"order\":\"Published\",\"origin\":\"1\",\"page\":1,\"pagesize\":20,\"sort\":\"2\",\"startTime\":\"2018-04-24 23:40:41\"}";
//
//		Params params = JSONObject.parseObject(json, Params.class);
//		params.setStatField(Params.STATFIELD_CUSTOMFLAG1);
//		StatView view = WyqWebMethodV1.statV1_001("194063", params);
//		System.out.println(view.getStatList().size());
		
		String str = "(延禧攻略)+(得体夫妇|得体cp|利落夫妇|(乾隆+璎珞)|(傅恒+璎珞))";
		str = "((旺旺|旺仔|莎娃|邦德咖啡|大口爽|黑皮|贝比玛玛|浪味仙|那多利|辣人|小馒头|(mr+hot)|雪饼|仙贝|碎冰冰|qq糖|冻痴|(哎哟+(米面|海苔|礼包|米条)))+(变质|漏液|异物|回潮|胀包|发霉|沉淀|危机|业绩下滑|瓶颈|出路|不旺))|(旺旺+(蔡衍明|蔡旺家|ceo|高层|领导|总监|董事))|(旺旺不+年轻人)";
		System.out.println(str.replace("(", "").replace(")", "").replace("+", ",").replace("|", ","));
		
	}
}
