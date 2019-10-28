package com.miduchina.wrd.eventanalysis.constant;

import org.springframework.beans.factory.annotation.Value;

public class Flags {



	public final static String error_view="/view/error/error";

	public final static String login_view="redirect:/userLogin";

	public static final String Request_PlatformTag="wyq";

	public static final String CompetAnalysis_File_PositiveWord="positiveWord.txt";//竞品分析-高频词汇

	/**
	 * 微博传播效果分析固化文件名称
	 */
	//网友观点分析
	public static final String netFriendView="netFriendView";

	public static String filePath;

	public static boolean local_flag;
}
