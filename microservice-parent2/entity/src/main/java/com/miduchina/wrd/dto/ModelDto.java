package com.miduchina.wrd.dto;

import lombok.Data;

@Data
public class ModelDto {
    private int status = 1;
    private String msg;
    private Object obj;
    public ModelDto(int status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }
    public ModelDto() {}
    public ModelDto(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }
    public ModelDto(int status, Object obj) {
        super();
        this.status = status;
        this.obj = obj;
    }
    public ModelDto(int status) {
        super();
        this.status = status;
    }
    public ModelDto(Object obj) {
        super();
        this.obj = obj;
    }

}

