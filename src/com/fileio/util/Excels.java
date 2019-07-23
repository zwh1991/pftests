package com.fileio.util;

import com.api.core.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * . Created on 2015-7-14�?
 * @author kaixu
 * @version 1.0 Description
 */
public class Excels {
    /** . logger */
    private Logger logger;
    /** . xlsFile excel�?*/
    private File xlsFile;
    /** . workbook */
    private Workbook workbook;
    /** . sheet */
    private Sheet sheet;
    
//    public static FileOutputStream fileOut;

    public JSONObject setCell(String caseNo, String sheetname, String paramName, 
    		String setSt, String path) {
    	
        sheet = workbook.getSheet(sheetname);
        JSONObject res = new JSONObject();
        if (sheet == null) {
            return null;
        }
        Row row0 = sheet.getRow(0);
//        Row row1 = sheet.getRow(nRow);
        int n = row0.getLastCellNum();
        
        int indexCaseId = 0;
        int indexComment = 0;
        int indexResult = 0;
        for (int i = row0.getFirstCellNum(); i < n; i++) {
            Cell cell0 = row0.getCell(i);

            // 获取数据项所在第几列
            if (cell0.getStringCellValue().equals("CaseId")){
            	indexCaseId = cell0.getColumnIndex();
            }
            if (cell0.getStringCellValue().equals("Comment")){
            	indexComment = cell0.getColumnIndex();
            }
            if (cell0.getStringCellValue().equals("Result")){
            	indexResult = cell0.getColumnIndex();
            }
        }
        
        FileOutputStream fileOut = null;
        try {
        	fileOut = new FileOutputStream(path);

        	int firstRowNum = sheet.getFirstRowNum()+1;
            int lastRowNum = sheet.getLastRowNum()+1;
            Row row = null;
            Cell cell_Result = null;
            Cell cell_Comment = null;
            for (int k=firstRowNum; k<lastRowNum; k++){
            	row = sheet.getRow(k);
            	//取得 对应case所在行
            	if (row.getCell(indexCaseId).getStringCellValue().equals(caseNo)){
            		row = sheet.getRow(k);
                    cell_Comment = row.getCell(indexComment, Row.CREATE_NULL_AS_BLANK);
        			cell_Comment.setCellValue("");
            		if (paramName.equals("Result")){
            			//取得k行的第x列
                    	cell_Result = row.getCell(indexResult, Row.CREATE_NULL_AS_BLANK);
                        cell_Result.setCellValue(setSt);
            		}else {
            			cell_Comment = row.getCell(indexComment, Row.CREATE_NULL_AS_BLANK);
            			cell_Comment.setCellValue(setSt);
            		}
            		workbook.write(fileOut);
            		break;
            	}	
            }
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
		            workbook = new XSSFWorkbook(new FileInputStream(path));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        
        return res;
	}
    
    /**
     * . 判断读取的excel表是2003还是2007
     * @param filePath
     *            路径
     */
    public Excels(final String filePath) {
//        PropertyConfigurator.configure("Log4j.properties");
//        logger = Logger.getLogger(Excels.class.getName());

        xlsFile = new File(filePath);
        if (!xlsFile.exists()) {
//            logger.error(filePath + " can't find!");
        } else {
            try {
                if (is2007(filePath)) {
                    workbook = new XSSFWorkbook(new FileInputStream(filePath));
                } else {
                    workbook = new HSSFWorkbook(new FileInputStream(filePath));
                }

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * . 判断是否�?007的excel�?
     * @param filePath
     *            路径
     * @return boolean
     */
    private boolean is2007(final String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * . 得到相对应的行数
     * @param sheetname
     *            表名
     * @return int
     */
    public final int getRowNum(final String sheetname) {
        sheet = workbook.getSheet(sheetname);
        return sheet.getLastRowNum();
    }

    /**
     * . 获取excel表中的数据
     * @param nRow
     * @param sheetname
     * @return JSONObject
     * @throws Exception
     */
    public final JSONObject getParamsCase(final int nRow,
            final String sheetname) throws Exception {
        Double d;
        int j;
        int tag = -1;
        double nTag = -1;
        String sTag = null;
        String param;
        sheet = workbook.getSheet(sheetname);
        JSONObject res = new JSONObject();
        if (sheet == null) {
            return null;
        }
        Row row0 = sheet.getRow(0);
        Row row1 = sheet.getRow(nRow);
        int n = row0.getLastCellNum();
        for (int i = row0.getFirstCellNum(); i < n; i++) {
            Cell cell0 = row0.getCell(i);
            Cell cell1 = row1.getCell(i);
            if (cell1 != null && cell1.getCellType() != Cell.CELL_TYPE_BLANK) {
                if (cell0.getStringCellValue().equals("FuncTag")
                		|| cell0.getStringCellValue().equals("userId")) {
                    if (cell1.getCellType() == Cell.CELL_TYPE_STRING) {
                    	// 判断字符串是否为中文
                    	if (Core.isChineseChar(cell1.getStringCellValue())){
                    		sTag = cell1.getStringCellValue();
                    		row1.getCell(i).setCellValue(sTag);
                    	}
//                    	else {
//                    		tag = Integer.parseInt(cell1.getStringCellValue());
//                    		row1.getCell(i).setCellValue(tag);
//                    	}	
                    } else if (cell1.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    	tag = (int) cell1.getNumericCellValue();
                		row1.getCell(i).setCellValue(tag);
                    }
                }
                param = cell0.getStringCellValue();
                switch (cell1.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    res.put(param, cell1.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    d = cell1.getNumericCellValue();
                    j = (int) cell1.getNumericCellValue();
                    if (d == j) {
                    	res.put(param, j);
                    } else {
                    	res.put(param, String.valueOf(j));
                    }
//                    if (d == j) {
//                        res.put(param, String.valueOf(j));
//                    } else {
//                        res.put(param, "" + d);
//                    }
                    break;
                default:
                    res.put(param, cell1.getStringCellValue());
                }
            } else {
                param = cell0.getStringCellValue();
                if (!param.equals("json")) {
                    res.put(param, "");
                }
            }
        }
        String jo = res.toJSONString();
        if (sheetname.equals("XiaoMiLogin")){
        	jo = jo.replace("\"appId\":\"", "\"appId\":");
        	jo = jo.replace("\",\"comments\"", ",\"comments\"");
        }
//        jo = jo.replace("\"[{", "[{").replace("}]\"", "}]")
//                .replaceAll("\\\\", "");
        try {
            res = JSON.parseObject(jo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
