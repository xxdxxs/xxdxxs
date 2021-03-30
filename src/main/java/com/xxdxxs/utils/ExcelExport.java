package com.xxdxxs.utils;

import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * excel导出
 * @Author: xxdxxs
 */
public class ExcelExport {

    private static Logger _log = LoggerFactory.getLogger(ExcelExport.class);

    private DecimalFormat decimalFormat = new DecimalFormat("###################.###########");



    public <T> void addContextByList(HSSFSheet sheet, List<T> list,
                                     String[] fieldName, HSSFCellStyle contextStyle, boolean isHaveSerial, String type) {

        try {
            HSSFRow row = null;
            if (list != null) {
                List<T> tList = (List<T>) list;
                T t = null;
                String value = "";
                for (int i = 0; i < list.size(); i++) {
                    row = sheet.createRow(i + 1);
                    for (int j = 0; j < fieldName.length; j++) {
                        HSSFCell cell;
                        t = tList.get(i);
                        value = objectToString(getFieldValueByName(fieldName[j], t));

                        if (isHaveSerial) {
                            //首列加序号
                            if (row.getCell(0) != null && row.getCell(0).getStringCellValue() != null) {
                                cell = row.createCell(0);
                                cell.setCellValue("" + i);
                            }
                            cell = row.createCell(j + 1);

                        } else {
                            cell = row.createCell(j);
                            cell.setCellValue(value);
                        }
                        //100代表通用，不用特殊处理字段
                        if (!"100".equals(type)) {
                            if (j > 2 && !fieldName[j].equals("inst") && !fieldName[j].equals("outst") && !fieldName[j].equals("countTimeStr")
                                    && !fieldName[j].equals("whco")) {
                                if (value.length() > 9 || StringUtils.isContainChinese(value)) {
                                    cell.setCellValue(value);
                                } else {
                                    try {
                                        cell.setCellValue(Double.valueOf(StringUtils.isEmpty(value) ? "0" : value));
                                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    } catch (Exception e) {
                                        cell.setCellValue(value);
                                    }
                                }
                            } else {
                                cell.setCellValue(value);
                            }
                        } else {
                            if (j > 1 && !fieldName[j].equals("store") && !fieldName[j].equals("dept") && !fieldName[j].equals("shopName")
                                    && !fieldName[j].equals("time") && !fieldName[j].equals("shopCode")) {
                                if (value.length() > 8) {
                                    cell.setCellValue(value);
                                } else {
                                    try {
                                        cell.setCellValue(Double.valueOf(StringUtils.isEmpty(value) ? "0" : value));
                                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    } catch (Exception e) {
                                        cell.setCellValue(value);
                                    }
                                }
                            } else {
                                cell.setCellValue(value);
                            }
                        }
                        cell.setCellStyle(contextStyle);
                    }
                }
                for (int j = 1; j < fieldName.length; j++) {
                    sheet.autoSizeColumn(j);
                }
            } else {
                row = sheet.createRow(2);
                HSSFCell cell = row.createCell(0);
            }
        } catch (Throwable e) {
            _log.error("填充内容出现错误：" + e.getMessage(), e);
        }
    }


    public String objectToString(Object object) {
        String str = "";
        if (object == null) {
        } else if (object instanceof Date) {
            DateFormat from_type = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date) object;
            str = from_type.format(date);
        } else if (object instanceof String) {
            str = (String) object;
        } else if (object instanceof Integer) {
            str = ((Integer) object).intValue() + "";
        } else if (object instanceof Double) {
            str = decimalFormat.format(((Double) object).doubleValue()) + "";
        } else if (object instanceof Long) {
            str = Long.toString(((Long) object).longValue());
        } else if (object instanceof Float) {
            str = Float.toHexString(((Float) object).floatValue());
        } else if (object instanceof Boolean) {
            str = Boolean.toString((Boolean) object);
        } else if (object instanceof Short) {
            str = Short.toString((Short) object);
        }
        return str;
    }


    public void addTitle(HSSFSheet sheet, String[] assettitle, String titleName,
                         HSSFCellStyle headerStyle, HSSFCellStyle contextStyle) {
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        //cell.setCellValue(titleName);
        cell.setCellStyle(headerStyle);
        for (int i = 0; i < assettitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(assettitle[i]);
            cell.setCellStyle(contextStyle);
        }
    }


    @SuppressWarnings("unchecked")
    public Object getFieldValueByName(String fieldName, Object object) {
        try {
            Object fieldValue = null;
            if (StringUtils.hasLength(fieldName) && object != null) {
                String firstLetter = "";
                String getter = "";
                Method method = null;
                String extraKey = null;
                // 处理扩展属性 extraData.key
                if (fieldName.indexOf(".") > 0) {
                    String[] extra = fieldName.split("\\.");
                    fieldName = extra[0];
                    extraKey = extra[1];
                }
                firstLetter = fieldName.substring(0, 1).toUpperCase();
                getter = "get" + firstLetter + fieldName.substring(1);
                method = object.getClass().getMethod(getter, new Class[]{});
                fieldValue = method.invoke(object, new Object[]{});
                if (extraKey != null) {
                    Map<String, Object> map = (Map<String, Object>) fieldValue;
                    fieldValue = map == null ? "" : map.get(extraKey);
                }
            }
            if (StringUtils.isEmpty(fieldValue)) {
                fieldValue = "";
            }
            return fieldValue;
        } catch (Throwable e) {
            _log.error("获取属性值出现异常：" + e.getMessage(), e);
            return null;
        }
    }


    public void buildExcelDocument(List<?> list, String[] showName, String[] fieldName, String filename,
                                   String fileType, HttpServletRequest request, HttpServletResponse response, String type) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        OutputStream os = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(filename + fileType, "UTF-8"));
            os = response.getOutputStream();

            HSSFSheet sheet = workbook.createSheet(filename);
            addTitle(sheet, showName, filename, getHeader(workbook), getContext(workbook));
            addContextByList(sheet, list, fieldName, getContext(workbook), false, type);
            workbook.write(os);
        } catch (Throwable e) {
            _log.error("excel导出出错：" + e.getMessage(), e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (Throwable e) {
                _log.error("导出Excel出错：" + e.getMessage(), e);
            }
        }
    }

    /**
     * 标题样式
     */
    public HSSFCellStyle getHeader(HSSFWorkbook workbook) {

        HSSFCellStyle format = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);
        format.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        format.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        format.setFont(font);
        return format;
    }

    /**
     *  内容样式
     */
    public HSSFCellStyle getContext(HSSFWorkbook workbook) {
        HSSFCellStyle format = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        format.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        format.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        format.setFont(font);
        return format;
    }

}
