package com.miduchina.wrd.api.mapper.user;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.h5.H5ShortUrl;
import com.miduchina.wrd.po.h5.WeiXinMaterial;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: UserThirdpartyAuthInfoMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:01 AM
 */
@Mapper
public interface UserThirdpartyAuthInfoMapper {

    List<UserThirdpartyAuthInfo> selectOneUserThirdpartyAuth(JSONObject params);

    UserThirdpartyAuthInfo selectUserThirdpartyAuthInfoByUid(String thirdpartyUserId);

    boolean insertUserThirdpartyAuthInfo(UserThirdpartyAuthInfo userThirdpartyAuthInfo);

    boolean updateUserThirdpartyAuthInfo(UserThirdpartyAuthInfo userThirdpartyAuthInfo);

    boolean saveH5ShortUrl(H5ShortUrl hh5url);

    List<H5ShortUrl> findH5ShortUrlByShortUrl(String shortUrl);

    List<WeiXinMaterial> findWeiXinMaterialByOpenIdAndType(String openId, Integer type);

    boolean saveWeiXinMaterial(WeiXinMaterial weiXinMaterial);
}
