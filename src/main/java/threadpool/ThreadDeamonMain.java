package threadpool;

import java.util.concurrent.TimeUnit;

/**
 * 对Deamon线程进程测试
 * Deamon线程只有当jvm所有的用户线程消失时才会消失
 * 
 * @author mymai
 *
 */
public class ThreadDeamonMain {

	public static void main(String[] args) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						int i = 0;
						while (i++ < 20) {
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							System.out.println(Thread.currentThread().getName() + "\tdown:" + i);
						}

						System.out.println(Thread.currentThread().getName() + "\t end:" + i);
					}
				});

				t.setName("线程3");

				t.setDaemon(true);

				t.start();

				int i = 0;
				while (i++ < 5) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(Thread.currentThread().getName() + "\tup:" + i);
				}

				System.out.println(Thread.currentThread().getName() + "\t end:" + i);

			}
		}).start();

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 0;
				while (i++ < 15) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(Thread.currentThread().getName() + "\tdown:" + i);
				}

				System.out.println(Thread.currentThread().getName() + "\t end:" + i);
			}
		});

		t.setName("线程2");

		t.start();

		System.out.println("-----------------------");

	}

}
