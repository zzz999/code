package com.htsec.mysql.service.impl;

import com.htsec.mysql.dao.UserDataMapper;
import com.htsec.mysql.dto.swcompositescore;
import com.htsec.mysql.service.UserDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataBaseServiceImpl implements UserDataBaseService{

    @Autowired
    private UserDataMapper userDataMapper;


    @Override
    public swcompositescore getswbyaccount(int account) {
       return userDataMapper.getbyaccount(1212);
    }
}
