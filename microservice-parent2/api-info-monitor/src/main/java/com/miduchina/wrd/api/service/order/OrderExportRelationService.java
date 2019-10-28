package com.miduchina.wrd.api.service.order;

import com.miduchina.wrd.po.order.OrderExportRelation;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 15:40
 */
public interface OrderExportRelationService {
    List<OrderExportRelation> findOrderExportStatus(String str);

    Boolean saveOrUpdateOrderExport(OrderExportRelation oer);
}
