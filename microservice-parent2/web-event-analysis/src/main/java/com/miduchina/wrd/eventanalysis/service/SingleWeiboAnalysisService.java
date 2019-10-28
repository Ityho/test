package com.miduchina.wrd.eventanalysis.service;

import javax.servlet.http.HttpServletRequest;

public interface SingleWeiboAnalysisService {
    Integer queryCount(HttpServletRequest request,int userId, int confirmType);
}
