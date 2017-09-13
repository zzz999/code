package com.htsec.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Description : TODO
 * Date : 2017/3/30 10:01
 * User : 011419
 * Copyright (c) 2017, goldendba@gmail.com All Right Reserved.
 */
public interface UserClickDictionaryService {
    /**
     * 查询所有渠道.
     *
     * @return
     * @throws IOException
     */
    List<Map<String, Object>> queryChannels() throws IOException ;

    /**
     * 查询所有版本.
     *
     * @return
     * @throws IOException
     */
    List<Map<String, Object>> queryVersions() throws IOException ;

    String queryChannelsString() throws IOException;
}
