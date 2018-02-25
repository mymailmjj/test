package mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class RabbitMQReceiver2Main {
    
    static class Consumer extends DefaultConsumer{

        public Consumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
            long deliveryTag = envelope.getDeliveryTag();
            System.out.println("consumer2收到消息："+new String(body));
            getChannel().basicAck(deliveryTag, false);
            synchronized (this) {
                this.notify();
            }
        }
        
    }

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
        
        String queueName = channel.queueDeclare().getQueue();
        
        channel.queueBind(queueName, "log", "lazy.#");
        
        Consumer consumer = new Consumer(channel);
        
        channel.basicConsume(queueName, consumer);
        
        try {
            while(true){
                synchronized (consumer) {
                    consumer.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            channel.close();
            conn.close();
        }
        
    }

}
