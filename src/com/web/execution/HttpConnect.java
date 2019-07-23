package com.web.execution;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import android.util.Log;

import com.api.core.Config;
import com.api.core.Core;
import com.alibaba.fastjson.JSONObject;
import com.tools.util.Tools;

/**
 * . 网络连接
 * @author 
 */
public class HttpConnect extends TestCase {
	 /** the network out time.*/
    private static int outtime = 60000;
    /** the network out time.*/
    private static int settime = 120000;
    static {
        PropertyConfigurator.configure("Log4j.properties");
    }
    /** . logger */
    private static Logger logger = Logger
            .getLogger(HttpConnect.class.getName());
    /**
     * . if do server check ,it need to wait visit server
     */
    private static boolean bwait = false;
    /** . 等待时间 */
    private static int waittime;
    /** . mProxy */
    private static Proxy mProxy = null;
    /** . bProxy */
    private static boolean bProxy = false;
    
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");

//    public static byte[] doDelete(String url, JSONObject param) {
//
//    	InputStream in;
//    	byte[] bre = null;
//    	HttpResponse response;
//
//    	HttpDelete delete = new HttpDelete(url);
//
//    	if(param.containsKey("accesstoken")){
//    		delete.setHeader("accessToken", param.getString("accesstoken"));
//        }
//        if(param.containsKey("userid")){
//        	delete.setHeader("userid", param.getString("userid"));
//        }
//        if(param.containsKey("cameraid")){
//        	delete.setHeader("cameraid", param.getString("cameraid"));
//        }
//        if(param.containsKey("personid")){
//        	delete.setHeader("personid", param.getString("personid"));
//        }
//    	
//    	try {
//    		response = new DefaultHttpClient().execute(delete);
//    		
//    		if (response != null){
//    			int statusCode = response.getStatusLine().getStatusCode();
//    			
//    			if (statusCode == 200 || statusCode == 403) {
//    				in = response.getEntity().getContent();
//    	        	if (in != null) {
//    	        		bre = ResourceUtil.readStream(in);
//    	        	}
//    			}
//    	    }
//    	}catch (IOException e) {
//    		e.printStackTrace();
//    	}
//
//    	return bre;
//    }
    
    /**
     * . 获取http连接
     * @param testUrl
     *            �?��连接的URL
     * @return 类HttpURLConnection的实�?
     */
    public static HttpURLConnection getUrlConnect(final String testUrl) {
        final int outtime = 60000;
        final int readtime = 120000;
        HttpURLConnection urlconn = null;
        try {
            if (bProxy) {
                urlconn = (HttpURLConnection) (new URL(validUrl(testUrl.trim()))
                        .openConnection(mProxy));
            } else {
                urlconn = (HttpURLConnection) (new URL(validUrl(testUrl.trim()))
                        .openConnection());
            }

        } catch (MalformedURLException e1) {
            logger.error(testUrl);
            logger.error(e1.getMessage());
        } catch (IOException e1) {
            logger.error(testUrl);
            logger.error(e1.getMessage());
        }
        if (urlconn == null) {
            return null;
        }
        urlconn.setConnectTimeout(outtime);
        urlconn.setReadTimeout(readtime);
        try {
            urlconn.setRequestMethod("POST");
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        }
        urlconn.setDoOutput(true);
        urlconn.setDoInput(true);
        urlconn.setUseCaches(false);
        urlconn.setDefaultUseCaches(false);
        urlconn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        urlconn.setRequestProperty("user-agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; "
                        + "rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13");

        return urlconn;
    }

//    public static String sendGet(final String url, final String content) {
//		// Waiting the server response.
//		if (bwait) {
//			Tools.sleep(waittime);
//		}
//		System.out.println("WebUrl: " + url);
//		String temp = "";
//		HttpClient client = new HttpClient();
//		client.getHttpConnectionManager().getParams()
//				.setConnectionTimeout(5000);
////		client.getHttpConnectionManager().getParams().setSoTimeout(CONNECT_OUT);
//		GetMethod getMethod = new GetMethod(url);
//		try {
//			client.executeMethod(getMethod);
//			ByteArrayOutputStream bout = new ByteArrayOutputStream();
//			byte[] rsp = null;
//			try {
//				final int read = 512;
//				InputStream in = getMethod.getResponseBodyAsStream();
//				byte[] buf = new byte[read];
//				int count = 0;
//				while ((count = in.read(buf)) > 0) {
//					bout.write(buf, 0, count);
//				}
//				rsp = bout.toByteArray();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			} finally {
//				if (bout != null) {
//					bout.close();
//				}
//			}
//			assertEquals("200", getMethod.getStatusCode());
//			if (rsp == null || rsp.length <= 0) {
//				return "";
//			}
//			temp = new String(rsp, "UTF-8");
//			temp = URLDecoder.decode(temp, "UTF-8");
//			System.out.println(Tools.getLineInfo() + ":" + temp);
//		} catch (HttpException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return temp;
//	}
    
