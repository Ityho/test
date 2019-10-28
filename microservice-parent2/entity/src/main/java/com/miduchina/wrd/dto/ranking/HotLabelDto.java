package com.miduchina.wrd.dto.ranking;

import com.miduchina.wrd.po.ranking.HotLabel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: HotLabelDto
 * @Description: 标签
 * @author: 许双龙
 * @date: 2018年11月7日 下午9:00:17
 */
@Data
public class HotLabelDto {

    @ApiModelProperty(value = "编号")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "父编号")
    private Integer parentId;

    private List<HotLabelDto> childrens;
}
