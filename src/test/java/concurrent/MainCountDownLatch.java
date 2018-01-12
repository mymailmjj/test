package concurrent;

import java.util.concurrent.CountDownLatch;

public class MainCountDownLatch {
	
	public static void main(String[] args) {
		
		CountDownLatch countDownLatch = new CountDownLatch(5);
		
		Runner[] runers = new Runner[5];
		
		for(int i = 0;i <5 ;i++){
			runers[i] = new Runner("线程"+i,countDownLatch);
		}
		
		for(int i = 0;i <5 ;i++){
			runers[i].start();
		}
		
		
	}

}
