package com.htsec.mysql.service;

import com.htsec.mysql.dto.HotSearch;

import java.util.List;

/**
 * Created by bernard on 2017/7/6.
 */
public interface UserHotSearchService {
    public void updateHotSearchService(List<HotSearch> updateList);
}
