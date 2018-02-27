package mq.kafka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * kafka consumer 多线程模拟多个consumer的情况
 * 同一个consumeGroup则是竞争去同一个topic的消息
 * 不同的consumerGroup则是订阅同一个topic的消息
 * @author cango
 *
 */
public class KafkaConsumerMain {

    public static void main(String[] args) {
        
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
        
        for(int i = 0; i<5;i++){
            newFixedThreadPool.submit(new KafkaConsumerTask());
        }
        
        newFixedThreadPool.shutdown();
        
    }

}
