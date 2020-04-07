package com.kbja.bestmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by yiming.zhou on 2020/4/3
 **/
public class POIFillAndColorExample {

  public static void main(String[] args) throws IOException {
    // Create a workbook object
    Workbook workbook = new XSSFWorkbook();
    // Create sheet
    Sheet sheet = workbook.createSheet();

    // Create a row and put some cells in it.
    Row row = sheet.createRow((short) 1);
    // Aqua background

    CellStyle style = workbook.createCellStyle();

    style.setFillForegroundColor(IndexedColors.RED.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    Cell cell = row.createCell((short) 1);
    cell.setCellValue("红色球员");
    cell.setCellStyle(style);

    style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    cell = row.createCell((short) 2);
    cell.setCellValue("黄色球员");
    cell.setCellStyle(style);

    style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.PINK.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    cell = row.createCell((short) 3);
    cell.setCellValue("粉色球员");
    cell.setCellStyle(style);

    style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    cell = row.createCell((short) 4);
    cell.setCellValue("蓝色球员");
    cell.setCellStyle(style);

    style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    cell = row.createCell((short) 5);
    cell.setCellValue("绿色球员");
    cell.setCellStyle(style);

    style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    cell = row.createCell((short) 6);
    cell.setCellValue("已选球员");
    cell.setCellStyle(style);

    style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    cell = row.createCell((short) 7);
    cell.setCellValue("未选球员1");
    cell.setCellStyle(style);

    style = workbook.createCellStyle();
    style.setBorderBottom(BorderStyle.THIN); //下边框
    style.setBorderLeft(BorderStyle.THIN);//左边框
    style.setBorderTop(BorderStyle.THIN);//上边框
    style.setBorderRight(BorderStyle.THIN);//右边框
    style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    cell = row.createCell((short) 8);
    cell.setCellValue("未选球员2");
    cell.setCellStyle(style);

    // Write the output to a file
    File f = new File("/Users/zhou/Desktop/POIFillAndColorExample.xlsx");
    if (!f.exists()) {
      f.createNewFile();
    }
    FileOutputStream fileOut = new FileOutputStream(f);
    workbook.write(fileOut);
    fileOut.close();

  }

}
