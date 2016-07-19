package testCode;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LogData {
	private boolean					processed;
	
	private String						text;
	
	private ArrayList<Integer>			nlp2rdfResult;
	private long						nlp2rdfTime;
	
	private ArrayList<Integer>			l2kResult;
	private long						l2kTime;
	
	private ArrayList<Integer>			disambiguationResult;
	private long						disTime;
	
	private ArrayList<Integer>			bBoxResult;
	private long						bboxTime;
	
	private ArrayList<Integer>			vBoxResult;
	private long						vBoxTime;
	
	private ArrayList<UniqueTriple>		triple;
	
	private String 						log;
	
	public LogData(ArrayList<String> logs) {
		
		nlp2rdfResult = new ArrayList<Integer>();
		l2kResult = new ArrayList<Integer>();
		disambiguationResult = new ArrayList<Integer>();
		bBoxResult = new ArrayList<Integer>();
		vBoxResult = new ArrayList<Integer>();
		int state = 0;
		processed = true;
		StringBuilder sb = new StringBuilder();
		for (String string : logs) {
			sb.append(string).append("\n");
			if(string.startsWith("text")){
				this.text = string.substring(5);
				state = 1;
				continue;
			}
			if(state == 1){
				if(string.equals("Not Processed")){
					processed = false;
					break;
				}else if(string.equals("NLP2RDF result")){
					state = 10;
					continue;
				}
			}
			if(state == 10){
				if(string.startsWith("Processed Time")){
					nlp2rdfTime = Long.valueOf(string.split(":")[1].trim());
					state = 2;
					continue;
				}else{
					nlp2rdfResult.add(Integer.valueOf(string.trim()));
					continue;
				}
			}
			if(state == 2){
				if(string.equals("L2K no Input")){
					break;
				}else{
					state = 20;
					continue;
				}
			}
			if(state==20){
				if(string.startsWith("Processed Time")){
					l2kTime = Long.valueOf(string.split(":")[1].trim());
					state = 3;
					continue;
				}else{
					l2kResult.add(Integer.valueOf(string.trim()));
					continue;
				}
			}
			if(state == 3){
				if(string.equals("Disambiguation no Input")){
					break;
				}else{
					state = 30;
					continue;
				}
			}
			if(state==30){
				if(string.startsWith("Processed Time")){
					disTime = Long.valueOf(string.split(":")[1].trim());
					state = 4;
					continue;
				}else{
					disambiguationResult.add(Integer.valueOf(string.trim()));
					continue;
				}
			}
			if(state == 4){
				if(string.equals("B-Box no Input")){
					break;
				}else{
					state = 40;
					continue;
				}
			}
			if(state==40){
				if(string.startsWith("Processed Time")){
					bboxTime = Long.valueOf(string.split(":")[1].trim());
					state = 5;
					continue;
				}else{
					bBoxResult.add(Integer.valueOf(string.trim()));
					continue;
				}
			}
			if(state == 5){
				if(string.equals("V-Box no Input")){
					break;
				}else{
					state = 50;
					continue;
				}
			}
			if(state==50){
				if(string.startsWith("Processed Time")){
					vBoxTime = Long.valueOf(string.split(":")[1].trim());
					break;
				}
				else{
					vBoxResult.add(Integer.valueOf(string.trim()));
					continue;
				}
			}

		}
		this.log = sb.toString();
		

		
	}
	
	public long getNlp2rdfTime() {
		return nlp2rdfTime;
	}

	public long getL2kTime() {
		return l2kTime;
	}

	public long getDisTime() {
		return disTime;
	}

	public long getBboxTime() {
		return bboxTime;
	}

	public long getvBoxTime() {
		return vBoxTime;
	}

	public int getErrorCode(){
		if(!this.processed){
			return 1;
		}
		if(l2kResult.isEmpty()){
			return 2;
		}
		if(disambiguationResult.isEmpty()){
			return 3;
		}
		if(bBoxResult.isEmpty()){
			return 4;
		}
		if(vBoxResult.isEmpty()){
			return 5;
		}
		
		return 0;
	}
	
	public String getLog(){
		return log;
	}

	public int getTripleNumber() {
		int result = vBoxResult.size();
		if(result == 0){
			result = bBoxResult.size();
		}
		return result;
	}
	public int getReducedTripleNumber(){
		if(triple!=null)
			return triple.size();
		return 0;
	}
	
	public List<Integer> getVBoxResult(){
		return vBoxResult;
	}
	public List<Integer> getBBoxResult(){
		return bBoxResult;
	}
	
	public void setTripleList(ArrayList<UniqueTriple> list){
		this.triple = list;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(text).append('\n');
		if(triple==null){
			return sb.toString();
		}
		for (UniqueTriple ut : triple) {
			sb.append(ut).append('\n');
		}
		return sb.toString();
	}

	public int getCEValueAssignedCount() {
		int count = 0;
		for (UniqueTriple ut : triple) {
			if(ut.hasCEValue()){
				count++;
			}
		}
		return count;
	}

	public String getText() {
		
		return text;
	}

	public List<Integer> getBboxResult() {
		return bBoxResult;
	}
}
