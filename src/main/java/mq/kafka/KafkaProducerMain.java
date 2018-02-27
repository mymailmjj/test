package mq.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * kakfa使用消息端发送的程序
 * @author cango
 *
 */
public class KafkaProducerMain {

    public static void main(String[] args) {
        
        Properties props = new Properties();
        props.put("bootstrap.servers", "47.93.42.123:9092");
        props.put("transactional.id", "11225");
        props.put("partitioner.class", "mq.kafka.TestProducerPartitioner");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        
        producer.initTransactions();
       
        try {
            
            long startTime = System.currentTimeMillis();
            
            producer.beginTransaction();
            for (int i = 0; i < 100; i++){
                producer.send(new ProducerRecord<>("test1", Integer.toString(i), Integer.toString(i)));
                
                System.out.println("发送："+i);
            }
            
            long end = System.currentTimeMillis();
            
            System.out.println("总耗时:"+(end-startTime)/1000+"秒");
               
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
        
        

    }

}
