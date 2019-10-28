package com.miduchina.wrd.dto.hotsportview;

import com.miduchina.wrd.po.hotspot.Coupon;
import com.miduchina.wrd.vo.PageVo;
import lombok.Data;

import java.util.List;


/**
 * @author  qiuqiu
 * @date 创建时间：2018年1月29日 上午10:31:59
 */
@Data
public class CouponsView extends PageVo {
	private static final long serialVersionUID = -8596348871139012034L;
	private List<Coupon> coupons;

}
