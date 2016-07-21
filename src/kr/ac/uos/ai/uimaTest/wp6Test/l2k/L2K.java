package kr.ac.uos.ai.uimaTest.wp6Test.l2k;

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
import org.openkb.l2k.L2KAction;

import activemq.Receiver;
import activemq.ResourceMonitor;
import activemq.Sender;
import enumType.AnnotatorType;
import enumType.ProcessType;

public class L2K extends Log4Anno {

	private String text;
	private final Logger logger = Logger.getLogger(L2K.class);
	private final String HOME									= System.getenv("HOME");
	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		 try {
			 WP6Cas l2kAnno = new WP6Cas(cas);
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
			 
			Sender sender = new Sender();
			sender.init();
			sender.createQueue("main");
			
			ResourceMonitor resourceMonitor = new ResourceMonitor();
			resourceMonitor.init();
				
			 l2kAnno.setStartTime(System.currentTimeMillis());
			 l2kAnno.setProcessName(L2K);
			 
			 
					logger.info("CollectionReader:collectionReaderDescriptor" + "|"
							+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:L2K"
							+ "|" + "ProcessType:LOAD" + "|" + "Data:" + text);
				
				
			 
			 System.out.println("L2K start");
			 StringBuilder sb = new StringBuilder();
			 
			 AnnotationIndex<Annotation> anno = cas.getAnnotationIndex(WP6Cas.type);
			 sb.append(L2K).append("\n");
			 WP6Cas t = null;
			 for (Annotation annotation : anno) {
				 if(annotation instanceof WP6Cas){
					WP6Cas temp = (WP6Cas) annotation;
					if(temp.getProcessName().equals(Nlp2Rdf)){
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
			 ArrayList<Integer> list = new ArrayList<Integer>();
			 int[] array = ir.toArray();
			 for (int data : array) {
				 sb.append("input : " +data).append("\n");
				 list.add(Integer.valueOf(data));
			}
			 if(list.size()==0){
				 System.out.println("no Input");
				 return;
			 }
			 List<Integer> tripleIds =  new L2KAction().doAction(list);
			 
			 
					logger.info("CollectionReader:collectionReaderDescriptor" + "|"
							+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:L2K"
							+ "|" + "ProcessType:PROCESSING" + "|" + "Data:" + text);
				
				
			 sb.append("result").append("\n");
			 sb.append("\tnumber : "+tripleIds.size());
			 System.out.println(sb.toString());
			 
			 IntegerArray ia = new IntegerArray(cas, tripleIds.size());
	       	  	
	       	  	int[] intArray = new int[tripleIds.size()];
	       	  	for (int i = 0; i < intArray.length; i++) {
	       	  		intArray[i] = tripleIds.get(i);
					
				}
	       	  	ia.copyFromArray(intArray, 0, 0, intArray.length);
	       	  	l2kAnno.setResult(ia);
	       	  	l2kAnno.setEndTime(System.currentTimeMillis());
	       	  	
	       	  	l2kAnno.addToIndexes();
	       	  	
	       	 
					logger.info("CollectionReader:collectionReaderDescriptor" + "|"
							+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:L2K"
							+ "|" + "ProcessType:DONE" + "|" + "Data:" + text);
					
					sender.returnMessage("CollectionReader:collectionReaderDescriptor" + "|"
							+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:L2K"
							+ "|" + "CPU:" + resourceMonitor.getFreeCpu() + "|" + "MEM:" + resourceMonitor.getFreeMem() 
							+ "|" + "ProcessType:DONE" + "|" + "Data:" + text);
					
	       	  	
	     } catch (IOException ioe) {
			 ioe.printStackTrace();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
}
