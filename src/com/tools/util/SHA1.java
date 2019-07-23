package com.tools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * . MD5
 * @author kai.xu
 */
public final class SHA1 {
    /** . 方法构建 . */
    private SHA1() {
    }

    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messageDigest = null;
    static {
        try {

            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            // System.err.println(md5.class.getName()+"��ʼ��ʧ�ܣ�");
            e.printStackTrace();
        }
    }

    /**
     * . 字符串加密
     * 
     * @param a
     *            加密内容
     * @return String
     * @throws IOException
     *             if has error
     */
    public static String encode(final String a) throws IOException {
        byte[] source = a.getBytes("utf-8"); // ͳһʹ��UTF-8����
        messageDigest.update(source);
        byte[] tmp = messageDigest.digest();
        return bufferToHex(tmp);

    }

    /**
     * . 文件加密
     * 
     * @param file
     *            文件
     * @return String
     * @throws IOException
     *             if has error
     */
    public static String encode(final File file) throws IOException {
        in = new FileInputStream(file);
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
     * . 转化为HEX类型
     * 
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

    /**
     * . 测试主函数
     * 
     * @param s
     *            内容
     * @throws IOException
     *             if has error
     */
    public static void main(final String[] s) throws IOException {
        System.out.println(SHA1.encode("ABCD"));
    }

    private static String request;
    private static FileInputStream in;

    /**
     * . 请求
     * 
     * @param params
     *            请求内容
     * @param secret
     *            加密
     * @return String
     * @throws Exception
     *             if has error
     */
    public static String getByRequest(final Map<String, Object> params,
            final String secret) throws Exception {
        request = "";
        String sig = null;
        sortMap(params);
        sig = SHA1.encode(secret + request);
        // System.out.println(secret + request);
        // System.out.println(sig);

        return sig;
    }

    /**
     * . 类型排序
     * @param params
     */
    private static void sortMap(final Map<String, Object> params) {
        /**
         * . map排序
         * @author cln8596
         * @param <M>
         *            String
         * @param <N>
         *            Object
         */
        class MyMap<M, N> {
            private M key;
            private Object value;

            /**
             * . 获取 M key
             * @return M
             */
            private M getKey() {
                return key;
            }

            /**
             * . 设置key
             * @param key1
             *            内容
             */
            private void setKey(final M key1) {
                this.key = key1;
            }

            /**
             * . 获取 Object
             * @return Object
             */
            private Object getValue() {
                return value;
            }

            /**
             * . 设置Object
             * @param object
             *            内容
             */
            private void setValue(final Object object) {
                this.value = object;
            }
        }
        List<MyMap<String, Object>> list = new ArrayList<MyMap<String, Object>>();
        for (Iterator<String> i = params.keySet().iterator(); i.hasNext();) {
            MyMap<String, Object> my = new MyMap<String, Object>();
            String key = i.next();
            my.setKey(key);
            my.setValue(params.get(key));
            list.add(my);
        }

        Collections.sort(list, new Comparator<MyMap<String, Object>>() {
            public int compare(final MyMap<String, Object> o1,
                   final MyMap<String, Object> o2) {

                return o1.getKey().compareTo(o2.getKey());
            }
        });

        for (int i = 0, k = list.size(); i < k; i++) {
            Object value;
            if (list.get(i).getValue() == null) {
                value = "";
            } else {
                value = list.get(i).getValue();
            }
            request = request + "&" + list.get(i).getKey() + "=" + value;
        }
    }

}
