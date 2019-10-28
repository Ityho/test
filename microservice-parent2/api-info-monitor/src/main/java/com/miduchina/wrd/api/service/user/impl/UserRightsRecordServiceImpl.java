package com.miduchina.wrd.api.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.log.SignInLogMapper;
import com.miduchina.wrd.api.mapper.user.UserExtendInfoMapper;
import com.miduchina.wrd.api.mapper.user.UserRightRechargeRecordMapper;
import com.miduchina.wrd.api.mapper.user.UserRightsRecordMapper;
import com.miduchina.wrd.api.mapper.user.UserWdRightsRecordMapper;
import com.miduchina.wrd.api.service.infomonitor.KeyWordService;
import com.miduchina.wrd.api.service.order.OrderService;
import com.miduchina.wrd.api.service.order.ProductPackageService;
import com.miduchina.wrd.api.service.user.UserRightsRecordService;
import com.miduchina.wrd.api.service.user.UserService;
import com.miduchina.wrd.bo.user.PackgeManagentBO;
import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.bo.user.UserExtendInfoBO;
import com.miduchina.wrd.bo.user.VipInfoBO;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.user.UserRightsRechargeRecord;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.po.log.SignInLog;
import com.miduchina.wrd.po.order.PackgeManagent;
import com.miduchina.wrd.po.user.User;
import com.miduchina.wrd.po.user.UserExtendInfo;
import com.miduchina.wrd.po.user.UserRightsRecord;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @version v1.0.0
 * @ClassName: UserRightsRecordServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 1:31 PM
 */
@Service
public class UserRightsRecordServiceImpl implements UserRightsRecordService{

    @Resource
    private UserRightsRecordMapper userRightsRecordMapper;

    @Resource
    private UserRightRechargeRecordMapper userRightRechargeRecordMapper;

    @Resource
    private SignInLogMapper signInLogMapper;

    @Resource
    private UserExtendInfoMapper userExtendInfoMapper;

    @Resource
    private UserWdRightsRecordMapper userWdRightsRecordMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private KeyWordService keyWordService;

    @Autowired
    private ProductPackageService productPackageService;

