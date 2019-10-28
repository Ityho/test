package com.miduchina.wrd.eventanalysis.service;

import com.miduchina.wrd.dto.user.FavoriteNews;

import java.util.List;

public interface FavoriteService {
    public List<FavoriteNews> getFavoriteNewsList(int userId, int type, int order);
}
