/**
 * 
 */
package activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

/**
 * @author cango
 *
 */
public class MainStompReceiver {
	

    public static void main(String []args) throws JMSException {

        String user = "admin";
        String password = "password";
        String host = "localhost";
        int port = 61613;
        String destination = "/topic/event";

        StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
        factory.setBrokerURI("tcp://" + host + ":" + port);

        Connection connection = factory.createConnection(user, password);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = new StompJmsDestination(destination);

        MessageConsumer consumer = session.createConsumer(dest);
        long start = System.currentTimeMillis();
        long count = 1;
        System.out.println("Waiting for messages...");
        while(true) {
            Message msg = consumer.receive();
            if( msg instanceof  TextMessage ) {
                String body = ((TextMessage) msg).getText();
                if( "SHUTDOWN".equals(body)) {
                    long diff = System.currentTimeMillis() - start;
                    System.out.println(String.format("Received %d in %.2f seconds", count, (1.0*diff/1000.0)));
                    break;
                } else {
                    if( count != msg.getIntProperty("id") ) {
                        System.out.println("mismatch: "+count+"!="+msg.getIntProperty("id"));
                    }
                    count = msg.getIntProperty("id");

                    if( count == 0 ) {
                        start = System.currentTimeMillis();
                    }
                    if( count % 1000 == 0 ) {
                        System.out.println(String.format("Received %d messages.", count));
                    }
                    count ++;
                }

            } else {
                System.out.println("Unexpected message type: "+msg.getClass());
            }
        }
        connection.close();
    }

}
