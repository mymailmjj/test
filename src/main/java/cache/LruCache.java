/**
 * 
 */
package cache;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import bo.User;

/**
 * 
 * LRU-K
 * 
 * @author mujjiang
 * 
 */
public class LruCache<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 记录hashmap的数据的方位次数
	 * 
	 */
	private ConcurrentHashMap<K, Integer> count = new ConcurrentHashMap<K, Integer>();

	private Cache<K, V> cache; // 上一级缓存

	public LruCache(int size, int threadhold, Cache<K, V> cache) {
		this(size);
		this.cache = cache;
		this.threadhold = threadhold;
	}

	public LruCache(int size, int threadhold) {
		this(size, threadhold, null);
		cache = new OrderCache<K, V>(this.size);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int size = 0;

	private int threadhold = 0;

	private Lock lock = new ReentrantLock();

	private void initCount(K k) {
		Integer i = count.get(k);
		if (i == null) {
			count.put(k, 0);
		}
	}

	private void increaseQueryCount(K k) {
		Integer i = count.get(k);
		if (i != null) {
			count.put(k, i + 1);
		}
	}

	private void removeKeyCount(K k) {
		count.remove(k);
	}

	public LruCache(int size) {
		super(size, 0.75f, Boolean.TRUE);
		this.size = size;
	}

	@Override
	public V get(Object key) {
		lock.lock();

		//先在一级缓存里面找
		V v = super.get(key);
		
		//当前缓存没有找到，则在二级缓存查找
		if(v==null){
			v = this.cache.get((K)key);
		}
		
		increaseQueryCount((K) key);
		addSecondCache((K) key,v);

		lock.unlock();
		return v;
	}

	/**
	 * 判断是否进入二级缓存
	 * 
	 * @param k
	 */
	private void addSecondCache(K k,V v) {

		Integer i = count.get(k);

		// 如果达到了阈值，则进入二级缓存
		if (i >= this.threadhold) {
			V v2 = cache.get(k);
			if (v2 == null) {
				cache.put(k, v);
			}
		}
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		boolean toDeleted = super.size() > this.size;

		if (toDeleted) {
			removeKeyCount(eldest.getKey());
		}

		return toDeleted;
	}

	@Override
	public V put(K key, V value) {
		lock.lock();
		initCount(key);
		V v = super.put(key, value);
		lock.unlock();
		return v;
	}

	public ConcurrentHashMap<K, Integer> getCount() {
		return count;
	}

	public void setCount(ConcurrentHashMap<K, Integer> count) {
		this.count = count;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static void main(String[] args) {

		LruCache<String, User> cache = new LruCache<String, User>(5);
		for (int i = 0; i < 20; i++) {
			User user = new User();
			user.setId(i);
			user.setName("zhangsan" + i);
			cache.put("zhangsan" + i, user);
			System.out.println(cache);

		}

		User user = cache.get("zhangsan18");
		System.out.println("--------------------------------");

		ConcurrentHashMap<String, Integer> count2 = cache.getCount();

		Integer integer = count2.get("zhangsan18");

		System.out.println("--------------------------------integer" + integer);

		System.out.println(cache);

		User user1 = cache.get("zhangsan18");
		System.out.println("--------------------------------");

		Integer integer1 = count2.get("zhangsan18");

		System.out
				.println("--------------------------------integer" + integer1);

		System.out.println(cache);

		User user3 = cache.get("zhangsan15");
		System.out.println("--------------------------------");

		Integer integer2 = count2.get("zhangsan15");

		System.out
				.println("--------------------------------integer" + integer2);

		System.out.println(cache);

	}

}
