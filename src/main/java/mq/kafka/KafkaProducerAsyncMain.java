package mq.kafka;

import java.time.LocalTime;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * 可以同步发送，也可以异步发送，这里是异步发送的API调用
 * kakfa使用消息端发送的程序
 * @author cango
 *
 */
public class KafkaProducerAsyncMain {
	
	static class SendCallBack implements Callback{
		
		private LocalTime time;
		
		private int messageId;
		
		public SendCallBack(LocalTime time, int messageId) {
			this.time = time;
			this.messageId = messageId;
		}


		@Override
		public void onCompletion(RecordMetadata metadata, Exception exception) {
			LocalTime finishTime= LocalTime.now();
			System.out.println("messageId: "+messageId+"\tstart send message at "+time+" receive act at:"+finishTime);
			if(metadata!=null){
				int partition = metadata.partition();
				long offset = metadata.offset();
				System.out.println("messageId: "+messageId+"\tpartition:"+partition+"\toffset:"+offset);
			}
		}
		
	}

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
                Future<RecordMetadata> result = producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)),new SendCallBack(LocalTime.now(), i));
                
                RecordMetadata recordMetadata = result.get(3, TimeUnit.SECONDS);
                
                System.out.println("异步发送成功："+i);
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
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        producer.close();
        
        

    }

}
