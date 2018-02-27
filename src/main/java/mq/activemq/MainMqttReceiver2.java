package mq.activemq;

import org.fusesource.hawtbuf.*;
import org.fusesource.mqtt.client.*;

/**
 * mqtt 接受
 * 
 * @author cango
 * 
 */
public class MainMqttReceiver2 {

	public static void main(String[] args) throws Exception {

		String user = "mqtt-test";
		String password = "mqtt-test";
		String host = "39.107.103.45";
		int port = 1883;
		final String destination = "/topic/event";

		MQTT mqtt = new MQTT();
		mqtt.setHost(host, port);
		mqtt.setUserName(user);
		mqtt.setPassword(password);

		final CallbackConnection connection = mqtt.callbackConnection();

		connection.listener(new org.fusesource.mqtt.client.Listener() {
			long count = 0;
			long start = System.currentTimeMillis();

			public void onConnected() {
			}

			public void onDisconnected() {
			}

			public void onFailure(Throwable value) {
				value.printStackTrace();
				System.exit(-2);
			}

			public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
				String body = msg.utf8().toString();
				if ("SHUTDOWN".equals(body)) {
					long diff = System.currentTimeMillis() - start;
					System.out.println(String.format(
							"Received %d in %.2f seconds", count, (1.0 * diff / 1000.0)));
					connection.disconnect(new Callback<Void>() {
						public void onSuccess(Void value) {
							System.exit(0);
						}

						public void onFailure(Throwable value) {
							value.printStackTrace();
							System.exit(-2);
						}
					});
				} else {
					if (count == 0) {
						start = System.currentTimeMillis();
					}
					if (count % 1000 == 0) {
						System.out.println(String.format(
								"Received %d messages.", count)+"\t"+body);
					}
					count++;
				}
				ack.run();
			}
		});

		connection.connect(new Callback<Void>() {
			public void onSuccess(Void value) {
				Topic[] topics = { new Topic(destination, QoS.AT_LEAST_ONCE) };
				connection.subscribe(topics, new Callback<byte[]>() {
					public void onSuccess(byte[] qoses) {
						System.out.println(new String(qoses));
					}

					public void onFailure(Throwable value) {
						value.printStackTrace();
						System.exit(-2);
					}
				});
			}

			public void onFailure(Throwable value) {
				value.printStackTrace();
				System.exit(-2);
			}
		});

		// Wait forever..
		synchronized (Listener.class) {
			while (true)
				Listener.class.wait();
		}
	}

}
