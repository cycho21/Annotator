package testCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Random;

import kr.ac.uos.ai.uimaTest.core.LogLine;


public class TestCode {

	public TestCode() {
//		ArrayList<LogLine> list = new ArrayList<LogLine>();
//		File file = new File ("total.log");
//		File f = new File("resultTotal.txt");
//		try {
//			f.createNewFile();
//			FileReader fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			String line = null;
//			while((line = br.readLine())!= null){
//				if(line.contains("text:")){
//					String text = line.substring(5);
//					LogLine log = new LogLine(text);
//					while((line = br.readLine())!= null){
//						if(line.contains("Vbox")){
//							break;
//						}
//					}
//					while((line = br.readLine())!= null){
//						if(!line.startsWith("\t")){
//							break;
//						}
//						String id = line.substring(1);
//						log.addID(Integer.valueOf(id));
//						
//					}
//					list.add(log);
//				}
//			}
//			
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
				
			try {
//				con = DriverManager.getConnection("jdbc:mysql://211.109.9.80/pseudo_kbox_db?user=openkbuser&password=openkbpass");
				con = DriverManager.getConnection("jdbc:mysql://211.109.9.80/pseudo_kbox_db?user=openkbuser&password=openkbpass");
//				int i = 0;
//				for (LogLine logLine : list) {
//					i++;
//					System.out.println(i);
//					if(logLine.hasResult()){
						StringBuilder sb = new StringBuilder();
						sb.append("(");
						String[] temp = new String[]{"5262200"};
//						for (int id : logLine.getIDList()) {
						for (String id : temp) {
							sb.append("t_id = ").append(id).append(" or ");
						}
						sb.delete(sb.length()-4, sb.length()-1).append(')');
//						String query = "SELECT * FROM quintuples WHERE "+sb.toString();
						String query = "SHOW TABLES";
						stmt = con.createStatement();
						rs = stmt.executeQuery(query);
						while(rs.next()){
							for(int i = 1; i<=rs.getMetaData().getColumnCount(); i++){
								System.out.print(rs.getMetaData().getColumnLabel(i));
								System.out.println(" "+rs.getMetaData().getColumnTypeName(i));
							}
							System.out.println(rs.getString("Tables_in_pseudo_kbox_db"));
//							String[] data = new String[]{String.valueOf(rs.getInt("t_id")),
//									rs.getString("s"),
//									rs.getString("p"),
//									rs.getString("o"),
//									String.valueOf(rs.getFloat("ce"))
//							};
//							for (String string : data) {
//								System.out.print(string+'\t');
//								
//							}
//							System.out.println();
//							logLine.addData(data);
						}
						rs.close();
						stmt.close();
						
//					}
//				}
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
//			FileWriter fw = new FileWriter(f);
//			for (LogLine logLine : list) {
//				fw.write(logLine.toString());
//			}
//			fw.close();
			
		}
//		
		
		
	
	public static void main(String[] args) {
		new TestCode();
	}
}
