package mq.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.spring.ActiveMQConnectionFactory;

import utils.ExecutorUtils;

public class MainActiveMQConsumer {
    private static String user = "system";
    private static String password = "password";
    private static String defaultURL = "tcp://39.107.103.45:61616";
    
    private static ActiveMQConnection connection;
    
    static{
        setConnection();
    }
    
    public static void setConnection(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        connectionFactory.setBrokerURL(defaultURL);

        ActiveMQConnection connection = null;
        
        try {
            connection = (ActiveMQConnection) connectionFactory.createConnection(user, password);
            
            connection.setClientID("aaaaa");
            
            connection.start();
            MainActiveMQConsumer.connection = connection; 
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    
    static class ConsumerTask implements Runnable{

        public void run() {
            
            try {

                ActiveMQConnection connection = MainActiveMQConsumer.connection;
                
                connection.start();

                Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

                Queue queue = session.createQueue("newevent11");
                
//                Topic createTopic = session.createTopic("eventTopic");
                
                ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(queue);
                
//                TopicSubscriber subscriber = session.createDurableSubscriber(queue, "sub1");
                
               /* while(true){
                    Message message = subscriber.receive(Integer.MAX_VALUE);
                    
                    if(message instanceof ActiveMQTextMessage){
                        ActiveMQTextMessage t = (ActiveMQTextMessage)message;
                        System.out.println("t:"+t.getText());
                    }
                }*/
                
             /*   consumer.setMessageListener(new MessageListener() {
					
					@Override
					public void onMessage(Message message) {
						System.out.println("收到消息:"+message);
						
					}
				});*/
                
                
                while(true){
                	
                    Message message = consumer.receive(Integer.MAX_VALUE);
                    
                    if(message instanceof ActiveMQTextMessage){
                        ActiveMQTextMessage t = (ActiveMQTextMessage)message;
                        System.out.println("t:"+t.getText());
                    }
                    
                    consumer.acknowledge();
                    
                }
                
               
                

//                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    

    public static void main(String[] args) throws JMSException {
        
        ExecutorUtils.executor(new ConsumerTask(),1);
       

    }

}
