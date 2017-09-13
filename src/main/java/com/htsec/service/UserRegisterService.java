package com.htsec.service;

import java.sql.Date;

/**
 * Description : TODO
 * Date : 2017/3/30 10:01
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public interface UserRegisterService {
    String callUserRegisterStat() throws Exception;

    String callUserRegisterDetail(Date start, Date end, long offset, long fetchSize) throws Exception;
}
