package util;
 
import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 





import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.alibaba.fastjson.JSONObject;
import com.socket.util.SocketMessagFormer.MessageType;

import util.ChartUtils;
import util.Serie;
 
/**
 * 
 * 折线图
 *       <p>
 *       创建图表步骤：<br/>
 *       1：创建数据集合<br/>
 *       2：创建Chart：<br/>
 *       3:设置抗锯齿，防止字体显示不清楚<br/>
 *       4:对柱子进行渲染，<br/>
 *       5:对其他部分进行渲染<br/>
 *       6:使用chartPanel接收<br/>
 * 
 *       </p>
 */
public class LineChart {
    public LineChart() {
    }
 
    public static CategoryDataset createDataset1(int cell,String path) {
        // 标注类别
//        String[] categories1 = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        int num =cell;
        String pt= path;
        List<String> categories = new ArrayList<String>();
//        String name =null;
//	
//			switch(num){
//
//			// 开始直播 10010210
//			case 0:
//				name = "内存";
//				break;
//			case 1:
//				name = "上传的流量";
//				break;
//			case 2:
//				name = "下载的流量";
//				break;
//			case 3:
//				name = "cpu";
//				break;
//			default:
//				break;
//			}
//	
        
        Vector<Serie> series = new Vector<Serie>();
        List<Double> data1 = InputAndOutput.data(num, pt);
        
//        Double[] d=new Double[data1.size()];
//        data1.toArray(d);
        
        int size = data1.size();
        Double[] d1 = (Double[])data1.toArray(new Double[size]);
//        Double[] d1 ={18.9, 18.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2};
        // 柱子名称：柱子所有的值集合
        series.add(new Serie("", d1));
//        series.add(new Serie("New York", new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
//        series.add(new Serie("London", new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
//        series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
        
        
        for(int l = 0; l<size;l++){
        	categories.add(String.valueOf(l));
        }
        int sizec = categories.size();
        String[] d2 = (String[])categories.toArray(new String[sizec]);
        
        
        // 1：创建数据集合
        CategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, d2);
        return dataset;
    }
 
    public static DefaultCategoryDataset createDataset() {
        // 标注类别
        String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        Vector<Serie> series = new Vector<Serie>();
        // 柱子名称：柱子所有的值集合
//        series.add(new Serie("Tokyo", data));
//        series.add(new Serie("New York", new int[] { 83, 78, 98, 93, 106, 84, 105, 104, 91, 83, 106, 92 }));
        series.add(new Serie("London", new Double[] { 18.9, 18.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
//        series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
        // 1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }
    
//    public static ChartPanel createChart1() {
//        // 2：创建Chart[创建不同图形]
//        JFreeChart chart = ChartFactory.createLineChart("Monthly Average Rainfall", "", "Rainfall (mm)", createDataset());
//        // 3:设置抗锯齿，防止字体显示不清楚
//        ChartUtils.setAntiAlias(chart);// 抗锯齿
//        // 4:对柱子进行渲染[[采用不同渲染]]
//        ChartUtils.setLineRender(chart.getCategoryPlot(), false,true);//
//        // 5:对其他部分进行渲染
//        ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
//        ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
//        // 设置标注无边框
//        chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
//        // 6:使用chartPanel接收
//        ChartPanel chartPanel = new ChartPanel(chart);
//        return chartPanel;
//    }
    
    
//    public static ChartPanel createChart() {
//        // 2：创建Chart[创建不同图形]
//        JFreeChart chart = ChartFactory.createLineChart("Monthly Average Rainfall", "", "Rainfall (mm)", createDataset());
//        // 3:设置抗锯齿，防止字体显示不清楚
//        ChartUtils.setAntiAlias(chart);// 抗锯齿
//        // 4:对柱子进行渲染[[采用不同渲染]]
//        ChartUtils.setLineRender(chart.getCategoryPlot(), false,true);//
//        // 5:对其他部分进行渲染
//        ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
//        ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
//        // 设置标注无边框
//        chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
//        // 6:使用chartPanel接收
//        ChartPanel chartPanel = new ChartPanel(chart);
//        return chartPanel;
//    }
// 
//    public static void main(String[] args) {
//        final JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1024, 420);
//        frame.setLocationRelativeTo(null);
// 
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                // 创建图形
//                ChartPanel chartPanel = new LineChart().createChart();
//                frame.getContentPane().add(chartPanel);
//                frame.setVisible(true);
//            }
//        });
// 
//    }
// 
    
    
    
 

}
