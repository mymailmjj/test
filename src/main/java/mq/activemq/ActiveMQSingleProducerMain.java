package mq.activemq;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.activemq.util.Callback;

public class ActiveMQSingleProducerMain {
	
	 private static String defaultURL = "tcp://localhost:61616?trace=true";
	 
	 private static String user = "system";
	 private static String password = "password";

	public static void main(String[] args) {
		
		  ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

	        connectionFactory.setBrokerURL(defaultURL);

	        ActiveMQConnection connection = null;
	        
	        try {
	        	//流控制技术，如果使用异步发送，需要结合ProducerWindowSize
	        	//持久化的消息默认使用同步发送，非持久化默认使用异步发送
	        	//可以指定发送方式，进行流控制，同步发送的时候是实时控制的，异步发送需要结合WindowSize和服务器一起控制
	        	//windowSize表示收到下一个ProducerAck之前，可以发送的字节数
	            connection = (ActiveMQConnection) connectionFactory.createConnection(user, password);
	            connection.setProducerWindowSize(50);
	            connection.setAlwaysSyncSend(false);
	            connection.setUseAsyncSend(true);
	            connection.start();
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
	        
	        try {
				Session session = connection.createSession(false, ActiveMQSession.CLIENT_ACKNOWLEDGE);
				
				Queue queue = session.createQueue("test");
				
				MessageProducer producer = session.createProducer(queue);
				
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				
				String ss = "aaa";
				
				for(int i =0;i<10;i++){
					ActiveMQTextMessage textMessage = new ActiveMQTextMessage();  
					
					ss = ss+ss+i;
					
					textMessage.setText(ss);
					
					producer.send(textMessage);
					
				}
					
				
				
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		

	}

}
