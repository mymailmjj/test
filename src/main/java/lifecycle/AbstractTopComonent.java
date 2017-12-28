package lifecycle;

/**
 * 模拟顶层组件的父类，顶层组件一般较少
 * @author cango
 *
 */
public abstract class AbstractTopComonent extends LifeCycleBase implements Component {

	@Override
	protected void initInner() {
		setStatus(LifeStatus.INITIZING);
		
	}

	@Override
	protected void startInner() {
		setStatus(LifeStatus.STARTING);
		
	}

	@Override
	protected void destroyInner() {
		setStatus(LifeStatus.DESTROYING);
		
	}

}
