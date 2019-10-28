package com.miduchina.wrd.api.service.order.impl;

import com.miduchina.wrd.api.listener.SysConfig;
import com.miduchina.wrd.api.mapper.order.PackgeManagentMapper;
import com.miduchina.wrd.api.mapper.order.ProductPackageMapper;
import com.miduchina.wrd.api.service.order.ProductPackageService;
import com.miduchina.wrd.po.order.PackgeManagent;
import com.miduchina.wrd.po.order.ProductPackage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: ProductPackageServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:17 PM
 */
@Service
public class ProductPackageServiceImpl implements ProductPackageService{

    @Resource
    private ProductPackageMapper productPackageMapper;

    @Resource
    private PackgeManagentMapper packgeManagentMapper;

    @Override
    public List<ProductPackage> queryAllProductPackages() {
        return productPackageMapper.selectAllProductPackages();
    }

    @Override
    public void initProductPackage() {
        List<ProductPackage> productPackages = queryAllProductPackages();
        if (productPackages != null && productPackages.size() > 0) {
            SysConfig.packages = productPackages;
        }
    }

    @Override
    public ProductPackage queryProductPackageById(int packageId) {
        if (SysConfig.packages != null && SysConfig.packages.size() > 0) {
            for (ProductPackage productPackage : SysConfig.packages) {
                if (productPackage.getProductPackageId() == packageId) {
                    return productPackage;
                }
            }
        }
        return null;
    }

    @Override
    public PackgeManagent queryCustomMade(Integer customMadeId) {
        PackgeManagent packgeManagent = packgeManagentMapper.queryCustomMadeById(customMadeId);
        if (packgeManagent !=null){
            return packgeManagent;
        }else {
            return null;
        }
    }
}
