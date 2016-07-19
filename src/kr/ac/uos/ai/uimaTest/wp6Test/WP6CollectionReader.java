package kr.ac.uos.ai.uimaTest.wp6Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;


public class WP6CollectionReader extends CollectionReader_ImplBase{
	private ArrayList<String>						inputList;
	private int										index;
	
	private final String UIMA_HOME								= "/opt/exobrain/apache-uima-as-2.6.0/";
	private final String HOME									= System.getenv("HOME");
	
	public WP6CollectionReader() {
		inputList = new ArrayList<String>();
		index = 0;
		readyForInputData();
	}
	
	private void readyForInputData() {
		
		File f = new File(HOME+"/input/input.data");
		String readData = null;
		ArrayList<String> strings = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null;
			while((line = br.readLine())!=null){
				inputList.add(line.trim());
				IndexNumInfo.getMap().put(inputList.get(inputList.size()-1), inputList.size()-1);
				IndexNumInfo.getTransactionMap().put(inputList.get(inputList.size()-1), f.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void getNext(CAS cas) throws IOException, CollectionException {
		cas.setDocumentText(inputList.get(index));
		index++;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Progress[] getProgress() {
		return new Progress[]{new ProgressImpl(index, inputList.size(), Progress.ENTITIES)};

	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return index<inputList.size();
	}
	
}
