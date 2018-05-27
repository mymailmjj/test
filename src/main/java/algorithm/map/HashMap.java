/**
 * 
 */
package algorithm.map;

/**
 * @author mymai
 *
 */
public class HashMap<K,V> implements Map<K,V>{
	
	private final static int DEFAULT_SIZE = 16;
	
	private Entry<K,V>[] entries = null;
	
	static class Entry<K,V>{
		private K k;
		private V v;
		public Entry(K k, V v) {
			this.k = k;
			this.v = v;
		}
		public K getK() {
			return k;
		}
		public void setK(K k) {
			this.k = k;
		}
		public V getV() {
			return v;
		}
		public void setV(V v) {
			this.v = v;
		}
		
	}
	
	
	public HashMap(Entry[] entries) {
		this.entries = new Entry[DEFAULT_SIZE];
	}

	@Override
	public void put(Object k, Object v) {
		
		if(k==null) return;
		
		
		
		//先求hashCode
		int hashCode = k.hashCode();
		
		//求出在数组中的位置
		int location = hashCode%DEFAULT_SIZE;
		
		//如果冲突，解决冲突
		Entry entry = entries[location];
		
		if(entry!=null){
			
		}else{
			Entry newEntry = new Entry(k,v);
			entries[location] = newEntry;
		}
		
	}

	@Override
	public Object get(Object k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object k) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args) {
		
		int a = 1;
		
		if(a==1|(a=2)==2){
			System.out.println(a);
		}
		
		
		
	}
	

}
