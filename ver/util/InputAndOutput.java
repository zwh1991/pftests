package util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.junit.Test;

public class InputAndOutput {

	   public static void saveAsFile(JFreeChart chart, String outputPath,      
	            int weight, int height)throws Exception {      
	        FileOutputStream out = null;      
	            File outFile = new File(outputPath);      
	            if (!outFile.getParentFile().exists()) {      
	                outFile.getParentFile().mkdirs();      
	            }      
	            out = new FileOutputStream(outputPath);      
	            // 保存为PNG      
//	            ChartUtilities.writeChartAsPNG(out, chart, weight, height);      
	            // 保存为JPEG      
	             ChartUtilities.writeChartAsJPEG(out, chart, weight, height);      
	            out.flush();      
	            if (out != null) {      
	                try {      
	                    out.close();      
	                } catch (IOException e) {      
	                    // do nothing      
	                }      

	        }      
	    }
	   
	   
	   
	    public static List<Double> data(int cell,String path) {
	    	List<Double> data = new ArrayList<Double>();
	    	int cells= cell;
	    	try {
	    	Workbook wb = new HSSFWorkbook(new FileInputStream(new File(path)));
	    	//对excel文件的处理
	    	Sheet st = wb.getSheetAt(0);
	    	Row row = st.getRow(0);
	    	if(st != null){
//	    	map = new HashMap<Integer, String[]>(st.getLastRowNum());
//	    	map = new HashMap<String, Double>(row.getLastCellNum());
//	    	for(int i = 1;i < st.getLastRowNum();i++){
	    	for(int i = 0;i < st.getLastRowNum()+1;i++){
//	    		int a = st.getLastRowNum();
//	    		int  b = row.getLastCellNum();
	    	//行
//	    	 row = st.getRow(i);
//	    	 int[] columns = st.getColumnBreaks();

	    	if(row != null){
//	    	int rows = row.getLastCellNum();
	    	//最后一行
	    	int rows = st.getLastRowNum();
//	    	double strs = 0;
	    	if(rows > 0){
//	    	strs = new String[rows];
//	    	for(int j = 0;j < a;j++){
//	    	for(int j = 0;j < a;j++){
	    	//列
	    	row = st.getRow(i);
	    	Cell c = row.getCell(cells);
//	    	Cell c = columns
	    	
//	    	strs = c.toString();

	    	if(c.getCellType() == Cell.CELL_TYPE_STRING){
//	    	strs = c.toString();
	    		//不读取中文
	    		continue;
	    	}else if(c.getCellType() == Cell.CELL_TYPE_NUMERIC){
	    	double d = c.getNumericCellValue();

//	    	String s = String.valueOf(d);
//	    	s = s.substring(0, s.indexOf("E")).replace(".", "");
//	    	strs = s;
	    	data.add(d);
//	    	}
	    	}
	    	}
	    	
	    	}
	    	}
	    	}
	    	} catch (FileNotFoundException e) {
	    	System.out.println("文件未找到.." + e.getClass().getName() + "\t 信息：" + e.getMessage());
	    	} catch (IOException e) {
	    	System.out.println("Io异常：" + e.getClass().getName() + "\t 信息:" + e.getMessage());
	    	} 
	    	return data;
	    	}

}
