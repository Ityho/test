package com.miduchina.wrd.eventanalysis.controller.weibospread;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.analysis.AnalysisDto;
import com.miduchina.wrd.dto.analysis.weiboanalysis.*;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.log.model.StatsView;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;
import com.miduchina.wrd.po.eventanalysis.weiboevent.IContentCommonNet;
import com.miduchina.wrd.po.eventanalysis.weiboevent.IContentCommonNets;
import com.miduchina.wrd.po.eventanalysis.weiboevent.Stat;
import com.miduchina.wrd.po.eventanalysis.weiboevent.Stats;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;
import com.miduchina.wrd.util.CommonFile;
import com.miduchina.wrd.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.miduchina.wrd.eventanalysis.controller.weibospread.SingleWeiboAnalysisController.str10ToStr62;

@Slf4j
@RestController
@RequestMapping(value = "/weiboAnalysis")
public class SingleWeiboAnalysisAjaxController extends BaseController {

    /**
     * 我的博文
     *
     * @throws Exception
     */
    @RequestMapping("/myWeibo")
    public WeiboUserStatusListRes myWeibo(HttpServletRequest request,String uid) throws Exception {
        fetchSessionAdmin(request);
        System.out.println("-----------------myWeibo start-------------------");
//		admin.setUserId(424);
        if (admin != null && uid != null && !"".equals(uid)) {
            BaseDto baseDto=apiInfoClient.queryOneUserThirdpartyAuth(String.valueOf(admin.getUserId()));
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                UserThirdpartyAuthInfo thirdpartyAuthInfo = (UserThirdpartyAuthInfo) baseDto.getData();
                if (thirdpartyAuthInfo != null && thirdpartyAuthInfo.getPlatformType() == BusinessConstant.THIRDPARTY_TYPE_SINA) {
                    if (uid.equals(thirdpartyAuthInfo.getThirdpartyUserId())) {
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("userTag", admin.getUserId());
                        params.put("userId", uid);

                        String result = sendSWAGet(IntraBusinessAPIConfig.getValue("myWeibo"), params);
                        System.out.println("------------myweibo Result-----------"+result);
                        if (result == null || "".equals(result.trim())) {
                            System.out.println("SingleWeiboAnalysisAction.myWeibo() : weibo status result is null!");
                        } else {
                            WeiboUserStatusListRes weiboUserStatusListRes = JSONObject.parseObject(result, WeiboUserStatusListRes.class);
                            if (weiboUserStatusListRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboUserStatusListRes.getCode())) {
                                System.out.println("SingleWeiboAnalysisAction.myWeibo() : weibo status return code = '" + weiboUserStatusListRes.getCode() + "', message = '" + weiboUserStatusListRes.getMessage() + "'!");
                            } else {
                                if (weiboUserStatusListRes.getStatusList() != null && !weiboUserStatusListRes.getStatusList().isEmpty()) {
                                    for (Weibo weibo : weiboUserStatusListRes.getStatusList()) {
                                        // 替换微博表情
                                        weibo.setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(weibo.getStatusText()));
                                        weibo.setUrl("http://weibo.com/" + weibo.getUser().getUserId() + "/" + id2Mid(weibo.getStatusId()));

                                        if (weibo.getRetweetedStatus() != null) {
                                            weibo.getRetweetedStatus().setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(weibo.getRetweetedStatus().getStatusText()));
                                            weibo.getRetweetedStatus().setUrl("http://weibo.com/" + weibo.getRetweetedStatus().getUser().getUserId() + "/" + id2Mid(weibo.getRetweetedStatus().getStatusId()));
                                        }
                                    }
                                }
                                return  weiboUserStatusListRes;
                            }
                        }
                    }
                }
            }


        }
        return null;
    }
    public static String id2Mid(String id)
    {
        return url2Mid(id);
    }
    public static String url2Mid(String url)
    {
        String mid = null;

        try
        {
            url = url.trim().substring(url.lastIndexOf("/") + 1);// 取得url地址后的10进制数??3491397862794657??

            String[] surl = new String[3];
            // 3491397862794657：第??????4】第二段??139786】第三段??794657??
            surl[2] = url.substring(url.length() - 7, url.length());
            surl[1] = url.substring(url.length() - 14, url.length() - 7);
            surl[0] = url.substring(0, url.length() - 14);
            // 若不是第????，则去掉左边0
            while (surl[2].startsWith("0"))
            {
                surl[2] = surl[2].substring(1);
            }
            while (surl[1].startsWith("0"))
            {
                surl[1] = surl[1].substring(1);
            }

            String surl0Str62 = str10ToStr62(Long.parseLong(surl[0]));
            String surl1Str62 = str10ToStr62(Long.parseLong(surl[1]));
            String surl2Str62 = str10ToStr62(Long.parseLong(surl[2]));
            // 若不是第????，则不足4位补0 -- add by ydliu 20130604
            while (surl1Str62.length() < 4)
            {
                surl1Str62 = "0" + surl1Str62;
            }
            while (surl2Str62.length() < 4)
            {
                surl2Str62 = "0" + surl2Str62;
            }

            // mid = str10ToStr62(Long.parseLong(surl[0])) + str10ToStr62(Long.parseLong(surl[1])) + str10ToStr62(Long.parseLong(surl[2]));
            mid = surl0Str62 + surl1Str62 + surl2Str62;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return mid;
    }
    /**
     * 发送GET请求
     *
     * @param url
     * @param params
     * @return
     */
    private static String sendSWAGet(String url, Map<String, Object> params) {
        System.out.println("------------sendSWAGet start------------");
        System.out.println("------------url-------------"+url);
        if (url != null && !"".equals(url) && params != null) {
            url = SysConfig.cfgMap.get("API_SINGLE_WEIBO_ANALYSIS_URL") + url;
            params.put("format", IntraBusinessAPIConfig.getValue("format"));
            params.put("platformTag", 2);

            return CommonUtils.sendGet(url, params);
        }

        return null;
    }
    private   <T> T getSolidData(String fileName, Class<T> className, String createTime, String taskTicket){
        T view = null;

        Flags.local_flag = local_flag;
        Flags.filePath = filePath;

        String filePath="";
        if(Flags.local_flag){
            filePath = Flags.filePath + "singleWeiboAnalysis/" +
                    createTime + "/" +
                    taskTicket ;
        }else{
            filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "singleWeiboAnalysis/" +
                    createTime + "/" +
                    taskTicket ;
        }
        //走任务 7天以外的
        view = Utils.getWeiboFileDataToObject(fileName, filePath, className);
        return view;
    }
    /**
     * 获取微博内容
     *
     * @throws Exception
     */
    @RequestMapping("/taskResultStatus")
    public WeiboTaskResultStatusRes taskResultStatus(Integer userId,Integer markType, String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView statsView= getSolidData("weiboDetail", WeiboExtendStatsView.class, createTime, taskTicket);
            if(statsView!=null && statsView.getIContentCommonNetList()!=null && statsView.getIContentCommonNetList().size()>0){
                WeiboTaskResultStatusRes weiboTaskResultStatusRes=new WeiboTaskResultStatusRes();
                UserDto userInfo = new UserDto();
                IContentCommonNet icc = statsView.getIContentCommonNetList().get(0);
                userInfo.setPageUrl(icc.getWebpageUrl());
                if (icc.getPraiseNum() != null) {
                    userInfo.setStatusAttitudesCount(icc.getPraiseNum().intValue());

                }
                userInfo.setStatusCommentsCount(icc.getComments().intValue());
                userInfo.setStatusCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(icc.getPublished()));
                if (icc.getViews() != null) {
                    userInfo.setStatusReadsCount(icc.getViews().intValue());
                }

                userInfo.setStatusRepostsCount(icc.getForwardNumber().intValue());
                userInfo.setStatusText(icc.getContent());
                userInfo.setUserFollowersCount(icc.getFansNumber().intValue());
                userInfo.setUserFriendsCount(icc.getFriendsCount());
                // userInfo.setUserId(icc.getUid());
                userInfo.setUserLogoUrl(icc.getProfileImageUrl());
                userInfo.setUserScreenName(icc.getAuthor());
                Weibo weibo=new Weibo();
                if(icc.getPictureList()!=null && icc.getPictureList().size()>0){
                    List<String> list1 = new ArrayList<>();
                    for (String str:icc.getPictureList()){
                        String str1 = str;
                        str1 = str1.replaceAll("wx1.sinaimg.cn", "tva1.sinaimg.com");
                        str1 = str1.replaceAll("wx2.sinaimg.cn", "tva2.sinaimg.com");
                        str1 = str1.replaceAll("wx3.sinaimg.cn", "tva3.sinaimg.com");
                        str1 = str1.replaceAll("wx4.sinaimg.cn", "tva4.sinaimg.com");
                        list1.add(str1);
                    }
                    icc.setPictureList(list1);
                    weibo.setStatusPicList(icc.getPictureList());
                }

                if (icc.getPraiseNum() != null) {
                    weibo.setStatusAttitudesCount(icc.getPraiseNum().intValue());
                }
                weibo.setStatusCommentsCount(icc.getComments().intValue());
                weibo.setStatusCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(icc.getPublished()));
                if (icc.getViews() != null) {
                    weibo.setStatusReadsCount(icc.getViews().intValue());
                }

                weibo.setStatusRepostsCount(icc.getForwardNumber().intValue());
                weibo.setStatusText(icc.getContent());
                // userInfo.setUserId(icc.getUid());
                weibo.setStatusReadsCount(icc.getViews().intValue());
                weibo.setUser(userInfo);
                weiboTaskResultStatusRes.setStatus(weibo);
                return  weiboTaskResultStatusRes;
            }
        }else{
            if(userId==null || userId==0){
                userId=1;
            }
            if (taskTicket != null && !"".equals(taskTicket) && userId > 0) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("taskTicket", taskTicket);
                params.put("userTag", userId);
                String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultStatus"), params);
                if (result == null || "".equals(result.trim())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultStatus() : weibo status result is null!");
                } else {
                    WeiboTaskResultStatusRes weiboTaskResultStatusRes = JSONObject.parseObject(result, WeiboTaskResultStatusRes.class);
                    if (weiboTaskResultStatusRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultStatusRes.getCode())) {
                        System.out.println("SingleWeiboAnalysisAction.taskResultStatus() : weibo status return code = '" + weiboTaskResultStatusRes.getCode() + "', message = '" + weiboTaskResultStatusRes.getMessage() + "'!");
                    } else {
                        if (weiboTaskResultStatusRes.getStatus() != null) {
                            if (weiboTaskResultStatusRes.getStatus().getStatusText() != null && !"".equals(weiboTaskResultStatusRes.getStatus().getStatusText())) {
                                weiboTaskResultStatusRes.getStatus().setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(weiboTaskResultStatusRes.getStatus().getStatusText()));
                            }

                            if (weiboTaskResultStatusRes.getStatus().getUser() != null) {
                                weiboTaskResultStatusRes.getStatus().getUser().setPageUrl("http://weibo.com/" + weiboTaskResultStatusRes.getStatus().getUser().getUserId());
                                weiboTaskResultStatusRes.getStatus().setUrl(weiboTaskResultStatusRes.getStatus().getUser().getPageUrl() + "/" + id2Mid(weiboTaskResultStatusRes.getStatus().getStatusId()));
                            }
                            List<String> sList=weiboTaskResultStatusRes.getStatus().getStatusPicList();
                            if(sList!=null && sList.size()>0){
                                List<String> list1 = new ArrayList<>();
                                for (String str:sList){
                                    String str1 = str;
                                    str1 = str1.replaceAll("wx1.sinaimg.cn", "tva1.sinaimg.com");
                                    str1 = str1.replaceAll("wx2.sinaimg.cn", "tva2.sinaimg.com");
                                    str1 = str1.replaceAll("wx3.sinaimg.cn", "tva3.sinaimg.com");
                                    str1 = str1.replaceAll("wx4.sinaimg.cn", "tva4.sinaimg.com");
                                    list1.add(str1);
                                }
                                weiboTaskResultStatusRes.getStatus().setStatusPicList(list1);
                            }

                            return weiboTaskResultStatusRes;
                        } else {

                            System.out.println("SingleWeiboAnalysisAction.analysis() : weibo status return null!");
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * 获取微博分析状态
     *
     * @throws Exception
     */
    @RequestMapping(value = "/taskStatus")
    public WeiboTaskStatusRes taskStatus(HttpServletRequest request,Integer markType,Integer userId,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userEncode", getUserEncodeNew(userId));
            params.put("platformTag", "wyq");
            params.put("tickets", taskTicket);
            String result=Utils.sendIntraBusinessAPIPOST(request, "swaQuerySolidifySchedule", params);
            System.out.println("taskStatus_result="+result);
//			String result = sendSWAGet(SingleWeiboTaskConfig.getValue("WeiboTaskStatus"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskStatus() : result is null!");
            } else {
                WeiboAnalysisRes weiboTaskStatusRes = JSONObject.parseObject(result, WeiboAnalysisRes.class);
                if (weiboTaskStatusRes != null && !BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(weiboTaskStatusRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskStatus() : return code = '" + weiboTaskStatusRes.getCode() + "', message = '" + weiboTaskStatusRes.getMessage() + "'!");
                } else {
                    WeiBoAnalysisTask fenxiWeibo = new WeiBoAnalysisTask();
                    WeiboTaskStatusRes wRes=new WeiboTaskStatusRes();
                    wRes.setNewVersion(true);
                    wRes.setCode(weiboTaskStatusRes.getCode());
                    wRes.setMessage(weiboTaskStatusRes.getMessage());
                    WeiboTaskStatusVO task=new WeiboTaskStatusVO();
                    if(weiboTaskStatusRes.getSolidifyList()!=null && weiboTaskStatusRes.getSolidifyList().size()>0){
                        AnalysisDto solidify=weiboTaskStatusRes.getSolidifyList().get(0);
                        int status=solidify.getAnalysisStatus();
                        if(status==5){
                            task.setAnalysisStatus("3");
                        }else {
                            if(status!=3){
                                task.setAnalysisStatus(String.valueOf(status));
                            }
                        }
                        task.setSchedulePercent(new Double(solidify.getSchedulePercent()*100).intValue());

                        //更新任务分析状态
                        fenxiWeibo.setAnalysisStatus(solidify.getAnalysisStatus());
                        fenxiWeibo.setTaskTicket(taskTicket);
                        fenxiWeibo.setUpdateTime(new Date());
                        apiInfoClient.doModifyWeiboPayment(fenxiWeibo);

                    }
                    wRes.setTask(task);
                    return wRes;
                }
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskStatus"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskStatus() : result is null!");
            } else {
                WeiboTaskStatusRes weiboTaskStatusRes = JSONObject.parseObject(result, WeiboTaskStatusRes.class);
                if (weiboTaskStatusRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskStatusRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskStatus() : return code = '" + weiboTaskStatusRes.getCode() + "', message = '" + weiboTaskStatusRes.getMessage() + "'!");
                } else {
                    //更新任务分析状态
                    WeiBoAnalysisTask fenxiWeibo = new WeiBoAnalysisTask();
                    fenxiWeibo.setAnalysisStatus(Integer.parseInt(weiboTaskStatusRes.getTask().getAnalysisStatus()));
                    fenxiWeibo.setTaskTicket(taskTicket);
                    fenxiWeibo.setUpdateTime(new Date());
                    fenxiWeibo.setUserId(Integer.valueOf(weiboTaskStatusRes.getTask().getUserTag()));
                    if (weiboTaskStatusRes.getTask().getSchedulePercent() == null && userId == 1){
                        weiboTaskStatusRes.getTask().setSchedulePercent(100);
                    }
                    apiInfoClient.doModifyWeiboPayment(fenxiWeibo);

                    weiboTaskStatusRes.setNewVersion(false);
                    return weiboTaskStatusRes;
                }
            }
        }
        return null;
    }
    private boolean getNewWeiboVersion(Integer markType){
        if(markType!=null && markType==1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 传播节点
     *
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultStar")
    public String taskResultStar(Integer markType,String createTime,String taskTicket,int tier,int userId) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboTaskResultStarRes weiboTaskResultStarRes=getSolidData("dandelionFigure", WeiboTaskResultStarRes.class, createTime, taskTicket);
            if (weiboTaskResultStarRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultStarRes.getCode())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultStar() : return code = '" + weiboTaskResultStarRes.getCode() + "', message = '" + weiboTaskResultStarRes.getMessage() + "'!");
            } else {
                if (weiboTaskResultStarRes.getLeafList() == null || weiboTaskResultStarRes.getLeafList().size() <= 0) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultStar() : return list is empty!");
                } else {
                    // 取出所有有下级节点的节点
                    List<Leaf> leafs = new ArrayList<Leaf>();
                    if (weiboTaskResultStarRes.getLeafList() != null && weiboTaskResultStarRes.getLeafList().size() > 0) {
                        for (int i = 0; i < weiboTaskResultStarRes.getLeafList().size(); i++) {
                            List<Leaf> list = weiboTaskResultStarRes.getLeafList().get(i);
                            if (list != null && list.size() > 0) {
                                for (int j = 0; j < list.size(); j++) {
                                    Leaf leaf = list.get(j);
                                    boolean isAdd = false;
                                    for (List<Leaf> leafs2 : weiboTaskResultStarRes.getLeafList()) {
                                        if (isAdd){
                                            break;
                                        }
                                        for (Leaf leaf2 : leafs2) {
                                            if (leaf2.getParentStatusId().equals(leaf.getStatusId())) {
                                                leafs.add(leaf);
                                                isAdd = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 节点数补足2000
                    int maxNodeCount = 2000;
                    if (leafs.size() < maxNodeCount) {
                        for (List<Leaf> leafs2 : weiboTaskResultStarRes.getLeafList()) {
                            for (Leaf leaf : leafs2) {
                                if (!leafs.contains(leaf)){
                                    leafs.add(leaf);
                                }
                            }
                        }
                    }
                    if (leafs.size() > maxNodeCount){
                        leafs = leafs.subList(0, maxNodeCount);
                    }
                    // 数据结构化
                    Map<String, Leaf> maps = new HashMap<String, Leaf>();
                    if (leafs != null && leafs.size() > 0) {
                        for (Leaf leaf : leafs) {
                            maps.put(leaf.getStatusId(), leaf);
                        }
                    }
                    WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes=null;
                    WeiboExtendStatsView iNetList=getSolidData("keyUsersPropagationPath", WeiboExtendStatsView.class, createTime, taskTicket);
                    if(iNetList!=null){
                        if(iNetList.getIContentCommonNetList()!=null && iNetList.getIContentCommonNetList().size()>0){
                            weiboTaskResultKeyUserRes=new WeiboTaskResultKeyUserRes();
                            weiboTaskResultKeyUserRes.setCode("0000");
                            List<WeiboUser> userList=new ArrayList<WeiboUser>();
                            List<IContentCommonNet> iCommonNetList=iNetList.getIContentCommonNetList();
                            for(IContentCommonNet iList : iCommonNetList){
                                WeiboUser user=new WeiboUser();
                                user.setPageUrl(iList.getWebpageUrl());
                                user.setUserLogoUrl(iList.getProfileImageUrl());
                                user.setUserId(iList.getUid());
                                user.setStatusId(iList.getTitleHs());
                                user.setStatusCreatedAt(Utils.getStringFromDate(iList.getIndexTime(), "yyyy-MM-dd HH:mm:ss"));
                                userList.add(user);
                            }
                            weiboTaskResultKeyUserRes.setUserList(userList);
                        }
                    }

                    List<Node> nodes = new ArrayList<Node>(); // 节点
                    List<Link> edges = new ArrayList<Link>(); // 节点关系
                    if (leafs != null && leafs.size() > 0) {
                        for (int i = 0; i < leafs.size(); i++) {
                            Leaf leaf = leafs.get(i);
                            if (i == 0 || maps.get(leaf.getParentStatusId()) != null) {
                                Node node = new Node();
                                node.setId(leaf.getStatusId());
                                node.setLabel(leaf.getUserScreenName());
                                int value = 10;

                                for (WeiboUser user : weiboTaskResultKeyUserRes.getUserList()) {
                                    if (user.getStatusId().equals(leaf.getStatusId())) {
                                        value = 45;
                                        break;
                                    }
                                }

                                if (i == 0){
                                    value = 45;
                                }

                                node.setValue(value);

                                nodes.add(node);

                                if (i > 0) {
                                    Link edge = new Link();
                                    edge.setSource(leaf.getParentStatusId());
                                    edge.setTarget(leaf.getStatusId());
                                    edge.setId("e" + leaf.getStatusId());
                                    edges.add(edge);
                                }
                            } else {
                                maps.remove(leaf.getStatusId());
                            }
                        }
                    }

                    StringBuilder gml = new StringBuilder("Creator \"zkq on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\"\r\ngraph\r\n[\r\n directed 1 \r\n");
                    for (Node node : nodes) {
                        gml.append(" node\r\n[\r\n id ").append(node.getId());
                        gml.append("\r\n label \"").append(node.getLabel()).append("\"\r\n");
                        gml.append(" value " + node.getValue() + " \r\n]\r\n");
                    }
                    for (Link link : edges) {
                        gml.append(" edge\r\n[\r\n source ").append(link.getSource());
                        gml.append("\r\n target ").append(link.getTarget()).append(" \r\n]\r\n");
                    }

                    // 文件中间目录
                    String[] tickets = taskTicket.split("-");
                    String path = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "/gexf/singleWeiboAnalysis/resultStar/" + createTime+"/"+taskTicket + "/";
                    String gmlFileName = taskTicket + ".gml";
                    String gexfFileName = taskTicket + ".gexf";
                    File file = new File(path + gexfFileName);
                    if (!file.exists()) {
                        CommonFile.createFile(path, gmlFileName, gml.toString(), "UTF-8");

                        CommonUtils.script(path + gmlFileName, path + gexfFileName);
                        CommonUtils.parserXml(path + gexfFileName);
                    }

                    String gexfPath = path.replace(SysConfig.cfgMap.get("FILE_UPLOAD_PATH"), "") + gexfFileName;

                   return gexfPath;
                }
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultStar"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultStar() : result is null!");
            } else {
                WeiboTaskResultStarRes weiboTaskResultStarRes = JSONObject.parseObject(result, WeiboTaskResultStarRes.class);
                if (weiboTaskResultStarRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultStarRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultStar() : return code = '" + weiboTaskResultStarRes.getCode() + "', message = '" + weiboTaskResultStarRes.getMessage() + "'!");
                } else {
                    if (weiboTaskResultStarRes.getLeafList() == null || weiboTaskResultStarRes.getLeafList().size() <= 0) {
                        System.out.println("SingleWeiboAnalysisAction.taskResultStar() : return list is empty!");
                    } else {
                        // 取出所有有下级节点的节点
                        List<Leaf> leafs = new ArrayList<Leaf>();
                        if (weiboTaskResultStarRes.getLeafList() != null && weiboTaskResultStarRes.getLeafList().size() > 0) {
                            for (int i = 0; i < weiboTaskResultStarRes.getLeafList().size(); i++) {
                                List<Leaf> list = weiboTaskResultStarRes.getLeafList().get(i);
                                if (list != null && list.size() > 0) {
                                    for (int j = 0; j < list.size(); j++) {
                                        Leaf leaf = list.get(j);
                                        boolean isAdd = false;
                                        for (List<Leaf> leafs2 : weiboTaskResultStarRes.getLeafList()) {
                                            if (isAdd){
                                                break;
                                            }
                                            for (Leaf leaf2 : leafs2) {
                                                if (leaf2.getParentStatusId().equals(leaf.getStatusId())) {
                                                    leafs.add(leaf);
                                                    isAdd = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // 节点数补足2000
                        int maxNodeCount = 2000;
                        if (leafs.size() < maxNodeCount) {
                            for (List<Leaf> leafs2 : weiboTaskResultStarRes.getLeafList()) {
                                for (Leaf leaf : leafs2) {
                                    if (!leafs.contains(leaf)){
                                        leafs.add(leaf);
                                    }
                                }
                            }
                        }
                        if (leafs.size() > maxNodeCount){
                            leafs = leafs.subList(0, maxNodeCount);
                        }


                        // 数据结构化
                        Map<String, Leaf> maps = new HashMap<String, Leaf>();
                        if (leafs != null && leafs.size() > 0) {
                            for (Leaf leaf : leafs) {
                                maps.put(leaf.getStatusId(), leaf);
                            }
                        }

                        WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes = taskResultKeyUserRoad(taskTicket, tier, userId);
                        List<Node> nodes = new ArrayList<Node>(); // 节点
                        List<Link> edges = new ArrayList<Link>(); // 节点关系
                        if (leafs != null && leafs.size() > 0) {
                            for (int i = 0; i < leafs.size(); i++) {
                                Leaf leaf = leafs.get(i);
                                if (i == 0 || maps.get(leaf.getParentStatusId()) != null) {
                                    Node node = new Node();
                                    node.setId(leaf.getStatusId());
                                    node.setLabel(leaf.getUserScreenName());
                                    int value = 10;

                                    for (WeiboUser user : weiboTaskResultKeyUserRes.getUserList()) {
                                        if (user.getStatusId().equals(leaf.getStatusId())) {
                                            value = 45;
                                            break;
                                        }
                                    }

                                    if (i == 0){
                                        value = 45;
                                    }

                                    node.setValue(value);

                                    nodes.add(node);

                                    if (i > 0) {
                                        Link edge = new Link();
                                        edge.setSource(leaf.getParentStatusId());
                                        edge.setTarget(leaf.getStatusId());
                                        edge.setId("e" + leaf.getStatusId());
                                        edges.add(edge);
                                    }
                                } else {
                                    maps.remove(leaf.getStatusId());
                                }
                            }
                        }

                        StringBuilder gml = new StringBuilder("Creator \"zkq on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\"\r\ngraph\r\n[\r\n directed 1 \r\n");
                        for (Node node : nodes) {
                            gml.append(" node\r\n[\r\n id ").append(node.getId());
                            gml.append("\r\n label \"").append(node.getLabel()).append("\"\r\n");
                            gml.append(" value " + node.getValue() + " \r\n]\r\n");
                        }
                        for (Link link : edges) {
                            gml.append(" edge\r\n[\r\n source ").append(link.getSource());
                            gml.append("\r\n target ").append(link.getTarget()).append(" \r\n]\r\n");
                        }

                        // 文件中间目录
                        String[] tickets = taskTicket.split("-");
                        String path="";
                        if(Flags.local_flag){
                            path = Flags.filePath + "/gexf/singleWeiboAnalysis/resultStar/" + tickets[1] + "/" + tickets[2] + "/" + tickets[0] + "/" + tier + "/";

                        }else{
                            path = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "/gexf/singleWeiboAnalysis/resultStar/" + tickets[1] + "/" + tickets[2] + "/" + tickets[0] + "/" + tier + "/";

                        }
                         String gmlFileName = tickets[0] + ".gml";
                        String gexfFileName = tickets[0] + ".gexf";
                        File file = new File(path + gexfFileName);
                        if (!file.exists()) {
                            CommonFile.createFile(path, gmlFileName, gml.toString(), "UTF-8");

                            CommonUtils.script(path + gmlFileName, path + gexfFileName);
                            CommonUtils.parserXml(path + gexfFileName);
                        }

                        String gexfPath = "";
                        if(Flags.local_flag){
                            gexfPath = path.replace(Flags.filePath, "") + gexfFileName;
                        }else {
                            gexfPath = path.replace(SysConfig.cfgMap.get("FILE_UPLOAD_PATH"), "") + gexfFileName;
                        }
                        return gexfPath;
                    }
                }
            }
        }
        return null;
    }
    /**
     * 关键用户传播路径
     *
     * @param taskTicket
     * @param tier
     * @param userId
     * @return
     * @throws Exception
     */
    public WeiboTaskResultKeyUserRes taskResultKeyUserRoad(String taskTicket, int tier, int userId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("taskTicket", taskTicket);
        params.put("tier", tier);
        params.put("userTag", userId);
        String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultKeyUserRoad"), params);
        if (result == null || "".equals(result.trim())) {
            System.out.println("SingleWeiboAnalysisAction.taskResultKeyUserRoad() : result is null!");
        } else {
            WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes = JSONObject.parseObject(result, WeiboTaskResultKeyUserRes.class);
            if (weiboTaskResultKeyUserRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultKeyUserRes.getCode())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultKeyUserRoad() : return code = '" + weiboTaskResultKeyUserRes.getCode() + "', message = '" + weiboTaskResultKeyUserRes.getMessage() + "'!");
            } else {
                if (weiboTaskResultKeyUserRes.getUserList() != null && !weiboTaskResultKeyUserRes.getUserList().isEmpty()) {
                    for (WeiboUser user : weiboTaskResultKeyUserRes.getUserList()) {
                        user.setPageUrl("http://weibo.com/" + user.getUserId());
                    }
                }

                return weiboTaskResultKeyUserRes;
            }
        }

        return null;
    }

    /**
     * 转发评论曲线
     *
     * 转发评论趋势图
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultLine")
    public Map<String, Object> taskResultLine(int markType,String createTime,String taskTicket,int tier,int userId) throws Exception {
        String result="";
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView extendStatsView = getSolidData("forwardCommentsTrendChart", WeiboExtendStatsView.class, createTime, taskTicket);
            if(extendStatsView!=null){
                if(extendStatsView.getExtendStats()!=null){
                    if(extendStatsView.getExtendStats().getStatsList()!=null && extendStatsView.getExtendStats().getStatsList().size()>0){
                        List<Stats> statsList =extendStatsView.getExtendStats().getStatsList();
                        TreeSet<String> legendList = new TreeSet<String>();

                        List<Integer> repostsList = new ArrayList<Integer>(); // 转发列表
                        List<Integer> commentsList = new ArrayList<Integer>(); // 评论列表

                        for(Stats stats : statsList){
                            if(stats.getName().trim().equals("转发")){
                                List<Stat> statList=stats.getStatList();
                                if(statList!=null && statList.size()>0){
                                    for (Stat coordinates : statList) {
                                        legendList.add(coordinates.getName());
                                        repostsList.add(coordinates.getNum().intValue());
                                    }
                                }

                            }else if(stats.getName().trim().equals("评论")){
                                if(stats.getStatList()!=null && stats.getStatList().size()>0){
                                    List<Stat> statList=stats.getStatList();
                                    for (Stat coordinates : statList) {
                                        commentsList.add(coordinates.getNum().intValue());
                                    }
                                }
                            }
                        }
                        Map<String, Object> rtnMap = new HashMap<String, Object>();
                        rtnMap.put("legend", legendList);
                        rtnMap.put("repostsList", repostsList);
                        rtnMap.put("commentsList", commentsList);

                        return rtnMap;
                    }
                }
            }

        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultLine"), params);
            WeiboTaskResultLineRes weiboTaskResultLineRes = JSONObject.parseObject(result, WeiboTaskResultLineRes.class);
            weiboTaskResultLineRes = JSONObject.parseObject(result, WeiboTaskResultLineRes.class);
            if (weiboTaskResultLineRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultLineRes.getCode())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultLine() : return code = '" + weiboTaskResultLineRes.getCode() + "', message = '" + weiboTaskResultLineRes.getMessage() + "'!");
            } else {
                // 数据结构化
                TreeSet<String> legendList = new TreeSet<String>();
                for (Coordinates coordinates : weiboTaskResultLineRes.getRepostsList()) {
                    legendList.add(coordinates.getX().toString());
                }

                List<Integer> repostsList = new ArrayList<Integer>(); // 转发列表
                List<Integer> commentsList = new ArrayList<Integer>(); // 评论列表

                for (String x : legendList) {
                    for (Coordinates coordinates : weiboTaskResultLineRes.getRepostsList()) {
                        if (x.equals(coordinates.getX().toString())) {
                            repostsList.add(Integer.parseInt(coordinates.getY().toString()));
                            break;
                        }
                    }
                    for (Coordinates coordinates : weiboTaskResultLineRes.getCommentsList()) {
                        if (x.equals(coordinates.getX().toString())) {
                            commentsList.add(Integer.parseInt(coordinates.getY().toString()));
                            break;
                        }
                    }
                }

                Map<String, Object> rtnMap = new HashMap<String, Object>();
                rtnMap.put("legend", legendList);
                rtnMap.put("repostsList", repostsList);
                rtnMap.put("commentsList", commentsList);

                return rtnMap;
            }
        }
        return null;
    }
    /**
     * 传播关键用户
     *
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultKeyUser")
    public WeiboTaskResultKeyUserRes taskResultKeyUser(int markType,String createTime,String taskTicket,int tier,int userId) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView statsView=getSolidData("weiboDisseminationKeyUsers", WeiboExtendStatsView.class, createTime, taskTicket);
            if(statsView!=null && statsView.getIContentCommonNetList()!=null && statsView.getIContentCommonNetList().size()>0){
                List<IContentCommonNet> iContentCommonNetList=statsView.getIContentCommonNetList();
                WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes=new WeiboTaskResultKeyUserRes();
                weiboTaskResultKeyUserRes.setCode("0000");
                try {
                    IContentCommonNet iContentCommonNet=iContentCommonNetList.get(0);
                    UserDto user=new UserDto();
                    user.setStatusCreatedAt(Utils.getStringFromDate(iContentCommonNet.getPublished().getTime(), ""));
                    user.setUserLogoUrl(iContentCommonNet.getProfileImageUrl());
                    user.setUserScreenName(iContentCommonNet.getAuthor());
                    user.setUserVerifiedType(iContentCommonNet.getVerifyType());
                    user.setStatusRepostsCount(iContentCommonNet.getForwardNumber().intValue());
                    user.setWeiboUrl(iContentCommonNet.getWebpageUrl());
                    user.setStatusCommentsCount(iContentCommonNet.getComments().intValue());
                    if(iContentCommonNet.getViews()!=null){
                        user.setStatusReadsCount(iContentCommonNet.getViews().intValue());
                    }
//					user.setUserFriendsCount(iContentCommonNet.getFriendsCount());
//					user.setStatusAttitudesCount(iContentCommonNet.getPraiseNum().intValue());
//					user.setUserStatusCount(iContentCommonNet.getWeiboNums());
                    user.setUserFollowersCount(iContentCommonNet.getFansNumber().intValue());
                    user.setStatusText(iContentCommonNet.getContent());
                    weiboTaskResultKeyUserRes.setUser(user);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return weiboTaskResultKeyUserRes;
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultKeyUser"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultKeyUser() : result is null!");
            } else {
                WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes = JSONObject.parseObject(result, WeiboTaskResultKeyUserRes.class);
                if (weiboTaskResultKeyUserRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultKeyUserRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultKeyUser() : return code = '" + weiboTaskResultKeyUserRes.getCode() + "', message = '" + weiboTaskResultKeyUserRes.getMessage() + "'!");
                } else {
                    if (weiboTaskResultKeyUserRes.getUser() != null && weiboTaskResultKeyUserRes.getUser().getStatusText() != null && !"".equals(weiboTaskResultKeyUserRes.getUser().getStatusText())) {
                        weiboTaskResultKeyUserRes.getUser().setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(weiboTaskResultKeyUserRes.getUser().getStatusText()));

                        weiboTaskResultKeyUserRes.getUser().setPageUrl("http://weibo.com/" + weiboTaskResultKeyUserRes.getUser().getUserId());
                    }

                    return weiboTaskResultKeyUserRes;
                }
            }
        }
        return null;
    }
    /**
     * 关键用户传播路径
     *
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultKeyUserRoad")
    public WeiboTaskResultKeyUserRes taskResultKeyUserRoad(int markType,String createTime,String taskTicket,int tier,int userId) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView statsView=getSolidData("keyUsersPropagationPath", WeiboExtendStatsView.class, createTime, taskTicket);
            if(statsView!=null && statsView.getIContentCommonNetList()!=null && statsView.getIContentCommonNetList().size()>0){
                List<IContentCommonNet> iContentCommonNetList=statsView.getIContentCommonNetList();
                WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes=new WeiboTaskResultKeyUserRes();
                weiboTaskResultKeyUserRes.setCode("0000");
                List<WeiboUser> userList=new ArrayList<WeiboUser>();
                try {
                    for(IContentCommonNet iContentCommonNet : iContentCommonNetList){
                        WeiboUser user=new WeiboUser();
                        user.setStatusCreatedAt(Utils.getStringFromDate(iContentCommonNet.getPublished().getTime(), ""));
                        user.setUserLogoUrl(iContentCommonNet.getProfileImageUrl());
                        user.setUserScreenName(iContentCommonNet.getAuthor());
                        user.setWeiboUrl(iContentCommonNet.getWebpageUrl());
                        user.setPageUrl(iContentCommonNet.getWebpageUrl());
                        userList.add(user);
                    }

                    weiboTaskResultKeyUserRes.setUserList(userList);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return weiboTaskResultKeyUserRes;
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultKeyUserRoad"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultKeyUserRoad() : result is null!");
            } else {
                WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes = JSONObject.parseObject(result, WeiboTaskResultKeyUserRes.class);
                if (weiboTaskResultKeyUserRes != null && !BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(weiboTaskResultKeyUserRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultKeyUserRoad() : return code = '" + weiboTaskResultKeyUserRes.getCode() + "', message = '" + weiboTaskResultKeyUserRes.getMessage() + "'!");
                } else {
                    if (weiboTaskResultKeyUserRes.getUserList() != null && !weiboTaskResultKeyUserRes.getUserList().isEmpty()) {
                        for (WeiboUser user : weiboTaskResultKeyUserRes.getUserList()) {
                            user.setPageUrl("http://weibo.com/" + user.getUserId());
                        }
                    }

                    return weiboTaskResultKeyUserRes ;
                }
            }
        }
        return null ;
    }
    /**
     * 引爆点
     *
     * @throws Exception
     */
    @RequestMapping("/taskResultKeyUserTop")
    public WeiboTaskResultKeyUserRes taskResultKeyUserTop(int userId,int tier,int markType,String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            ExtendStatsView extendStatsView = getSolidData("forwardWeiboContentTop", ExtendStatsView.class, createTime, taskTicket);
            WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes=new WeiboTaskResultKeyUserRes();
            weiboTaskResultKeyUserRes.setCode(extendStatsView.getCode());
            weiboTaskResultKeyUserRes.setMessage(extendStatsView.getMessage());
            List<WeiboUser> userList=new ArrayList<WeiboUser>();
            List<IContentCommonNet> iContentCommonNetList=extendStatsView.getIContentCommonNetList();
            for(IContentCommonNet iContentCommonNet : iContentCommonNetList){
                WeiboUser user=new WeiboUser();
                user.setStatusCreatedAt(Utils.getStringFromDate(iContentCommonNet.getPublished().getTime(), ""));
                user.setUserLogoUrl(iContentCommonNet.getProfileImageUrl());
                user.setUserScreenName(iContentCommonNet.getAuthor());
                user.setUserVerifiedType(iContentCommonNet.getVerifyType());
                user.setStatusRepostsCount(iContentCommonNet.getForwardNumber());
                user.setWeiboUrl(iContentCommonNet.getWebpageUrl());
                user.setStatusCommentsCount(iContentCommonNet.getComments());
                user.setStatusReadsCount(iContentCommonNet.getViews());
                user.setUserFriendsCount(iContentCommonNet.getFriendsCount());
                user.setStatusAttitudesCount(iContentCommonNet.getPraiseNum());
                user.setUserStatusCount(iContentCommonNet.getWeiboNums());
                user.setUserFollowersCount(iContentCommonNet.getFansNumber());
                user.setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(iContentCommonNet.getContent()));
                userList.add(user);
            }
            weiboTaskResultKeyUserRes.setUserList(userList);
            return weiboTaskResultKeyUserRes;
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultKeyUserTop"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultKeyUserTop() : result is null!");
            } else {
                WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes = JSONObject.parseObject(result, WeiboTaskResultKeyUserRes.class);
                if (weiboTaskResultKeyUserRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultKeyUserRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultKeyUserTop() : return code = '" + weiboTaskResultKeyUserRes.getCode() + "', message = '" + weiboTaskResultKeyUserRes.getMessage() + "'!");
                } else {
                    if (weiboTaskResultKeyUserRes.getUserList() != null && weiboTaskResultKeyUserRes.getUserList().size() > 0) {
                        for (WeiboUser user : weiboTaskResultKeyUserRes.getUserList()) {
                            user.setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(user.getStatusText()));

                            user.setPageUrl("http://weibo.com/" + user.getUserId());
                            user.setWeiboUrl(user.getPageUrl() + "/" + id2Mid(user.getStatusId()));
                        }
                    }
                    return weiboTaskResultKeyUserRes;
                }
            }
        }
        return null;
    }
    /**
     * 转发评论表情分析
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultFace")
    public Map<String, Object> taskResultFace(String taskTicket,int tier,int userId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("taskTicket", taskTicket);
        params.put("tier", tier);
        params.put("userTag", userId);
        String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultFace"), params);
        if (result == null || "".equals(result.trim())) {
            System.out.println("SingleWeiboAnalysisAction.taskResultFace() : result is null!");
        } else {
            WeiboTaskResultFaceRes weiboTaskResultFaceRes = JSONObject.parseObject(result, WeiboTaskResultFaceRes.class);
            if (weiboTaskResultFaceRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultFaceRes.getCode())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultFace() : return code = '" + weiboTaskResultFaceRes.getCode() + "', message = '" + weiboTaskResultFaceRes.getMessage() + "'!");
            } else {
                // 数据结构化
                Set<String> emoji = new HashSet<String>();
                List<FaceRes> faceList = new ArrayList<FaceRes>();
                int maxValue = 0;
                if ((weiboTaskResultFaceRes.getRepostsList() != null && weiboTaskResultFaceRes.getRepostsList().size() > 0) || (weiboTaskResultFaceRes.getCommentsList() != null && weiboTaskResultFaceRes.getCommentsList().size() > 0)) {
                    if (weiboTaskResultFaceRes.getRepostsList() != null && weiboTaskResultFaceRes.getRepostsList().size() > 0) {
                        int length = weiboTaskResultFaceRes.getRepostsList().size() > 10 ? 10 : weiboTaskResultFaceRes.getRepostsList().size();
                        for (int i = 1; i <= length; i++) {
                            Face face = weiboTaskResultFaceRes.getRepostsList().get(i - 1);
                            emoji.add(face.getFaceName());
                            if (face.getFaceCount() > maxValue){
                                maxValue = face.getFaceCount();
                            }
                        }
                    }
                    if (weiboTaskResultFaceRes.getCommentsList() != null && weiboTaskResultFaceRes.getCommentsList().size() > 0) {
                        int length = weiboTaskResultFaceRes.getCommentsList().size() > 10 ? 10 : weiboTaskResultFaceRes.getCommentsList().size();
                        for (int i = 1; i <= length; i++) {
                            Face face = weiboTaskResultFaceRes.getCommentsList().get(i - 1);
                            emoji.add(face.getFaceName());
                            if (face.getFaceCount() > maxValue){
                                maxValue = face.getFaceCount();
                            }
                        }
                    }

                    if (emoji.size() > 0) {
                        for (String str : emoji) {
                            List<Integer> values = new ArrayList<Integer>();
                            boolean isAdd = false;
                            for (Face face : weiboTaskResultFaceRes.getRepostsList()) {
                                if (str.equals(face.getFaceName())) {
                                    isAdd = true;
                                    values.add(face.getFaceCount());
                                    break;
                                }
                            }
                            if (!isAdd){
                                values.add(0);
                            }


                            isAdd = false;
                            for (Face face : weiboTaskResultFaceRes.getCommentsList()) {
                                if (str.equals(face.getFaceName())) {
                                    isAdd = true;
                                    values.add(face.getFaceCount());
                                    break;
                                }
                            }
                            if (!isAdd){
                                values.add(0);
                            }


                            faceList.add(new FaceRes(str, "", values));
                        }
                    }
                }

                if (faceList != null && faceList.size() > 0) {
                    for (FaceRes faceRes : faceList) {
                        faceRes.setFaceUrl(com.miduchina.wrd.other.util.CommonUtils.getEmoji(faceRes.getFaceName()));
                    }
                }

                Collections.sort(faceList, new Comparator<FaceRes>() {
                    @Override
                    public int compare(FaceRes o1, FaceRes o2) {
                        if (o1.getFaceCount().get(0) > o2.getFaceCount().get(0)){
                            return -1;
                        } else{
                            return 1;
                        }
                    }
                });

                Map<String, Object> rtnMap = new HashMap<String, Object>();
                rtnMap.put("maxValue", maxValue);
                rtnMap.put("faceList", faceList);

                return rtnMap;
            }
        }
        return null;
    }
    /**
     * 影响力TOP10
     *
     * @throws Exception
     */
    @RequestMapping("/taskResultVerifyUser")
    public Object taskResultVerifyUser(HttpServletRequest request,int markType,String createTime,String taskTicket,int tier,int userId) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView weiboExtendStatsView=getSolidData("influenceRanking", WeiboExtendStatsView.class, createTime, taskTicket);
            return  weiboExtendStatsView;
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultVerifyUser"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultVerifyUser() : result is null!");
            } else {
                WeiboTaskResultVerifyUserRes weiboTaskResultVerifyUserRes = JSONObject.parseObject(result, WeiboTaskResultVerifyUserRes.class);
                if (weiboTaskResultVerifyUserRes != null && !BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(weiboTaskResultVerifyUserRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultVerifyUser() : return code = '" + weiboTaskResultVerifyUserRes.getCode() + "', message = '" + weiboTaskResultVerifyUserRes.getMessage() + "'!");
                } else {
                    if (weiboTaskResultVerifyUserRes.getNormalUserList() != null && !weiboTaskResultVerifyUserRes.getNormalUserList().isEmpty()) {
                        for (UserDto user : weiboTaskResultVerifyUserRes.getNormalUserList()) {
                            user.setPageUrl("http://weibo.com/" + user.getUserId());
                        }
                    }
                    if (weiboTaskResultVerifyUserRes.getPersonalUserList() != null && !weiboTaskResultVerifyUserRes.getPersonalUserList().isEmpty()) {
                        for (UserDto user : weiboTaskResultVerifyUserRes.getPersonalUserList()) {
                            user.setPageUrl("http://weibo.com/" + user.getUserId());
                        }
                    }
                    if (weiboTaskResultVerifyUserRes.getOfficalUserList() != null && !weiboTaskResultVerifyUserRes.getOfficalUserList().isEmpty()) {
                        for (UserDto user : weiboTaskResultVerifyUserRes.getOfficalUserList()) {
                            user.setPageUrl("http://weibo.com/" + user.getUserId());
                        }
                    }

                    return weiboTaskResultVerifyUserRes;
                }
            }
        }
        return null;
    }
    /**
     * 转发评论地域分析
     * 转发者地域分析
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultLocation",produces = "text/plain;charset=GBK")
    public String taskResultLocation(int userId,int tier,int markType,String createTime,String taskTicket) throws Exception {

        //转发评论地域
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView weiboTaskResultLocation=getSolidData("regionalDistribution", WeiboExtendStatsView.class, createTime, taskTicket);
            if (weiboTaskResultLocation != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultLocation.getCode())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultLocation() : return code = '" + weiboTaskResultLocation.getCode() + "', message = '" + weiboTaskResultLocation.getMessage() + "'!");
            } else {
                // 数据结构化
                long repostsMax = 0;
                long commentsMax = 0;
                List<NameAndValue> repostsList = new ArrayList<NameAndValue>();
                List<NameAndValue> commentsList = new ArrayList<NameAndValue>();
                if (weiboTaskResultLocation.getStatsList() != null && weiboTaskResultLocation.getStatsList().size() > 0) {
                    for (Stats location : weiboTaskResultLocation.getStatsList()) {
                        log.info("---location.getName()="+location.getName()+"|转发");
                        System.out.println("location.getName()="+location.getName()+"|转发");
                        if(location.getName().trim().equals("转发")){
                            List<Stat> statList=location.getStatList();
                            if(statList!=null && statList.size()>0){
                                for(Stat stat : statList){
                                    if (stat.getNum() > repostsMax){
                                        repostsMax = stat.getNum();
                                    }
                                    repostsList.add(new NameAndValue(stat.getName(), stat.getNum()));
                                }

                            }
                        }else if(location.getName().trim().equals("评论")){
                            List<Stat> statList=location.getStatList();
                            if(statList!=null && statList.size()>0){
                                for(Stat stat : statList){
                                    if (stat.getNum() > commentsMax){
                                        commentsMax = stat.getNum();
                                    }
                                    commentsList.add(new NameAndValue(stat.getName(), stat.getNum()));
                                }

                            }
                        }

                    }
                }

                Map<String, Object> rtnMap = new HashMap<String, Object>();
                rtnMap.put("repostsMax", repostsMax);
                rtnMap.put("commentsMax", commentsMax);
                rtnMap.put("repostsList", repostsList);
                rtnMap.put("commentsList", commentsList);

                return JSONObject.toJSONString(rtnMap);
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultLocation"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultLocation() : result is null!");
            } else {
                WeiboTaskResultLocation weiboTaskResultLocation = JSONObject.parseObject(result, WeiboTaskResultLocation.class);
                if (weiboTaskResultLocation != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultLocation.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultLocation() : return code = '" + weiboTaskResultLocation.getCode() + "', message = '" + weiboTaskResultLocation.getMessage() + "'!");
                } else {
                    // 数据结构化
                    int repostsMax = 0;
                    int commentsMax = 0;
                    List<NameAndValue> repostsList = new ArrayList<NameAndValue>();
                    List<NameAndValue> commentsList = new ArrayList<NameAndValue>();
                    if (weiboTaskResultLocation.getRepostsList() != null && weiboTaskResultLocation.getRepostsList().size() > 0) {
                        for (Location location : weiboTaskResultLocation.getRepostsList()) {
                            if (location.getLocationCount() > repostsMax){
                                repostsMax = location.getLocationCount();
                            }
                            repostsList.add(new NameAndValue(location.getLocationName(), location.getLocationCount()));
                        }
                    }
                    if (weiboTaskResultLocation.getCommentsList() != null && weiboTaskResultLocation.getCommentsList().size() > 0) {
                        for (Location location : weiboTaskResultLocation.getCommentsList()) {
                            if (location.getLocationCount() > commentsMax){
                                commentsMax = location.getLocationCount();
                            }
                            commentsList.add(new NameAndValue(location.getLocationName(), location.getLocationCount()));
                        }
                    }

                    Map<String, Object> rtnMap = new HashMap<String, Object>();
                    rtnMap.put("repostsMax", repostsMax);
                    rtnMap.put("commentsMax", commentsMax);
                    rtnMap.put("repostsList", repostsList);
                    rtnMap.put("commentsList", commentsList);
                    return JSONObject.toJSONString(rtnMap);
                }
            }
        }
        return null;
    }
    /**
     * 转发评论性别分析
     *
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultGender")
    public Map<String, Object> taskResultGender(int userId,int tier,int markType,String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView weiboTaskResultGender= getSolidData("genderRatio", WeiboExtendStatsView.class, createTime, taskTicket);
            // 数据结构化
            List<NameAndValue> repostsList = new ArrayList<NameAndValue>();
            List<NameAndValue> commentsList = new ArrayList<NameAndValue>();
            if(weiboTaskResultGender.getStatsList()!=null && weiboTaskResultGender.getStatsList().size()>0){
                for(Stats stats : weiboTaskResultGender.getStatsList()){
                    if(stats.getName().trim().equals("转发")){
                        if(stats.getStatList()!=null && stats.getStatList().size()>0){
                            for(Stat stat :  stats.getStatList()){

                                if(stat.getName().trim().equals("m")){
                                    repostsList.add(new NameAndValue("男", stat.getNum()));
                                }else if(stat.getName().trim().equals("f")){
                                    repostsList.add(new NameAndValue("女", stat.getNum()));
                                }
                            }
                            Collections.sort(repostsList);
                        }
                    }else if(stats.getName().trim().equals("评论")){
                        if(stats.getStatList()!=null && stats.getStatList().size()>0){
                            for(Stat stat :  stats.getStatList()){
                                if(stat.getName().trim().equals("m")){
                                    commentsList.add(new NameAndValue("男", stat.getNum()));
                                }else if(stat.getName().trim().equals("f")){
                                    commentsList.add(new NameAndValue("女", stat.getNum()));
                                }
                            }
                            Collections.sort(commentsList);
                        }
                    }
                }
            }

            Map<String, Object> rtnMap = new HashMap<String, Object>();
            rtnMap.put("repostsList", repostsList);
            rtnMap.put("commentsList", commentsList);

            return rtnMap;

        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultGender"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.weiboTaskResultGender() : result is null!");
            } else {
                WeiboTaskResultGenderRes weiboTaskResultGender = JSONObject.parseObject(result, WeiboTaskResultGenderRes.class);
                if (weiboTaskResultGender != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultGender.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.weiboTaskResultGender() : return code = '" + weiboTaskResultGender.getCode() + "', message = '" + weiboTaskResultGender.getMessage() + "'!");
                } else {
                    // 数据结构化
                    List<NameAndValue> repostsList = new ArrayList<NameAndValue>();
                    List<NameAndValue> commentsList = new ArrayList<NameAndValue>();

                    if (weiboTaskResultGender.getReposts() != null) {
                        repostsList.add(new NameAndValue("男", weiboTaskResultGender.getReposts().getMaleCount()));
                        repostsList.add(new NameAndValue("女", weiboTaskResultGender.getReposts().getFemaleCount()));
                        Collections.sort(repostsList);
                    }

                    if (weiboTaskResultGender.getComments() != null) {
                        commentsList.add(new NameAndValue("男", weiboTaskResultGender.getComments().getMaleCount()));
                        commentsList.add(new NameAndValue("女", weiboTaskResultGender.getComments().getFemaleCount()));
                        Collections.sort(commentsList);
                    }

                    Map<String, Object> rtnMap = new HashMap<String, Object>();
                    rtnMap.put("repostsList", repostsList);
                    rtnMap.put("commentsList", commentsList);

                    return  rtnMap;
                }
            }
        }
        return null;
    }
    /**
     * 转发评论用户兴趣标签
     *
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultUserTag")
    public Map<String, Object> taskResultUserTag(int userId,int tier,int markType,String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView weiboTaskResultUserTagRes=getSolidData("interestLabel", WeiboExtendStatsView.class, createTime, taskTicket);
            // 数据结构化
            List<NameAndValue> repostsList = new ArrayList<NameAndValue>();
            List<NameAndValue> commentsList = new ArrayList<NameAndValue>();
            if(weiboTaskResultUserTagRes!=null && weiboTaskResultUserTagRes.getStatsList()!=null
                    && weiboTaskResultUserTagRes.getStatsList().size()>0){
                for(Stats stats : weiboTaskResultUserTagRes.getStatsList()){
                    if(stats.getName().trim().equals("转发")){
                        if(stats.getStatList()!=null && stats.getStatList().size()>0){
                            for(Stat stat : stats.getStatList()){
                                if (stat.getNum() > 0){
                                    repostsList.add(new NameAndValue(stat.getName(), stat.getNum() + 30));
                                }
                            }
                        }
                    }else if(stats.getName().trim().equals("评论")){
                        for(Stat stat : stats.getStatList()){
                            if (stat.getNum() > 0){
                                commentsList.add(new NameAndValue(stat.getName(), stat.getNum() + 30));
                            }
                        }
                    }
                }
            }

            Map<String, Object> rtnMap = new HashMap<String, Object>();
            rtnMap.put("repostsList", repostsList);
            rtnMap.put("commentsList", commentsList);

            return rtnMap;
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultUserTag"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultUserTag() : result is null!");
            } else {
                WeiboTaskResultUserTagRes weiboTaskResultUserTagRes = JSONObject.parseObject(result, WeiboTaskResultUserTagRes.class);
                if (weiboTaskResultUserTagRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultUserTagRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultUserTag() : return code = '" + weiboTaskResultUserTagRes.getCode() + "', message = '" + weiboTaskResultUserTagRes.getMessage() + "'!");
                } else {
                    // 数据结构化
                    List<NameAndValue> repostsList = new ArrayList<NameAndValue>();
                    List<NameAndValue> commentsList = new ArrayList<NameAndValue>();
                    if (weiboTaskResultUserTagRes.getRepostsList() != null && weiboTaskResultUserTagRes.getRepostsList().size() > 0) {
                        for (Tag tag : weiboTaskResultUserTagRes.getRepostsList()) {
                            if (tag.getTagCount() > 0){
                                repostsList.add(new NameAndValue(tag.getTagName(), tag.getTagCount() + 30));
                            }
                        }
                    }
                    if (weiboTaskResultUserTagRes.getCommentsList() != null && weiboTaskResultUserTagRes.getCommentsList().size() > 0) {
                        for (Tag tag : weiboTaskResultUserTagRes.getCommentsList()) {
                            if (tag.getTagCount() > 0){
                                commentsList.add(new NameAndValue(tag.getTagName(), tag.getTagCount() + 30));
                            }
                        }
                    }

                    Map<String, Object> rtnMap = new HashMap<String, Object>();
                    rtnMap.put("repostsList", repostsList);
                    rtnMap.put("commentsList", commentsList);

                    return rtnMap;
                }
            }
        }
        return null;
    }
    /**
     *
     * 敏感度分析
     *
     * @param
     * @since 2016年11月22日 下午2:09:56
     * @author  benyong
     * @return
     */
    @RequestMapping(value = "/taskResultSensitive")
    public WeiboTaskResultSensitiveRes taskResultSensitive(int userId,int tier,int markType, String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView weiboExtendStatsView=getSolidData("sensitivityAnalysis", WeiboExtendStatsView.class, createTime, taskTicket);
            if(weiboExtendStatsView !=null && weiboExtendStatsView.getCode().equals("0000")){
                WeiboTaskResultSensitiveRes weiboTaskResultSensitiveRes=new WeiboTaskResultSensitiveRes();
                weiboTaskResultSensitiveRes.setCode(weiboExtendStatsView.getCode());
                weiboTaskResultSensitiveRes.setMessage(weiboExtendStatsView.getMessage());

                WeiboTaskResultSensitiveVO sensitive=new WeiboTaskResultSensitiveVO();
                long insensitiveCount=0;
                long sensitiveCount=0;
                List<Stats> statsList=weiboExtendStatsView.getStatsList();
                if(statsList!=null && statsList.size()>0){
                    for(Stats stats : statsList){
                        if(stats.getStatList()!=null && stats.getStatList().size()>0){
                            for(Stat stat : stats.getStatList()){
                                if(stat.getName().trim().equals("非敏感")){
                                    insensitiveCount= insensitiveCount+stat.getNum();
                                }else if(stat.getName().trim().equals("敏感")){
                                    sensitiveCount=sensitiveCount+stat.getNum();
                                }
                            }
                        }
                    }
                }
                sensitive.setSensitiveCount(sensitiveCount);
                sensitive.setInsensitiveCount(insensitiveCount);
                weiboTaskResultSensitiveRes.setSensitive(sensitive);
                return weiboTaskResultSensitiveRes;
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultSensitive"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("WeiboController.taskResultSensitive() : result is null!");
            }

            WeiboTaskResultSensitiveRes weiboTaskResultSensitiveRes =JSONObject.parseObject(result, WeiboTaskResultSensitiveRes.class);
            if (weiboTaskResultSensitiveRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultSensitiveRes.getCode())) {
                System.out.println("WeiboController.taskResultSensitive() : return code = '" + weiboTaskResultSensitiveRes.getCode() + "', message = '" + weiboTaskResultSensitiveRes.getMessage() + "'!");
            }
            return weiboTaskResultSensitiveRes;
        }
        return null;
    }
    /**
     *
     * 转发评论热词
     *
     * @param
     * @since 2016年11月22日 下午2:11:13
     * @author  benyong
     * @return
     */
    @RequestMapping("/taskResultHotWords")
    public List<NameAndValue> taskResultHotWords(int userId,int tier,int markType, String createTime,String taskTicket) throws Exception{
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView weiboExtendStatsView=getSolidData("wordCloud", WeiboExtendStatsView.class, createTime, taskTicket);
            if(weiboExtendStatsView!=null){
                if(weiboExtendStatsView.getStatList()!=null && weiboExtendStatsView.getStatList().size()>0){
                    List<WeiboTaskResultHotWordsVO> sum = new ArrayList<WeiboTaskResultHotWordsVO>();
                    List<Stat> statList=weiboExtendStatsView.getStatList();
                    if(statList!=null && statList.size()>0){
                        for(Stat stat : statList){
                            WeiboTaskResultHotWordsVO wordsVO=new WeiboTaskResultHotWordsVO();
                            wordsVO.setWordsName(stat.getName());
                            wordsVO.setWordsCount(stat.getNum().intValue());
                            sum.add(wordsVO);
                        }
                    }
                    return parseHotWordsSumList(sum);
                }
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultHotWords"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("WeiboController.taskResultHotWords() : result is null!");
            }

            WeiboTaskResultHotWordsRes weiboTaskResultHotWordsRes = JSONObject.parseObject(result, WeiboTaskResultHotWordsRes.class);
            if (weiboTaskResultHotWordsRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultHotWordsRes.getCode())) {
                System.out.println("WeiboController.taskResultHotWords() : return code = '" + weiboTaskResultHotWordsRes.getCode() + "', message = '" + weiboTaskResultHotWordsRes.getMessage() + "'!");
            }

            List<WeiboTaskResultHotWordsVO> sum = new ArrayList<WeiboTaskResultHotWordsVO>();
            if (weiboTaskResultHotWordsRes.getRepostsList() != null && weiboTaskResultHotWordsRes.getRepostsList().size() > 0){
                sum.addAll(weiboTaskResultHotWordsRes.getRepostsList());
            }
            if (weiboTaskResultHotWordsRes.getCommentsList() != null && weiboTaskResultHotWordsRes.getCommentsList().size() > 0){
                sum.addAll(weiboTaskResultHotWordsRes.getCommentsList());
            }
            return parseHotWordsSumList(sum);
        }
        return null;
    }
    private List<NameAndValue> parseHotWordsSumList(List<WeiboTaskResultHotWordsVO> sum) {
        List<WeiboTaskResultHotWordsVO> tempList = null;
        if (sum != null && sum.size() > 0) {
            tempList = new ArrayList<WeiboTaskResultHotWordsVO>();

            Collections.sort(sum, new Comparator<WeiboTaskResultHotWordsVO>() {
                @Override
                public int compare(WeiboTaskResultHotWordsVO o1, WeiboTaskResultHotWordsVO o2) {
                    if (o1.getWordsCount() > o2.getWordsCount()){
                        return -1;
                    }  else{
                        return 1;
                    }

                }
            });

            for (int i = 0; i < 10; i++) {
                if ((i + 1) > sum.size()) {
                    tempList.add(null);
                    tempList.add(null);
                } else {
                    tempList.add(sum.get(i));
                    if ((i + 10 + 1) > sum.size()){
                        tempList.add(null);
                    }  else{
                        tempList.add(sum.get(i + 10));
                    }
                }
            }
        }

        List<NameAndValue> nameAndValues = null;
        if (tempList != null && tempList.size() > 0) {
            nameAndValues = new ArrayList<NameAndValue>();
            for (WeiboTaskResultHotWordsVO vo : tempList) {
                if(vo!=null){
                    nameAndValues.add(new NameAndValue(vo.getWordsName(), vo.getWordsCount()));
                }else{
                    nameAndValues.add(null);
                }

            }
        }

        return nameAndValues;
    }
    /**
     *
     * 转发热词详情
     *
     * @param
     * @since 2016年11月22日 下午2:14:18
     * @author  benyong
     * @return
     */
    @RequestMapping(value = "/taskResultHotWordsDetail")
    public WeiboTaskResultKeyUserRes taskResultHotWordsDetail(int userId,int tier,String keyWords,int markType,String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView statsView=getSolidData("forwardContent", WeiboExtendStatsView.class, createTime, taskTicket);
            if(statsView!=null){
                List<IContentCommonNet> iContentCommonNetList=statsView.getIContentCommonNetList();
                if(iContentCommonNetList!=null && iContentCommonNetList.size()>0){
                    WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes=new WeiboTaskResultKeyUserRes();
                    weiboTaskResultKeyUserRes.setCode("0000");
                    List<WeiboUser> userList=new ArrayList<WeiboUser>();
                    for(int i=0;i<20;i++){
                        IContentCommonNet iContentCommonNet=iContentCommonNetList.get(i);
                        WeiboUser user=new WeiboUser();
                        user.setStatusCreatedAt(Utils.getStringFromDate(iContentCommonNet.getPublished().getTime(), ""));
                        user.setUserLogoUrl(iContentCommonNet.getProfileImageUrl());
                        user.setUserScreenName(iContentCommonNet.getAuthor());
                        user.setUserVerifiedType(iContentCommonNet.getVerifyType());
                        user.setStatusRepostsCount(iContentCommonNet.getForwardNumber().intValue());
                        user.setWeiboUrl(iContentCommonNet.getWebpageUrl());
                        user.setStatusCommentsCount(iContentCommonNet.getComments().intValue());

                        user.setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(iContentCommonNet.getContent()));
                        if(!TextUtils.isEmpty(keyWords)){
                            if(iContentCommonNet.getContent().contains(keyWords)){
                                user.setStatusText(user.getStatusText().replaceAll(keyWords, "<font color=\"red\">" + keyWords + "</font>"));
                                userList.add(user);
                            }
                        }else{
                            userList.add(user);
                        }
                    }
                    weiboTaskResultKeyUserRes.setUserList(userList);
                    return weiboTaskResultKeyUserRes;
                }
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            params.put("searchKeyword", keyWords);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultHotWordsDetail"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("WeiboController.taskResultHotWordsDetail() : result is null!");
            }

            WeiboTaskResultKeyUserRes weiboTaskResultKeyUserRes = JSONObject.parseObject(result, WeiboTaskResultKeyUserRes.class);
            if (weiboTaskResultKeyUserRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultKeyUserRes.getCode())) {
                System.out.println("WeiboController.taskResultHotWordsDetail() : return code = '" + weiboTaskResultKeyUserRes.getCode() + "', message = '" + weiboTaskResultKeyUserRes.getMessage() + "'!");
            }

            if (weiboTaskResultKeyUserRes.getUserList() != null && weiboTaskResultKeyUserRes.getUserList().size() > 0) {
                for (WeiboUser user : weiboTaskResultKeyUserRes.getUserList()) {
                    user.setStatusText(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(user.getStatusText()));
                    user.setStatusText(user.getStatusText().replaceAll(keyWords, "<font color=\"red\">" + keyWords + "</font>"));
                }
            }
            return weiboTaskResultKeyUserRes;
        }
        return null;
    }
    /**
     * 转发评论粉丝分布
     *
     * @throws Exception
     */
    @RequestMapping(value = "/taskResultFans")
    public Map<String, Object> taskResultFans(int userId,int tier,int markType,String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView statsView=getSolidData("fansNumsRange", WeiboExtendStatsView.class, createTime, taskTicket);
            if(statsView!=null){
                // 数据结构化
                List<String> legend = new ArrayList<String>();
                legend.add("0-9");
                legend.add("10-49");
                legend.add("50-99");
                legend.add("100-199");
                legend.add("200-299");
                legend.add("300-399");
                legend.add("400-499");
                legend.add("500-999");
                legend.add("1000-1999");
                legend.add("2000-2999");
                legend.add("3000-3999");
                legend.add("4000-4999");
                legend.add("5000-9999");
                legend.add("10000-49999");
                legend.add("50000-99999");
                legend.add("100000-*");

                List<Integer> repostsList = new ArrayList<Integer>();
                List<Integer> commentsList = new ArrayList<Integer>();

                if (statsView.getStatsList() != null && statsView.getStatsList().size() > 0) {
                    for (String str : legend) {
                        for (Stats fan : statsView.getStatsList()) {
                            if(fan.getName().trim().equals("转发")){
                                if(fan.getStatList()!=null && fan.getStatList().size()>0){
                                    for(Stat stat : fan.getStatList()){
                                        if (str.trim().equals(stat.getName().trim())){
                                            repostsList.add(stat.getCount().intValue());
                                        }
                                    }
                                }
                            }else if(fan.getName().trim().equals("评论")) {
                                if(fan.getStatList()!=null && fan.getStatList().size()>0){
                                    for(Stat stat : fan.getStatList()){
                                        if (str.trim().equals(stat.getName().trim())){
                                            commentsList.add(stat.getCount().intValue());
                                        }
                                    }
                                }
                            }

                        }
                    }
                }


                Map<String, Object> rtnMap = new HashMap<String, Object>();
                rtnMap.put("legend", legend);
                rtnMap.put("repostsList", repostsList);
                rtnMap.put("commentsList", commentsList);

                return rtnMap;
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultFans"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultFans() : result is null!");
            } else {
                WeiboTaskResultFansRes weiboTaskResultFansRes = JSONObject.parseObject(result, WeiboTaskResultFansRes.class);
                if (weiboTaskResultFansRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultFansRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultFans() : return code = '" + weiboTaskResultFansRes.getCode() + "', message = '" + weiboTaskResultFansRes.getMessage() + "'!");
                } else {
                    // 数据结构化
                    List<String> legend = new ArrayList<String>();
                    legend.add("0-9");
                    legend.add("10-49");
                    legend.add("50-99");
                    legend.add("100-199");
                    legend.add("200-299");
                    legend.add("300-399");
                    legend.add("400-499");
                    legend.add("500-999");
                    legend.add("1000-1999");
                    legend.add("2000-2999");
                    legend.add("3000-3999");
                    legend.add("4000-4999");
                    legend.add("5000-9999");
                    legend.add("10000-49999");
                    legend.add("50000-99999");
                    legend.add("100000-*");

                    List<Integer> repostsList = new ArrayList<Integer>();
                    List<Integer> commentsList = new ArrayList<Integer>();

                    if (weiboTaskResultFansRes.getRepostsList() != null && weiboTaskResultFansRes.getRepostsList().size() > 0) {
                        for (String str : legend) {
                            for (Fan fan : weiboTaskResultFansRes.getRepostsList()) {
                                if (str.equals(fan.getFansName())){
                                    repostsList.add(fan.getFansCount());
                                }
                            }
                        }
                    }
                    if (weiboTaskResultFansRes.getCommentsList() != null && weiboTaskResultFansRes.getCommentsList().size() > 0) {
                        for (String str : legend) {
                            for (Fan fan : weiboTaskResultFansRes.getCommentsList()) {
                                if (str.equals(fan.getFansName())){
                                    commentsList.add(fan.getFansCount());
                                }
                            }
                        }
                    }

                    Map<String, Object> rtnMap = new HashMap<String, Object>();
                    rtnMap.put("legend", legend);
                    rtnMap.put("repostsList", repostsList);
                    rtnMap.put("commentsList", commentsList);

                    return rtnMap;
                }
            }
        }
        return null;
    }
    /**
     *
     * 发布设备来源
     *
     * @param
     * @since 2016年11月22日 下午2:20:42
     * @author  benyong
     * @return
     */
    @RequestMapping("/taskResultSource")
    public List<WeiboTaskResultSourceVO> taskResultSource(int userId,int tier,int markType,String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            StatView statView=getSolidData("releaseEquipment", StatView.class, createTime, taskTicket);
            if(statView!=null){
                if(statView.getStatList()!=null && statView.getStatList().size()>0){
                    List<Stat> sList=statView.getStatList();
                    List<WeiboTaskResultSourceVO> sourceList=new ArrayList<WeiboTaskResultSourceVO>();
                    for(Stat stat : sList){
                        WeiboTaskResultSourceVO wSourceVO=new WeiboTaskResultSourceVO();
                        wSourceVO.setSourceName(stat.getName());
                        wSourceVO.setSourceCount(new Long(Math.abs(stat.getNum())).intValue());
                        sourceList.add(wSourceVO);
                    }

                    List<WeiboTaskResultSourceVO> list = null;
                    WeiboTaskResultSourceVO other = null;
                    if (sourceList != null && sourceList.size() > 0) {
                        list = new ArrayList<WeiboTaskResultSourceVO>();
                        for (WeiboTaskResultSourceVO vo : sourceList) {
                            if ("其他".equals(vo.getSourceName())){
                                other = vo;
                            }  else{
                                list.add(vo);
                            }

                        }
                        if (other != null){
                            list.add(other);
                        }

                    }

                    return list;
                }
            }

        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultSource"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("WeiboController.taskResultSource() : result is null!");
            }

            WeiboTaskResultSourceRes weiboTaskResultSourceRes = JSONObject.parseObject(result, WeiboTaskResultSourceRes.class);
