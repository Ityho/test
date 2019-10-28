package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class NameAndValue implements Comparable<NameAndValue>{
    private String name;
    private Object value;

    public NameAndValue() {
    }

    public NameAndValue(String name, Object value) {
        super();
        this.name = name;
        this.value = value;
    }
    @Override
    public int compareTo(NameAndValue o) {
        return o.getName().trim().compareTo(this.getName().trim());
    }
}
