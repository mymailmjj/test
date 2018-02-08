package mq.kafka;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerMain {

    public static void main(String[] args) {
        
        Properties props = new Properties();  
        props.put("bootstrap.servers", "39.107.103.45:9092");//服务器ip:端口号，集群用逗号分隔  
        props.put("group.id", "test");  
        props.put("enable.auto.commit", "true");  
        props.put("auto.commit.interval.ms", "1000");  
        props.put("session.timeout.ms", "30000");  
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
        KafkaConsumer consumer = new KafkaConsumer<>(props);  
        consumer.subscribe(Arrays.asList("test"));  
        
        
        while(true){  
            ConsumerRecords<String,String> records = consumer.poll(100);
            
            for(ConsumerRecord<String,String> c:records){
                System.out.println("fetch result:key:"+c.key()+"\tvalue:"+c.value());
            }
            
        }  
        
    }

}