//            if (weiboTaskResultSourceRes != null && !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(weiboTaskResultSourceRes.getCode())) {
//                System.out.println("WeiboController.taskResultSource() : return code = '" + weiboTaskResultSourceRes.getCode() + "', message = '" + weiboTaskResultSourceRes.getMessage() + "'!");
//            }

            List<WeiboTaskResultSourceVO> list = null;
            WeiboTaskResultSourceVO other = null;
            if (weiboTaskResultSourceRes.getSourceList() != null && weiboTaskResultSourceRes.getSourceList().size() > 0) {
                list = new ArrayList<WeiboTaskResultSourceVO>();
                for (WeiboTaskResultSourceVO vo : weiboTaskResultSourceRes.getSourceList()) {
                    if ("其它".equals(vo.getSourceName())){
                        other = vo;
                    } else{
                        list.add(vo);
                    }
                }
                if (other != null){
                    list.add(other);
                }

            }

            return list;
        }
        return null;
    }
    /**
     *
     * 网友观点分析
     *
     * @param
     * @since 2016年11月22日 下午2:28:22
     * @author  benyong
     * @return
     */
    @RequestMapping(value ="/taskResultViewPoint" )
    public Map<String, Object> taskResultViewPoint(int userId,int tier,int markType,String createTime,String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            StatsView statsView=getSolidData(Flags.netFriendView, StatsView.class, createTime, taskTicket);
            if(statsView!=null){
                if(statsView.getStatsList()!=null && statsView.getStatsList().size()>0){
                    for(Stats stats : statsView.getStatsList()){
                        if(stats.getName().trim().equals("全部")){
                            if(stats.getStatList()!=null && stats.getStatList().size()>0){
                                WeiboTaskResultViewPointRes weiboTaskResultViewPointRes=new WeiboTaskResultViewPointRes();
                                weiboTaskResultViewPointRes.setCode("0000");
                                List<WeiboTaskResultViewPointVO> viewPointList=new ArrayList<WeiboTaskResultViewPointVO>();
                                List<WeiboTaskResultViewPointVO> contentList = new ArrayList<WeiboTaskResultViewPointVO>();
                                for(Stat  stat :  stats.getStatList()){
                                    WeiboTaskResultViewPointVO weiboTaskResultViewPointVO = new WeiboTaskResultViewPointVO();
                                    weiboTaskResultViewPointVO.setViewPointName(stat.getName());
                                    weiboTaskResultViewPointVO.setViewPointCount(stat.getNum().intValue());
                                    contentList.add(weiboTaskResultViewPointVO);

                                }
                                for(Stat  stat :  stats.getStatList()){
                                    WeiboTaskResultViewPointVO weiboTaskResultViewPointVO = new WeiboTaskResultViewPointVO();
                                    List<String> tempList = new ArrayList<String>();
                                    weiboTaskResultViewPointVO.setViewPointName(stat.getName());
                                    weiboTaskResultViewPointVO.setViewPointCount(stat.getNum().intValue());
                                    if(stat.getIContentCommonNetList()!=null && stat.getIContentCommonNetList().size()>0){
                                        IContentCommonNet iCommonNet=stat.getIContentCommonNetList().get(0);
                                        tempList.add("@"+iCommonNet.getAuthor()+":"+iCommonNet.getTitle());
                                    }
                                    weiboTaskResultViewPointVO.setViewPointList(tempList);
                                    viewPointList.add(weiboTaskResultViewPointVO);

                                }
                                weiboTaskResultViewPointRes.setViewPointList(viewPointList);
                                Map<String, Object> rtnMap = new HashMap<String, Object>();
                                rtnMap.put("list", weiboTaskResultViewPointRes.getViewPointList());
                                rtnMap.put("chart", contentList);

                                return rtnMap;
                            }
                        }
                    }
                }
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultViewPoint"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("WeiboController.taskResultViewPoint() : result is null!");
            }

            WeiboTaskResultViewPointRes weiboTaskResultViewPointRes = JSONObject.parseObject(result, WeiboTaskResultViewPointRes.class);
//            if (weiboTaskResultViewPointRes != null && !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(weiboTaskResultViewPointRes.getCode())) {
//                System.out.println("WeiboController.taskResultViewPoint() : return code = '" + weiboTaskResultViewPointRes.getCode() + "', message = '" + weiboTaskResultViewPointRes.getMessage() + "'!");
//            }

            if (weiboTaskResultViewPointRes.getViewPointList() != null && weiboTaskResultViewPointRes.getViewPointList().size() > 0) {
                List<WeiboTaskResultViewPointVO> contentList = new ArrayList<WeiboTaskResultViewPointVO>();
                for (WeiboTaskResultViewPointVO vo : weiboTaskResultViewPointRes.getViewPointList()) {
                    WeiboTaskResultViewPointVO weiboTaskResultViewPointVO = new WeiboTaskResultViewPointVO();
                    weiboTaskResultViewPointVO.setViewPointName(vo.getViewPointName());
                    weiboTaskResultViewPointVO.setViewPointCount(vo.getViewPointCount());
                    contentList.add(weiboTaskResultViewPointVO);
                }

                for (WeiboTaskResultViewPointVO vo : weiboTaskResultViewPointRes.getViewPointList()) {
                    vo.setViewPointName(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(vo.getViewPointName()));
                    if (vo.getViewPointList() != null && vo.getViewPointList().size() > 0) {
                        List<String> tempList = new ArrayList<String>();
                        for (String s : vo.getViewPointList()) {
                            tempList.add(com.miduchina.wrd.other.util.CommonUtils.replaceEmoji(s));
                        }
                        vo.setViewPointList(tempList);
                    }
                }

                Map<String, Object> rtnMap = new HashMap<String, Object>();
                rtnMap.put("list", weiboTaskResultViewPointRes.getViewPointList());
                rtnMap.put("chart", contentList);

                return  rtnMap;
            }
        }
        return null;
    }

    @RequestMapping(value ="/taskResultTier" )
    public WeiboTaskResultTierRes taskResultTier(int userId, int tier, int markType, String createTime, String taskTicket) throws Exception {
        if(getNewWeiboVersion(markType)){
            WeiboExtendStatsView iccView =getSolidData("weiboForwardLevel", WeiboExtendStatsView.class, createTime, taskTicket);
            if (iccView !=null && iccView.getExtendStats()!=null && iccView.getExtendStats().getStatsList() != null) {
                for (int i = 0; i < iccView.getExtendStats().getStatsList().size(); i++) {
                    List<Stat> statList = iccView.getExtendStats().getStatsList().get(i).getStatList();
                    if (statList != null && statList.size() > 0) {
                        List<WeiboTaskResultTierVO> transTierList=new ArrayList<WeiboTaskResultTierVO>();
                        for (int j = 0; j < statList.size(); j++) {
                            try {
                                WeiboTaskResultTierVO tierVO = new WeiboTaskResultTierVO();
                                Tier tierInfo = new Tier();
                                Stat stat = statList.get(j);
                                tierInfo.setRepostsUserCount(stat.getCount().intValue());
                                tierInfo.setRepostsCoverUserCount(stat.getSum().intValue());
                                if(stat.getPercent()!=null){
                                    tierInfo.setRepostsProportion(stat.getPercent().floatValue());
                                }
                                tierVO.setTierInfo(tierInfo);

                                if (!stat.getName().equals("全部")) {
                                    IContentCommonNets iContentCommonNetLists = iccView.getExtendStats().getiContentCommonNetLists().get(j);
                                    List<IContentCommonNet> iContentCommonNetList = iContentCommonNetLists.getiContentCommonNetList();
                                    if (iContentCommonNetList != null && iContentCommonNetList.size() > 0) {
                                        List<UserDto> list = new ArrayList<UserDto>();
                                        for (int k = 0; k < iContentCommonNetList.size(); k++) {
                                            UserDto userInfo = new UserDto();
                                            IContentCommonNet icc = iContentCommonNetList.get(k);
                                            userInfo.setPageUrl(icc.getWebpageUrl());
                                            if (icc.getPraiseNum() != null) {
                                                userInfo.setStatusAttitudesCount(icc.getPraiseNum().intValue());

                                            }
                                            userInfo.setStatusCommentsCount(icc.getComments().intValue());
                                            userInfo.setStatusCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(icc.getPublished()));
                                            if (icc.getViews() != null) {
                                                userInfo.setStatusReadsCount(icc.getViews().intValue());
                                            }

                                            userInfo.setStatusRepostsCount(icc.getForwardNumber().intValue());
                                            userInfo.setStatusText(icc.getContent());
                                            userInfo.setUserFollowersCount(icc.getFansNumber().intValue());
                                            userInfo.setUserFriendsCount(icc.getFriendsCount());
                                            // userInfo.setUserId(icc.getUid());
                                            userInfo.setUserLogoUrl(icc.getProfileImageUrl());
                                            userInfo.setUserScreenName(icc.getAuthor());
                                            // userInfo.setUserVerifiedType(icc.getVerifiedType());
                                            list.add(userInfo);
                                        }
                                        tierVO.setUserInfoList(list);

                                    }
                                    transTierList.add(tierVO);
                                }
                                if (stat.getName().equals("全部")) {
                                    transTierList.add(0, tierVO);
                                }
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        WeiboTaskResultTierRes weiboTaskResultTierRes2 = new WeiboTaskResultTierRes();
                        weiboTaskResultTierRes2.setTransTierList(transTierList);
                        return weiboTaskResultTierRes2;
                    }

                }
            }
        }else{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskTicket", taskTicket);
            params.put("tier", tier);
            params.put("userTag", userId);
            String result = sendSWAGet(IntraBusinessAPIConfig.getValue("weiboTaskResultTier"), params);
            if (result == null || "".equals(result.trim())) {
                System.out.println("SingleWeiboAnalysisAction.taskResultTier() : result is null!");
            } else {
                WeiboTaskResultTierRes weiboTaskResultTierRes = JSONObject.parseObject(result, WeiboTaskResultTierRes.class);
                if (weiboTaskResultTierRes != null && !CodeConstant.SUCCESS_CODE.equals(weiboTaskResultTierRes.getCode())) {
                    System.out.println("SingleWeiboAnalysisAction.taskResultTier() : return code = '" + weiboTaskResultTierRes.getCode() + "', message = '" + weiboTaskResultTierRes.getMessage() + "'!");
                } else {
                    float sumValue = 0F;
                    WeiboTaskResultTierRes weiboTaskResultTierRes2 = null;
                    if (weiboTaskResultTierRes.getTransTierList() != null && !weiboTaskResultTierRes.getTransTierList().isEmpty()) {
                        for (WeiboTaskResultTierVO weiboTaskResultTierVO : weiboTaskResultTierRes.getTransTierList()) {
                            if (weiboTaskResultTierVO.getUserInfoList() != null && !weiboTaskResultTierVO.getUserInfoList().isEmpty()) {
                                sumValue += weiboTaskResultTierVO.getTierInfo().getRepostsUserCount();
                                for (UserDto user : weiboTaskResultTierVO.getUserInfoList()) {
                                    user.setPageUrl("http://weibo.com/" + user.getUserId());
                                }
                            }
                        }
                        List<WeiboTaskResultTierVO> weiboTaskResultTierVOs = new ArrayList<WeiboTaskResultTierVO>();
                        for (WeiboTaskResultTierVO weiboTaskResultTierVO : weiboTaskResultTierRes.getTransTierList()) {
                            float proportion = weiboTaskResultTierVO.getTierInfo().getRepostsUserCount() / sumValue * 100;
                            weiboTaskResultTierVO.getTierInfo().setRepostsProportion(proportion);
                            if (weiboTaskResultTierVO.getTierInfo().getRepostsUserCount() > 0){
                                weiboTaskResultTierVOs.add(weiboTaskResultTierVO);
                            }
                        }
                        weiboTaskResultTierRes2 = new WeiboTaskResultTierRes();
                        weiboTaskResultTierRes2.setTransTierList(weiboTaskResultTierVOs);
                    }
                    return weiboTaskResultTierRes2;
                }
            }
        }

        WeiboTaskResultTierRes  weiboTaskResultTierRes2 = new WeiboTaskResultTierRes();
        return weiboTaskResultTierRes2;
    }



}
