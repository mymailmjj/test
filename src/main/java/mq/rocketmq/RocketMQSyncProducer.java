package mq.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * rocketmq 官网例子
 * Reliable synchronous transmission
 * @author cango
 *
 */
public class RocketMQSyncProducer {
    
    public static void main(String[] args) throws Exception {
        
        String mqNameServer = "39.107.103.45:9876";  
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("mygroup");
        
        producer.setNamesrvAddr(mqNameServer);
        
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 5; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ " +
                    i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

}
