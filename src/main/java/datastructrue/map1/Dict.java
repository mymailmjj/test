/**
 * 
 */
package datastructrue.map1;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * 字典
 * 按照redis中的dict结构编写
 * @author az6367
 *
 */
public class Dict<K,V> implements Map<K, V> {
	
	static class Node<T, U> {
		private T t;
		private U u;
		private Node<T, U> next;
		private Node<T, U> prev;

		public Node(Node<T, U> node) {
			this(node.t, node.u);
		}

		public Node(T t, U u) {
			this(t, u, null);
		}

		public Node(T t, U u, Node<T, U> next) {
			this(t, u, next, null);
		}

		public Node(T t, U u, Node<T, U> next, Node<T, U> prev) {
			this.t = t;
			this.u = u;
			this.next = next;
			this.prev = prev;
		}

		public T getT() {
			return t;
		}

		public void setT(T t) {
			this.t = t;
		}

		public U getU() {
			return u;
		}

		public void setU(U u) {
			this.u = u;
		}

		public Node<T, U> getNext() {
			return next;
		}

		public void setNext(Node<T, U> next) {
			this.next = next;
		}

		@Override
		public String toString() {
			return "Node [" + (t != null ? "t=" + t + ", " : "") + (u != null ? "u=" + u + ", " : "")
					+ (next != null ? "next.t=" + next.t : "") + "]";
		}

	}
	
	

	public Dict() {
		this.hashmap = new HashMap[2];
		this.hashmap[0] = new HashMap<>();
		this.hashmap[1] = new HashMap<>();
	}


	private int size;
	
	private HashMap<K,V>[] hashmap;
	
	private int index = -1;
	
	private int inuse = 0;
	
	private int capacity = 1;
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean containsKey(Object key) {
		return hashmap[0].containsKey(key)||hashmap[1].containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return hashmap[0].containsValue(value)||hashmap[1].containsValue(value);
	}

	@Override
	public V get(Object key) {
		return hashmap[0].get(key)!=null?hashmap[0].get(key):hashmap[1].get(key);
	}
	

	@Override
	public V put(K key, V value) {
		
		if(this.hashmap[inuse]==null){
			this.hashmap[inuse] = new HashMap<K,V>();
		}

		this.hashmap[inuse].put(key, value);

		// 检查是否需要迁移
		// 开始默认使用ht[0]
		if (this.hashmap[0].isBeyongThiled()) {
			hashmap[1].expandCapacity();
			this.inuse = 1;

		}
		if (this.hashmap[1].isBeyongThiled()) {
			hashmap[0].expandCapacity();
			this.inuse = 0;
			// 进行迁移 从1到0
			hashmap[1].shiftToHashmap(hashmap[0]);

		}

		if (this.inuse == 0 && hashmap[1].size > 0) {
			// 进行迁移 从1到0
			hashmap[1].shiftToHashmap(hashmap[0]);
		}

		if (this.inuse == 1 && hashmap[0].size > 0) {
			// 进行迁移 从0到1
			hashmap[0].shiftToHashmap(hashmap[1]);
		}

		size++;

		return value;
	}

