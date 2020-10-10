package com.swustacm.poweroj.common.util;

import com.google.gson.Gson;
import com.swustacm.poweroj.common.PojoToMapUtils;
import com.swustacm.poweroj.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.TypeMismatchException;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel操作工具
 * @author xingzi
 */
@Slf4j
public class ExcelUtils<T> {
    public final static String XLS = "xls";
    public final static String XLSX = "xlsx";

    public static void main(String[] args) throws IOException {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUid(23423423);
        user.setName("132423");
        user.setPassword("sdasda");
        user.setToken("edfdfsfs");
        user.setAvatar("#@$2342");
        users.add(user);
        List<String> strings = new ArrayList<String>(){{add("3rerew");add("3242342");}};

        Workbook workbook = ExcelUtils.exportData(users,strings,ExcelUtils.XLSX);
        OutputStream outputStream = new FileOutputStream("D:/4455454.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();
        List<User> users1 = ExcelUtils.readExcel("D:/4455454.xlsx",user);
        System.out.println(new Gson().toJson(users1));
    }

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param excelEnum   文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String excelEnum) throws IOException {
        Workbook workbook;
        if (XLS.equals(excelEnum)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (XLSX.equals(excelEnum)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new TypeMismatchException("不支持的文件类型", excelEnum.getClass());
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     *
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static <T> List<T> readExcel(String fileName, T bean) {
        Workbook workbook = null;
        FileInputStream inputStream = null;
        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                log.warn("指定的Excel文件不存在！");
                return null;
            }
            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            if (ExcelUtils.XLS.equals(fileType)) {
                workbook = getWorkbook(inputStream, ExcelUtils.XLS);
            } else {
                workbook = getWorkbook(inputStream, ExcelUtils.XLSX);
            }
            // 读取excel中的数据
            return parseExcel(workbook, bean);
        } catch (Exception e) {
            log.warn("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.warn("关闭数据流出错！错误信息：" + e.getMessage());
            }
        }
    }

    /**
     * 解析Excel数据
     *
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static <T> List<T> parseExcel(Workbook workbook, T bean) {
        List<T> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);
            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }
            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                log.warn("解析Excel失败，在第一行没有读取到任何数据！");
            }
            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (null == row) {
                    continue;
                }
                T resultData = convertRowToData(row, bean);
                if (null == resultData) {
                    log.warn("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }
        return resultDataList;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     * <p>
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    @SuppressWarnings("unchecked")
    private static <T> T convertRowToData(Row row, T bean) {
        T resultData;
        try {
            resultData = (T) bean.getClass().newInstance();
            Field[] fields = resultData.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                PojoToMapUtils.setValue(fields[i],resultData,row.getCell(i));
            }
            return resultData;
        } catch (InstantiationException | IllegalAccessException   e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 生成Excel并写入数据信息
     *
     * @param dataList 数据列表
     * @return 写入数据后的工作簿对象
     */
    public static <T> Workbook exportData(List<T> dataList, List<String> header,String excelEnum) {
        // 生成xlsx的Excel
        Workbook workbook;
        if (XLS.equals(excelEnum)) {
            workbook = new HSSFWorkbook();
        } else if (XLSX.equals(excelEnum)) {
            workbook = new SXSSFWorkbook();
        } else {
            throw new TypeMismatchException("文件类型不支持", excelEnum.getClass());
        }
        // 生成Sheet表，写入第一行的列头
        Sheet sheet = buildDataSheet(workbook,header);
        //构建每行的数据内容
        int rowNum = 1;
        for (T data : dataList) {
            if (data == null) {
                continue;
            }
            //输出行数据
            Row row = sheet.createRow(rowNum++);
            convertDataToRow(data, row);
        }
        return workbook;
    }

    /**
     * 生成sheet表，并写入第一行数据（列头）
     *
     * @param workbook 工作簿对象
     * @return 已经写入列头的Sheet
     */
    private static Sheet buildDataSheet(Workbook workbook,List<String> header) {
        Sheet sheet = workbook.createSheet();
        if(CollectionUtils.isEmpty(header)){
            throw new NullPointerException("Excel第一行数据不能为null");
        }
        // 设置列头宽度
        for (int i = 0; i < header.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < header.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(header.get(i));
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    /**
     * 设置第一行列头的样式
     *
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    private static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //粗体字设置
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    /**
     * 将数据转换成行
     *
     * @param data 源数据
     * @param row  行对象
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T> void convertDataToRow(T data, Row row) {
        try {
            Cell cell;
            T resultData = (T) data.getClass().newInstance();
            Field[] fields = resultData.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                final Object attrValue  = PojoToMapUtils.getValue(fields[i],data);
                cell = row.createCell(i);
                cell.setCellValue(String.valueOf(attrValue));
            }
        } catch (IllegalAccessException  | InstantiationException  e) {
            e.printStackTrace();
        }
    }
}
