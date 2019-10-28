package com.miduchina.wrd.api.service.order;

/**
 * Created by shitao on 2019-05-09 20:58.
 *
 * @author shitao
 */
public interface CartRecordService {
    /**
     * 获取单个关键词
     * @see #queryCount(Integer)
     * @param userId 查询参数
     * */
    Integer queryCount(Integer userId);

}
