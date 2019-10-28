package com.miduchina.wrd.dto.user;

import com.miduchina.wrd.dto.ip.IpInfoDto;
import lombok.Data;

/**
 * @version v1.0.0
 * @ClassName: RestrictRegisterUserDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/19 2:05 PM
 */
@Data
public class RestrictRegisterUserDto {
    private String username;
    private String ip;
    private Integer sameNum;
    private IpInfoDto ipInfo;
}
