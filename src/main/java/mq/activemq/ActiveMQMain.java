package mq.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class ActiveMQMain {

	public static void main(String[] args) throws JMSException {
		
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		
		ActiveMQConnection connection = (ActiveMQConnection) activeMQConnectionFactory.createConnection();
		
		Session	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Queue queue = session.createQueue("newevent11");
		
//		Topic topic = session.createTopic("eventTopic");
		
		MessageProducer messageProducer = session.createProducer(queue);
		
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
		
		activeMQTextMessage.setText("Xi, also general secretary of the Communist Party of China Central Committee, made the remark in a letter to entrepreneurs taking part in the poverty alleviation campaign, in which private businesses are encouraged to extend a helping hand to thousands of economically struggling villages.");
		
		messageProducer.send(activeMQTextMessage);
		
//		connection.close();

	}

}
