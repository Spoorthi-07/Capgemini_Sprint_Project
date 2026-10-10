package genericutility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	public String getDataFromExcel(String fileName, String sheetName, int row, int column) throws IOException {

	    FileInputStream fis = new FileInputStream("./src/test/resources/" + fileName);

	    Workbook wb = WorkbookFactory.create(fis);

	    String data = "";

	    if (wb.getSheet(sheetName).getRow(row).getCell(column) != null) {
	        data = wb.getSheet(sheetName).getRow(row).getCell(column).toString();
	    }

	    wb.close();
	    fis.close();

	    return data;
	}
}

