package com.miduchina.wrd.dto.hotsportview;


import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.user.User;
import com.miduchina.wrd.vo.BaseVo;
import lombok.Data;

/**
 */
@Data
public class Statuses extends BaseVo{
	private String trueMid;

	private String text;
	//微博MID
	private String mid;
	//转发数
	private int reposts_count;
	//评论数
	private int comments_count;
	//赞数
	private String attitudes_count;

	private String orig_text;
	private Long orig_created_at;

	private Status status;
	private Integer hot;
	private String url;
	private User user;
	@Override
	public String toString() {
		return "Statuses [trueMid=" + trueMid + ", text=" + text + ", mid="
				+ mid + ", reposts_count=" + reposts_count
				+ ", comments_count=" + comments_count + ", attitudes_count="
				+ attitudes_count + ", orig_text=" + orig_text
				+ ", orig_created_at=" + orig_created_at + ", status=" + status
				+ ", hot=" + hot + ", url=" + url + ", user=" + user + "]";
	}


}
