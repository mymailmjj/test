/**
 * 
 */
package nio;


/**
 * @author mujjiang
 *
 */
public class ClientRequest implements Runnable {
	
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		Request.requestSocket();
		
	}

}
