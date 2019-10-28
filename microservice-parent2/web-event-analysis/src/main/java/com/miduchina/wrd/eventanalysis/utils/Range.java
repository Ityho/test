package com.miduchina.wrd.eventanalysis.utils;

public class Range {
    private Long from;
    private Long to;
    private String name;

    public Range(Long from, Long to, String name) {
        this.from = from;
        this.to = to;
        this.name = name;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
