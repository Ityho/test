package com.miduchina.wrd.dto;

import com.miduchina.wrd.CodeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: PageDto
 * @Description: 通用返回结果分页类
 * @author: sty
 * @date: 2019/4/23 12:17 PM
 */
@Data
@SuppressWarnings("unchecked")
public class PageDto<T> extends BaseDto{

    @ApiModelProperty(value = "最大页数")
    private Integer maxPage;
    @ApiModelProperty(value = "当前页")
    private Integer page;
    @ApiModelProperty(value = "页大小")
    private Integer pageSize;
    @ApiModelProperty(value = "总数")
    private Long totalCount;
    @ApiModelProperty(value = "集合")
    private List<T> list;

    @Override
    public PageDto<T> setCode(String code) {
        super.setCode(code);
        return this;
    }

    @Override
    public PageDto<T> setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public PageDto<T> setCodeMessage(String code) {
        super.setCode(code);
        super.setMessage(CodeConstant.MSG_MAP.get(code));
        return this;
    }

    @Override
    public PageDto<T> setData(Object data) {
        super.setData(data);
        return this;
    }

    public PageDto<T> setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
        return this;
    }

    public PageDto<T> setPage(Integer page) {
        this.page = page;
        return this;
    }

    public PageDto<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageDto<T> setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }
    public PageDto<T> setList(List<T> list) {
        this.list = list;
        return this;
    }
}