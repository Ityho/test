package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.dto.user.UserDto;
import lombok.Data;

import java.util.List;
@Data
public class WeiboTaskResultKeyUserRes {
    private String code;
    private String message;
    private UserDto user;
    private List<WeiboUser> userList;


}
