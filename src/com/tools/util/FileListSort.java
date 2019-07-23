/**-----------------------------------------------------------------------
 * 
 * Copyright 2014, ArcSoft Inc.
 * All rights reserved.
 *
 * This file is ArcSoft's property. It contains ArcSoft's trade secret,
 * proprietary and confidential information.
 *
 * The information and code contained in this file is only for authorized
 * ArcSoft employees to design, create, modify, or review.
 *
 * DO NOT DISTRIBUTE, DO NOT DUPLICATE OR TRANSMIT IN ANY FORM WITHOUT
 * PROPER AUTHORIZATION.
 *
 * If you are not an intended recipient of this file, you must not copy,
 * distribute, modify,or take any action in reliance on it.
 *
 * If you have received this file in error, please immediately notify
 * ArcSoft and permanently delete the original and any copy of any file
 * and any printout thereof.
 *
 *------------------------------------------------------------------------*/

package com.tools.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileListSort {

	public static void sort(String[] fileList){
		final String begin = "1280_720_";
//		for (int i=0; i<fileList.length;i++){
//			System.err.println(fileList[i]);
//		}
		
		Arrays.sort(fileList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                long n1 = -1;
                long n2 = -1;
                int n11 = -1;
                int n21 = -1;

                String s1 = o1.substring(o1.indexOf(begin) + begin.length(), o1.indexOf(begin) + begin.length() + 13);
                n1 = Long.parseLong(s1);
                String s2 = o2.substring(o2.indexOf(begin) + begin.length(), o2.indexOf(begin) + begin.length() + 13);
                n2 = Long.parseLong(s2);
                String s11 = o1.substring(o1.lastIndexOf("_") + 1, o1.lastIndexOf("."));
                n11 = Integer.parseInt(s11);
                String s21 = o2.substring(o2.lastIndexOf("_") + 1, o2.lastIndexOf("."));
                n21 = Integer.parseInt(s21);
                if(n1 > n2){
                	return 1;
                }else if ( n1 == n2){
                	if(n11 > n21){
                    	return 1;
                    }else if ( n11 == n21){
                    	return 0;
                    }else{
                    	return -1;
                    }
                }else{
                	return -1;
                }
            }
        });
	}
	
	public static void main(String[] args) {
		String[] fileList = {
				"E:\\Share\\20141204\\1m_gaodu_3m_juli\\lvyang\\lvyang(1)\\1280_720_1417701652396_3_68.i420",
				"E:\\Share\\20141204\\1m_gaodu_3m_juli\\lvyang\\lvyang(1)\\1280_720_1417701652396_3_61.i420",
				"E:\\Share\\20141204\\1m_gaodu_3m_juli\\lvyang\\lvyang(1)\\1280_720_1417701652396_3_57.i420",
				"E:\\Share\\20141204\\1m_gaodu_3m_juli\\lvyang\\lvyang(1)\\1280_720_1417701652396_3_69.i420",
		};
		sort(fileList);
	}
}
