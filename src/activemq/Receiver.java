package activemq;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import kr.ac.uos.ai.uimaTest.wp6Test.IndexNumInfo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.*;

public class Receiver implements Runnable {

	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private String queueName;
	private final String HOME									= System.getenv("HOME");
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	private final Logger logger = Logger.getLogger(Receiver.class);
	private Queue queue;
	private MessageConsumer consumer;
	private Message message;
	private TextMessage tMsg;
	private InetAddress ip;
	private String ipAdrs;

	public TextMessage gettMsg() {
		return tMsg;
	}

	public void settMsg(TextMessage tMsg) {
		this.tMsg = tMsg;
	}

	public Receiver() {
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		ipAdrs = ip.getHostAddress();
	}


	private void consume() {
		try {
			consumer = session.createConsumer(queue);
			message = consumer.receive();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		try {
			while(true){
				consume();
				tMsg = (TextMessage) message;
				
				String parsedString = parse(tMsg.getText());
				String[] parsed = tMsg.getText().split("\\|");
//				casIndex = IndexNumInfo.indexMap.get(parsedString);
//				inputFile = IndexNumInfo.transactionMap.get(parsedString);
//				System.out.println("parsed string : " + parsedString);
//				logger.info("InputFile:" + inputFile + "|"
//				+ "CollectionReader:" + collectionReaderName + "|"
//				+ parsed[0] + "|" + parsed[1] + "|" + parsed[2] + "|" + parsed[3] + "|casIndex:" + casIndex
//				+ "|" + "Data:"+parsedString);
				if(parsed[0].equals("IP:"+ipAdrs)){
						logger.info("CollectionReader:collectionReaderDescriptor"+ "|"
						+ parsed[0] + "|" + parsed[1] + "|" + parsed[2] + "|" + parsed[3]
						+ "|" + "Data:"+parsedString);
				}
			}
			
		} catch (Exception e) {
		}
		
	}

	private String parse(String string) {
		String parsedString = string.split("Data:")[1].trim();
		return parsedString;
	}

	public void init() {
		PropertyConfigurator.configure(HOME+"/log4j.properties");
		System.out.println(HOME+"/log4j.properties");
		factory = new ActiveMQConnectionFactory("tcp://211.109.9.71:61616");
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
