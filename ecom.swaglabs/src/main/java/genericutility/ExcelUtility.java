package genericutility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtility {

    public String getDataFromExcel(
            String fileName,
            String sheetName,
            int row,
            int column) throws IOException {

        String path = "./src/test/resources/" + fileName;

        try (FileInputStream fis = new FileInputStream(path);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                    "Sheet not found: " + sheetName
                );
            }

            Row excelRow = sheet.getRow(row);

            if (excelRow == null) {
                return "";
            }

            Cell cell = excelRow.getCell(column);

            if (cell == null) {
                return "";
            }

            DataFormatter formatter = new DataFormatter();
            FormulaEvaluator evaluator =
                    wb.getCreationHelper().createFormulaEvaluator();

            return formatter.formatCellValue(
                    cell, evaluator
            ).trim();
        }
    }
}


