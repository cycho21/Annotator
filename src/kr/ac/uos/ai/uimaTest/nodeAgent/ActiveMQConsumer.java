package kr.ac.uos.ai.uimaTest.nodeAgent;
	
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQConsumer implements ExceptionListener {

	
	public ActiveMQConsumer() {
		ActiveMQConnectionFactory fac = new ActiveMQConnectionFactory("tcp://211.109.9.71:61616");
		Connection connection;
		try {
			connection = fac.createConnection();
			connection.start();
			connection.setExceptionListener(this);
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("Test");
			
			MessageConsumer con = session.createConsumer(destination);
			while(true){
				Message message = con.receive();
				System.out.println(message);
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ActiveMQConsumer();
	}

	@Override
	public void onException(JMSException arg0) {
		
		
	}
}
