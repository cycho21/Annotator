package testCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;

import edu.stanford.smi.protege.storage.database.pool.ConnectionPool;
import kr.ac.uos.ai.uimaTest.core.LogLine;

public class CountLog {
	public CountLog() {
//		File f = new File("total.log");
		
		
		
		File f = new File("logFile.log");
		ArrayList<String> data =new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			while((line = br.readLine())!=null){
				data.add(line);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> temp = null;
		ArrayList<LogData> logs = new ArrayList<LogData>();
		for (String string : data) {
			if(string.contains("text:")){
				if(temp!=null){
					logs.add(new LogData(temp));
				}
				temp = new ArrayList<String>();
			}
//			if(string.contains("text:")||string.contains("NLP")||string.contains("L2K ")||string.contains("Disambiguation")||string.contains("Bbox")||string.contains("Vbox")||string.contains("\t")){
				temp.add(string);
//			}
			
		}
		
		
		
		
		 
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://211.109.9.80/pseudo_kbox_db");
		config.setUsername("openkbuser");
		config.setPassword("openkbpass");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.setLeakDetectionThreshold(2000);
		config.setInitializationFailFast(true);;
		config.addDataSourceProperty("logWriter", new PrintWriter(System.out));
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setReadOnly(true);
		HikariDataSource ds = new HikariDataSource(config);
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
			
		try {
			TripleData triple = null;
			UniqueTriple unique = null;
			ArrayList<UniqueTriple> tList = null;
			int i = 0;
			int j = 0;
			for (LogData log : logs) {
				
				if(log.getTripleNumber()>0){
					con = ds.getConnection();
					System.out.println(i+":"+j);
					StringBuilder sb = new StringBuilder();
					sb.append("(");
					List<Integer> result = log.getVBoxResult();
					if(result.size()==0){
						result = log.getBboxResult();
					}
					if(result.size()==0){
						continue;
					}
					for (int id : result) {
						sb.append("t_id = ").append(id).append(" or ");
					}
					sb.delete(sb.length()-4, sb.length()-1).append(')');
					String query = "SELECT * FROM quintuples WHERE "+sb.toString();
					stmt = con.createStatement();
					rs = stmt.executeQuery(query);
					tList = new ArrayList<UniqueTriple>();
					boolean duplicated = false;
					while(rs.next()){
						triple = new TripleData(rs.getString("s"),rs.getString("p"),rs.getString("o"));
						triple.setConstraintEnforcementValue(rs.getFloat("ce"));
						
						for (UniqueTriple uniqueTriple : tList) {
							if(uniqueTriple.isDuplicated(triple)){
								uniqueTriple.addCount();
								duplicated = true;
								break;
							}
						}
						if(duplicated){
							duplicated = false;
							continue;
						}
						unique = new UniqueTriple(triple);
						tList.add(unique);
						
					}
					log.setTripleList(tList);
					rs.close();
					stmt.close();
					con.close();
					j++;
				}
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
				if(con!=null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		File file = new File("Result.txt");
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "CP949");
			bw = new BufferedWriter(osw);
			
			
			for (LogData logData : logs) {
				bw.write(logData.toString());
			}
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(fos!=null){
					fos.close();
				}
				if(osw!=null){
					osw.close();
				}
				if(bw!= null){
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int processed = 0;
		int noPro = 0;
		int nol2k = 0;
		int nodis = 0;
		int nobb = 0;
		int novb = 0;
		int triples = 0;
		int unique = 0;
		int ceCount = 0;
		int vBoxed = 0;
		int vBoxedUnique = 0;
		
		long sumNlp = 0;
		long sumL2K = 0;
		long sumDis = 0;
		long sumBb = 0;
		long sumVb = 0;
		int i = 0;
		for (LogData logData : logs) {
			
			sumNlp+=logData.getNlp2rdfTime();
			sumL2K+=logData.getL2kTime();
			sumDis+=logData.getDisTime();
			sumBb+=logData.getBboxTime();
			sumVb+=logData.getvBoxTime();
			
			triples += logData.getTripleNumber();
			unique += logData.getReducedTripleNumber();

			switch (logData.getErrorCode()) {
			case 0:
				processed++;
				ceCount += logData.getCEValueAssignedCount();
				vBoxed += logData.getVBoxResult().size();
				vBoxedUnique += logData.getReducedTripleNumber();

				break;
			case 1:
				noPro++;
				break;
			case 2:
				nol2k++;
				break;
			case 3:
				nodis++;
				break;
			case 4:
				nobb++;
				break;
			case 5:
				novb++;
				break;
			default:
				break;
			}
			i++;
		}
		
		System.out.println("-----------Analysis Result------------");
		System.out.println("Total : "+logs.size());
		System.out.println("NLP2RDF Return Invalid : "+ noPro);
		System.out.println("L2K No Result : " + nol2k);
		System.out.println("Disambiguation No Result : "+nodis);
		System.out.println("B-Box No Result : "+nobb);
		System.out.println("V-Box No Result : "+novb);
		System.out.println("Result : "+processed);
		System.out.println("Validated Triples : "+vBoxed);
		System.out.println("Validated Unique Triple : "+vBoxedUnique);
		System.out.println("C.E. assigned Triple : "+ceCount);
		System.out.println("Full Triples : "+triples);
		System.out.println("Unique Triples : "+unique);
		System.out.println("-----------Statistic Result------------");
		System.out.println("NLP2RDF");
		System.out.println("\tTotal Time : "+ sumNlp);
		System.out.println("\tAverage TIme : "+ (sumNlp/(logs.size()-noPro)));
		System.out.println("L2K");
		System.out.println("\tTotal Time : "+ sumL2K);
		System.out.println("\tAverage TIme : "+ (sumL2K/(logs.size()-noPro)));
		System.out.println("Disambiguation");
		System.out.println("\tTotal Time : "+ sumDis);
		System.out.println("\tAverage TIme : "+ (sumDis/(logs.size()-noPro-nol2k)));
		System.out.println("B-Box");
		System.out.println("\tTotal Time : "+ sumBb);
		System.out.println("\tAverage TIme : "+ (sumBb/(logs.size()-noPro-nol2k-nodis)));
		System.out.println("V-Box");
		System.out.println("\tTotal Time : "+ sumVb);
		System.out.println("\tAverage TIme : "+ (sumVb/(logs.size()-noPro-nol2k-nodis-nobb)));
		System.out.println("Total Time : "+(sumNlp+sumL2K+sumDis+sumBb+sumVb));
		
	}


	public static void main(String[] args) {
		new CountLog();
	}
	
}
