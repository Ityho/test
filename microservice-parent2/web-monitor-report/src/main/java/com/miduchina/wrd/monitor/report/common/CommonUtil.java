package com.miduchina.wrd.monitor.report.common;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.monitor.report.pojos.IContentCommonNet;
import com.miduchina.wrd.monitor.report.pojos.Stat;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 *
 * @author liym
 */
public class CommonUtil {

	public static Map<String,Object> getData(String path) {
		Map map = new HashedMap();
		try {
//			Map<String, Object> customflagNum = JSON.parseObject(CommonFile.readTxt(path+"loadCustomflag.txt", "utf-8"), Map.class);
//			map.put("customflagNum", customflagNum);
			List<Stat> sourceTypeNum = JSON.parseArray(CommonFile.readTxt(path+"loadSourceTypeNum.txt", "utf-8"), Stat.class);
			map.put("sourceTypeNum", sourceTypeNum);
			List<IContentCommonNet> fwbList =JSON.parseArray(CommonFile.readTxt(path+"loadNotWb.txt", "utf-8"), IContentCommonNet.class);
			map.put("fwbList", fwbList);
			List<IContentCommonNet> wbList = JSON.parseArray(CommonFile.readTxt(path+"loadWb.txt", "utf-8"), IContentCommonNet.class);
			map.put("wbList", wbList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
