package kr.ac.uos.ai.uimaTest.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;


import org.apache.uima.aae.client.UimaAsBaseCallbackListener;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.IntArrayFS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.collection.EntityProcessStatus;

public class WP6CallBackListener extends UimaAsBaseCallbackListener {
	private final String			outputDirectory = "./output/";
	private final String			logFileName;
	private File					file;
	private FileWriter				writer;
	
	public WP6CallBackListener() {
		StringBuilder sb = new StringBuilder();
		sb.append(outputDirectory);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
        int mon = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
		sb.append(year).append('_').append(mon).append('_').append(day).append('_').append(hour).append('_').append(min).append('_').append(sec);
		sb.append(".log");
		logFileName = sb.toString();
		file = new File(sb.toString());
		File temp = new File(outputDirectory);
		if(!temp.exists()){
			temp.mkdirs();
		}
		try {
			file.createNewFile();
			writer = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void entityProcessComplete(CAS aCas, EntityProcessStatus aStatus) {
		
		StringBuilder sb = new StringBuilder();
		
		String text = aCas.getDocumentText();
//		AnnotationIndex<AnnotationFS> anno = aCas.getAnnotationIndex();
		Type annoType = aCas.getTypeSystem().getType(
				"kr.ac.uos.ai.uimaTest.wp6Test.cas.WP6Cas");

		AnnotationIndex<AnnotationFS> fsiter = aCas.getAnnotationIndex(annoType);
//		DummyAnnotation t = (DummyAnnotation)anno.iterator().next();
//		DummyAnnotation t = (DummyAnnotation)anno.iterator().next();
		if(!fsiter.iterator().hasNext()){
			sb.append("text:").append(text).append("\n").append("Not Processed").append('\n');
			synchronized(writer){
				try {
					writer.write(sb.toString());
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		 }
		ProcessResult result = new ProcessResult();
		for (AnnotationFS annotationFS : fsiter) {
			FeatureStructure fs = annotationFS;
			Feature name = annoType.getFeatureByBaseName("processName");
			Feature resultValue = annoType.getFeatureByBaseName("result");
			Feature startTime = annoType.getFeatureByBaseName("startTime");
			Feature endTime = annoType.getFeatureByBaseName("endTime");
			result.setResult(fs.getStringValue(name), fs.getLongValue(startTime), fs.getLongValue(endTime), (IntArrayFS)fs.getFeatureValue(resultValue));
		}
		 	 
		 sb.append("text:").append(text).append("\n");
		 sb.append(result.toString());
//		 
		 synchronized(writer){
			 System.out.println(sb.toString());
			 try {
				writer.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	@Override
	public void collectionProcessComplete(EntityProcessStatus aStatus) {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<LogLine> list = new ArrayList<LogLine>();
		File f = new File(logFileName.replace("log", "csv"));
		try {
			f.createNewFile();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine())!= null){
				if(line.contains("text:")){
					String text = line.substring(5);
					LogLine log = new LogLine(text);
					while((line = br.readLine())!= null){
						if(line.contains("Vbox")){
							break;
						}
					}
					while((line = br.readLine())!= null){
						if(!line.startsWith("\t")){
							break;
						}
						String id = line.substring(1);
						log.addID(Integer.valueOf(id));
						
					}
					list.add(log);
				}
			}
			
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
				
			try {
				con = DriverManager.getConnection("jdbc:mysql://211.109.9.80/pseudo_kbox_db?user=openkbuser&password=openkbpass");
				for (LogLine logLine : list) {
					if(logLine.hasResult()){
						StringBuilder sb = new StringBuilder();
						sb.append("(");
						for (int id : logLine.getIDList()) {
							sb.append("t_id = ").append(id).append(" or ");
						}
						sb.delete(sb.length()-4, sb.length()-1).append(')');
						String query = "SELECT * FROM quintuples WHERE "+sb.toString();
						stmt = con.createStatement();
						rs = stmt.executeQuery(query);
						while(rs.next()){
							String[] data = new String[]{String.valueOf(rs.getInt("t_id")),
									rs.getString("s"),
									rs.getString("p"),
									rs.getString("o"),
									String.valueOf(rs.getFloat("ce"))
							};
							logLine.addData(data);
						}
						rs.close();
						stmt.close();
						
					}
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
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileWriter fw = new FileWriter(f);
			for (LogLine logLine : list) {
				fw.write(logLine.toString());
			}
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
