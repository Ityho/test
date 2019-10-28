package com.miduchina.wrd.api.research.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.research.mapper.ApplicantMapper;
import com.miduchina.wrd.api.research.service.ApplicantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther yho
 * @vreate 2019-08 15:16
 */
@Service
public class ApplicantServiceImpl implements ApplicantService {

    @Resource
    private ApplicantMapper applicantMapper;

    @Override
    public boolean doSaveApplicant(JSONObject applicant) {
        return applicantMapper.doSaveApplicant(applicant);
    }
}
