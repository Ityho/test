package com.miduchina.wrd.eventanalysis.model;

import com.miduchina.wrd.dto.pay.ConfirmOrderViewOrderElementDto;
import com.miduchina.wrd.dto.pay.ConfirmOrderViewPackageElementDto;
import com.miduchina.wrd.dto.pay.PayRecordDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderListViewElement implements Serializable {

	private Double useCreditAmount;
	private ConfirmOrderViewOrderElementDto orderInfo;
	private PayRecordDto payInfo;
	private List<ConfirmOrderViewPackageElementDto> packagesInfo;

}
