package com.miduchina.wrd.api.research.mapper;

import com.alibaba.fastjson.JSONObject;
import org.mapstruct.Mapper;

/**
 * @auther yho
 * @vreate 2019-08 15:19
 */
@Mapper
public interface ApplicantMapper {
    boolean doSaveApplicant(JSONObject applicant) ;
}
