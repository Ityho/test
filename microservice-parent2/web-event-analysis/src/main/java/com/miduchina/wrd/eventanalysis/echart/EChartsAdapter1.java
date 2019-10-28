package com.miduchina.wrd.eventanalysis.echart;

import com.miduchina.wrd.dto.echart.IGroupResult;
import com.miduchina.wrd.dto.echart.MarkPointData;
import com.miduchina.wrd.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;




public class EChartsAdapter1 {
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	//private static final String[] QG_COLOR = new String[]{"#87CEFA","#FF7F50"};
	private static String []mapColors = {"#f18d00","#f4aa53","#cf421e","#b17b47","#914f23","#6e7584","#3fa579","#526dab","#e69064","#46af35","#0e7dc0","#b6becc"};
	private String type;
	private String title;
	private List<IGroupResult> list;
	private static Map<String, String> ColorMap = new HashMap<String, String>();
	public void init(){
		ColorMap.put("非敏感", "#f4aa53");
		ColorMap.put("敏感", "#f18d00");
		ColorMap.put("境内","#3454a1");
		ColorMap.put("境外","#f29300");

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
	public EChartsAdapter1(List<IGroupResult> list, String type, String title) {
		init();
		this.list = list;
		this.type = type;
		this.title = title;
	}

	public Object[] getChart() {
		format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		if (list == null || list.size()==0) {
			System.out.println(type+ "：EChartAdapter --> List<IGroupResult> Objects Can Not Be Empty");
		}
		Object[] data = null;
		Object[] datetime = null;
		Object[] chartLabel=null;
		Object[] legend = new Object[list.size()];;
		Object[] chartArray = new Object[list.size()];
		boolean flag = true;
		ECharts charts = new ECharts();
		if (type.equals("pie")||type.indexOf("eventpie")!=-1||type.equals("eventbar3")||type.equals("pie1")||type.equals("eventpie3")||type.equals("pie3")) {
			Object[] objs = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name)){
					continue;
				}
				name = convert(name);

				ECharts item = null;
				if(type.equals("eventpie2")){
					Double value = result.getCalculatedValue();
					item = new ECharts(name, value);
				}else{
					long value = result.getTotal();
					if(value==0){
						continue;
					}
					item = new ECharts(name, value);
				}

				//渲染用
				/*if(ColorMap.containsKey(name)){
					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, ColorMap.get(name));
					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
					item.setItemStyle(itemStyle);
				}else{*/
				ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, mapColors[i%11]);
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
				item.setItemStyle(itemStyle);
                /*}*/

