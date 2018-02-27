package mq.activemq;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.spring.ActiveMQConnectionFactory;

import utils.ExecutorUtils;

public class MainActiveMQPublisher {
    
    
    private static String user = "system";
    private static String password = "password";
    private static String defaultURL = "tcp://localhost:61616";
    private static CountDownLatch countDownLatch;
    
    private static ActiveMQConnection connection;
    
    private static AtomicInteger num = new AtomicInteger(5);
    
    static{
        setConnection();
    }
    
    public static void setConnection(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        connectionFactory.setBrokerURL(defaultURL);

        ActiveMQConnection connection = null;
        
        try {
            connection = (ActiveMQConnection) connectionFactory.createConnection(user, password);
            connection.start();
            MainActiveMQPublisher.connection = connection; 
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    
    static class PublishTask implements Runnable{

        public void run() {
            try {
                    String name = Thread.currentThread().getName();
                    
                    ActiveMQConnection connection = MainActiveMQPublisher.connection;
                    
                    connection.start();
                    
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    
//                    Queue queue = session.createQueue("event");
                    
                    Topic topic = session.createTopic("eventTopic");
                    
                    MessageProducer messageProducer = session.createProducer(topic);
                    
                    messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
                    
                    int i = 0;
                    
                    while(i < num.get()){
                        
                        System.out.println("开始发送："+i);
                        
                        ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
                        
                        activeMQTextMessage.setText(name+" activemq sender send text"+i);
                        
                        messageProducer.send(activeMQTextMessage);
                        
                        System.out.println("发送消息："+i);
                        
                        i++;
                        
                        num.getAndDecrement();
                    }
                    
//                    countDownLatch.countDown();
                
               /* countDownLatch.await(); */
                
                    if(num.get()==0){
                        connection.close();
                    }
                
            } catch (MessageNotWriteableException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                e.printStackTrace();
            } 
        }
        
        
    }
    
    

    public static void main(String[] args) throws JMSException {
//        countDownLatch = new CountDownLatch(5);
        ExecutorUtils.executor(new PublishTask(),1);
    }

}
