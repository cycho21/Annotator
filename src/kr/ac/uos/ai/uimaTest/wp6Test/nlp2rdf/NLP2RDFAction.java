package kr.ac.uos.ai.uimaTest.wp6Test.nlp2rdf;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
                if (retries < 5) {
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
        try {
            List<Integer> docIds =
                    new NLP2RDFAction(NLPParserConstant.ETRI).doAction("커트 코베인은 1967년 미국 워싱턴주 애버딘에서 태어났다" );
            for(Integer docId : docIds) {
                System.out.println("result : "+docId);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
