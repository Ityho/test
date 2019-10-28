package com.miduchina.wrd.eventanalysis.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.eventanalysis.products.PackageListRes;
import com.miduchina.wrd.dto.eventanalysis.products.PackageVO;
import com.miduchina.wrd.dto.log.LoginLog;
import com.miduchina.wrd.dto.user.IContentCommonNetView;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.constant.Constants;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.log.model.UserRes;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.LoginUtils;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.po.eventanalysis.UserExclusiveChannelRes;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.util.CommonUtils;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.PagerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Controller
public abstract class  BaseController<T> {
    @Autowired
    protected APIInfoClient apiInfoClient;


    protected  String njxBasePath;
    protected  String staticResourcePathH5;
    protected  String staticResourcePath;
    protected  String webPath;
    protected int pagesize;// 页面输入的一页多少条数据
    protected int page;// 页面输入的页码
    protected Logger LOGGER= LoggerFactory.getLogger(this.getClass());
    protected UserDto admin;
    HttpServletRequest request;
    protected String platform;
    protected boolean showSharePlanAmountMenu; // 是否显示互动基金菜单
    protected Integer userPlatform;





    @Value("${filePath}")
    protected String filePath;

    @Value("${local_flag}")
    protected boolean local_flag;

    @Autowired
    private OrderClientService orderService;

    protected void initView(ModelAndView modelAndView){

//        njxBasePath= Utils.getRequest().getContextPath();
//        staticResourcePathH5 = SysConfig.cfgMap.get("FILE_VIEW_PATH")+"h5";
//        staticResourcePath = SysConfig.cfgMap.get("FILE_VIEW_PATH")+"web";
//        webPath= SysConfig.cfgMap.get("SYSTEM_WEB_URL");


//        modelAndView.addObject("njxBasePath",njxBasePath);
//        modelAndView.addObject("staticResourcePathH5",staticResourcePathH5);
//        modelAndView.addObject("staticResourcePath",staticResourcePath);
//        modelAndView.addObject("webPath",webPath);

    }

    protected void beforeAdd(Map map){

    }

    protected  void afterAdd(Map map){

    }

    /**
     * PackageVO转ProductPackage
     *
     * @param vo
     * @return
     */
    public static ProductPackage parseProductPackage(PackageVO vo) {
        if (vo != null) {
            ProductPackage productPackage = new ProductPackage();
            productPackage.setProductPackageId(vo.getProductPackageId());
            productPackage.setPackageName(vo.getPackageName());
            productPackage.setPackagePrice(vo.getPackagePrice().floatValue());
            productPackage.setPackageType(vo.getPackageType());
            productPackage.setKeywordServeDays(vo.getKeywordServeDays());
            productPackage.setKeywordCount(vo.getKeywordCount());
            productPackage.setAnalysisCount(vo.getAnalysisCount());
            productPackage.setWeiboAnalysisCount(vo.getWeiboAnalysisCount());
            productPackage.setBriefCount(vo.getBriefCount());
            productPackage.setProductAnalysisCount(vo.getProductAnalysisCount());
            productPackage.setSingleWeiboAnalysisCount(vo.getSingleWeiboAnalysisCount());
            productPackage.setExportDataCount(vo.getExportDataCount());
            productPackage.setCreditCount(vo.getCreditCount());
            productPackage.setGiftCreditCount(vo.getGiftCreditCount());
            productPackage.setHotReportCount(vo.getHotReportCount());
            productPackage.setHotReportServerDays(vo.getHotReportServerDays());
            productPackage.setCommentsAnalysisCount(vo.getCommentsCount());
            productPackage.setDescription(vo.getDescription());
            productPackage.setSortSeq(vo.getSortSeq());
            productPackage.setStatus(vo.getStatus());
            productPackage.setRenewPackageId(vo.getRenewPackageId());

            return productPackage;
        }
        return null;
    }
    /**
     * 加载产品包列表
     *
     * @param type
     *            产品包类型<br />
     *            0 - 全部<br />
     *            1 - 体验型产品包<br />
     *            2 - 监测方案产品包<br />
     *            3 - 全网事件分析产品包<br />
     *            4 - 微博事件分析产品包<br />
     *            5 - 简报制作产品包<br />
     *            6 - 竞品分析产品包<br />
     *            7 - 单条微博分析产品包<br />
     *            8 - 导出数据产品包<br />
     *            9 - 人工简报产品包<br />
     *            10 - 专业版产品包<br />
     *            12 - 微积分产品包<br />
     *            13 - 热度报告按次数产品包<br />
     *            14 - 热度报告按时长产品包<br />
     *            18 - 评论产品包
     *            19 - 京东万象产品包
     *            20 - 新微积分产品包
     * @throws Exception
     */

