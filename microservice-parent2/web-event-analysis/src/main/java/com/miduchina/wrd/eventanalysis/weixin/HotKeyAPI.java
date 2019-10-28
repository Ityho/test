package com.miduchina.wrd.eventanalysis.weixin;

import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.util.CommonFile;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.List;

public class HotKeyAPI implements Serializable {
    private static final long serialVersionUID = -1152960490461535047L;

//	public static List getStr() {
//		URL hotUrl = Thread.currentThread().getContextClassLoader().getResource("hotKey.txt");
//		List<String> list = null;
//		try {
//			String strs = CommonFile.readTxt(URLDecoder.decode(hotUrl.getPath()), "GBK");
//			// String newStrs = new String(strs.getBytes("GBK"),"GBK");
//			Random r = new Random();
//			System.out.println("HotKeyAPI.getStr() ==>" + strs);
//			String[] strArray = strs.split(" ");
//			list = new ArrayList<>();
//			int i = 10;
//			while (i > 0) {
//				Integer index = r.nextInt(strArray.length);
//				String value = strArray[index];
//				if (!list.contains(value)) {
//					list.add(value);
//					i--;
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
//	public static String getImgUrlByUserInfo(UserInfo ui) {
//		System.out.println("weixin headImgUrl ==>" + ui.getHeadimgurl());
//		if (StringUtils.isNotBlank(ui.getHeadimgurl()) && ui.getHeadimgurl().contains("http://wx.qlogo.cn")) {
//			String imgStr = ui.getHeadimgurl().substring(ui.getHeadimgurl().length() - 2);
//			if (imgStr.equals("/0")) {
//				ui.setHeadimgurl(ui.getHeadimgurl().substring(0, ui.getHeadimgurl().length() - 2) + "/132");
//			}
//		}
//		return htmlToImg(getStr(), ui);
//	}
//	public static String htmlToImg(List list, UserInfo ui) {
//		try {
//			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//			// imageGenerator.loadUrl("http://localhost:63342/201410201501/dewedes2.html");
//			StringBuffer sb = new StringBuffer();
//			sb.append("<html>");
//			sb.append("<head>");
//			sb.append("<meta charset='UTF-8'/>");
//			sb.append("<style>");
//			sb.append("html,body,div,ul,li,a{margin:0;padding:0;}");
//			sb.append("</style>");
//			sb.append("</head>");
//			sb.append("<body style='height: 800px;width: 550px;margin: 0px auto;'>");
//			sb.append("<div style='height: 1000px;width: 550px;margin: 0px auto;background: url("
//					+ SysConfig.FILE_VIEW_PATH_H5 + "/images/www.jpg) no-repeat;'>");
//			// sb.append("<div style = 'background:url("+ui.getHeadimgurl()+")
//			// no-repeat;margin-left:200px;height:200px;'></div>");
//			sb.append("<div style = 'background:url(" + ui.getHeadimgurl()
//					+ ")  no-repeat;margin:40px 224px 0px;height:140px;width:140px;border:solid 1px #efefef;'></div>");
//			sb.append(
//					"<div style='position: absolute;width:550px;float:left;margin:20px 100px 0px;font-size:32px;text-align:center;font-family:方正稚艺简体;'>"
//							+ ui.getNickname() + "</div>");
//			sb.append("<div style='position: absolute;width:550px;margin:185px 90px 0px;text-align:center;'>");
//			sb.append("<ul>");
//			for (int i = 0; i < list.size(); i++) {
//				sb.append("<a style='font-size: " + (int) (Math.random() * 16 + 14) + "px;font-family:方正稚艺简体';>&nbsp;"
//						+ list.get(i) + "&nbsp;</a>");
//			}
//			sb.append("</ul>");
//			sb.append("</div>");
//			sb.append("</div>");
//			sb.append("</body>");
//			sb.append("</html>");
//			imageGenerator.loadHtml(sb.toString());
//			Dimension dimension = new Dimension(800, 550);
//			imageGenerator.setSize(dimension);
//			String uploadPath = SysConfig.FILE_UPLOAD_PATH;
//			Calendar cal = Calendar.getInstance();
//			int year = cal.get(Calendar.YEAR);// 获取年份
//			int month = cal.get(Calendar.MONTH);// 获取月份
//			int day = cal.get(Calendar.DATE);// 获取日
//			uploadPath = uploadPath + "weixin" + "/" + year + "/" + (month + 1) + "/" + day + "/";
//			File diskFile = new File(uploadPath);
//			if (!diskFile.exists()) {
//				diskFile.mkdirs();
//			}
//			uploadPath = uploadPath + ui.getOpenid() + ".png";
//			imageGenerator.saveAsImage(uploadPath);
//			return uploadPath;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return null;
//	}
//	public static List<String> getStr1() {
//		List<String> list = new ArrayList<>();
//		list.add("bbd.png");
//		list.add("cwzy.png");
//		list.add("dykw.png");
//		list.add("gkx.png");
//		list.add("grxz.png");
//		list.add("jndj.png");
//		list.add("jx.png");
//		list.add("ldlbp.png");
//		list.add("nlup.png");
//		list.add("sjms.png");
//		list.add("sxxmb.png");
//		list.add("sz.png");
//		list.add("tp.png");
//		list.add("xtp.png");
//		list.add("xxsc.png");
//		list.add("zdq.png");
//		list.add("zzjh.png");
//		return list;
//	}
//	public static String getImgUrlByUserInfo1(UserInfo ui) {
//		System.out.println("weixin headImgUrl ==>" + ui.getHeadimgurl());
//		if (StringUtils.isNotBlank(ui.getHeadimgurl()) && ui.getHeadimgurl().contains("http://wx.qlogo.cn")) {
//			String imgStr = ui.getHeadimgurl().substring(ui.getHeadimgurl().length() - 2);
//			if (imgStr.equals("/0")) {
//				ui.setHeadimgurl(ui.getHeadimgurl().substring(0, ui.getHeadimgurl().length() - 2) + "/64");
//			}
//		}
//		return htmlToImg1(getStr1(), ui);
//	}
//	public static String htmlToImg1(List list, UserInfo ui) {
//		try {
//			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//			Random r = new Random();
//			StringBuffer sb = new StringBuffer();
//			sb.append("<html>");
//			sb.append("<head>");
//			sb.append("<meta charset='UTF-8'/>");
//			sb.append("<style>");
//			sb.append("html,body,div,ul,li,a{margin:0;padding:0;}");
//			sb.append("</style>");
//			sb.append("</head>");
//			sb.append("<body style='height: 1020px;width: 575px;margin: 0px auto;'>");
//			sb.append("<div style='height: 1020px;width: 575px;padding-top:430px;margin: 0px auto;background: url("
//					+ SysConfig.FILE_VIEW_PATH_H5 + "/images/wx/title.jpg) no-repeat;overflow: hidden;'>");
//			sb.append("<div style = 'background:url(" + ui.getHeadimgurl()
//					+ ")  no-repeat;margin-left: 18px; width: 50px;padding-top:12px;height: 50px;padding-left:55px;font-size:22px;font-family:微软雅黑;'>"
//					+ ui.getNickname() + "祝您2017：</div>");
//			sb.append("<img style='height: 560px;width: 615px;' ");
//			for (int i = 0; i < list.size(); i++) {
//				sb.append(
//						"src='" + SysConfig.FILE_VIEW_PATH_H5 + "/images/wx/" + list.get(r.nextInt(list.size())) + "'");
//			}
//			sb.append(" />");
//			sb.append("</div>");
//			sb.append("</body>");
//			sb.append("</html>");
//			imageGenerator.loadHtml(sb.toString());
//			Dimension dimension = new Dimension(575, 1020);
//			imageGenerator.setSize(dimension);
//			String uploadPath = SysConfig.FILE_UPLOAD_PATH;
//			Calendar cal = Calendar.getInstance();
//			int year = cal.get(Calendar.YEAR);// 获取年份
//			int month = cal.get(Calendar.MONTH);// 获取月份
//			int day = cal.get(Calendar.DATE);// 获取日
//			uploadPath = uploadPath + "weixin" + "/" + year + "/" + (month + 1) + "/" + day + "/";
//			File diskFile = new File(uploadPath);
//			if (!diskFile.exists()) {
//				diskFile.mkdirs();
//			}
//			uploadPath = uploadPath + ui.getOpenid() + ".png";
//			imageGenerator.saveAsImage(uploadPath);
//			return uploadPath;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return null;
//	}
//	public static List<String> getStr2(UserInfo ui) {
//		URL hotUrl = null;
//		List<String> list = null;
//		if (ui.getSex() == 2) {
//			hotUrl = Thread.currentThread().getContextClassLoader().getResource("woman-keyword.txt");
//		} else {
//			hotUrl = Thread.currentThread().getContextClassLoader().getResource("man-keyword.txt");
//		}
//		if (hotUrl != null) {
//			try {
//				String strs = CommonFile.readTxt(URLDecoder.decode(hotUrl.getPath(), "UTF-8"), "GBK");
//				Random r = new Random();
//				System.out.println("HotKeyAPI.getStr2() ==>" + strs);
//				String[] strArray = strs.split(" ");
//				list = new ArrayList<>();
//				int i = 10;
//				while (i > 0) {
//					Integer index = r.nextInt(strArray.length);
//					String value = strArray[index];
//					if (!list.contains(value)) {
//						list.add(value);
//						i--;
//					}
//				}
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return list;
//	}
//	public static String getImgUrlByUserInfo2(UserInfo ui,String officialNumber) {
//		System.out.println("weixin headImgUrl2 ==>" + ui.getHeadimgurl());
//		if (StringUtils.isNotBlank(ui.getHeadimgurl()) && ui.getHeadimgurl().contains("http://wx.qlogo.cn")) {
//			String imgStr = ui.getHeadimgurl().substring(ui.getHeadimgurl().length() - 2);
//			if (imgStr.equals("/0")) {
//				ui.setHeadimgurl(ui.getHeadimgurl().substring(0, ui.getHeadimgurl().length() - 2) + "/132");
//			}
//		}
//		return htmlToImg2(getStr2(ui), ui,officialNumber);
//	}
//	public static String htmlToImg2(List<String> list, UserInfo ui,String officialNumber) {
//		LinkedList<Float> sizeList = new LinkedList<>();
//		sizeList.add(1F);
//		sizeList.add(0.6F);
//		sizeList.add(0.6F);
//		sizeList.add(0.6F);
//		sizeList.add(0.5F);
//		sizeList.add(0.5F);
//		sizeList.add(0.4F);
//		sizeList.add(0.3F);
//		sizeList.add(0.2F);
//		sizeList.add(0.1F);
//		try {
//			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//			StringBuffer sb = new StringBuffer();
//			sb.append("<html>").append("<head>").append("<meta charset='UTF-8'/>").append("<style>")
//					.append("html,body,div,ul,li,a{margin:0;padding:0;}").append("</style>").append("</head>")
//					.append("<body style='height: 756px;width: 545px;margin: 0px auto;'>")
//					.append("<div style='height: 960px;width: 545px;margin: 0px auto;background: url(");
//			sb.append(SysConfig.FILE_VIEW_PATH_H5);
//			//sb.append("http://cdn.files-beta.51wyq.cn/h5");
//			if(Constants.SERVERNUMBER.equals(officialNumber)) {
//				sb.append("/images/wxActivity/xnzf1.jpg) no-repeat;'>");
//			}else {
//				sb.append("/images/wxActivity/xnzf3.jpg) no-repeat;'>");
//			}
//
//			sb.append("<div style = 'width:545px;height:240px;background:url(" + ui.getHeadimgurl()
//					+ ")  no-repeat center;'>");
//			sb.append("<div style='width: 545px;height: 185px;padding-top:0px;background: url(");
//			sb.append(SysConfig.FILE_VIEW_PATH_H5);
//			//sb.append("http://cdn.files-beta.51wyq.cn/h5");
//			sb.append("/images/wxActivity/head.png) no-repeat;'></div>");
//			sb.append(
//					"<div style='text-align: center;font-size: 28px;color:#62cade;margin-top: 10px;font-family:方正稚艺简体;'>"
//							+ ui.getNickname() + "</div></div>");
//			sb.append("<div style='position: absolute;width:520px;margin:165px 65px 0px;text-align:center;'>");
//			sb.append("<ul>");
//			Random r = new Random();
//			for (int i = 0; i < list.size(); i++) {
//				sb.append("<a style='color:#94f9ff;font-size: " + (int) (sizeList.remove(r.nextInt(sizeList.size())) * 50 + 14)
//						+ "px;font-family:方正稚艺简体';>&nbsp;" + list.get(i) + "&nbsp;</a>");
//			}
//			sb.append("</ul>");
//			sb.append("</div>");
//			sb.append("</div>");
//			sb.append("</body>");
//			sb.append("</html>");
//			imageGenerator.loadHtml(sb.toString());
//			Dimension dimension = new Dimension(756, 545);
//			imageGenerator.setSize(dimension);
//			String uploadPath = SysConfig.FILE_UPLOAD_PATH;
//			Calendar cal = Calendar.getInstance();
//			int year = cal.get(Calendar.YEAR);// 获取年份
//			int month = cal.get(Calendar.MONTH);// 获取月份
//			int day = cal.get(Calendar.DATE);// 获取日
//			int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
//			int minute = cal.get(Calendar.MINUTE);// 获取分钟
//			uploadPath = uploadPath + "weixin" + "/" + year + "/" + (month + 1) + "/" + day + "/" + hour + "/" + minute
//					+ "/";
//			File diskFile = new File(uploadPath);
//			if (!diskFile.exists()) {
//				diskFile.mkdirs();
//			}
//			uploadPath = uploadPath + ui.getOpenid() + ".png";
//			imageGenerator.saveAsImage(uploadPath);
//			return uploadPath;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return null;
//	}
//	//狗年H5词库抓取
//	public static List<String> getStr3(UserInfo ui) {
//		URL hotUrl = null;
//		List<String> list = null;
//		if (ui.getSex() == 2) {
//			hotUrl = Thread.currentThread().getContextClassLoader().getResource("children-woman.txt");
//		} else {
//			hotUrl = Thread.currentThread().getContextClassLoader().getResource("children-man.txt");
//		}
//		if (hotUrl != null) {
//			try {
//				String strs = CommonFile.readTxt(URLDecoder.decode(hotUrl.getPath(), "UTF-8"), "GBK");
//				Random r = new Random();
//				System.out.println("HotKeyAPI.getStr4() ==>" + strs);
//				String[] strArray = strs.split("#");
//				list = new ArrayList<>();
//				int i = 1;
//				while (i > 0) {
//					Integer index = r.nextInt(strArray.length);
//					String value = strArray[index].trim();
//					if (!list.contains(value)) {
//						list.add(value);
//						i--;
//					}
//				}
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return list;
//	}
//
//	public static String getImgUrlByUserInfo4(UserInfo ui,String officialNumber) {
//		System.out.println("weixin headImgUrl4 ==>" + ui.getHeadimgurl());
//		if (StringUtils.isNotBlank(ui.getHeadimgurl()) && ui.getHeadimgurl().contains("http://wx.qlogo.cn")) {
//			String imgStr = ui.getHeadimgurl().substring(ui.getHeadimgurl().length() - 2);
//			if (imgStr.equals("/0")) {
//				ui.setHeadimgurl(ui.getHeadimgurl().substring(0, ui.getHeadimgurl().length() - 2) + "/132");
//			}
//		}
//		return htmlToImg4(getStr4(ui), ui,officialNumber);
//	}
//
//	public static String htmlToImg4(List<String> list, UserInfo ui,String officialNumber) {
//
//		try {
//			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//			StringBuffer sb = new StringBuffer();
//			sb.append("<html><head><meta charset='utf-8'/><style>html,body,div,ul,li,a{margin:0;padding:0;}</style></head><body>");
//			sb.append("<div style='background:url(");
//			sb.append(SysConfig.cfgMap.get("FILE_VIEW_PATH_H5"));
//			if (BusinessConstant.SUBSCRIPTIONNUMBER.equals(officialNumber)) {
//				sb.append("/images/wxActivity/children.jpg) no-repeat;margin: 0px auto; width:490px; height:870px;'>");
//			}else if (BusinessConstant.SERVERNUMBER.equals(officialNumber)) {
//				sb.append("/images/wxActivity/children.jpg) no-repeat;margin: 0px auto; width:490px; height:870px;'>");
//			}
//			sb.append("<div style='text-align: left;font-size: 45px;padding-top: 300px;padding-left: 65px;'>");
//			sb.append("<font style='color: white;font-weight: bold;font-family: ΢���ź�;'>");
//			if (ui.getNickname().length()<=4) {
//				sb.append("<font style='font-size: 70px;'>"+ui.getNickname()+"</font>");
//			}else{
//				sb.append("<font style='font-size: 45px;'>"+ui.getNickname()+"</font>");
//			}
//			for (int i = 0; i < list.size(); i++) {
//				sb.append(""+list.get(i)+"");
//			sb.append("<br>");
//			sb.append("<div style='background:url("+ui.getHeadimgurl()
//			+")  no-repeat ;width: 150px;height: 140px;padding-left: 120px;margin: 20px 50px 0px 50px;'>");
//			sb.append("<div style='padding-top: 30px;padding-right: 100px;font-size: 25px;font-family: 站酷快乐体2016修订版;'>"
//					+ ui.getNickname() + "</div></div>");
//			sb.append("<div style='text-align: center;font-size: 28px;padding-top: 0px;padding-right: 15px;margin: 20px 50px;'><ul>");
//			Random r = new Random();
//			for (int i = 0; i < list.size(); i++) {
//				if (img==0) {
//					//牧羊犬
//					sb.append("<a style='color: #ffae12; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==1) {
//					//泰迪
//					sb.append("<a style='color: #ffbe85; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==2) {
//					//金毛
//					sb.append("<a style='color: #ffbe84; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==3) {
//					//拉布拉多
//					sb.append("<a style='color: #ffffff; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==4) {
//					//吉娃娃
//					sb.append("<a style='color: #ffe9ac; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==5) {
//					//萨摩耶
//					sb.append("<a style='color: #ffb6c0; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==6) {
//					//中华田园犬
//					sb.append("<a style='color: #2e4f76; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==7) {
//					//博美
//					sb.append("<a style='color: #176ece; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}else if (img==8) {
//					//哈士奇
//					sb.append("<a style='color: #ffe25a; font-size:"+ (int) (sizeList.remove(r.nextInt(sizeList.size())) * 30 + 9)+"px;font-family: 站酷快乐体2016修订版;'>"+list.get(i)+"&nbsp;</a>");
//				}
//			}
//			sb.append("</font></div></div></body></html>");
//
//			imageGenerator.loadHtml(sb.toString());
//			Dimension dimension = new Dimension(800, 450);
//			imageGenerator.setSize(dimension);
//			String uploadPath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH");
//			Calendar cal = Calendar.getInstance();
//			int year = cal.get(Calendar.YEAR);//
//			int month = cal.get(Calendar.MONTH);//
//			int day = cal.get(Calendar.DATE);//
//			int hour = cal.get(Calendar.HOUR_OF_DAY);//
//			int minute = cal.get(Calendar.MINUTE);//
//			uploadPath = uploadPath + "weixin" + "/" + year + "/" + (month + 1) + "/" + day + "/" + hour + "/" + minute
//					+ "/";
//			File diskFile = new File(uploadPath);
//			if (!diskFile.exists()) {
//				diskFile.mkdirs();
//			}
//			uploadPath = uploadPath + ui.getOpenid() + ".png";
//			imageGenerator.saveAsImage(uploadPath);
//			return uploadPath;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return null;
//	}
//
//
//
//	public static void main(String[] args) {
//		/*StringBuffer sb = new StringBuffer();
//		sb.append("<html><head><meta charset='utf-8'/><style>html,body,div,ul,li,a{margin:0;padding:0;}</style></head><body>");
//		sb.append("<div style='background:url(");
//		sb.append(SysConfig.FILE_VIEW_PATH_H5);
//		sb.append("/images/wxActivity/children.jpg) no-repeat;margin: 0px auto; width:490px; height:870px;'>");
//		sb.append("<div style='text-align: left;font-size: 45px;padding-top: 300px;padding-left: 65px;'>");
//		sb.append('<font style="font-weight: bold;font-family: ΢���ź�;color: white;">');
//		sb.append("<font style='font-size: 45px;'>�ܽ���������</font>");
//		UserInfo userInfo = new UserInfo();
//		List<String> list = getStr4(userInfo);
//		for (int i = 0; i < list.size(); i++) {
//			sb.append(""+list.get(i)+"");
//		}
//		sb.append("</font></div></div></body></html>");
//		System.out.println(sb.toString());*/
//
//		/* HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//		 Dimension dimension = new Dimension(800, 450);
//		 imageGenerator.setSize(dimension);
//		 imageGenerator.loadUrl("http://127.0.0.1:8020/weixinImage/index.html?__hbt=1527499408775");
//		 imageGenerator.saveAsImage("C:/image/dog111.png");
//		 System.out.println("....");*/
//	}
//}

