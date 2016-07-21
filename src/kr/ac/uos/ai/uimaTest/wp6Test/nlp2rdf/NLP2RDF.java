package kr.ac.uos.ai.uimaTest.wp6Test.nlp2rdf;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import kr.ac.uos.ai.uimaTest.wp6Test.IndexNumInfo;
import kr.ac.uos.ai.uimaTest.wp6Test.Log4Anno;
import kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.IntegerArray;

import enumType.AnnotatorType;
import enumType.ProcessType;
import activemq.Receiver;
import activemq.ResourceMonitor;
import activemq.Sender;

public class NLP2RDF extends Log4Anno {

	private String text;
	private final Logger logger = Logger.getLogger(NLP2RDF.class);
	private final String HOME									= System.getenv("HOME");
	
	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		try {
			WP6Cas anno = new WP6Cas(cas);
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

			anno.setProcessName("NLP2RDF");
			anno.setStartTime(System.currentTimeMillis());

				logger.info("CollectionReader:collectionReaderDescriptor" + "|"
						+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:NLP2RDF"
						+ "|" + "ProcessType:LOAD" + "|" + "Data:" + text);
			
			StringBuilder sb = new StringBuilder();
			sb.append("NLP2RDF").append("\n");
			sb.append(cas.getDocumentText()).append("\n");

				logger.info("CollectionReader:collectionReaderDescriptor" + "|"
						+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:NLP2RDF"
						+ "|" + "ProcessType:PROCESSING" + "|" + "Data:" + text);
			
			List<Integer> docIds = new NLP2RDFAction(NLPParserConstant.ETRI).doAction(cas.getDocumentText());

			/*
			 * Log
			 */


			for (Integer docId : docIds) {
				sb.append("result : " + docId).append("\n");
			}

			IntegerArray ia = new IntegerArray(cas, docIds.size());
			System.out.println(sb.toString());

			int[] intArray = new int[docIds.size()];
			for (int i = 0; i < intArray.length; i++) {
				intArray[i] = docIds.get(i);
			}

			ia.copyFromArray(intArray, 0, 0, intArray.length);

			anno.setResult(ia);
			anno.setEndTime(System.currentTimeMillis());
			
			anno.addToIndexes();

			
				logger.info("CollectionReader:collectionReaderDescriptor" + "|"
						+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:NLP2RDF"
						+ "|" + "ProcessType:DONE" + "|" + "Data:" + text);
				
				sender.returnMessage("CollectionReader:collectionReaderDescriptor" + "|"
						+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:NLP2RDF"
						+ "|" + "CPU:" + resourceMonitor.getFreeCpu() + "|" + "MEM:" + resourceMonitor.getFreeMem() 
						+ "|" + "ProcessType:DONE" + "|" + "Data:" + text);
					

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
