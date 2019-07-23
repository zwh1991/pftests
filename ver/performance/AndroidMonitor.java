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
        String str3 = null;
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
            String PID = str1.trim();
            str3 = PID;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return str3;
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
            String str2 = str1.substring(str1.indexOf("=") + 1, str1.indexOf("=") + 8).trim();

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

	
	public static String getUid(String PackageName) {
		Process proc = null;
        String str4 = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String adb = "adb shell ps | grep "+ PackageName;
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
            str4 = "u0"+str2;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }

        return str4;
    }
	// 获取程序消耗的上传流量
    public static double sFlow(String PackageName) {
        double sflow = 0;
        try {
            String UID = getUID(PackageName);

            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("adb shell cat /proc/uid_stat/" + UID + "/tcp_snd");
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
                //上传的流量
                String str4 = str1.trim();
                int b = Integer.parseInt(str4);
                sflow = b / 1024;
                System.out.println("sflow:"+ sflow);

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
            Process proc = runtime.exec("adb shell cat /proc/uid_stat/" + UID + "/tcp_rcv");
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
                //上传的流量
                String str4 = str1.trim();
                int b = Integer.parseInt(str4);
                rflow = b / 1024;
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
                String str3 = str1.substring(str1.indexOf(PackageName) - 43, str1.indexOf(PackageName)).trim();
                String cpu = str3.substring(0, 2);
                cpu = cpu.trim();
                Cpu = Double.parseDouble(cpu);

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
//        System.err.println(Cpu);
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
    	String uid = getUid(PackageName);
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
            System.out.print("m请检查设备是否连接");
            prop= "未接入设备" ;

        }
        return prop;
    
    }
    
    
	//获取包名
	public static String getPackageName() {

        Process proc = null;
        String packagename = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String adb ="adb shell dumpsys activity | grep mFocusedActivity";
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
    public static String getVersion(){
    	String version = null;
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
                version = str2;
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
            System.out.print("m请检查设备是否连接");
            version= "未接入设备" ;

        }
        return version;
    
    }
	
	
    @Test
    public void tets(){
    	String packagename ="com.thankyo.hwgame";
    	getVersion();
    	getPackageName();
    	
    	getUID(packagename);
    	getUid(packagename);
    	getMemory(packagename);
    	getPower(packagename);
    	String ppp= getprop();
    	
    }
}
