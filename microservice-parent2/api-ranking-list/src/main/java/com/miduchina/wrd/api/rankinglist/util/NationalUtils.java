package com.miduchina.wrd.api.rankinglist.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.miduchina.wrd.dto.ranking.AbilityDto;
import com.miduchina.wrd.po.ranking.StatisData;
import org.apache.commons.collections.CollectionUtils;


public class NationalUtils extends org.springframework.beans.BeanUtils {
	
	public static List<AbilityDto> getRankingList(List<StatisData> items){
		LinkedHashMap<String, AbilityDto> map = new LinkedHashMap<String, AbilityDto>();
		List<AbilityDto> AbilityDtoList = new ArrayList<AbilityDto>();
		for(StatisData statisData : items){
			String serviceUnit = statisData.getServiceUnit();
			AbilityDto vo = map.get(serviceUnit);
			if(vo == null){
				vo = new AbilityDto(serviceUnit);
			}
			if(statisData.getOriginType() != null){
				//统计各力榜数值（综合力榜）
//				if(abilityType == 0) {
//					statisData.statisAbilityVal();
//				}
				if(statisData.getOriginType() == 1){
					vo.setWbData(statisData);
				}else if(statisData.getOriginType() == 2){
					vo.setWxData(statisData);
				}else if(statisData.getOriginType() == 3){
					vo.setPcData(statisData);
				}else if(statisData.getOriginType() == 4){
					vo.setAppData(statisData);
				}else {
					System.out.println("-----------");
				}
			}
				map.put(serviceUnit.trim(), vo);
		}
		
		for(String serviceUnit : map.keySet()){
			AbilityDtoList.add(map.get(serviceUnit));
		}
		for(AbilityDto av : AbilityDtoList){
			Long cb = av.getPcData().getReferPresidentNum()+av.getPcData().getReferGovAffairsNum()+av.getPcData().getSocietyPositiveEnergyNum()+av.getPcData().getPolicyUnscrambleNum()+av.getPcData().getReprintedIndex()+av.getPcData().getAlexaValue()+av.getPcData().getRightNum()
			+av.getWxData().getReferPresidentNum()+av.getWxData().getReferGovAffairsNum()+av.getWxData().getSocietyPositiveEnergyNum()+av.getWxData().getPolicyUnscrambleNum()+av.getWxData().getRightNum()
			+av.getAppData().getReferPresidentNum()+av.getAppData().getReferGovAffairsNum()+av.getAppData().getSocietyPositiveEnergyNum()+av.getAppData().getPolicyUnscrambleNum()+av.getAppData().getTotalDownloadNum()+av.getAppData().getMonthDownloadNum()+av.getAppData().getRightNum()+
			av.getPcData().getReferPresidentOriginNum()+ av.getWxData().getReferPresidentOriginNum()+ av.getAppData().getReferPresidentOriginNum();
			Long gx = (long) (av.getPcData().getPcPer()+av.getPcData().getReferEntNum()+av.getPcData().getRumourNum()+av.getPcData().getDistortHistoryAndDiscreditHeroNum()+
					av.getWxData().getWxPer()+av.getWxData().getReferEntNum()+av.getWxData().getRumourNum()+av.getWxData().getDistortHistoryAndDiscreditHeroNum()+
					av.getAppData().getAppPer()+av.getAppData().getReferEntNum()+av.getAppData().getRumourNum()+av.getAppData().getDistortHistoryAndDiscreditHeroNum()+
					av.getWbData().getWbPer()+av.getWbData().getReferEntNum()+av.getWbData().getRumourNum()+av.getWbData().getDistortHistoryAndDiscreditHeroNum());
			Long yd = (long) (av.getWxData().getAttitudesNum()+av.getWxData().getCommentsRate()+
					av.getWbData().getCommentsNum()+av.getWbData().getCommentsRate()+av.getWbData().getAttitudesNum()+av.getWbData().getAttitudesRate()+av.getWbData().getRepostsNum()+av.getWbData().getRepostsRate());
			Long yx = (long) (av.getPcData().getPolicyUnscrambleNum()+
					av.getWxData().getReferPresidentNum()+av.getWxData().getReferGovAffairsNum()+av.getWxData().getReadsNum()+av.getWxData().getReadsRate()+
					av.getWbData().getReadsNum()+
					av.getWxData().getReferPresidentOriginNum());
			System.out.println("============");
			System.out.println(yx);
			if(av.getWxData().getTotalNum()>0) {
				System.out.println(av.getWxData().getReadsNum()/av.getWxData().getTotalNum());
				yx += (av.getWxData().getReadsNum()/av.getWxData().getTotalNum());
			}
			System.out.println(yx);
			av.setAbilityCB(cb);
			av.setAbilityGX(gx);
			av.setAbilityYD(yd);
			av.setAbilityYX(yx);
			av.setAbilityTotal(cb+gx+yd+yx);
		}
		return AbilityDtoList;
	}
	
	public static String getLastMonth(String str) {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMM");
		 Calendar calendar = Calendar.getInstance();    
		 try {
			calendar.setTime(sdf.parse(str));
			calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
			Date time = calendar.getTime();//获取一年前的时间，或者一个月前的时间
			return sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}    
		 return null;
	}
	
	public static List<AbilityDto> getLastList(List<AbilityDto> AbilityDtoList,List<AbilityDto> AbilityDtoList2){
		if(CollectionUtils.isEmpty(AbilityDtoList) || CollectionUtils.isEmpty(AbilityDtoList2)){
			return AbilityDtoList;
		}
		for(int i=0;i<AbilityDtoList.size();i++){
			AbilityDto a1 = AbilityDtoList.get(i);
			AbilityDto a2 = AbilityDtoList2.get(i);
			
			a1.setDifferTotal(a2.getAbilityTotal());
			a1.setDifferCB(a2.getAbilityCB());
			a1.setDifferGX(a2.getAbilityGX());
			a1.setDifferYD(a2.getAbilityYD());
			a1.setDifferYX(a2.getAbilityYX());
		}
		
		return AbilityDtoList;
	}

}
