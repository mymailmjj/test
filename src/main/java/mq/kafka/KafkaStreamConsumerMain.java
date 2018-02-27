/**
 * 
 */
package mq.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.SystemTime;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.state.internals.KeyValueStoreBuilder;

/**
 * 
 * kafka流式API典型使用方法
 * 这里是新API版本 1.0
 * 处理过程由多个NODE构成，每个NODE可以包含一个或者多个PORCESSOR
 * 
 * @author cango
 *
 */
public class KafkaStreamConsumerMain {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "47.93.42.123:9092");// 服务器ip:端口号，集群用逗号分隔
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("application.id", "streamoperate");    //用于区分是否是同一个客户端
        props.put("loggingEnabled", false);

        Map<String, String> maps = new HashMap<>((Map) props);

        Topology topology = new Topology();

        topology.addSource("SOURCE", "kafka_topic");
        
      
        String process1 = "PROCESS1";
        
        String process2 = "PROCESS2";

        topology.addProcessor(process1, () -> {
            return new MyProcessor(process1);
        },  "SOURCE");
        
        KeyValueByteStoreSupplier keyValueByteStoreSupplier1 = new KeyValueByteStoreSupplier(process1);

        KeyValueStoreBuilder<String, String> keyValueStoreBuilder1 = new KeyValueStoreBuilder<>(keyValueByteStoreSupplier1, Serdes.String(), Serdes.String(),
                new SystemTime());
        
        keyValueStoreBuilder1.withLoggingDisabled();
        
        topology.addStateStore(keyValueStoreBuilder1, process1);

        topology.addProcessor(process2, () -> {
            return new MyProcessor(process2);
        },  "SOURCE");
        
        KeyValueByteStoreSupplier keyValueByteStoreSupplier2 = new KeyValueByteStoreSupplier(process2);

        KeyValueStoreBuilder<String, String> keyValueStoreBuilder2 = new KeyValueStoreBuilder<>(keyValueByteStoreSupplier2, Serdes.String(), Serdes.String(),
                new SystemTime());
        
        keyValueStoreBuilder2.withLoggingDisabled();
        
        topology.addStateStore(keyValueStoreBuilder2, process2);
        
        String subProcessor = "SUBPROCESSOR1";
        
        topology.addProcessor(subProcessor, () -> {
            return new MySubProcessor(subProcessor);
        }, process1);
        

        KafkaStreams kafaKafkaStreams = new KafkaStreams(topology, props);

        kafaKafkaStreams.start();

    }
}
