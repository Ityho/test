package com.miduchina.wrd.dto.user;

import com.miduchina.wrd.dto.BaseDto;
import lombok.Data;

/**
 * Created by shitao on 2019-05-09 14:09.
 *
 * @author shitao
 */
@Data
public class SignInRewardWdNumDto  extends BaseDto {

    private Integer todaySignInRewardWdNum;
    private Integer tomorrowSignInRewardWdNum;
    private String  lastSignInDate;
}
