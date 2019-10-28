package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.dto.user.UserDto;
import lombok.Data;

import java.util.List;
@Data
public class WeiboTaskResultVerifyUserRes {
    private String code;
    private String message;
    private List<UserDto> normalUserList;
    private List<UserDto> personalUserList;
    private List<UserDto> officalUserList;
}