    public static Map<String, Object> sendGet(String url,
    		String Content, String setCookie) {
		System.out.println("GET URL:"+url);
		Map<String, Object> map=new HashMap<String, Object>();
		String temp = "";
		url=url+"?"+Content;

		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		
		if (!url.equals(Core.inspLoginUrl)){
			getMethod.setRequestHeader("Cookie", setCookie);
		}	
		
		try {

			client.executeMethod(getMethod);

			if (url.equals(Core.inspLoginUrl+"?"+Content)){
				// 获取所有响应头字段
				Header[] headrs = getMethod.getResponseHeaders();
				String SetCookie = "";
	            // 遍历所有的响应头字段
	            for (int i=0; i<headrs.length; i++) {
	            	if (headrs[i].getName().equals("Set-Cookie")){
	            		map.put("SetCookie", headrs[i].getValue());
	            		//SetCookie = headrs[i].getValue();
	            		break;
	            	}
	            }
			}
            
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] rsp = null;
			try {
				InputStream in = getMethod.getResponseBodyAsStream();
				byte[] buf = new byte[512];
				int count = 0;
				while ((count = in.read(buf)) > 0) {
					bout.write(buf, 0, count);
				}
				rsp = bout.toByteArray();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (bout != null)
					bout.close();
			}
			assertEquals(200, getMethod.getStatusCode());
			if (rsp == null || rsp.length <= 0) {
				return null;
			}
			temp = new String(rsp, "UTF-8");
			map.put("res", temp);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print temp string
		// System.out.println(temp);
		//response = JSONObject.parseObject(temp);
		return map;
	}
    
    public static String sendGet(String url, String Content) {
		System.out.println("GET URL:"+url);
		
		String temp = "";
		url=url+"?"+Content;
		//JSONObject  response = null ;
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);

		try {

			client.executeMethod(getMethod);
            
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] rsp = null;
			try {
				InputStream in = getMethod.getResponseBodyAsStream();
				byte[] buf = new byte[512];
				int count = 0;
				while ((count = in.read(buf)) > 0) {
					bout.write(buf, 0, count);
				}
				rsp = bout.toByteArray();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (bout != null)
					bout.close();
			}
			assertEquals(200, getMethod.getStatusCode());
			if (rsp == null || rsp.length <= 0) {
				return null;
			}
			temp = new String(rsp, "UTF-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print temp string
		// System.out.println(temp);
		//response = JSONObject.parseObject(temp);
		return temp;
	}
    
