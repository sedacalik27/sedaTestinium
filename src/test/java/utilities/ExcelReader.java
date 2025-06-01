package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    public Workbook workbook;
    public Sheet sheet;

    public ExcelReader(String path, String pageName) {
        try {
            FileInputStream fis = new FileInputStream(path);
            workbook = WorkbookFactory.create(fis);
            sheet = workbook.getSheet(pageName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Excelde satır ve sütunlardan veri almak için
    public  String getCellData(int row, int column) {
        Cell cell = sheet.getRow(row - 1).getCell(column - 1);
        return cell.toString();
    }



}
