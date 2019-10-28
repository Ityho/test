package com.miduchina.wrd.api.research.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @auther yho
 * @vreate 2019-08 15:13
 */
public interface ApplicantService {
    /**
     * 保存研究院申请人信息
     *
     * @param applicant
     * @return
     */
   boolean doSaveApplicant(JSONObject applicant) ;
}
