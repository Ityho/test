package com.miduchina.wrd.monitor.report.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件操作类
 *
 * @author liym
 */
public class CommonFile {
	/**
	 * 读取文本文件内容
	 *
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public static String readTxt(String filePathAndName, String encoding) throws IOException {
		encoding = encoding.trim();

		BufferedReader br = null;
		StringBuffer sb = new StringBuffer("");
		String st = "";

		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;

			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}

			br = new BufferedReader(isr);

			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					sb.append(data + " ");
				}
			} catch (Exception e) {
				sb.append(e.toString());
			}
			st = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();
		}

		return st;
	}
}