    //六一H5词库抓取
    public static List<String> getStr4(UserInfo ui) {
        URL hotUrl = null;
        List<String> list = null;
        if (ui.getSex() == 2) {
            hotUrl = Thread.currentThread().getContextClassLoader().getResource("children-woman.txt");
        } else {
            hotUrl = Thread.currentThread().getContextClassLoader().getResource("children-man.txt");
        }
        if (hotUrl != null) {
            try {
                String strs = CommonFile.readTxt(URLDecoder.decode(hotUrl.getPath(), "UTF-8"), "GBK");
                Random r = new Random();
                System.out.println("HotKeyAPI.getStr4() ==>" + strs);
                String[] strArray = strs.split("#");
                list = new ArrayList<>();
                int i = 1;
                while (i > 0) {
                    Integer index = r.nextInt(strArray.length);
                    String value = strArray[index].trim();
                    if (!list.contains(value)) {
                        list.add(value);
                        i--;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String getImgUrlByUserInfo4(UserInfo ui,String officialNumber) {
        System.out.println("weixin headImgUrl4 ==>" + ui.getHeadimgurl());
        if (StringUtils.isNotBlank(ui.getHeadimgurl()) && ui.getHeadimgurl().contains("http://wx.qlogo.cn")) {
            String imgStr = ui.getHeadimgurl().substring(ui.getHeadimgurl().length() - 2);
            if (imgStr.equals("/0")) {
                ui.setHeadimgurl(ui.getHeadimgurl().substring(0, ui.getHeadimgurl().length() - 2) + "/132");
            }
        }
        return htmlToImg4(getStr4(ui), ui,officialNumber);
    }

    public static String htmlToImg4(List<String> list, UserInfo ui,String officialNumber) {

        try {
            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
            StringBuffer sb = new StringBuffer();
            sb.append("<html><head><meta charset='utf-8'/><style>html,body,div,ul,li,a{margin:0;padding:0;}</style></head><body>");
            sb.append("<div style='background:url(");
            sb.append(SysConfig.cfgMap.get("FILE_VIEW_PATH_H5"));
            if (BusinessConstant.SUBSCRIPTIONNUMBER.equals(officialNumber)) {
                sb.append("/images/wxActivity/children.jpg) no-repeat;margin: 0px auto; width:490px; height:870px;'>");
            }else if (BusinessConstant.SERVERNUMBER.equals(officialNumber)) {
                sb.append("/images/wxActivity/children.jpg) no-repeat;margin: 0px auto; width:490px; height:870px;'>");
            }
            sb.append("<div style='text-align: left;font-size: 45px;padding-top: 300px;padding-left: 65px;'>");
            sb.append("<font style='color: white;font-weight: bold;font-family: 微软雅黑;'>");
            if (ui.getNickname().length()<=4) {
                sb.append("<font style='font-size: 70px;'>"+ui.getNickname()+"</font>");
            }else{
                sb.append("<font style='font-size: 45px;'>"+ui.getNickname()+"</font>");
            }
            for (int i = 0; i < list.size(); i++) {
                sb.append(""+list.get(i)+"");
            }
            sb.append("</font></div></div></body></html>");

            imageGenerator.loadHtml(sb.toString());
            Dimension dimension = new Dimension(800, 450);
            imageGenerator.setSize(dimension);
            String uploadPath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH");
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);//
            int month = cal.get(Calendar.MONTH);//
            int day = cal.get(Calendar.DATE);//
            int hour = cal.get(Calendar.HOUR_OF_DAY);//
            int minute = cal.get(Calendar.MINUTE);//
            uploadPath = uploadPath + "weixin" + "/" + year + "/" + (month + 1) + "/" + day + "/" + hour + "/" + minute
                    + "/";
            File diskFile = new File(uploadPath);
            if (!diskFile.exists()) {
                diskFile.mkdirs();
            }
            uploadPath = uploadPath + ui.getOpenid() + ".png";
            imageGenerator.saveAsImage(uploadPath);
            return uploadPath;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    public static void main(String[] args) {
		/*StringBuffer sb = new StringBuffer();
		sb.append("<html><head><meta charset='utf-8'/><style>html,body,div,ul,li,a{margin:0;padding:0;}</style></head><body>");
		sb.append("<div style='background:url(");
		sb.append(SysConfig.FILE_VIEW_PATH_H5);
		sb.append("/images/wxActivity/children.jpg) no-repeat;margin: 0px auto; width:490px; height:870px;'>");
		sb.append("<div style='text-align: left;font-size: 45px;padding-top: 300px;padding-left: 65px;'>");
		sb.append('<font style="font-weight: bold;font-family: 微软雅黑;color: white;">');
		sb.append("<font style='font-size: 45px;'>周杰伦你好你好</font>");
		UserInfo userInfo = new UserInfo();
		List<String> list = getStr4(userInfo);
		for (int i = 0; i < list.size(); i++) {
			sb.append(""+list.get(i)+"");
		}
		sb.append("</font></div></div></body></html>");
		System.out.println(sb.toString());*/

		/* HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		 Dimension dimension = new Dimension(800, 450);
		 imageGenerator.setSize(dimension);
		 imageGenerator.loadUrl("http://127.0.0.1:8020/weixinImage/index.html?__hbt=1527499408775");
		 imageGenerator.saveAsImage("C:/image/dog111.png");
		 System.out.println("....");*/
    }
}
