package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.hotspot.GiftCard;
import com.miduchina.wrd.po.hotspot.NotLoginOperateRecord;
import com.miduchina.wrd.po.ranking.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: UserCenterService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:42 AM
 */
public interface UserCenterService {
    List<Notice> showNewNotice(Date str);

    List<GiftCard> queryGiftCardByUserId(String str);

    Integer findTodayLoginCountByUserId(String userId);

    boolean saveOrUpdateNotLoginOperateRecord(NotLoginOperateRecord nor);

    UserDto findUserByMobile(String mobile);


    NotLoginOperateRecord findNotLoginOperateRecordByIpAndUA(@Param("ip")String ip, @Param("ua")String ua);

    boolean updateNotLoginOperateRecord(String ip, String ua);
}
