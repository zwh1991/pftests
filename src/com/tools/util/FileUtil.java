package com.tools.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * . 文件处理
 * @author cln8596
 */
public class FileUtil {

    private static int EXT_Other = 0;
    private static int EXT_Photo = 3;
    private static int EXT_Video = 5;
    private static int EXT_Music = 6;
    private static File zipFile; 
    private static final int BUFFER = 8192;

    /**.
     * 文件类型判断
     * @param filePath
     *            文件路径
     * @return int
     * @throws Exception
     *             if has error
     */
    public static int getFileType(final String filePath) throws Exception {
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1,
                filePath.length()); // get the extentions of file
        System.out.println(ext);
        int fileType = EXT_Other;
        if (ext.equalsIgnoreCase("mp4") | ext.equalsIgnoreCase("m4v")
                | ext.equalsIgnoreCase("3gpp") | ext.equalsIgnoreCase("3gp")
                | ext.equalsIgnoreCase("3g2") | ext.equalsIgnoreCase("wmv")
                | ext.equalsIgnoreCase("mkv") | ext.equalsIgnoreCase("3gpp2")) {
            fileType = EXT_Video;
        } else if (ext.equalsIgnoreCase("jpg") | ext.equalsIgnoreCase("jpeg")
                | ext.equalsIgnoreCase("gif") | ext.equalsIgnoreCase("png")
                | ext.equalsIgnoreCase("bmp") | ext.equalsIgnoreCase("wbmp")) {
            fileType = EXT_Photo;
        } else if (ext.equalsIgnoreCase("mp3") | ext.equalsIgnoreCase("m4a")
                | ext.equalsIgnoreCase("wav") | ext.equalsIgnoreCase("amr")
                | ext.equalsIgnoreCase("awb") | ext.equalsIgnoreCase("wma")
                | ext.equalsIgnoreCase("ogg") | ext.equalsIgnoreCase("mid")
                | ext.equalsIgnoreCase("xmf") | ext.equalsIgnoreCase("rtttl")
                | ext.equalsIgnoreCase("smf") | ext.equalsIgnoreCase("imy")) {
            fileType = EXT_Music;
        }
        return fileType;
    }

    /**
     * . 读取文件
     * @param filepath
     *            文件路径
     * @param pathMap
     *            存放问题
     * @return Map<Integer, String>
     * @throws Exception
     *             if has error
     */
    public static Map<Integer, String> readfile(final String filepath,
            Map<Integer, String> pathMap) throws Exception {
        if (pathMap == null) {
            pathMap = new HashMap<Integer, String>();
        }
        File file = new File(filepath);
        if (!file.isDirectory() & !file.isHidden()) {
            pathMap.put(pathMap.size(), file.getPath());
        } else if (file.isDirectory()) { //
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "/" + filelist[i]);
                if (!readfile.isDirectory() & !readfile.isHidden()) {
                    pathMap.put(pathMap.size(), readfile.getPath());
                } else if (readfile.isDirectory()) { //
                    readfile(filepath + "/" + filelist[i], pathMap);
                }
            }
        }
        return pathMap;
    }

    /**
     * . 切割文件
     * @param filepath
     *            文件路径
     * @param blockSize
     *            大小
     * @throws Exception
     *             if has error
     */
    public static void cutFile(final String filepath, final long blockSize)
            throws Exception {
        File file = new File(filepath);
        long fileSize = file.length();
        long writeTotal = 0;
        long writeSize = 0;
        // String fileName = file.getName();
        // System.out.println(fileSize);
        // System.out.println(fileName);
        long blockNum = fileSize / blockSize + 1;
        // System.out.println(blockNum);
        for (int i = 1; i <= blockNum; i++) {
            String blockName = filepath.substring(0, filepath.lastIndexOf("."))
                    + ".part" + String.valueOf(i);
            // System.out.println(blockName);

            if (i < blockNum) {
                writeSize = blockSize;
            } else {
                writeSize = fileSize - writeTotal;
            }

            writeFile(filepath, blockName, writeSize, writeTotal);
            File blockfile = new File(blockName);
            String sets = "attrib +H \"" + blockfile.getAbsolutePath() + "\"";

            // System.out.println(sets);
            //
            Runtime.getRuntime().exec(sets);

            writeTotal = writeTotal + writeSize;
            // System.out.println(writeTotal);
        }

    }

    /**
     * . 写文�?
     * @param filepath
     *            文件路径
     * @param blockName
     *            名称
     * @param blockSize
     *            大小
     * @param beginPos
     *            �?��位置
     * @return boolean
     * @throws Exception
     *             if has error
     */
    public static boolean writeFile(final String filepath,
            final String blockName, final long blockSize, final long beginPos)
            throws Exception {
        RandomAccessFile raf = null;
        FileOutputStream fos = null;
        byte[] bt = new byte[1024];
        long writeByte = 0;
        int len = 0;
        try {
            raf = new RandomAccessFile(filepath, "r");
            raf.seek(beginPos);
            fos = new FileOutputStream(blockName);
            while ((len = raf.read(bt)) > 0) {
                if (writeByte < blockSize) { //
                    writeByte = writeByte + len;
                    if (writeByte <= blockSize) {
                        fos.write(bt, 0, len);
                    } else {
                        len = len - (int) (writeByte - blockSize);
                        fos.write(bt, 0, len);
                    }
                }
            }
            fos.close();
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fos != null) {
                    fos.close();
                }
                if (raf != null) {
                    raf.close();
                }
            } catch (Exception f) {
                f.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**.
     * 获取file二进制文�?
     * @param fileName 文件�?
     * @return byte[]
     * @throws FileNotFoundException if has error
     */
    public final byte[] getFileByte(final String fileName)
            throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        return getFileByte1(fileInputStream);
    }

    /**.
     * 获取file二进制文�?
     * @param url url连接
     * @return byte[]
     * @throws IOException if has error
     */
    public final byte[] getFileByte(final URL url) throws IOException {
        if (url != null) {
            return getFileByte1(url.openStream());
        } else {
            return null;
        }
    }

    /**.
     * 获取数据流二进制文件
     * @param in 输入数据�?
     * @return byte[]
     */
    public final byte[] getFileByte1(final InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        try {
            copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();

    }

   /**.
    * copy
    * @param in 输入�?
    * @param out 输出�?
    * @throws IOException if has error
    */
    private void copy(final InputStream in, final OutputStream out)
            throws IOException {
        try {
            byte[] buffer = new byte[4096];
            int nrOfBytes = -1;
            while ((nrOfBytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, nrOfBytes);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
    
    public static void saveFile(String buffer, String directory, String filename)
    		throws IOException {
    	File f = new File(directory, filename); 
    	FileOutputStream fos=null;
		
    	try {
    	    if (!f.exists()) {//文件不存在则创建
    	        f.createNewFile();
    	    }
    	    fos=new FileOutputStream(f);

    	    fos.write(buffer.getBytes());//写入文件内容
    	  fos.flush();   
    	} catch (IOException e) {
    	     System.err.println("文件创建失败");
    	}finally{
    	    if (fos!=null) {
    	        try {
    	            fos.close();
    	        } catch (IOException e) {
    	            System.err.println("文件流关闭失败");
    	        }
    	    }
    	}
    }
    
    public static void zip(String zipFileName, File inputFile) throws Exception {
		System.out.println("压缩�?..");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		BufferedOutputStream bo = new BufferedOutputStream(out);
		zip(out, inputFile, inputFile.getName(), bo);
		bo.close();
		out.close(); // 输出流关�?
		System.out.println("压缩完成");
	}

	public static void zip(ZipOutputStream out, File f, String base,
			BufferedOutputStream bo) throws Exception { // 方法重载
		int k = 1;
				
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
				System.out.println(base + "/");
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
			}
			System.out.println("�? + k + ");
			k++;
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
			System.out.println(base);
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bi = new BufferedInputStream(in);
			int b;
			while ((b = bi.read()) != -1) {
				bo.write(b); // 将字节流写入当前zip目录
			}
			bi.close();
			in.close(); // 输入流关�?
		}
	}
   

    /** 
     * 把字节数组保存为�?��文件 
     *  
     * @param b 
     * @param outputFile 
     * @return 
     */  
    public static File getFileFromBytes(byte[] b, String outputFile) {  
        File ret = null;  
        BufferedOutputStream stream = null;  
        try {  
            ret = new File(outputFile);  
            FileOutputStream fstream = new FileOutputStream(ret);  
            stream = new BufferedOutputStream(fstream);  
            stream.write(b);  
        } catch (Exception e) {  
            // log.error("helper:get file from byte process error!");  
            e.printStackTrace();  
        } finally {  
            if (stream != null) {  
                try {  
                    stream.close();  
                } catch (IOException e) {  
                    // log.error("helper:get file from byte process error!");  
                    e.printStackTrace();  
                }  
            }  
        }  
        return ret;  
    }  
    
  //根据byte数组，生成文�?
    public static void byteToFile(byte[] bfile, String filePath) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath); 
//            file = new File(filePath+"\\"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                   e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    } 
    
    /**
     * 解压zip格式的压缩包
     *
     * @param filePath
     *            压缩文件路径
     * @param outPath
     *            输出路径
     * @return 解压成功或失败标�?
     */
    public static Boolean unZip(String filePath, String outPath) {
    	String unzipfile = filePath; // 解压缩的文件�?

    	try {
    		ZipInputStream zin = new ZipInputStream(new FileInputStream(
    				unzipfile));
    		ZipEntry entry;
    		// 创建文件�?
    		while ((entry = zin.getNextEntry()) != null) {
    			if (entry.isDirectory()) {
    				File directory = new File(outPath, entry.getName());
    				if (!directory.exists()) {
    					if (!directory.mkdirs()) {
    						System.exit(0);
    						}
    					}
    				zin.closeEntry();
    				} else {
    					File myFile = new File(entry.getName());
    					FileOutputStream fout = new FileOutputStream(outPath
    							+ "\\" + myFile.getPath());
    					DataOutputStream dout = new DataOutputStream(fout);
    					byte[] b = new byte[1024];
    					int len = 0;
    					while ((len = zin.read(b)) != -1) {
    						dout.write(b, 0, len);
    					}
    					dout.close();
    					fout.close();
    					zin.closeEntry();
    				}
    			}
    		return true;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    
  //删除指定文件夹下�?��文件
  //param path 文件夹完整绝对路�?
     public static boolean delAllFile(String path) {
         boolean flag = false;
         
         File file = new File(path);
         if (!file.exists()) {
        	 return flag;
         }
         if (!file.isDirectory()) {
        	 return flag;
         }
         String[] tempList = file.list();
         File temp = null;
         for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
            	temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
               temp.delete();
            }
            if (temp.isDirectory()) {
               delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文�?
               delFolder(path + "/" + tempList[i]);//再删除空文件�?
               flag = true;
            }
         }
         return flag;
       }
     
  //删除文件�?
  //param folderPath 文件夹完整绝对路�?

       public static void delFolder(String folderPath) {
       try {
          delAllFile(folderPath); //删除完里面所有内�?
          String filePath = folderPath;
          filePath = filePath.toString();
          java.io.File myFilePath = new java.io.File(filePath);
          myFilePath.delete(); //删除空文件夹
       } catch (Exception e) {
         e.printStackTrace(); 
       }
  }
}
