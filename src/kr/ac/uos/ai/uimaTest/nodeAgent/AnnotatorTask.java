package kr.ac.uos.ai.uimaTest.nodeAgent;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import static kr.ac.uos.ai.uimaTest.nodeAgent.ContextConfiguration.*;


public class AnnotatorTask {
	private final String							name;
	private final boolean							hasRemote;
	private final ArrayList<String>				remoteTask;
	private final String							deploymentDescriptionXMLPath;
	private final String							annotatorDescriptionXMLPath;
//	private final String							annotatorJarPath;
	
	public AnnotatorTask(String descPath) throws Exception{
		this.remoteTask = new ArrayList<String>();
		this.deploymentDescriptionXMLPath = DescriptionPath+descPath;
		File f = new File(deploymentDescriptionXMLPath);
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
		Document d = db.parse(f);
		this.name = d.getElementsByTagName("name").item(0).getTextContent();
		this.annotatorDescriptionXMLPath = DescriptionPath + d.getElementsByTagName("import").item(0).getAttributes().getNamedItem("location").getTextContent();
		NodeList list = d.getElementsByTagName("remoteAnalysisEngine");
		for(int i = 0; i< list.getLength(); i++){
			String remoteTaskName = list.item(i).getAttributes().getNamedItem("key").getNodeValue();
			remoteTask.add(remoteTaskName);
		}
		if(remoteTask.size()>0){
			hasRemote = true;
		}else{
			hasRemote = false;
		}
		d = db.parse(annotatorDescriptionXMLPath);
		
		
	}
	

	public boolean hasRemote() {
		return hasRemote;
	}


	public String getName() {
		return name;
	}
	
	
//
//	public boolean isValidTask(){
//		File f = new File(deploymentDescriptionXMLPath);
//		if(!f.exists()){
//			return false;
//		}else{
//			try {
//				Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
//				NodeList list = d.getElementsByTagName("remoteAnalysisEngine");
//				boolean matched;
//				for(int i = 0; i< list.getLength(); i++){
//					matched = false;
//					String remoteTaskName = list.item(i).getAttributes().getNamedItem("key").getNodeValue();
//					for (AnnotatorTask annotatorTask : remoteTask) {
//						if(annotatorTask.getName().equals(remoteTaskName)){
//							matched = annotatorTask.isValidTask();
//							break;
//						}
//					}
//					if(!matched){
//						return false;
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return true;
//	}

	
	public static void main(String[] args) {
		try {
			AnnotatorTask at = new AnnotatorTask("./desc/dummyWP6/DummyWP6Deployment.xml");
			System.out.println(at.getName());
			System.out.println(at.hasRemote());
			for (String string : at.getRemoteTask()) {
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public ArrayList<String> getRemoteTask() {
		return remoteTask;
	}


	public String getDeploymentDescriptionXMLPath() {
		return deploymentDescriptionXMLPath;
	}


	public String getAnnotatorDescriptionXMLPath() {
		return annotatorDescriptionXMLPath;
	}
}
