/**
 * 
 */
package mq.activemq;


import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

/**
 * stomp publisher
 * @author cango
 *
 */
public class MainStompPublisher {
	
	 public static void main(String []args) throws JMSException {
		 
		 	long currentTimeMillis = System.currentTimeMillis();

	        String user = "system";
	        String password = "password";
	        String host = "39.107.103.45";
	        int port = 61613;
	        String destination = "/topic/event";

	        int messages = 2000;
	        int size = 256;

	        String DATA = "abcdefghijklmnopqrstuvwxyz";
	        String body = "";
	        for( int i=0; i < size; i ++) {
	            body += DATA.charAt(i%DATA.length());
	        }

	        StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
	        factory.setBrokerURI("tcp://" + host + ":" + port);

	        Connection connection = factory.createConnection(user, password);
	        connection.start();
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Destination dest = new StompJmsDestination(destination);
	        MessageProducer producer = session.createProducer(dest);
	        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

	        for( int i=1; i <= messages; i ++) {
	            TextMessage msg = session.createTextMessage(body);
	            msg.setIntProperty("id", i);
	            producer.send(msg);
	            if( (i % 100) == 0) {
	                System.out.println(String.format("Sent %d messages", i));
	            }
	        }

	    	long now = System.currentTimeMillis();
			
			System.out.println("cost:"+(now-currentTimeMillis)/1000+"\tseconds");
	        
	        producer.send(session.createTextMessage("SHUTDOWN"));
	        connection.close();

	    }

}
