package lifecycle;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		
		//模拟tomcat的server组件
		TopCommponent topCommponent = new TopCommponent();
		
		ComponentA componentA = new ComponentA();
		
		topCommponent.setComponentA(componentA);
		
		ComponentASon1 componentASon1 = new ComponentASon1();
		
		ComponentASon2 componentASon2 = new ComponentASon2();
		
		componentA.addChild(componentASon1);
		
		componentA.addChild(componentASon2);
		
		topCommponent.start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		topCommponent.stop();
		
	}

}
