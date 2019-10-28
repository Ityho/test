package com.miduchina.wrd.api.service.user.impl;

import com.miduchina.wrd.api.mapper.user.UserCenterMapper;
import com.miduchina.wrd.api.service.user.UserCenterService;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.hotspot.GiftCard;
import com.miduchina.wrd.po.hotspot.NotLoginOperateRecord;
import com.miduchina.wrd.po.ranking.Notice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: SubUserInfoServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:42 AM
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {
   @Resource
   private UserCenterMapper userCenterMapper;
    @Override
    public List<Notice> showNewNotice(Date str) {
        return userCenterMapper.showNewNotice(str);
    }

    @Override
    public List<GiftCard> queryGiftCardByUserId(String userId) {
        return userCenterMapper.queryGiftCardByUserId(userId);
    }

    @Override
    public Integer findTodayLoginCountByUserId(String userId) {
        Calendar calendar = Calendar.getInstance();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())+"00:00:00";
        String format1 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())+"23:59:59";
        return userCenterMapper.findTodayLoginCountByUserId(userId,format,format1);
    }

    @Override
    public boolean saveOrUpdateNotLoginOperateRecord(NotLoginOperateRecord nor) {
        return userCenterMapper.saveOrUpdateNotLoginOperateRecord(nor);
    }

    @Override
    public UserDto findUserByMobile(String mobile) {
       List<UserDto> list= userCenterMapper.findUserByMobile(mobile);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public NotLoginOperateRecord findNotLoginOperateRecordByIpAndUA(String ip, String ua) {
        List<NotLoginOperateRecord> list= userCenterMapper.findNotLoginOperateRecordByIpAndUA(ip,ua);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean updateNotLoginOperateRecord(String ip, String ua) {

        return userCenterMapper.updateNotLoginOperateRecord(ip,ua);
    }


}
