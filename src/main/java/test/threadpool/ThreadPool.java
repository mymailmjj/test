/**
 * 
 */
package test.threadpool;

/**
 * @author mujjiang
 *
 */
public interface ThreadPool {
	
	public void execute(Runnable run);
	
	public void destroy();

}
