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
import org.junit.Test;

import performance.AndroidMonitor;
import performance.ImageExcel;
import performance.pojo;

public class GenerateImage {
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	@Test
	public void test2() throws Exception {
		File directory = new File(".");
        String path = null;
        try {
            path = directory.getCanonicalPath();//获取当前路径
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
		ImageExcel imageExcel = new ImageExcel();
		List<pojo> list = new ArrayList<pojo>();
		String packagename="com.thankyo.hwgame";
		System.out.println("开始收集数据:.......");
		double lastsFlow = AndroidMonitor.sFlow(packagename);
		double lastrFlow = AndroidMonitor.rFlow(packagename);
		double firstPower = AndroidMonitor.getPower(packagename);
		String prop = AndroidMonitor.getprop();
//		System.out.println("lastsFlow"+ lastsFlow);
		
		for (int i = 0; i < 20; i++){
			String Memory = Double.toString(AndroidMonitor.getMemory(packagename));
			String CPU = Double.toString(AndroidMonitor.getCPU(packagename));
			String sFlow = Double.toString(AndroidMonitor.sFlow(packagename)-lastsFlow);
			String rFlow = Double.toString(AndroidMonitor.rFlow(packagename)-lastrFlow);		
			String Power = Double.toString(AndroidMonitor.getPower(packagename)-firstPower);
			
			lastsFlow = AndroidMonitor.sFlow(packagename);
			lastrFlow = AndroidMonitor.rFlow(packagename);
//			System.out.println("sFlow:"+ sFlow);
			pojo bi1 = new pojo(Memory, sFlow, rFlow, CPU, Power);
			int l = i+1;
			System.out.println("采集数据中："+(i+1)+"次");
			list.add(bi1);
//			System.err.println(df.format(System.currentTimeMillis()));
		}
		System.out.println("数据采集完成");
		//数据储存到excle
		String savePath = path+"/"+"performance.xls";
		imageExcel.generateExcel(list, savePath, prop+"性能数据");;
		
		System.out.println(new File(".").getAbsolutePath());
		
		
		//每2秒采集一次
//		try {
//			Thread.sleep(2000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//				}
		
		
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
			
//			ChartPanel chart =LineChart.createChart(data1);
//			String outputPath= "D:/Image/"+valueAxisLabel+".png";
	        String outputPath = path+"/"+valueAxisLabel+".png";
//	        System.err.println(outputPath);
			InputAndOutput.saveAsFile(chart, outputPath, 1500, 800);
			
		}
		System.out.println("图片绘制完成");
	}
	
	
//	@Test
//	public void test22() throws Exception{
//		JFreeChart chart = ChartFactory.createLineChart("当前CPU使用率", "时间",
//				"", LineChart.createDataset());
//		
////		ChartPanel chart =LineChart.createChart(data1);
//		String outputPath= "D:/Image/"+"当前1"+".png";
////        String outputPath = path+"/"+valueAxisLabel+"_"+prop+".png";
////        System.err.println(outputPath);
//
//			InputAndOutput.saveAsFile(chart, outputPath, 1500, 800);
//
//	}
}
