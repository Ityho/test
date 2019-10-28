package com.miduchina.wrd.eventanalysis.echart;


import com.miduchina.wrd.dto.eventanalysis.products.IGroupResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class EChartsAdapter implements Serializable {
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	//private static final String[] QG_COLOR = new String[]{"#87CEFA","#FF7F50"};
	private static String []mapColors = {"#f29300","#3454a1","#277bc0","#72c1be","#9e9d9e","#ec6a3a","#d44e24","#a05623","#bb814e","#f5bc00","#d2b539"};
	private String type;
	private String title;
	private List<IGroupResult> list;
	private static Map<String, String> ColorMap = new HashMap<String, String>();
	public void init(){
		ColorMap.put("非敏感", "#87CEFA");
		ColorMap.put("敏感", "#FF7F50");
		ColorMap.put("境内","#32cf78");
		ColorMap.put("境外","#f5613d");

		/*ColorMap.put("网站", "#f29300");
		ColorMap.put("微博", "#3454a1");
		ColorMap.put("论坛", "#277bc0");
		ColorMap.put("微信", "#9e9d9e");
		ColorMap.put("博客", "#ec6a3a");
		ColorMap.put("外媒", "#d44e24");
		ColorMap.put("新闻", "#bb814e");
		ColorMap.put("报刊", "#a05623");
		ColorMap.put("视频", "#d2b539");
		ColorMap.put("政务", "#f5bc00");*/
	}
	public EChartsAdapter(List<IGroupResult> list, String type, String title) {
		init();
		this.list = list;
		this.type = type;
		this.title = title;
	}

	public Object[] getChart()  {
		format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		if (list == null || list.size()==0) {
			System.out.println(type+ "：EChartAdapter --> List<IGroupResult> Objects Can Not Be Empty");
		}
		Object[] data = null;
		Object[] datetime = null;
		Object[] legend = new Object[list.size()];;
		Object[] chartArray = new Object[list.size()];
		boolean flag = true;
		ECharts charts = new ECharts();
		if (type.equals("pie")||type.indexOf("eventpie")!=-1||type.equals("eventbar3")||type.equals("pie1")||type.equals("eventpie3")) {
			Object[] objs = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name)){
					continue;
				}
				name = convert(name);
/*				Integer value = result.getTotal();
				if(value==0){
					continue;
				}*/
				ECharts item = null;
				if(type.equals("eventpie2")){
					Double value = result.getCalculatedValue();
					item = new ECharts(name, value);
				}else{
					Integer value = new Long(result.getTotal()).intValue();
					if(value==0){
						continue;
					}
					item = new ECharts(name, value);
				}
				//渲染用
				/*
				if(ColorMap.containsKey(name)){
					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, ColorMap.get(name));
					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
					item.setItemStyle(itemStyle);
				}/*else{
                    ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, mapColors[i%10]);
                    ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
                    item.setItemStyle(itemStyle);
                }*/
				ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, mapColors[i%11]);
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
				item.setItemStyle(itemStyle);
				if(type.equals("eventbar3")){//事件分析观点分析用
					objs[list.size()-1-i] = item;
					legend[list.size()-1-i] = name;
				}else{
					objs[i] = item;
					legend[i] = name;
				}
			}
			objs = deleteNullItem(objs);
			charts.setData(objs);
			charts.setTitle(title);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			return new ECharts[]{charts};
		}else if(type.equals("bar")||type.indexOf("eventbar")!=-1||type.equals("mediaBar")||type.equals("eventbar2")){
			String colors = "";
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
							if("eventbar".equals(type)&&j>2){
								String color = "#f29300";
								if(j>6){
									color ="#72c1be";
								}
								ECharts item = new ECharts("", value);
								ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"},color);
								ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
								item.setItemStyle(itemStyle);
								data[j] = item;
							}else if("mediaBar".equals(type)){
								if(mapColors.length>j){
									colors = mapColors[j];
								}
								ECharts item = new ECharts("", value);
								ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"},colors);
								ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
								item.setItemStyle(itemStyle);
								data[j] = item;
							}else{
								ECharts item = new ECharts("", value);
								ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"},mapColors[8]);
								ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
								item.setItemStyle(itemStyle);
								data[j] = item;
							}
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
					if(!type.equals("eventbar2")){
						temp.setStack("数量");
					}
					temp.setValue(result.getTotal());
					flag = false;
					chartArray[i] = temp;
				}

			}
			charts.setData(chartArray);
			charts.setDatetime(datetime);
			charts.setLegend(legend);
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
							date = convert(date);
							Integer value = subList.get(j).getTotal();
							try {
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
					IGroupResult igrTop =  getTopResult(subList);
					ECharts tempTop = null;


					if(igrTop!=null){
						if("eventline2".equals(type)){
							tempTop = new ECharts(igrTop.getGroupValue(), (double)igrTop.getTotal()/1000);
						}else {
							tempTop = new ECharts(igrTop.getGroupValue(), igrTop.getTotal());
						}
					}
					/*if(igrTop!=null){
						tempTop = new ECharts(igrTop.getGroupValue(),igrTop.getTotal());
					}*/
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
						if("line3".equals(type)){
							data[k] = value*(-1);
						}else if("eventline2".equals(type)){
							data[k] = (double)value/1000;
						}else{
							data[k] = value;
						}
						try {
							if(date!=null && !date.equals("")){
								System.out.println("date---"+date);
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date time = format.parse(date);
								hoursL+=time.getHours();
								long dateL = time.getTime();
								if(i==list.size()-1){
									datetime[k] = dateL;
								}
							}



						} catch (ParseException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
						if(datetime[k]==null){//事件分析-网站统计
							datetime[k] = date;
						}

					}
					if(flag){
						//temp.setLegend(legend);
						//temp.setDatetime(datetime);
					}


					temp.setData(data);
					temp.setLabel(tempTop);
					temp.setType("line");
					//temp.setStack("数量");
					flag = false;

					if("eventline1".equals(type)||"eventline2".equals(type)){
						ECharts type = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"type"},"default");
						ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"areaStyle"}, type);
						ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
						temp.setItemStyle(itemStyle);
					}

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
			//StatCondition statConditionSORT=new StatCondition();
			//Collections.sort(newList, statConditionSORT.getSortCompartor());
			List<IGroupResult> list_=new ArrayList<IGroupResult>();
			for (int j = 0; j < list.size(); j++) {
				IGroupResult _result=list.get(j);
				IGroupResult result=new IGroupResult();
				try {
					com.miduchina.wrd.util.BeanUtils.copyProperties(_result, result);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				List<IGroupResult> list2=newList;
				List<IGroupResult> listok=new ArrayList<IGroupResult>();
				for (int k = 0; k < list2.size(); k++) {

					IGroupResult _res1=list2.get(k);
					IGroupResult res1=new IGroupResult();
					try {
						com.miduchina.wrd.util.BeanUtils.copyProperties(_res1, res1);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
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
			Object[] topData = new Object[list.size()];
			for(int j=0;j<list.size();j++){
				IGroupResult igr  = list.get(j);
				String keyName = igr.getGroupValue();
				legend[j] = keyName;
				List<IGroupResult> subList = igr.getSubGroups();
				String [] colors = new String[list.size()];
				colors = pickColor(list,1);
				Object[] subObjs = new Object[subList.size()];
				Object[] subTopData = null;
				if(subList.size()>10){
					subTopData = new Object[10];
				}else{
					subTopData = new Object[subList.size()];
				}
				for (int i = 0; i < subList.size(); i++) {
					IGroupResult result = subList.get(i);
					String name = result.getGroupValue();
					if(name==null || "".equals(name) || "外媒".equals(name)
							||"全国".equals(name)){
//							continue;
						name = "其他";
					}
//						name = convert(name);
					Integer value = result.getTotal();
						/*if(value==0){
							continue;
						}*/
					if(i<=9&&name!=null&&!"".equals(name)){
						ECharts item1 = new ECharts(name, value);
						subTopData[i] = item1;
					}
					ECharts subItem = new ECharts(name, value);
					subObjs[i] = subItem;
				}
				subObjs = deleteNullItem(subObjs);
				ECharts item = new ECharts();
				//渲染用
//					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"show"},true);
//					ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"show"},true);
//					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
//					item.setItemStyle(itemStyle);
				item.setName(keyName);
				item.setType("map");
				item.setMapType("china");
				item.setRoam(false);
//					item.setLabel("normal: {show: true},emphasis: {show: true}");
				item.setData(subObjs);
				objs[j] = item;
				//排名前10的省份
				ECharts item2 = new ECharts();
				item2.setTopData(subTopData);
				item2.setName(keyName);
				topData[j] = item2;
			}
			topData = deleteNullItem(topData);
			objs = deleteNullItem(objs);
			charts.setTitle(title);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			charts.setType("map");
			charts.setTopData(topData);
			charts.setData(objs);
			return new ECharts[]{charts};
		}else if("map3".equals(type)){
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
				String id = "";
				System.out.println("----"+name.split("_")[1]);
				if(name.indexOf("(")!=-1){
					id = name.split("\\(")[1];
					name= name.split("\\(")[0];
				}else if(name.indexOf("_")!=-1){
					id = name.split("_")[1];
					name= name.split("_")[0];
				}else{
					name = name;
				}
				System.out.println(id);
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
				item.setId(id);
				//渲染用
//				ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color","borderColor"}, colors[i],"#FFFFFF");
//				ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor"},"#FFFF00");
//				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
//				item.setItemStyle(itemStyle);

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
		}else if("gauge".equals(type)){
			Object[] objs = new Object[list.size()];
			int sumTotal = 0;
			for(IGroupResult igr:list){
				sumTotal+= igr.getTotal();
			}
			for(int j=0;j<list.size();j++){
				IGroupResult igr  = list.get(j);
				String keyName = igr.getGroupValue();
				int total = igr.getTotal();
				legend[j] = keyName;
				List<IGroupResult> subList = igr.getSubGroups();
				ECharts item = new ECharts(keyName,igr.getTotal());
				if(subList!=null&&subList.size()>0){
					Object[] subObjs = new Object[subList.size()];
					for (int i = 0; i < subList.size(); i++) {
						IGroupResult result = subList.get(i);
						String name = result.getGroupValue();
						if(name==null || "".equals(name) || "外媒".equals(name)
								||"全国".equals(name)){
							continue;
						}
						name = convert(name);
						Integer value = result.getTotal();
						String percent = "0.00";
						if(total==0||value==0){
							value = 0;
						}else{
							DecimalFormat    df   = new DecimalFormat("######0.00");
							percent = df.format(((float)value*100/total));
						}
						ECharts subItem = new ECharts(name, percent);
						subObjs[i] = subItem;
					}
					subObjs = deleteNullItem(subObjs);
					item.setData(subObjs);
				}else{
					log.info("keyName="+keyName);
//					int length=keyName.length();
//					log.info("length="+length);
					log.info("敏感length="+"敏感".length());
//					if("敏感".equals("敏感")){	//敏感时将敏感百分比计算出来
//						DecimalFormat df = new DecimalFormat("######0.00");
//						String percent = df.format(((float)total*100/sumTotal));
//						ECharts subItem = new ECharts(keyName, percent);
//						charts.setLabel(subItem);
//					}
					if(keyName.trim().equals("敏感")){	//敏感时将敏感百分比计算出来
						DecimalFormat    df   = new DecimalFormat("######0.00");
						String percent = df.format(((float)total*100/sumTotal));
						ECharts subItem = new ECharts(keyName, percent);
						charts.setLabel(subItem);
					}
				}
				item.setName(keyName);
				objs[j] = item;
			}
			objs = deleteNullItem(objs);
			charts.setTitle(title);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			charts.setType("gauge");
			charts.setData(objs);
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

		}else if("bar3".equals(type)){
			data=new Object[list.size()];
			Object[] label = null;
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				legend[i] = result.getGroupValue();
				List<IGroupResult> subList = result.getSubGroups();
				Object[] chartArray2=new Object[subList.size()];
				datetime = new Object[subList.size()];

				ECharts temp = new ECharts();
				temp.setName(result.getGroupValue());


				//第二层
				for(int j=0;j<subList.size();j++){
					IGroupResult result2 = subList.get(j);
					List<IGroupResult> subList2 = result2.getSubGroups();
					label=new Object[subList2.size()];
					Object[] data2=new Object[subList2.size()];
					String name = result2.getGroupValue();
					if(name==null || "".equals(name)){
						continue;
					}
					name = convert(name);
					ECharts temp2 = new ECharts();
					temp2.setName(name);
					temp2.setType("bar");
					datetime[j] = name;
					//第三层
					for(int k=0;k<subList2.size();k++){

						String name1 = subList2.get(k).getGroupValue();
						if(name1==null || "".equals(name1)){
							continue;
						}
						name1 = convert(subList2.get(k).getGroupValue());
						Integer value = subList2.get(k).getTotal();
						data2[k] = value;
						label[k] = name1;
						/*if(j==list.size()-1){
							legend[k] = name1;
							datetime[k] = name1;
						}*/
					}
					temp2.setData(data2);
					temp2.setStack("数量");
					chartArray2[j] = temp2;
				}
				temp.setData(chartArray2);
				data[i] = temp;
			}
			label = deleteNullItem(label);
			charts.setLabel(label);
			charts.setData(data);
			charts.setDatetime(datetime);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			charts.setTitle(title);
			return new ECharts[]{charts};

		}else if("origin".equals(type)){


			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				legend[i] = result.getGroupValue();
				List<IGroupResult> subList = result.getSubGroups();
				ECharts temp = new ECharts();
				temp.setName(result.getGroupValue());
				//第二层
				data=new Object[subList.size()];
				for(int j=0;j<subList.size();j++){
					IGroupResult result2 = subList.get(j);
					String name = result2.getGroupValue();
					int value = result2.getTotal();
					if(name==null || "".equals(name)){
						continue;
					}
					name = convert(name);
					ECharts temp2 = new ECharts(name,value);
					temp2.setName(name);
					data[j] = temp2;
				}
				temp.setData(data);
				chartArray[i] = temp;
			}
			charts.setData(chartArray);
			charts.setTitle(title);
			return new ECharts[]{charts};
		}else if("wbPie".equals(type)){	//微博分析的饼图（带百分比）

			Object[] objs = new Object[list.size()];
			Object[] lables = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name)){
					continue;
				}
				name = convert(name);
				Integer value = result.getTotal();
				double percentStr = result.getCalculatedValue();
