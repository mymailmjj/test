/**
 * 
 */
package cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import bo.User;

/**
 * @author mujjiang
 * 
 */
public class OrderCache<K, V> implements Cache<K, V> {

	private Entry<K, V> head;

	private int size;

	private Lock lock = new ReentrantLock();

	private Cache<K, V> cache;

	public OrderCache(int size, Cache<K, V> cache) {
		this(size);
		this.cache = cache;
	}

	public OrderCache() {
		this(5);
	}

	public OrderCache(int size) {
		this.size = size;
		init();
	}

	private void init() {
		head = new Entry<K, V>(null, null, -1);
	}

	private class Entry<K, V> {

		private K k;
		private V v;
		private int order = 1;
		private Entry<K, V> prev;
		private Entry<K, V> next;

		public Entry(K k, V v) {
			this.k = k;
			this.v = v;
		}

		public Entry(K k, V v, int order) {
			this(k, v);
			this.order = order;
		}

	}

	/**
	 * 
	 * 按顺序在前面加入
	 * 
	 * @param node
	 * @return
	 */
	public boolean addFront(Entry<K, V> node) {
		lock.lock();

		Entry<K, V> temp = this.head;
		while (temp != null) {
			if (temp.order < node.order) {
				// 这里插入
				if (temp.prev != null) {
					temp.prev.next = node;
					temp.prev = node;
					node.next = temp;
					node.prev = temp.prev;
				} else {
					// 如果前面为空，则是在头部直接加入
					addHead(node);
				}

				size++;
				break;
			}

			temp = temp.next;
		}

		lock.unlock();
		return Boolean.TRUE;
	}

	/**
	 * 查找某个k值得entry
	 * 
	 * @param k
	 * @return
	 */
	public Entry<K, V> find(K k) {
		Entry<K, V> temp = head;
		while (temp != null) {

			if (temp.k!=null&&temp.k.equals(k)) {
				return temp;
			}

			temp = temp.next;
		}
		return null;
	}

	/**
	 * 
	 * 打印所有的all值
	 */
	public void printAll() {
		Entry<K, V> temp = head;
		System.out.println();
		while (temp != null) {
			System.out.print("\t\ttemp:" + temp.k+"\torder:"+temp.order);
			temp = temp.next;
		}
	}

	/**
	 * 重新排序
	 */
	public void reOrder(Entry<K, V> f) {
		
		Entry<K, V> newElement = new Entry<K, V>(f.k, f.v, ++f.order);
		remove(f);

		putFront(newElement.k, newElement.v, newElement.order);

	}

	/**
	 * 删除某个元素
	 * 
	 * @param k
	 * @return
	 */
	public boolean remove(K k) {
		Entry<K, V> find = find(k);
		return remove(find);
	}

	/**
	 * 删除某个元素
	 * 
	 * @param k
	 */
	private boolean remove(Entry<K, V> find) {

		if (find == null)
			return false;

		if (find.prev == null) {
			find = find.next;
			find.prev = null;
			this.head = find;
		} else {
			find.prev.next = find.next;
		}

		if (find.next == null) {
			find = find.prev;
		} else {
			find.next.prev = find.prev;
		}
		
		return true;

	}

	public void clear() {
		init();
	}

	/**
	 * 头部加入
	 * 
	 * @return
	 */
	public boolean addHead(Entry<K, V> node) {
		lock.lock();
		node.next = this.head;
		this.head.prev = node;
		this.head = node;
		size++;
		lock.unlock();
		return Boolean.TRUE;
	}

	/**
	 * 尾部加入
	 * 
	 * @return
	 */
	public boolean addTail(Entry<K, V> node) {
		lock.lock();

		Entry<K, V> temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}

		node.prev = temp;
		temp.next = node;

		size++;
		lock.unlock();
		return Boolean.TRUE;
	}

	public void put(K k, V v) {
		lock.lock();

		// 先组件对象
		Entry<K, V> insertNode = new Entry<K, V>(k, v);
		addFront(insertNode);
		lock.unlock();
	}

	@Deprecated
	public void putFront(K k, V v, int o) {
		lock.lock();
		Entry<K, V> entry = new Entry<K, V>(k, v, o);
		addFront(entry);

		lock.unlock();
	}

	public V get(K k) {

		lock.lock();

		Entry<K, V> f = find(k);

		if (f != null) {
			reOrder(f);
		}
		
		System.out.println("\n访问二级缓存之后打印............");
		
		printAll();
		
		
		lock.unlock();

		return f != null ? f.v : null;
	}

	public int size() {
		return this.size;
	}

	public static void main(String[] args) {

		OrderCache<String, User> caches = new OrderCache<String, User>();

		String add5 = "zhangsan" + 5;

		User user5 = new User();

		user5.setId(1);

		user5.setName(add5);

		caches.put(add5, user5);

	/*	String add2 = "zhangsan" + 2;

		User user2 = new User();

		user2.setId(2);

		user2.setName(add2);

		caches.put(add2, user2);

		String add1 = "zhangsan" + 1;

		User user = new User();

		user.setId(1);

		user.setName(add1);

		caches.put(add1, user);

		String add4 = "zhangsan" + 4;

		User user4 = new User();

		user4.setId(4);

		user4.setName(add1);

		caches.put(add4, user4);

		caches.printAll();

		System.out.println("删除某个元素----------------------------:" + add2);

		caches.remove(add2);

		caches.printAll();

		System.out.println("删除某个元素----------------------------:" + add1);*/
		
		caches.printAll();

		caches.remove(add5);

		caches.printAll();
		
		User user3 = caches.get("zhangsan5");
		
		System.out.println("得到某个元素----------------------------:" + user3);

	}

}
