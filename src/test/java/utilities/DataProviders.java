package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "AllData") // Data provider to extract all data
	public String[][] AllDataProvider() {
		String fName = "D://TestData.xlsx";

		int ttlRowCnt = ReadExcelFile.getRowCount(fName, "Sheet1");
		int ttlColCnt = ReadExcelFile.getColCount(fName, "Sheet1");

		String userData[][] = new String[ttlRowCnt - 1][ttlColCnt];

		for (int rowNo = 1; rowNo < ttlRowCnt; rowNo++) {
			for (int colNo = 0; colNo < ttlColCnt; colNo++) {
				userData[rowNo - 1][colNo] = ReadExcelFile.getCellValue(fName, "Sheet1", rowNo, colNo);
			}

		}
		return userData;
	}

	@DataProvider(name = "UserNamesData") // Data provider to extract only user name
	public String[] UserNamesDataProvider() {
		String fName = "D://TestData.xlsx";

		int ttlRowCnt = ReadExcelFile.getRowCount(fName, "Sheet1");

		String userNamesData[] = new String[ttlRowCnt - 1];

		for (int rowNo = 1; rowNo < ttlRowCnt; rowNo++) {
			userNamesData[rowNo - 1] = ReadExcelFile.getCellValue(fName, "Sheet1", rowNo, 1);

		}
		return userNamesData;
	}

	@DataProvider(name = "UserCredentialsData") // Data provider to extract user name & password
	public Object[][] UserCredentialsDataProvider() {
		String fName = "D://TestData.xlsx";

		int ttlRowCnt = ReadExcelFile.getRowCount(fName, "Sheet1");

		Object[][] userCredentialsData = new Object[ttlRowCnt - 1][2];

		for (int rowNo = 1; rowNo < ttlRowCnt; rowNo++) {
			userCredentialsData[rowNo - 1][0] = ReadExcelFile.getCellValue(fName, "Sheet1", rowNo, 1); // User name
			userCredentialsData[rowNo - 1][1] = ReadExcelFile.getCellValue(fName, "Sheet1", rowNo, 5); // Password
		}
		return userCredentialsData;
	}
}
