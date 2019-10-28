package com.miduchina.wrd.api.service.order.impl;

import com.miduchina.wrd.api.mapper.order.OrderExportRelationMapper;
import com.miduchina.wrd.api.service.order.OrderExportRelationService;
import com.miduchina.wrd.po.order.OrderExportRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 15:41
 */
@Service
public class OrderExportRelationServiceImpl implements OrderExportRelationService{
    @Resource
    private OrderExportRelationMapper orderExportRelationMapper;
    @Override
    public List<OrderExportRelation> findOrderExportStatus(String userId) {
        return orderExportRelationMapper.findOrderExportStatus(userId);
    }

    @Override
    public Boolean saveOrUpdateOrderExport(OrderExportRelation oer) {
        return orderExportRelationMapper.saveOrUpdateOrderExport(oer);
    }

}
