package datastructrue.map1;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author az6367
 * 
 * 采用链接散列法实现的哈希表
 *
 * @param <K>
 * @param <V>
 */
public class HashMap<K, V> implements Map<K, V> {

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

	private float loadfactor = 0.75f;

	private Node<K, V>[] table = null;

	private int size = 0;

	private int capacity = 1;

	private int d = 0;

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

		for (int i = 0; i < capacity; i++) {
			Node<K, V> node = table[i];
			if (node == null)
				continue;
			System.out.print("�?" + i + "�?:\t");
			for (Node temp = node; temp != null; temp = temp.next) {
				System.out.print(temp + "\t");
			}

			System.out.println();
		}

	}

	public static void main(String[] args) {
		/*HashMap<String, Integer> hh = new HashMap<String, Integer>();
		hh.put("a", 1);
		hh.put("b", 2);
		hh.put("c", 3);
		hh.put("d", 4);
		hh.put("e", 5);

		hh.put("m", 6);
		hh.put("n", 7);
		hh.put("x", 8);
		hh.put("y", 9);

		hh.printAll();*/
		
		
		
	}

}
