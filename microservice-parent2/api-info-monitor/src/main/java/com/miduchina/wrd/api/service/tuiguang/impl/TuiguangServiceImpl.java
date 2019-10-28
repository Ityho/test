package com.miduchina.wrd.api.service.tuiguang.impl;

import com.miduchina.wrd.api.mapper.tuiguang.TuiguangMapper;
import com.miduchina.wrd.api.service.tuiguang.TuiguangService;
import com.miduchina.wrd.po.tuiguang.Tuiguang;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther Administrator
 * @vreate 2019-05 10:24
 */
@Service
public class TuiguangServiceImpl implements TuiguangService {

    @Resource
    private TuiguangMapper tuiguangMapper;
    @Override
    public boolean doSaveRecord(Tuiguang tuiguang) {
        boolean bul=true;
        if (tuiguang == null){
            bul= false;
        }else{
            bul=  tuiguangMapper.insert(tuiguang);
        }
        return bul;
    }
}
