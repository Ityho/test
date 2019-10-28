package com.miduchina.wrd.dto.hotsportview;

import com.miduchina.wrd.po.eventanalysis.BaseView;
import com.miduchina.wrd.po.hotspot.ActivitySendRecord;
import lombok.Data;

import java.util.List;

/**
 * @author  qiuqiu
 * @date 创建时间：2018年4月17日 下午3:57:32
 */
@Data
public class ActivitySendRecordsView extends BaseView {

	private List<ActivitySendRecord> activitySendRecords;


}
