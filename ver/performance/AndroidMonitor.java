package performance;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fileio.util.BaseIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;


/**
 * Created on 2019-7-01
 * 获取安卓手机性能数据
 */
public class AndroidMonitor {

	//获取pid
	public static String getPID(String PackageName) {

        Process proc = null;
        String PID = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec("adb shell pgrep -o " + PackageName);

            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");

            }
            String str1 = stringBuffer.toString();
            PID = str1.trim();

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return PID;
    }
	
	//获取流量uid
	public static String getUID(String packagename) {

        Process proc = null;
        String UID = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String adb = "adb shell dumpsys package " +packagename+" | grep userId=";
            proc = runtime.exec(adb);

            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");

            }
            String str1 = stringBuffer.toString();
            String str2 = str1.substring(str1.indexOf("=") + 1, str1.length()).trim();

            UID = str2;

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return UID;
    }

	//获取电量uid
	public static String getUid(int version, String PackageName) {
		Process proc = null;
        String Uid = null;
        String adb = null;
        if(version < 8 ){
        	adb = "adb shell ps | grep "+ PackageName;
        }else {
        	adb = "adb shell ps -A | grep "+ PackageName;  	
        }      
        try {
            Runtime runtime = Runtime.getRuntime();  
            proc = runtime.exec(adb);

            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");

            }
            String str1 = stringBuffer.toString();
            String str2 = str1.substring(str1.indexOf("_") + 1, str1.indexOf("_") + 8).trim();
            Uid = "u0"+str2;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return Uid;
    }
	// 获取程序消耗的上传流量
    public static double sFlow(String PackageName) {
        double sflow = 0;
        try {
            String UID = getUID(PackageName);

            Runtime runtime = Runtime.getRuntime();
            String adb = "adb shell cat /proc/uid_stat/" + UID + "/tcp_snd";
            Process proc = runtime.exec(adb);
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");

                }
                String str1 = stringBuffer.toString();
                if (str1.equals("cat: /proc/uid_stat/"+ UID + "/tcp_snd: No such file or directory ")){
                	System.out.println("No such file or directory");
                	sflow = -1;
                }else {
                    //上传的流量
                    String str4 = str1.trim();
                    int b = Integer.parseInt(str4);
                    sflow = b / 1024;
                    System.out.println("sflow:"+ sflow);
                }


            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        } catch (Exception StringIndexOutOfBoundsException) {
            System.out.println("sf请检查设备是否连接");
            sflow = -1;
        }

        return sflow;
    }
    
    // 获取程序消耗的下载流量
    public static double rFlow(String PackageName) {

        double rflow = 0;
        try {
            String UID = getUID(PackageName);
            
            Runtime runtime = Runtime.getRuntime();
            String adb = "adb shell cat /proc/uid_stat/" + UID + "/tcp_rcv";
            Process proc = runtime.exec(adb);
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");

                }
                
                
                String str1 = stringBuffer.toString();
                if (str1.equals("cat: /proc/uid_stat/"+ UID + "/tcp_rcv: No such file or directory ")){
                	System.out.println("No such file or directory");
                	rflow = -1;
                }else {
                    //上传的流量
                    String str4 = str1.trim();
                    int b = Integer.parseInt(str4);
                    rflow = b / 1024;	
                	
                }

            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        } catch (Exception StringIndexOutOfBoundsException) {
            System.out.println("rf请检查设备是否连接");
            rflow = -1;
        }

        return rflow;
    }
    
    
    public static double getCPU(String PackageName) {

        double Cpu = 0;
        int version = getVersion();
        if (version<8){
            try {
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec("adb shell top -n 1| grep " + PackageName);
                try {
                    if (proc.waitFor() != 0) {
                        System.err.println("exit value = " + proc.exitValue());
                    }
                    BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        stringBuffer.append(line + " ");

                    }

                    String str1 = stringBuffer.toString();
                    String str3 = str1.substring(str1.indexOf("%") - 4, str1.indexOf("%")).trim();
                    Cpu = Double.parseDouble(str3);

                } catch (InterruptedException e) {
                    System.err.println(e);
                } finally {
                    try {
                        proc.destroy();
                    } catch (Exception e2) {
                    }
                }
            } catch (Exception StringIndexOutOfBoundsException) {

                System.out.println("c请检查设备是否连接");
                Cpu = -1;
            }
        }else{
        	Cpu = -1;
        }
        System.out.println("Cpu:"+ Cpu);
        return Cpu;

    }

    public static double getMemory(String PackageName) {
        double Heap = 0;
        try {
            Runtime runtime = Runtime.getRuntime();
            String adb = "adb shell dumpsys meminfo " + PackageName + "| grep TOTAL:";
            Process proc = runtime.exec("adb shell dumpsys meminfo " + PackageName + "| grep TOTAL:");
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");

                }

                String str1 = stringBuffer.toString();
                String str2 = str1.substring(str1.indexOf("TOTAL:") + 6 ,str1.indexOf("TOTAL SWAP")).trim();
                Heap = Double.parseDouble(str2) / 1024;
                DecimalFormat df = new DecimalFormat("#.00");
                String memory = df.format(Heap);
                Heap = Double.parseDouble(memory);
                System.err.println("Heap:" + Heap);

            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        }

        catch (Exception StringIndexOutOfBoundsException) {
            System.out.print("m请检查设备是否连接");
            Heap= -1 ;

        }
        
        return Heap;
    }
    
    //获取电量
    public static double getPower(String PackageName){
    	double power = 0;	
    	String uid = getUid(getVersion(),PackageName);
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("adb shell dumpsys batterystats " + PackageName + "| grep "+ uid);
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");

                }
                String str1 = stringBuffer.toString();
                String str2 = str1.substring(str1.indexOf(":") + 1 ,str1.indexOf("("));
                str2 = str2.trim();
                power = Double.parseDouble(str2);
                System.out.print("power:" + power);
                
            	} catch (InterruptedException e) {
            		System.err.println(e);
            	} finally {
            		try {
                    proc.destroy();
            		} catch (Exception e2) {
                }
            }
        }

        catch (Exception StringIndexOutOfBoundsException) {
            System.out.print("m请检查设备是否连接");
            power= -1 ;

        }
        return power;
    
    }
    
    //获取GPU
    public static double getGPU(String PackageName){
    	double GPU = 0;
        try {
            Runtime runtime = Runtime.getRuntime();
            String adb = "adb shell dumpsys gfxinfo " + PackageName + " reset | grep Janky";
            Process proc = runtime.exec(adb);
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");

                }
                String str1 = stringBuffer.toString();
                String str2 = str1.substring(str1.indexOf("(") + 1 ,str1.indexOf("%")).trim();
                GPU = Double.parseDouble(str2);
                System.err.println("GPU:" + GPU);

            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        }

        catch (Exception StringIndexOutOfBoundsException) {
            System.out.print("请检查设备是否连接");
            GPU= -1 ;

        }
        return GPU;
    
    }
    
    //获取设备名称
    public static String getprop(){
    	String prop = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("adb shell getprop ro.product.model");
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");

                }
                String str1 = stringBuffer.toString().trim();
                prop = str1;
                System.err.println("prop:" + prop);

            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        }

        catch (Exception StringIndexOutOfBoundsException) {
            System.out.print("请检查设备是否连接");
            prop= "未接入设备" ;

        }
        return prop;
    
    }
    
    
	//获取包名
	public static String getPackageName() {

        Process proc = null;
        String packagename = null;
        String adb = null;
        int version = getVersion();
        if (version < 8){
        	adb ="adb shell dumpsys activity | grep mFocusedActivity";
        }else{
        	adb ="adb shell dumpsys activity | grep mResumed";
        }
        try {
            Runtime runtime = Runtime.getRuntime();
             
            proc = runtime.exec(adb);

            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");

            }
            String str1 = stringBuffer.toString();
            String str2 = str1.substring(str1.indexOf(".") -4 ,str1.indexOf("/")).trim();
            packagename = str2;
            System.out.print("packagename:" + packagename);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return packagename;
    }
    
    //获取安卓版本
    public static int getVersion(){
    	int version = 0;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("adb shell getprop ro.build.version.release");
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " + proc.exitValue());
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + " ");

                }
                String str1 = stringBuffer.toString();
                String str2 = str1.substring(0,str1.indexOf(".")).trim();
                version = Integer.valueOf(str2);
                System.err.println("version:" + version);

            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                try {
                    proc.destroy();
                } catch (Exception e2) {
                }
            }
        }

        catch (Exception StringIndexOutOfBoundsException) {
            System.out.print("请检查设备是否连接");
            version= -1 ;

        }
        return version;
    
    }

}
