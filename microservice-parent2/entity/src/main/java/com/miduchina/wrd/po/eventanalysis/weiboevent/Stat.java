package com.miduchina.wrd.po.eventanalysis.weiboevent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.*;
@Data
public class Stat {

    public static List<Map.Entry<String, String>> listXdnlp = new ArrayList<>();
    public static Map<String, String> mapXdnlp = new HashMap<>();
    public static Map<String, String> mapXdnlpSt = new HashMap<>();
    public static Map<String, String> mapXdnlpType = new HashMap<>();
    private List<IContentCommonNet> iContentCommonNetList = new ArrayList<IContentCommonNet>();// 内容列表
    static {
        mapXdnlp.put("a", "形容词");
        mapXdnlp.put("ad", "副形词");
        mapXdnlp.put("an", "名形词");
        mapXdnlp.put("ag", "形容词性语素");
        mapXdnlp.put("al", "形容词性惯用语");
        mapXdnlp.put("b", "区别词");
        mapXdnlp.put("bl", "区别词性惯用语");
        mapXdnlp.put("c", "连词");
        mapXdnlp.put("cc", "并列连词");
        mapXdnlp.put("d", "副词");
        mapXdnlp.put("e", "叹词");
        mapXdnlp.put("f", "方位词");
        mapXdnlp.put("h", "前缀");
        mapXdnlp.put("i", "成语");
        mapXdnlp.put("j", "简称略语");
        mapXdnlp.put("k", "后缀");
        mapXdnlp.put("l", "习用语");
        mapXdnlp.put("m", "数词");
        mapXdnlp.put("mq", "数量词");
        mapXdnlp.put("n", "名词");
        mapXdnlp.put("ng", "名词性语素");
        mapXdnlp.put("nl", "名词性惯用语");
        mapXdnlp.put("nz", "其它专名");
        mapXdnlp.put("o", "拟声词");
        mapXdnlp.put("p", "介词");
        mapXdnlp.put("pba", "介词“把”");
        mapXdnlp.put("pbei", "介词“被”");
        mapXdnlp.put("q", "量词");
        mapXdnlp.put("qv", "动量词");
        mapXdnlp.put("qt", "时量词");
        mapXdnlp.put("r", "代词");
        mapXdnlp.put("rr", "人称代词");
        mapXdnlp.put("rz", "指示代词");
        mapXdnlp.put("rzt", "时间指示代词");
        mapXdnlp.put("rzs", "处所指示代词");
        mapXdnlp.put("rzv", "谓词性指示代词");
        mapXdnlp.put("ry", "疑问代词");
        mapXdnlp.put("ryt", "时间疑问代词");
        mapXdnlp.put("rys", "处所疑问代词");
        mapXdnlp.put("ryv", "谓词性疑问代词");
        mapXdnlp.put("rg", "代词性语素");
        mapXdnlp.put("s", "处所词");
        mapXdnlp.put("tg", "时间词性语素");
        mapXdnlp.put("u", "助词");
        mapXdnlp.put("ude1", "的 底");
        mapXdnlp.put("ude2", "地");
        mapXdnlp.put("ude3", "得");
        mapXdnlp.put("udeng", "等 等等 云云");
        mapXdnlp.put("udh", "的话");
        mapXdnlp.put("uguo", "过");
        mapXdnlp.put("ule", "了 喽");
        mapXdnlp.put("ulian", "连 （“连小学生都会”）");
        mapXdnlp.put("uls", "来讲 来说 而言 说来");
        mapXdnlp.put("uqj", "起见");
        mapXdnlp.put("usuo", "所");
        mapXdnlp.put("uyy", "一样 一般 似的 般");
        mapXdnlp.put("uzhe", "着");
        mapXdnlp.put("uzhi", "之");
        mapXdnlp.put("v", "动词");
        mapXdnlp.put("vd", "副动词");
        mapXdnlp.put("vn", "名动词");
        mapXdnlp.put("vshi", "动词“是”");
        mapXdnlp.put("vyou", "动词“有”");
        mapXdnlp.put("vf", "趋向动词");
        mapXdnlp.put("vx", "形式动词");
        mapXdnlp.put("vi", "不及物动词（内动词）");
        mapXdnlp.put("vl", "动词性惯用语");
        mapXdnlp.put("vn", "名动词");
        mapXdnlp.put("vg", "动词性语素");
        mapXdnlp.put("w", "标点符号");
        mapXdnlp.put("wkz", "左括号，全角:（ 〔 ［ ｛ 《 【 〖 〈 半角:( [ { <");
        mapXdnlp.put("wky", "右括号，全角:） 〕 ］ ｝ 》 】 〗 〉 半角: ) ] { >");
        mapXdnlp.put("wyb", "半角引号，半角:\" '");
        mapXdnlp.put("wyz", "左引号，全角:“ ‘ 『");
        mapXdnlp.put("wyy", "右引号，全角:” ’ 』");
        mapXdnlp.put("wj", "句号，全角:。");
        mapXdnlp.put("ww", "问号，全角:？ 半角:?");
        mapXdnlp.put("wt", "叹号，全角:！ 半角:!");
        mapXdnlp.put("wd", "逗号，全角:， 半角:,");
        mapXdnlp.put("wf", "分号，全角:； 半角: ;");
        mapXdnlp.put("wn", "顿号，全角:、");
        mapXdnlp.put("wm", "冒号，全角:: 半角: :");
        mapXdnlp.put("ws", "省略号，全角:…… …");
        mapXdnlp.put("wp", "破折号，全角:—— －－ ——－ 半角:--- ----");
        mapXdnlp.put("wb", "百分号千分号，全角:％ ‰ 半角:%");
        mapXdnlp.put("wh", "单位符号，全角:￥ ＄ ￡ ° ℃ 半角:$");
        mapXdnlp.put("x", "字符串");
        mapXdnlp.put("xx", "非语素字 （只是一个符号，代表未知数或者符号）");
        mapXdnlp.put("xu", "网址URL");
        mapXdnlp.put("y", "语气词(delete yg)");
        mapXdnlp.put("z", "状态词");
        mapXdnlp.put("un", "未知词");

        mapXdnlpSt.put("nis", "机构后缀");
        mapXdnlpSt.put("nnd", "职业");
        mapXdnlpSt.put("nnt", "职务职称");
        mapXdnlpSt.put("nr", "人名");
        mapXdnlpSt.put("nr1", "汉语姓氏");
        mapXdnlpSt.put("nr2", "汉语名字");
        mapXdnlpSt.put("nrj", "日语人名");
        mapXdnlpSt.put("nrf", "音译人名");
        mapXdnlpSt.put("ns", "地名");
        mapXdnlpSt.put("nsf", "音译地名");
        mapXdnlpSt.put("nt", "机构团体名");
        mapXdnlpSt.put("t", "时间词");

        mapXdnlp.putAll(mapXdnlpSt);

        listXdnlp = sortMapByValueSizeToList(mapXdnlp);

        mapXdnlpType.put("n", "名词");
        mapXdnlpType.put("v", "动词");
        mapXdnlpType.put("a", "形容词");
        mapXdnlpType.put("r", "代词");
        mapXdnlpType.put("q", "量词");
    }

