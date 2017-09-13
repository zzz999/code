package com.htsec.hbase.utils;

/**
 * Created by bernard on 2017/3/13.
 */

import com.google.common.collect.Lists;
import com.htsec.service.impl.UpdateHotSearchTaskServiceImpl;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class HBaseHelper {
    // 声明静态配置
    private static final Logger logger = Logger.getLogger(HBaseHelper.class);


    /*
     * 创建表
     *
     * @tableName 表名
     *
     * @family 列族列表
     */
    public static void creatTable(String tableName, String[] family)
            throws Exception {
        HBaseAdmin admin = (HBaseAdmin) HBaseUtil.getHConnection().getAdmin();
        HTableDescriptor desc = new HTableDescriptor(tableName);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tableName)) {
            System.out.println("table Exists!");
            return;
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
        }
    }

    /*
     * 为表添加数据（适合知道有多少列族的固定表）
     *
     * @rowKey rowKey
     *
     * @tableName 表名
     *
     * @column1 第一个列族列表
     *
     * @value1 第一个列的值的列表
     *
     * @column2 第二个列族列表
     *
     * @value2 第二个列的值的列表
     */
    public static void addData(String rowKey, String tableName,
                               String[] column1, String[] value1, String[] column2, String[] value2)
            throws Exception {
        Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        //  HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
        // 获取表
        HColumnDescriptor[] columnFamilies = table.getTableDescriptor() // 获取所有的列族
                .getColumnFamilies();

        for (int i = 0; i < columnFamilies.length; i++) {
            String familyName = columnFamilies[i].getNameAsString(); // 获取列族名
            if (familyName.equals("article")) { // article列族put数据
                for (int j = 0; j < column1.length; j++) {
                    put.add(Bytes.toBytes(familyName),
                            Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
                }
            }
            if (familyName.equals("author")) { // author列族put数据
                for (int j = 0; j < column2.length; j++) {
                    put.add(Bytes.toBytes(familyName),
                            Bytes.toBytes(column2[j]), Bytes.toBytes(value2[j]));
                }
            }
        }
        table.put(put);
        System.out.println("add data Success!");
    }

    /*
     * 根据rwokey查询
     *
     * @rowKey rowKey
     *
     * @tableName 表名
     */
    public static Result getResult(String tableName, String rowKey)
            throws Exception {
        Get get = new Get(Bytes.toBytes(rowKey));
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        // HTable table = new HTable(conf, Bytes.toBytes(tableName));// 获取表
        Result result = table.get(get);
        return result;
    }

    /*
     * 遍历查询hbase表
     *
     * @tableName 表名
     */
    public static void getResultScann(String tableName) throws Exception {
        Scan scan = new Scan();
        ResultScanner rs = null;
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        int a = 0;
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                a++;
                for (KeyValue kv : r.list()) {
                    System.out.println("row:" + Bytes.toString(kv.getRow()));
                    System.out.println("family:"
                            + Bytes.toString(kv.getFamily()));
                    System.out.println("qualifier:"
                            + Bytes.toString(kv.getQualifier()));
                    System.out
                            .println("value:" + Bytes.toString(kv.getValue()));
                    System.out.println("timestamp:" + kv.getTimestamp());
                    System.out
                            .println("-------------------------------------------");
                }
            }
            System.out.println("total:" + a);
        } finally {
            rs.close();
        }
    }

    /*
     * 遍历查询hbase表
     *
     * @tableName 表名
     */
    public static ResultScanner getResultScann(String tableName, String start_rowkey,
                                               String stop_rowkey) throws Exception {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(start_rowkey));
        scan.setStopRow(Bytes.toBytes(stop_rowkey));
        ResultScanner rs = null;
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        rs = table.getScanner(scan);
        return rs;
           /* for (Result r : rs) {
                a++;

                for (KeyValue kv : r.list()) {

                    System.out.println("row:" + Bytes.toString(kv.getRow()));
                    System.out.println("family:"
                            + Bytes.toString(kv.getFamily()));
                    System.out.println("qualifier:"
                            + Bytes.toString(kv.getQualifier()));
                    System.out
                            .println("value:" + Bytes.toString(kv.getValue()));
                    System.out.println("timestamp:" + kv.getTimestamp());
                    System.out
                            .println("-------------------------------------------");
                }
            }
            System.out.println("total: "+a);*/

    }

    /*
     * 查询表中的某一列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     */
    public static void getResultByColumn(String tableName, String rowKey,
                                         String familyName, String columnName) throws Exception {
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out
                    .println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
    }

    /*
     * 更新表中的某一列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     *
     * @familyName 列族名
     *
     * @columnName 列名
     *
     * @value 更新后的值
     */
    public static void updateTable(String tableName, String rowKey,
                                   String familyName, String columnName, String value)
            throws Exception {
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
                Bytes.toBytes(value));
        table.put(put);
        System.out.println("update table Success!");
    }



    public static void save(List<Put> put, String tableName) {
        logger.info("start to update hbase table:"+tableName);
        HTableInterface table = null;
        try{
            table =  HBaseUtil.getHConnection().getTable(tableName);
            table.put(put);
        }catch(Exception e){
            logger.info("update hbase table failed",e);
        }finally{
            try{
                table.close();
            }catch(IOException e){
                logger.info("update hbase table failed and close table failed",e);
            }
        }
    }

    /*
     * 查询某列数据的多个版本
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     *
     * @familyName 列族名
     *
     * @columnName 列名
     */
    public static void getResultByVersion(String tableName, String rowKey,
                                          String familyName, String columnName) throws Exception {
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        get.setMaxVersions(5);
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out
                    .println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
        /*
         * List<?> results = table.get(get).list(); Iterator<?> it =
         * results.iterator(); while (it.hasNext()) {
         * System.out.println(it.next().toString()); }
         */
    }

    /*
     * 删除指定的列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     *
     * @familyName 列族名
     *
     * @columnName 列名
     */
    public static void deleteColumn(String tableName, String rowKey,
                                    String falilyName, String columnName) throws Exception {
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.deleteColumns(Bytes.toBytes(falilyName),
                Bytes.toBytes(columnName));
        table.delete(deleteColumn);
        System.out.println(falilyName + ":" + columnName + "is deleted!");
    }

    /*
     * 删除指定的列
     *
     * @tableName 表名
     *
     * @rowKey rowKey
     */
    public static void deleteAllColumn(String tableName, String rowKey)
            throws Exception {
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);
        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
        table.delete(deleteAll);
        System.out.println("all columns are deleted!");
    }

    /*
     * 删除表
     *
     * @tableName 表名
     */
    public static void deleteTable(String tableName) throws Exception {
        HBaseAdmin admin = (HBaseAdmin) HBaseUtil.getHConnection().getAdmin();
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
        System.out.println(tableName + "is deleted!");
    }

    /**
     * 传入rowKey集合返回多条结果.
     *
     * @param tableName 表名
     * @param rowKeys   rowKey集合 可变字符串
     * @return 多条结果集
     * @author yang.xia
     */
    public static Result[] batchGetResults(String tableName, String... rowKeys) throws Exception {
        List<Get> gets = Lists.newArrayList();
        for (String rowKey : rowKeys) {
            Get get = new Get(Bytes.toBytes(rowKey));
            gets.add(get);
        }
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);

        return table.get(gets);
    }

    /**
     * 通过Filter扫描hbase表.
     *
     * @param tableName 表名
     * @param filter    过滤器(eg. SubstringComparator, BinaryComparator, etc.)
     * @return 扫描结果集
     * @author yang.xia
     */
    public static ResultScanner scanByFilter(String tableName, Filter filter) throws Exception {
        Scan scan = new Scan();
        scan.setFilter(filter);
        HTableInterface table = HBaseUtil.getHConnection().getTable(tableName);

        ResultScanner scanner = table.getScanner(scan);
        for (Result res : scanner) {
            res.isEmpty();
        }

        return table.getScanner(scan);
    }

    public static void main(String[] args) throws Exception {

        // 创建表
       String tableName = "blog2";
        String[] family = { "article", "author" };
        creatTable(tableName, family);

        // 为表添加数据

        String[] column1 = { "title", "content", "tag" };
        String[] value1 = {
                "Head First HBase",
                "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data.",
                "Hadoop,HBase,NoSQL" };
        String[] column2 = { "name", "nickname" };
        String[] value2 = { "nicholas", "lee" };
        addData("rowkey1", "blog2", column1, value1, column2, value2);
        addData("rowkey2", "blog2", column1, value1, column2, value2);
        addData("rowkey3", "blog2", column1, value1, column2, value2);

        // 遍历查询
        getResultScann("blog2", "rowkey4", "rowkey5");
        // 根据row key范围遍历查询
        getResultScann("blog2", "rowkey4", "rowkey5");

        // 查询
        getResult("blog2", "rowkey1");

        // 查询某一列的值
        getResultByColumn("blog2", "rowkey1", "author", "name");

        // 更新列
        updateTable("blog2", "rowkey1", "author", "name", "bin");

        // 查询某一列的值
        getResultByColumn("blog2", "rowkey1", "author", "name");

        // 查询某列的多版本
        getResultByVersion("blog2", "rowkey1", "author", "name");

        // 删除一列
        deleteColumn("blog2", "rowkey1", "author", "nickname");

        // 删除所有列
        deleteAllColumn("blog2", "rowkey1");

        // 删除表
        deleteTable("blog2");
        // HBaseHelper.getResultScann("userClickDetail","click_"+"2017-03-24","click_"+"2017-03-25");
        HBaseHelper.getResultScann("userClickDetail");

        HBaseUtil.getHConnection().close();

    }
}
