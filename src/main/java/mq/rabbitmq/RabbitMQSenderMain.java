package mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class RabbitMQSenderMain {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("system");
        factory.setPassword("password");
        factory.setVirtualHost("vh");
        factory.setHost("39.107.103.45");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        
     /*   ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost");
        Connection conn = factory.newConnection();*/
        
        
        Channel channel = conn.createChannel();
        
        channel.exchangeDeclare("log", "topic");
        
        for (int i = 0; i < 20; i++) {
            if(i%2==0){
                channel.basicPublish("log", "quick.orange.rabbit", MessageProperties.PERSISTENT_TEXT_PLAIN, ("hello"+i).getBytes());
            }else{
                channel.basicPublish("log", "lazy.orange.elephant", MessageProperties.PERSISTENT_TEXT_PLAIN, ("hello"+i).getBytes());
            }
           
            try {
                System.out.println("发送消息："+i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        
        System.out.println("发送完毕");
        
        channel.close();
        conn.close();
        
    }

}
