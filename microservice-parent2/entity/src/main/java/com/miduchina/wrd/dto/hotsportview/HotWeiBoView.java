package com.miduchina.wrd.dto.hotsportview;

import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.vo.BaseVo;
import lombok.Data;

import java.util.List;

/**
 */
@Data
public class HotWeiBoView extends BaseVo {
	private Integer total_number;
	private HotWeiBoData data;
	private List<Statuses> statuses;
	
}
