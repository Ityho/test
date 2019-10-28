package com.miduchina.wrd.api.mapper.order;

import com.miduchina.wrd.po.order.PackgeManagent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: PackgeManagentMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/19 12:18 PM
 */
@Mapper
public interface PackgeManagentMapper {

    PackgeManagent queryCustomMadeById(Integer customMadeId);
}
