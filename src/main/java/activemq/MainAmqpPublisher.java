package activemq;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;

/**
 * 发送的客户端
 * amqp使用队列发送的时候，如果客户端设置持久化，
 * 则服务器会保留消息，直到接收方上线后发送
 * 
 * @author cango
 *
 */
public class MainAmqpPublisher {

	public static void main(String[] args) throws Exception {
		
		long currentTimeMillis = System.currentTimeMillis();

		final String TOPIC_PREFIX = "topic://";

		String user = "system";
		String password = "password";
		String host = "39.107.103.45";
		int port = 5672;

		String connectionURI = "amqp://" + host + ":" + port;
		String destinationName = "topic://event";

//		String destinationName = "slimsmart.queue.test";
		
		int messages = 2000;
		int size = 256;

		String DATA = "amqp publish";
		String body = "";
		for (int i = 0; i < size; i++) {
			body += DATA.charAt(i % DATA.length());
		}

		JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);

		Connection connection = factory.createConnection(user, password);
		
		connection.start();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		Destination destination = null;
		if (destinationName.startsWith(TOPIC_PREFIX)) {
			destination = session.createTopic(destinationName
					.substring(TOPIC_PREFIX.length()));
		} else {
			destination = session.createQueue(destinationName);
		}
		
		MessageProducer producer = session.createProducer(destination);
		
		//设置持久化,选择持久化则服务器会保留消息，直到接收方上线
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		
		for (int i = 1; i <= messages; i++) {
			TextMessage msg = session.createTextMessage("#:" + i+"\t"+body);
			msg.setIntProperty("id", i);
			producer.send(msg);
			if ((i % 100) == 0) {
				System.out.println(String.format("Sent %d messages", i));
			}
		}
		
		long now = System.currentTimeMillis();
		
		System.out.println("cost:"+(now-currentTimeMillis)/1000+"\tseconds");
		
		Thread.sleep(1000 * 3);
		
		producer.send(session.createTextMessage("SHUTDOWN"));
		
		
		Thread.sleep(1000 * 3);
		
	
		
		connection.close();
		System.exit(0);
	}


}
