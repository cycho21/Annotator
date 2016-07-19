package kr.ac.uos.ai.uimaTest.core;

import java.util.ArrayList;

public class LogLine {
	private String					text;
	private ArrayList<Integer>		t_ids;
	private ArrayList<String[]>		data;
	
	public LogLine(String text) {
		this.text = text;
		this.t_ids = new ArrayList<Integer>();
		this.data = new ArrayList<String[]>();
	}
	
	public void addID(int id){
		t_ids.add(id);
	}
	
	public ArrayList<Integer> getIDList(){
		return t_ids;
	}
	
	public void addData(String[] data){
		this.data.add(data);
	}
	public boolean hasResult(){
		return t_ids.size() != 0;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(text).append('\n');
		for (String[] strings : data) {
			for (String string : strings) {
				sb.append(string).append('\t');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}
