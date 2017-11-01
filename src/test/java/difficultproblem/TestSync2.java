package difficultproblem;

public class TestSync2 implements Runnable {
	int b = 100;

	synchronized void m1() throws InterruptedException {
		// System.out.println(Thread.currentThread().getName()+"执行m1");
		b = 1000;
		Thread.sleep(500);
		System.out.println("b=" + b);
	}

	synchronized void m2() throws InterruptedException {
		// System.out.println(Thread.currentThread().getName()+"执行m2");
		Thread.sleep(250);
		b = 2000;
	}

	public static void main(String[] args) throws InterruptedException {
		TestSync2 tt = new TestSync2();
		Thread t = new Thread(tt);
		t.start();

		tt.m2();
		System.out.println("main thread b=" + tt.b);
	}

	public void run() {
		try {
			m1();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
