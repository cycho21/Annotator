package activemq;

import org.hyperic.sigar.*;
import org.junit.Test;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-11-03
 */

public class ResourceMonitor {

    public static Sigar sigar;

    public ResourceMonitor(){
    }

    public void init() {
        if (sigar == null) {
            sigar = new Sigar();
        }
    }

    public String getMem() {
        Mem mem = null;
        try {
            mem = sigar.getMem();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return String.valueOf(mem.getTotal()/1024);
    }

    public CpuInfo[] getCPU() {
        CpuInfo[] cpuInfoList = null;
        try {
            cpuInfoList = sigar.getCpuInfoList();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return cpuInfoList;
    }
    
    public String getFreeMem() {
    	String tempString = null;
    	try {
			tempString = String.valueOf(sigar.getMem().getFreePercent());
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return tempString;
    }
    
    public void getCpuUsageByPID(String pid){
        while (true) {
            ProcCpu cpu = null;
            try {
                cpu = sigar.getProcCpu(pid);
            } catch (SigarException e) {
                e.printStackTrace();
            }
            System.out.println(cpu.getPercent());
        }
    }

}