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
        props.put("client.id", "streamtest");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("application.id", "streamoperate");
        props.put("loggingEnabled", false);

        Map<String, String> maps = new HashMap<>((Map) props);

        Topology topology = new Topology();

        KeyValueByteStoreSupplier keyValueByteStoreSupplier = new KeyValueByteStoreSupplier("Counts");

        KeyValueStoreBuilder<String, String> keyValueStoreBuilder = new KeyValueStoreBuilder<>(keyValueByteStoreSupplier, Serdes.String(), Serdes.String(),
                new SystemTime());

        keyValueStoreBuilder.withLoggingDisabled();

        topology.addGlobalStore(keyValueStoreBuilder, "SOURCE", new StringDeserializer(), new StringDeserializer(), "kafka_topic", "PROCESS1",
                new ProcessorSupplier<String, String>() {

                    @Override
                    public Processor<String, String> get() {
                        return new MyProcessor("第一个");
                    }
                });

        topology.addProcessor("PROCESS2", () -> {
            return new MyProcessor("第二个");
        },  "SOURCE");

        KafkaStreams kafaKafkaStreams = new KafkaStreams(topology, props);

        kafaKafkaStreams.start();

    }
}
