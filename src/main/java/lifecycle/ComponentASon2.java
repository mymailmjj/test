/**
 * 
 */
package lifecycle;

/**
 * @author cango
 *
 */
public class ComponentASon2 extends AbstractComponent{

	@Override
	protected void initInner() {
		super.initInner();
		System.out.println("ComponentASon2 initInner");
	}

	@Override
	protected void startInner() {
		super.startInner();
		System.out.println("ComponentASon2 startInner");
		
	}

	@Override
	protected void destroyInner() {
		super.destroyInner();
		System.out.println("ComponentASon2 destroyInner");
		
	}
	
	
	
	

}
