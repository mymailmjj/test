package datastructrue.map;

public interface Map<K, V> {
	
	public void put(K k,V v);
	
	public V get(K k);
	
	public boolean remove(K k);

}
