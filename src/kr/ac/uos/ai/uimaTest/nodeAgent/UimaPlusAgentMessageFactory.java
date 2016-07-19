package kr.ac.uos.ai.uimaTest.nodeAgent;

import java.io.IOException;
import java.io.InputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.BlobMessage;

public class UimaPlusAgentMessageFactory {
	private static UimaPlusAgentMessageFactory 				instance;
	static{
		instance = new UimaPlusAgentMessageFactory();
	}
	private UimaPlusAgentMessageFactory() {}
	
	public UimaPlusMessage castUimaPlusAgentMessage(Message m){
		try {
			UimaPlusMessageType type = null;
			String typeString = m.getStringProperty("MessageType");
			type = UimaPlusMessageType.valueOf(typeString);
			switch (type) {
			case AnnotatorMessage:
				BytesMessage bm = (BytesMessage)m;
				String fileName = bm.getStringProperty("FileName");
				String className = bm.getStringProperty("ClassName");
				byte[] data = new byte[(int)bm.getBodyLength()];
				bm.readBytes(data);
				return new AnnotatorMessage(fileName, className, data);
			case ExcuteMessage:
				break;
			case ProfileReportMessage:
				break;
			case StateConfirmationMessage:
				break;
			case TaskResultMessage:
				break;
			default:
				break;
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static UimaPlusAgentMessageFactory getInstance() {
		return instance;
	}
	
	
	

}
