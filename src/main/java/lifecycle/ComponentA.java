/**
 * 
 */
package lifecycle;

/**
 * @author cango
 * 
 */
public class ComponentA extends AbstractComponent {

	@Override
	protected void initInner() {
		super.initInner();
		System.out.println("ComponentA initInner");
	}

	@Override
	protected void startInner() {
		super.startInner();
		System.out.println("ComponentA startInner");
	}

	@Override
	protected void destroyInner() {
		super.destroyInner();
		System.out.println("ComponentA destroyInner");
		
	}

	public void addChild(Component component) {

		if (component != null) {
			hashSetComponents.add(component);
		}

	}

}
