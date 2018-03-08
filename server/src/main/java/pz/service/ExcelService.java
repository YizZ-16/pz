package pz.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileInputStream;

@Service
@Slf4j
public class ExcelService {

    public void parseExcel() {
        try {
            XSSFWorkbook excel = new XSSFWorkbook(
                    new FileInputStream(
                            new File("G:\\three.xlsx")));
            XSSFSheet sheet = null;
            for (int i = 0; i< excel.getNumberOfSheets(); i++) {
                sheet = excel.getSheetAt(i);
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
                    XSSFRow row = sheet.getRow(j);
                    if (row != null) {
                        for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个

                             //System.out.println(row.getCell(k));
                            Object o = getCellFormatValue(row.getCell(k));
                            if (o != null && o != "") { // getCell 获取单元格数据
                                System.out.print(row.getCell(k) + "\t");
                            } else {
                                System.out.print("\t");
                            }


                        }
                    }
                    System.out.println(""); // 读完一行后换行
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}

