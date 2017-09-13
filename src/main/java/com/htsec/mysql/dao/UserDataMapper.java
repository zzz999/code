package com.htsec.mysql.dao;

import com.htsec.mysql.dto.swcompositescore;

public interface UserDataMapper {
    public swcompositescore getbyaccount(int account);
}