	@Override
	public V remove(Object key) {
		
		hashmap[0].remove(key);
		
		hashmap[1].remove(key);
		
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		if(hashmap[0]!=null) hashmap[0].clear();
		if(hashmap[1]!=null) hashmap[1].clear();
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void printAll(){
		hashmap[0].printAll();
		System.out.println("**********************第二个map*********************************");
		hashmap[1].printAll();
		System.out.println("capacity:"+this.capacity);
	}
	
	
	class HashMap<K, V> implements Map<K, V> {

		private float loadfactor = 0.75f;

		private Node<K, V>[] table = null;

		private int size = 0;

		private int d = 0;
		
		public float getLoadfactor() {
			return loadfactor;
		}

		public void setLoadfactor(float loadfactor) {
			this.loadfactor = loadfactor;
		}

		public Node<K, V>[] getTable() {
			return table;
		}

		public void setTable(Node<K, V>[] table) {
			this.table = table;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getCapacity() {
			return capacity;
		}

		public int getD() {
			return d;
		}

		public void setD(int d) {
			this.d = d;
		}

		private int hash(K k) {
			String string = k.toString();
			char[] charArray = string.toCharArray();
			int i = 1;
			int sum = 0;
			for (int j = 0; j < charArray.length; j++) {
				char c = charArray[j];
				sum += ((int) c) * 3 * ++i;
			}
			return sum;
		}
		
		public boolean isBeyongThiled(){
			d = (int) (capacity * loadfactor);
			return this.size > d;
		}

		private int index(K k) {
			int index = hash(k) % table.length;
			return index;
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean isEmpty() {
			return size != 0;
		}
		
		//供外部使用
		public void expandCapacity(){
			int ncapacity = 2 * capacity;
			Node<K, V>[] ntable = new Node[ncapacity];
			table = ntable;
			capacity = ncapacity;
			d = (int) (capacity * loadfactor);
		}
		
		
		/**
		 * 把当前map的数据移到另外一个map,每次只迁移一个
		 * @param hashmap
		 */
		public void shiftToHashmap(HashMap<K, V> hashmap) {

			if (size == 0)
				return;
			
			if(index==-1) index=0;
			
			if(index>table.length-1) {
				index = -1;
				return;
			}

			Node<K, V> node = table[index];

			if (node == null) {
				index++;
				return;
			}

			hashmap.put((K) node.t, (V) node.u);

			if (node.next == null) {
				table[index] = null;
				index++;
				size--;
				return;
			}

			table[index] = node.next;

			// 重置参数
			size--;

		}
		

		private void ensureCapacity() {

			int ncapacity = 2 * capacity;

			Node<K, V>[] ntable = new Node[ncapacity];
			// 把原来的数组都拷贝进�?
			for (int i = 0; i < capacity; i++) {
				Node<K, V> node = table[i];
				if (node == null)
					continue;
				for (Node temp = node; temp != null; temp = temp.next) {
					int index = index((K) temp.getT()) % (ncapacity);
					if (ntable[index] == null) {
						Node<K, V> n = new Node<K, V>(temp);
						ntable[index] = n;
					} else {

						Node<K, V> temp1;

						for (temp1 = ntable[index];; temp1 = temp1.next) {
							if (temp1.next == null)
								break;
						}

						Node<K, V> n = new Node<K, V>(temp);

						insertNodeAfterThisNode(temp1, n);
					}
				}
			}

			table = ntable;
			capacity = ncapacity;
			d = (int) (capacity * loadfactor);

		}

		@Override
		public boolean containsKey(Object key) {

			int index = index((K) key);

			Node<K, V> node = table[index];

			while (node != null) {

				if (node.t.equals(key))
					return Boolean.TRUE;

				node = node.next;
			}

			return Boolean.FALSE;
		}

		@Override
		public boolean containsValue(Object value) {

			for (int i = 0; i < size; i++) {
				Node<K, V> node = table[i];
				for (Node temp = node; temp != null; temp = temp.next) {
					if (temp.getU().equals(value))
						return true;
				}
			}

			return false;
		}

		@Override
		public V get(Object key) {
			int index = index((K) key);

			Node<K, V> node = table[index];

			while (node != null) {

				if (node.t.equals(key))
					return node.getU();

				node = node.next;
			}

			return node != null ? node.getU() : null;
		}

		private void insertNodeAfterThisNode(Node<K, V> currentNode, Node<K, V> insertNode) {

			if (currentNode.next != null) {
				currentNode.next.prev = insertNode;
				insertNode.next = currentNode.next;
			}
			currentNode.next = insertNode;
			insertNode.prev = currentNode;

		}

		@Override
		public V put(K key, V value) {

			recaculateD();

			Node node = new Node(key, value);

			if (table == null) {
				table = new Node[capacity];
				table[0] = node;
				size++;
				return (V) node.getU();
			} else if (table != null && size > d) {
				// 先放大数组，然后放在适合的位�?
				ensureCapacity();

			}

			if (table != null && size <= d) {
				int p = index(key);
				Node<K, V> temp;

				boolean insertR = false;

				for (temp = table[p];; temp = temp.next) {
					if (temp == null) {
						table[p] = node;
						insertR = Boolean.TRUE;
						break;
					}

					if (temp.getU().equals(node.getU())) {
						return (V) node.getU();
					}

					if (temp.next == null)
						break;

				}

				if (!insertR) {
					insertNodeAfterThisNode(temp, node);
					size++;
					return (V) node.getU();
				}
			}

			// 处理计数
			size++;

			return value;
		}

		@Override
		public V remove(Object key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			// TODO Auto-generated method stub

		}

		private void recaculateD() {
			d = (int) (capacity * loadfactor);
		}

		@Override
		public void clear() {
			table = new Node[1];
			size = 0;
			capacity = 1;
			recaculateD();
		}

		@Override
		public Set<K> keySet() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<V> values() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<java.util.Map.Entry<K, V>> entrySet() {
			// TODO Auto-generated method stub
			return null;
		}

		public void printAll() {
			
			if(table==null) return;
			
			for (int i = 0; i < table.length; i++) {
				Node<K, V> node = table[i];
				if (node == null)
					continue;
				System.out.print("第" + i + "行:\t");
				for (Node temp = node; temp != null; temp = temp.next) {
					System.out.print(temp + "\t");
				}

				System.out.println();
			}

		}
		
	}
	
	
	public static void main(String[] args) {
		
		long currentTimeMillis = System.currentTimeMillis();
		
		int number = 1000;
		
		Dict<Integer, Integer> dict = new Dict<>();
		
		for(int i = 0; i < number; i++){
			dict.put(i, i);
		}
		
		System.out.println("cost:"+(System.currentTimeMillis()-currentTimeMillis));
		
		long currentTimeMillisnn = System.currentTimeMillis();
		Integer integer = dict.get(500);
		System.out.println("get cost:"+(System.currentTimeMillis()-currentTimeMillisnn)+"\t"+integer);
		
		
		long currentTimeMillisn = System.currentTimeMillis();
		
		java.util.HashMap<Integer, Integer> hash = new java.util.HashMap<Integer, Integer>();
		
		for(int i = 0; i < number; i++){
			hash.put(i, i);
		}
		
		System.out.println("cost:"+(System.currentTimeMillis()-currentTimeMillisn));
		
		long currentTimeMillisnnn = System.currentTimeMillis();
		Integer integer1 = hash.get(500);
		System.out.println("get cost1:"+(System.currentTimeMillis()-currentTimeMillisnnn)+"\t"+integer1);
		
		
		
	}
	

}
