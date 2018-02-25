package mq.rocketmq;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.MessageQueueListener;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;

public class RocketMQPullConsumer {

    public static void main(String[] args) throws MQClientException {
        
        
     /*   String mqNameServer = "39.107.103.45:9876";  

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("defaultMQPullGroup");
        
        consumer.setNamesrvAddr(mqNameServer);
        
        consumer.start();
        
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TopicTest");
        
        for(MessageQueue messageQueue:messageQueues){  
            
            System.out.println(messageQueue.getTopic());  
        }  
          
          
        //消息队列的监听  
        consumer.registerMessageQueueListener("TopicTest", new MessageQueueListener() {  
              
            @Override  
            //消息队列有改变，就会触发  
            public void messageQueueChanged(String topic, Set<MessageQueue> mqAll,  
                    Set<MessageQueue> mqDivided) {  
                for(MessageQueue messageQueue:mqAll){  
                    
                    System.out.println("aaa"+messageQueue.getTopic());  
                }  
            }  
        });  
        
        consumer.shutdown();*/
        
        String mqNameServer = "39.107.103.45:9876";  
        
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("broker-a");  
        consumer.setNamesrvAddr(mqNameServer);  
        try {  
              
            // 订阅PushTopic下Tag为push的消息,都订阅消息  
            consumer.subscribe("TopicTestjjj", "TagA || TagC || TagD");  
              
            // 程序第一次启动从消息队列头获取数据  
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);  
            //可以修改每次消费消息的数量，默认设置是每次消费一条  
             consumer.setConsumeMessageBatchMaxSize(1000);  
             
             
             MessageListenerConcurrently messageListenerConcurrently = new MessageListenerConcurrently() {  
                 //在此监听中消费信息，并返回消费的状态信息  
                  public ConsumeConcurrentlyStatus consumeMessage(  
                          List<MessageExt> msgs,  
                          com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext context) {  
                        
                      // msgs中只收集同一个topic，同一个tag，并且key相同的message  
                      // 会把不同的消息分别放置到不同的队列中  
                      for(Message msg:msgs){  
                          System.out.println(new String(msg.getBody()));  
                      }
                      
                      synchronized (this) {
                          this.notify();
                      }
                      
                      return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;  
                  }  
              };
             
            //注册消费的监听  
            consumer.registerMessageListener(messageListenerConcurrently);  
  
            consumer.start();  
            
            while(true){
                synchronized (messageListenerConcurrently) {
                    messageListenerConcurrently.wait();
                }
            }
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

}
