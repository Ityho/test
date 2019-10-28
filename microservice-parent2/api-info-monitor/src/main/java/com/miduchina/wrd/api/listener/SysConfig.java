package com.miduchina.wrd.api.listener;

import com.miduchina.wrd.po.order.ProductPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: SysConfig
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:24 PM
 */
public class SysConfig extends com.miduchina.wrd.common.redis.util.SysConfig{

    public static List<ProductPackage> packages = new ArrayList<>();
}
