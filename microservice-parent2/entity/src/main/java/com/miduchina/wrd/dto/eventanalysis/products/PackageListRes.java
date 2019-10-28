package com.miduchina.wrd.dto.eventanalysis.products;

import com.miduchina.wrd.po.eventanalysis.BaseRes;

import java.util.List;

public class PackageListRes extends BaseRes {
    private static final long serialVersionUID = 2920894309727878878L;
    private List<PackageVO> packageList;

    public List<PackageVO> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageVO> packageList) {
        this.packageList = packageList;
    }
}
