package com.s3corp.heat.helper;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.s3corp.heat.log.Log;

public class ExcelHelper {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static org.apache.poi.ss.usermodel.Cell Cell;
	private static XSSFRow Row;

	// private static XSSFRow Row;

	public static void setExcelFile(String Path) throws Exception {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			Log.error("Class Utils | Method setExcelFile | Exception desc : " + e.getMessage());
		}
	}

	public static String getCellData(int RowNum, int ColNum, String SheetName) throws Exception {
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			Log.error("Class Utils | Method getCellData | Exception desc : " + e.getMessage());
			return "";
		}
	}

	public static int getRowCount(String SheetName) {
		int iNumber = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber = ExcelWSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowCount | Exception desc : " + e.getMessage());
		}
		return iNumber;
	}

	public static int getRowContains(String sTestCaseName, int colNum, String SheetName) throws Exception {
		int iRowNum = 0;
		try {
			// ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int rowCount = ExcelHelper.getRowCount(SheetName);
			for (; iRowNum < rowCount; iRowNum++) {
				if (ExcelHelper.getCellData(iRowNum, colNum, SheetName).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowContains | Exception desc : " + e.getMessage());
		}
		return iRowNum;
	}

	
//	@SuppressWarnings("static-access")
//	public static void setCellData(String Result, int RowNum, int ColNum,String SheetName) throws Exception {
//		try {
//
//			ExcelWSheet = ExcelWBook.getSheet(SheetName);
//			Row = ExcelWSheet.getRow(RowNum);
//			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
//			if (Cell == null) {
//				Cell = Row.createCell(ColNum);
//				Cell.setCellValue(Result);
//			} else {
//				Cell.setCellValue(Result);
//			}
//			FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
//			ExcelWBook.write(fileOut);
//			// fileOut.flush();
//			fileOut.close();
//			ExcelWBook = new XSSFWorkbook(new FileInputStream(
//					Constants.Path_TestData));
//		} catch (Exception e) {
//
//		}
//	}
}
