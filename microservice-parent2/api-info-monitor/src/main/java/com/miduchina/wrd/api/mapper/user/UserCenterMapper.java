package com.miduchina.wrd.api.mapper.user;

import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.hotspot.GiftCard;
import com.miduchina.wrd.po.hotspot.NotLoginOperateRecord;
import com.miduchina.wrd.po.ranking.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 13:58
 */
@Mapper
public interface UserCenterMapper {
    List<Notice> showNewNotice(Date str);

    List<GiftCard> queryGiftCardByUserId(String userId);

    Integer findTodayLoginCountByUserId(@Param("format") String format,@Param("format1") String format1, @Param("userId")String userId);

    boolean saveOrUpdateNotLoginOperateRecord(NotLoginOperateRecord nor);

    List<UserDto> findUserByMobile(String mobile);

    List<NotLoginOperateRecord> findNotLoginOperateRecordByIpAndUA(@Param("ip") String ip, @Param("ua") String ua);

    boolean updateNotLoginOperateRecord(@Param("ip") String ip, @Param("ua") String ua);
}
