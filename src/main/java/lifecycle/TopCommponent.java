/**
 * 
 */
package lifecycle;

/**
 * @author cango
 *
 */
public class TopCommponent extends AbstractTopComonent{
	
	private ComponentA componentA;
	
	public ComponentA getComponentA() {
		return componentA;
	}

	public void setComponentA(ComponentA componentA) {
		this.componentA = componentA;
	}

	public TopCommponent() {
	}

	@Override
	protected void initInner() {
		super.initInner();
		System.out.println("TopCommponent initInner");
		if(componentA!=null){
			componentA.start();
		}
	}

	@Override
	protected void startInner() {
		super.startInner();
		System.out.println("TopCommponent startInner");
	}

	@Override
	protected void destroyInner() {
		super.destroyInner();
		System.out.println("TopCommponent destroyInner");
		if(componentA!=null){
			componentA.stop();
		}
	}
	

}
