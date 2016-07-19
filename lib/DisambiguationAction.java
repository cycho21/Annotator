package org.openkb.disambiguation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/*
def demo_disambiguation(data):
    url = 'http://'+host+':12346/request'
    payload = { "ID": [data] }
    headers = { 'X-AUTH-TOKEN': 'XXXX','content-type': 'application/json'}

    r = requests.post(url, data=json.dumps(payload), headers=headers)
    print r.text

    if r.text.find('error')>-1:
        print '** EXIT** demo_disambiguation()'
        sys.exit(-1)

    return r.text

*/

/**
 * Created by Administrator on 2015-05-09.
 */
public class DisambiguationAction {
    private static Logger logger = LoggerFactory.getLogger(DisambiguationAction.class);
    int port=12345;
    String host = "211.109.9.71";

    public DisambiguationAction() throws IOException {
        String properties = System.getProperty("user.home")+"/OPENKB/HOME/bin/openkb.properties";
        File file = new File(properties);
        if(!file.exists()) {
            throw new RuntimeException("ERROR : " +properties +"is no found!!!");
        }
        Properties prop = new Properties();
        prop.load(new FileInputStream(file));

	    port = Integer.parseInt(prop.getProperty("openkb.disambiguation.service.port"));
        host = prop.getProperty("openkb.disambiguation.service.host");
    }

    public List<Integer> doAction(List<Integer> tids) throws Exception {
        if(tids == null) {
            throw new RuntimeException("tids is null");
        }
        if(tids.size() == 0) {
            throw new RuntimeException("tids size is zero");
        }

        long startTime = new Date().getTime(); // start time

        logger.debug("input datas: {}",tids);


        String url = "http://"+host+":"+port+"/request";

        StringBuffer sb = new StringBuffer("[");
        for(Integer tid : tids) {
            sb.append(""+tid);
            if(tid != tids.get(tids.size()-1)) sb.append(",");
        }
        sb.append("]");

        String a1 = "\"ID\":"+sb.toString();
        String a2 = "\"X-AUTH-TOKEN\":\"XXXX\"";
        String a3 = "\"to_lbox\":true";

        String body = "{"+a1+","+a2+","+a3+"}";

        // REST Service 호출, docids 값을 REST Service에서 리턴함.
        HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("X-AUTH-TOKEN", "XXXXXXX")
                .body(body)
                .asJson();

        logger.debug("return value : {}",jsonResponse.getBody().toString());
        String[] tokens = jsonResponse.getBody().toString().split(":|}");
        logger.debug("tokens.length={}",tokens.length);

        if(tokens.length != 2) {
            //System.out.println(tokens[1]);
            throw new RuntimeException("ERROR : return value is not valid");
        }
        logger.debug("tokens[0]={}, tokens[1]={}",tokens[0],tokens[1]);

        // {"newID": []}
        ObjectMapper objectMapper = new ObjectMapper();
        //Integer[] a = objectMapper.readValue(tokens[1],Integer[].class);
        List<Integer> retvalTids = objectMapper.readValue(tokens[1], new TypeReference<List<Integer>>(){});

        long endTime = new Date().getTime(); // end time

        long difference = endTime - startTime; // check different

        logger.debug("Elapsed seconds: {} seconds", difference / 1000.0);

        //List<Integer> retvalTids = new ArrayList<Integer>();

        return retvalTids;
    }

    public static void main(String[] args) {
        try {
            List<Integer> docIds =
		new DisambiguationAction().doAction(Arrays.asList(4889764));//100,200,300));
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
