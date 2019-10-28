package com.miduchina.wrd.api.mapper.order;

import com.miduchina.wrd.po.order.OrderExportRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 15:41
 */
@Mapper
public interface OrderExportRelationMapper {
    List<OrderExportRelation> findOrderExportStatus(String userId);

    Boolean saveOrUpdateOrderExport(OrderExportRelation oer);
}