    @ApiModelProperty(value = "统计数量", required = true)
    private Long count;
    @ApiModelProperty(value = "漏斗类型", required = true)
    private String funnelType;
    @ApiModelProperty(value = "内容对象", required = true)
    private IContentCommonNet iContentCommonNet;
    @ApiModelProperty(value = "内容列表", required = true)
    private List<IContentCommonNet> IContentCommonNetList;
    @ApiModelProperty(value = "增量", required = true)
    private Long increment;
    @ApiModelProperty(value = "位置", required = true)
    private Long index;
    @ApiModelProperty(value = "统计键值", required = true)
    private String key;
    @ApiModelProperty(value = "关键词", required = true)
    private String keys;
    @ApiModelProperty(value = "最大值", required = true)
    private Double max;
    @ApiModelProperty(value = "均值", required = true)
    private Double mean;
    @ApiModelProperty(value = "最小值", required = true)
    private Double min;
    @ApiModelProperty(value = "缺失数量", required = true)
    private Long missing;
    @ApiModelProperty(value = "统计名称(时间)", required = true)
    private String name;
    @ApiModelProperty(value = " 统计数量", required = true)
    private Long num;
    @ApiModelProperty(value = "源内容列表", required = true)
    private List<IContentCommonNet> originLst = new ArrayList<IContentCommonNet>();
    @ApiModelProperty(value = "统计百分比", required = true)
    private Double percent;
    @ApiModelProperty(value = "均差比", required = true)
    private Double percentAverage;
    @ApiModelProperty(value = " 均差比差", required = true)
    private Double percentAverageMinus;
    @ApiModelProperty(value = "统计百分比", required = true)
    private String percentStr;
    @ApiModelProperty(value = "标准差", required = true)
    private Double stddev;
    @ApiModelProperty(value = "和值", required = true)
    private Double sum;
    @ApiModelProperty(value = "权重", required = true)
    private Float tfidf;
    @ApiModelProperty(value = "统计热度(双精度)", required = true)
    private double total;
    @ApiModelProperty(value = "统计类型", required = true)
    private int type = 1;
    @ApiModelProperty(value = "词性", required = true)
    private String xdnlp;
    @ApiModelProperty(value = "名称", required = true)
    private String xdnlpName;
    @ApiModelProperty(value = "类别", required = true)
    private String xdnlpType;

    public static List<Map.Entry<String, String>> sortMapByValueSizeToList(Map<String, String> oriMap) {
        if (oriMap != null && !oriMap.isEmpty() && oriMap.size() > 0) {
            List<Map.Entry<String, String>> entryList = new LinkedList<Map.Entry<String, String>>(oriMap.entrySet());
            Collections.sort(entryList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return o2.getKey().length() - o1.getKey().length();
                }
            });
            return entryList;
        }
        return new LinkedList<Map.Entry<String, String>>();
    }

}
