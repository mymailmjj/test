/**
 * 
 */
package lifecycle;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 所有组建的父类，封装了一些组件需要的通用方法
 * 
 * @author cango
 * 
 */
public class AbstractComponent extends LifeCycleBase implements Component {

	protected HashSet<Component> hashSetComponents = new HashSet<Component>();

	@Override
	protected void initInner() {
		setStatus(LifeStatus.INITIZING);

		// start all child componnet
		Iterator<Component> iterator = hashSetComponents.iterator();
		while (iterator.hasNext()) {
			AbstractComponent component = (AbstractComponent) iterator.next();
			component.init();
		}

	}

	@Override
	protected void startInner() {
		setStatus(LifeStatus.STARTING);

		// start all child componnet
		Iterator<Component> iterator = hashSetComponents.iterator();
		while (iterator.hasNext()) {
			AbstractComponent component = (AbstractComponent) iterator.next();
			component.start();

		}
	}

	@Override
	protected void destroyInner() {
		setStatus(LifeStatus.DESTROYING);

		// start all child componnet
		Iterator<Component> iterator = hashSetComponents.iterator();
		while (iterator.hasNext()) {
			AbstractComponent component = (AbstractComponent) iterator.next();
			component.stop();
		}

	}

}
