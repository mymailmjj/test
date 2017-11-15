/**
 * 
 */
package server;

import server.connector.ServerConnector;

/**
 * 测试用serversocket做的多个线程接受的服务器
 * @author mujjiang
 *
 */
public class SocketConnectorTest {
	
	public static void main(String[] args) {
		
		ServerConnector serverConnector = new ServerConnector(8081,ServerConnector.NIO, 5);
		
		serverConnector.start();
		
	}

}
