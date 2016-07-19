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
import static kr.ac.uos.ai.uimaTest.nodeAgent.ContextConfiguration.*;

public class JMSMessageAdaptor implements MessageListener{
	private Session							jmsSession;
	
	private UimaNodeAgentMessageDispatcher dispatcher;
	
	public JMSMessageAdaptor(String agentName, UimaNodeAgentMessageDispatcher dispatcher) {
		this.dispatcher = dispatcher;
		ActiveMQConnectionFactory fac = new ActiveMQConnectionFactory(JMSBrokerURL);
		Connection connection;
		try {
			connection = fac.createConnection();
			connection.start();
			jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = jmsSession.createQueue(agentName);
			MessageConsumer con = jmsSession.createConsumer(destination);
			con.setMessageListener(this);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String reciever, Message msg){
		try {
			Destination destination = jmsSession.createQueue(reciever);
			MessageProducer producer = jmsSession.createProducer(destination);
			producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onMessage(Message arg0) {
		dispatcher.dispatch(arg0);
		
	}
	
}
