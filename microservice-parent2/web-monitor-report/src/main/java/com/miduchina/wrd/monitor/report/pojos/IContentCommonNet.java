package com.miduchina.wrd.monitor.report.pojos;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "iContentCommonNet")
public class IContentCommonNet {

	public static final String ORIGINTYPE_ALL = "all";

	private Integer comments = 0;// 微博评论数

	private Integer fansNumber = 0;// 微博粉丝数

	private Integer forwardNumber = 0;// 微博转发数

	private Integer repeatNum = 0;// 相同文章数
	private String percentStr; // 相同文章数占比

	private Integer friendsCount = 0;// 微博关注数

	private Integer praiseNum = 0;// 微博点赞数

	private String source;// 发布微博的设备

	protected Integer Spare3;// 微博层级

	protected String Spare5;// 父微博用户昵称

	protected String userLocation;// 用户省份

	private Integer verifyType = -1;// 微博用户类型

	private Integer views = 0;// 微博阅读数

	private Integer weiboNums = 0;// 微博微博数

	private Date published;

	private String captureWebsiteName;

	private String title;

	private String profileImageUrl;

	private String author;

	private long num = 0;// 统计数量

	private String webpageUrl;

	private String originType;

	private String content;	//内容

	private String summary;

	private String forwarderContent;

	private int customFlag1;//4是敏感

	private String userScreenName; //微博名称

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public int getCustomFlag1() {
		return customFlag1;
	}

	public void setCustomFlag1(int customFlag1) {
		this.customFlag1 = customFlag1;
	}

	public Integer getComments() {
		return comments;
	}

	public Integer getFansNumber() {
		return fansNumber;
	}

	public Integer getForwardNumber() {
		return forwardNumber;
	}

	public Integer getFriendsCount() {
		return friendsCount;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public String getSource() {
		return source;
	}

	public Integer getSpare3() {
		return Spare3;
	}

	public String getSpare5() {
		return Spare5;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public Integer getVerifyType() {
		return verifyType;
	}

	public Integer getViews() {
		return views;
	}

	public Integer getWeiboNums() {
		return weiboNums;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public void setFansNumber(Integer fansNumber) {
		this.fansNumber = fansNumber;
	}

	public void setForwardNumber(Integer forwardNumber) {
		this.forwardNumber = forwardNumber;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setSpare3(Integer spare3) {
		Spare3 = spare3;
	}

	public void setSpare5(String spare5) {
		Spare5 = spare5;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public void setWeiboNums(Integer weiboNums) {
		this.weiboNums = weiboNums;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public String getCaptureWebsiteName() {
		return captureWebsiteName;
	}

	public void setCaptureWebsiteName(String captureWebsiteName) {
		this.captureWebsiteName = captureWebsiteName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
	}

	public String getPercentStr() {
		return percentStr;
	}

	public void setPercentStr(String percentStr) {
		this.percentStr = percentStr;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getWebpageUrl() {
		return webpageUrl;
	}

	public void setWebpageUrl(String webpageUrl) {
		this.webpageUrl = webpageUrl;
	}

	public String getOriginType() {
		return originType;
	}

	public void setOriginType(String originType) {
		this.originType = originType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getForwarderContent() {
		return forwarderContent;
	}

	public void setForwarderContent(String forwarderContent) {
		this.forwarderContent = forwarderContent;
	}
}