package kr.ac.uos.ai.uimaTest.nodeAgent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class AnnotatorMessage extends UimaPlusMessage {
	private final String 					fileName;
	private final String					className;
	private final byte[]					fileData;

	public AnnotatorMessage(String fileName, String className, byte[] data) {
		super(UimaPlusMessageType.AnnotatorMessage);
		this.fileName = fileName;
		this.className = className;
		this.fileData = data; 
	}


	public String getFileName() {
		return fileName;
	}

	public String getClassName() {
		return className;
	}

	public byte[] getData() {
		return fileData;
	}
}