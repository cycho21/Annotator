package kr.ac.uos.ai.uimaTest.nodeAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class UimaNodeAgent {
	private HashMap<String, AnnotatorLoader>					annoLoaderMap;

	public void annotatorMessageRecieved(AnnotatorMessage annoMessage) {
		String className = annoMessage.getClassName();
		String fileName = annoMessage.getFileName();
		byte[] data = annoMessage.getData();
		File f = new File("./annotator/class/"+fileName);
		
		try {
			if(f.exists()){
				f.delete();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(data);
			fos.flush();
			fos.close();
			URLClassLoader urlCL = new URLClassLoader(new URL[]{f.toURI().toURL()}, System.class.getClassLoader());
			Class<AnnotatorLoader> loaderClass = (Class<AnnotatorLoader>) urlCL.loadClass(className);
			AnnotatorLoader anno = loaderClass.newInstance();
			annoLoaderMap.put(anno.getName(), anno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
