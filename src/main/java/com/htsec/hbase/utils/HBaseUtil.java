package com.htsec.hbase.utils;

import com.google.common.collect.Lists;
import com.htsec.commons.utils.SpringContextHolder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bernard on 2017/3/13.
 */
public class HBaseUtil {
    private static String quorum;
    private static String clientPort;
    private static Configuration conf = null;
    private static HConnection conn = null;

    /**
     * 获取全局唯一的Configuration实例
     *
     * @return
     */
    public static synchronized Configuration getConfiguration() {
        if (conf == null) {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", quorum);
            conf.set("hbase.zookeeper.property.clientPort", clientPort);
        }
        return conf;
    }

    /**
     * 获取全局唯一的HConnection实例
     *
     * @return
     * @throws ZooKeeperConnectionException
     */
    public static synchronized HConnection getHConnection() throws Exception {
        if (conn == null) {
            conn = HConnectionManager.createConnection(getConfiguration());
        }
        if (conn.isClosed() == true) {
            conn = HConnectionManager.createConnection(getConfiguration());
        }

        return conn;
    }

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }

    public void setClientPort(String clientPort) {
        this.clientPort = clientPort;
    }
}
