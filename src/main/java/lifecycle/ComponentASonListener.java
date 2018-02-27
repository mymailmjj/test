/**
 * 
 */
package lifecycle;

/**
 * @author cango
 *
 */
public class ComponentASonListener implements LifeCycleListener {

	/* (non-Javadoc)
	 * @see lifecycle.LifeCycleListener#lifeCycleEvent(lifecycle.LifeCycleEvent)
	 */
	public void lifeCycleEvent(LifeCycleEvent event) {
		System.out.println("收到事件,源头是："+event.getLifeCycle()+"\t事件类型："+event.getType());
	}

}
