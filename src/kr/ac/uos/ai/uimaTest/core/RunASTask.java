package kr.ac.uos.ai.uimaTest.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.uima.UIMAFramework;
import org.apache.uima.aae.client.UimaAsynchronousEngine;
import org.apache.uima.adapter.jms.client.BaseUIMAAsynchronousEngine_impl;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import activemq.Receiver;

public class RunASTask {
	private final UimaAsynchronousEngine uimaEngine;
	private final HashMap<String, Object> applicationSetting;
	private File collectionReader;

	private final String UIMA_HOME = System.getenv("UIMA_HOME");
	private final String HOME = System.getenv("HOME");
	private final String collectionReaderFilePath = HOME + "/annotator/DummyWP6CR.xml";

	public RunASTask() {
		long time = System.currentTimeMillis();

		uimaEngine = new BaseUIMAAsynchronousEngine_impl();
		applicationSetting = new HashMap<String, Object>();
		collectionReader = new File(collectionReaderFilePath);

		init();
		System.out.println(System.currentTimeMillis() - time);
		System.exit(-1);
	}

	private void init() {
		applicationSetting.put(UimaAsynchronousEngine.DD2SpringXsltFilePath, UIMA_HOME + "/bin/dd2spring.xsl");
		applicationSetting.put(UimaAsynchronousEngine.SaxonClasspath, "file:"+ UIMA_HOME + "/saxon/saxon8.jar");

		applicationSetting.put(UimaAsynchronousEngine.ServerUri, "tcp://211.109.9.71:61616");
		applicationSetting.put(UimaAsynchronousEngine.ENDPOINT, "DummyWP6");
		// applicationSetting.put(UimaAsynchronousEngine.Timeout, 5000);
		//
		applicationSetting.put(UimaAsynchronousEngine.GetMetaTimeout, 10000);
		// applicationSetting.put(UimaAsynchronousEngine.CpcTimeout, 50000);
		//
		// // Add the Cas Pool Size and initial FS heap size
		applicationSetting.put(UimaAsynchronousEngine.CasPoolSize, 100);
//		applicationSetting.put(UIMAFramework.CAS_INITIAL_HEAP_SIZE, Integer.valueOf(2048).toString());

		uimaEngine.addStatusCallbackListener(new WP6CallBackListener());

		try {
			CollectionReaderDescription crd = UIMAFramework.getXMLParser()
					.parseCollectionReaderDescription(
							new XMLInputSource(collectionReader));

			CollectionReader cr = UIMAFramework.produceCollectionReader(crd);

			uimaEngine.setCollectionReader(cr);

		} catch (InvalidXMLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			uimaEngine.initialize(applicationSetting);
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		}

		try {
			setReceiver();
			uimaEngine.process();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		System.out.println("end");

	}

	private void setReceiver() {
		Receiver rcv = new Receiver();
		rcv.setQueueName("SysLogMsg");
		rcv.init();
		Thread rcvThread = new Thread(rcv);
		rcvThread.start();
	}

	public static void main(final String[] args) {
		new RunASTask();
	}
}
