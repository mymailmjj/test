package mq.activemq;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import org.slf4j.event.LoggingEvent;

public class Log4jJMSAppenderExample implements MessageListener {
	
	public Log4jJMSAppenderExample() throws Exception {
        // create a logTopic topic consumer
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection conn = factory.createConnection();
        Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        conn.start();
        MessageConsumer consumer = sess.createConsumer(sess.createTopic("logTopic"));
        consumer.setMessageListener(this);
        // log a message
        Logger log = Logger.getLogger(Log4jJMSAppenderExample.class);
        log.info("Test log");
        // clean up
        Thread.sleep(1000);
        consumer.close();
        sess.close();
        conn.close();
        System.exit(1);
    }
	
	public static void main(String[] args) throws Exception {
		
		new Log4jJMSAppenderExample();
		
	}

	@Override
	public void onMessage(Message message) {
		  try {
	            // receive log event in your consumer
	            LoggingEvent event = (LoggingEvent)((ActiveMQObjectMessage)message).getObject();
	            System.out.println("Received log [" + event.getLevel() + "]: "+ event.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

}
