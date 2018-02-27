package mq.kafka;

import java.util.Properties;

import org.apache.kafka.common.security.JaasUtils;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;

public class KafkaCreateTopicMain {

    public static void main(String[] args) {
        
        ZkUtils zkUtil = ZkUtils.apply("39.107.103.45:2181", 30000, 30000, false);
        
        String topic = "test100";
        
        boolean e = AdminUtils.topicExists(zkUtil, topic);
        
        if(!e){
            
            Properties prop = new Properties();
            
            AdminUtils.createTopic(zkUtil, topic, 3, 3,prop, AdminUtils.createTopic$default$6());
        }else{
            
            System.out.println("topic existed!");
            
        }
        
    }

}
