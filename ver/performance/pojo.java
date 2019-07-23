package performance;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.Serializable;

public class pojo implements Serializable {

	private static final long serialVersionUID = 6072371965678419954L;
	private String Memory;// 内存
	private String sFlow;// 上传的流量
	private String rFlow;// 下载的流量
	private String CPU;// cpu使用
	private String Power;//电量
	
	public pojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public pojo(String Memory, String sFlow,String rFlow, String CPU, String Power ) {
		super();
		this.Memory = Memory;
		this.sFlow = sFlow;
		this.rFlow = rFlow;
		this.CPU = CPU;
		this.Power= Power;
	}

	public String getMemory() {
		return Memory;
	}

	public void setMemory(String Memory) {
		this.Memory = Memory;
	}

	public String getsFlow() {
		return sFlow;
	}

	public void setsFlow(String sFlow) {
		this.sFlow = sFlow;
	}
	public String getrFlow() {
		return rFlow;
	}

	public void setrFlow(String rFlow) {
		this.rFlow = rFlow;
	}
	
	public String getCPU() {
		return CPU;
	}

	public void setCPU(String CPU) {
		this.CPU = CPU;
	}
	
	public String getPower() {
		return Power;
	}

	public void setPower(String Power) {
		this.Power = Power;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sFlow == null) ? 0 : sFlow.hashCode());
		result = prime * result + ((Memory == null) ? 0 : Memory.hashCode());
		result = prime * result + ((rFlow == null) ? 0 : rFlow.hashCode());
		result = prime * result + ((CPU == null) ? 0 : CPU.hashCode());
		result = prime * result + ((Power == null) ? 0 : Power.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		pojo other = (pojo) obj;
		if (sFlow == null) {
			if (other.sFlow != null)
				return false;
		} else if (!sFlow.equals(other.sFlow))
			return false;
		if (rFlow == null) {
			if (other.rFlow != null)
				return false;
		} else if (!rFlow.equals(other.rFlow))
			return false;
		if (Memory == null) {
			if (other.Memory != null)
				return false;
		} else if (!Memory.equals(other.Memory))
			return false;
		if (CPU == null) {
			if (other.CPU != null)
				return false;
		} else if (!CPU.equals(other.CPU))
			return false;
		if (Power == null) {
			if (other.Power != null)
				return false;
		} else if (!Power.equals(other.Power))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Info [Memory=" + Memory + ", sFlow=" + sFlow + ",rFlow=" + rFlow + ", CPU=" + CPU + ", Power=" + Power+"]";
	}

}

