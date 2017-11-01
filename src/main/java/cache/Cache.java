/**
 * 
 */
package cache;

/**
 * @author mujjiang
 *
 */
public interface Cache<K,V> {
	
	public void put(K k,V v);
	
	public V get(K k);
	
	public int size();

}
