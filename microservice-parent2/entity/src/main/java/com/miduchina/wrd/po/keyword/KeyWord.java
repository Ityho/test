package com.miduchina.wrd.po.keyword;

import lombok.Data;

import java.util.Date;

@Data
public class KeyWord implements java.io.Serializable {

	private static final long serialVersionUID = -3286564461647015367L;
	private Integer keywordId;
	private int userId;
	private String productCode;
	private String keywordName;
	private String keyword;
	private String filterKeyword;
	private String inputKeyword;
	private String inputFilterKeyword;

	private Date validEndDate;
	private int keywordPipeiType;
	private int filterKeywordPipeiType;

	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;

	private int status;
	private Date createTime;
	private Date updateTime;

	private int warningId;
	private int warningIsValid;
	private int keywordSequence;
	private int keywordType;
	private int provinceCount = 0;
	private int ksetCount = 0;
	private int monitorType = 0;
	private Integer type;
	private Integer referenceId;
	private Integer RemainingDays;
	private Integer modifyNum;

	private String programdaynum;// 监测方案到期天数


	public String getNamewStr() {
		if (keywordName != null && !"".equals(keywordName.trim())) {
			keywordName = keywordName.trim();
			int len = keywordName.length();
			if (len > 10) {// 10以上
				len = 10;
			}
			return keywordName.substring(0, len);
		}
		return keywordName;
	}

	private boolean overdue;

	public boolean isOverdue() {
		Date now = new Date();
		long l = now.getTime() - this.validEndDate.getTime();
		if (l < 0) {
			overdue = false;
		} else {
			overdue = true;
		}

		return overdue;
	}

	@Override
	public String toString() {
		return "KeyWord{" +
				"keywordId=" + keywordId +
				", userId=" + userId +
				", keywordName='" + keywordName + '\'' +
				", keyword='" + keyword + '\'' +
				", filterKeyword='" + filterKeyword + '\'' +
				", keyword1='" + keyword1 + '\'' +
				", keyword2='" + keyword2 + '\'' +
				", keyword3='" + keyword3 + '\'' +
				", keyword4='" + keyword4 + '\'' +
				'}';
	}

}
