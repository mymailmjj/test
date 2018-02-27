/**
 * 
 */
package mq.kafka;

import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

/**
 * @author cango
 *
 */
public class MyKafkaConsumerRebalanceListener implements ConsumerRebalanceListener {

    /* (non-Javadoc)
     * @see org.apache.kafka.clients.consumer.ConsumerRebalanceListener#onPartitionsRevoked(java.util.Collection)
     */
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        for (TopicPartition topicPartition : partitions) {
            System.out.println("onPartitionsRevoked:"+topicPartition.partition());
        }
    }

    /* (non-Javadoc)
     * @see org.apache.kafka.clients.consumer.ConsumerRebalanceListener#onPartitionsAssigned(java.util.Collection)
     */
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        for (TopicPartition topicPartition : partitions) {
            System.out.println("onPartitionsAssigned:"+topicPartition.partition());
        }
        
    }

}
