package com.miduchina.wrd.po.eventanalysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseView implements Serializable{
    @ApiModelProperty(value = "返回码", required = true)
    protected String code;
    private Boolean succ;
    @ApiModelProperty(value = "返回信息", required = true)
    protected String message;
    public BaseView() {
        super();
    }

    public BaseView(String code) {
        this.code = code;
        this.message = message;
    }
    public BaseView(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
