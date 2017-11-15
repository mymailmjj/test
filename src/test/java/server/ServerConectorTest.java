package server;

import server.connector.ServerConnector;

public class ServerConectorTest {

	public static void main(String[] args) {
		
		ServerConnector serverConnector = new ServerConnector(8081,ServerConnector.NIO,2);
		
		serverConnector.start();
	}

}
