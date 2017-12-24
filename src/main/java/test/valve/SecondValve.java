package test.valve;

public class SecondValve implements Valve<String> {
	
	private Valve<String> v;

	public void setNext(Valve<String> v) {
		this.v = v;
	}

	public Valve<String> getNext() {
		return v;
	}

	public void invoke(String v) {
		String replace = v.replace("a", "m");
		getNext().invoke(replace);
		
	}

}
