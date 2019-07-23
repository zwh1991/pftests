package com.tools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * . MD5检查
 * @author cln8596
 */
public final class MD5Check {
    /** . 构造方法 */
    private MD5Check() {
    }

    /**
     * . 加密文件
     * @param strFilePath
     *            文件路径
     * @return String
     */
    public static String encodeFile(final String strFilePath) {
        File file = new File(strFilePath);
        if (!file.exists() || !file.canRead()) {
            return null;
        }

        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        int iSize = 0;
        try {
            iSize = is.available();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        byte[] buffer = new byte[iSize];
        try {
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return encode(buffer);
    }

    /**
     * . 加密源字符串
     * @param sourceString
     *            源字符串
     * @return String
     */
    public static String encode(final String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * . 加密二进制数
     * @param bytes
     *            二进制数
     * @return String
     */
    public static String encode(final byte[] bytes) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(bytes));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * . 加密二进制数成hex类型
     * @param bytes
     *            二进制数
     * @return String
     */
    public static String byte2hexString(final byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

}
