package kr.ac.uos.ai.uimaTest.nodeAgent;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQProducer implements MessageListener {
	
	public ActiveMQProducer() {
		ActiveMQConnectionFactory fac = new ActiveMQConnectionFactory("tcp://211.109.9.71:61616");
		Connection connection;
		try {
			connection = fac.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("Test");
			
			MessageProducer pro = session.createProducer(destination);
			pro.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			String text = "Hellow world!";
			TextMessage message = session.createTextMessage(text);
			
			MessageConsumer con = session.createConsumer(destination);
			con.setMessageListener(this);
			while(true){
				System.out.println("Send : " + message);
				pro.send(message);
				Thread.sleep(1000);
				
				
			}
			
		} catch (JMSException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ActiveMQProducer();
	}

	@Override
	public void onMessage(Message arg0) {
		System.out.println("Message Recieved");
		
	}
}
