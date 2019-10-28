package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.po.h5.H5ShortUrl;
import com.miduchina.wrd.po.h5.WeiXinMaterial;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;

/**
 * @version v1.0.0
 * @ClassName: UserThirdpartyAuthInfoService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 9:59 AM
 */
public interface UserThirdpartyAuthInfoService {

    /**
     * 获取第三方登录信息
     * */
    UserThirdpartyAuthInfo queryOneUserThirdpartyAuth(String userId);

    /**
     * 根据UID获取用户授权信息
     *
     * @param userThirdpartyAuthInfo
     * @return
     */
    UserThirdpartyAuthInfo queryUserThirdpartyAuthInfoByUid(UserThirdpartyAuthInfo userThirdpartyAuthInfo);

    /**
     * 保存用户授权信息
     *
     * @param userThirdpartyAuthInfo
     * @return
     */
    boolean doSaveUserThirdpartyAuthInfo(UserThirdpartyAuthInfo userThirdpartyAuthInfo);

    /**
     * 更新授权信息
     *
     * @param userThirdpartyAuthInfo
     * @return
     */
    boolean doModifyUserThirdpartyAuthInfo(UserThirdpartyAuthInfo userThirdpartyAuthInfo);

    boolean saveH5ShortUrl(H5ShortUrl hh5url);

    H5ShortUrl findH5ShortUrlByShortUrl(String shortUrl);

    WeiXinMaterial findWeiXinMaterialByOpenIdAndType(String openId, Integer type);

    boolean saveWeiXinMaterial(WeiXinMaterial weiXinMaterial);
}
