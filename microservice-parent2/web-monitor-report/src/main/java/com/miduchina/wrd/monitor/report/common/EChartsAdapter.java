package com.miduchina.wrd.monitor.report.common;

import com.miduchina.wrd.monitor.report.pojos.ECharts;
import com.miduchina.wrd.monitor.report.pojos.IGroupResult;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EChartsAdapter {
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	//private static final String[] QG_COLOR = new String[]{"#87CEFA","#FF7F50"};
	private static String []mapColors = {"#f29300","#3454a1","#277bc0","#72c1be","#9e9d9e","#ec6a3a","#d44e24","#a05623","#bb814e","#f5bc00"};
	private String type;
	private String title;
	private List<IGroupResult> list;
	private static Map<String, String> ColorMap = new HashMap<String, String>();
	public void init(){
		ColorMap.put("非敏感", "#3454a1");
		ColorMap.put("敏感", "#f29300");

		/*ColorMap.put("网站", "#cd5c5c");
		ColorMap.put("微博", "#ba55d3");
		ColorMap.put("论坛", "#ff69b4");
		ColorMap.put("微信", "#6495ed");
		ColorMap.put("博客", "#32cd32");
		ColorMap.put("外媒", "#ffb980");
		ColorMap.put("新闻", "#ff5f34");
		ColorMap.put("报刊", "#0048a1");
		ColorMap.put("视频", "#a19f00");
		ColorMap.put("政务", "#08e690");*/
	}
	public EChartsAdapter(List<IGroupResult> list, String type, String title) {
		init();
		this.list = list;
		this.type = type;
		this.title = title;
	}

	public Object[] getChart() {

		if (list == null) {
			System.out.println(
					type
							+ "：EChartAdapter --> List<IGroupResult> Objects Can Not Be Empty");
		}
		Object[] data = null;
		Object[] datetime = null;
		Object[] legend = new Object[list.size()];;
		Object[] chartArray = new Object[list.size()];
		boolean flag = true;
		ECharts charts = new ECharts();
		if ("pie".equals(type)) {
			Object[] objs = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name)){
					continue;
				}
				name = convert(name);
				Integer value = result.getTotal();
				if(value==0){
					continue;
				}
				ECharts item = new ECharts(name, value);
				//渲染用
				/*if(ColorMap.containsKey(name)){
					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, ColorMap.get(name));
					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
					item.setItemStyle(itemStyle);
				}*/
				ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, mapColors[i%10]);
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
				item.setItemStyle(itemStyle);

				objs[i] = item;
				legend[i] = name;
			}
			objs = deleteNullItem(objs);
			charts.setData(objs);
			charts.setTitle(title);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			return new ECharts[]{charts};
		}else if("bar".equals(type)){
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				List<IGroupResult> subList = result.getSubGroups();
				if(subList==null || subList.size()==0){
					subList = list;
					data=new Object[subList.size()];
					datetime=new Object[subList.size()];
					legend=new Object[subList.size()];
					//普通
					if(flag){
						for(int j = 0;j<subList.size();j++){
							String name2 = subList.get(j).getGroupValue();
							if(name2==null || "".equals(name2)){
								continue;
							}
							name2 = convert(name2);
							Integer value = subList.get(j).getTotal();
							//ECharts chart = new ECharts();
							//chart.setValue(value);
							data[j] = value;
							ECharts item = new ECharts("", value);
							ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"},mapColors[8]);
							ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
							item.setItemStyle(itemStyle);
							data[j] = item;
							datetime[j] = name2;
							legend[j] = name2;
						}
						flag = false;
						charts.setData(data);
						charts.setLegend(legend);
						charts.setDatetime(datetime);
						charts.setTitle(title);
						return new ECharts[]{charts};
					}
				}else{
					data=new Object[subList.size()];
					datetime=new Object[subList.size()];
					legend=new Object[subList.size()];

					String name = result.getGroupValue();
					if(name==null || "".equals(name)){
						continue;
					}
					name = convert(name);
					ECharts temp = new ECharts();
					temp.setName(name);
					temp.setType("bar");

					for(int k=0;k<subList.size();k++){

						String name1 = subList.get(k).getGroupValue();
						if(name1==null || "".equals(name1)){
							continue;
						}
						name1 = convert(subList.get(k).getGroupValue());
						Integer value = subList.get(k).getTotal();
						data[k] = value;
						if(i==list.size()-1){
							legend[k] = name1;
							datetime[k] = name1;
						}
					}
					if(flag){
						//temp.setLegend(legend);
						//temp.setDatetime(datetime);
					}
					temp.setData(data);
					temp.setType("bar");
					temp.setStack("数量");
					flag = false;
					chartArray[i] = temp;
				}

			}
			charts.setData(chartArray);
			charts.setDatetime(datetime);
			charts.setLegend(legend);
			charts.setTitle(title);
			return new ECharts[]{charts};
		}else if("bar2".equals(type)){
			legend=new Object[list.size()];
			Object[] topData = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				List<IGroupResult> subList = result.getSubGroups();
				if(subList==null || subList.size()==0){
					subList = list;
					data=new Object[subList.size()];
					datetime=new Object[subList.size()];

					//普通
					if(flag){
						for(int j = 0;j<subList.size();j++){
							String name2 = subList.get(j).getGroupValue();
							if(name2==null || "".equals(name2)){
								continue;
							}
							name2 = convert(name2);
							Integer value = subList.get(j).getTotal();
							//ECharts chart = new ECharts();
							//chart.setValue(value);
							data[j] = value;
							datetime[j] = name2;
							legend[j] = name2;
						}
						flag = false;
						charts.setData(data);
						charts.setLegend(legend);
						charts.setDatetime(datetime);
						charts.setTitle(title);
						return new ECharts[]{charts};
					}
				}else{
					data=new Object[subList.size()];
					datetime=new Object[subList.size()];

					String name = result.getGroupValue();
					if(name==null || "".equals(name)){
						continue;
					}
					name = convert(name);
					ECharts temp = new ECharts();
					temp.setName(name);
					temp.setType("bar");
					legend[i] = name;
					//分类中数量最多的取出来
					ECharts topTemp = new ECharts(convert(subList.get(0).getGroupValue()),subList.get(0).getTotal());
					topTemp.setLabel(name);
					topData[i] = topTemp;
					for(int k=0;k<subList.size();k++){

						String name1 = subList.get(k).getGroupValue();
						if(name1==null || "".equals(name1)){
							continue;
						}
						name1 = convert(subList.get(k).getGroupValue());
						Integer value = subList.get(k).getTotal();
						data[k] = value;
						if(i==list.size()-1){
							datetime[k] = name1;
						}
					}
					temp.setData(data);
					temp.setType("bar");
					flag = false;
					chartArray[i] = temp;
				}

			}
			charts.setData(chartArray);
			charts.setDatetime(datetime);
			charts.setLegend(legend);
			charts.setTopData(topData);
			charts.setTitle(title);
			return new ECharts[]{charts};

		}else if(type.indexOf("line")!=-1){
			int hoursL = 0;
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				List<IGroupResult> subList = result.getSubGroups();
				if(subList==null || subList.size()==0){
					subList = list;
					data=new Object[subList.size()];
					datetime=new Object[subList.size()];
					legend=new Object[subList.size()];
					//普通
					if(flag){
						for(int j = 0;j<subList.size();j++){
							String date = subList.get(j).getGroupValue();
							if(date==null || "".equals(date)){
								continue;
							}
							date = convert(subList.get(j).getGroupValue());
							Integer value = subList.get(j).getTotal();
							try {
								Date time = format.parse(date);
								hoursL+=time.getHours();
								date = String.valueOf(format.parse(date).getTime());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//ECharts chart = new ECharts();
							//chart.setValue(value);
							data[j] = value;
							datetime[j] = date;
							legend[j] = date;
						}
						flag = false;
						charts.setData(data);
						charts.setLegend(legend);
						charts.setDatetime(datetime);
						charts.setTitle(title);
						charts.setDateFormat("yyyy-MM-dd");
						if(hoursL>0){
							charts.setDateFormat("MM-dd hh:00");
						}
						return new ECharts[]{charts};
					}
				}else{
					data=new Object[subList.size()];
					datetime=new Object[subList.size()];


					String name = result.getGroupValue();
					if(name==null || "".equals(name)){
						continue;
					}
					name = convert(name);
					ECharts temp = new ECharts();
					temp.setName(name);
					temp.setType("line");

					legend[i] = name;
					for(int k=0;k<subList.size();k++){

						String date = subList.get(k).getGroupValue();
						if(date==null || "".equals(date)){
							continue;
						}

						date = convert(subList.get(k).getGroupValue());
						Integer value = subList.get(k).getTotal();
						if(value==0){
							//continue;
						}
						data[k] = value;
						try {
							Date time = format.parse(date);
							hoursL+=time.getHours();
							long dateL = time.getTime();
							if(i==list.size()-1){
								datetime[k] = dateL;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					if(flag){
						//temp.setLegend(legend);
						//temp.setDatetime(datetime);
					}
					temp.setData(data);
					temp.setType("line");
					//temp.setStack("数量");
					flag = false;
					chartArray[i] = temp;
				}

			}
			charts.setDateFormat("yyyy-MM-dd");
			if(hoursL>0){
				charts.setDateFormat("MM-dd hh:00");
			}
			charts.setData(chartArray);
			charts.setDatetime(datetime);
			charts.setLegend(legend);
			charts.setTitle(title);
			return new ECharts[]{charts};
		}else if("radar".equals(type)){
			Map<String,Integer> maptotal=new HashMap<String, Integer>();
			for (int j = 0; j < list.size(); j++) {
				IGroupResult res1=list.get(j);
				for(IGroupResult res2: res1.getSubGroups()){
					if(res2!=null){
						String val=res2.getGroupValue();
						if(!maptotal.containsKey(val)){
							maptotal.put(val,0);
						}
						maptotal.put(val,res2.getTotal()+maptotal.get(val));
					}
				}
			}

			List<IGroupResult> newList=new ArrayList<IGroupResult>();
			Iterator iterator =maptotal.keySet().iterator();
			while (iterator.hasNext()) {
				String key=iterator.next().toString();
				IGroupResult result=new IGroupResult();
				result.setTotal(maptotal.get(key));
				result.setGroupValue(key);
				newList.add(result);
			}
			/*StatCondition statConditionSORT=new StatCondition();
			Collections.sort(newList, statConditionSORT.getSortCompartor());*/
			List<IGroupResult> list_=new ArrayList<IGroupResult>();
			for (int j = 0; j < list.size(); j++) {
				IGroupResult _result=list.get(j);
				IGroupResult result=new IGroupResult();
				BeanUtils.copyProperties(_result, result);
				List<IGroupResult> list2=newList;
				List<IGroupResult> listok=new ArrayList<IGroupResult>();
				for (int k = 0; k < list2.size(); k++) {

					IGroupResult _res1=list2.get(k);
					IGroupResult res1=new IGroupResult();
					BeanUtils.copyProperties(_res1, res1);
					res1.setTotal(0);
					String val1=res1.getGroupValue();
					for(IGroupResult res2: result.getSubGroups()){
						if(res2!=null){
							String val2=res2.getGroupValue();
							if(val1.equals(val2)){
								res1.setTotal(res2.getTotal());
								break;
							}
						}
					}
					//System.out.println("j:"+j+"  "+res1.getTotal());
					listok.add(res1);

				}

				//System.out.println("j:"+j+"  "+listok.get(0).getTotal());
				result.setSubGroups(listok);
				//System.out.println("\t j:"+j+"  "+result.getSubGroups().get(0).getTotal());
				list_.add(result);

			}
			list = list_;
			Object[] indicator = null;
			Integer[] maxVal = null;
			data = new Object[list.size()];
			for(int i=0;i<list.size();i++){
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name)){
					continue;
				}
				name = convert(result.getGroupValue());

				List<IGroupResult> subList = result.getSubGroups();
				if(subList==null || subList.size()==0){
					continue;
				}
				legend[i] = name;
				ECharts c = new ECharts();
				Object[] o = new Object[subList.size()];
				indicator = new Object[subList.size()];
				datetime = new Object[subList.size()];
				if(flag){
					maxVal = new Integer[subList.size()];
				}
				for(int j=0;subList!=null &&j<subList.size();j++){
					IGroupResult gr = subList.get(j);
					String gv = gr.getGroupValue();
					gv = convert(gv);
					Integer val = gr.getTotal();
					o[j] = val;
					if(flag){
						maxVal[j] = val;
					}
					if(maxVal[j]<val){
						maxVal[j]=val;
					}
					if(i==list.size()-1){
						ECharts e = new ECharts();
						e.setText(gv);
						e.setMax(maxVal[j]);
						indicator[j] = e;
						datetime[j] = gv;
					}
				}
				c.setName(name);
				c.setValue(o);
				data[i]=c;
				flag = false;
			}
			charts.setDatetime(datetime);
			charts.setLegend(legend);
			charts.setTitle(title);
			charts.setType(type);
			charts.setData(data);
			charts.setIndicator(indicator);
			return new ECharts[]{charts};
		}else if("map".equals(type)){
			Object[] objs = new Object[list.size()];
			Object[] topData = null;
			if(list.size()>3){
				topData = new Object[3];
			}else{
				topData = new Object[list.size()];
			}
			String [] colors = new String[list.size()];
			colors = pickColor(list,1);
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name) || "外媒".equals(name)
						||"全国".equals(name)){
					continue;
				}
				name = convert(name);
				Integer value = result.getTotal();
				if(value==0){
					continue;
				}
				ECharts item = new ECharts(name, value);

				//渲染用
				ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color","borderColor"}, colors[i],"#FFFFFF");
				ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor"},"#FFFF00");
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
				item.setItemStyle(itemStyle);

				objs[i] = item;
				if(i<=2){
					legend[i] = name;
					ECharts item1 = new ECharts(name, value);
					topData[i] = item1;
				}
			}
			objs = deleteNullItem(objs);
			charts.setData(objs);
			charts.setTitle(title);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			charts.setTopData(topData);
			return new ECharts[]{charts};
		}else if("map2".equals(type)){
			Object[] objs = new Object[list.size()];
			Object[] topData = null;
			if(list.size()>10){
				topData = new Object[10];
			}else{
				topData = new Object[list.size()];
			}
			String [] colors = new String[list.size()];
			colors = pickColor(list,1);

			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name) || "外媒".equals(name)
						||"全国".equals(name)){
					continue;
				}
				name = convert(name);
				Integer value = result.getTotal();
				if(value==0){
					continue;
				}
				ECharts item = new ECharts(name, value);

				//渲染用
				ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color","borderColor"}, colors[i],"#FFFFFF");
				ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor"},"#FFFF00");
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
				item.setItemStyle(itemStyle);

				objs[i] = item;
				if(i<10){
					legend[i] = name;
					ECharts item1 = new ECharts(name, value);
					topData[i] = item1;
				}
			}
			objs = deleteNullItem(objs);
			charts.setData(objs);
			charts.setTitle(title);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			charts.setTopData(topData);
			return new ECharts[]{charts};
		}

		return null;
	}

	public static String convert(String gv) {
		return "wz".equals(gv) ? "网站" : "wb".equals(gv) ? "微博" : "lt".equals(gv) ? "论坛" : "jw".equals(gv) ? "外媒" : "bk".equals(gv) ? "博客" : "wx".equals(gv) ? "微信" : "zw".equals(gv) ? "政务" : "sp".equals(gv) ? "视频" : "baokan".equals(gv) ? "报刊" : "xw".equals(gv) ? "新闻" : "app".equals(gv) ? "客户端" : "all".equals(gv) ? "全部" : "0".equals(gv) ? "全部" : "1".equals(gv) ? "敏感" : "2".equals(gv) ? "敏感" : "3".equals(gv) ? "敏感" : "4".equals(gv) ? "非敏感" : gv;
	}

	public Object[] deleteNullItem(Object[] objs){
		List resList = new ArrayList();
		for(Object obj:objs){
			if(obj!=null){
				resList.add(obj);
			}
		}
		objs = resList.toArray();
		resList = null;
		return objs;
	}
	public String[] pickColor(List<IGroupResult> gr,int type){
		if(gr==null || gr.size()==0){
			return null;
		}
		String [] colors = new String[gr.size()];
		for (int i = 0; i < gr.size(); i++) {
			int total = gr.get(i).getTotal();
			if(type==1){
				if(total== 1){
					colors[i] = mapColors[0];
				}else if(total== 2 ){
					colors[i] = mapColors[1];
				}else if(total>= 3 && total < 6){
					colors[i] = mapColors[2];
				}else if(total>= 6 && total< 11){
					colors[i] = mapColors[3];
				}else if(total>= 11 && total< 16){
					colors[i] = mapColors[4];
				}else if(total>= 16 && total< 21){
					colors[i] = mapColors[5];
				}else if(total >= 21 && total < 101 ){
					colors[i] = mapColors[6];
				}else if(total>= 101){
					colors[i] = mapColors[7];
				}
			}else if(type==2){
				if(total >= 1 && total < 11){
					colors[i] = mapColors[0];
				}else if(total >= 11 && total <21 ){
					colors[i] = mapColors[1];
				}else if(total>= 21 && total < 51){
					colors[i] = mapColors[2];
				}else if(total>= 51 && total< 101){
					colors[i] = mapColors[3];
				}else if(total>= 101 && total< 151){
					colors[i] = mapColors[4];
				}else if(total>= 151 && total< 201){
					colors[i] = mapColors[5];
				}else if(total >= 201 && total < 501 ){
					colors[i] = mapColors[6];
				}else if(total>= 501){
					colors[i] = mapColors[7];
				}
			}
		}

		return colors;
	}


	/**
	 * 日期（时间）的数据补齐
	 * @param list	补齐前数据
	 * @param isHourSeach true-按小时，false-按天
	 * @param startDate	开始时间
	 * @param endDate	结束时间
	 * @param type 维度
	 * @return
	 */
	public static List<IGroupResult> buqiDate(List<IGroupResult> list,boolean isHourSeach,Date startDate,Date endDate,int type){
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		long miunsL = 1 * 24 * 60 * 60; // 1天
		long startDateL = startDate.getTime() / 1000;
		long endDateL = endDate.getTime() / 1000;
		long currDateL = System.currentTimeMillis()/1000;
		//如果结束时间大于当前时间，则画图的结束时间为当前时间
		if(endDateL>currDateL){
			endDateL = currDateL;
		}
		if (isHourSeach) {
			miunsL = 3600;
		}
		long days = (endDateL - startDateL) / miunsL;

		Map<String, Integer> dateMap = new HashMap();
		long tempStartDateL = startDateL;
		// 计算日期的数据位置
		for (int j = 0; j <= days; j++) {
			Date tDate = new Date(tempStartDateL * 1000);
			String value = formatDate.format(tDate);
			dateMap.put(value, j);
			tempStartDateL = tempStartDateL + miunsL;
		}

		if(type==1){
			// 先补0
			List<IGroupResult> rlist = new ArrayList<IGroupResult>();
			// 如果没有某个来源数据，则补每天的总数0
			tempStartDateL = startDateL;
			for (int j = 0; j <= days; j++) {
				IGroupResult r = new IGroupResult();
				Date tDate = new Date(tempStartDateL * 1000);
				r.setGroupValue(formatDate.format(tDate));
				r.setTotal(0);
				rlist.add(r);
				tempStartDateL = tempStartDateL + miunsL;
			}
			//循环list
			for (IGroupResult igr : list) {
				String groupV = igr.getGroupValue();
				//防止因抓去时间为空
				if(groupV==null || "".equals(groupV)){
					continue;
				}
				if (isHourSeach) {
					groupV += ":00:00";
				} else {
					groupV += " 00:00:00";
				}
//				igr.setGroupValue(groupV);
				Integer pos = dateMap.get(groupV);
				if(pos==null){
					continue;
				}
				IGroupResult temp = rlist.get(pos);
				temp.setTotal(igr.getTotal());
			}
			return rlist;
		}else if(type==2){
			for (IGroupResult igr : list) {
				// 先补0
				List<IGroupResult> rlist = new ArrayList<IGroupResult>();
				// 如果没有某个来源数据，则补每天的总数0
				tempStartDateL = startDateL;
				for (int j = 0; j <= days; j++) {
					IGroupResult r = new IGroupResult();
					Date tDate = new Date(tempStartDateL * 1000);
					r.setGroupValue(formatDate.format(tDate));
					r.setTotal(0);
					rlist.add(r);
					tempStartDateL = tempStartDateL + miunsL;
				}
				// 缺失的数据需要补0
				List<IGroupResult> subGroups = igr.getSubGroups();
				if (!CollectionUtils.isEmpty(subGroups)) {
					for (IGroupResult igr1 : subGroups) {
						String groupV = igr1.getGroupValue();
						//防止因抓去时间为空
						if(groupV==null || "".equals(groupV)){
							continue;
						}
						if (isHourSeach) {
							groupV += ":00:00";
						} else {
							groupV += " 00:00:00";
						}
						Integer pos = dateMap.get(groupV);
						if(pos==null){
							continue;
						}
						IGroupResult temp = rlist.get(pos);
						temp.setTotal(igr1.getTotal());
					}
				}
				igr.setSubGroups(rlist);
			}

		}
		return list;
	}

}

