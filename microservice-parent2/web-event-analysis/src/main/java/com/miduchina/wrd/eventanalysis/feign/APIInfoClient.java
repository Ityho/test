package com.miduchina.wrd.eventanalysis.feign;

import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.bigdata.OperationAdminWbDto;
import com.miduchina.wrd.dto.home.HeatShareDto;
import com.miduchina.wrd.dto.log.LoginLog;
import com.miduchina.wrd.dto.pay.H5ActivityDto;
import com.miduchina.wrd.dto.pay.PayRecordDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.dto.user.UserExclusiveChannelDto;
import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.dto.user.UserRightsRechargeRecord;
import com.miduchina.wrd.po.analysis.ProductsAnalysis;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.analysis.ProductsAnalysisShare;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.po.ranking.OperationAdminHot;
import com.miduchina.wrd.po.ranking.OperationAdminHotContent;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;
import com.xd.tools.pojo.db.mysql.wyq.weiyuqing.H5SearchShare;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.07.30
 */

@FeignClient(value="api-info-monitor",path="/api/")
public interface APIInfoClient {

    @RequestMapping(value = "v3/cartRecord/queryCount",method = RequestMethod.POST)
    BaseDto queryCount(@RequestParam("userId") String userId);


    @RequestMapping(value = "v1/heatAnalysis/queryCountHeatReportByUserId",method = RequestMethod.POST)
    BaseDto getCountHeatReportByUserId(@RequestParam("userId") Integer userId);

//	/**
//	 * 获取大数据解读
//	 * @param
//	 * @return
//	 */
//	@RequestMapping(value = "home/queryCountHeatReportByUserId",method = RequestMethod.POST)
//	BaseDto getBigData();
    /**
     * 获取大数据解读
     * @param
     * @return
     */
    @RequestMapping(value = "v1/bigdata/findBigDataByNameTypePage",method = RequestMethod.POST)
    PageDto<OperationAdminWbDto> findBigDataByNameTypePage(@RequestParam("page")int page, @RequestParam("pagesize")int pagesize, @RequestParam("type")Integer type,@RequestParam("isPackagePrice")Integer isPackagePrice);

    @RequestMapping(value = "v1/bigdata/queryBigDataDetail",method = RequestMethod.POST)
    BaseDto<OperationAdminWbDto> queryBigDataDetail(@RequestParam("id")int id);


    /**
     * 查询用户购买的大数据报告
     * @return
     */
    @RequestMapping(value = "v1/bigdata/queryBuyedBigData",method = RequestMethod.POST)
    BaseDto queryBuyedBigData(@RequestParam("userId")int userId);
    /**
     * 查询用户购买的大数据报告
     * @return
     */
    @RequestMapping(value = "v1/bigdata/queryBuyedBigDataByUserId",method = RequestMethod.POST)
    PageDto queryBuyedBigDataByUserId(@RequestBody Map<String,Object> params);
    /**
     * 修改用户购买的大数据报告
     * @return
     */
    @RequestMapping(value = "v1/bigdata/deletReport",method = RequestMethod.POST)
    BaseDto deletReport(@RequestBody Map<String,Object> params);
    /**
     * 竞品分析总数
     * @return
     */
    @RequestMapping(value = "v3/competitive/queryProductCount",method = RequestMethod.POST)
    BaseDto<Integer> queryProductCount(@RequestParam("userId")int userId);

    /**
     * 查询
     * @return
     */
    @RequestMapping(value = "v1/heatAnalysis/findShareCode",method = RequestMethod.POST)
    BaseDto findShareCode(@RequestParam Map<String,Object> params);


    /**
     * 根据监测方案Id获取监测方案
     * @param keywordId
     * @return
     */
    @RequestMapping(value = "v1/keyword/quertyOneById",method = RequestMethod.POST)
    BaseDto<KeyWord> quertyOneById(@RequestParam("keywordId") String keywordId);


    @RequestMapping(value = "v1/keyword/quertyUserPlatform",method = RequestMethod.POST)
    BaseDto<LoginLog> quertyUserPlatform(@RequestParam("userId") Integer userId);

    /**
     * 根据用户Id获取监测方案
     * @param userId
     * @return
     */
    @RequestMapping(value = "v1/keyword/queryByUser",method = RequestMethod.POST)
    BaseDto<List<KeyWord>> quertyByUserId(@RequestParam("userId") String userId);


    @RequestMapping(value = "v1/keyword/queryByUserTime",method = RequestMethod.POST)
    BaseDto getKeyWordByUserTime(@RequestParam("userId") long userId);


    /**
     * 获取急需付费监测方案
     * @param userId
     * @return
     */
    @RequestMapping(value = "v1/keyword/getRenewKeywordList",method = RequestMethod.POST)
    BaseDto getRenewKeyWord(@RequestParam("userId") Integer userId,@RequestParam("expiredCondition") Integer expiredCondition);

