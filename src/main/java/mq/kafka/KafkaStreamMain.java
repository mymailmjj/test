package mq.kafka;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

/**
 * kafka API使用方法
 * 这里用的是老的API
 * @author cango
 *
 */
public class KafkaStreamMain {

    public static void main(String[] args) {
        try {
            Properties config = new Properties();
            config.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
            config.put(StreamsConfig.APPLICATION_SERVER_CONFIG, "47.93.42.123:9092");
            config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "47.93.42.123:9092");
            config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
            config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
            config.put(StreamsConfig.CLIENT_ID_CONFIG, "aaaa");

            KStreamBuilder builder = new KStreamBuilder();
            KStream<String, String> textLines = builder.stream("kafka_topic");

            textLines.foreach((t1,t2) -> {
                System.out.println("key:"+t1 + "\tvalue:"+t2);
            });
            
            KafkaStreams streams = new KafkaStreams(builder, config);
            streams.start();

            Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        } catch (StreamsException | IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
