package test.threadpool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadPool implements ThreadPool {

	private final static int DEFAULT_NUM = 5;

	// 定义一个变量，用来表示任务数量
	private AtomicInteger tasknum = new AtomicInteger();

	private LinkedBlockingDeque<Runnable> taskDequeue = new LinkedBlockingDeque<Runnable>();

	private HashSet<WorkThread> threads = new HashSet<WorkThread>();

	// 定义一个变量，用来表示程序是否在运行
	private volatile boolean isRunning = false;

	private DefaultThreadPool() {
		this(DEFAULT_NUM);
	}
	
	public static ThreadPool getThreadPool(){
		return new DefaultThreadPool();
	}
	
	public static ThreadPool getThreadPool(int num){
		return new DefaultThreadPool(num);
	}
	

	private int num;

	public DefaultThreadPool(int num) {
		if (num < 0) {
			num = DEFAULT_NUM;
		}

		this.num = num;

		for (int i = 0; i < num; i++) {
			threads.add(new WorkThread("线程"+i));
		}

		start(); // 初始化好线程之后，启动所有线程

	}

	//初始化之后真正启动线程的方法
	private void start() {
		
		Iterator<WorkThread> iterator = threads.iterator();
		
		while(iterator.hasNext()){
			WorkThread workThread = iterator.next();
			workThread.start();
		}
	}
	
	
	public void destroy() {

		while(!taskDequeue.isEmpty()){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		isRunning =false;
		
		threads.clear();
		
	}
	

	// 这里是添加任务的
	public void execute(Runnable run) {
		synchronized (taskDequeue) {
			addWorker(run);
			tasknum.incrementAndGet();
		}
	}

	private void addWorker(Runnable run) {

		taskDequeue.add(run);

		taskDequeue.notifyAll();

	}

	class WorkThread extends Thread {
		
		public WorkThread(String name){
			super(name);
		}

		@Override
		public void run() {
			// 把变量标识置为true
			isRunning = true;
			System.out.println(Thread.currentThread().getName()+"开始执行");
			while (true) {

				synchronized (taskDequeue) {

					// 如果任务不为空，则开始处理task里面的任务
					if (!taskDequeue.isEmpty() && isRunning) {

						Runnable r = taskDequeue.pollLast(); // 把最后的一个取出并且删除

						if (r != null) {
							r.run();
							tasknum.decrementAndGet();
							try {
								taskDequeue.wait();
								taskDequeue.notifyAll();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					}
					
					if(!isRunning) {
						break;
					}
				}

			}

		}

	}


}
