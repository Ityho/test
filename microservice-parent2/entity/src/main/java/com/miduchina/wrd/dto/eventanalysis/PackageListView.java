package com.miduchina.wrd.dto.eventanalysis;

import com.miduchina.wrd.po.eventanalysis.BaseView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class PackageListView extends BaseView {
    private static final long serialVersionUID = -2110249262383684057L;

    private List<ProductPackage> packageList; // 产品列表


}