package com.tools.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StrConvert {
    public static List<String> convertStrToList(String str,String symbol){
    	List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str,symbol);
        while(st.hasMoreTokens()){
        	list.add(st.nextToken());
        }
        return list;
    }
}
