package com.miduchina.wrd.api.mapper.order;

import com.miduchina.wrd.po.order.ProductPackage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: ProductPackageMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:21 PM
 */
@Mapper
public interface ProductPackageMapper {

    List<ProductPackage> selectAllProductPackages();

    ProductPackage selectProductPackageById(int packageId);
}