    @RequestMapping(value = "v1/order/findOrderCountByProductPackageIds",method = RequestMethod.POST)
    BaseDto<Integer> findOrderCountByProductPackageIds(@RequestParam("userId")Integer userId, @RequestParam("package_type_report_ids")String package_type_report_ids);






    /**
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "v3/weiboAnalysis/queryWeiboAnalysisListByUserId",method = RequestMethod.POST)
    BaseDto queryWeiboAnalysisListByUserId(@RequestParam("userId") String userId);

    /**
     *
     * @param weiboId
     * @return
     */
    @RequestMapping(value = "v3/weiboAnalysis/delWeiboAnalysis",method = RequestMethod.POST)
    BaseDto delWeiboAnalysis(@RequestParam("weiboId") String weiboId);

    /**
     *
     * @param weiboId
     * @param weiboURL
     * @param userId
     * @return
     */
    @RequestMapping(value = "v3/weiboAnalysis/queryWeiboAnalysisList",method = RequestMethod.POST)
    BaseDto<WeiBoAnalysisTask> queryWeiboAnalysisList(@RequestParam("weiboId") int weiboId , @RequestParam("weiboURL") String weiboURL, @RequestParam("userId")int userId);

    /**
     * 取出最新的一条
     * @param userId
     * @return
     */
    @RequestMapping(value = "v3/weiboAnalysis/queryLatestWeibo",method = RequestMethod.POST)
    BaseDto<WeiBoAnalysisTask> queryLatestWeibo(@RequestParam("userId")Integer userId);

