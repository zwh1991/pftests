package com.tools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.tools.util.MD5;

/**
 * . MD5加密
 * @author cln8596
 */
public final class MD5 {
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messageDigest = null;
    static {
        try {

            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println(MD5.class.getName() + "");
            e.printStackTrace();
        }
    }

    /** . MD5 */
    private MD5() {
    }

    /**
     * . 字符串加�?
     * @param a
     *            加密内容
     * @return String
     * @throws IOException
     *             if has error
     */
    public static String encode(final String a) throws IOException {
        byte[] source = a.getBytes("utf-8"); // utf-8
        messageDigest.update(source);
        byte[] tmp = messageDigest.digest();
        return bufferToHex(tmp);

    }

    /**
     * . 部分字符串转化为hex类型
     * @param a
     *            源字符串
     * @param num
     *            确定转化hex类型的长�?
     * @return String
     * @throws IOException
     *             if has error
     */
    public static String encodeSub(final String a, final int num)
            throws IOException {
        byte[] source = a.getBytes("utf-8"); // utf-8
        messageDigest.update(source);
        byte[] tmp = messageDigest.digest();
        return bufferToHex(tmp).substring(0, num);

    }

    /**
     * . 加密文件
     * @param file
     *            文件
     * @return String
     * @throws IOException
     *             if has error
     */
    public static String encode(final File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                file.length());
        messageDigest.update(byteBuffer);
        byte[] tmp = messageDigest.digest();
        // System.out.println(bufferToHex(tmp));
        // System.out.println(s);
        return bufferToHex(tmp);
    }

    /**
     * . 二进制转化为hex类型
     * @param bytes
     *            二进制数
     * @return String
     */
    private static String bufferToHex(final byte[] bytes) {
        char[] str = new char[bytes.length * 2];
        int k = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte byte0 = bytes[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
