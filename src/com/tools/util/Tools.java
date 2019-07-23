package com.tools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
/**.
 * 获取各种信息
 * @author kai.xu
 *
 */
public final class Tools {
    /**. 构建方法*/
    private Tools() {
    }
    /**
     * . 当前进程休眠
     * 
     * @param timeout
     *            休眠的时间，单位s/10
     */
    public static void sleep(final int timeout) {
        try {
            Thread.sleep(timeout*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * . 将Byte类型转换为Hex类型
     * 
     * @param b
     *            Byte类型变量
     * @return String
     */
    public static String toHex(final byte b) {
        return ("" + "0123456789ABCDEF".charAt(0xf & b >> 4) + "0123456789ABCDEF"
                .charAt(b & 0xf));
    }

    /**
     * . get Current time
     * 
     * @return String
     */
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * . 获取行信息
     * 
     * @return String
     */
    public static String getLineInfo() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return ste.getFileName() + "(" + ste.getLineNumber() + ")";
    }

    /**
     * . 执行进程
     * 
     * @param cmd
     *            执行命令
     */
    public static void runExec(final String cmd) {
        try {
            // 创建本机进程
            Process p = Runtime.getRuntime().exec(cmd);

            // 等待 Process 执行完毕再继续向下运行
            p.waitFor();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("run finish");
    }

    /**
     * . 随意生成字符串
     * 
     * @param length
     *            表示生成字符串的长度
     * @return String
     */
    public static String getRandomString(final int length) {
        String base = "0123456789abcdef"; // 生成字符串从此序列中
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    /**
     * . 随意生成字符串
     * 
     * @param length
     *            表示生成字符串的长度
     * @return String
     */
    public static String getIdentiyNumber(final int length) {
        String base = "123456789"; // 生成字符串从此序列中
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * . 随意生成字符串
     * 
     * @param length
     *            表示生成字符串的长度
     * @return String
     */
    public static String getRandomFloat(final int length) {
        String base = "0123456789"; // 生成字符串从此序列中
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }
    
    /**
     * 将二进制转换成图片保存
     * @param imgStr 二进制流转换的字符串
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return 
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgByBytes(File imgFile,String imgPath,String imgName){
 
        int stateInt = 1;
        if(imgFile.length() > 0){
            try {
                File file=new File(imgPath,imgName);//可以是任何图片格式jpg,.png等
                FileOutputStream fos=new FileOutputStream(file);
                 
                FileInputStream fis = new FileInputStream(imgFile);
                   
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = fis.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
                fis.close();
     
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
            }
        }
        return stateInt;
    }
    
    public static String getExpireTime(int expireTime) {
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, expireTime);

        return sf.format(c.getTime());
    }
    
    public static String MD5(String plainText) {
        String result = null;
        // 首先判断是否为空
        if (plainText == null || "".equals(plainText.trim())) {
            return null;
        }
        try {
            // 首先进行实例化和初始化
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 得到一个操作系统默认的字节编码格式的字节数组
            byte[] btInput = plainText.getBytes();
            // 对得到的字节数组进行处理
            md.update(btInput);
            // 进行哈希计算并返回结果
            byte[] btResult = md.digest();
            // 进行哈希计算后得到的数据的长度
            StringBuffer sb = new StringBuffer();
            for (byte b : btResult) {
                int bt = b & 0xff;
                if (bt < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    /**
     * 生成红包发放历史记录id
     * @param roomId 房间id
     * @param userId 用户id
     * @return 返回红包发放历史记录id
     */
    public static String generateHistId(int roomId, int userId) {
        long timestamp = new Date().getTime();
        return MD5(roomId + "," + userId + "," + timestamp + new Random().nextInt(100)).toUpperCase();
    }

    public static long setStartTime() {

    	Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

    	int year = c.get(Calendar.YEAR); 
    	int month = c.get(Calendar.MONTH)+1; 
    	int day = c.get(Calendar.DATE); 

    	String time = year + "/" + month + "/" + day + " " + "00:00:00";
//    	Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = null;
		try {
			date = dateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        long timeStemp = date.getTime();

        return timeStemp;
    }
    
    public static long setEndTime() {

    	Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

    	int year = c.get(Calendar.YEAR); 
    	int month = c.get(Calendar.MONTH)+1; 
    	int day = c.get(Calendar.DATE); 

    	String time = year + "/" + month + "/" + day + " " + "23:59:59";
//    	Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = null;
		try {
			date = dateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        long timeStemp = date.getTime();

        return timeStemp;
    }
    
    public static boolean sortList(JSONArray ja, String sortTag){
    	boolean b = true;

    	long o1 = 0;
    	long o2 = 0;
    	for (int i=1; i<ja.size(); i++){
    		o1 = Long.valueOf(ja.getJSONObject(i-1).getString(sortTag));
    		o2 = Long.valueOf(ja.getJSONObject(i).getString(sortTag));
    		
    		if (o2 > o1){
    			return false;
    		}else {
    			continue;
    		}
    	}
    	
    	return b;
    }
    
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName, ArrayList<String> list) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
            	list.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static String currentTimeStamp(){  
        long time = System.currentTimeMillis();  
        String t = String.valueOf(time/1000);  
        return t;  
    }
    
    //获得本周一0点时间
    public static int getTimesWeekmorning(){
    	Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (int) (cal.getTimeInMillis()/1000);
    }

    //获得本周日24点时间
//    public static int getTimesWeeknight(){
//    	Calendar cal = Calendar.getInstance();
//        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        return (int) ((cal.getTime().getTime()+ (7 24 60 60 1000))/1000);
//    }

    //获得本月第一天0点时间
    public static int getTimesMonthmorning(){
    	Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return (int) (cal.getTimeInMillis()/1000);
    }

    //获得本月最后一天24点时间
    public static int getTimesMonthnight(){
    	Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return (int) (cal.getTimeInMillis()/1000);
    }

    //获得当天0点时间
    public static int getTimesmorning(){
    	Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }
    
    //获得当天24点时间
    public static int getTimesnight(){
    	Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }
      
    //  输出结果：  
    //  timeStamp=1417792627  
    //  date=2014-12-05 23:17:07  
    //  1417792627  
    public static void main(String[] args) {  
        String timeStamp = currentTimeStamp();  
        System.out.println("timeStamp="+timeStamp);  
          
        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");  
        System.out.println("date="+date);  
          
        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");  
        System.out.println(timeStamp2);  
    }
}
