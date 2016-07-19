package kr.ac.uos.ai.uimaTest.wp6Test.bBox;

import static kr.ac.uos.ai.uimaTest.wp6Test.Configuration.*;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.uos.ai.uimaTest.wp6Test.Log4Anno;
import kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.openkb.bbox.BBoxAction;

import activemq.Receiver;
import activemq.ResourceMonitor;
import activemq.Sender;
import enumType.AnnotatorType;
import enumType.ProcessType;

public class B_Box extends Log4Anno {

	private String text;
	private final Logger logger = Logger.getLogger(B_Box.class);
	private final String HOME									= System.getenv("HOME");
	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
			
		Sender sender = new Sender();
		sender.init();
		sender.createQueue("main");
		
		ResourceMonitor resourceMonitor = new ResourceMonitor();
		resourceMonitor.init();
			
		try {
			WP6Cas bboxAnno = new WP6Cas(cas);
			PropertyConfigurator.configure(HOME+"/log4j.properties");
			text = cas.getDocumentText();
			String pid = ManagementFactory.getRuntimeMXBean().getName()
					.split("@")[0];
			InetAddress ip = null;
			try {
				ip = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			String ipAdrs = ip.getHostAddress();

			init();

			bboxAnno.setStartTime(System.currentTimeMillis());
			bboxAnno.setProcessName(BBox);

			
				logger.info("CollectionReader:collectionReaderDescriptor" + "|"
						+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:BBOX"
						+ "|" + "ProcessType:LOAD" + "|" + "Data:" + text);
			

			System.out.println("B-box start");
			StringBuilder sb = new StringBuilder();
			sb.append(BBox).append("\n");

			AnnotationIndex<Annotation> anno = cas
					.getAnnotationIndex(WP6Cas.type);
			ArrayList<Integer> list = new ArrayList<Integer>();

			WP6Cas t = null;
			for (Annotation annotation : anno) {
				if (annotation instanceof WP6Cas) {
					WP6Cas temp = (WP6Cas) annotation;
					if (temp.getProcessName().equals(Disambiguation)) {
						t = temp;
					}
				}
			}
			if (t == null) {
				System.out.println("no Annotation");
				return;
			}

			IntegerArray ir = t.getResult();
			if (ir == null) {
				for (Annotation annotation : anno) {
					if (annotation instanceof WP6Cas) {
						WP6Cas temp = (WP6Cas) annotation;
						if (temp.getProcessName().equals(L2K)) {
							ir = temp.getResult();
						}
					}
				}

				if (ir == null) {
					System.out.println("no Input");
					return;
				}
			}
			int[] array = ir.toArray();

			for (int data : array) {
				sb.append("input : " + data).append("\n");
				list.add(Integer.valueOf(data));
			}
			if (list.size() == 0) {
				System.out.println("no Input");
				return;
			}

		
				logger.info("CollectionReader:collectionReaderDescriptor" + "|"
						+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:BBOX"
						+ "|" + "ProcessType:PROCESSING" + "|" + "Data:" + text);
			

			List<Integer> tripleIds = new BBoxAction().doAction(list);

			sb.append("result").append("\n");
			sb.append("\tnumber : " + tripleIds.size());
			System.out.println(sb.toString());
			IntegerArray ia = new IntegerArray(cas, tripleIds.size());

			int[] intArray = new int[tripleIds.size()];
			for (int i = 0; i < intArray.length; i++) {
				intArray[i] = tripleIds.get(i);

			}
			ia.copyFromArray(intArray, 0, 0, intArray.length);
			bboxAnno.setResult(ia);
			bboxAnno.setEndTime(System.currentTimeMillis());

			bboxAnno.addToIndexes();

			
				logger.info("CollectionReader:collectionReaderDescriptor" + "|"
						+ "PID:" + pid + "|" + "IP:" + ipAdrs + "|" + "AnnotatorType:BBOX"
						+ "|" + "ProcessType:DONE" + "|" + "Data:" + text);
			

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
