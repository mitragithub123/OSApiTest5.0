package utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

/*readExcelData(String fileName, String sheetName): This method reads data from an Excel file specified by fileName and from a particular sheet identified by sheetName. It reads the data in bulk, meaning it retrieves all the data from the specified sheet and returns it as a two-dimensional array (Object[][]). Each element in the array represents a cell in the Excel sheet. This method is useful when you need to process all the data in the sheet at once.

getCellValue(String fileName, String sheetName, int rowNum, int colNum): This method retrieves the value of a specific cell in the Excel sheet. You specify the row number (rowNum) and column number (colNum) of the cell you want to retrieve. It returns the value of that cell as a string. This method is useful when you need to access individual cells in the Excel sheet.

getRowCount(String fileName, String sheetName): This method returns the total number of rows in the specified Excel sheet. It provides the count of rows starting from 1 (excluding the header row). It can be useful when you need to iterate over rows in the Excel sheet or perform operations based on the total number of rows.

getColCount(String fileName, String sheetName, int rowNum): This method returns the total number of columns in a specific row of the Excel sheet. You specify the row number (rowNum) for which you want to get the column count. It can be useful when you need to iterate over columns in a specific row or perform operations based on the total number of columns in a row.*/

public class ReadExcelFile {

	public static FileInputStream inputStream;
	public static XSSFWorkbook workBook;
	public static XSSFSheet excelSheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	public static String getCellValue(String fileName, String sheetName, int rowNo, int cellNo/* column no. */) {
		try {
			inputStream = new FileInputStream(fileName);
			workBook = new XSSFWorkbook(inputStream);
			excelSheet = workBook.getSheet(sheetName);
			cell = workBook.getSheet(sheetName).getRow(rowNo).getCell(cellNo);
			workBook.close();

			return cell.getStringCellValue();
		}

		catch (Exception e) {
			return "";
		}
	}

	public static int getRowCount(String fileName, String sheetName) {
		try {
			inputStream = new FileInputStream(fileName);
			// create XSSFWorkBook Class object for excel file manipulation

			workBook = new XSSFWorkbook(inputStream);
			excelSheet = workBook.getSheet(sheetName);

			// get total no. of rows
			int ttlRows = excelSheet.getLastRowNum() + 1;
			workBook.close();

			return ttlRows;
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getColCount(String fileName, String sheetName) {
		try {

			FileInputStream inputStream = new FileInputStream(fileName);
			// create XSSFWorkBook Class object for excel file manipulation
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			XSSFSheet excelSheet = workBook.getSheet(sheetName);

			// get total no. of columns
			int ttlCells = excelSheet.getRow(0).getLastCellNum();

			workBook.close();
			return ttlCells;
		} catch (Exception e) {
			return 0;

		}
	}

	public static Object[][] readExcelData(String fileName, String sheetName) throws IOException {
		FileInputStream inputStream = new FileInputStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheet(sheetName); // Assuming data is in the specified sheet

		int rowCount = sheet.getPhysicalNumberOfRows();
		int colCount = sheet.getRow(0) != null ? sheet.getRow(0).getPhysicalNumberOfCells() : 0; // Handle empty first
																									// row

		Object[][] data = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < colCount; j++) {
				Cell cell = row.getCell(j);
				if (cell != null) {
					data[i - 1][j] = cell.getCellType() == CellType.NUMERIC
							? String.valueOf((int) cell.getNumericCellValue())
							: cell.getStringCellValue();
				} else {
					// Handle empty cell (e.g., add an empty string to the data array)
					data[i - 1][j] = "";
				}
			}
		}

		workbook.close();
		return data;
	}

}
