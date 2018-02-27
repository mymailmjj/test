/**
 * 
 */
package lifecycle;

/**
 * 生命周期的事件对象
 * @author cango
 *
 */
public class LifeCycleEvent {
	
	//事件源
	private LifeCycle lifeCycle;
	//事件类型
	private String type;
	//事件参数
	private Object obj;

	public LifeCycleEvent(LifeCycle lifeCycle, String type) {
		this(lifeCycle, type, null);
	}
	
	public LifeCycleEvent(LifeCycle lifeCycle, String type,Object obj) {
		this.lifeCycle = lifeCycle;
		this.type = type;
		this.obj = obj;
	} 

	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	public void setLifeCycle(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return obj;
	}

	public void setData(Object obj) {
		this.obj = obj;
	}
	
	
}
