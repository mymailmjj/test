/**
 * 
 */
package lifecycle;

/**
 * @author cango
 * 
 */
public interface LifeCycle {

	// 暂时分三个阶段 init start destroy
	public final static String BEFORE_INIT = "before_init";

	public final static String START_INIT = "start_init";

	public final static String INITIZING = "inilizing";

	public final static String INITIZED = "inilized";

	public final static String BEFORE_START = "before_start";

	public final static String STARTING = "starting";

	public final static String STARTED = "started";

	public final static String BEFORE_DESTROY = "before_destroy";

	public final static String DESTROYING = "destroying";

	public final static String DESTROYED = "destroyed";

	public void init();

	public void start();

	public void stop();

}
