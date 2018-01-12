package activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.spring.ActiveMQConnectionFactory;

public class MainActiveMQPublisher {

	public static void main(String[] args) throws JMSException {
		
		String user = "admin";
		String password = "password";
		String host = "localhost";
		int port = 5672;

		String connectionURI = "amqp://" + host + ":" + port;
		
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		
		activeMQConnectionFactory.setBrokerURL(connectionURI);
		
		ActiveMQConnection connection = (ActiveMQConnection) activeMQConnectionFactory.createConnection(user, password);
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination destination = session.createQueue("dist");
		
		MessageProducer messageProducer = session.createProducer(destination);
		
//		messageProducer.send(message);
		
		
	}

}
