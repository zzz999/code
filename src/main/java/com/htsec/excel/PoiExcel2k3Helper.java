package com.htsec.excel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel 读取（97-2003格式）
 * @author	chengesheng
 * @date	2012-4-27 下午03:39:01
 * @note	PoiExcel2k3Helper
 */
public class PoiExcel2k3Helper extends PoiExcelHelper {
	/** 获取sheet列表 */
	public ArrayList<String> getSheetList(InputStream filePath) {
		ArrayList<String> sheetList = new ArrayList<String>(0);
		try {
			HSSFWorkbook wb = new HSSFWorkbook(filePath);
			int i = 0;
			while (true) {
				try {
					String name = wb.getSheetName(i);
					sheetList.add(name);
					i++;
				} catch (Exception e) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetList;
	}

	/** 读取Excel文件内容 */
	public ArrayList<ArrayList<String>> readExcel(InputStream filePath, int sheetIndex, String rows, String columns) {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(filePath);
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			
			dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	/** 读取Excel文件内容 */
	public ArrayList<ArrayList<String>> readExcel(InputStream filePath, int sheetIndex, String rows, int[] cols) {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(filePath);
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			
			dataList = readExcel(sheet, rows, cols);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
}