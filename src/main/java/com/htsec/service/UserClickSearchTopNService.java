package com.htsec.service;

import java.sql.Date;

/**
 * Description : TODO
 * Date : 2017/3/30 10:01
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public interface UserClickSearchTopNService {
    String callUbsSearchTopNStat() throws Exception;

    String callUbsSearchFuncDetail(String name, Date start, Date end, long offset, long fetchSize) throws Exception;

    String callUbsSearchStockDetail(String name, Date start, Date end, long offset, long fetchSize) throws Exception;
}
