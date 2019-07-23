package performance;

import static org.junit.Assert.*;

import org.junit.Test;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javafx.scene.chart.NumberAxis;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;


public class ImageExcel {

	/**
	 * 将数据保存进Eacel中
	 * 
	 * @param list
	 *            数据集合
	 * @param savePath
	 *            保存路径
	 */
	public void generateExcel(List<pojo> list, String savePath,String name) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		
		HSSFSheet sheet = workbook.createSheet(name);
		sheet.setDefaultRowHeightInPoints(12);// 设置缺省列高
		sheet.setDefaultColumnWidth(10);// 设置缺省列宽

//		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
//		HSSFRow row1 = sheet.createRow(0);
//		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//		HSSFCell cell = row1.createCell(0);
//		// 设置单元格内容
//		cell.setCellValue("性能数据");
//		//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		

		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(0);
		// 创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("内存");
		row2.createCell(1).setCellValue("上传的流量");
		row2.createCell(2).setCellValue("下载的流量");
		row2.createCell(3).setCellValue("cpu");
		row2.createCell(4).setCellValue("Power");
		for (int i = 0, len = list.size(); i < len; i++) {
			pojo Info = list.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(Double.parseDouble(Info.getMemory()));
			row.createCell(1).setCellValue(Double.parseDouble(Info.getsFlow()));
			row.createCell(2).setCellValue(Double.parseDouble(Info.getrFlow()));
			row.createCell(3).setCellValue(Double.parseDouble(Info.getCPU()));
			row.createCell(4).setCellValue(Double.parseDouble(Info.getPower()));
		}

		// 输出Excel文件
		try {
			FileOutputStream fos = new FileOutputStream(new File(savePath));
			workbook.write(fos);
//			workbook.close();
			fos.close();
			System.out.println("生成excel文档成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("生成excel文档失败");
		}
	}
	
	

}
