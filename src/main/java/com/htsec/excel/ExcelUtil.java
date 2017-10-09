package com.htsec.excel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static void main(String[] args){
        // 获取Excel文件的sheet列表
        //testGetSheetList("c:/test.xlsx");

       // 获取Excel文件的第1个sheet的内容
      // File file = new File("C:/Users/berna/Desktop/8家制造业公司6年财务报表.xls");
       // try {
        //   testReadExcel(new FileInputStream(file), 0);
    //    } catch (FileNotFoundException e) {
     //       e.printStackTrace();
      //  }

        // 获取Excel文件的第2个sheet的第2、4-7行和第10行及以后的内容
       // testReadExcel("c:/test.xlsx", 1, "2,4-7,10-");

        // 获取Excel文件的第3个sheet中a,b,g,h,i,j等列的所有内容
      //  testReadExcel("c:/test.xls", 2, new String[] {"a","b","g","h","i","j"});

        // 获取Excel文件的第4个sheet的第2、4-7行和第10行及以后，a,b,g,h,i,j等列的内容
       // testReadExcel("c:/test.xlsx", 3, "2,4-7,10-", new String[] {"a","b","g","h","i","j"});
    }

    // 测试获取sheet列表
    public static ArrayList<String> testGetSheetList(InputStream filePath,String name) {
        PoiExcelHelper helper = getPoiExcelHelper(filePath,name);

        // 获取Sheet列表
        return  helper.getSheetList(filePath);

        // 打印Excel的Sheet列表
       // printList(name, sheets);
    }

    // 测试Excel读取
    public static ArrayList<ArrayList<String>> testReadExcel(InputStream filePath, int sheetIndex,String name) {
        PoiExcelHelper helper = getPoiExcelHelper(filePath,name);

        // 读取excel文件数据
        ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex);
        return dataList;

        // 打印单元格数据
        //printBody(dataList);
    }

    // 测试Excel读取
    private static void testReadExcel(InputStream filePath, int sheetIndex, String rows,String name) {
        PoiExcelHelper helper = getPoiExcelHelper(filePath,name);

        // 读取excel文件数据
        ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex, rows);

        // 打印单元格数据
        printBody(dataList);
    }

    // 测试Excel读取
    private static void testReadExcel(InputStream filePath, int sheetIndex, String[] columns,String name) {
        PoiExcelHelper helper = getPoiExcelHelper(filePath,name);

        // 读取excel文件数据
        ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex, columns);

        // 打印列标题
        printHeader(columns);

        // 打印单元格数据
        printBody(dataList);
    }

    // 测试Excel读取
    private static void testReadExcel(InputStream filePath, int sheetIndex, String rows, String[] columns,String name) {
        PoiExcelHelper helper = getPoiExcelHelper(filePath,name);

        // 读取excel文件数据
        ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex, rows, columns);

        // 打印列标题
        printHeader(columns);

        // 打印单元格数据
        printBody(dataList);
    }

    // 获取Excel处理类
    private static PoiExcelHelper getPoiExcelHelper(InputStream filePath,String name) {
        PoiExcelHelper helper;
        if(name.indexOf(".xlsx")!=-1) {
            helper = new PoiExcel2k7Helper();
        }else {
            helper = new PoiExcel2k3Helper();
        }
        return helper;
    }

    // 打印Excel的Sheet列表
    private static void printList(String filePath, ArrayList<String> sheets) {
        System.out.println();
        for(String sheet : sheets) {
            System.out.println(filePath + " ==> " + sheet);
        }
    }

    // 打印列标题
    private static void printHeader(String[] columns) {
        System.out.println();
        for(String column : columns) {
            System.out.print("\t\t" + column.toUpperCase());
        }
    }

    // 打印单元格数据
    public static void printBody(List<ArrayList<String>> dataList) {
        int index = 0;
        for(List<String> data : dataList) {
            index ++;
            System.out.println();
            System.out.print(index);
            for(String v : data) {
                System.out.print("\t\t" + v);
            }
        }
    }

}
