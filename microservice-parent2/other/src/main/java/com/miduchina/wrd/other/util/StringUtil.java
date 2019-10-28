package com.miduchina.wrd.other.util;


import com.miduchina.wrd.common.redis.util.SysConfig;


import com.tuke.gospel.core.util.SimpleUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static Pattern PATTERN_EMOJI = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]",Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);


	//不可见的字符
	private static char[] input = new char[] { 0x7f, 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a,
			0x8b, 0x8c, 0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99,
			0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f, 0xad, 0x483, 0x484, 0x485, 0x486, 0x487, 0x488, 0x489,
			0x559, 0x55a, 0x58a, 0x591, 0x592, 0x593, 0x594, 0x595, 0x596, 0x597, 0x598, 0x599, 0x59a,
			0x59b, 0x59c, 0x59d, 0x59e, 0x59f, 0x5a0, 0x5a1, 0x5a2, 0x5a3, 0x5a4, 0x5a5, 0x5a6, 0x5a7,
			0x5a8, 0x5a9, 0x5aa, 0x5ab, 0x5ac, 0x5ad, 0x5ae, 0x5af, 0x5b0, 0x5b1, 0x5b2, 0x5b3, 0x5b4,
			0x5b5, 0x5b6, 0x5b7, 0x5b8, 0x5b9, 0x5ba, 0x5bb, 0x5bc, 0x5bd, 0x5bf, 0x5c1, 0x5c2, 0x5c4,
			0x5c5, 0x5c6, 0x5c7, 0x606, 0x607, 0x608, 0x609, 0x60a, 0x63b, 0x63c, 0x63d, 0x63e, 0x63f,
			0x674, 0x6e5, 0x6e6, 0x70f, 0x76e, 0x76f, 0x770, 0x771, 0x772, 0x773, 0x774, 0x775, 0x776,
			0x777, 0x778, 0x779, 0x77a, 0x77b, 0x77c, 0x77d, 0x77e, 0x77f, 0xa51, 0xa75, 0xb44, 0xb62,
			0xb63, 0xc62, 0xc63, 0xce2, 0xce3, 0xd62, 0xd63, 0x135f, 0x200b, 0x200c, 0x200d, 0x200e,
			0x200f, 0x2028, 0x2029, 0x202a, 0x202b, 0x202c, 0x202d, 0x202e, 0x2044, 0x2071, 0xf701,
			0xf702, 0xf703, 0xf704, 0xf705, 0xf706, 0xf707, 0xf708, 0xf709, 0xf70a, 0xf70b, 0xf70c,
			0xf70d, 0xf70e, 0xf710, 0xf711, 0xf712, 0xf713, 0xf714, 0xf715, 0xf716, 0xf717, 0xf718,
			0xf719, 0xf71a, 0xfb1e, 0xfc5e, 0xfc5f, 0xfc60, 0xfc61, 0xfc62, 0xfeff, 0xfffc };


//	private static MemCachedClient client = MemCacheUtil.getInstance().getMemCachedClient();

	public static String filterErrorCharacter(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}

		String result = str;

		List<Character> charList = new ArrayList<Character>();

		char[] charArray = result.toCharArray();
		if (charArray != null && charArray.length > 0) {

			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];

				if (c >= '\uD800' && c <= '\uDBFF') {
					if (i == charArray.length - 1 || charArray[i + 1] < '\uDC00' || charArray[i + 1] > '\uDFFF') {
						continue;
					} else {
						charList.add(c);
					}
				} else if (c >= '\uDC00' && c <= '\uDFFF') {
					if (i == 0 || charArray[i - 1] < '\uD800' || charArray[i - 1] > '\uDBFF') {
						continue;
					} else {
						charList.add(c);
					}
				} else {
					FILTER_CHAR:

					for (int idx = 0; idx < input.length; idx++) {
						int ic = input[idx];

						if (c == ic) {
							break FILTER_CHAR;
						} else if (idx == input.length - 1) {
							charList.add(c);
						}
					}
				}
			}
		}


		char[] ca = new char[charList.size()];
		for (int i = 0; i < charList.size(); i++) {
			ca[i] = charList.get(i);
		}

		result = new String(ca);