    public static String sendDelete(String url, JSONObject param) {
    	System.out.println("POST路径: " + url);
    	System.out.println("param: " + param);
    	
        String result = "";
        HttpClient httpClient = new HttpClient();
        DeleteMethod deleteMethod = new DeleteMethod(url);
        try {
        	
        	// 设置通用的请求属�?
            if(param.containsKey("accesstoken")){
            	deleteMethod.setRequestHeader("accessToken", param.getString("accesstoken"));
            }
            if(param.containsKey("userid")){
            	deleteMethod.setRequestHeader("userid", param.getString("userid"));
            }
            if(param.containsKey("cameraid")){
            	deleteMethod.setRequestHeader("cameraid", param.getString("cameraid"));
            }
            if(param.containsKey("personid")){
            	deleteMethod.setRequestHeader("personid", param.getString("personid"));
            }

            httpClient.executeMethod(deleteMethod);
            result = resultmanage(deleteMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	deleteMethod.releaseConnection();
        }
        return result;
    }
    
    public static String sendGet(String url, JSONObject content) {		
		String temp = "";
		 
		ArrayList<String> list = new ArrayList<String>(content.keySet());
		for (int i=0; i<list.size(); i++){
			url = url + "&" + list.get(i) + "=" + content.getString(list.get(i));
		}
		System.err.println("GET URL:"+url);
		
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);

		try {
			client.executeMethod(getMethod);            
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] rsp = null;
			try {
				InputStream in = getMethod.getResponseBodyAsStream();
				byte[] buf = new byte[512];
				int count = 0;
				while ((count = in.read(buf)) > 0) {
					bout.write(buf, 0, count);
				}
				rsp = bout.toByteArray();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (bout != null)
					bout.close();
			}
			assertEquals(200, getMethod.getStatusCode());
			if (rsp == null || rsp.length <= 0) {
				return null;
			}
			temp = new String(rsp, "UTF-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print temp string
		// System.out.println(temp);
		//response = JSONObject.parseObject(temp);
		return temp;
	}
    
    /**
     * . 设置代理网络
     * @param host
     *            主机
     * @param port
     *            端口
     */
    public static void setProxy(final String host, final int port) {
        bProxy = true;
        SocketAddress pAddress = new InetSocketAddress("172.17.1.121", port);
        mProxy = new Proxy(Proxy.Type.HTTP, pAddress);

    }

    /**
     * . FD接口测试
     * @param url
     *            接口连接
     * @param content
     *            内容
     * @param token
     *            accesstoken
     * @return String
     */
    public static String sendPost(final String url, final byte[] content,
            final String token) {
        System.out.println("POST路径: " + url);
        String result = "";
        String bounday = "-----------------------------7db372eb000e2";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        RequestEntity requestEntity;
        try {
            System.out.println(content);
            requestEntity = new ByteArrayRequestEntity(content,
                    "multipart/form-data;boundary=" + bounday + "");

            postMethod.setRequestEntity(requestEntity);
            if (token != null && token.length() != 0) {
                postMethod.setRequestHeader("accesstoken", token);
            }
            System.out.println(postMethod.getRequestEntity().toString());
            httpClient.executeMethod(postMethod);
            result = resultmanage(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    
    /**
     * . FD接口测试2
     * @param url
     *            接口连接
     * @param content
     *            内容
     * @param token
     *            accesstoken
     * @return String
     */
    public static String sendPost1(final String url, final byte[] content,
            final String token,final String Ordinal) {
        System.out.println("POST路径: " + url);
        String result = "";
        HttpClient httpClient = new HttpClient();
        HttpConnectionManagerParams managerParams = httpClient
                .getHttpConnectionManager().getParams();
        managerParams.setConnectionTimeout(outtime);
        managerParams.setSoTimeout(settime);
        PostMethod postMethod = new PostMethod(url);
        RequestEntity requestEntity;
        try {
            System.out.println(content);
            requestEntity = new ByteArrayRequestEntity(content,
                    "image/jpeg; charset=UTF-8");

            postMethod.setRequestEntity(requestEntity);
            if (token != null && token.length() != 0) {
                postMethod.setRequestHeader("accesstoken", token);
            }
			if (Ordinal != null && Ordinal.length() != 0) {
				postMethod.setRequestHeader("Ordinal", Ordinal);
			}
            httpClient.executeMethod(postMethod);
            result = resultmanage(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }
    
    public static String sendPostF1(final String url, final byte[] content,
			final JSONObject param) {
        System.out.println("POST路径: " + url);
        String result = "";
        HttpClient httpClient = new HttpClient();
        HttpConnectionManagerParams managerParams = httpClient
                .getHttpConnectionManager().getParams();
        managerParams.setConnectionTimeout(outtime);
        managerParams.setSoTimeout(settime);
        PostMethod postMethod = new PostMethod(url);
        RequestEntity requestEntity;
        try {
            System.out.println(content);
            requestEntity = new ByteArrayRequestEntity(content,
                    "image/jpeg; charset=UTF-8");

            postMethod.setRequestEntity(requestEntity);
            Set<String> k = param.keySet();
			String key = k.toString().replace("[", "").replace("]", "");
			String[] key1 = key.split(",");
			System.out.println(key1.length);
			for (int i = 0; i < key1.length; i++) {
				System.out.println(key1[i].trim());
				postMethod.setRequestHeader(key1[i].trim(),
						param.getString(key1[i].trim()));
			}
            httpClient.executeMethod(postMethod);
            result = resultmanage(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }
    /**
     * facerecognize/faceregister.
     * @param url
     * @param content
     * @param param
     * @return
     */
    public static String sendPostF(final String url, final byte[] content,
			final JSONObject param) {
		System.out.println("POST路径: " + url);
		String result = "";
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		managerParams.setConnectionTimeout(outtime);
		managerParams.setSoTimeout(settime);
		PostMethod postMethod = new PostMethod(url);
		try {

			String randStr = "----WebKitFormBoundary9ksxPC3P7yBHaBBt";
			String postBody = "--"
					+ randStr
					+ "\r\n"
					+ "Content-Disposition: form-data; name=\"file\"; filename=\"file\"\r\n"
					+ "Content-Type: application/octet-stream; charset=UTF-8\r\n\r\n";
			postBody += content;
			postBody += "\r\n--" + randStr + "--\r\n";
			String contentType = "multipart/form-data; boundary=" + randStr;
			postMethod.setRequestHeader("Content-Type", contentType);
			postMethod.setRequestBody(postBody);
			Set<String> k = param.keySet();
			String key = k.toString().replace("[", "").replace("]", "");
			String[] key1 = key.split(",");
			System.out.println(key1.length);
			for (int i = 0; i < key1.length; i++) {
				System.out.println(key1[i].trim());
				postMethod.setRequestHeader(key1[i].trim(),
						param.getString(key1[i].trim()));
			}
			httpClient.executeMethod(postMethod);
			result = resultmanage(postMethod);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}

    public static String sendPostF2(final String url,
            final JSONObject params,
			final List<byte[]> data) throws Exception {
		System.out.println("WebUrl:" + url);
		URL uri = new URL(url);
		String dataname = "file";
		final int truecode = 200;
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		String responseData = null;
		String boundary = java.util.UUID.randomUUID().toString();
		String prefix = "--", linend = "\r\n";
		String multipart = "multipart/form-data";
		String encode = "UTF-8";
		conn.setConnectTimeout(outtime);
		conn.setReadTimeout(outtime); // 缓存的最长时�?
		conn.setDoInput(true); // 允许输入
		conn.setDoOutput(true); // 允许输出
		conn.setUseCaches(false); // 不允许使用缓�?
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", multipart + ";boundary="
				+ boundary);
		// 首先组拼文本类型的参�?
		StringBuilder sb = new StringBuilder();
		Iterator<String> paramkey = params.keySet().iterator();
		for (int i = 0; i < params.size(); i++) {
			String key = paramkey.next();
			sb.append(prefix);
			sb.append(boundary);
			sb.append(linend);
			sb.append("Content-Disposition: form-data; name=\"" + key + "\""
					+ linend);
			sb.append("Content-Type: text/plain; charset=" + encode + linend);
			sb.append("Content-Transfer-Encoding: 8bit" + linend);
			sb.append(linend);
			sb.append(params.getString(key));
			sb.append(linend);
		}
		// sb.append(content);
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		// 发�?文件数据
		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(prefix);
				sb1.append(boundary);
				sb1.append(linend);
				// name是post中传参的�?filename是文件的名称
				sb1.append("Content-Disposition: form-data; name=\"" + dataname
						+ "\"; filename=\"" + dataname + "\"" + linend);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ encode + linend);
				sb1.append(linend);
				outStream.write(sb1.toString().getBytes());
				if (data.get(i) != null) {
					outStream.write(data.get(i));
				}
				outStream.write(linend.getBytes());
			}
		}
		// 请求结束标志
		byte[] enddata = (prefix + boundary + prefix + linend).getBytes();
		outStream.write(enddata);
		outStream.flush();
		// 得到响应�?
		int res = conn.getResponseCode();
		if (res == truecode) {
			in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
			responseData = sb2.toString();
		} else {
			responseData = "{rc:" + res + "}";
		}
		outStream.close();
		outStream = null;
		conn.disconnect();
		responseData = URLDecoder.decode(responseData, "UTF-8");
		System.out.println(Tools.getLineInfo() + ":" + responseData);
		return responseData;
	}
    /**
     * . put请求
     * @param url
     *            连接
     * @param content
     *            内容
     * @param accesstoken
     *            token
     * @param type
     *            type
     * @return String
     */
    public static String sendPut(final String url, final String content,
            final String accesstoken, final String type) {
        System.out.println("POST路径: " + url);
        String result = "";
        HttpClient httpClient = new HttpClient();
        PutMethod putMethod = new PutMethod(url);
        RequestEntity requestEntity;
        try {
            System.out.println(content);
            requestEntity = new StringRequestEntity(content, type, "utf-8");
            if (accesstoken != null && accesstoken.length() != 0) {
                putMethod.setRequestHeader("accesstoken", accesstoken);
            }
            putMethod.setRequestEntity(requestEntity);
            httpClient.getHttpConnectionManager();
            httpClient.executeMethod(putMethod);
            result = resultmanage(putMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            putMethod.releaseConnection();
        }
        return result;
    }

    /**
     * . post传�?
     * @param url 接口连接
     * @param content 内容
     * @param accesstoken 头文�?
     * @param type 类型
     * @return String
     */
    public static String sendPost(String url, JSONObject params, String type) {
    	String result = "";

        String content = params.toJSONString();
        System.out.println("post: " + content);
        
        HttpClient httpClient = new HttpClient();
//        PostMethod postMethod = new PostMethod(url);
        PostMethod postMethod = new PostMethod(url+"?parameter="+content);

        RequestEntity requestEntity;
        try {
//            System.out.println(content);
            requestEntity = new StringRequestEntity(content, type, "utf-8");
            postMethod.setRequestEntity(requestEntity);
//            System.out.println(postMethod.getResponseBodyAsString());
            httpClient.executeMethod(postMethod);
            result = resultmanage(postMethod);
            System.out.println("reponse: " + result);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }

        return result;
    }
    
    public static Map<String, Object> sendPost(String url, String Content,
    		String setCookie) {
		System.out.println("POST URL:"+url);
		Map<String, Object> map=new HashMap<String, Object>();
		String temp = "";
//		url=url+"?"+Content;
		//JSONObject  response = null ;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(Content);
		
		postMethod.setRequestHeader("Cookie", setCookie);
		
		try {
			client.executeMethod(postMethod);
            
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] rsp = null;
			try {
				InputStream in = postMethod.getResponseBodyAsStream();
				byte[] buf = new byte[512];
				int count = 0;
				while ((count = in.read(buf)) > 0) {
					bout.write(buf, 0, count);
				}
				rsp = bout.toByteArray();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (bout != null)
					bout.close();
			}
			assertEquals(200, postMethod.getStatusCode());
			if (rsp == null || rsp.length <= 0) {
				return null;
			}
			temp = new String(rsp, "UTF-8");
			map.put("res", temp);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print temp string
		// System.out.println(temp);
		//response = JSONObject.parseObject(temp);
		return map;
	}
   
    /**
     * 
     * @param url
     * @param content
     * @param accesstoken
     * @param type
     * @return
     */
    public static String sendPost(final String url, final JSONObject header,
            final JSONObject content, final String type) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        DeleteMethod postMethod = new DeleteMethod(url);
        RequestEntity requestEntity;
		try {
			String keys = header.keySet().toString();
			if (keys.length() > 0) {
//				String key = keys.toString();
				String[] keye = keys.replace("[", "").replace("]", "").split(",");
				
				for (int i = 0; i < keye.length; i++) {
					String key = keye[i].trim();
					postMethod.addRequestHeader(key, header.getString(key));
				}
			}
			// System.out.println(postMethod.getRequestEntity().toString());
			httpClient.executeMethod(postMethod);
			result = resultmanage(postMethod);
		} catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }

        return result;
    }
    
    public static String sendPostUrlParam(String url,String param)
    {
      String result="";
      try{
    	  if (!url.equals(Core.inspPublishUPUrl)){
    		  url = url + "?parameter=" + param;
    	  }    	  
    	  
    	  URL httpurl = new URL(url);
    	  HttpURLConnection httpConn = (HttpURLConnection)httpurl.openConnection();
    	  httpConn.setDoOutput(true);
    	  httpConn.setDoInput(true);
    	  // 请求头添加安全校验字段
    	  httpConn.setRequestProperty("Referer", "kktvwebsecurity");
    	  PrintWriter out = new PrintWriter(httpConn.getOutputStream());
    	  out.print(param);
    	  out.flush();
    	  out.close();
    	  BufferedReader in = 
    			  new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
    	  
    	  String line;
    	  while ((line = in.readLine())!= null){
    		  result += line;
    	  }
    	  in.close();
      }catch(Exception e){
    	  System.out.println("没有结果"+e);
      }
      
      return result;
    }
    
    public static Map<String, Object> sendPostUrlPar(String url,String param,
    		String Cookie)
    {
      String result="";
      Map<String, Object> hashTmp = new HashMap<String, Object>();
      try{  	  
//    	  System.err.println(url);
    	  URL httpurl = new URL(url);
    	  HttpURLConnection httpConn = (HttpURLConnection)httpurl.openConnection();
    	  httpConn.setDoOutput(true);
    	  httpConn.setDoInput(true);
    	  httpConn.setRequestProperty("Cookie", Cookie);
    	  PrintWriter out = new PrintWriter(httpConn.getOutputStream());
    	  out.print(param);
    	  out.flush();
    	  out.close();
    	  BufferedReader in = 
    			  new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
    	  
    	  String line;
    	  while ((line = in.readLine())!= null){
    		  result += line;
    	  }
    	  in.close();
      }catch(Exception e){
    	  System.out.println("没有结果"+e);
      }
      hashTmp.put("res", result);
      
      return hashTmp;
    }

    public static String sendPostXiaoMiPayMent(String url)
    {
      String result="";
      try{
//    	  System.err.println(url);
    	  URL httpurl = new URL(url);
    	  HttpURLConnection httpConn = (HttpURLConnection)httpurl.openConnection();
    	  httpConn.setDoOutput(true);
    	  httpConn.setDoInput(true);
    	  PrintWriter out = new PrintWriter(httpConn.getOutputStream());
//    	  out.print(param);
    	  out.flush();
    	  out.close();
    	  BufferedReader in = 
    			  new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
    	  
    	  String line;
    	  while ((line = in.readLine())!= null){
    		  result += line;
    	  }
    	  in.close();
      }catch(Exception e){
    	  System.out.println("没有结果"+e);
      }
      
      return result;
    }
    
    /**.
     * 上传图片
     * @param url 接口连接
     * @param content 传�?内容
     * @param type 类型
     * @return String
     * @throws Exception if has error
     */
    public static String sendupload(final String url, final JSONObject content,
            final String type) throws Exception {
        String result = "";
        final int sizes = 100;
        String accesstoken = content.getString("accessToken");
        int time = content.getIntValue("times");
        String size = content.getString("size");
        String fileID = content.getString("fileID");
        byte[] contents = Core.encodeBase64File(content.getString("fileupload"));
        System.out.println(contents.length);
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        RequestEntity requestEntity;
        try {
            System.out.println(content);
            requestEntity = new ByteArrayRequestEntity(contents, type);
            postMethod.setRequestEntity(requestEntity);
            if (accesstoken != null && accesstoken.length() != 0) {
                postMethod.setRequestHeader("accesstoken", accesstoken);
            }
            if (fileID != null && fileID.length() != 0) {
                postMethod.setRequestHeader("fileID", fileID);
            }
            if (time == 0) {
                postMethod.setRequestHeader("size", size);
                postMethod.setRequestHeader("offset", "0");
            } else {
                int i = 0;
                for (; i < time; i++) {
                    postMethod.setRequestHeader("size",
                            String.valueOf(sizes * (i + 1)));
                    postMethod.setRequestHeader("offset",
                            String.valueOf(sizes * i));
                }
                postMethod.setRequestHeader("size", size);
                postMethod
                        .setRequestHeader("offset", String.valueOf(sizes * i));
            }
            httpClient.executeMethod(postMethod);
            result = resultmanage(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }

        return result;
    }

    /**
     * for register and authorize .
     * @param webUrl
     *            接口连接
     * @param method
     *            方法
     * @param params
     *            传�?内容
     * @exception Throwable
     *                if has error
     * @return String
     */
    public static String sendRequestSign(final String webUrl,
            final String method, final String params) throws Throwable {
        String temp;
        System.out.println("POST路径: " + webUrl);
        System.out.println("POST内容: " + params);

        String response = sendPost(webUrl, JSONObject.parseObject(params), "application/json");
        temp = response.substring(response.indexOf("{"),
                response.lastIndexOf("}") + 1);
        System.out.println("POST结果: " + temp);
        return temp;
    }

    /**
     * for put the upload new .
     * @param webUrl
     *            接口连接
     * @param method
     *            方法
     * @param params
     *            传�?内容
     * @param token
     *            accesstoken
     * @return String
     * @throws Throwable
     *             if has error
     */
    public static String sendRequestuploadnew(final String webUrl,
            final String method, final String params, final String token)
            throws Throwable {
        String temp;
        System.out.println("POST路径: " + webUrl);
        System.out.println("POST内容: " + params);

        String response = sendPut(webUrl, params, token, "application/json");
        temp = response.substring(response.indexOf("{"),
                response.lastIndexOf("}") + 1);
        System.out.println("POST结果: " + temp);
        return temp;
    }

    public static String sendDelete(final String webUrl,
            final String method, final JSONObject params)
            throws Throwable {
        String temp;
        System.out.println("POST路径: " + webUrl);
        System.out.println("POST内容: " + params);

        String response = sendPost(webUrl, params, null, "application/json");
        temp = response.substring(response.indexOf("{"),
                response.lastIndexOf("}") + 1);
        System.out.println("POST结果: " + temp);
        return temp;
    }
    
    /**
     * . for put the upload new
     * @param webUrl 接口连接
     * @param method 方法
     * @param params 内容
     * @param token token
     * @return String
     * @throws Throwable if has error
     */
    public static String sendRequestupload(final String webUrl,
            final String method, final JSONObject params, final String token)
            throws Throwable {
        String temp;
        System.out.println("POST路径: " + webUrl);
        System.out.println("POST内容: " + params);

        String response = sendupload(webUrl, params,
                "application/octet-stream; charset=UTF-8");
        temp = response.substring(response.indexOf("{"),
                response.lastIndexOf("}") + 1);
        System.out.println("POST结果: " + temp);
        return temp;
    }

   /**.
    * 请求上传图片
    * @param webUrl 接口连接
    * @param method 对应�?
    * @param params 内容
    * @return String
    * @throws Throwable if has error
    */
    public static String sendRequestImage(final String webUrl,
            final String method, final String params) throws Throwable {
        System.out.println("POST路径: " + webUrl);
        System.out.println("POST内容: " + params);

        String response = sendPost(webUrl, JSONObject.parseObject(params), "image/pjpeg");
        String temp = response.substring(response.indexOf("{"),
                response.lastIndexOf("}") + 1);
        System.out.println("POST结果: " + temp);

        return temp;
    }

    /**.
     * http上传图片
     * @param webUrl 接口连接
     * @param method 对应�?
     * @param accesstoken 头文件内�?
     * @param image 图片二进制数�?
     * @return String
     * @throws Throwable if has error
     */
    public static String sendRequestWithImage(final String webUrl,
            final String method, final String accesstoken, final byte[] image)
            throws Throwable {
        String temp;
        System.out.println(image);
        String images = new String(image);
        // System.out.println(images);
        System.out.println(images.getBytes());
        if (bwait) {
            Tools.sleep(waittime);
        }
        String content = "-------------------------------7db372eb000e2\r\n"
                + "Content-Disposition: form-data; name=\"file\"; "
                + "filename=\"kn.jpg\"\r\n Content-Type: image/jpeg\r\n\r\n"
                + images
                + "\r\n-------------------------------7db372eb000e2--\r\n";
        String response = sendPost(webUrl, content.getBytes(), accesstoken);
        temp = response.substring(response.indexOf("{"),
                response.lastIndexOf("}") + 1);
        System.out.println(Tools.getLineInfo() + ": " + temp);
        return temp;
    }

    /**
     * . 处理结果
     * @param postMethod
     *            执行后结�?
     * @return String
     * @throws IOException
     *             if has error
     */
    public static String resultmanage(final PostMethod postMethod)
            throws IOException {
        final int lager = 512;
        String result = "";
        int statuscode = postMethod.getStatusCode();
        byte[] rsp = null;
        if (statuscode == HttpStatus.SC_OK) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                InputStream in = postMethod.getResponseBodyAsStream();
                byte[] buf = new byte[lager];
                int count = 0;
                while ((count = in.read(buf)) > 0) {
                    bout.write(buf, 0, count);
                }
                rsp = bout.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (bout != null) {
                    bout.close();
                }
            }
        }
        System.out.println(postMethod.getStatusCode());
        if (rsp == null || rsp.length <= 0) {
            return "";
        }
        result = new String(rsp, "UTF-8");
        return result;
    }
    /**
     * DeleteMethod .
     * @param postMethod
     * @return
     * @throws IOException
     */
    public static String resultmanage(final DeleteMethod postMethod)
            throws IOException {
        final int lager = 512;
        String result = "";
        int statuscode = postMethod.getStatusCode();
        byte[] rsp = null;
        if (statuscode == HttpStatus.SC_OK) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                InputStream in = postMethod.getResponseBodyAsStream();
                byte[] buf = new byte[lager];
                int count = 0;
                while ((count = in.read(buf)) > 0) {
                    bout.write(buf, 0, count);
                }
                rsp = bout.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (bout != null) {
                    bout.close();
                }
            }
        }
        System.out.println(postMethod.getStatusCode());
        if (rsp == null || rsp.length <= 0) {
            return "";
        }
        result = new String(rsp, "UTF-8");
        return result;
    }
    /**
     * . 处理结果
     * @param putMethod
     *            执行后结�?
     * @return String
     * @throws IOException
     *             if has error
     */
    public static String resultmanage(final PutMethod putMethod)
            throws IOException {
        final int lager = 512;
        String result = "";
        int statuscode = putMethod.getStatusCode();
        byte[] rsp = null;
        if (statuscode == HttpStatus.SC_OK) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                InputStream in = putMethod.getResponseBodyAsStream();
                byte[] buf = new byte[lager];
                int count = 0;
                while ((count = in.read(buf)) > 0) {
                    bout.write(buf, 0, count);
                }
                rsp = bout.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (bout != null) {
                    bout.close();
                }
            }
        }
        System.out.println(putMethod.getStatusCode());
        if (rsp == null || rsp.length <= 0) {
            return "";
        }
        result = new String(rsp, "UTF-8");
        return result;
    }

    /**
     * . 格式化URL，去除不合规范的字符
     * @param strUrl
     *            待格式化的URL
     * @return 格式化完成后的URL
     */
    public static String validUrl(final String strUrl) {
        logger.debug("<-----");
        String temp = strUrl;
        logger.debug("Old URL: " + temp);
        if (null == temp) {
            logger.debug("----->(url null)");
            return null;
        }
        temp = temp.replaceAll("/(\\.|\\.\\.)/", "/");
        int iSize = temp.length();
        StringBuffer strRes = new StringBuffer();
        int iCur = 0;
        String strTemp = null;
        while (iCur < iSize) {
            strTemp = temp.substring(iCur, iCur + 1);
            byte[] ary = strTemp.getBytes();
            if (ary.length > 1) {
                for (int i = 0; i < ary.length; i++) {
                    strRes.append("%" + Tools.toHex(ary[i]));
                }
            } else {
                /* url中含有空�?*/
                if (strTemp.compareTo(" ") == 0) {
                    strRes.append("%20");
                } else if (strTemp.compareTo("\\") == 0) {
                    /* url中含有文件路�?如upload 文件 */
                    strRes.append("%5c");
                    /* 注稀,ku6 中的uri �?��替换&�?26 */
                    // }else if (strTemp.compareTo("%") == 0) {
                    // strRes.append("%25");// strRes.append("/");
                } else {
                    strRes.append(strTemp);
                }
            }
            iCur++;
        }
        logger.debug("fixed URL: " + strRes.toString());
        logger.debug("----->");
        return strRes.toString();
    }
    
    public static Map<String, Object> sendRequestInspector(String webUrl,
            JSONObject params) throws Throwable {
      
    	Map<String, Object> response = null;
    	String strParams = "";
    	
    	String bb2 = null;
		ArrayList<String> list = new ArrayList<String>(params.keySet());
		for (int i=0; i<list.size(); i++){
			bb2 = list.get(i);
			if (bb2.equals("setCookie")){
				continue;
			}
			if (i != list.size()-1){
				strParams = strParams + bb2 + "=" + params.getString(bb2) + "&";
			}else {
				strParams = strParams + bb2 + "=" + params.getString(bb2);
			}
		}
    	
        System.out.println("["+df.format(System.currentTimeMillis())
        		+ "] " + webUrl +"?" + strParams);

        response = webUrl.equals(Core.inspPublishUPUrl) ? sendPostUrlPar(webUrl, strParams, params.getString("setCookie"))
        		: sendGet(webUrl, strParams, 
        				params.containsKey("setCookie")?params.getString("setCookie"):"");

        System.out.println("["+df.format(System.currentTimeMillis())
        		+ "] " + response);
    
        return response;
    }
    
    @SuppressWarnings("deprecation")
	public static String sendRequest(String webUrl,
            JSONObject params) throws Throwable {  
    	String response = null;
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    	//SimpleDateFormat df = new SimpleDateFormat();
        System.out.println("["+df.format(System.currentTimeMillis())
        		+ "] "+ webUrl + "?parameter=" + params);

//        // 对含空格的字段做url编码
//        String bb2 = null;
//		ArrayList<String> list = new ArrayList<String>(params.keySet());
//		for (int i=0; i<list.size(); i++){
//			bb2 = list.get(i);
//			if (params.getString(bb2).contains(" ")
//					|| bb2.contains("Url")){
//				params.put(bb2, URLEncoder.encode(params.getString(bb2)));
//        	}
//		}

		// web平台登录时做urlencode
		if (params.containsKey("platform") 
				&& params.getString("platform").equals("1")){
			String s = URLEncoder.encode(params.toJSONString(), "UTF-8");
			response = sendPostUrlParam(webUrl, s);
		}else {
			response = sendPostUrlParam(webUrl, params.toString());
		}

        System.out.println("["+df.format(System.currentTimeMillis())
        		+ "] "+ response);
    
        return response;
    }
    
    
    public static String sendRequestXiaoMiPayment(String webUrl) throws Throwable {
      
    	String response = null;
    	
        System.out.println("url: " + webUrl);

//        webUrl = URLEncoder.encode(webUrl, "UTF-8");
		response = sendPostXiaoMiPayMent(webUrl);

        System.out.println("response: " + response);
    
        return response;
    }
    
    public static HttpResponds sendPostHeader(String url, String content,
    		JSONObject requestHeader) throws HttpException, IOException {
		String temp = "";
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		System.err.print(url);
		RequestEntity entity;

		entity = new StringRequestEntity(content, "text/xml", "utf-8");
		System.out.println("\nbodybodybody="+content);
		postMethod.setRequestEntity(entity);
		if(requestHeader!=null){
			Set<String> keys = requestHeader.keySet();
			// 灏咼SONArray鏁版嵁鏀惧埌鏈�悗瑙ｆ瀽
			for (String tp : keys) {
				postMethod.setRequestHeader(tp, requestHeader.getString(tp));
			}
		}

		client.executeMethod(postMethod);
		org.apache.commons.httpclient.Header[] hd=postMethod.getRequestHeaders();
//		System.out.println("################");
//		for(Header hdtemp:hd){
//			System.out.println(hdtemp.getName()+"="+hdtemp.getValue());
//		}
	
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] rsp = null;
		try {
			InputStream in = postMethod.getResponseBodyAsStream();
//			System.err.print("log");
			byte[] buf = new byte[2048];
			int count = 0;
			while ((count = in.read(buf)) > 0) {
				bout.write(buf, 0, count);
			}
			rsp = bout.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bout != null)
				bout.close();
		}
		if (rsp == null || rsp.length <= 0) {
			return null;
		}
		//temp = new String(rsp, "UTF-8");
		
        temp=new String(rsp,"UTF-8");
        temp=temp.replace("&#xA;", ""); 
		assertEquals(200, postMethod.getStatusCode());
		hd=postMethod.getResponseHeaders();
