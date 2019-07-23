package com.web.execution;

import org.apache.commons.httpclient.Header;

public class HttpResponds {
	Header[] headerList;
	String body;
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

}
