package com.miduchina.wrd.eventanalysis.controller;

import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.web.portlet.ModelAndView

@Slf4j
@Controller
@RequestMapping(value = "/app")
public class AppController extends BaseController{



    @RequestMapping(value = "/ruleBean")
    public ModelAndView ruleBean(ModelAndView modelAndView){
        modelAndView.setViewName("view/app/ruleBean");
        return modelAndView;
    }

    @RequestMapping(value = "/rulePrize")
    public ModelAndView rulePrize(ModelAndView modelAndView){
        modelAndView.setViewName("view/app/rulePrize");
        return modelAndView;
    }

    @RequestMapping(value = "/ruleSign")
    public ModelAndView ruleSign(ModelAndView modelAndView){
        modelAndView.setViewName("view/app/ruleSign");
        return modelAndView;
    }

    @RequestMapping(value = "/invitation")
    public ModelAndView invitation(ModelAndView modelAndView){
        modelAndView.setViewName("view/app/invitation");
        return modelAndView;
    }


    @RequestMapping(value = "/inviteRegister")
    public ModelAndView inviteRegister(HttpServletRequest request,ModelAndView modelAndView){
        admin=fetchSessionAdmin(request);

        String requestURI = request.getRequestURI();
        String inviteUserId = requestURI.substring(requestURI.indexOf("_")+1, requestURI.indexOf("."));
        if (StringUtils.isNoneBlank(inviteUserId)) {
            BaseDto<UserDto> userBaseDtoDto = apiInfoClient.findUserByUserId(Integer.valueOf(inviteUserId));
            if (userBaseDtoDto != null && userBaseDtoDto.getData()!= null && userBaseDtoDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                modelAndView.addObject("admin",userBaseDtoDto.getData());
                modelAndView.setViewName("view/app/inviteRegister");
            }else {
                modelAndView.setViewName(Flags.login_view);
            }
        }
        return modelAndView;
    }

}
