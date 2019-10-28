package com.miduchina.wrd.dto.analysis.weiboanalysis;

/**
 * 层级
 * 
 * @author liym
 */
public class Tier {
	private Integer repostsUserCount;
	private Integer repostsCoverUserCount;
	private Float repostsProportion;

	public Integer getRepostsUserCount() {
		return repostsUserCount;
	}

	public void setRepostsUserCount(Integer repostsUserCount) {
		this.repostsUserCount = repostsUserCount;
	}

	public Integer getRepostsCoverUserCount() {
		return repostsCoverUserCount;
	}

	public void setRepostsCoverUserCount(Integer repostsCoverUserCount) {
		this.repostsCoverUserCount = repostsCoverUserCount;
	}

	public Float getRepostsProportion() {
		return repostsProportion;
	}

	public void setRepostsProportion(Float repostsProportion) {
		this.repostsProportion = repostsProportion;
	}
}
