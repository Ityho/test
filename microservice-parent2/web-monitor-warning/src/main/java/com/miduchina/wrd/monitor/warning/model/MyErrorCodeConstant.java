package com.miduchina.wrd.monitor.warning.model;


import java.util.HashMap;
import java.util.Map;

public class MyErrorCodeConstant {
	public static final String T_REQUEST_HANDLE_SUCCESS = "0000";
	public static final String T_REQUEST_HANDLE_FAILURE = "9999";

	// ϵͳ������Ҫ�Լ���
	// 101-999
	public static final String F_LACK_RIGHT_SYSTEM_PARAM = "101";
	public static final String F_LACK_RIGHT_FORMAT = "102";

	// ��������״̬У����
	// 1001-1999
	public static final String F_LACK_INVALID_INPUT_PAGE = "1301";
	public static final String F_LACK_INVALID_INPUT_PAGE_SIZE = "1302";
	public static final String F_LACK_INVALID_INPUT_NUM = "1303";
	
	public static final String F_LACK_INVALID_INPUT_FIMALYNUM = "1401";

	// ������״̬
	// 2001-2099

	// ҵ�������Ҫ�Լ���
	// 2101-2199
	public static final String F_LACK_NECESSARY_INPUT_TICKET = "2101";
	
	// 2201-2299
	public static final String F_LACK_NECESSARY_INPUT_PLATFORM = "2201";
	public static final String F_LACK_NECESSARY_INPUT_CHANNEL = "2202";
	public static final String F_LACK_NECESSARY_INPUT_USERTAG = "2203";
	public static final String F_LACK_NECESSARY_INPUT_KEYWORD = "2204";
	public static final String F_LACK_NECESSARY_INPUT_FILTERKEYWORD = "2205";
	
	// 2301-2399
	public static final String F_LACK_NECESSARY_INPUT_PAGE = "2301";
	public static final String F_LACK_NECESSARY_INPUT_PAGE_SIZE = "2302";
	public static final String F_LACK_NECESSARY_INPUT_NUM = "2303";
	
	// 2401-2499
	public static final String F_LACK_NECESSARY_INPUT_NICKNAME = "2401";
	public static final String F_LACK_NECESSARY_INPUT_WEIBOID_ID = "2402";
	public static final String F_LACK_NECESSARY_INPUT_FIMALYNUM = "2403";

	// �������ݴ�����
	// 3001-3999

	// ��������У��
	// 4001-4999

	public static final Map<String, String> messageMap = new HashMap<String, String>();

	static {
		
		messageMap.put(MyErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS, "������������Ӧ");
		messageMap.put(MyErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, "������ʧ��");

		messageMap.put(MyErrorCodeConstant.F_LACK_RIGHT_SYSTEM_PARAM, "���ύ��ȷ��ϵͳ����");
		messageMap.put(MyErrorCodeConstant.F_LACK_RIGHT_FORMAT, "���ύ��ȷ�ķ��ظ�ʽ(format)");

		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_PLATFORM, "���ύƽ̨��ʶ(platform)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_CHANNEL, "���ύ������ʶ(channel)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_USERTAG, "���ύ�û���ʶ(userTag)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_KEYWORD, "���ύ�ؼ���(keyword)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_FILTERKEYWORD, "���ύ�ų��ؼ���(filterkeyword)");
		
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_PAGE, "���ύҳ����(page)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_PAGE_SIZE, "���ύÿҳ����(pagesize)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_NUM, "���ύ��������(num)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_FIMALYNUM, "���ύ���ش�Ⱥ����(fimalyNum)");
		
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_NICKNAME, "���ύ΢���û��ǳ�(nickName)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_WEIBOID_ID, "���ύ΢��ID(weiboId)");
		
		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_PAGE, "ҳ����(page)������Ч��Χ��");
		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_PAGE_SIZE, "����ÿҳ����(pagesize)������Ч��Χ��");
		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_NUM, "��������(num)������Ч��Χ��");
		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_FIMALYNUM, "���ش�Ⱥ����(fimalyNum)������Ч��Χ��");
		
	}

	public static String getMsg(String code) {
		return messageMap.get(code);
	}
}
