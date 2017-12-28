/**
 * 
 */
package lifecycle;

/**
 * @author cango
 * 
 */
public abstract class LifeCycleBase implements LifeCycle {

	private LifeCycleSupport lifeCycleSupport = new LifeCycleSupport(this);

	public void setStatus(LifeStatus status) {
		this.lifeStatus = status;
	}

	protected LifeStatus lifeStatus = LifeStatus.NEW;

	public LifeStatus getStatus() {
		return this.lifeStatus;
	}

	public void init() {
		setStatus(LifeStatus.BEFORE_INIT);
		initInner();
		setStatus(LifeStatus.INITIZED);

	}

	public void start() {

		if (!this.lifeStatus.equals(LifeStatus.NEW)
				&& !this.lifeStatus.equals(LifeStatus.INITIZED)) {
			throw new IllegalStateException("状态不正确");
		}

		if (this.lifeStatus.equals(LifeStatus.NEW)) {
			init();
		}
		setStatus(LifeStatus.BEFORE_START);
		startInner();
		setStatus(LifeStatus.STARTED);

	}

	public void stop() {
		setStatus(LifeStatus.BEFORE_DESTROY);
		destroyInner();
		setStatus(LifeStatus.DESTROYED);

	}

	public void addLifeCycleListener(LifeCycleListener lifeCycleListener) {
		lifeCycleSupport.addListener(lifeCycleListener);
	}

	public void fireLifeCycleEvent(String type) {
		lifeCycleSupport.fireLifeCycleEvent(type);
	}

	protected abstract void initInner();

	protected abstract void startInner();

	protected abstract void destroyInner();

}
