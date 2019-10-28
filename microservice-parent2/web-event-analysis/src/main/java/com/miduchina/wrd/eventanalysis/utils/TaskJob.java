package com.miduchina.wrd.eventanalysis.utils;


import com.miduchina.wrd.dto.analysis.weiboanalysis.StatList;
import com.miduchina.wrd.po.eventanalysis.weiboevent.Stat;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TaskJob {

    public static String genaretPathData(StatList icc){
        Stat icn = icc.getStat();
        List<StatList> icl = icc.getStatListList();
        StringBuilder node = new StringBuilder();
        String name = icn.getName();
        if(icn.getType()==2){
            name += "（首发）";
        }
        node.append("{name:'"+name+"',");
        node.append("label:'"+name+"',");
        node.append("value: 10,symbolSize: 15,itemStyle: {normal: {color: '#f29300',label: {show: true,position: 'right'}},");
        node.append("emphasis: {label: {show: false},borderWidth: 0}}");
        if(CollectionUtils.isNotEmpty(icl)){
            node.append(",children:[");
            for(StatList iccnl:icl){
                node.append(genaretPathData(iccnl));
            }
            node.append("]},");
            return node.toString();
        }else{
            node.append(",children:[]},");
            return node.toString();
        }
    }
}