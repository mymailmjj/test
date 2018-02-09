package mq.kafka;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

/**
 * kafka扩展工具，可以针对不同的key是消息发送到不同的partition
 * 一个topic可以有一个或者多个topic
 * 可以在kafka配置文件中指定partition的数量
 * @author cango
 *
 */
public class TestProducerPartitioner implements Partitioner {

    @Override
    public void configure(Map<String, ?> configs) {
        // TODO Auto-generated method stub

    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partis = cluster.availablePartitionsForTopic(topic);
        
        int i = 0;
        
        int numPartitions = partis.size();
        
        try {
            int partitionNum = Integer.parseInt((String) key);
            i= Math.abs(Integer.parseInt((String) key) % numPartitions);
        } catch (Exception e) {
            i=  Math.abs(key.hashCode() % numPartitions);
        }
        
        System.out.println("key:"+key+"\t分区："+i);
        
        return i;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

}
