package com.miduchina.wrd.eventanalysis.service.impl;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.eventanalysis.PackageListView;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.pay.ConfirmOrderDto;
import com.miduchina.wrd.dto.pay.ConfirmOrderViewDto;
import com.miduchina.wrd.dto.pay.OrderFeeViewDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.config.WyqBusinessConfig;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.po.eventanalysis.BaseView;
import com.miduchina.wrd.po.order.PayRecord;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class OrderClientServiceImpl implements OrderClientService {

    @Autowired
    APIInfoClient apiInfoClient;

    @Override
    public List<ProductPackage> findProductPackageByType(HttpServletRequest request,Integer type) {
        // 组装参数
        Map<String, Object> params = WyqBusinessConfig.getDataInitMap();
        if (type != null) {
            params.put("type", type);
        }
        String jsonStr =  Utils.sendWrdIntraBusinessAPIPOST(request,"packageList",params);
        if (StringUtils.isNotBlank(jsonStr)){
            PackageListView pages = JSON.parseObject(jsonStr, PackageListView.class);
            return pages.getPackageList();
        }
        return null;
    }

    @Override
    public OrderFeeViewDto findOrderFeeByUserId(HttpServletRequest request, ConfirmOrderDto orderVo, UserDto admin) {
        // 组装参数
        Map<String, Object> params = WyqBusinessConfig.getDataInitMap();
        params.put("userEncode", WyqBusinessConfig.encryptString(admin.getUserId().toString()));
        params.put("orderParam", JSON.toJSONString(orderVo));
        String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,"orderFee",params);
        OrderFeeViewDto pages = JSON.parseObject(jsonStr, OrderFeeViewDto.class);
        return pages;
    }
    @Override
    public Map<String, Object> doComfirmOrder(HttpServletRequest request,ConfirmOrderDto orderVo, UserDto admin) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 保存购物车
        // 组装参数
        Map<String, Object> params = WyqBusinessConfig.getDataInitMap();
        params.put("userEncode", WyqBusinessConfig.encryptString(admin.getUserId().toString()));
        params.put("orderParam", JSON.toJSONString(orderVo));

        String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,"orderCreate",params);

        if (StringUtils.isNotBlank(jsonStr)) {
            ConfirmOrderViewDto confirmOrderViewDto = JSON.parseObject(jsonStr, ConfirmOrderViewDto.class);
            log.info("doComfirmOrder=" + JSON.toJSONString(confirmOrderViewDto));
            if (confirmOrderViewDto != null) {
                if (CodeConstant.SUCCESS_CODE.equals(confirmOrderViewDto.getCode())) {
                    if (confirmOrderViewDto.getPayInfo() != null) {
                        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                        PayRecord pay = BeanUtils.copyProperties(confirmOrderViewDto.getPayInfo(), PayRecord.class);
                        log.info(JSON.toJSONString("doComfirmOrder pay=" + JSON.toJSONString(pay)));
                        map.put("code", confirmOrderViewDto.getCode());
                        map.put("payRecord", pay);
                        return map;
                    }
                } else {
                    map.put("code", confirmOrderViewDto.getCode());
                    return map;
                }
            }
        }
        map.put("code", "-1");
        return map;
    }

    @Override
    public ConfirmOrderViewDto doPayEnd(HttpServletRequest request,String innerTradeNo, UserDto admin, String trade_no) {
        // 完成订单，修改订单状态
        // 组装参数
        Map<String, Object> params = WyqBusinessConfig.getDataInitMap();
        params.put("userEncode", WyqBusinessConfig.encryptString(admin.getUserId().toString()));
        params.put("innerTradeNo", innerTradeNo);
        if (trade_no != null) {
            params.put("tradeNo", trade_no);
        }
        String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,"orderPayEnd",params);
        BaseView baseView = JSON.parseObject(jsonStr, BaseView.class);
        log.info("doPayEnd params = code" + baseView.getCode() + "message=" + baseView.getMessage());
        // 查询订单
        // 组装参数
        Map<String, Object> params1 = WyqBusinessConfig.getDataInitMap();
        params1.put("userEncode", WyqBusinessConfig.encryptString(admin.getUserId().toString()));
        params1.put("innerTradeNo", innerTradeNo);
        String jsonStr1 = Utils.sendWrdIntraBusinessAPIPOST(request,"orderDetail",params1);
        ConfirmOrderViewDto confirmOrderViewDto = JSON.parseObject(jsonStr1, ConfirmOrderViewDto.class);
        if (confirmOrderViewDto != null && CollectionUtils.isNotEmpty(confirmOrderViewDto.getPackagesInfo())) {
            log.info("doPayEnd params1 = code" + confirmOrderViewDto.getCode() + "message="
                    + confirmOrderViewDto.getMessage());
            log.info("doPayEnd PackageId = " + confirmOrderViewDto.getPackagesInfo().get(0).getProductPackageId());
            return confirmOrderViewDto;
        }
        log.info("doPayEnd message=" + confirmOrderViewDto.getMessage());
        return null;
    }

    @Override
    public Integer findPayRecordByUserIdAndPackageId(Integer userId, int productPackageId) {
        BaseDto<Integer> baseDto=apiInfoClient.findPayRecordByUserIdAndPackageId(userId,productPackageId);
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
            return baseDto.getData();
        }
        return null;
    }

    @Override
    public Integer findOrderCountByProductPackageIds(UserDto admin, String package_type_report_ids) {
        BaseDto<Integer> baseDto=apiInfoClient.findOrderCountByProductPackageIds(Integer.valueOf(admin.getUserId()),package_type_report_ids);
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
            return baseDto.getData();
        }
        return null;
    }

    @Override
    public ConfirmOrderViewDto findOrderByInnerTradeNo(HttpServletRequest request,Integer userId, String innerTradeNo) {
        // 组装参数
        Map<String, Object> params = WyqBusinessConfig.getDataInitMap();

        params.put("userTag", userId.toString());
        params.put("userEncode", WyqBusinessConfig.encryptString(userId.toString()));
        params.put("innerTradeNo", innerTradeNo);
        // 加密
        String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,"orderDetail",params);
//                sendGet(SysConfig.cfgMap.get("API_INTRA_BUSINESS_URL") + IntraBusinessAPIConfig.getValue("orderDetail"), params);
        ConfirmOrderViewDto confirmOrderViewDto = JSON.parseObject(jsonStr, ConfirmOrderViewDto.class);
        if (confirmOrderViewDto != null && CollectionUtils.isNotEmpty(confirmOrderViewDto.getPackagesInfo())) {
            log.info("doPayEnd params1 = code" + confirmOrderViewDto.getCode() + "message="
                    + confirmOrderViewDto.getMessage());
            log.info("doPayEnd PackageId = " + confirmOrderViewDto.getPackagesInfo().get(0).getProductPackageId());
            return confirmOrderViewDto;
        }
        log.info("doPayEnd message=" + confirmOrderViewDto.getMessage());
        return null;
    }


    @Override
    public void doPayRecordFail(String innerTradeNo, UserDto userDto, String trade_status) {
        // 组装参数
        Map<String, Object> params = WyqBusinessConfig.getDataInitMap();
        params.put("userEncode", WyqBusinessConfig.encryptString(userDto.getUserId().toString()));
        params.put("innerTradeNo", innerTradeNo);
        HttpRequestUtils.sendGet(SysConfig.cfgMap.get("API_INTRA_BUSINESS_URL") + IntraBusinessAPIConfig.getValue("orderPayFail"), params);
    }


}
