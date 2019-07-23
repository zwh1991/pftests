package com.tools.util;

import org.apache.commons.httpclient.Header;

public class HttpResponds {
	Header[] headerList;
	String body;
	int Code;
	public void setHeaderList(Header[] hd){
		this.headerList=hd;
	}
	public Header[] getHeaderList(){
		return headerList;
	}
	public void setBody(String hd){
		this.body=hd;
	}
	public String getbody(){
		return body;
	}
	public void setCode(int code){
		this.Code=code;
	}
	public int getCode(){
		return Code;
	}

}