				if(type.equals("eventbar3")){//事件分析观点分析用
					objs[list.size()-1-i] = item;
					legend[list.size()-1-i] = name;
				}else{
					objs[i] = item;
					legend[list.size()-1-i] = name;
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
							long value = subList.get(j).getTotal();
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
						long value = subList.get(k).getTotal();
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
			int m = 0;
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				List<IGroupResult> subList = result.getSubGroups();
				long size = result.getTotal();
				Object[] dataPoint = new Object[(int) size];
				Object[] dataParPoint = new Object[1];
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
							long value = subList.get(j).getTotal();
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
					IGroupResult igrTop = null;
					if("line1".equals(type)){
						igrTop = getTopResultHot(subList);
					}else {
						igrTop = getTopResult(subList);
					}
					ECharts tempTop = null;


					if(igrTop!=null){
						if("eventline2".equals(type)||"line1".equals(type)||"line2".equals(type)){
							if("line1".equals(type)) {
								tempTop = new ECharts(igrTop.getGroupValue(), igrTop.getTotalHot());
							}else{
								tempTop = new ECharts(igrTop.getGroupValue(), (double) igrTop.getTotal() / 100);
							}
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
						long value = subList.get(k).getTotal();
						if(value==0){
							//continue;
						}
						if("line3".equals(type)){
							data[k] = value*(-1);
						}else if("eventline2".equals(type)||"line1".equals(type)||"line2".equals(type)){
							double calValue = subList.get(k).getCalculatedValue();
							if(calValue == 1.0){
								MarkPointData mpData = new MarkPointData();
								mpData.setName("热度指数："+subList.get(k).getTotalHot()+"<br/>发表媒体(网友)："+subList.get(k).getAuthorOrCaptureWebsiteName()+"<br/>重要报道："+subList.get(k).getCluster()+"<br/>发布时间："+subList.get(k).getPublished());
								//mpData.setValue((double)value/100);
								mpData.setxAxis(subList.get(k).getGroupValue());
								mpData.setyAxis(subList.get(k).getTotalHot());
								dataPoint[m] = mpData;
								if(k<=subList.size()-1) {
									dataParPoint[0] = dataPoint;
									ECharts markPoint = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"data"}, dataParPoint);
									temp.setMarkPoint(markPoint);
								}
								m++;
							}

							if("line1".equals(type)){
								data[k] = subList.get(k).getTotalHot();
							}else {
								data[k] = (double) value / 100;
							}
							temp.setSymbolSize(10);
							ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"type","width"},"solid","2");
							ECharts lineStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
							temp.setLineStyle(lineStyle);
						}else{
							data[k] = value;
						}
						try {
							if(date!=null && !date.equals("") && CommonUtils.match("^\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}$", date)){
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
							e.printStackTrace();
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
			Map<String,Long> maptotal=new HashMap<String, Long>();
			for (int j = 0; j < list.size(); j++) {
				IGroupResult res1=list.get(j);
				for(IGroupResult res2: res1.getSubGroups()){
					if(res2!=null){
						String val=res2.getGroupValue();
						if(!maptotal.containsKey(val)){
							maptotal.put(val,0l);
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
			Long[] maxVal = null;
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
					maxVal = new Long[subList.size()];
				}
				for(int j=0;subList!=null &&j<subList.size();j++){
					IGroupResult gr = subList.get(j);
					String gv = gr.getGroupValue();
					gv = convert(gv);
					long val = gr.getTotal();
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
		}else if("map".equals(type)||"yellowMap".equals(type)){
			Object[] objs = new Object[list.size()];
			Object[] topData = null;
			if(list.size()>3){
				topData = new Object[3];
			}else{
				topData = new Object[list.size()];
			}
			String [] colors = null;
			if("yellowMap".equals(type)){
				colors = pickColor(list,2);
			}else{
				colors = pickColor(list,1);
			}

			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name) || "外媒".equals(name)
						|| "全国".equals(name) || "中国".equals(name) || "海外".equals(name)){
					continue;
				}
				name = convert(name);
				long value = result.getTotal();
				if(value==0){
					continue;
				}
				ECharts item = new ECharts(name, value);

				//渲染用

				if("yellowMap".equals(type)){
					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color","borderColor"}, colors[i],"#FFFFFF");
					ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor"},"yellow");
					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
					item.setItemStyle(itemStyle);
				}else{
					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color","borderColor"}, colors[i],"#FFFFFF");
					ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor"},"#FFFF00");
					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
					item.setItemStyle(itemStyle);
				}

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
		}else if("map2".equals(type)||"map3".equals(type)){
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
					long value = result.getTotal();
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
				//渲染用
               /* ECharts normal ;
                ECharts emphasis;*/
				if("map3".equals(type)){
                    /*JSONObject showObject = new JSONObject();
                    showObject.put("show",true);
                    JSONObject showObject2 = new JSONObject();
                    showObject2.put("show",true);
                    normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor","label"},"#FFFFFF",showObject);
                    emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor","label"},"#FFFF00",showObject2);*/
					item.setShowLegendSymbol(false);
				}else {
					ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor"}, "#FFFFFF");
					ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"borderColor"}, "#FFFF00");
					ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
					item.setItemStyle(itemStyle);
				}

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
		}else if("gauge".equals(type)){
			Object[] objs = new Object[list.size()];
			int sumTotal = 0;
			for(IGroupResult igr:list){
				sumTotal+= igr.getTotal();
			}
			for(int j=0;j<list.size();j++){
				IGroupResult igr  = list.get(j);
				String keyName = igr.getGroupValue();
				long total = igr.getTotal();
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
						long value = result.getTotal();
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
					if("敏感".equals(keyName)){	//敏感时将敏感百分比计算出来
						DecimalFormat    df   = new DecimalFormat("######0.00");
						String percent = df.format(((float)total*100/sumTotal));
						ECharts subItem = new ECharts(keyName, percent);
						charts.setLabel(subItem);
					}else if("境内".equals(keyName)){
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
		}else if("bar2".equals(type)||"bar4".equals(type)){
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
							long value = subList.get(j).getTotal();
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
					chartLabel = new Object[subList.size()];
					data=new Object[subList.size()];
					datetime=new Object[subList.size()];
					Long[] arr=new Long[subList.size()];
					String name = result.getGroupValue();
					if(name==null || "".equals(name)){
						continue;
					}
					name = convert(name);
					ECharts temp = new ECharts();
					temp.setName(name);
					temp.setType("bar");
					legend[i] = name;

					for(int k=0;k<subList.size();k++){

						String name1 = subList.get(k).getGroupValue();
						if(name1==null || "".equals(name1)){
							continue;
						}
						name1 = convert(subList.get(k).getGroupValue());

						if("bar4".equals(type)){
							double valueCV = subList.get(k).getCalculatedValue();
							data[k] = valueCV;
						}else{
							long value = subList.get(k).getTotal();
							data[k] = value;
						}

//						if(i==list.size()-1){
						datetime[k] = name1;
//						}
						arr[k]=subList.get(k).getTotal();
					}
					long maxIndex=getMaxIndex(arr);

					//分类中数量最多的取出来
					ECharts topTemp = new ECharts(convert(subList.get((int)maxIndex).getGroupValue()),subList.get((int)maxIndex).getTotal());
					topTemp.setLabel(name);
					topData[i] = topTemp;
					temp.setData(data);
					temp.setDatetime(datetime);
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
						long value = subList2.get(k).getTotal();
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
					long value = result2.getTotal();
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
		}else if("ring".equals(type)){
			String name1 =null;
			double percent1;
			double mg0;
			double mg1;
			IGroupResult iGroupResult = new IGroupResult();
			long total = list.get(0).getTotal()+list.get(1).getTotal();

			for (int i = 0; i < list.size(); i++) {
				Object[] json = new Object[2];
				IGroupResult result = list.get(i);
				legend[i] = result.getGroupValue();
				ECharts temp0 = new ECharts();
				ECharts show = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"show"},"false");
				ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color",},"rgba(0,0,0,0)");
				ECharts emphasis = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"},"rgba(0,0,0,0)");
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal","emphasis"},normal,emphasis);
				DecimalFormat    df   = new DecimalFormat("######0.00");

				String percent0 = df.format(((float)result.getTotal()*100/total));
				String percent2 = df.format(((float)(total-result.getTotal())*100/total));
				//ECharts item = new ECharts(result.getGroupValue(),percent);
				ECharts data1 = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"value","name"},percent0,result.getGroupValue());
				ECharts data2 = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"value","itemStyle"},percent2,itemStyle);
				json[0] = data1;
				json[1] = data2;
				temp0.setData(json);
				chartArray[i] = temp0;
			}
			charts.setData(chartArray);
			charts.setLegend(legend);
			charts.setTitle(title);

			return new ECharts[]{charts};

		}else if("wbPie".equals(type)){	//微博分析的饼图（带百分比）

			Object[] objs = new Object[list.size()];
			Object[] lables = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String name = result.getGroupValue();
				if(name==null || "".equals(name)){
					name = "其他";
//					continue;
				}
				if(name.indexOf("<")!=-1){
					name = name.replace("<", "");
				}
				if(name.indexOf(">")!=-1){
					name = name.replace(">", "");
				}
				if(name.indexOf("\"")!=-1){
					name = name.replace("\"", "");
				}
				if(name.indexOf("a href=")!=-1){	//将发布设备中的名字中带a标签的 处理一下
					name = name.replace("a href=", "");
				}
				name = convert(name);
				long value = result.getTotal();
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
				}else if("其它".equals(name)){
					normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"}, "#666");
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
				long total = igr.getTotal();
				legend[j] = keyName;
				if("敏感".equals(keyName)){	//敏感时将敏感百分比计算出来
					DecimalFormat    df   = new DecimalFormat("######0.00");
					String percent = df.format(((float)total*100/sumTotal));
					ECharts item = new ECharts(keyName,percent);
					objs[j] = item;
				}else if("境内".equals(keyName)){
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
			List<Long> totalList = new ArrayList<Long>();
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
					long value = subList.get(j).getTotal();
					data[j] = value;
					String color = "#F29300";
					//低
					if(totalList.size()>1&&value<=totalList.get(1)){
						color = "#72C1BE";
					}
					//中
					if(totalList.size()>4&&value>totalList.get(1)&&value<=totalList.get(4)){
						color = "#BB814E";
					}
					//高
					if(totalList.size()>6&&value>totalList.get(4)&&value<=totalList.get(7)){
						color = "#F29300";
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
		}else if("sourceBar".equals(type)){
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
							long value = subList.get(j).getTotal();
							//ECharts chart = new ECharts();
							//chart.setValue(value);
							data[j] = value;

							//渲染用
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
					//legend=new Object[subList.size()];

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
						name1 = convert(name1);
						long value = subList.get(k).getTotal();
						data[k] = value;


						if(i==list.size()-1){

							datetime[k] = name1;
						}
					}
					if(flag){
						//temp.setLegend(legend);
						//temp.setDatetime(datetime);
					}
                  /*  ECharts normal = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"color"},mapColors[i%10]);
                    ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
                    temp.setItemStyle(itemStyle);*/

					temp.setData(data);
					temp.setType("bar");
					temp.setStack("数量");
					flag = false;
					chartArray[i] = temp;
					legend[i] = name;
				}

			}
			charts.setData(chartArray);
			charts.setDatetime(datetime);
			charts.setLegend(legend);
			charts.setTitle(title);
			return new ECharts[]{charts};
		}else if("area".equals(type)){

			int hoursL = 0;
			ECharts temp = new ECharts();
			for (int i = 0; i < list.size(); i++) {
				IGroupResult result = list.get(i);
				String date = result.getGroupValue();
				if(date==null || "".equals(date)){
					continue;
				}
				long num = result.getTotal();
				legend[i] = date;
				chartArray[i] = num;
				flag = false;
			}

			/*ECharts areaStyleItem = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"type"},"default");
			ECharts item = (ECharts) ECharts.getInstance(ECharts.class, new String[]{"areaStyle"},areaStyleItem);
	        ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},item);
	        temp.setItemStyle(itemStyle);
	        temp.setType("line");
	        temp.setSmooth(true);
	        temp.setData(chartArray);
			charts.setData(temp);*/
			charts.setData(chartArray);
			charts.setDateFormat("yyyy-MM-dd");
			if(hoursL>0){
				charts.setDateFormat("MM-dd hh:00");
			}
			//charts.setData(chartArray);
			charts.setDatetime(legend);
			//charts.setLegend(legend);
			charts.setTitle(title);

			return new ECharts[]{charts};
		}else if("proGauge".equals(type)){
			Object[] objs = new Object[list.size()];
			for(int j=0;j<list.size();j++){
				IGroupResult igr  = list.get(j);
				String keyName = igr.getGroupValue();
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
						double value = result.getCalculatedValue();
						ECharts subItem = new ECharts(name, value);
						subObjs[i] = subItem;
					}
					subObjs = deleteNullItem(subObjs);
					item.setData(subObjs);
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
		}else if("wordCloud".equals(type)){
			String[] colors = {"#72c1be","#f29300","#a05623","#277bc0"};
			Object[] objs = new Object[list.size()];
			for(int i=0;i<list.size();i++){
				IGroupResult igr = list.get(i);
				ECharts item = new ECharts(igr.getGroupValue(),igr.getTotal());
				ECharts normal = (ECharts)ECharts.getInstance(ECharts.class,new String[]{"color"},colors[(int)(Math.random()*4)]);
				ECharts itemStyle = (ECharts) ECharts.getInstance(ECharts.class,new String[]{"normal"},normal);
				item.setItemStyle(itemStyle);
				objs[i] = item;
			}
			objs = deleteNullItem(objs);
			charts.setData(objs);
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
			long total = gr.get(i).getTotal();
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
		long total = 0;
		List<Long> tlist = new ArrayList<Long>();
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

	//在一个list中提取total最大的一个对象
	public  IGroupResult getTopResultHot(List<IGroupResult> list){
		double total = 0;
		List<Double> tlist = new ArrayList<Double>();
		if(list!=null&&list.size()>0){
			for(IGroupResult igr:list){
				tlist.add(igr.getTotalHot());
			}
			Collections.sort(tlist);
			total = tlist.get(tlist.size()-1);	//截取最大值

			for(IGroupResult igr:list){
				if(igr.getTotalHot()==total){
					return igr;
				}
			}
		}
		return null;
	}

	//获取最大值的下标
	public static Long getMaxIndex(Long[] arr){
		int maxIndex = 0;   //获取到的最大值的角标
		for(int i=0; i<arr.length; i++){
			if(arr[i] > arr[maxIndex]){
				maxIndex = i;
			}
		}
		return (long) maxIndex;
	}

}
