package com.api.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class rpcResult <T> {
	
	T resultbody;
	String message;
	
	public rpcResult(T resultbody, String message) {
		super();
		this.resultbody = resultbody;
		this.message = message;
	}
	
	
	public <T> rpcResult() {
		super();
	}
	public void setResultBody(T body){
		
		this.resultbody=body;
		
	}
	public void setMessage(String message){
		this.message=message;
	}
	
	public T getResultBody(){
		
		return resultbody;
		
	}
	public String getMessage(){
		return message;
	}

}
