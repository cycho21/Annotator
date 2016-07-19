package kr.ac.uos.ai.uimaTest.core;

import static kr.ac.uos.ai.uimaTest.wp6Test.Configuration.*;
import kr.ac.uos.ai.uimaTest.wp6Test.nlp2rdf.NLP2RDF;

import org.apache.uima.cas.IntArrayFS;

public class ProcessResult {
	
	private IndividualResult			nlp2rdfResult;
	private IndividualResult			l2kResult;
	private IndividualResult			disamResult;
	private IndividualResult			bBoxResult;
	private IndividualResult			vBoxResult;

	public void setResult(String name, long startTime, long endTime,
			IntArrayFS result) {
		IndividualResult indResult = new IndividualResult(startTime, endTime, result);
		if(name.equals(Nlp2Rdf)){
			nlp2rdfResult = indResult;
		}else if(name.equals(L2K)){
			l2kResult = indResult;
		}else if(name.equals(Disambiguation)){
			disamResult = indResult;
		}else if(name.equals(BBox)){
			bBoxResult = indResult;
		}else if(name.equals(VBox)){
			vBoxResult = indResult;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getLogData(Nlp2Rdf, nlp2rdfResult));
		sb.append(getLogData(L2K, l2kResult));
		sb.append(getLogData(Disambiguation, disamResult));
		sb.append(getLogData(BBox, bBoxResult));
		sb.append(getLogData(VBox, vBoxResult));
		return sb.toString();
	}
	
	private String getLogData(String string, IndividualResult result) {
		if(result==null){
			 return string + " no Input\n";
		 }
		 int[] array = result.getResultArray();
		 StringBuilder sb = new StringBuilder();
		 sb.append(string+" result").append("\n");
		 if(array==null){
			 return string + " no Result\n";
		 }
		 for (int data : array) {
			 sb.append("\t" +data).append("\n");
		}
		 sb.append("Processed Time : "+ (result.getEndTime()-result.getStartTime()));
		 sb.append("\n");
		 return sb.toString();
	}

}
