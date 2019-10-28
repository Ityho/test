package com.miduchina.wrd.dto.eventanalysis;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TasksView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private Integer totalCount;
    private List<Task> tasksList;
}
