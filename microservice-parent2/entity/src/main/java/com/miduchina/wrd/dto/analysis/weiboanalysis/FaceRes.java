package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;
@Data
public class FaceRes {
    private String faceName;
    private String faceUrl;
    private List<Integer> faceCount;

    public FaceRes(String faceName, String faceUrl, List<Integer> faceCount) {
        this.faceName = faceName;
        this.faceUrl = faceUrl;
        this.faceCount = faceCount;
    }
}
