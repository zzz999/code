package com.htsec.mysql.service.impl;

import com.htsec.commons.utils.TimeUtil;
import com.htsec.mysql.dto.HotSearch;
import com.htsec.mysql.service.UserHotSearchService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bernard on 2017/7/10.
 */
@Service
public class UserHotSearchServiceImpl implements UserHotSearchService{
    private static final Logger logger = Logger.getLogger(UserHotSearchServiceImpl.class);

    //数据库连接
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactory;


    @Override
    public void updateHotSearchService(List<HotSearch> updateList) {
        logger.info("Start to update userHotSearchInfo");
        long starttime = System.currentTimeMillis();
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.getObject().openSession(ExecutorType.BATCH, false);
            int i = 0;

            for (HotSearch hotSearch : updateList) {
                Map map = new HashMap();
                map.put("channelIdinput", "--");
                map.put("versioninput", "--");
                map.put("deviceTypeinput", "--");
                map.put("appidinput", "--");
                map.put("clickKeyinput", hotSearch.getClickKey());
                map.put("timestampinput", TimeUtil.getNow());
                map.put("clickCountinput", hotSearch.getCount());
                sqlSession.insert("com.htsec.mysql.dao.PetMapper.insertByProc", map);
                i++;
                if (i > 500) {
                    i=0;
                    sqlSession.commit();
                    sqlSession.clearCache();
                }
            }
            sqlSession.commit();
            sqlSession.clearCache();
            logger.info("插入数据库完毕,时间：" + (System.currentTimeMillis() - starttime));
        }catch (Exception e){
            logger.error("Insert hotsearch info into DB failed",e);
            sqlSession.rollback();
        }finally {
            if (sqlSession!=null){
                sqlSession.close();
            }
        }
    }
}
