package com.tools.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.Iterator;

import com.alibaba.fastjson.JSONObject;
/**.
 * 加密和解密
 * @author kai.xu
 *
 */
public final class UrlEncode {
    /**. 构建方法*/
    private UrlEncode() {
    }
    /**
     * . 加密
     * @param jo
     *            内容
     * @return JSONObject
     */
    public static JSONObject encode(final JSONObject jo) {
        // urlencoder
        Iterator<String> it = jo.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            try {
                jo.put(key.toString(), URLEncoder.encode(
                        jo.getString(key.toString()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return jo;
    }

    /**
     * . 解码
     * @param jo
     *            内容
     * @return JSONObject
     */
    public static JSONObject decode(final JSONObject jo) {
        // urlencoder
        Iterator<String> it = jo.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            try {
                jo.put(key.toString(), URLDecoder.decode(
                        jo.getString(key.toString()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return jo;
    }

}
