package com.miduchina.wrd.dto.home;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.07.18
 */

@Data
public class StatValueDto implements Serializable {

    private String name;
    private Double value;
    private Double percent;
    private Long count;
    private List<NewsContenDto> contentList;

}

