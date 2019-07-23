package com.tools.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * . 正则表达式
 * @author kaixu
 */
public final class RegexUtil {
    /** . 构建方法 */
    private RegexUtil() {
    }

    /**
     * .
     * @param sourceString
     *            源字符串
     * @param regexString
     *            正则字符
     * @param replaceString
     *            替换字符串
     * @return String
     */
    public static String replaceString(final String sourceString,
            final String regexString, final String replaceString) {
        Pattern p = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sourceString);
        String result = m.replaceAll(replaceString);
        return result;
    }
    /**.
     * 证实
     * @param sourceString 源字符串
     * @param regexString 证实字符串
     * @return boolean
     */
    public static boolean validateSting(final String sourceString,
            final String regexString) {
        Pattern p = Pattern.compile(regexString);
        Matcher m = p.matcher(sourceString);
        return m.matches();
    }

    /**
     * @param sourceString 源字符串
     * @param regexString 证实字符串
     * @return List<String>
     */
    public static List<String> getString(final String sourceString,
            final String regexString) {
        List<String> result = null;
        try {
            Pattern p = Pattern.compile(regexString);
            Matcher m = p.matcher(sourceString);
            result = new ArrayList<String>();
            while (m.find()) {
                result.add(m.group());
            }
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
