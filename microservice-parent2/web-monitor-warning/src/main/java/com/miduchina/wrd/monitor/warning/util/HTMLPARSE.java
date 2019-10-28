package com.miduchina.wrd.monitor.warning.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLPARSE {


	public static String parseChar(String sor)
	{
		String aims="";
		if(sor==null||sor.equals(""))
		{
			return aims;
		}else
		{
			aims=sor.replaceAll("\\\\r\\\\n", "<br/>");
			aims = aims.replaceAll("<br/>\\\\n", "<br/>");
			aims=aims.replaceAll("\\\\n", "<br/>");
			aims=aims.replaceAll("\r", "<br/>");	
			aims=aims.replaceAll("\n", "<br/>");
			aims=aims.replaceAll("　　", "<br/>");
			aims=aims.replaceAll("<p>", " ");
			aims=aims.replaceAll("</p>", "<br/>");
			aims=aims.replaceAll("<br/><br/><br/>", "<br/>");
			aims=aims.replaceAll("<br/><br/>", "<br/>");
			
			
		}
		return aims;
	}
	public static String getTitle(String content) {
		String title = "";
		Pattern sip = Pattern.compile("(【[^】]*】)"); // 查找[]中的标题
		Matcher mp = sip.matcher(content);
		while (mp.find()) {
			title = mp.group(1);
			break;
		}

		if (title.equals("【原微博】")){
			title="";
		}
		if (title.equals("") && content.length() > 15) {
			title = content.substring(0, 15) + "...";

		} else if (title.equals("") && content.length() < 15) {
			title = content;

		}
		return title;

	}
	
	public static String parseTitle(String sor)
	{
		//处理首页列表中的 title中的字符
		String aims="";
		if(sor==null||sor.equals(""))
		{
			return aims;
		}else
		{
			aims=sor.replaceAll("\\\\r\\\\n", "");	
			aims=aims.replaceAll("\\\\n", "");	
			aims=aims.replaceAll("<br/>", "");
			aims=aims.replaceAll("<BR/>", "");
			aims=aims.replaceAll("</br>", "");
			aims=aims.replaceAll("</br", "");
			aims=aims.replaceAll("<br>", "");
		}
		return aims;
	}
	public static String parseHighLight(String sor,String highLightWord){
		
		String aims="";
		if(sor==null||sor.equals(""))
		{
			return aims;
		}else if (!highLightWord.equals("")){
			highLightWord = highLightWord.trim();
			
			//aims = sor.replace(highLightWord, "<span style=\"color:red;background-color:#fcff00;font-style:italic;font-weight:bold\">" + highLightWord +"</span>");
			//aims = sor.replace(highLightWord, "<span class=\"fontHighlight\">" + highLightWord +"</span>");
			
			aims = sor.replace(highLightWord, "<font color=\"red\">" + highLightWord +"</font>");
			
			String highLightWordUpperCase = highLightWord.toUpperCase();
			
			if (!highLightWordUpperCase.equals(highLightWord)){
			
			//匹配(等特殊符号\\
			//	aims = aims.replace(highLightWordUpperCase, "<span  class=\"fontHighlight\">" + highLightWordUpperCase +"</span>");
				
				aims = aims.replace(highLightWordUpperCase, "<font color=\"red\">" + highLightWordUpperCase +"</font>");
			}
		}
		
		return aims;
	}
	
	public static String removeForwardContent(String sor ){
		
		String aims="";
		aims =  sor;
		if(sor==null||sor.equals(""))
		{
			return aims;
		}else if (aims.indexOf("【原微博】")>0){
			
			 int pos = aims.indexOf("【原微博】");
			 aims = aims.substring(0,pos);
		 
		}
		
		return aims;
	}
	public static String getCorrectString(String str) {
		// 去重
		String reg = "\\({1}[^\\+\\|\\(\\)]*\\)";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			str = str.replace(matcher.group(), matcher.group().replaceAll(
					"[()]", ""));
			matcher = pattern.matcher(str);
		}
		return str;
		
	}
	public static String filterSummary(String summary) {
		// 过滤列表中的摘要，1.过滤iframe，2.过滤最后一个<
		if (null == summary || summary.length() == 0) {

			return "";

		}
		
		summary=summary.replaceAll("\\\\r\\\\n", "");	
		summary = summary.replace("<table", ""); // 过滤Iframe
		summary = summary.replace("</table>", ""); // 过滤Iframe
		summary = summary.replace("<div>", ""); // 过滤Iframe
		summary = summary.replace("</div>", ""); // 过滤Iframe
		/*
		 * 获取摘要中的高亮词
		String[]  highWords = getHighLightWord(summary);
		
		//去除HTML标签
		summary = removeHTMLTag(summary);
		
		//高亮词匹配
		for (int j = 0; j < 30; j++) {
			if  (highWords[j]!=null){
				summary = HTMLPARSE.parseHighLight(summary, highWords[j]);
			}
		}
		//2014-12-16日 jar中 描述中已做去HTML处理，不自己做去除
		*/
		summary = summary.replace("　", " ");
		summary = summary.replace("<iframe", ""); // 过滤Iframe
		summary = summary.replace("<IFRAME", ""); // 过滤Iframe
		summary = summary.replace("xml:namespace?prefix=\"o\"?ns=\"urn:schemas-microsoft-com:office:office\">", "" ) ;
		summary = summary.replace("</?xml:namespace>","");
		summary = summary.replace("</IFRAME", ""); // 过滤Iframe
		summary = summary.replace("<IFRAME", ""); // 过滤Iframe
		// 摘要去除多的换行
		summary = summary.replace("<p>", ""); // 过滤Iframe
		summary = summary.replace("</p>", "<br>"); // 过滤Iframe

		summary = summary.trim();
		if (summary.indexOf("<br>") == 0) {
			summary = summary.substring(4, summary.length());

		}
		if (summary.indexOf("<br>") == 0) {
			summary = summary.substring(4, summary.length());

		}
		/*
		 * if (summary.length()>100){ //控制在100字以内 summary = summary.substring(0,
		 * 100); }
		 */

		int lpos = 0;
		if (summary.lastIndexOf("<") != -1) {
			lpos = summary.lastIndexOf("<");
		}
		int bpos = 0;
		bpos = summary.lastIndexOf(">");

		if (lpos > 0 && lpos > bpos) {

			summary = summary.substring(0, lpos) + summary.substring(lpos + 1);
		}

		return summary;
	}
	
	//替换高亮为<span class=\"fontHighlight\"></span>
	public static String replaceHighLight(String summary){
		
 
		if (summary.indexOf("<font color=\"red")>=0){
		 	//logger.info("  highlight title =  "+ title  );
			String regex = "<font color=\"red\" >(.*?)</font>";
	    	Pattern	pattern = Pattern.compile(regex); 
			Matcher matcher = pattern.matcher(summary); 
			 
			while (matcher.find()) { 
				//String rstr = matcher.group(0);
				
				//String replaceStr = rstr.replace("<font color=\"red\" >", "<span class=\"fontHighlight\">");
				//replaceStr = replaceStr.replace("</font>", "</span>");
				//summary=summary.replace(rstr, replaceStr);
				 
				 
			}
		}
		return summary;
		 
	}
	
	
	
	 public static String removeHTMLTag(String htmlStr) {
    	 java.util.regex.Pattern p_html1;   
    	 java.util.regex.Matcher m_html1; 
    	 java.util.regex.Pattern p_html;   
    	 java.util.regex.Matcher m_html; 
    	 String textStr="";
    	 try {   
    		 String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式   
    		 String regEx_html1 = "<[^>]+";   

    		 	p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);   
    	        m_html = p_html.matcher(htmlStr);   
    	         htmlStr = m_html.replaceAll(""); //过滤html标签   
                 
    	        p_html1 = Pattern.compile(regEx_html1,Pattern.CASE_INSENSITIVE);   
    	            m_html1 = p_html1.matcher(htmlStr);   
              htmlStr = m_html1.replaceAll(""); //过滤html标签   
              textStr = htmlStr;   
                     
                 }catch(Exception e) {   
                         System.err.println("Html2Text: " + e.getMessage());   
              }   
              
                 return textStr;//返回文本字符串 

}
	public static String urlToLink(String urlText){   
	    // 匹配的条件选项为结束为空格(半角和全角)、换行符、字符串的结尾或者遇到其他格式的文本   
	    String regexp   
	        = "(((http|ftp|https|file)://)|((?<!((http|ftp|https|file)://))www\\.))"  // 以http...或www开头   
	        + ".*?"                                                                   // 中间为任意内容，惰性匹配   
	        + "(?=(&nbsp;|\\s|　|<br />|$|[<>]))";                                     // 结束条件   
	    Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);   
	    Matcher matcher = pattern.matcher(urlText);   
	    StringBuffer stringbuffer = new StringBuffer();   
	    while(matcher.find()){   
	        String url = matcher.group().substring(0, 3).equals("www") ? "http://" + matcher.group() : matcher.group();   
	        String tempString = "<a href=\"" + url + "\" target=\"_blank\">" + matcher.group() + "</a>";   
	        // 这里对tempString中的"\"和"$"进行一次转义，因为下面对它替换的过程中appendReplacement将"\"和"$"作为特殊字符处理   
	        int tempLength = tempString.length();   
	        StringBuffer buffer = new StringBuffer();   
	        for(int i = 0; i < tempLength; ++i){   
	            char c = tempString.charAt(i);   
	            if(c == '\\' || c == '$'){   
	                buffer.append("\\").append(c);   
	            } else {   
	                buffer.append(c);   
	            }   
	        }   
	        tempString = buffer.toString();   
	        matcher.appendReplacement(stringbuffer, tempString);   
	    }   
	    matcher.appendTail(stringbuffer);   
	    return stringbuffer.toString();   
	}   
	
	public static double getMathValue(double d){
		int b = (int)Math.round(d * 100); 
    	d = (double)b/100.00;
    	
		return d;
	}
	public static int conLengthPage = 500;
	public static int pgConLengthPage = 25;  //每页新闻条数
	
	
	
	public static void main(String args[]){ 
//			String content ="治部原顾问陈茂辉同志（副兵团职），因病医治无效，于３月２３日在 南京</span>逝世，享年１０３岁。\r\n陈茂辉是福建上杭人，１９２９年加入中国共产主义青年团，同年参加中国工农红军，１９３１年加入中国共产党。土地革命战争时期，他历任排长、队长、室主任、连副政治指导员、连政治指导员、营政治委员、科长、区委书记兼大队政治委员、大队主任兼政治委员、区委书记、县委书记等职，参加了虎岗、黎川、广安、广昌、温坊和中央苏区第一至第五次反“围剿”等战役战斗。抗日战争时期，他历任科长、营政治教导员、副团长、师兵工厂政治委员、分区参谋长、团政治委员等职。解放战争时期，他历任师政治部副主任、主任、政治委员等职，参加了淮海、渡江、孟良崮、莱芜、苏中七战七捷、上海、杭州、高邮、淮阴、盐城、碾庄、枣庄、费县、邹县等战役战斗。新中国成立后，他历任军政治部主任、副政治委员、政治委员，江苏省军区副政治委员、第三政治委员、顾问等职，参加了抗美援朝，为部队革命化、现代化、正规化建设作出了贡献。\r\n陈茂辉１９５５年被授予少将军衔，曾荣获二级八一勋章、二级独立自由勋章、一级解放勋章和一级红星功勋荣誉章。\r\n\r\n\r\n原标题";
//			
//			String summary ="<font color=\"red\" >游</font><font color=\"red\" >骂</font><font color=\"red\" >人</font>肯定是不人对的，";
//			if (summary.indexOf("<font color=\"red")>=0){
//				 	//logger.info("  highlight title =  "+ title  );
//					String regex = "<font color=\"red\" >(.*?)</font>";
//			    	Pattern	pattern = Pattern.compile(regex); 
//					Matcher matcher = pattern.matcher(summary); 
//					String[] hl = new String[50];
//					int hll = 0;
//					while (matcher.find()) { 
//						String rstr = matcher.group(0);
//						
//						String replaceStr = rstr.replace("<font color=\"red\" >", "<span class=\"fontHighlight\">");
//						replaceStr = replaceStr.replace("</font>", "</span>");
//						 summary=summary.replace(rstr, replaceStr);
//						 
//						 
//					}
//					 
//			 }
			
			//content= parseChar(content);
			
			//System.out.print(summary);
			
			
			String opUserCookieName="b4ww_1313524283866_4075";
			int pos = opUserCookieName.indexOf("_");
			String userStr = opUserCookieName.substring(pos+1);
			pos = userStr.indexOf("_");
			
			userStr = userStr.substring(0,pos);
			userStr = userStr.substring(0,userStr.length()-11);
			
			System.out.print(userStr);
			
	}
	
	public static String getTitle30(String content){
		String title = "";
		Pattern sip = Pattern.compile("(【[^】]*】)"); // 查找[]中的标题
		Matcher mp = sip.matcher(content);
		while (mp.find())
		{
			title = mp.group(1);
			if ("【原微博】".equals(title))
			{
				continue;
			}
			title = title.replaceAll("[【|】]", "");
			break;
		}

		if (title.equals("【原微博】"))
		{
			title = "";
		}
		if (title.equals("") && content.length() > 30)
		{
			title = content.substring(0, 30) + "...";

		}
		else if (title.equals("") && content.length() < 30)
		{
			title = content;

		}
		return title;

	}
	
	
}
