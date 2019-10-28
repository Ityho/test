package com.miduchina.wrd.dto.ranking;/**
 * Created by sty on 2019/5/29.
 */

import lombok.Data;

/**
 * @version v1.0.0
 * @ClassName: AreaDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/29 5:41 PM
 */
@Data
public class AreaDto {
    private String province;
    private String city;
    private String lat;
    private String lng;
}
