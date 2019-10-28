package com.miduchina.wrd.po.eventanalysis.weiboevent;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;


@Data
public class IContentCommonNet {
	// "索引时间"
	private Date indexTime;
	// "用户ID"
	private String uid;
	// "正文含图片数据列表"
	private List<String> pictureList;

	public static final String ORIGINTYPE_ALL = "all";

	private Integer comments = 0;// 微博评论数

	private Integer fansNumber = 0;// 微博粉丝数

	private Integer forwardNumber = 0;// 微博转发数
	private String  forwarderContent;

	private Integer repeatNum = 0;// 相同文章数

	private Integer friendsCount = 0;// 微博关注数

	private Integer praiseNum = 0;// 微博点赞数

	private String source;// 发布微博的设备

	protected Integer Spare3;// 微博层级

	protected String Spare5;// 父微博用户昵称

	protected String userLocation;// 用户省份

	protected String province;// 用户省份

	private Integer verifyType = -1;// 微博用户类型

	private Integer views = 0;// 微博阅读数

	private Integer weiboNums = 0;// 微博微博数

	private Date published;

	private String captureWebsiteName;

	private String title;
	private String titleHs;
	private String summary;

	private String profileImageUrl;

	private String author;

	private long num = 0;// 统计数量

	private String webpageUrl;

	private String originType;

	private String content;	//内容

	private String id;

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

	public String getForwarderContent() {
		return forwarderContent;
	}

	public void setForwarderContent(String forwarderContent) {
		this.forwarderContent = forwarderContent;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public String getTitleHs() {
		return titleHs;
	}

	public void setTitleHs(String titleHs) {
		this.titleHs = titleHs;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
