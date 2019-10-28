package com.miduchina.wrd.webthermalquery.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.bigdata.OperationAdminWbDto;
import com.miduchina.wrd.dto.hotsportview.CouponsView;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.po.hotspot.Coupon;
import com.miduchina.wrd.po.hotspot.OperationAdminWb;
import com.miduchina.wrd.util.CommonUtils;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import com.tuke.gospel.core.vo.PaginationSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

/**
 * 新版热点事件模块功能
 *
 * @param
 * @author zhufangtao
 * @date
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
@Controller
@RequestMapping("hot/events")
public class HotEventController extends BaseController {
    private final String ONE = "1";
    private final String TWO = "2";
    private final String NATIONAL = "全国";
    @Autowired
    private UserFeignClient userFeignClient;

//    @Autowired
//    @Qualifier("stringRedisTemplate")
//    private StringRedisTemplate redisTemplate;
    /**
     * 排行榜控制请求
     *
     * @author zhufangtao
     * @date
     * @param
     * @return
     */
    private Boolean flag = false;
    private Boolean flag1 = false;
    private Boolean flag2 = false;
    private Boolean flag4 = false;
    private Boolean flag5 = false;
    private Boolean flag6 = false;
    private Boolean flag7 = false;
    /**
     * 微舆情解读案例
     *
     * @author zhufangtao
     * @date
     * @param
     * @return
     */


    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "goHotEvent")
    String goHotEvent(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map) {
//        fetchSessionAdmin();
//        return Action.SUCCESS;
        String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
        map.put("sysConfig",System.currentTimeMillis());
        map.put("url",url);
        map.put("request",request);
        map.put("collectTotal",0);
        map.put("cartTotal",0);
        String firstLoginSign = request.getParameter("firstLoginSign");
        if(StringUtils.isNotBlank(firstLoginSign)){
            map.put("firstLoginSign",firstLoginSign);
        }
        UserDto userDto = fetchSessionAdmin();
//        userDto = fetchSessionAdmin();
        if(userDto==null){
            UserDto userDto1 = new UserDto();
            map.put("admin",userDto1);
            map.put("admin1",userDto);
            map.put("islogin",false);
        }else{
            map.put("admin",userDto);
            map.put("admin1",userDto);
            map.put("islogin",true);
        }
//        map.put("nickName",nickName );
        String qrCodeImg = SysConfig.cfgMap.get("QR_CODE_IMG");
        map.put("qrCodeImg",qrCodeImg );
        map.put("msg",null );
        map.put("selectLoginType",1 );
        return "events/hotEvents";
    }
    @GetMapping("goHotHome")
    String goHotHome(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map) {
//		return Action.SUCCESS;
        String firstLoginSign = request.getParameter("firstLoginSign");
        if(StringUtils.isNotBlank(firstLoginSign)){
            map.put("firstLoginSign",firstLoginSign);
        }else{
            map.put("firstLoginSign","0");
        }
        String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
        map.put("sysConfig",System.currentTimeMillis());
        map.put("url",url);
        map.put("request",request);
        map.put("collectTotal",0);
        map.put("cartTotal",0);
        UserDto userDto = fetchSessionAdmin();
//        userDto = fetchSessionAdmin();
        if(userDto==null){
            UserDto userDto1 = new UserDto();
            map.put("admin",userDto1);
            map.put("admin1",userDto);
            map.put("islogin",false);
        }else{
            map.put("admin",userDto);
            map.put("admin1",userDto);
            map.put("islogin",true);
        }
//        UserDto userDto = new UserDto();
//        userDto.setProUserValidEndTime(new Date());
//        userDto.setUserId(12);

        map.put("nickname","7777" );
        map.put("item","7777" );
        String qrCodeImg = SysConfig.cfgMap.get("QR_CODE_IMG");
        map.put("qrCodeImg",qrCodeImg );
        map.put("msg",null );
        map.put("selectLoginType",1 );
        map.put("isShowTop","1");
        map.put("currentPage","hot");
        String login = request.getParameter("login");
        if(StringUtils.isNotBlank(login)){
            map.put("login",login);
        }else{
            map.put("login","");
        }
        return "events/test";
//        return "events/navigate";
    }
    private static String getVcode(int length) {
        String vCode = "";
        Random random = new Random();
        int temp = 0;
        String tempStr = "";
        try {
            for (int i = 0; i < length; i++) {
                switch (random.nextInt(3)) {
                    case 1:// A~Z
                        temp = random.nextInt(26) + 65;
                        tempStr = String.valueOf((char) temp);
                        if ("I".equals(tempStr))
                            tempStr = "A";
                        else if ("O".equals(tempStr))
                            tempStr = "B";
                        vCode += tempStr;

                        break;

                    case 2:// a~z
                        temp = random.nextInt(26) + 97;
                        tempStr = String.valueOf((char) temp);
                        if ("l".equals(tempStr))
                            tempStr = "c";
                        else if ("o".equals(tempStr))
                            tempStr = "d";
                        vCode += tempStr;

                        break;

                    case 3:// 汉字
                        String[] rBase = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
                        int r1 = random.nextInt(3) + 11; // 生成第1位的区码
                        String strR1 = rBase[r1]; // 生成11～14的随机数
                        int r2; // 生成第2位的区码
                        if (r1 == 13)
                            r2 = random.nextInt(7); // 生成0～7的随机数
                        else
                            r2 = random.nextInt(16); // 生成0～16的随机数
                        String strR2 = rBase[r2];
                        int r3 = random.nextInt(6) + 10; // 生成第1位的位码
                        String strR3 = rBase[r3];
                        int r4; // 生成第2位的位码
                        if (r3 == 10)
                            r4 = random.nextInt(15) + 1; // 生成1～16的随机数
                        else if (r3 == 15)
                            r4 = random.nextInt(15); // 生成0～15的随机数
                        else
                            r4 = random.nextInt(16); // 生成0～16的随机数
                        String strR4 = rBase[r4];
                        // 将生成的机内码转换成数字
                        byte[] bytes = new byte[2];
                        String strR12 = strR1 + strR2; // 将生成的区码保存到字节数组的第1个元素中
                        int tempLow = Integer.parseInt(strR12, 16);
                        bytes[0] = (byte) tempLow;
                        String strR34 = strR3 + strR4; // 将生成的区码保存到字节数组的第2个元素中
                        int tempHigh = Integer.parseInt(strR34, 16);
                        bytes[1] = (byte) tempHigh;
                        vCode += new String(bytes);

                        break;

                    default:// 数字
                        temp = random.nextInt(10) + 48;
                        tempStr = String.valueOf((char) temp);
                        if ("0".equals(tempStr))
                            tempStr = "2";
                        else if ("1".equals(tempStr))
                            tempStr = "3";
                        vCode += tempStr;

                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vCode;
    }
    public String getCertPic(int width, int height, OutputStream os){
        //在内存中创建图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        //开始生成验证码,这里用加法求和
        Random r = new Random();
        String vCode = getVcode(6);

        //将验证码存入session
        //将验证码显示到图像中
        g.setColor(Color.BLACK);
        g.setFont(new Font("", Font.PLAIN, 20));
        g.drawString(vCode, (int) (Math.random() * 30), (int) (Math.random() * 20) + 18);
        //随即产生5条直线
        for (int i = 0; i < 5; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            g.setColor(Color.RED);
            g.drawLine(width/(x+1), height/(y+1), x, y);
        }
        //产生200个点
        for(int i=0;i<200;i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            g.setColor(Color.BLUE);
            g.drawOval(x, y, 1, 1);
        }

        try {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            return "";
        }
        return String.valueOf(vCode);
    }
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getRan")
    String getRan() throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        String str =getCertPic(110, 45, os);
        String c = request.getParameter("c");
        if (c == null || (c = c.trim()).length() == 0) {
            c = "reg";
        }
//        String vcodeKey =null;
//        if (StringUtils.isNotBlank(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN_VCODE).append(c).toString())) {
//            vcodeKey= new StringBuilder(SystemConstants.JEDIS_KEYS_PREFIX).append(SysConfig.cfgMap.get("WEBID_COOKIE_NAME")).append(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN_VCODE).append(c).toString()).toString();
//        }
        String vcodeKey = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN_VCODE).append(c).toString());
        System.out.println("validation jsp c = [" + c + "] str = [" + str + "] vcodeKey = [" + vcodeKey + "]");
        System.out.println("validation jsp c = [" + c + "] obj1 = [" + RedisUtils.getAttribute(vcodeKey) + "]");
//        String rtnStr = redisTemplate.opsForValue().get(vcodeKey);
//        if (StringUtils.isBlank(rtnStr))
        if (StringUtils.isBlank(RedisUtils.getAttribute(vcodeKey)))
//            redisTemplate.opsForValue().set(vcodeKey, str, 120);
//        String sd = redisTemplate.opsForValue().get(vcodeKey);
            RedisUtils.setAttribute(vcodeKey, str,SystemConstants.JEDIS_SESSION_TIME);
        System.out.println("validation jsp c = [" + c + "] obj2 = [" +RedisUtils.getAttribute(vcodeKey) + "]");
//        out.clear();
//        out = pageContext.pushBody();
        System.out.println("validation jsp c = [" + c + "] obj3 = [" + RedisUtils.getAttribute(vcodeKey) + "]");
        response.getOutputStream().flush();
        return str;
    }
    /**
     * 五十条热点事件
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "allChina")
    @ResponseBody
    BaseDto allChina() {
        BaseDto bd = new BaseDto();
        if (!flag7) {
            this.flag7 = true;
            UserDto userDto = fetchSessionAdmin();
            String url = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL") + "/v3/hotIncident/web/list";
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
//            HttpServletRequest request = ServletActionContext.getRequest();
            HashMap<String, Object> params = new HashMap<>(16);
            String timeType2 = request.getParameter("timeType");
            //即24小时时差
            if (ONE.equals(timeType2)) {
                //获取前1天的当前时间
                params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
                //获取当天当前时间
                params.put("endTime", DateUtils.getNow());
            }
            //即72小时时差
            if (TWO.equals(timeType2)) {
                //获取前1天的当前时间
                params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -3)));
                //获取当天当前时间
                params.put("endTime", DateUtils.getNow());
            }
            params.put("page", 1);
            params.put("pageSize", 50);
            params.put("showTag", 1);
            String sort = request.getParameter("sort");
            params.put("sort", sort);
            String areaType = request.getParameter("areaType");
            params.put("areaType", areaType);
            params.put("labelShowTag", 0);
            String result = HttpRequestUtils.sendPost(url, params);
            bd.setData(JSON.parseObject(result));
//            CommonUtils.writeJSONString(result);
            this.flag7 = false;
        }
        return bd;
    }

    /**
     * 排行榜列表数据
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "selectChooseListData")
    @ResponseBody
    BaseDto selectChooseListData(String timeType,Integer sort,String labels,String province,Integer areaType ) {
        BaseDto bd = new BaseDto();
        if (!flag) {
            this.flag = true;
            fetchSessionAdmin();
            String url = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL") + "/v3/hotIncident/web/list";
//            HttpServletRequest request = ServletActionContext.getRequest();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HashMap<String, Object> params = new HashMap<>(16);
//            String timeType2 = request.getParameter("timeType");
            String timeType2=timeType;
            //即24小时时差
            if (ONE.equals(timeType2)) {
                //获取前1天的当前时间
                params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
                //获取当天当前时间
                params.put("endTime", DateUtils.getNow());
            }
            //即72小时时差
            if (TWO.equals(timeType2)) {
                //获取前1天的当前时间
                params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -3)));
                //获取当天当前时间
                params.put("endTime", DateUtils.getNow());
            }

            String labels1=labels;
//            String labels1 = request.getParameter("labels");
            if (StringUtils.isNotBlank(labels1)) {
                params.put("labels", labels1);
            }
//            String province1 = request.getParameter("province");
            String province1=province;
            if (StringUtils.isNotBlank(province1) && !NATIONAL.equals(province1)) {
                params.put("province", province1);
            }
            params.put("page", 1);
            params.put("pageSize", 20);
            params.put("showTag", 1);
//            String sort = request.getParameter("sort");
            params.put("sort", sort);
//            String areaType = request.getParameter("areaType");
            params.put("areaType", areaType);
            params.put("labelShowTag", 0);
            String result = HttpRequestUtils.sendPost(url, params);
//            CommonUtils.writeJSONString(result);
            bd.setData(JSON.parseObject(result));
//            CommonUtils.writeJSONString(result);
            this.flag = false;
        }
        return bd;
    }

    /**
     * 地图右侧均值，事件数等数据
     * 1
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "selectRankData")
    @ResponseBody
    BaseDto selectRankData() throws Exception {
        BaseDto bd = new BaseDto();
        if (!flag1) {
            this.flag1 = true;
            String url = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL") + "/v3/hotIncident/count";
            Map<String, Object> paramsMap = new HashMap<String, Object>(16);
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
//            HttpServletRequest request = ServletActionContext.getRequest();
            String timeType = request.getParameter("timeType");
            //即24小时时差
            if (ONE.equals(timeType)) {
                //获取前1天的当前时间
                paramsMap.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
                //获取当天当前时间
                paramsMap.put("endTime", DateUtils.getNow());
            }
            //即72小时时差
            if (TWO.equals(timeType)) {
                //获取前1天的当前时间
                paramsMap.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -3)));
                //获取当天当前时间
                paramsMap.put("endTime", DateUtils.getNow());
            }

            String labels1 = request.getParameter("labels");
            if (StringUtils.isNotBlank(labels1)) {
                paramsMap.put("labels", labels1);
            }

            String province1 = request.getParameter("province");
            if (StringUtils.isNotBlank(province1)) {
                paramsMap.put("province", province1);
            }
            String result = HttpRequestUtils.sendPost(url, paramsMap);
            bd.setData(JSON.parseObject(result));
//            CommonUtils.writeJSONString(rankData);
            this.flag1 = false;

        }
        return bd;
    }

    /**
     * 热点类型标签
     * 2
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getHotClassList")
    @ResponseBody
    BaseDto getHotClassList() {
        BaseDto bd = new BaseDto();
        if (!flag2) {
            this.flag2 = true;
            String url = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL") + "/v1/hotLabel/get/groupByLevel";
            String result = HttpRequestUtils.sendPost(url);
//            CommonUtils.writeJSONString(s);
            bd.setData(JSON.parseObject(result));
            this.flag2 = false;
        }
        return bd;
    }

    /**
     * 查询未登录下的大数据解读的信息
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    private PaginationSupport<OperationAdminWb> DataPage(Integer page, Integer pagesize) {
        if (page == 0) {
            page = 1;
            pagesize = 15;
        }
        PaginationSupport<OperationAdminWb> res = null;
//        res = userCenterBean.findHotEventByPage(page, pagesize);
        return res;
    }
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "queryDataRead")
    @ResponseBody
    BaseDto queryDataRead() throws Exception {
        BaseDto bd = new BaseDto();
        PaginationSupport<OperationAdminWb> result = DataPage(0,0);
//        CommonUtils.writeJSON(result);
        bd.setData(JSON.toJSONString(result));
        return bd;
    }

    /**
     * 类型占比数据
     * 4
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "typeProp")
    @ResponseBody
    BaseDto typeProp() {
        BaseDto bd = new BaseDto();
        if (!flag4) {
            this.flag4 = true;
            HashMap<String, Object> param = new HashMap<>(16);
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
//            HttpServletRequest request = ServletActionContext.getRequest();
            String url = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL") + "/v3/hotIncident/typeCount";
            String timeType = request.getParameter("timeType");
            //即24小时时差
            if (ONE.equals(timeType)) {
                //获取前1天的当前时间
                param.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
                //获取当天当前时间
                param.put("endTime", DateUtils.getNow());
            }
            //即72小时时差
            if (TWO.equals(timeType)) {
                //获取前1天的当前时间
                param.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -3)));
                //获取当天当前时间
                param.put("endTime", DateUtils.getNow());
            }
            String province = request.getParameter("province");
            if (StringUtils.isNotBlank(province)) {
                param.put("province", province);
            }
            String result = HttpRequestUtils.sendPost(url, param);
            bd.setData(JSON.parseObject(result));
//            CommonUtils.writeJSONString(result);
            this.flag4 = false;

        }
        return bd;
    }

    /**
     * 查询地图上所有省份的热点事件
     * 5
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "allProvince")
    @ResponseBody
    BaseDto allProvince() {
        BaseDto bd = new BaseDto();
        if (!flag5) {
            this.flag5 = true;
            HashMap<String, Object> params = new HashMap<>(16);
            String url = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL") + "/v3/hotIncident/provinceGroupBy";
//            HttpServletRequest request = ServletActionContext.getRequest();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String timeType = request.getParameter("timeType");
            //即24小时时差
            if (ONE.equals(timeType)) {
                //获取前1天的当前时间
                params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
                //获取当天当前时间
                params.put("endTime", DateUtils.getNow());
            }
            //即72小时时差
            if (TWO.equals(timeType)) {
                //获取前1天的当前时间
                params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -3)));
                //获取当天当前时间
                params.put("endTime", DateUtils.getNow());
            }
            String result = HttpRequestUtils.sendPost(url, params);
            bd.setData(JSON.parseObject(result));
//            CommonUtils.writeJSONString(s);
            this.flag5 = false;
        }
        return bd;
    }

    /**
     * 数据解读
     * 6
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "dataRead")
    @ResponseBody
    BaseDto dataRead(Integer page,Integer pagesize,String likeName,Integer type ) {
        BaseDto bd = new BaseDto();
        if (!flag6) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>(16);
            if (page == null) {
                page = 1;
            }
            pagesize = 4;
            PageDto<OperationAdminWbDto>  res = null;
            if (likeName == null) {
                likeName = "";
            }
            if (type == null) {
                type = 0;
            }
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("page",page);
            param.put("pageSize",pagesize);
            param.put("likeName",likeName);
            param.put("type",type);
            PageDto<OperationAdminWbDto> operationAdminWbs1 = userFeignClient.list(param);
            List<OperationAdminWbDto> operationAdminWbs = operationAdminWbs1.getList();
            if (CollectionUtils.isNotEmpty(operationAdminWbs)) {
                for (OperationAdminWbDto oAdminWb : operationAdminWbs) {
                    oAdminWb.setArticleUrl(SysConfig.cfgMap.get("SYSTEM_WEB_URL") + "share/hotSearch/hotEventPreview.shtml?hotEventPreview.id=" + oAdminWb.getId());
                }
                hashMap.put("recommendList", operationAdminWbs);
            }
            Map<String, Object> param1 = new HashMap<String, Object>();
            param1.put("page",page);
            param1.put("pageSize",pagesize);
            param1.put("likeName",likeName);
            param1.put("type",type);
            res = userFeignClient.findHotEventByNameTypePage(param1);
            Long maxpage = Long.valueOf(0);
            if (res != null) {
                maxpage = res.getTotalCount() / 15 + (res.getTotalCount() % 15 == 0 ? 0 : 1);

                if (CollectionUtils.isNotEmpty(res.getList())) {
                    List<OperationAdminWbDto> oAdminWbs = res.getList();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
                    if (oAdminWbs != null && oAdminWbs.size() > 0) {
                        for (OperationAdminWbDto oAdminWb : oAdminWbs) {
                            oAdminWb.setArticleUrl(SysConfig.cfgMap.get("SYSTEM_WEB_URL") + "share/hotSearch/hotEventPreview.shtml?hotEventPreview.id=" + oAdminWb.getId());
                        }
                        hashMap.put("code", "0000");
                        hashMap.put("data", oAdminWbs);
                        hashMap.put("totalCount", maxpage);

                    }
                }
            }
            hashMap.put("code", "0000");
            bd.setData(hashMap);
//            CommonUtils.writeJSONString(JSONObject.toJSONString(hashMap));

        }
        this.flag6 = false;
        return bd;
    }

    /**
     * 功能描述
     * 领取优惠券
     *
     * @param
     * @return
     * @author zhufangtao
     * @date
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getCoupon")
    @ResponseBody
    BaseDto getCoupon() {
        BaseDto bd = new BaseDto();
        UserDto admin = fetchSessionAdmin();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
//        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        if (admin != null && id != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            params.put("couponId", id);
            params.put("platform", 1);
            String result = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request,IntraBusinessAPIConfig .getValue("exchangeCoupon"), params);
//            CommonUtils.writeJSONString(rtnStr);
            bd.setData(JSON.parseObject(result));
        }
//        CommonUtils.writeJSON(null);
        bd.setData(null);
        return bd;
    }


    /**
     * 根据用户id查询所以的微积分抵用卷
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "selectCoupons")
    @ResponseBody
    BaseDto selectCoupons(Integer page,Integer pagesize) {
        BaseDto bd = new BaseDto();
        UserDto admin = fetchSessionAdmin();
        if (admin != null) {
            if (pagesize == null)
                pagesize = 100;
            if (page == null)
                page = 1;
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
//            HttpServletRequest request = ServletActionContext.getRequest();
            int couponType = Integer.parseInt(request.getParameter("couponType"));
            int couponUse = Integer.parseInt(request.getParameter("couponUse"));
            int nearGet = Integer.parseInt(request.getParameter("nearGet"));
            int nearDue = Integer.parseInt(request.getParameter("nearDue"));
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            params.put("page", page);
            Object pageSize = params.put("pageSize", pagesize);
            List<Coupon> coupons = new ArrayList<>();
            if (couponType == 2) {
                //服务抵用券
                params.put("couponTypes", "0");
            } else {
                //微积分抵用券
                params.put("couponTypes", "1,2,100");
            }
            //1.待使用 2已使用 3已过期（未使用）
            if (couponUse == 1) {
                //全部
                params.put("status", 0);
            } else if (couponUse == 3) {
                //已使用
                params.put("status", 2);
            } else if (couponUse == 4) {
                //已过期
                params.put("status", 3);
            } else {
                //未使用
                params.put("status", 1);
            }
            if (nearGet == 2) {
                params.put("order", 1);//最近领取排序 1.领取时间排序 2.有效期时间排序
                params.put("aspect", 2);
            }
            if (nearDue == 2) {
                params.put("order", 2);//最近到期排序
            }

            String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("getWJFVouchersList"), params);
            CouponsView couponsView = JSONObject.parseObject(rtnStr, CouponsView.class);
            HashMap<String, CouponsView> stringCouponsViewHashMap = new HashMap<>();
            stringCouponsViewHashMap.put("data",couponsView);
//            CommonUtils.writeJSONString(JSONObject.toJSONString(stringCouponsViewHashMap));
            bd.setData(JSONObject.toJSONString(stringCouponsViewHashMap));
        }
        return bd;
    }
}

