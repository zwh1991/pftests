/**
 * 
 */
package android.util;

/**
 * @author ybzhang@melot.com
 * @date	2015-7-20
 */
public class Log {

	public static int d(String tag,String msg){
		System.out.println("------------------------------------------");
		System.out.println("tag:" + tag);
		System.out.println(msg);
		System.out.println("------------------------------------------");
		return 0;
	}
	
	public static int e(String tag,String msg){
		System.out.println("------------------------------------------");
		System.err.println("tag:" + tag);
		System.err.println(msg);
		System.out.println("------------------------------------------");
		return 0;
	}
	
	public static int i(String tag,String msg){
		System.out.println("------------------------------------------");
		System.out.println("tag:" + tag);
		System.out.println(msg);
		System.out.println("------------------------------------------");
		return 0;
	}

}
