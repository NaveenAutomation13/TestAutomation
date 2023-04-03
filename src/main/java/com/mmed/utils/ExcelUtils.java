package com.mmed.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;

public class ExcelUtils {
    
	private ExcelUtils() {
		
	}
	
	public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
	private static String testDataExcelPath = null;
	private static XSSFWorkbook excelWBook;
	private static XSSFSheet excelWSheet;
	private static XSSFCell cell;
	private static XSSFRow row;

	public static int rowNumber;
	public static int columnNumber;

	public static void setExcelFileSheet(String testDataExcelFileName, String sheetName) throws IOException {
		if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
			testDataExcelPath = CURRENT_DIRECTORY + "/";
		} else if (Platform.getCurrent().toString().contains("WIN")) {
			testDataExcelPath = CURRENT_DIRECTORY + "\\";
		}
		FileInputStream ExcelFile = new FileInputStream(testDataExcelPath + "\\" + testDataExcelFileName);
		excelWBook = new XSSFWorkbook(ExcelFile);
		excelWSheet = excelWBook.getSheet(sheetName);
	}

	public static String getCellData(int RowNum, int ColNum) {
		cell = excelWSheet.getRow(RowNum).getCell(ColNum);
		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell);
	}

	public static XSSFRow getRowData(int RowNum) {
		row = excelWSheet.getRow(RowNum);
		return row;
	}

	public static void setCellData(String testDataExcelFileName, int RowNum, int ColNum, String value) throws IOException {
		row = excelWSheet.getRow(RowNum);
		cell = row.getCell(ColNum);
		if (cell == null) {
			cell = row.createCell(ColNum);
			cell.setCellValue(value);
		} else {
			cell.setCellValue(value);
		}
		FileOutputStream fileOut = new FileOutputStream(testDataExcelPath + testDataExcelFileName);
		excelWBook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static int getTotalRows() {
		int noOfRows = excelWSheet.getLastRowNum() + 1;
		return noOfRows;
	}

	public static int getTotalColumns() {
		int noOfColumns = excelWSheet.getRow(0).getLastCellNum();
		return noOfColumns;
	}
}
