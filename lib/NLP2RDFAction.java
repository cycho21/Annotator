package org.openkb.nlp2rdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * Created by Administrator on 2015-05-09.
 */
public class NLP2RDFAction {
    private static Logger logger = LoggerFactory.getLogger(NLP2RDFAction.class);
    int port=12345;
    String host = "211.109.9.71";
    NLPParserConstant parser;

    public NLP2RDFAction(NLPParserConstant parser) throws IOException {
        if(parser == null) {
            throw new RuntimeException("ERROR : parser is null");
        }
        if(parser == NLPParserConstant.SALTLUX) {
            throw new RuntimeException("ERROR : SALTLUX parser not yet supported!!!");
        }

        this.parser = parser;

        String properties = System.getProperty("user.home")+"/OPENKB/HOME/bin/openkb.properties";
        File file = new File(properties);
        if(!file.exists()) {
            throw new RuntimeException("ERROR : " +properties +"is no found!!!");
        }
        Properties prop = new Properties();
        prop.load(new FileInputStream(file));

        port = Integer.parseInt(prop.getProperty("openkb.nlp2rdf.service.port"));
        host = prop.getProperty("openkb.nlp2rdf.service.host");
    }

    public List<Integer> doAction(String sentence) throws Exception {
        if(sentence == null) {
            throw new RuntimeException("ERROR : sentence is null");
        }
        if(sentence.length() == 0) {
            throw new RuntimeException("ERROR : sentence length is zero");
        }

        long startTime = new Date().getTime(); // start time

        logger.debug("sentence : {}, nlp parser : {}",sentence,parser);
        List<Integer> docIds = new ArrayList<Integer>();

        String url = "http://"+host+":"+port+"/request";

        String a1 = "\"sentence\":\""+sentence+"\"";
        String a2 = "\"X-AUTH-TOKEN\":\"XXXX\"";
        String a3 = "\"to_lbox\":true";

        String body = "{"+a1+","+a2+","+a3+"}";

        for (int retries = 0; ; retries++) {
            try {
                // REST Service 호출, docids 값을 REST Service에서 리턴함.
                HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("X-AUTH-TOKEN", "XXXXXXX")
                        .body(body)
                        .asJson();

                logger.debug("return value : {}", jsonResponse.getBody().toString());
                String[] tokens = jsonResponse.getBody().toString().split(":|}");
                logger.debug("tokens.length={}", tokens.length);

                if (tokens.length != 2) {
                    //System.out.println(tokens[1]);
                    throw new RuntimeException("ERROR : return value is not valid");
                }
                docIds.add(Integer.parseInt(tokens[1]));
                //System.out.println(jsonResponse.getBody().isArray());

                long endTime = new Date().getTime(); // end time

                long difference = endTime - startTime; // check different

                logger.debug("Elapsed seconds: {} seconds", difference / 1000.0);

                return docIds;
            } catch (UnirestException ue) {
                if (retries < 3) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch(InterruptedException ex) {
                        //Thread.currentThread().interrupt();
                    }
                    continue;
                } else {
                    throw ue;
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] sentences=new String[]{
           "RDF는 자원의 정보를 표현하고 상호 연결 및 교환하기 위하여 월드 와이드 웹 컨소시움(W3C)에 의해 개발되고 있는 언어 규격이다",
           "여기서 말하는 자원이란 웹상에 존재하는 대부분의 객체를 의미하는 것으로, 사람이나 웹페이지, 저작물과 같은 특정지을 수 있는 개체들이 해당된다",
           "RDF는 이러한 자원들의 메타데이터를 표현하기 위하여 개발된 규격으로, 예를 들어 웹페이지라면 제목, 저자, 수정날짜와 같은 정보가 RDF로 서술하는 대상이 된다",
           "RDF로 서술하는 자원은 반드시 웹상에서 식별가능(identified)하여야 한다",
           "(하지만 반드시 당장 데이터를 받아올 수 있어야 하는 상태일 필요는 없다)", 
           "이러한 특징은 RDF로 서술한 특정자원의 정보가 다수 존재하는 경우 상호간의 정보가 동일함을 식별하기 위해서인데, 이것은 RDF의 목적이 서술한 자원정보를 상호 연결하여 해석할 수 있는 시멘틱 웹 기술에 부합하는 것이기 때문이다",
           "실제로는 세 부분으로 된 단순한 모델인데, 1999년 2월 W3C의 권고안이 되었다",
           "더블린코어와는 달리, 포함해야 할 개별요소들을 명시하지 않는 게 특징",
           "이것은 이용자들이 필요에 따라 구조 내에서 세부사항을 선택하고 정의하도록 한 것이기 때문에 메타데이터 가공을 위한 기초가 된다",
           "전달 신텍스로 XML을 쓴다",
           "예를 들면, 웹페이지에 관한 데이터는 주제, 부제, 작성일자, 저자 등으로 나뉘어질 수 있는데, 이러한 데이터를 XML 태그에 의해 지시될 수 있는 필드 내에 넣으면, 검색엔진을 통해 보다 훌륭한 검색을 할 수 있게 된다",
           "RDF의 특징은 웹자원의 자동화 처리에 초점을 두고 있는데, 예를 들면, 날짜는 텍스트로 보기보다는 그냥 날짜로 인식하는 것을 말한다"
        };
//        try {
//        	for(int i=0; i< sentences.length; i++) {
//	            List<Integer> docIds =
//	                    //new NLP2RDFAction(NLPParserConstant.ETRI).doAction("커트 코베인은 1967년 미국 워싱턴주 애버딘에서 태어났다" );
//	                    new NLP2RDFAction(NLPParserConstant.ETRI).doAction(sentences[i]);
//	            for(Integer docId : docIds) {
//	                System.out.println("result : "+docId);
//	            }
//          }
//      } catch (Exception e) {
//            e.printStackTrace();
//      }
//    
        for (int ii = 0; ii < sentences.length; ii++) {
            for (int i = 0; i < sentences.length; i++) {
                Runnable r = new TestRunnable(sentences[i]);
                Thread t = new Thread(r);
                t.start();
            }
        }
    }
}
