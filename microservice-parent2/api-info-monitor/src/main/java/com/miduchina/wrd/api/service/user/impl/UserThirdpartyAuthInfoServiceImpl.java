package com.miduchina.wrd.api.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.service.user.UserThirdpartyAuthInfoService;

import com.miduchina.wrd.api.mapper.user.UserThirdpartyAuthInfoMapper;
import com.miduchina.wrd.po.h5.H5ShortUrl;
import com.miduchina.wrd.po.h5.WeiXinMaterial;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0.0
 * @ClassName: UserThirdpartyAuthInfoServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:00 AM
 */
@Service
public class UserThirdpartyAuthInfoServiceImpl implements UserThirdpartyAuthInfoService {

    @Resource
    private UserThirdpartyAuthInfoMapper userThirdpartyAuthInfoMapper;



    @Override
    public UserThirdpartyAuthInfo queryOneUserThirdpartyAuth(String userId) {
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("userId",userId);
        JSONObject jsonObject=new JSONObject(objectMap);
        List<UserThirdpartyAuthInfo> thirdpartyAuthInfoList=userThirdpartyAuthInfoMapper.selectOneUserThirdpartyAuth(jsonObject);
        if(thirdpartyAuthInfoList!=null && thirdpartyAuthInfoList.size()>0){
            return thirdpartyAuthInfoList.get(0);
        }
        return null;
    }

    @Override
    public UserThirdpartyAuthInfo queryUserThirdpartyAuthInfoByUid(UserThirdpartyAuthInfo userThirdpartyAuthInfo) {
        return userThirdpartyAuthInfoMapper.selectUserThirdpartyAuthInfoByUid(userThirdpartyAuthInfo.getThirdpartyUserId());
    }

    @Override
    public boolean doSaveUserThirdpartyAuthInfo(UserThirdpartyAuthInfo userThirdpartyAuthInfo) {
        return userThirdpartyAuthInfoMapper.insertUserThirdpartyAuthInfo(userThirdpartyAuthInfo);
    }

    @Override
    public boolean doModifyUserThirdpartyAuthInfo(UserThirdpartyAuthInfo userThirdpartyAuthInfo) {
        return userThirdpartyAuthInfoMapper.updateUserThirdpartyAuthInfo(userThirdpartyAuthInfo);
    }

    @Override
    public boolean saveH5ShortUrl(H5ShortUrl hh5url) {
        return userThirdpartyAuthInfoMapper.saveH5ShortUrl(hh5url);
    }

    @Override
    public H5ShortUrl findH5ShortUrlByShortUrl(String shortUrl) {
        List<H5ShortUrl> h5ShortUrlByShortUrl = userThirdpartyAuthInfoMapper.findH5ShortUrlByShortUrl(shortUrl);
        if(h5ShortUrlByShortUrl!=null && h5ShortUrlByShortUrl.size()>0){
            return h5ShortUrlByShortUrl.get(0);
        }
        return null;
    }

    @Override
    public WeiXinMaterial findWeiXinMaterialByOpenIdAndType(String openId, Integer type) {
        List<WeiXinMaterial> WeiXinMaterial = userThirdpartyAuthInfoMapper.findWeiXinMaterialByOpenIdAndType(openId,type);
        if(WeiXinMaterial!=null && WeiXinMaterial.size()>0){
            return WeiXinMaterial.get(0);
        }
        return null;
    }

    @Override
    public boolean saveWeiXinMaterial(WeiXinMaterial weiXinMaterial) {
        return userThirdpartyAuthInfoMapper.saveWeiXinMaterial(weiXinMaterial);
    }
}
