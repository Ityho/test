package com.miduchina.wrd.api.controller.v1.log;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.other.util.CommonUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by shitao on 2019-05-27 10:00.
 *
 * @author shitao
 */
@Slf4j
@Api(tags = {"操作日志"})
@RestController
@RequestMapping("api/v1/operatlog")
public class OperatLogController {


    @ApiOperation(value = "添加操作日志", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "添加操作日志")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "addLog", headers = "Accept=application/json")
    public BaseDto addLog(@ApiParam(value = "日志对象JOSN字符串") @RequestParam(required = false) String jsonStr ) {
        BaseDto baseDto = new BaseDto();

        if (StringUtils.isNoneBlank(jsonStr)){
            try {
                CommonUtils.opreateLog(jsonStr);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
            }
        }
        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
    }


    @ApiOperation(value = "添加操作日志", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "添加操作日志")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "addMapLog", headers = "Accept=application/json")
    public BaseDto addMapLog(@ApiParam(value = "日志对象（productSection,operateType,url,OperateLogObjectDto对象）") @RequestParam(required = true) Map<String, Object> map) {
        BaseDto baseDto = new BaseDto();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        try {
            JSONObject jsonParams = new JSONObject(map);
            Integer productSection = jsonParams.getInteger("productSection") == null ? 0:jsonParams.getInteger("productSection");
            Integer operateType = jsonParams.getInteger("operateType") == null ? 0:jsonParams.getInteger("operateType");
            String url = jsonParams.getString("url") == null ? "":jsonParams.getString("url");
            OperateLogObjectDto objectDto =  CommonUtils.mapToEntity(map,OperateLogObjectDto.class);

            CommonUtils.opreateLog(request,url, productSection, objectDto,operateType,null,null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }
        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
    }




}
