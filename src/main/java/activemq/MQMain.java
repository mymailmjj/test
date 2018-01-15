package activemq;


/**
 * activemq server 启动入口
 * @author cango
 *
 */
public class MQMain {
	
	public static void main(String[] args) {
		
		org.apache.activemq.console.Main main = new org.apache.activemq.console.Main();
		
		
		//-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005
		String[] s = {"start","xbean:activemq.xml"};
		System.setProperty("java.io.tmpdir", "D://temp");
		
		main.main(s);
		
	}

}
