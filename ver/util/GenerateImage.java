package util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Before;
import org.junit.Test;

import com.api.core.Core;

import performance.AndroidMonitor;
import performance.ImageExcel;
import performance.pojo;

public class GenerateImage {
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String prop = null;
	private static String path = null;
	private static String packagename =null;
	private static String savePath =null;
	
	
	@Before
	public void dataPrepare(){
		File directory = new File(".");
//		System.out.println(new File(".").getAbsolutePath());
        try {
            path = directory.getCanonicalPath();//获取当前路径
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
		
        //获取包名
        packagename =AndroidMonitor.getPackageName();
        //获取设备名称
        prop = AndroidMonitor.getprop();
        //数据存储地址
        savePath = path+"/"+"performance.xls";      
	}
	

	@Test
	public void N001_GetData() throws Exception {
		ImageExcel imageExcel = new ImageExcel();
		List<pojo> list = new ArrayList<pojo>();		
		System.out.println("开始收集数据:.......");
		double firstsFlow = AndroidMonitor.sFlow(packagename);
		double firstrFlow = AndroidMonitor.rFlow(packagename);
		double firstPower = AndroidMonitor.getPower(packagename);
		
		for (int i = 0; i < 20; i++){
			String Memory = Double.toString(AndroidMonitor.getMemory(packagename));
			String CPU = Double.toString(AndroidMonitor.getCPU(packagename));
			String sFlow = Double.toString(AndroidMonitor.sFlow(packagename)-firstsFlow);
			String rFlow = Double.toString(AndroidMonitor.rFlow(packagename)-firstrFlow);		
			String Power = Double.toString(AndroidMonitor.getPower(packagename)-firstPower);
			
//			lastsFlow = AndroidMonitor.sFlow(packagename);
//			lastrFlow = AndroidMonitor.rFlow(packagename);
			pojo data = new pojo(Memory, sFlow, rFlow, CPU, Power);
			list.add(data);
		}
		System.out.println("数据采集完成");
		//数据储存到excle
		imageExcel.generateExcel(list, savePath, prop+"性能数据");;		

	}
		
		//每2秒采集一次
//		try {
//			Thread.sleep(2000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//				}
		
		
	@Test
	public void N002_CreatePicture() throws Exception{		
		String valueAxisLabel= null;
		for(int i =0 ; i < 5; i++){
			switch(i){
			case 0:
				valueAxisLabel = "内存(M)";
				break;
			case 1:
				valueAxisLabel = "上传的流量KB";
				break;
			case 2:
				valueAxisLabel = "下载的流量KB";
				break;
			case 3:
				valueAxisLabel = "当前CPU使用率";
				break;
			case 4:
				valueAxisLabel = "电量消耗Amh";
				break;	
			default:
				valueAxisLabel = "数据异常";
				break;
			}
			JFreeChart chart = ChartFactory.createLineChart(valueAxisLabel+"_"+prop, "时间",
					"", LineChart.createDataset1(i, savePath));
	        String outputPath = path+"/"+valueAxisLabel+".png";
			InputAndOutput.saveAsFile(chart, outputPath, 1500, 800);	
		}
		System.out.println("图片绘制完成");
}
	
	
}