//				if(value==0){
//					continue;
//				}
				ECharts item = new ECharts(name, value);
				ECharts item1 = new ECharts(name, percentStr);
				String color = "";
				if(mapColors.length>i){
					color = mapColors[i];
				}else{
					if(ColorMap.containsKey(name)){
						color = ColorMap.get(name);
					}
				}
				//渲染用
				ECharts normal = null;
				if("男性".equals(name)){//男女指定颜色（web层的男女图标颜色固定）
					normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, "#277bc0");
				}else if("女性".equals(name)){
					normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, "#ec6a3a");
				}else{
					normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, color);
				}
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
				item.setItemStyle(itemStyle);
				lables[i] = item1;
				objs[i] = item;
				legend[i] = name;
			}
			objs = deleteNullItem(objs);
			lables = deleteNullItem(lables);
			charts.setData(objs);
			charts.setLabel(lables);
			charts.setTitle(title);
			legend = deleteNullItem(legend);
			charts.setLegend(legend);
			return new ECharts[]{charts};

		}else if("wbGauge".equals(type)){	//微博分析的那个仪表图（生成图片用）
			Object[] objs = new Object[list.size()];
			int sumTotal = 0;
			for(IGroupResult igr:list){
				sumTotal+= igr.getTotal();
			}
			for(int j=0;j<list.size();j++){
				IGroupResult igr  = list.get(j);
				String keyName = igr.getGroupValue();
				int total = igr.getTotal();
				legend[j] = keyName;
				if("敏感".equals(keyName)){	//敏感时将敏感百分比计算出来
					DecimalFormat    df   = new DecimalFormat("######0.00");
					String percent = df.format(((float)total*100/sumTotal));
					ECharts item = new ECharts(keyName,percent);
					objs[j] = item;
				}

			}
			objs = deleteNullItem(objs);
			charts.setType("gauge");
			charts.setData(objs);
			return new ECharts[]{charts};
		}else if("wbBar".equals(type)){	//微博分析中的柱状图（注意：这里固定横坐标8个，有变化的话程序需要相应的调整）
			List<Integer> totalList = new ArrayList<Integer>();
			for(IGroupResult igr:list){
				totalList.add(igr.getTotal());
			}
			Collections.sort(totalList);	//将值从小到大排序
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				List<IGroupResult> subList = result.getSubGroups();
				subList = list;
				data=new Object[subList.size()];
				datetime=new Object[subList.size()];
				legend=new Object[subList.size()];
				//普通
				for(int j = 0;j<subList.size();j++){
					String name2 = subList.get(j).getGroupValue();
					if(name2==null || "".equals(name2)){
						continue;
					}
					name2 = convert(name2);
					Integer value = subList.get(j).getTotal();
					data[j] = value;
					String color = "#FF8500";
					//低
					if(totalList.size()>1&&value<=totalList.get(1)){
						color = "#9bca63";
					}
					//中
					if(totalList.size()>4&&value>totalList.get(1)&&value<=totalList.get(4)){
						color = "#31AFFF";
					}
					//高
					if(totalList.size()>6&&value>totalList.get(4)&&value<=totalList.get(7)){
						color = "#FF8500";
					}
					ECharts item = new ECharts("", value);
					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"},color);
					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
					item.setItemStyle(itemStyle);
					data[j] = item;
					datetime[j] = name2;
					legend[j] = name2;
				}
				charts.setData(data);
				charts.setLegend(legend);
				charts.setDatetime(datetime);
				charts.setTitle(title);
				return new ECharts[]{charts};

			}
			charts.setData(chartArray);
			charts.setDatetime(datetime);
			charts.setLegend(legend);
			charts.setTitle(title);
			return new ECharts[]{charts};
		}

		return null;
	}

	public static String convert(String gv) {
		return "wz".equals(gv) ? "网站" : "wb".equals(gv) ? "微博" : "lt"
				.equals(gv) ? "论坛" : "jw".equals(gv) ? "外媒"
				: "bk".equals(gv) ? "博客" : "wx".equals(gv) ? "微信" :
				"zw".equals(gv) ? "政务" :"sp".equals(gv) ? "视频" :
						"baokan".equals(gv) ? "报刊":"xw".equals(gv) ? "新闻":"app"
								.equals(gv) ? "客户端":"all"
								.equals(gv) ? "全部" : "0".equals(gv) ? "全部" : "1"
								.equals(gv) ? "敏感" : "2".equals(gv) ? "敏感" : "3"
								.equals(gv) ? "敏感" : "4".equals(gv) ? "非敏感" : gv;
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
		long currDateL = new Date().getTime()/1000;
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
				if (CollectionUtils.isNotEmpty(subGroups)) {
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

	//在一个list中提取total最大的一个对象
	public  IGroupResult getTopResult(List<IGroupResult> list){
		int total = 0;
		List<Integer> tlist = new ArrayList<Integer>();
		if(list!=null&&list.size()>0){
			for(IGroupResult igr:list){
				tlist.add(igr.getTotal());
			}
			Collections.sort(tlist);
			total = tlist.get(tlist.size()-1);	//截取最大值

			for(IGroupResult igr:list){
				if(igr.getTotal()==total){
					return igr;
				}
			}
		}
		return null;
	}


}

