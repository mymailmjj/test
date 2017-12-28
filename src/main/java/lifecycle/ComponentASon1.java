package lifecycle;

public class ComponentASon1 extends AbstractComponent {

	@Override
	protected void initInner() {
		super.initInner();
		addLifeCycleListener(new ComponentASonListener());
		System.out.println("ComponentASon1 initInner");
	}

	@Override
	protected void startInner() {
		super.startInner();
		System.out.println("ComponentASon1 startInner");
		fireLifeCycleEvent(LifeCycle.INITIZING);
	}

	@Override
	protected void destroyInner() {
		super.destroyInner();
		System.out.println("ComponentASon1 destroyInner");
	}
	
	
	
	

}
