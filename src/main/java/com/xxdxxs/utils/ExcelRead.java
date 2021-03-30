package com.xxdxxs.utils;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 读取excel
 * @author: xxdxxs
 */
public class ExcelRead {

    private static final Logger _log = LoggerFactory.getLogger(ExcelRead.class);

    public static List<String[]> getExcelData(MultipartFile file) throws IOException {
        checkFile(file);
        Workbook workbook = getWorkBook(file);
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getLastCellNum();
                    if (StringUtils.isEmpty(row.getCell(firstCellNum)) && StringUtils.isEmpty(row.getCell(lastCellNum - 1))) {
                        break;
                    }
                    String[] cells = new String[row.getLastCellNum()];
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }



    public static void checkFile(MultipartFile file) throws IOException {
        if (null == file) {
            _log.error("文件不存在！");
        }
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            _log.error(fileName + "不是excel文件");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            _log.error(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = stringDateProcess(cell).replaceAll(",", "");
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue()).replaceAll(",", "");
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                try {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }


    public static String stringDateProcess(Cell cell) {
        String result;
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }
        return result;
    }



    public static List<String[]> getExcelDataByAbeam(MultipartFile file) throws IOException {
        checkFile(file);
        Workbook workbook = getWorkBook(file);
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);

                if (sheet == null) {
                    continue;
                }
                int firstRowNum = sheet.getFirstRowNum();
                Row firstRow = sheet.getRow(firstRowNum);
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getLastCellNum();
                    if (StringUtils.isEmpty(row.getCell(firstCellNum)) && StringUtils.isEmpty(row.getCell(lastCellNum - 1))) {
                        break;
                    }
                    Cell cell = row.getCell(firstCellNum);
                    for (int cellNum = firstCellNum + 1; cellNum < lastCellNum; cellNum++) {
                        String[] cells = new String[row.getLastCellNum()];
                        Cell valueCell = row.getCell(cellNum);
                        cells[0] = getCellValue(cell);
                        cells[1] = getCellValue(firstRow.getCell(cellNum));
                        cells[2] = getCellValue(valueCell);
                        list.add(cells);
                    }
                }
            }
        }
        return list;
    }
}
