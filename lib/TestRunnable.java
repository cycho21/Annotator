package org.openkb.nlp2rdf;

import java.io.IOException;
import java.util.List;

public class TestRunnable implements Runnable {
	String sentence = null;
	public TestRunnable(String sentence) {
		this.sentence = sentence;
	}

	@Override
	public void run() {
        List<Integer> docIds = null;
		try {
			docIds = //new NLP2RDFAction(NLPParserConstant.ETRI).doAction("커트 코베인은 1967년 미국 워싱턴주 애버딘에서 태어났다" );
			new NLP2RDFAction(NLPParserConstant.ETRI).doAction(sentence);
            for(Integer docId : docIds) {
                System.out.println("result : "+docId);
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
