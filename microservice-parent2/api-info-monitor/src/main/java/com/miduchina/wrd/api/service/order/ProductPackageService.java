package com.miduchina.wrd.api.service.order;

import com.miduchina.wrd.po.order.PackgeManagent;
import com.miduchina.wrd.po.order.ProductPackage;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: ProductPackageService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:17 PM
 */
public interface ProductPackageService {

    /**
     * 查询所有产品列表
     *
     * @return
     */
    List<ProductPackage> queryAllProductPackages();

    /**
     * 初始化产品包
     */
    void initProductPackage();

    /**
     * 根据ID获取产品包
     *
     * @param packageId
     * @return
     */
    ProductPackage queryProductPackageById(int packageId);

    /**
     * 根据ID获取自定义套餐包
     *
     * @param customMadeId
     * @return
     */
    PackgeManagent queryCustomMade(Integer customMadeId);
}
