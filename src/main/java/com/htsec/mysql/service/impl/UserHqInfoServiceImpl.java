package com.htsec.mysql.service.impl;

import com.htsec.mysql.service.UserHqInfoService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bernard on 2017/3/13.
 */

@Service
public class UserHqInfoServiceImpl implements UserHqInfoService {
    private static final Logger logger = Logger.getLogger(UserHqInfoServiceImpl.class);

    //@Autowired
    // private PetMapper petMapper;
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactory;

    @Override
    public void play() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.getObject().openSession(ExecutorType.BATCH, false);
            long start =System.currentTimeMillis();
            for(int j=0 ;j<10;j++) {
                for (int i = 0; i < 1000; i++) {
                    Map map = new HashMap();
                    map.put("channelIdinput", i+"");
                    map.put("versioninput", "-");
                    map.put("deviceTypeinput", "-");
                    map.put("appidinput", "-");
                    map.put("clickKeyinput", "my-key");
                    map.put("timestampinput", "2017-07-07");
                    map.put("clickCountinput", 1);
                    sqlSession.insert("com.htsec.mysql.dao.PetMapper.insertByProc", map);
                }
                sqlSession.commit();
                sqlSession.clearCache();
            }
            System.out.println("插入共花费时间" + (System.currentTimeMillis()-start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

      /*  List<Pet> a= petMapper.getAllPets();
        System.out.println(a.size());*/
    }
}
