package com.miduchina.wrd.pojo;

import java.util.List;

/**
 * @Classname ResultRank
 * @Description TODO
 * @Date 2019/7/9 12:12
 * @Author ZhuFangTao
 */
public class ResultRank {
    private String code;
    private Object data;
    private List<NewRankData> list;
    private Integer maxPage;
    private String message;
    private Integer page;
    private Integer pageSize;
    private Integer totalCount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<NewRankData> getList() {
        return list;
    }

    public void setList(List<NewRankData> list) {
        this.list = list;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "ResultRank{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", list=" + list +
                ", maxPage=" + maxPage +
                ", message='" + message + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                '}';
    }
}
