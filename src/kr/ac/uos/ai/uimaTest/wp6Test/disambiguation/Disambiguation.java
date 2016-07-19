package kr.ac.uos.ai.uimaTest.wp6Test.disambiguation;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static kr.ac.uos.ai.uimaTest.wp6Test.Configuration.*;
import kr.ac.uos.ai.uimaTest.wp6Test.IndexNumInfo;
import kr.ac.uos.ai.uimaTest.wp6Test.Log4Anno;
import kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.tcas.Annotation;

import activemq.Receiver;
import enumType.AnnotatorType;
import enumType.ProcessType;

public class Disambiguation extends Log4Anno{

	private String text;
	private final Logger logger = Logger.getLogger(Disambiguation.class);
	private final String HOME									= System.getenv("HOME");
	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		try {
			 WP6Cas disamAnno = new WP6Cas(cas);
			 PropertyConfigurator.configure(HOME+"/log4j.properties");
			 text = cas.getDocumentText();	 
				String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
				InetAddress ip = null;
				try {
					ip = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				String ipAdrs = ip.getHostAddress();
			 
			 init();
				
			 disamAnno.setStartTime(System.currentTimeMillis());
			 disamAnno.setProcessName(Disambiguation);
			 
			 
					logger.info("CollectionReader:collectionReaderDescriptor" + "|"
							+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:DISAMBIGUATION"
							+ "|" + "ProcessType:LOAD" + "|" + "Data:" + text);
				
			 
			 System.out.println("Disambiguation start");
			 StringBuilder sb = new StringBuilder();
			 AnnotationIndex<Annotation> anno = cas.getAnnotationIndex(WP6Cas.type);
			 sb.append(Disambiguation).append("\n");
			 WP6Cas t = null;
			 for (Annotation annotation : anno) {
				 if(annotation instanceof WP6Cas){
					WP6Cas temp = (WP6Cas) annotation;
					if(temp.getProcessName().equals(L2K)){
						t = temp;
					}
				 }
			}
			 if(t == null){
				 System.out.println("no Annotation");
				 return;
			 }
			 IntegerArray ir  = t.getResult();
			 if(ir==null){
				 System.out.println("no Input");
				 return;
			 }

			 int[] array = ir.toArray();
			 ArrayList<Integer> list = new ArrayList<Integer>();
			 for (int data : array) {
				 sb.append("input : " +data).append("\n");
				 list.add(Integer.valueOf(data));
			}
			
			 if(list.size()==0){
				 System.out.println("no Input");
				 return;
			 }
			 
			 
			 
					logger.info("CollectionReader:collectionReaderDescriptor" + "|"
							+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:DISAMBIGUATION"
							+ "|" + "ProcessType:PROCESSING" + "|" + "Data:" + text);
				 
			 
			 List<Integer> tripleIds =  new DisambiguationAction().doAction(list);
			 
			 
			 sb.append("result").append("\n");
			 sb.append("\tnumber : "+tripleIds.size());
			 System.out.println(sb.toString());
			 IntegerArray ia = new IntegerArray(cas, tripleIds.size());
	       	  	
	       	  	int[] intArray = new int[tripleIds.size()];
	       	  	for (int i = 0; i < intArray.length; i++) {
	       	  		intArray[i] = tripleIds.get(i);
					
				}
	       	  	ia.copyFromArray(intArray, 0, 0, intArray.length);
	       	  	disamAnno.setResult(ia);
	       	  	disamAnno.setEndTime(System.currentTimeMillis());
	       	  	disamAnno.addToIndexes();
	       	  	
	       	 
					logger.info("CollectionReader:collectionReaderDescriptor" + "|"
							+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:DISAMBIGUATION"
							+ "|" + "ProcessType:DONE" + "|" + "Data:" + text);
				
	       	 
	  	} catch (IOException ioe) {
		  ioe.printStackTrace();
	  	} catch (Exception e) {
		  e.printStackTrace();
	  	}
	}

	
}
