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
	public static String PID(String PackageName) {

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
//            String str2 = str1.substring(str1.indexOf(" " + PackageName) - 46, str1.indexOf(" " + PackageName));
//            String PID = str2.substring(0, 7);
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
	
	//获取uid
	public static String UID(String PID) {

        Process proc = null;
        String str3 = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec("adb shell cat /proc/" + PID +"/status");

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
            String str2 = str1.substring(str1.indexOf("Uid:"), str1.indexOf("Uid:") + 30);
            String UID = str2.substring(4, 11);
            UID = UID.trim();

            str3 = UID;
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

	
	public static String Uid(String PackageName) {
		Process proc = null;
        String str4 = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec("adb shell ps | grep "+ PackageName);

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
//            String str2 = str1.substring(str1.indexOf("com.thankyo.hwgame")-30, str1.indexOf("Uid:"));
            String UID = str1.substring(3, 7);
            UID = UID.trim();

            str4 = "u0"+UID;
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
//    	double lsf = lastsFlow;
        double sflow = 0;
        try {

            String PID = PID(PackageName);
            String UID = UID(PID);

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
//                String str2 = str1.substring(str1.indexOf("uid:"), str1.indexOf("uid:") + 120);
//                String str4 = str2.substring(6, 17);
                //上传的流量
                String str4 = str1.trim();
//                String str6 = str2.substring(68, 75);
//                str6 = str6.trim();
                int b = Integer.parseInt(str4);
//                int a = Integer.parseInt(str6);

//                double sendFlow = a / 1024;
                 sflow = b / 1024;
//                sflow = sendFlow/1024;
//                flow = sendFlow + revFlow;
                
                
//                System.out.println(sflow);

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

            String PID = PID(PackageName);
            String UID = UID(PID);

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
//                rflow = revFlow/1024;


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
            Process proc = runtime.exec("adb shell dumpsys meminfo " + PackageName);
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
//                String str2 = str1.substring(str1.indexOf("Objects") - 60, str1.indexOf("Objects"));
                String str2 = str1.substring(str1.indexOf("TOTAL:"),str1.indexOf("TOTAL:") + 20);
//                String str3 = str2.substring(0, 10);
                String str3 = str2.substring(8, 20);
                str3 = str3.trim();
                Heap = Double.parseDouble(str3) / 1024;
                DecimalFormat df = new DecimalFormat("#.000");
                String memory = df.format(Heap);
                Heap = Double.parseDouble(memory);
//                System.err.println(Heap);

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
    

    public static double getPower(String PackageName){
    	double power = 0;
    	String uid = Uid(PackageName);
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
//                String str2 = str1.substring(str1.indexOf("Objects") - 60, str1.indexOf("Objects"));
                String str2 = str1.substring(str1.indexOf("Uid"),str1.indexOf("Uid") + 20);
//                String str3 = str2.substring(0, 10);
                String str3 = str2.substring(11, 15);
                str3 = str3.trim();
                power = Double.parseDouble(str3);
//                System.err.println(Heap);

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
    
    public static String getprop(){
    	String prop = null;
//    	String uid = Uid(PackageName);
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

                String str1 = stringBuffer.toString();
//                String str2 = str1.substring(str1.indexOf("Objects") - 60, str1.indexOf("Objects"));
//                String str2 = str1.substring(str1.indexOf("Uid"),str1.indexOf("Uid") + 20);
//                String str3 = str2.substring(0, 10);
//                String str3 = str1.substring(1, 10);
                String str3 = str1.trim();
//                power = Double.parseDouble(str3);
                prop = str3;
                System.err.println(prop);

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
    
    
//    @Test
//    public void tets(){
//    	String ppp= getprop();
//    	
//    }
}
