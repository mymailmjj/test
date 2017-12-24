/**
 * 
 */
package test.valve;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * @author mymai
 *
 */
public class PileLine implements Pipe<String> {
	
	private Valve<String> basiceValve;
	
	private Valve<String> firstValuve;

	/* (non-Javadoc)
	 * @see test.valve.Pipe#setBasic(test.valve.Valve)
	 */
	public void setBasic(Valve<String> v) {
		basiceValve = v;

	}

	/* (non-Javadoc)
	 * @see test.valve.Pipe#addValve(test.valve.Valve)
	 */
	public void addValve(Valve<String> v) {
		if(firstValuve==null){
			firstValuve=v;
		}else{
			//找到最后一个去设置末尾
			Valve<String> next = firstValuve;
			while((next.getNext())!=basiceValve){
				next = next.getNext();
				break;
			}
			
			next.setNext(v);
			
		}
		
		v.setNext(this.getBasic());
	}

	public Valve<String> getBasic() {
		return basiceValve;
	}

	public Valve<String> getFirst() {
		return firstValuve;
	}
	
	public static void main(String[] args) {
		
		String str = "abcdef";
		
		PileLine p = new PileLine();
		
		BasicValve basicValve = new BasicValve();
		
		SecondValve secondValve = new SecondValve();
		
		ThirdValve thirdValve = new ThirdValve();
		
		p.setBasic(basicValve);
		
		p.addValve(secondValve);
		
		p.addValve(thirdValve);
		
		p.getFirst().invoke(str);
		
		
	}

}
