package com.miduchina.wrd.dto.user;

import com.miduchina.wrd.bo.user.UserBO;
import lombok.Data;

/**
 * @version v1.0.0
 * @ClassName: UserThirdpartyDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/19 11:01 AM
 */
@Data
public class UserThirdpartyDto {
    private String sid;
    private Boolean register;
    private UserBO userInfo;
}
