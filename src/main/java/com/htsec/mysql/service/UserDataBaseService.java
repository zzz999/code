package com.htsec.mysql.service;

import com.htsec.mysql.dto.swcompositescore;

public interface UserDataBaseService {
    public swcompositescore getswbyaccount(int account);
}
