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
    private static String defaultURL = "tcp://39.107.103.45:61616";
    private static CountDownLatch countDownLatch;
    
    private static ActiveMQConnection connection;
    
    private static ThreadLocal<AtomicInteger> sessions = new ThreadLocal<>();
    
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
    	
    	public void send(MessageProducer messageProducer){
    		
    		  String name = Thread.currentThread().getName();
    		
    		  int i = 0;
              
              while(true){
            	  
            	  synchronized (MainActiveMQPublisher.class) {
            		  
            		  AtomicInteger atomicInteger = sessions.get();
                	  
                	  if(atomicInteger==null){
                		  AtomicInteger num = new AtomicInteger(2);
                		  sessions.set(num);
                	  }else{
                		  int incrementAndGet = atomicInteger.decrementAndGet();
                		  i = incrementAndGet;
                		  if(incrementAndGet<0) break;
                	  }
                      
                      System.out.println(name+"开始发送："+i);
                      
                      try {
    					ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
    					  
    					 activeMQTextMessage.setText(name+" 央视网消息（新闻联播）：北京市西城区紧邻中南海“红墙”，特殊的区位是一份光荣，更是一份使命。"
    					 		+ "西城区广大党员干部深刻领悟到，红墙意识的根本就在于“人民”二字，他们把老百姓的“表情包”作为检验工作的“晴雨表”，提升改善民生水平，提高城市温度。这两天，位于西城区德胜街道的婴幼儿早教中心开张营业，向辖区0到6岁的婴幼儿家庭免费开放。这块寸土寸金的地方，是北京整治“开墙打洞”行动中腾退出来的。腾退出来的房屋怎么用？政府部门没有拍脑门做决定，而是先召开了居民议事会。"
    					 		+ "按照居民议事会的意见，这次腾退出来的几处房屋，分别办起了便民菜站、"
    					 		+ "养老驿站和婴幼儿服务中心。以人民为中心，是红墙意识的直接体现。西城区全面推行民生工作民意立项制度，"
    					 		+ "明确了凡是与百姓利益相关的事情，都要充分征集群众意见，作为决策的重要依据。目前，已经完成的132个涉及住房改善、社会服务等方面的项目，个个环节都采纳了群众意见。民生工作民意立项的做法，如今已经在北京全市推广。"+i);
    					  
    					  messageProducer.send(activeMQTextMessage);
    					  
    					  
    				} catch (JMSException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                      
                      System.out.println("发送消息："+i);
            		  
					
				}
            	  
            	
              }
    		
    		
    	}

        public void run() {
        	
                    
                    try {
						Session	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
						
						Queue queue = session.createQueue("newevent11");
						
//						Topic topic = session.createTopic("eventTopic");
						
						MessageProducer messageProducer = session.createProducer(queue);
						
						messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						
//						messageProducer.setTimeToLive(20*1000);
						
						send(messageProducer);
						
//                    countDownLatch.countDown();
            
            /* countDownLatch.await(); */
            
					} catch (JMSException e) {
						e.printStackTrace();
					}
        }
        
        
    }
    
    

    public static void main(String[] args) throws JMSException {
//        countDownLatch = new CountDownLatch(5);
        ExecutorUtils.executor(new PublishTask(),10);
    }

}
