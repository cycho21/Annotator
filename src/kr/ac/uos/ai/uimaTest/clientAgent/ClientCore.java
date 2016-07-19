package kr.ac.uos.ai.uimaTest.clientAgent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.kahadb.util.ByteArrayOutputStream;

import kr.ac.uos.ai.uimaTest.nodeAgent.AnnotatorTask;

public class ClientCore {
	private AnnotatorTask 						task;
	public ClientCore() {
		try {
			task = new AnnotatorTask("NLP2RDFAnnotator.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendTask(task);
	}
	private void sendTask(AnnotatorTask task) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			zos.putNextEntry(new ZipEntry(task.getDeploymentDescriptionXMLPath()));
			zos.write(getDeploymentDescription(task).getBytes());
			zos.closeEntry();
			
			zos.putNextEntry(new ZipEntry(task.getAnnotatorDescriptionXMLPath()));
			zos.write(getAnnotatorDescription(task).getBytes());
			zos.closeEntry();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.getAnnotatorDescriptionXMLPath();
		String deployment = getDeploymentDescription(task);
		System.out.println(deployment);
		String annoDesc = getAnnotatorDescription(task);
		System.out.println(annoDesc);
		byte[] jarData = getTaskJar(task);
		
		
	}
	private byte[] getTaskJar(AnnotatorTask task) {
		return null;
	}
	private String getAnnotatorDescription(AnnotatorTask task) {
		return readFileString(task.getAnnotatorDescriptionXMLPath());
	}
	private String getDeploymentDescription(AnnotatorTask task) {
		return readFileString(task.getDeploymentDescriptionXMLPath());
	}
	private String readFileString(String dataPath) {
		File f = new File(dataPath);
		String data = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
				sb.append(line).append('\n');
			}
			data = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	public static void main(String[] args) {
		new ClientCore();
	}

}