//		System.out.println("################");
//		for(Header hdtemp:hd){
//			System.out.println(hdtemp.getName()+"="+hdtemp.getValue());
//		}
		HttpResponds hr=new HttpResponds();
		hr.setHeaderList(postMethod.getResponseHeaders());
		
		hr.setBody(temp);
	//	String sessionstr=null;
	//	Header session=postMethod.getResponseHeader("Set-Cookie");
		
	//	sessionstr=session.getValue();

		return hr;
	}
	
	public static HttpResponds sendPostHeaderJSON(String url, JSONObject content,
			JSONObject requestHeader) throws HttpException, IOException {
		String temp = "";
		HttpClient client = new HttpClient();
		
		if (url.equals(Config.familyLogin())) {
			url = url + "date=" + content.getString("date")
					+ "&userid=" + content.getString("userid")
					+"&password=" + content.getString("password")
					+"&type=" + content.getString("type")
					+"&struts.token.name=token&token="
					+ content.getString("token")
					+"&JSESSIONID9494=" + content.getString("JSESSIONID9494");
		}
		PostMethod postMethod = new PostMethod(url);

		if(content != null && requestHeader != null && !url.contains(Config.familyLogin())){
			Set<String> keys = content.keySet();
			// 灏咼SONArray鏁版嵁鏀惧埌鏈�悗瑙ｆ瀽
			for (String tp : keys) {
				postMethod.setParameter(tp, content.getString(tp));
			}
		}
		
		if(requestHeader!=null){
			Set<String> keys = requestHeader.keySet();
			// 灏咼SONArray鏁版嵁鏀惧埌鏈�悗瑙ｆ瀽
			for (String tp : keys) {
				postMethod.setRequestHeader(tp, requestHeader.getString(tp));
			}
		}

		client.executeMethod(postMethod);
		Header[] hd=postMethod.getRequestHeaders();

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] rsp = null;
		try {
			InputStream in = postMethod.getResponseBodyAsStream();
			System.err.print("log");
			byte[] buf = new byte[2048];
			int count = 0;
			while ((count = in.read(buf)) > 0) {
				bout.write(buf, 0, count);
			}
			rsp = bout.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bout != null)
				bout.close();
		}
		if (rsp == null || rsp.length <= 0) {
			return null;
		}
		//temp = new String(rsp, "UTF-8");
		
        temp=new String(rsp,"UTF-8");
        temp=temp.replace("&#xA;", ""); 
		assertEquals(200, postMethod.getStatusCode());
		hd=postMethod.getResponseHeaders();

		HttpResponds hr=new HttpResponds();
		hr.setHeaderList(postMethod.getResponseHeaders());
		
		hr.setBody(temp);

		return hr;
	}
    
	public static HttpResponds sendGetBackGroud(String url,String Content,
			String SessionName,String SessionValue,JSONObject requestHeader) {
		
		String temp = "";
		url=url+Content;
		System.out.println("GET URL:"+url);
		//JSONObject  response = null ;
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		Set<String> keys = requestHeader.keySet();
		// 灏咼SONArray鏁版嵁鏀惧埌鏈�悗瑙ｆ瀽
		for (String tp : keys) {
			getMethod.setRequestHeader(tp, requestHeader.getString(tp));
		}
		getMethod.setRequestHeader("Cookie", SessionName+"="+SessionValue);

		try {

			client.executeMethod(getMethod);
			Header[] hd=getMethod.getRequestHeaders();
			System.out.println("################");
			for(Header hdtemp:hd){
				System.out.println(hdtemp.getName()+"="+hdtemp.getValue());
			}
			hd=getMethod.getResponseHeaders();
			System.out.println("################");
			for(Header hdtemp:hd){
				System.out.println(hdtemp.getName()+"="+hdtemp.getValue());
			}

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] rsp = null;
			try {
				InputStream in = getMethod.getResponseBodyAsStream();
				byte[] buf = new byte[512];
				int count = 0;
				while ((count = in.read(buf)) > 0) {
					bout.write(buf, 0, count);
				}
				rsp = bout.toByteArray();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (bout != null)
					bout.close();
			}
			// String status = String.valueOf(postMethod.getStatusCode());
		//	assertEquals(200, getMethod.getStatusCode());
			if (rsp == null || rsp.length <= 0) {
				return null;
			}
			temp = new String(rsp, "UTF-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print temp string
		// System.out.println(temp);
		//response = JSONObject.parseObject(temp);
		HttpResponds hr=new HttpResponds();
		hr.setBody(temp);
		hr.setHeaderList(getMethod.getResponseHeaders());
		return hr;
	}
}
