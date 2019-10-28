package com.miduchina.wrd.dto;

import com.miduchina.wrd.CodeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @version v1.0.0
 * @ClassName: BaseDto
 * @Description: 通用返回结果类
 * @author: sty
 * @date: 2019/4/23 12:17 PM
 */
@Data
public class BaseDto<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "提示码")
    private String code;
    @ApiModelProperty(value = "提示")
    private String message;
    @ApiModelProperty(value = "数据")
    private T data;

    public BaseDto<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public BaseDto<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public BaseDto<T> setCodeMessage(String code) {
        this.code = code;
        this.message = CodeConstant.MSG_MAP.get(code);
        return this;
    }

    public BaseDto<T> setData(T data) {
        this.data = data;
        return this;
    }
}
