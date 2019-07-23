package com.fileio.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tools.util.RegexUtil;

/**
 * . 文件处理
 * 
 * @author cln8596
 * 
 */
public final class BaseIO {
    /**
     * . 私有构�?方法
     */
    private BaseIO() {
    }

    static {
        PropertyConfigurator.configure("Log4j.properties");
    }

    /** . logger */
    private static Logger logger = Logger.getLogger(BaseIO.class.getName());

    /**
     * . 向文件中写入�?��文本（不会覆盖该文本原有的内容）
     * 
     * @param sBuf
     *            �?��写入的文本内�?
     * @param sFilename
     *            �?��被写入的文件的完整路径，路径中不可以包含空格和中文字�?
     */
    public static synchronized void lineToFile(final String sBuf,
            final String sFilename) {
        final int time = 8192;
        String filepath = null;
        if (sFilename.contains(":") || sFilename.startsWith("/")) {
            filepath = sFilename;
        }
        File wFile = new File(filepath);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(wFile, true), time);
            bw.append(sBuf);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * . 按照�?��定的后缀名获取指定目录下的文件列�?
     * 
     * @param fileList
     *            文件列表,获取到的文件列表会被写入其中
     * @param filePath
     *            目录的文件路�?
     * @param regex
     *            后缀表达式，多个后缀名时中间用�?|”符号间隔，比如"3gp|mp4"
     */
    public static void getFileList(final ArrayList<String> fileList,
            final String filePath, final String regex) {
        logger.debug("getFileList <-----");
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                getFileList(fileList, files[i].getAbsolutePath(), regex);
            } else {
                String path = files[i].getAbsolutePath().trim();
                if (RegexUtil.validateSting(path.toLowerCase(), ".+\\.("
                        + regex + ")")) {
                    fileList.add(files[i].getAbsolutePath());
                }
            }
        }
        logger.info("getFileList ----->");
    }

    /**
     * . 删除文件
     * 
     * @param path
     *            �?��被删除的文件的完整路�?
     */
    public static void delFile(final String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * . 将指定文件移到到目标目录
     * 
     * @param srcFile
     *            �?��被移动的文件的完整路�?
     * @param destPath
     *            目标目录的路�?
     * @return True表示移动成功，false表示移动失败
     */
    public static boolean move(final String srcFile, final String destPath) {
        File file = new File(srcFile);
        File dir = new File(destPath);

        // Move file to new directory
        boolean success = file.renameTo(new File(dir, file.getName()));

        return success;
    }

    /**
     * 去除文件名中�?�?
     * 
     * @param filename
     *            �?��被格式化的文件名
     * @return 除去后新的文件名
     */
    public static String validFilename(final String filename) {
        if (null == filename) {
            return null;
        }
        int iSize = filename.length();
        StringBuffer strRes = new StringBuffer();
        int iCur = 0;
        String strTemp;
        while (iCur < iSize) {
            strTemp = filename.substring(iCur, iCur + 1);
            if (strTemp.compareTo("*") == 0 || strTemp.compareTo("?") == 0) {
            } else {
                strRes.append(strTemp);
            }
            iCur++;
        }
        return strRes.toString();
    }

    /**
     * . 将带空格的字符串转换为一个列表，比如“a b c"转换为{a,b,c}
     * 
     * @param line
     *            �?��被转换的字符�?
     * @return 转换后得到的字符串列�?
     */
    public static List<String> lineToList(final String line) {
        List<String> ret = new ArrayList<String>();
        Scanner s = new Scanner(line);
        while (s.hasNext()) {
            ret.add(s.next());
        }
        s.close();
        return ret;
    }

    /**
     * . 将List中的元素按照�?��元素�?��的方式写入到文本中（不会覆盖文本原有的内容）
     * 
     * @param path
     *            �?��写入的文件的完整路径
     * @param array
     *            �?��写入的List
     */
    public static void arrayToFile(final String path,
            final List<String> array) {
        File file = new File(path);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            for (int i = 0; i < array.size(); i++) {
                bw.write(array.get(i));
                bw.newLine();
                bw.flush();
            }
            bw.write(" ");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能：Java读取txt文件的内�?
     * 步骤�?：先获得文件句柄
     * 2：获得文件句柄当做是输入�?��字节码流，需要对这个输入流进行读�?
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()�?
     * 备注：需要�?虑的是异常情�?
     * @param filePath
     */
    public static List<Object> readTxtFile(String filePath){
    	String lineTxt = null;
    	List<Object> ls = new ArrayList<Object>();
//    	System.err.println(filePath);
    	
        try {
        	String encoding="GBK";
        	File file=new File(filePath);
        	if(file.isFile() && file.exists()){ //判断文件是否存在
        		InputStreamReader read = new InputStreamReader(
        				new FileInputStream(file),encoding);//考虑到编码格�?
        		BufferedReader bufferedReader = new BufferedReader(read);
        		
        		while((lineTxt = bufferedReader.readLine()) != null){
        			ls.add(lineTxt);
        		}
        		read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return ls;
    }
}
