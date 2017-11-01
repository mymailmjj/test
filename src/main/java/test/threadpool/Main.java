package test.threadpool;

public class Main {

	public static void main(String[] args) {

		ThreadPool threadPool = DefaultThreadPool.getThreadPool();
		
		for(int i = 0; i < 30; i++){
			final int j = i;
			threadPool.execute(new Runnable() {
				
				public void run() {
					System.out.println("线程"+Thread.currentThread().getName()+"调用了任务:"+j);
				}
			});
		}
		
		threadPool.destroy();
		
	}

}