//		client.set(md5, result);

		return result;
	}



	private static String encryptString(String metedata) {
		// String time = DateUtil.format(DateUtils.addMonths(new Date(System.currentTimeMillis()), 1), "yyyyMM");
		// System.out.println("encryptString time=[" + time + "]");
		String result = metedata;
		// System.out.println("encryptString result=[" + result + "]");
		// System.out.println("encryptString result1=[" + StringUtil.md5(result + "$$$$") + "]");
		result += "@" + StringUtil.md5(result + "$$$$").substring(0, 8);
		// System.out.println("encryptString result2=[" + result + "]");
		return result;
	}

	private static String decryptString(String metedata) {
		if (StringUtils.isBlank(metedata)) {
			return null;
		}
		String[] m = metedata.split("@");
		if (m.length > 1) {
			if (StringUtil.md5(m[0] + "$$$$").substring(0, 8).equalsIgnoreCase(m[1])) {
				return m[0];
				// Long validtime = new Long(StringUtils.substring(m[0], 0, 6));
				// Long newtime = new Long(DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMM"));
				// if (newtime <= validtime)
				// {
				// return m[0].substring(6, m[0].length());
				// }
			}
		}
		return null;
	}

	public static String md5(String origin) {
		if (StringUtils.isEmpty(origin)) {
			return origin;
		}
		return SimpleUtils.MD5Encode(origin);
	}
	public static void main(String[] args) {
		System.out.println(encryptString("522"));
	}

	public static String getRandomNum(int length) {
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		return stringBuilder.toString();
	}

	/**
	 * 根据用户ID获得userEncode
	 *
	 * @param userId
	 * @return
	 */
	public static String buildUserEncode(String userId) {
		String userEncode = null;
		try {
			userEncode = encryptString(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return userEncode;
	}

	/**
	 * 根据userEncode获得用户ID
	 *
	 * @param userEncode
	 * @return
	 */
	public static int parseUserEncode(String userEncode) {
		int userId = 0;
		try {
			userId = Integer.parseInt(decryptString(userEncode));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return userId;
	}

	/**
	 * 校验邮箱格式
	 */
	public static boolean isEmail(String email) {
		boolean result = false;

		try {
			String regExp = SysConfig.cfgMap.get("EMAIL_REGEX");
			if(StringUtils.isBlank(regExp)){
				regExp = "^([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
			}
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(email);
			result = m.find();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	/**
	 * 校验用户名 手机号或者小号
	 */
	public static boolean isUserName(String username) {
		boolean result = false;

		try {

			String regExp = SysConfig.cfgMap.get("USER_NAME_REGEX");
			if(StringUtils.isBlank(regExp)){
				regExp = "^([1][3-9]\\d{9})|(s_[0-9A-Za-z]{4,18})$";
			}
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(username);
			result = m.find();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}



	/**
	 * 校验手机号码格式
	 */
	public static boolean isMobile(String mobile) {
		boolean result = false;

		try {

			String regExp = SysConfig.cfgMap.get("MOBILE_NUMBER_REGEX");
			if(StringUtils.isBlank(regExp)){
				regExp = "^[1]([3|4|5|6|7|8][0-9])[0-9]{8}$";
			}
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(mobile);
			result = m.find();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

	/**
	 * 中文参数处理
	 *
	 * @param originalStr
	 * @returns
	 */
	public static String convertRequestStringV3(String originalStr) {
		String resultStr = originalStr;

		try {
			if (checkC3C2Error(originalStr)) {
				resultStr = SimpleUtils.stringConvert(originalStr, "ISO8859-1", "UTF-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return resultStr;
	}


	/**
	 * 中文参数处理
	 *
	 * @param originalStr
	 * @return
	 */
	public static String convertRequestString(String originalStr) {
		String resultStr = originalStr;

		try {
			resultStr = SimpleUtils.stringConvert(originalStr, "ISO8859-1", "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return resultStr;
	}


	public static void toHex(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			System.out.printf("%x ", b[i]);
		}
		System.out.println();
	}


	/**
	 * 判断字符串编码是否有C3C2的问题
	 * http://blog.zeerd.com/archives/585
	 *
	 * @param original
	 * @return
	 */
	private static Boolean checkC3C2Error(String original) {
		Boolean result = false;
		byte[] chars = original.getBytes(StandardCharsets.UTF_8);

		if (chars.length >= 2) {
			for (int i = 0; i < chars.length - 2; i++) {
				int char1 = chars[i] & 0xFF;
				int char2 = chars[i + 2] & 0xFF;
				if ((char1 == 0xC2 || char1 == 0xC3)
						&& (char2 == 0xC2 || char2 == 0xC3)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}


	/**
	 * 将数据库中文转换成系统日志中文
	 *
	 * @param originalStr
	 * @return
	 */
	public static String convertDB2LogString(String originalStr) {
		String resultStr = originalStr;

//		try {
//			resultStr = SimpleUtils.stringConvert(originalStr, "GBK", "ISO-8859-1");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		return resultStr;
	}


	/**
	 * @param originalStr
	 * @param logicChar   与（+），或（|）
	 * @return
	 */
	public static String filterString(String originalStr, String logicChar) {
		String resultStr = "";

		try {
			originalStr = originalStr.replaceAll("\\|", "");
			originalStr = originalStr.replaceAll("\\+", "");
			originalStr = originalStr.replaceAll("\r", " ");
			originalStr = originalStr.replaceAll("\n", " ");
			String[] resultStrArr = originalStr.split(" ");
			for (int i = 0; i < resultStrArr.length; i++) {
				String resultStrItem = resultStrArr[i];
				if (resultStrItem != null && resultStrItem.trim().length() > 0) {
					resultStr += resultStrItem.trim() + logicChar;
				}
			}
			if (resultStr != null && resultStr.trim().length() > 0 && resultStr.trim().endsWith(logicChar)) {
				resultStr = resultStr.trim().substring(0, resultStr.trim().length() - logicChar.length());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return resultStr;
	}

	/**
	 * 过滤Emoji表情
	 *
	 * @param str
	 * @return
	 */
	public static String filterEmoji(String str) {
		String result = str;
		if (str != null && str.trim().length() > 0) {
			Matcher m = PATTERN_EMOJI.matcher(str);
			result = m.replaceAll("").trim();
		}

		return result;
	}


	/**
	 * 将字符串转换成URLEncode
	 * RFC1738,   "-"、"_"、"."三个符号外的所有非数字，字母都需要转换成带％形式
	 *
	 * @param sourceStr
	 * @return
	 */
	public static String urlEncode(final String sourceStr) {
		String result = "";
		try {
			result = URLEncoder.encode(sourceStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = sourceStr;
		}

		result = result.replace("+", "%20");

		return result;
	}


	public static String urlEncodeGBK(final String sourceStr) {
		String result = "";
		try {
			result = URLEncoder.encode(sourceStr, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = sourceStr;
		}

		result = result.replace("+", "%20");

		return result;
	}

	/**
	 * 字符串转换unicode
	 */
	public static String stringUnicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			String u = Integer.toHexString(c);
			if (u.length() < 4) {
				int f = 4 - u.length();
				String p = "";
				for (int j = 0; j < f; j++) {
					p = p + "0";
				}
				u = p + u;
			}
			// 转换为unicode
			unicode.append("\\u" + u);
		}

		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicodeString(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/**
	 * 判断对象是否为空
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
            return true;
        }

		if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

		if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

		if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}


	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

    public static String stringWithNumber(long count) {
		String suffer = "";
		long result = count;
		if (count >= 10000000) {
			suffer = "M";
			result = count / 1000000;
		} else if (count >= 100000) {
			suffer = "K";
			result = count / 1000;
		}

		return result + suffer;
    }


    public static String stringWithSpace(long num) {
		String result = "";
		while (num > 0) {
			result = result + " ";
			--num;
		}

		return result;
	}

}