    @Override
    public UserBO initUserRightsRecord(UserBO user) {
        if (user == null) {
            return null;
        }

        int analysisValidCount = 0; // 全网事件分析剩余次数
        int weiboAnalysisValidCount = 0; // 微博事件分析剩余次数
        int briefValidCount = 0; // 简报制作次数
        int reportValidCount = 0; // 自动报表次数
        int productAnalysisValidCount = 0; // 竞品分析次数
        int singleWeiboAnalysisValidCount = 0; // 单条微博分析转发条数
        int exportDataCount = 0; // 导出数据剩余条数
        int creditAmount = 0; // 微积分余额
        int heatReportCount = 0; // 热度对比剩余次数
        double sharePlanAmount = 0d; // 互动基金
        int commentsCount = 0; // 评论次数
        int allKeywordCount = 0; // 监测方案总数
        int noUseKeywordCount = 0; // 未使用监测方案总数
        int wrdNewsMonitorCount = 0; // 新闻稿监测次数
        List<UserRightsRecord> userRightsRecords = queryUserRightsRecords(new UserRightsRecord(user.getUserId()));
        if (CollectionUtils.isNotEmpty(userRightsRecords)) {
            for (UserRightsRecord userRightsRecord : userRightsRecords) {
                if (BusinessConstant.USER_RIGHTS_ITEM_AN == userRightsRecord.getUserRightsRecordItem()) {
                    analysisValidCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_BR == userRightsRecord.getUserRightsRecordItem()) {
                    briefValidCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_PA == userRightsRecord.getUserRightsRecordItem()) {
                    productAnalysisValidCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_RP == userRightsRecord.getUserRightsRecordItem()) {
                    reportValidCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_SWA == userRightsRecord.getUserRightsRecordItem()) {
                    singleWeiboAnalysisValidCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_WAN == userRightsRecord.getUserRightsRecordItem()) {
                    weiboAnalysisValidCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_CREDIT == userRightsRecord.getUserRightsRecordItem()) {
                    creditAmount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_ED == userRightsRecord.getUserRightsRecordItem()) {
                    exportDataCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_HR == userRightsRecord.getUserRightsRecordItem()) {
                    heatReportCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_SP == userRightsRecord.getUserRightsRecordItem()) {
                    sharePlanAmount += userRightsRecord.getUserRightsRecordCurrAmount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_COMMENTS == userRightsRecord.getUserRightsRecordItem()) {
                    commentsCount += userRightsRecord.getUserRightsRecordCurrCount();
                } else if (BusinessConstant.USER_RIGHTS_ITEM_NEWS_MONITOR == userRightsRecord.getUserRightsRecordItem()){
                    wrdNewsMonitorCount += userRightsRecord.getUserRightsRecordCurrCount();
                }
            }
        }

        //判断套餐类型
        user.setProLevel(userService.queryUserProLevel(user));

        List<KeyWord> keywords = keyWordService.queryValidKeywords(user.getUserId());
        if (CollectionUtils.isNotEmpty(keywords)) {
            allKeywordCount = keywords.size();
            for (KeyWord k : keywords) {
                if (k.getStatus() == BusinessConstant.KEYWORD_STATUS_NOUSE) {
                    noUseKeywordCount++;
                }
            }
        }

        user.setUserAnalysisValidCount(user.getUserAnalysisValidCount() + analysisValidCount);
        user.setUserWeiboAnalysisValidCount(user.getUserWeiboAnalysisValidCount() + weiboAnalysisValidCount);
        user.setUserBriefValidCount(user.getUserBriefValidCount() + briefValidCount);
        user.setUserReportValidCount(user.getUserReportValidCount() + reportValidCount);
        user.setUserProductAnalysisValidCount(user.getUserProductAnalysisValidCount() + productAnalysisValidCount);
        user.setUserSingleWeiboAnalysisValidCount(user.getUserSingleWeiboAnalysisValidCount() + singleWeiboAnalysisValidCount);
        user.setExportDataCount(user.getExportDataCount() + exportDataCount);
        user.setCreditAmount(user.getCreditAmount() + creditAmount);
        user.setHeatReportCount(user.getHeatReportCount() + heatReportCount);
        user.setSharePlanAmount(user.getSharePlanAmount() + sharePlanAmount);
        user.setCommentsCount(commentsCount);
        user.setAllKeywordCount(allKeywordCount);
        user.setNoUseKeywordCount(noUseKeywordCount);
        user.setWrdNewsMonitorCount(wrdNewsMonitorCount);
        PackgeManagent packgeManagent = productPackageService.queryCustomMade(user.getUserId());
        if (packgeManagent!=null){
            user.setPackgeManagent(BeanUtils.copyProperties(packgeManagent, PackgeManagentBO.class));
        }
        // 获取微积分累计充值金额，vip信息
        List<String> creditIdsList = new ArrayList<>();
        creditIdsList.addAll(Arrays.asList(BusinessConstant.PACKAGE_TYPE_CREDIT_IDS.split(",")));
        creditIdsList.addAll(Arrays.asList(BusinessConstant.PACKAGE_TYPE_CREDIT_NEW_IDS.split(",")));
        Double creditRechargeAmount = orderService.queryAllOrderFeeByPackageId(user.getUserId(), CommonUtils.strListToIntList(creditIdsList));
        user.setVipInfo(new VipInfoBO(creditRechargeAmount));

        //获取微豆数量
        int validWdSum = userWdRightsRecordMapper.selectUserValidWdSum(user.getUserId(), null);
        int willOverdueWdSum = userWdRightsRecordMapper.selectUserValidWdSum(user.getUserId(), Calendar.getInstance().get(Calendar.YEAR) + "-12-31 23:59:59");
        user.setValidWdSum(validWdSum);
        user.setWillOverdueWdSum(willOverdueWdSum);

        //最后一次签到日期
        SignInLog lastSignInLog = signInLogMapper.selectLastSignInLog(user.getUserId());
        if(lastSignInLog != null && lastSignInLog.getCreateTime() != null){
            user.setLastSignInDate(DateUtils.format(lastSignInLog.getCreateTime(), DateUtils.FORMAT_SHORT_TIME));
            Integer seriesDays = 0;
            if(lastSignInLog != null && lastSignInLog.getCreateTime() != null){
                Calendar calendar = Calendar.getInstance();
                if(calendar.get(Calendar.DAY_OF_MONTH) == 1){
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    if(lastSignInLog.getCreateTime().getTime() >= calendar.getTimeInMillis()){
                        seriesDays = 1;
                    }else{
                        seriesDays = 0;
                    }
                }else{
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    if(lastSignInLog.getCreateTime().getTime() >= calendar.getTimeInMillis()){
                        seriesDays = lastSignInLog.getSeriesDays();
                    }else{
                        seriesDays = 0;
                    }
                }
            }
            user.setSeriesSignInDays(seriesDays);
        }

        //获取用户扩展信息
        UserExtendInfo userExtendInfo = userExtendInfoMapper.selectUserExtendInfo(user.getUserId());
        user.setUserExtendInfo(BeanUtils.copyProperties(userExtendInfo, UserExtendInfoBO.class));
        return user;
    }

    /**
     * 刷新缓存对象
     *
     * @param userId
     * @param key
     */
    @Override
    public UserBO refreshSessionUser(int userId, String key) {
        if (userId > 0) {
            User user = userService.queryValidUserByUserId(userId);
            if (user != null) {
                String pattern = null;
                if (StringUtils.isBlank(key)) {
                    pattern = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN).append(String.valueOf(userId)).append("_*").toString());
                }

                // 初始化用户权益
                UserBO userBO = BeanUtils.copyProperties(user,UserBO.class);
                userBO = initUserRightsRecord(userBO);
                CommonUtils.refreshSessionUser(key, pattern, userBO);

                return userBO;
            }
        }

        return null;
    }

    @Override
    public List<UserRightsRecord> queryUserRightsRecords(UserRightsRecord userRightsRecord) {
        return userRightsRecordMapper.selectUserRightsRecords(userRightsRecord);
    }

    @Override
    public Boolean addRightsRechargeRecord(UserRightsRechargeRecord userRightsRechargeRecord) {
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("userId",userRightsRechargeRecord.getUserId());
        objectMap.put("relationId",userRightsRechargeRecord.getRelationId());
        objectMap.put("type",userRightsRechargeRecord.getType());
        objectMap.put("count",userRightsRechargeRecord.getCount());
        objectMap.put("currentCount",userRightsRechargeRecord.getCurrentCount());
        objectMap.put("item",userRightsRechargeRecord.getItem());
        objectMap.put("recordDesc",userRightsRechargeRecord.getRecordDesc());
        objectMap.put("platform",userRightsRechargeRecord.getPlatform());
        objectMap.put("status",userRightsRechargeRecord.getStatus());
        JSONObject jsonObject=new JSONObject(objectMap);
        return userRightRechargeRecordMapper.insertUserRightsRechargeRecordMapper(jsonObject);
    }

    @Override
    public List<UserRightsRechargeRecord> queryUserRightsRechargeRecord(JSONObject jsonObject) {
        return userRightRechargeRecordMapper.selectRightsRechargeRecordList(jsonObject);
    }
}
