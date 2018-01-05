/**
 * 
 */
package aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author cango
 * 
 */
public class Synchronizer {

	private Sync sync = new Sync();

	private class Sync extends AbstractQueuedSynchronizer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean tryAcquire(int arg) {

			Thread currentThread = Thread.currentThread();

			if (getState() == 0) {
				compareAndSetState(0, arg);
				setExclusiveOwnerThread(currentThread);
				return true;
			} else if (currentThread == getExclusiveOwnerThread()) {
				int s = getState() + arg;
				if (s < getState()) {
					throw new IllegalArgumentException("参数异常！");
				}
				setState(s);
				return true;
			}

			System.out.println("线程：" + currentThread.getName() + " try fail");

			return false;
		}

		@Override
		protected boolean tryRelease(int releases) {
			int c = getState() - releases;
			if (Thread.currentThread() != getExclusiveOwnerThread())
				throw new IllegalMonitorStateException();
			boolean free = false;
			if (c == 0) {
				free = true;
				System.out.println("线程：" + Thread.currentThread().getName()
						+ " empty current thread");
				setExclusiveOwnerThread(null);
			}
			
			setState(c);

			System.out.println("线程：" + Thread.currentThread().getName()
					+ " try Release " + free);

			return free;
		}

		@Override
		protected int tryAcquireShared(int arg) {
			return super.tryAcquireShared(arg);
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			return super.tryReleaseShared(arg);
		}

		public void lock() {
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
			} else {
				acquire(1);
			}
		}

		public void unlock() {
			release(1);
		}

	}

	public void lock() {
		System.out.println(Thread.currentThread().getName()
				+ "\tbegin acquire lock");
		sync.lock();
		System.out.println(Thread.currentThread().getName()
				+ "\tacquire lock success");
	}

	public void unlock() {
		System.out.println(Thread.currentThread().getName()
				+ "\tbegin release lock");
		sync.unlock();
		System.out.println(Thread.currentThread().getName()
				+ "\trelease lock success");
	}

}