    /**
     * 保存微博分析
     * @param analysisTask
     * @return
     */
    @RequestMapping(value = "v3/weiboAnalysis/doSaveWeibo",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseDto doSaveWeibo(@RequestBody WeiBoAnalysisTask analysisTask);

    /**
     * 修改微分析
     * @param analysisTask
     * @return
     */
    @RequestMapping(value = "v3/weiboAnalysis/doModifyWeiboPayment",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseDto<Boolean> doModifyWeiboPayment(@RequestBody WeiBoAnalysisTask analysisTask);

    @RequestMapping(value = "v3/competitive/getPABList",method = RequestMethod.POST)
    BaseDto<List<ProductsAnalysisBrief>> getPABList(@RequestParam("userId")Integer userId);

    @RequestMapping(value = "v3/competitive/getPABId",method = RequestMethod.POST)
    BaseDto<List<ProductsAnalysis>> getPABId(@RequestParam("id")Integer id);

    @RequestMapping(value = "v3/competitive/findisShareCodePabInId",method = RequestMethod.POST)
    BaseDto<List<ProductsAnalysisShare>> findisShareCodePabInId(@RequestParam("pabIds")String pabIds);

    @RequestMapping(value = "v3/weiboAnalysis/queryWeiboExistsByUrl",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseDto<WeiBoAnalysisTask> queryWeiboExistsByUrl(@RequestBody WeiBoAnalysisTask tempWeibo);



    @RequestMapping(value = "v1/user/checkMobile",method = RequestMethod.POST)
    BaseDto checkMobile(@RequestParam("mobile") String mobile);

    @RequestMapping(value = "v1/user/queryOneUserChannel",method = RequestMethod.POST)
    BaseDto<UserExclusiveChannelDto> queryOneUserChannel(@RequestParam("code") String code);

    @RequestMapping(value = "v1/user/doSaveUserExclusiveChannelRelation",method = RequestMethod.POST)
    BaseDto doSaveUserExclusiveChannelRelation(@RequestBody UserExclusiveChannelRelation channelRelation);

    @RequestMapping(value = "v1/user/queryRegisterCreditByMobile",method = RequestMethod.POST)
    BaseDto queryRegisterCreditByMobile(@RequestParam("mobile") String mobile);

    @RequestMapping(value = "v1/user/updateUser",method = RequestMethod.POST)
    BaseDto updateUser(@RequestBody UserDto userDto);

    @RequestMapping(value = "v1/user/updateRewardRecord",method = RequestMethod.POST)
    BaseDto updateRewardRecord(@RequestParam("mobile") String mobile);

    @RequestMapping(value = "v1/user/addRightsRechargeRecord",method = RequestMethod.POST)
    BaseDto addRightsRechargeRecord(@RequestParam("userRightsRechargeRecord") UserRightsRechargeRecord userRightsRechargeRecord);

    @RequestMapping(value = "v1/user/queryOneUserThirdpartyAuth",method = RequestMethod.POST)
    BaseDto<UserThirdpartyAuthInfo> queryOneUserThirdpartyAuth(@RequestParam("userId") String userId);

    @RequestMapping(value = "v1/user/queryUserRightsRechargeRecord",method = RequestMethod.POST)
    BaseDto queryUserRightsRechargeRecord(@RequestBody UserRightsRechargeRecord userRightsRechargeRecord);

    @RequestMapping(value = "v1/user/findPayRecordByUserIdAndPackageId",method = RequestMethod.POST)
    BaseDto<Integer> findPayRecordByUserIdAndPackageId(@RequestParam("userId")Integer userId, @RequestParam("productPackageId")int productPackageId);

//    @RequestMapping(value = "v1/user/findLatestLoginLogByUserId",method = RequestMethod.POST)
//    BaseDto<LoginLog> findLatestLoginLogByUserId(Integer userId);


    @RequestMapping(value = "v1/user/findUserByMobile",method = RequestMethod.POST)
    BaseDto<UserDto>findUserByMobile(@RequestParam("mobile") String mobile);

    @RequestMapping(value = "v1/user/findWeiXinMaterialByOpenIdAndType",method = RequestMethod.POST)
    BaseDto findWeiXinMaterialByOpenIdAndType(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/user/saveWeiXinMaterial",method = RequestMethod.POST)
    BaseDto saveWeiXinMaterial(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/user/queryUserThirdpartyAuthInfoByUid",method = RequestMethod.POST)
    BaseDto queryUserThirdpartyAuthInfoByUid(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/user/findUserByThirdpartyccount",method = RequestMethod.POST)
    BaseDto findUserByThirdpartyccount(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/user/findAccessRecords",method = RequestMethod.POST)
    BaseDto findAccessRecords(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/user/deleteAccessRecords",method = RequestMethod.POST)
    BaseDto deleteAccessRecords(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/user/findH5ShortUrlByShortUrl",method = RequestMethod.POST)
    BaseDto findH5ShortUrlByShortUrl(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/user/saveH5ShortUrl",method = RequestMethod.POST)
    BaseDto saveH5ShortUrl(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/heatshare/findHs",method = RequestMethod.POST)
    BaseDto<HeatShareDto> findHs(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/userCenter/updateNotLoginOperateRecord",method = RequestMethod.POST)
    BaseDto updateNotLoginOperateRecord(@RequestBody Map<String,Object> params);

    @RequestMapping(value = "v1/userCenter/findNotLoginOperateRecordByIpAndUA",method = RequestMethod.POST)
    BaseDto findNotLoginOperateRecordByIpAndUA(@RequestBody Map<String,Object> params);

    /**
     * 根据ID查询支付记录
     * @param id
     * @return
     */
    @RequestMapping(value = "v1/order/findPayRecordById",method = RequestMethod.POST)
    BaseDto<PayRecordDto> findPayRecordById(@RequestParam("id") Integer id);


    @RequestMapping(value = "v1/order/findPayRecordByInnerTradeNo",method = RequestMethod.POST)
    BaseDto<PayRecordDto> findPayRecordByInnerTradeNo(@RequestParam("innerTradeNo")String innerTradeNo);


    @RequestMapping(value = "v1/user/findUserByUserId",method = RequestMethod.POST)
    BaseDto<UserDto> findUserByUserId(@RequestParam("id")Integer id);

    /**
     * 根据产品包编号查找活动
     * @param id
     * @return
     */

    @RequestMapping(value = "v1/order/find5ActivityByPackageId",method = RequestMethod.POST)
    BaseDto<H5ActivityDto> find5ActivityByPackageId(@RequestParam("id") Integer id);


    /**
     * 保存搜索Share
     * @param h5SearchShare
     * @return
     */
    @RequestMapping(value = "v1/search/saveH5SearchShare",method = RequestMethod.POST)
    BaseDto saveH5SearchShare(@RequestBody H5SearchShare h5SearchShare);

    /**
     * 查找搜索Share
     * @param shareCode
     * @return
     */
    @RequestMapping(value = "v1/search/findH5SearchShare",method = RequestMethod.POST)
    BaseDto<H5SearchShare> findH5SearchShare(@RequestParam("shareCode") String shareCode);




    /**
     * 更新
     * @param h5SearchShare
     * @return
     */
    @RequestMapping(value = "v1/search/updateH5SearchShare",method = RequestMethod.POST)
    BaseDto updateH5SearchShare(@RequestBody H5SearchShare h5SearchShare);


    /**
     * 保存购买的大数据报告
     * @param h5SearchShare
     * @return
     */
    @RequestMapping(value = "v1/search/saveBigReport",method = RequestMethod.POST)
    BaseDto saveBigReport(@RequestBody H5SearchShare h5SearchShare);

    /**
     * 查询已购买的大数据报告
     * @param userId
     * @return
     */
    @RequestMapping(value = "v3/order/findBigReport",method = RequestMethod.POST)
    BaseDto<H5ActivityDto> findBigReport(@RequestParam("userId") Integer userId);


    @RequestMapping(value = "v3/hotEventRanking/getHotList",method = RequestMethod.POST)
    PageDto<OperationAdminHot> getHotEventList(@RequestParam Map<String,Object> params);

    @RequestMapping(value = "v3/hotEventRanking/getList",method = RequestMethod.POST)
    PageDto<OperationAdminHotContent> getHotEventContentList(@RequestParam Map<String,Object> params);

}
