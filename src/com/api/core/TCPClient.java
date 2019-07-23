//package api;
//
//import java.io.*;
//import java.net.*;
//
//import com.melot.module.kkrpc.core.api.RpcHeader;
//import com.melot.module.kkrpc.core.codec.SerializationUtils;
//
//public class TCPClient{
//	public InputStream in;
//	public OutputStream out;
//    public void TCPConnect(String ip,int port) throws IOException{
//        Socket client = new Socket(ip , port);
//        in = client.getInputStream();
//        out = client.getOutputStream();
//        
//
//    }
//    
//    public String SendResMsg(String msg) throws IOException, InterruptedException{
//    	byte[] msgByte;
//    	byte[] header = RpcHeader.encode(2, RpcHeader.DEF_VER, msg.length(), 0);
//    	byte[] payloadByte = com.melot.module.kkrpc.core.codec.SerializationUtils.serializeFastjson(msg);
//    	//msgByte=bytelink(header,payloadByte);
//    	out.write(header);
//    	out.write(payloadByte);
//
//    	BufferedReader br = new BufferedReader(new InputStreamReader(in));  
//    	for(int i=0;i<10;i++){
//    		String result=br.readLine();
//    		if(result==null){
//    			Thread.sleep(1000);
//    		}
//    		else{
//    			return result;
//    		}
//    		
//    	}
//    	return "nothing back";
//    	
//    }
//    
//    public static byte[] bytelink(byte[] Head,byte[] Body){
//    	  byte[] message=new byte[Head.length+Body.length];
//    	  for (int i = 0; i < message.length; i++) {
//    	   if(i<Head.length){
//    	    message[i]=Head[i];
//    	   }else{
//    	    message[i]=Body[i-Head.length];
//    	   }
//    	  }
//    	  return message;
//    }
//}

package com.api.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.melot.module.kkrpc.core.api.RpcHeader;
import com.melot.module.kkrpc.core.api.RpcRequest;
import com.melot.module.kkrpc.core.api.RpcResult;
import com.melot.module.kkrpc.core.codec.SerializationUtils;
import com.melot.room.consumption.domain.DealResult;
import com.melot.room.consumption.domain.ReturnResult;

public class TCPClient {
	private Socket client;
	private InputStream in;
	private OutputStream out;
    public void TCPConnect(String ip,int port) throws IOException{
        client = new Socket(ip , port);
        in = client.getInputStream();
        out = client.getOutputStream();
    }
    
    public String SendResMsg(RpcRequest req) throws IOException, InterruptedException{
    	System.out.println("Send rpc request: " + req);
    	byte[] payloadBuf = SerializationUtils.serializeFastjson(req);
    	byte[] header = RpcHeader.encode(2, RpcHeader.DEF_VER, payloadBuf.length, 0);
    	
    	out.write(header);
    	out.write(payloadBuf);

    	byte[] replyHeader = new byte[12];
    	if(-1 == in.read(replyHeader))
    	{
    		System.out.println("read date failed: maybe connection closed.");
    		return "";
    	}
    	
    	int payloadLen = RpcHeader.getPayloadLength(replyHeader);
    	byte[] replyPayload = new byte[payloadLen];
    	int offset = 0;
    	while(offset < payloadLen)
    	{
    		int iRead = in.read(replyPayload, offset, (payloadLen - offset));
			if (iRead == -1)
			{
				System.out.println("read date failed: maybe connection closed.");
				return "";
			}
			offset += iRead;
    	}
    	RpcResult ret = SerializationUtils.deserializeFastjson(replyPayload, RpcResult.class);
    	
    	return ret.toString();
    }
    //序列化用fastjson
    public RpcResult SendResMsgRpc(RpcRequest req) throws IOException, InterruptedException{
    	System.out.println("Send rpc request: " + req);
    	byte[] payloadBuf = SerializationUtils.serializeFastjson(req);
    	//byte[] payloadBuf = SerializationUtils.serializeHessian(req);
    	byte[] header = RpcHeader.encode(2, RpcHeader.DEF_VER, payloadBuf.length, 0);
    	//byte[] header = RpcHeader.encode(1, RpcHeader.DEF_VER, payloadBuf.length, 0);
    	
    	out.write(header);
    	out.write(payloadBuf);

    	byte[] replyHeader = new byte[12];
    	if(-1 == in.read(replyHeader))
    	{
    		System.out.println("read date failed: maybe connection closed.");
    		return null;
    	}
    	
    	int payloadLen = RpcHeader.getPayloadLength(replyHeader);
    	byte[] replyPayload = new byte[payloadLen];
    	int offset = 0;
    	while(offset < payloadLen)
    	{
    		int iRead = in.read(replyPayload, offset, (payloadLen - offset));
			if (iRead == -1)
			{
				System.out.println("read date failed: maybe connection closed.");
				return null;
			}
			offset += iRead;
    	}
    	RpcResult ret = SerializationUtils.deserializeFastjson(replyPayload, RpcResult.class);
  //  	RpcResult ret = SerializationUtils.deserializeHessian(replyPayload, RpcResult.class);
    	System.out.println(ret.getValue());
    	
    	return ret;
    }
    //序列化用hessian
    public RpcResult SendResMsgRpcHessian(RpcRequest req) throws IOException, InterruptedException{
    	System.out.println("Send rpc request: " + req);
    	//byte[] payloadBuf = SerializationUtils.serializeFastjson(req);
    	byte[] payloadBuf = SerializationUtils.serializeHessian(req);
    	//byte[] header = RpcHeader.encode(2, RpcHeader.DEF_VER, payloadBuf.length, 0);
    	byte[] header = RpcHeader.encode(1, RpcHeader.DEF_VER, payloadBuf.length, 0);
    	
    	out.write(header);
    	out.write(payloadBuf);

    	byte[] replyHeader = new byte[12];
    	if(-1 == in.read(replyHeader))
    	{
    		System.out.println("read date failed: maybe connection closed.");
    		return null;
    	}
    	
    	int payloadLen = RpcHeader.getPayloadLength(replyHeader);
    	byte[] replyPayload = new byte[payloadLen];
    	int offset = 0;
    	while(offset < payloadLen)
    	{
    		int iRead = in.read(replyPayload, offset, (payloadLen - offset));
			if (iRead == -1)
			{
				System.out.println("read date failed: maybe connection closed.");
				return null;
			}
			offset += iRead;
    	}
 //   	RpcResult ret = SerializationUtils.deserializeFastjson(replyPayload, RpcResult.class);
    	RpcResult ret = SerializationUtils.deserializeHessian(replyPayload, RpcResult.class);
    	System.out.println(ret.getValue());
    	
    	return ret;
    }
    

 
    
}
