package com.miduchina.wrd.dto.hotsportview;


import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.user.User;
import lombok.Data;

/**
 */
@Data
public class Status extends BaseDto {
	private Long created_at;
	private String text;
	//微博MID
	private String mid;
	//微博链接
	private String url;
	//转发数
	private int reposts_count;
	//评论数
	private int comments_count;
	//赞数
	private String attitudes_count;
	//作者信息
	private User user;

}