    protected void fetchProductlistV2(ModelAndView modelAndView,HttpServletRequest request,int type) throws Exception {


        log.info("fetchProductlist type = [" + type + "]");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        String rtnStr = Utils.sendWrdIntraBusinessAPIPOST(request, "packageList", params);
        log.info("fetchProductlist type = [" + type + "] rtnStr = [" + rtnStr + "]");
        System.out.println("type="+type+"|rtnStr="+rtnStr);
        if (!StringUtils.isBlank(rtnStr)) {
            PackageListRes packageListRes = JSONObject.parseObject(rtnStr, PackageListRes.class);
            if (packageListRes != null && CodeConstant.SUCCESS_CODE.equals(packageListRes.getCode()) && packageListRes.getPackageList() != null && packageListRes.getPackageList().size() > 0) {
                List<ProductPackage> packages = new ArrayList<ProductPackage>();
                for (PackageVO vo : packageListRes.getPackageList()) {
                    if (vo.getProductPackageId() != 33 && vo.getProductPackageId() != 35 && vo.getProductPackageId() != 36){
                        packages.add(parseProductPackage(vo));
                    }
                }
                switch (type) {
                    case 1:
                        Constants.PACKAGE_LIST_FREE = packages;
                        break;
                    case 2:
                        break;
                    case 64:
                        Constants.PACKAGE_LIST_KEYWORD.addAll(packages);
                        for(ProductPackage productPackage : packages){
                            if(productPackage.getProductPackageId()==64){
                                Constants.PACKAGE_LIST_KEYWORD_V_2.add(productPackage);
                            }
                        }
                        modelAndView.addObject("packageListKeyword",Constants.PACKAGE_LIST_KEYWORD);
                        break;
                    case 65:
                        for(ProductPackage productPackage : packages){
                            if(productPackage.getProductPackageId()==64){
                                Constants.PACKAGE_LIST_KEYWORD_V_2.add(productPackage);
                            }
                        }
                        modelAndView.addObject("packageListKeyword_v2",Constants.PACKAGE_LIST_KEYWORD_V_2);
                        break;
                    case 3:
                        Constants.PACKAGE_LIST_ANALYSIS = packages;
                        modelAndView.addObject("packageListAnalysis",Constants.PACKAGE_LIST_ANALYSIS);
                        break;
                    case 4:
                        Constants.PACKAGE_LIST_WEIBO_ANALYSIS = packages;
                        modelAndView.addObject("packageListWeiboAnalysis",Constants.PACKAGE_LIST_WEIBO_ANALYSIS);
                        break;
                    case 5:
                        Constants.PACKAGE_LIST_BRIEF = packages;
                        modelAndView.addObject("packageListBrief",Constants.PACKAGE_LIST_BRIEF);
                        break;
                    case 6:
                        Constants.PACKAGE_LIST_PRODUCT_ANALYSIS = packages;
                        modelAndView.addObject("packageListProductAnalysis",Constants.PACKAGE_LIST_PRODUCT_ANALYSIS);
                        break;
                    case 7:
                        Constants.PACKAGE_LIST_SINGLE_WEIBO_ANALYSIS = packages;
                        modelAndView.addObject("packageListSingleWeiboAnalysis",Constants.PACKAGE_LIST_SINGLE_WEIBO_ANALYSIS);
                        break;
                    case 8:
                        Constants.PACKAGE_LIST_EXPORT_DATA = packages;
                        modelAndView.addObject("packageListExportData",Constants.PACKAGE_LIST_EXPORT_DATA);
                        break;
                    case 9:
                        break;
                    case 10:
                        Constants.PACKAGE_LIST_PRO = packages;
                        modelAndView.addObject("packageListPro",Constants.PACKAGE_LIST_PRO);
                        break;
                    case 13:
                        Constants.PACKAGE_LIST_HOT_REPORT_COUNT = packages;
                        modelAndView.addObject("packageListHotReportCount",Constants.PACKAGE_LIST_HOT_REPORT_COUNT);
                        break;
                    case 14:
                        Constants.PACKAGE_LIST_HOT_REPORT_DAYS = packages;
                        modelAndView.addObject("packageListHotReportDays",Constants.PACKAGE_LIST_HOT_REPORT_DAYS);
                        break;
                    case 18:
                        Constants.PACKAGE_LIST_COMMENTS = packages;
                        modelAndView.addObject("packageListComments",Constants.PACKAGE_LIST_COMMENTS);
                        break;
                    case 20:
                        Constants.PACKAGE_LIST_CREDIT = packages;
                        modelAndView.addObject("packageListCredit",Constants.PACKAGE_LIST_CREDIT);
                        break;
                    default:
                        Constants.PACKAGE_LIST_ALL = packages;

                        Constants.PACKAGE_LIST_ANALYSIS = new ArrayList<>();
                        Constants.PACKAGE_LIST_WEIBO_ANALYSIS = new ArrayList<>();
                        Constants.PACKAGE_LIST_PRODUCT_ANALYSIS = new ArrayList<>();
                        Constants.PACKAGE_LIST_SINGLE_WEIBO_ANALYSIS = new ArrayList<>();
                        Constants.PACKAGE_LIST_CREDIT = new ArrayList<>();
                        Constants.PACKAGE_TYPE_BIGDATA_REPORT_IDS = new ArrayList<>();

                        List<ProductPackage> rtnProductPackages = new ArrayList<ProductPackage>();
                        for (ProductPackage productPackage : packages) {
                            if (BusinessConstant.PACKAGE_TYPE_ANALYSIS_IDS.equals(String.valueOf(productPackage.getRenewPackageId()))) {
                                Constants.PACKAGE_LIST_ANALYSIS.add(productPackage);
                            }
                            if (BusinessConstant.PACKAGE_TYPE_WEIBO_ANALYSIS_IDS.equals(String.valueOf(productPackage.getRenewPackageId()))) {
                                Constants.PACKAGE_LIST_WEIBO_ANALYSIS.add(productPackage);
                            }
                            if (BusinessConstant.PACKAGE_TYPE_PRODUCT_ANALYSIS_IDS.equals(String.valueOf(productPackage.getRenewPackageId()))) {
                                Constants.PACKAGE_LIST_PRODUCT_ANALYSIS.add(productPackage);
                            }
                            if (BusinessConstant.PACKAGE_TYPE_SINGLE_WEIBO_ANALYSIS_IDS.equals(String.valueOf(productPackage.getRenewPackageId()))) {
                                Constants.PACKAGE_LIST_SINGLE_WEIBO_ANALYSIS.add(productPackage);
                            }
                            for (String id : BusinessConstant.PACKAGE_TYPE_CREDIT_NEW_IDS.split(",")) {
                                if (productPackage.getProductPackageId() == Integer.parseInt(id)) {
                                    Constants.PACKAGE_LIST_CREDIT.add(productPackage);
                                }
                            }
                            for (String id : BusinessConstant.PACKAGE_TYPE_BIGDATA_REPORT_IDS.split(",")) {
                                if (productPackage.getProductPackageId() == Integer.parseInt(id)) {
                                    Constants.PACKAGE_TYPE_BIGDATA_REPORT_IDS.add(productPackage);
                                }
                            }

                            modelAndView.addObject("packageListAnalysis",Constants.PACKAGE_LIST_ANALYSIS);
                            modelAndView.addObject("packageListWeiboAnalysis",Constants.PACKAGE_LIST_WEIBO_ANALYSIS);
                            modelAndView.addObject("packageListSingleWeiboAnalysis",Constants.PACKAGE_LIST_SINGLE_WEIBO_ANALYSIS);
                            modelAndView.addObject("packageListProductAnalysis",Constants.PACKAGE_LIST_PRODUCT_ANALYSIS);
                            modelAndView.addObject("packageListCredit",Constants.PACKAGE_LIST_CREDIT);
                        }
                        modelAndView.addObject("packageListAll",Constants.PACKAGE_LIST_ALL);
                        break;
                }
            }
        }
    }
    /**
     * 加载产品列表
     *
     * @return
     * @throws Exception
     */
    protected void fetchProductlist(HttpServletRequest request, ModelAndView modelAndView,Integer productPackageType) throws Exception {


        // 获取产品包
        List<ProductPackage> packages = orderService.findProductPackageByType(request,productPackageType);
        if (CollectionUtils.isNotEmpty(packages)) {
            if(productPackageType == null){
                Constants.BASE_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.KEYWORD_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.WEIBO_ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.PRODUCT_ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.SINGLE_WEIBO_ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.PRO_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.USE_CREDIT_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.HOT_DAILY_MORE_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.HOT_DAILY_TIME_PRODUCT_LIST = new ArrayList<ProductPackage>();
                Constants.STRETCH_PRODUCT_LIST = new ArrayList<ProductPackage>();
            }
            else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_TASTE){
                Constants.BASE_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_KEYWORD){
                Constants.KEYWORD_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_ANALYSIS){
                Constants.ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_WEIBO_ANALYSIS){
                Constants.WEIBO_ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_PRODUCT_ANALYSIS){
                Constants.PRODUCT_ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_SINGLE_WEIBO_ANALYSIS){
                Constants.SINGLE_WEIBO_ANALYSIS_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_PRO_FUNCTION){
                Constants.PRO_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_USE_CREDIT){
                Constants.USE_CREDIT_PRODUCT_LIST = new ArrayList<ProductPackage>();
            } else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_HOTDAILYMORE){
                Constants.HOT_DAILY_MORE_PRODUCT_LIST = new ArrayList<ProductPackage>();
            }else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_HOTDAILYTIME){
                Constants.HOT_DAILY_TIME_PRODUCT_LIST = new ArrayList<ProductPackage>();
            }  else if(productPackageType == BusinessConstant.PRODUCT_PACKAGE_STRETCH){
                Constants.STRETCH_PRODUCT_LIST = new ArrayList<ProductPackage>();
            }
            for (ProductPackage productPackage : packages) {
                /**
                 * 体验型产品包
                 */
                if(productPackage.getPackageCategoryType() == BusinessConstant.PRODUCT_PACKAGE_TASTE){
                    Constants.BASE_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("baseProductList",Constants.BASE_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_PRO_FUNCTION){
                    Constants.PRO_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("proProductList",Constants.PRO_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_KEYWORD){
                    if(productPackage.getProductPackageId() == 3){
                        Constants.KEYWORD_PRODUCT_LIST.add(productPackage);
                        modelAndView.addObject("keywordProductList",Constants.KEYWORD_PRODUCT_LIST);
                    }
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_ANALYSIS){
                    Constants.ANALYSIS_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("analysisProductList",Constants.ANALYSIS_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_WEIBO_ANALYSIS){
                    Constants.WEIBO_ANALYSIS_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("weiboAnalysisProductList",Constants.WEIBO_ANALYSIS_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_USE_CREDIT){
                    Constants.USE_CREDIT_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("useCreditProductList",Constants.USE_CREDIT_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_PRODUCT_ANALYSIS){
                    Constants.PRODUCT_ANALYSIS_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("productAnalysisProductList",Constants.PRODUCT_ANALYSIS_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_SINGLE_WEIBO_ANALYSIS){
                    Constants.SINGLE_WEIBO_ANALYSIS_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("singleWeiboAnalysisProductList",Constants.SINGLE_WEIBO_ANALYSIS_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_HOTDAILYMORE){
                    Constants.HOT_DAILY_MORE_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("hotDailyMoreProductList",Constants.HOT_DAILY_MORE_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_HOTDAILYTIME){
                    Constants.HOT_DAILY_TIME_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("hotDailyTimeProductList",Constants.HOT_DAILY_TIME_PRODUCT_LIST);
                }else if(productPackage.getPackageCategoryType()  == BusinessConstant.PRODUCT_PACKAGE_STRETCH){
                    Constants.STRETCH_PRODUCT_LIST.add(productPackage);
                    modelAndView.addObject("stretchProductList",Constants.STRETCH_PRODUCT_LIST);
                }
            }
        }
        modelAndView.addObject("userPlatform",userPlatform);
        modelAndView.addObject("stretchProductList",Constants.STRETCH_PRODUCT_LIST);
    }
    /**
     * 重置每页显示数量
     */
    protected void resetPagesize() {
        if (pagesize <= 0) {
            pagesize = PagerUtils.PAGE_SIZE;
        }
        if (page <= 0) {
            page = 1;
        }
    }
    protected long collectTotal;
    protected void fetchRightNumber(HttpServletRequest request,ModelAndView modelAndView) throws Exception {
        fetchSessionAdmin(request);
        resetPagesize();
        if (admin != null && StringUtils.isNoneBlank(admin.getUserId())) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("userEncode", CommonUtils.getUserEncodeNew(admin.getUserId()));
            map.put("platformTag", "wyq");
            map.put("directory", "0");
            map.put("directoryType", "1");
            String jsonStr=Utils.sendWrdIntraBusinessAPIPOST(Utils.getRequest(), "briefTotalSc", map);
            if (StringUtils.isNotBlank(jsonStr)) {
                IContentCommonNetView icns = JSON.parseObject(jsonStr, IContentCommonNetView.class);
                collectTotal = icns.getTotalCount();
                modelAndView.addObject("collectTotal",collectTotal);
            }
            BaseDto baseDto=apiInfoClient.queryCount(String.valueOf(admin.getUserId()));
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                Integer cartTotal = (Integer) baseDto.getData(); // 查询购物车
                if(cartTotal!=null){
                    modelAndView.addObject("cartTotal",cartTotal);
                }
            }

            BaseDto baseDto1=apiInfoClient.quertyByUserId(String.valueOf(admin.getUserId()));
            if(baseDto1!=null&& baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null){
                List<KeyWord> keywordList = (List<KeyWord>) baseDto1.getData();
                modelAndView.addObject("keywordList",keywordList);
            }
        }
    }

    /**
     * 刷新缓存对象
     * @param request
     * @return
     */
    public UserDto refreshSesssionUserInfo(HttpServletRequest request) {
        UserDto user = null;
        String sid = LoginUtils.getSidFromCookie(request);
        if (StringUtils.isNotBlank(sid)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("sid", sid);
            String rtnStr = Utils.sendWrdIntraBusinessAPIPOST(request, "refreshSesssionUserInfo", params);
            if (StringUtils.isNotBlank(rtnStr)) {
                UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
                if (userRes != null && CodeConstant.SUCCESS_CODE.equals(userRes.getCode())) {
                    user = userResConvertAdmin(userRes);
                    platform=userRes.getUserInfo().getPlatform();
                }
            }
        }
        return user;
    }

    /**
     * 获取session管理员对象
     */
    public UserDto fetchSessionAdmin(HttpServletRequest request) {
        UserDto user = null;
        Flags.local_flag = local_flag;
        Flags.filePath = filePath;
        String sid = LoginUtils.getSidFromCookie(request);
        if (StringUtils.isNotBlank(sid)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("sid", sid);
            String rtnStr = Utils.sendWrdIntraBusinessAPIPOST(request, "userSession", params);
            if (StringUtils.isNotBlank(rtnStr)) {
                UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
                if (userRes != null && CodeConstant.SUCCESS_CODE.equals(userRes.getCode())) {
                    user = userResConvertAdmin(userRes);
                    platform=userRes.getUserInfo().getPlatform();
                }
            }
        }

        if(user!=null){
            admin=user;
            BaseDto<LoginLog> baseDto = apiInfoClient.quertyUserPlatform(Integer.valueOf(admin.getUserId()));
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                LoginLog loginLog=baseDto.getData();
                userPlatform = loginLog.getLoginType();
            }
            fetchShowMenu(request,user);
        }

        if (StringUtils.isBlank(platform)){
            platform = "wyq";
        }
        if (userPlatform == null){
            userPlatform  = 1;
        }
        return user;
    }


    public static UserDto userResConvertAdmin(UserRes userRes){
        UserDto user = new UserDto();
        user.setUserId(String.valueOf(userRes.getUserInfo().getUserId()));
        user.setUsername(userRes.getUserInfo().getUsername());
        if (StringUtils.isNoneBlank(userRes.getUserInfo().getNickname())){
            user.setNickname(userRes.getUserInfo().getNickname());
        }else {
            user.setNickname("");
        }
        user.setEmail(userRes.getUserInfo().getEmail());
        user.setMobile(userRes.getUserInfo().getMobile());
        user.setPassword(userRes.getUserInfo().getPassword());
        user.setPasswordStrength(userRes.getUserInfo().getPasswordStrength());
        user.setLastLoginTime(userRes.getUserInfo().getLastLoginTime());
        user.setRemark(userRes.getUserInfo().getRemark());
        user.setStatus(userRes.getUserInfo().getStatus());
        user.setCreateTime(userRes.getUserInfo().getCreateTime());
        user.setUpdateTime(userRes.getUserInfo().getUpdateTime());
        if (StringUtils.isNoneBlank(userRes.getUserInfo().getUserHead())){
            user.setUserHead(userRes.getUserInfo().getUserHead());
        }else {
            user.setUserHead("");
        }
        user.setAppUserStatus(userRes.getUserInfo().getAppUserStatus());
        user.setUserChannel(userRes.getUserInfo().getUserChannel());
        user.setUserType(userRes.getUserInfo().getUserType());
        user.setProUserValidEndTime(userRes.getUserInfo().getProUserValidEndTime());
        user.setUserAnalysisValidCount(userRes.getUserInfo().getUserAnalysisValidCount());
        user.setUserWeiboAnalysisValidCount(userRes.getUserInfo().getUserWeiboAnalysisValidCount());
        user.setUserBriefValidCount(userRes.getUserInfo().getUserBriefValidCount());
        user.setUserReportValidCount(userRes.getUserInfo().getUserReportValidCount());
        user.setUserProductAnalysisValidCount(userRes.getUserInfo().getUserProductAnalysisValidCount());
        user.setUserSingleWeiboAnalysisValidCount(userRes.getUserInfo().getUserSingleWeiboAnalysisValidCount());
        user.setExportDataCount(userRes.getUserInfo().getExportDataCount());
        user.setCreditAmount(userRes.getUserInfo().getCreditAmount());
        user.setPlatform(userRes.getUserInfo().getPlatform());
        user.setSharePlanAmount(Double.parseDouble(new DecimalFormat("0.00").format(userRes.getUserInfo().getSharePlanAmount())));
        user.setCommentsCount(userRes.getUserInfo().getCommentsCount());
        user.setAllKeywordCount(userRes.getUserInfo().getAllKeywordCount());
        user.setNoUseKeywordCount(userRes.getUserInfo().getNoUseKeywordCount());
        user.setVipInfo(userRes.getUserInfo().getVipInfo());
        user.setValidWdSum(userRes.getUserInfo().getValidWdSum());
        user.setStopEndTime(userRes.getUserInfo().getStopEndTime());
        if (StringUtils.isNoneBlank(userRes.getUserInfo().getLastSignInDate())){
            user.setLastSignInDate(DateUtils.parse(userRes.getUserInfo().getLastSignInDate(),DateUtils.FORMAT_SHORT_TIME));
        }

        return  user;
    }



    /**
     * 加载显示菜单权限
     *
     * @param user
     */
    protected void fetchShowMenu(HttpServletRequest request,UserDto user) {
        if (user != null) {
            /*Object showSharePlanAmountObj = SessionUtil.getAttribute(sid, key);*/

            String showSharePlanAmountObj = RedisUtils.generateJedisKey("_SHARE_PLAN_AMOUNT_MENU_"+user.getUserId() + "_SHARE_PLAN_AMOUNT_MENU_" + user.getUserId());
            if (showSharePlanAmountObj != null) {
                showSharePlanAmountMenu = Boolean.parseBoolean(showSharePlanAmountObj);
            } else {
                try {
                    UserExclusiveChannelRes userExclusiveChannelRes = fetchUserExclusiveChannel(request,Integer.valueOf(user.getUserId()));
                    if (userExclusiveChannelRes != null && userExclusiveChannelRes.getUserExclusiveChannel().getUecRewardFlag() == 1) {
                        showSharePlanAmountMenu = true;
                    } else {
                        showSharePlanAmountMenu = false;
                    }

                    /*SessionUtil.setAttribute(sid, key, showSharePlanAmountMenu);*/
                    String key2 = RedisUtils.generateJedisKey("_SHARE_PLAN_AMOUNT_MENU_"+user.getUserId() + "_SHARE_PLAN_AMOUNT_MENU_" + user.getUserId());
                    RedisUtils.setAttribute(key2, String.valueOf(showSharePlanAmountMenu));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 获取个人专属渠道
     *
     * @param userId
     * @return
     * @throws Exception
     */
    protected UserExclusiveChannelRes fetchUserExclusiveChannel(HttpServletRequest request,int userId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(userId)));

        String rtnStr = Utils.sendWrdIntraBusinessAPIPOST(Utils.getRequest(), "userExclusiveChannelURL", params);
        System.out.println("fetchUserExclusiveChannel rtnStr = [" + rtnStr + "]");
        if (StringUtils.isNotBlank(rtnStr)) {
            UserExclusiveChannelRes userExclusiveChannelRes = JSON.parseObject(rtnStr, UserExclusiveChannelRes.class);
            if (userExclusiveChannelRes != null && CodeConstant.SUCCESS_CODE.equals(userExclusiveChannelRes.getCode())) {
                return userExclusiveChannelRes;
            }
        }
        return null;
    }

    protected String getUserEncodeNew(int userId){
        return CommonUtils.buildUserEncode(String.valueOf(userId));
    }
    protected String getUserEncodeNew(String userId){
        return CommonUtils.buildUserEncode(userId);
    }
//    public abstract BaseService<T> getBaseService();
//    @RequestMapping("add")
//    public Object add(@RequestBody Map map){
//        beforeAdd(map);
//        T newinstance = getBaseService().newinstance(map);
//        T t = getBaseService().add(newinstance);
//        afterAdd(map);
//        return success("");
//    }
//
//    @RequestMapping("delete")
//    public Object delete(@RequestBody Map map){
//        T newinstance = getBaseService().newinstance(map);
//        getBaseService().delete(newinstance);
//        return success("");
//    }

    /**
     * 通过主键id批量删除 参数必须为数组，名为ids
     * @param ids
     * @return
     */
//    @RequestMapping("deletes")
//    public Object batchDelete(@RequestParam("ids[]") Integer ids[]){
//        int i = getBaseService().deleteByIds(Arrays.asList(ids));
//        if(i>0)
//            return success();
//        else
//            return fail("");
//    }



//    @RequestMapping("update")
//    public Object update(@RequestBody Map map){
//        T t = getBaseService().newinstance(map);
//        int rs= getBaseService().update(t);
//        if(rs>0){
//            return  success(null,"");
//        }else{
//            return fail("更新失败");
//        }
//
//    }
//    @RequestMapping("get")
//    public Object getOne(@RequestBody Map map){
//        T t = getBaseService().newinstance(map);
//        t=getBaseService().queryOne(t);
//        if(null==t)
//            return  fail("");
//        return success(t,"");
//    }

//    @RequestMapping("getlist")
//    public Object getList(@RequestBody Map map){
//        T t = getBaseService().newinstance(map);
//        List<T> rs=getBaseService().queryList(t);
//        return success(rs,"");
//    }

    /**
     * 附带检索条件的分页查询
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
//    @RequestMapping("getPageQuery")
//    public Object getListByPage(@RequestBody Map map, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
//        T t = getBaseService().newinstance(map);
//        PageInfo<T> pageInfo =getBaseService().queryListByPage(t,pageNo,pageSize);
//        return success(pageInfo,"");
//    }

    /**
     * 无检索条件的分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
//    @RequestMapping("getPage")
//    public Object getListByPage( @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
//        //T t = getBaseService().newinstance(null);
//        T t=null;
//        PageInfo<T> pageInfo =getBaseService().queryListByPage(t,pageNo,pageSize);
//        return success(pageInfo,"");
//    }





//    public JSONObject fail(String err) {
//        JSONObject object = new JSONObject();
//        object.put("status", "FAIL");
//        object.put("msg", err);
//        object.put("code", 1);
//        return object;
//    }

//    public JSONObject success() {
//        JSONObject object = new JSONObject();
//        object.put("status", "SUCCESS");
//        object.put("code", 0);
//        return object;
//    }

//    public JSONObject success(String msg) {
//        JSONObject object = new JSONObject();
//        object.put("status", "SUCCESS");
//        object.put("msg", msg);
//        object.put("code", 0);
//        return object;
//    }

//    public JSONObject success(Object data ,String msg) {
//        JSONObject object = new JSONObject();
//        object.put("status", "SUCCESS");
//        object.put("data", data);
//        object.put("msg", msg);
//        object.put("code", 0);
//        return object;
//    }
    /**
     * 从thread local获取网络上下文
     */
    public HttpServletRequest getServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes;
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取当前客户端session对象
     * @return
     */
    public HttpSession getSession() {
        return getServletRequest().getSession();
    }

    /**
     *@see
     *
     * @param req
     *            请求
     * @return uecCode
     *            返回找到的渠道代码，有返回代码，没有返回 null
     *
     */
    public String doUecChannel(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        String uecCode = null;
        System.out.println("doUecChannel().cookies = "+cookies);
        if(cookies != null){
            for (Cookie cookie : cookies) {
                System.out.println("doUecChannel().Cookie getName=" + cookie.getName());
                System.out.println("doUecChannel().Cookie getDomain=" + cookie.getDomain());
                System.out.println("doUecChannel().Cookie getValue=" + cookie.getValue());
                // 火车票类型单独判断
                if(StringUtils.isNotEmpty(cookie.getName()) && cookie.getName().equals(BusinessConstant.UEC_CHANNEL_CODE+"_"+BusinessConstant.UEC_CHANNEL_MHCP)){
                    uecCode = BusinessConstant.UEC_CHANNEL_MHCP+"_"+cookie.getValue();
                    break;
                }else if(StringUtils.isNotEmpty(cookie.getName()) && cookie.getName().contains(BusinessConstant.UEC_CHANNEL_CODE)){
                    //其它渠道类型
                    String[] userinfos = cookie.getName().split("_");
                    uecCode = userinfos[1]+"_"+cookie.getValue();
                    break;
                }
            }
        }
        System.out.println("doUecChannel().uecCode="+uecCode);
        return uecCode;
    }

}

