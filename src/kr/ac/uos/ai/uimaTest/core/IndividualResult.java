package kr.ac.uos.ai.uimaTest.core;

import org.apache.uima.cas.IntArrayFS;

public class IndividualResult {
	public IndividualResult(long startTime2, long endTime2, IntArrayFS result2) {
		this.startTime = startTime2;
		this.endTime = endTime2;
		if(result2!= null)
			this.result = result2.toArray();
	}
	private long					startTime;
	private long					endTime;
	private int[]					result;
	public int[] getResultArray() {
		
		return result;
	}
	public long getEndTime() {
		
		return endTime;
	}
	public long getStartTime() {
		
		return startTime;
	}
}
