package com.itheima.bos.dao.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**  
 * ClassName:POITest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午7:51:23 <br/>       
 */
public class POITest {
    public static void main(String[] args) throws Exception {
        //读取文件
        HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream("D:\\文档\\桌面\\a.xls"));
        //获取工作簿
        HSSFSheet sheetAt = workbook.getSheetAt(0);
        //行
        for (Row row : sheetAt) {
            int rowNum = row.getRowNum();
            if(rowNum==0){
                continue;
            }
            //列
            for (Cell cell : row) {
                String value = cell.getStringCellValue();
                System.out.print(value+"\t");
            }
            System.out.println();
        }
        workbook.close();
        
    }

}
  
