package com.miduchina.wrd.dto.log;

public class OperateLogKeywordInfo {
    private Integer keywordId; // 监测方案ID
    private Integer userId; // 用户ID
    private String keywordName; // 监测方案名称
    private String keyword; // 监测方案内容
    private String inputKeyword; // 用户输入内容
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String keyword4;
    private String inputFilterKeyword; // 用户输入过滤内容
    private String filterKeyword; // 过滤内容
    private Integer keywordType; // 监测方案类型
    private String validEndDate; // 有效期
    private Integer status; // 状态
    private Integer monitorType; // 引导类型
    public Integer getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Integer keywordId) {
        this.keywordId = keywordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getInputKeyword() {
        return inputKeyword;
    }

    public void setInputKeyword(String inputKeyword) {
        this.inputKeyword = inputKeyword;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(String keyword4) {
        this.keyword4 = keyword4;
    }

    public String getInputFilterKeyword() {
        return inputFilterKeyword;
    }

    public void setInputFilterKeyword(String inputFilterKeyword) {
        this.inputFilterKeyword = inputFilterKeyword;
    }

    public String getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(String filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    public Integer getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(Integer keywordType) {
        this.keywordType = keywordType;
    }

    public String getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(String validEndDate) {
        this.validEndDate = validEndDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
