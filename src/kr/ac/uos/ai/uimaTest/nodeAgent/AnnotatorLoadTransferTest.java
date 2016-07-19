package kr.ac.uos.ai.uimaTest.nodeAgent;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.BlobMessage;

public class AnnotatorLoadTransferTest {

	public AnnotatorLoadTransferTest() {
		ActiveMQConnectionFactory fac = new ActiveMQConnectionFactory("tcp://211.109.9.71:61616");
		Connection connection;
//		try {
//			connection = fac.createConnection();
//			connection.start();
//			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//			
//			Destination destination = session.createQueue("Test");
//			
//			MessageProducer pro = session.createProducer(destination);
//			pro.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//			
//			BytesMessage bm = session.createBytesMessage();
//			while(true){
//				System.out.println("Send : " + message);
//				pro.send(message);
//				Thread.sleep(1000);
//				
//				
//			}
//			
//		} catch (JMSException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
