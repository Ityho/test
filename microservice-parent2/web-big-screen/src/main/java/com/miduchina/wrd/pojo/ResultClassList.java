package com.miduchina.wrd.pojo;

/**
 * @Classname ResultClassList
 * @Description TODO
 * @Date 2019/7/9 17:50
 * @Author ZhuFangTao
 */
public class ResultClassList {
    private String code;
    private NewClassList2 data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NewClassList2 getData() {
        return data;
    }

    public void setData(NewClassList2 data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultClassList{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
