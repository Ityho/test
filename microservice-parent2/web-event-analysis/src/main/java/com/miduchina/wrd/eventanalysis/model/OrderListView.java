package com.miduchina.wrd.eventanalysis.model;

import com.miduchina.wrd.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class OrderListView extends BaseDto {
	private static final long serialVersionUID = 5268941231542615672L;
	private int page;
	private int pageSize;
	private int maxPage;
	private long totalCount;
	private Integer orderCountAll;
	private Integer orderCountPay;
	private Integer orderCountNoPay;
	private Integer orderCountPayFail;
	private List<OrderListViewElement> orderList;

}
