package com.tools.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tools.util.MD5;

/**
 * . 签名
 * @author cln8596
 */
public final class Signature {
    private static String request;

    /** . 默认函数构�? */
    private Signature() {
    }

    /**
     * . MD5编码
     * @param params
     *            内容
     * @param secret
     *            密钥
     * @return String
     * @throws Exception
     *             if has error
     */
    public static String getByRequest(final Map<String, Object> params,
            final String secret) throws Exception {
        request = "";
        String sig = null;
        sortMap(params);
        sig = MD5.encode(secret + request);
        // System.out.println(secret + request);
        // System.out.println(sig);

        return sig;
    }

    /**
     * . 类型排序
     * @param params
     *            传�?数据
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
             * . 获取 Object的�?
             * @return Object
             */
            private Object getValue() {
                return value;
            }

            /**
             * . 设置Object的�?
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
