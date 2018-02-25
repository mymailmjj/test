/**
 * 
 */
package mq.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 客户端取消息的程序
 * 
 * @author cango
 *
 */
public class KafkaConsumerTask implements Runnable {
    
    
    public void cosume(){
        String name = Thread.currentThread().getName();
        Properties props = new Properties();  
        props.put("bootstrap.servers", "47.93.42.123:9092");//服务器ip:端口号，集群用逗号分隔  
        props.put("group.id", "test"+name);  
        props.put("enable.auto.commit", "true");  
        props.put("auto.commit.interval.ms", "1000");  
        props.put("session.timeout.ms", "30000");  
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
        KafkaConsumer consumer = new KafkaConsumer<>(props);  
        consumer.subscribe(Arrays.asList("test"),new MyKafkaConsumerRebalanceListener());  
        
        while(true){  
            ConsumerRecords<String,String> records = consumer.poll(100);
            
            for(ConsumerRecord<String,String> c:records){
                System.out.println(name+"\tfetch result:key:"+c.key()+"\tvalue:"+c.value());
            }
            
        }  
        
    }
    

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        cosume();
    }
    
    
    public static void main(String[] args) {
        
        
        
        
    }

}
