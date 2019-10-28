package com.miduchina.wrd.eventanalysis.service;

import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.ranking.HotIncidentDto;
import com.miduchina.wrd.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface HomeService {
    /**
     * 热门微博
     * @param pagesize
     * @param page
     * @param categoryId
     * @return
     */
    Map<String,Object> getHotWeibo(int pagesize, int page, int categoryId);


    /**
     * 热门事件标签
     * @param request
     * @return
     */
    String getHotLabel(HttpServletRequest request);


    /**
     * 热门事件
     * @param request
     * @return
     */
    String getHotEvent(HttpServletRequest request);

    /**
     * 热门事件 count
     * @param request
     * @return
     */
    String getHotEventCount(HttpServletRequest request);

    /**
     * 重大事件
     * @param request
     * @return
     */
    String getBigEventList(HttpServletRequest request);

    /**
     * 重大事件详情
     * @param request
     * @param id
     * @return
     */
    HotIncidentDto getBigEventDetail(HttpServletRequest request, int id,String startTime,String endTime);

    /**
     * 根据重大事件Id获取热门事件列表
     * @param request
     * @param id
     * @return
     */
    List<HotIncidentDto> getHotEventListByBigEventId(HttpServletRequest request,int id);



    /**
     * 搜索指数
     * @param request
     * @return
     */
    String getHotline(HttpServletRequest request, UserDto user);


    /**
     * 搜索词云
     * @param request
     * @return
     */
    String getWordcloud(HttpServletRequest request,UserDto user);

    ModelDto getEmotionProportion(HttpServletRequest request, UserDto user);

    ModelDto getEmotionMap(HttpServletRequest request, UserDto user);

    ModelDto getContentTrends(HttpServletRequest request, UserDto user);

    ModelDto getEmotionSex(HttpServletRequest request, UserDto user);

    ModelDto getEmotionType(HttpServletRequest request, UserDto user);

    ModelDto getEmotionSex2(HttpServletRequest request, UserDto user);

    ModelDto getEmotionType2(HttpServletRequest request, UserDto user);

    ModelDto getEmotionFans(HttpServletRequest request, UserDto user);

    ModelDto getEmotionLevel(HttpServletRequest request, UserDto user);

    ModelDto getEmotionHobby(HttpServletRequest request, UserDto user);

    ModelDto getZMHotWord(HttpServletRequest request, UserDto user);

    ModelDto getFMHotWord(HttpServletRequest request, UserDto user);


    ModelDto getMediaFriend(HttpServletRequest request, UserDto user);


    ModelDto getInfoFromV2(HttpServletRequest request, UserDto user);
    ModelDto getActiveMediaV2(HttpServletRequest request, UserDto user);
    ModelDto getVolumeMapV3(HttpServletRequest request, UserDto user);
    ModelDto getVirtualSolutionV3(HttpServletRequest request, UserDto user);
    ModelDto getHotPeopleV2(HttpServletRequest request, UserDto user);

    ModelDto getMediaFriendV3(HttpServletRequest request, UserDto user);
    ModelDto getEmotionSexV2(HttpServletRequest request, UserDto user);
    ModelDto getEmotionTypeV2(HttpServletRequest request, UserDto user);
    ModelDto getEmotionFansV2(HttpServletRequest request, UserDto user);
    ModelDto getEmotionLevelV2(HttpServletRequest request, UserDto user);
    ModelDto getEmotionHobbyV2(HttpServletRequest request, UserDto user);
    ModelDto getZMHotWordV2(HttpServletRequest request, UserDto user);
    ModelDto getFMHotWordV2(HttpServletRequest request, UserDto user);

    ModelDto getStatAndLineNewV4(HttpServletRequest request, UserDto user);
    ModelDto getRelatedTermsV3(HttpServletRequest request, UserDto user);
    ModelDto getHotMessage(HttpServletRequest request, UserDto user);
    ModelDto getWordcloudV3(HttpServletRequest request, UserDto user);




}
