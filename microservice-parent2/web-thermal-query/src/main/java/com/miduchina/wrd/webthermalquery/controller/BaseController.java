package com.miduchina.wrd.webthermalquery.controller;



/**
 * 所有的Action 需继承此Action，主要用于公共参数集中设置及公共代码调用抽象，简化代码
 *
 * @author James
 *
 */
public abstract class BaseController extends MSBaseController {
        private static final long serialVersionUID = -772000430790002078L;

//        /**
//         * 操作日志服务对象
//         */
//        protected OperationalLogBean operationalLogBean;
//
//        protected OperationalLog operationalLog;
//        protected List<BriefNewsCategory> negativeFolds;
//        protected List<BriefNewsCategory> favoriteFolds;
//        protected List<BriefNewsCategory> sucaiFolds;
//        protected List<FilterWebsiteType> fwTypeList;
//        protected String cdnResourcesPath = SysConfig.CDN_RESOURCES_PATH;
//
//        protected UserOperateLog userOperateLog;
//        protected UserOperateLogBean userOperateLogBean;
//
//        protected String jcloudLoginSwitch = SysConfig.JCLOUD_LOGIN_SWITCH;	//京东万象登录入口开关
//
//        private static final Logger logger = Logger.getLogger(BaseAction.class);
//
//        public OperationalLogBean getOperationalLogBean() {
//            return operationalLogBean;
//        }
//
//        public void setOperationalLogBean(OperationalLogBean operationalLogBean) {
//            this.operationalLogBean = operationalLogBean;
//        }
//
//        public OperationalLog getOperationalLog() {
//            return operationalLog;
//        }
//
//        public void setOperationalLog(OperationalLog operationalLog) {
//            this.operationalLog = operationalLog;
//        }
//
//        @SuppressWarnings("unchecked")
//        protected void fetchKWFolders() {
//            negativeFolds = (List<BriefNewsCategory>) ActionContext.getContext().getSession().get(UserAdapter.FOLDER_NA);
//
//            if (negativeFolds == null || negativeFolds.size() == 0) {
//                System.out.println("*****find negativeFolds********");
//                negativeFolds = operationalLogBean.getFolders(admin, 3);
//                ActionContext.getContext().getSession().put(UserAdapter.FOLDER_NA, negativeFolds);
//            }
//
//            favoriteFolds = (List<BriefNewsCategory>) ActionContext.getContext().getSession().get(UserAdapter.FOLDER_FA);
//            if (favoriteFolds == null || favoriteFolds.size() == 0) {
//                favoriteFolds = operationalLogBean.getFolders(admin, 2);
//                ActionContext.getContext().getSession().put(UserAdapter.FOLDER_FA, favoriteFolds);
//            }
//
//            sucaiFolds = (List<BriefNewsCategory>) ActionContext.getContext().getSession().get(UserAdapter.FOLDER_SC);
//
//            if (sucaiFolds == null || sucaiFolds.size() == 0) {
//                sucaiFolds = operationalLogBean.getFolders(admin, 5);
//                ActionContext.getContext().getSession().put(UserAdapter.FOLDER_SC, sucaiFolds);
//            }
//        }
//
//        protected void initFilterWebSiteType() {
//            // fetchSessionAdmin();
//            logger.info(SimpleUtils.appendFlag("initFilterWebSiteType username=[" + admin.getUserId() + "]"));
//            // fwTypeList =
//            // (List<FilterWebsiteType>)ActionContext.getContext().getSession().get(
//            // UserAdapter.FILTER_WEBSITE_TYPE);
//            //
//            // if (fwTypeList==null || fwTypeList.size()==0){
//            /*
//             * 暂时不取admin，memocache admin = (Admin)
//             * ActionContext.getContext().getSession().get(
//             * UserAdapter.SYS_SESSION_NAME);
//             */
//            fwTypeList = operationalLogBean.getFWType(admin); // 过滤的网站类型
//            if (fwTypeList != null && fwTypeList.size() > 0) {
//                logger.info(SimpleUtils.appendFlag("fwTypeList size=[" + fwTypeList.size() + "];username=[" + admin.getUserId() + "]"));
//            }
//
//            /*
//             * ActionContext.getContext().getSession().put(
//             * UserAdapter.FILTER_WEBSITE_TYPE, fwTypeList);
//             */
//            // }
//        }
//
//        @Autowired
//        public WarningBean warningBean;
//        @Autowired
//        protected SendErrorEmailBean sendErrorEmailBean;
//
//        private String retMsg;// 操作返回信息
//
//        public String getRetMsg() {
//            return retMsg;
//        }
//
//        public void setRetMsg(String retMsg) {
//            this.retMsg = retMsg;
//        }
//
//        public WarningBean getWarningBean() {
//            return warningBean;
//        }
//
//        public void setWarningBean(WarningBean warningBean) {
//            this.warningBean = warningBean;
//        }
//
//        protected void initOperationalLog() {
//
//            operationalLog = new OperationalLog();
//            operationalLog.setSystem(SystemConstants.SYS_SOURCE + "");
//            // operationalLog.setAccount(admin.getUserId());
//            // operationalLog.setIp(ServletActionContext.getRequest().getHeader(""));
//
//            HttpServletRequest req = ServletActionContext.getRequest();
//            String ip = ServletActionContext.getRequest().getHeader("x-forwarded-for");
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
//                ip = req.getHeader("Proxy-Client-IP");
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
//                ip = req.getHeader("WL-Proxy-Client-IP");
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
//                ip = req.getRemoteAddr();
//            }
//            operationalLog.setIp(ip);
//        }
//
//        protected void initSmsSendLog() {
//            if (admin != null) {
//                // LogSmsSend slog = new LogSmsSend();
//                // // slog.setUsername(admin.getUserId());
//                //
//                // slog.setCreateTime(new Date());
//            }
//        }
//
//        protected String getTitle(String content) {
//            String title = "";
//            Pattern sip = Pattern.compile("(【[^】]*】)"); // 查找[]中的标题
//            Matcher mp = sip.matcher(content);
//            while (mp.find()) {
//                title = mp.group(1);
//                break;
//            }
//
//            if (title.equals("【原微博】")) {
//                title = "";
//            }
//            if (title.equals("") && content.length() > 15) {
//                title = content.substring(0, 15) + "...";
//
//            } else if (title.equals("") && content.length() < 15) {
//                title = content;
//
//            }
//            return title;
//
//        }
//
//        public static String getTitle(String content, int len, String sourceName) {
//            String title = "";
//            Pattern sip = Pattern.compile("(【[^】]*】)"); // 查找[]中的标题
//            Matcher mp = sip.matcher(content);
//            while (mp.find()) {
//                title = mp.group(1);
//                break;
//            }
//
//            if (title.equals("") && content.length() > len) {
//                title = sourceName + " " + content.substring(0, len) + "...";
//            } else if (title.equals("") && content.length() < len) {
//                title = sourceName + " " + content;
//            }
//            return title;
//
//        }
//
//        /*
//         * public String getPropertiesValue(String key) { try { Properties pts = new
//         * Properties(); URL classpath =
//         * Thread.currentThread().getContextClassLoader().getResource("");
//         *
//         * String cfgFilePath = classpath.getPath() + "url-base.properties";
//         * pts.load(new FileInputStream(cfgFilePath)); return
//         * pts.get(key).toString(); } catch (Exception e) { e.printStackTrace(); }
//         * return null; }
//         */
//
//        /**
//         * @see 添加日志
//         *
//         * @param category
//         *            位置
//         * @param action
//         *            类型
//         * @param briefInfo
//         *            描述
//         * @param relatedIds
//         *            用户
//         *
//         */
//        public synchronized void saveLog(String category, byte action, String briefInfo, String relatedIds) {
//            initOperationalLog();
//            operationalLog.setCategory(category);
//            operationalLog.setAction(SystemConstants.Operate_Modify);
//            operationalLog.setBriefInfo(briefInfo);
//            operationalLog.setRelatedIds(admin.getUserId() + "");
//            operationalLogBean.save(operationalLog);
//        }
//
//        /**
//         * 记录日志
//         *
//         * @param userId
//         * @param operateType
//         * @param operateDetail
//         * @return
//         */
//        protected boolean saveUserOperateLog(int userId, int operateType, String operateDetail) {
//            return true;
//            // if (operateType > 0) {
//            // HttpServletRequest request = ServletActionContext.getRequest();
//            // String ip = request.getHeader("x-forwarded-for");
//            // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
//            // {
//            // ip = request.getHeader("Proxy-Client-IP");
//            // }
//            // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
//            // {
//            // ip = request.getHeader("WL-Proxy-Client-IP");
//            // }
//            // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
//            // {
//            // ip = request.getRemoteAddr();
//            // }
//            // // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
//            // if (ip != null && ip.length() > 15) {
//            // if (ip.indexOf(",") > 0)
//            // ip = ip.substring(0, ip.indexOf(","));
//            // }
//            //
//            // userOperateLog = new UserOperateLog();
//            // userOperateLog.setUserId(userId);
//            // userOperateLog.setOperateType(operateType);
//            // userOperateLog.setOperateDetail(operateDetail);
//            // userOperateLog.setSourceIp(ip);
//            // userOperateLog.setSourceProduct(1);
//            // userOperateLog.setOperateTime(new Date());
//            //
//            // return userOperateLogBean.saveLog(userOperateLog);
//            // }
//            // return false;
//        }
//
//        public List<BriefNewsCategory> getNegativeFolds() {
//            return negativeFolds;
//        }
//
//        public void setNegativeFolds(List<BriefNewsCategory> negativeFolds) {
//            this.negativeFolds = negativeFolds;
//        }
//
//        public List<BriefNewsCategory> getFavoriteFolds() {
//            return favoriteFolds;
//        }
//
//        public void setFavoriteFolds(List<BriefNewsCategory> favoriteFolds) {
//            this.favoriteFolds = favoriteFolds;
//        }
//
//        public List<BriefNewsCategory> getSucaiFolds() {
//            return sucaiFolds;
//        }
//
//        public void setSucaiFolds(List<BriefNewsCategory> sucaiFolds) {
//            this.sucaiFolds = sucaiFolds;
//        }
//
//        public List<FilterWebsiteType> getFwTypeList() {
//            return fwTypeList;
//        }
//
//        public void setFwTypeList(List<FilterWebsiteType> fwTypeList) {
//            this.fwTypeList = fwTypeList;
//        }
//
//        public UserOperateLog getUserOperateLog() {
//            return userOperateLog;
//        }
//
//        public void setUserOperateLog(UserOperateLog userOperateLog) {
//            this.userOperateLog = userOperateLog;
//        }
//
//        public UserOperateLogBean getUserOperateLogBean() {
//            return userOperateLogBean;
//        }
//
//        public void setUserOperateLogBean(UserOperateLogBean userOperateLogBean) {
//            this.userOperateLogBean = userOperateLogBean;
//        }
//
//        public String getJcloudLoginSwitch() {
//            return jcloudLoginSwitch;
//        }
//
//        public void setJcloudLoginSwitch(String jcloudLoginSwitch) {
//            this.jcloudLoginSwitch = jcloudLoginSwitch;
//        }
//
//        public String getCdnResourcesPath() {
//            return cdnResourcesPath;
//        }
//
//        public void setCdnResourcesPath(String cdnResourcesPath) {
//            this.cdnResourcesPath = cdnResourcesPath;
//        }
//
//    }

}
