package com.miduchina.wrd.api.hot.all.service;

import com.xd.tools.pojo.Params;
import com.xd.tools.pojo.RelatedWord;
import com.xd.tools.pojo.Stat;
import com.xd.tools.pojo.StatStatList;
import com.xd.tools.view.CtksStatsView;

import java.util.List;

/**
 * 热度接口service
 * 
 * @author lkc
 * @date 2017年4月12日 上午11:30:27
 * @version V1.0
 *
 */
public interface HotService {

	/**
	 * 填充聚类分析
	 * 
	 * @Title: doFillAnlyzerComputerInSide
	 * @param list
	 * @throws Exception
	 * @author lkc
	 */
	public void doFillAnlyzerComputerInSide(String userTag, List<StatStatList> list, Params params) throws Exception;

	/**
	 * 填充数量
	 * 
	 * @Title: doFillRelatedWordNum
	 * @param analysisTaskTicket
	 * @param list
	 * @param params
	 * @throws Exception
	 * @author lkc
	 */
	public void doFillRelatedWordNum(String userTag, String analysisTaskTicket, List<RelatedWord> list, Params params,
                                     boolean isSolr) throws Exception;

	/**
	 * 填充关联词数量 和同比
	 *
	 * @param analysisTaskTicket
	 * @param list
	 * @param params
	 * @param isSolr
	 * @throws Exception
	 */
	public void doFillRelatedWordNumAndRatio(String userTag, String analysisTaskTicket, List<RelatedWord> list,
                                             Params params, boolean isSolr) throws Exception;

	/**
	 * 获取聚类列表
	 * 
	 * @Title: getClustering
	 * @param params
	 * @param samplingNum
	 * @param fimalyNum
	 * @param num
	 * @return
	 * @throws Exception
	 * @author lkc
	 */
	public List<Stat> getClustering(String userTag, Params params, Integer samplingNum, Integer fimalyNum, Integer num)
			throws Exception;

	/**
	 * 获取热度值
	 * 
	 * @return
	 * @throws Exception
	 */
	public CtksStatsView getHot(String userTag, Params params) throws Exception;

}
