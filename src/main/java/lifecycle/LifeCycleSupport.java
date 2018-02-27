/**
 * 
 */
package lifecycle;


/**
 * 这是一个生命周期事件监听容器的集合
 * @author cango
 * 
 */
public class LifeCycleSupport{

	private LifeCycle lifeCycle;

	public LifeCycleSupport(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	
	private LifeCycleListener[] lifeCycleListeners = new LifeCycleListener[0];
	
	public void addListener(LifeCycleListener listener){
		LifeCycleListener[] results = new LifeCycleListener[lifeCycleListeners.length+1];
		for (int i = 0; i < lifeCycleListeners.length; i++) {
			results[i] = lifeCycleListeners[i];
		}
		
		results[lifeCycleListeners.length] = listener;
		lifeCycleListeners = results;
		
	}
	
	public void fireLifeCycleEvent(String type){
		LifeCycleEvent lifeCycleEvent =  new LifeCycleEvent(lifeCycle, type);
		for (int i = 0; i < lifeCycleListeners.length; i++) {
			LifeCycleListener lifeCycleListener = lifeCycleListeners[i];
			lifeCycleListener.lifeCycleEvent(lifeCycleEvent);
		}
		
	}

}
