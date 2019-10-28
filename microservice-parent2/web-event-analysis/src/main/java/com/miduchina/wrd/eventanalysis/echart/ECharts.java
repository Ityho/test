package com.miduchina.wrd.eventanalysis.echart;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ECharts implements Serializable{
	private Object type;
	private Object name;
	private Object data;
	private Object title;
	private Object legend;
	private Object datetime;
	private Object value;
	private Object stack;
	private Object indicator;
	private Object text;
	private Object max;

	private Object itemStyle;
	private Object normal;
	private Object emphasis;
	private Object color;
	private Object borderColor;
	private Object dateFormat;
	private Object topData;
	private Object mapType;
	private Object roam;
	private Object label;
	private Object id;


	private Object areaStyle;
	private Boolean showLegendSymbol;
	private Object barWidth;
	private Object symbol;
	private Object symbolSize;
	private Object lineStyle;
	private Object width;
	private Object markPoint;
	private Object map;
	private Object emotion;//情绪地图情感值
	public ECharts(){

	}

	public ECharts(Object name,Object value){
		this.name = name;
		this.value = value;
	}

	public static Object getInstance(Class _class,String[] fields,Object...objs){
		Object object = null;
		try {
			object = _class.newInstance();
			int paramsLen = 0;
			if(fields==null || fields.length==0 || objs==null || objs.length==0){
				paramsLen = 0;
			}
			paramsLen = fields.length-objs.length>0?objs.length:fields.length;
			if(paramsLen==0){
				return null;
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i=0;i<paramsLen;i++){
					map.put(fields[i], objs[i]);
				}
				Method[] methods = _class.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].getName().startsWith("set")) {// 方法返回方法名
						methods[i].setAccessible(true);//允许private被访问(以避免private getXX())
						String filed = methods[i].getName().replace("set", "").toLowerCase();
						if(map.get(filed)!=null){
							methods[i].invoke(object, map.get(filed));
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getTitle() {
		return title;
	}
	public void setTitle(Object title) {
		this.title = title;
	}
	public Object getLegend() {
		return legend;
	}
	public void setLegend(Object legend) {
		this.legend = legend;
	}
	public Object getDatetime() {
		return datetime;
	}
	public void setDatetime(Object datetime) {
		this.datetime = datetime;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Object getStack() {
		return stack;
	}
	public void setStack(Object stack) {
		this.stack = stack;
	}
	public Object getText() {
		return text;
	}
	public void setText(Object text) {
		this.text = text;
	}
	public Object getMax() {
		return max;
	}
	public void setMax(Object max) {
		this.max = max;
	}
	public Object getIndicator() {
		return indicator;
	}
	public void setIndicator(Object indicator) {
		this.indicator = indicator;
	}
	public Object getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(Object itemStyle) {
		this.itemStyle = itemStyle;
	}
	public Object getNormal() {
		return normal;
	}
	public void setNormal(Object normal) {
		this.normal = normal;
	}
	public Object getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(Object emphasis) {
		this.emphasis = emphasis;
	}
	public Object getColor() {
		return color;
	}
	public void setColor(Object color) {
		this.color = color;
	}

	public Object getMapType() {
		return mapType;
	}
	public void setMapType(Object mapType) {
		this.mapType = mapType;
	}

	public Object getRoam() {
		return roam;
	}
	public void setRoam(Object roam) {
		this.roam = roam;
	}
	public Object getLabel() {
		return label;
	}
	public void setLabel(Object label) {
		this.label = label;
	}
	public static void main(String[] args) {
		Object name = "11111";
		Object value = "4124141";
		//new ECharts(ECharts.class,name,value);
		ECharts charts = (ECharts) getInstance(ECharts.class,new String[]{"name","value","type"},name,value);
		System.out.println();
	}
	public Object getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(Object dateFormat) {
		this.dateFormat = dateFormat;
	}
	public Object getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(Object borderColor) {
		this.borderColor = borderColor;
	}
	public Object getTopData() {
		return topData;
	}
	public void setTopData(Object topData) {
		this.topData = topData;
	}
	/**
	 * @return the id
	 */
	public Object getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Object id) {
		this.id = id;
	}

	public Object getAreaStyle() {
		return areaStyle;
	}

	public void setAreaStyle(Object areaStyle) {
		this.areaStyle = areaStyle;
	}

	public Boolean getShowLegendSymbol() {
		return showLegendSymbol;
	}

	public void setShowLegendSymbol(Boolean showLegendSymbol) {
		this.showLegendSymbol = showLegendSymbol;
	}

	public Object getBarWidth() {
		return barWidth;
	}

	public void setBarWidth(Object barWidth) {
		this.barWidth = barWidth;
	}

	public Object getSymbol() {
		return symbol;
	}

	public void setSymbol(Object symbol) {
		this.symbol = symbol;
	}

	public Object getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(Object symbolSize) {
		this.symbolSize = symbolSize;
	}

	public Object getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(Object lineStyle) {
		this.lineStyle = lineStyle;
	}

	public Object getWidth() {
		return width;
	}

	public void setWidth(Object width) {
		this.width = width;
	}

	public Object getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(Object markPoint) {
		this.markPoint = markPoint;
	}

	public Object getMap() {
		return map;
	}

	public void setMap(Object map) {
		this.map = map;
	}

	public Object getEmotion() {
		return emotion;
	}

	public void setEmotion(Object emotion) {
		this.emotion = emotion;
	}

}
