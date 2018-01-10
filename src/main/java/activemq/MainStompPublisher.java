/**
 * 
 */
package activemq;


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

	        String user = "admin";
	        String password = "password";
	        String host = "localhost";
	        int port = 61613;
	        String destination = "/topic/event";

	        int messages = 10000;
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
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	        for( int i=1; i <= messages; i ++) {
	            TextMessage msg = session.createTextMessage(body);
	            msg.setIntProperty("id", i);
	            producer.send(msg);
	            if( (i % 1000) == 0) {
	                System.out.println(String.format("Sent %d messages", i));
	            }
	        }

	        producer.send(session.createTextMessage("SHUTDOWN"));
	        connection.close();

	    }

}
