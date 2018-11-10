/**
 * 
 */
package datastructrue.tree1;

import java.util.Arrays;

import org.perf4j.log4j.Log4JStopWatch;

/**
 * 这是一个B+树
 * 
 * @author az6367
 *
 */
public class BLinkedNodeTree<K extends Comparable<K>> {

	private int phase = 8; // 树的阶

	private Node<K> root; // 根节点

	private int half_pahse = phase / 2; // 初始化节点内部数组元素的个数

	static int gen_id = 1;

	/**
	 * B+树节点的结构
	 * 
	 */
	class Node<U extends K> {

		private Object[] keys;
		private int keysize = 0;
		private Object[] values;
		private Node<U>[] childrens;
		private Node<U> parent; // 父节点
		private boolean isRoot = false; // 是否是根节点
		private boolean isLeaf = false; // 是否是叶子节点
		private boolean isNonLeaf = false; // 是否是非叶子节点非根节点
		private Node<U> next; // 链接指针
		private int id;

		public Node<U> getParent() {
			return parent;
		}

		public void setParent(Node<U> parent) {
			this.parent = parent;
		}

		public Node() {
			id = gen_id++;
		}

		public Node(Node<U> next) {
			this.next = next;
		}

		public Node(Object[] keys, Object[] values) {
			this.keys = keys;
			this.values = values;
		}

		public boolean isRoot() {
			return isRoot;
		}

		public void setRoot(boolean isRoot) {
			this.isRoot = isRoot;
		}

		public boolean isLeaf() {
			return isLeaf;
		}

		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}

		public boolean isNonLeaf() {
			return isNonLeaf;
		}

		public void setNonLeaf(boolean isNonLeaf) {
			this.isNonLeaf = isNonLeaf;
		}

		public Node<U> getNext() {
			return next;
		}

		public void setNext(Node<U> next) {
			this.next = next;
		}

	}

	public void insertNode(K key, Object e) {
		if (root == null) {
			root = new Node<K>();
			initNode(root);
			root.setRoot(true); // 开始只有一个节点，既是根节点，也是叶节点
			root.setLeaf(true);
			root.keys[0] = key;
			root.values[0] = e;
		} else {
			insert(root, key, e);
		}

	}

	/**
	 * 节点内部元素复制
	 * 
	 * @param sourceNode
	 * @param targetNode
	 * @param fromIndexSrc
	 * @param toIndexSrc
	 * @param length
	 */
	private void splitCopyNode(BLinkedNodeTree<K>.Node<K> sourceNode, BLinkedNodeTree<K>.Node<K> targetNode, int fromIndexSrc,
			int toIndexSrc, int length) {
		// keys
		System.arraycopy(sourceNode.keys, fromIndexSrc, targetNode.keys, toIndexSrc, length);
		// values
		System.arraycopy(sourceNode.values, fromIndexSrc, targetNode.values, toIndexSrc, length);
		// childrens
		System.arraycopy(sourceNode.childrens, fromIndexSrc, targetNode.childrens, toIndexSrc,
				sourceNode.childrens.length / 2);

		resetChildNode(targetNode);
	}

	/**
	 * 重新设置复制的儿子节点的父子关系
	 * 
	 * @param parentNode
	 */
	private void resetChildNode(BLinkedNodeTree<K>.Node<K> parentNode) {
		Node<K>[] childrens = parentNode.childrens;
		for (int i = 0; i < childrens.length; i++) {
			Node<K> node = childrens[i];
			if (node != null) {
				node.setParent(parentNode);
			}

		}
	}

	/**
	 * 节点初始化
	 * 
	 * @param node
	 */
	private void initNode(BLinkedNodeTree<K>.Node<K> node) {
		node.keys = new Object[half_pahse + 1];
		node.values = new Object[half_pahse + 1];
		node.childrens = new Node[2 * phase];
	}

	/**
	 * 节点的元素超过限制后进行切分,次方法内部可能会递归调用
	 * 
	 * @param node
	 */
	private void split(BLinkedNodeTree<K>.Node<K> node) {

		// 先切分当前节点
		int m = node.keys.length / 2;

		Node<K> leftnode = new Node<K>();

		initNode(leftnode);

		splitCopyNode(node, leftnode, 0, 0, m);

		Node<K> rightNode = new Node<K>();

		initNode(rightNode);

		if (node.isLeaf) {
			splitCopyNode(node, rightNode, m, 0, node.keys.length - m);
		} else {
			splitCopyNode(node, rightNode, m, 0, node.keys.length - m);
		}

		if (node.next != null) {
			rightNode.setNext(node.next);
		}

		if (countSize(node.childrens) == 0) {
			rightNode.setLeaf(true);
			leftnode.setLeaf(true);
			leftnode.setNext(rightNode); // B+树叶子节点设置节点关系

		}

		// 查看父亲节点，如果如空，则父节点是根节点
		if (node.parent == null) {
			Node<K> root = new Node<K>();
			root.setRoot(true);
			this.root = root;
			node.setParent(root);
			initNode(root);
			root.keys[0] = node.keys[m];
			root.values[0] = node.values[m];
			root.childrens[0] = leftnode;
			root.childrens[1] = rightNode;
			leftnode.setParent(root);
			rightNode.setParent(root);
		}

		// 不是空，则递归调用，切分方法
		else {

			K mk = (K) node.keys[m];
			Node<K> p = node.parent;

			leftnode.setParent(p);

			rightNode.setParent(p);

			Object[] keys = p.keys;
			int countSize = countSize(keys);

			if (countSize == half_pahse + 1 && keys.length == half_pahse + 1) {
				// 扩容
				ensureCapacity(p, phase + 1);
			}

			// 正常插入
			int findInsertIndex = findInsertIndex(mk, p.keys);

			insertAtIndex(findInsertIndex, mk, p.keys);

			countSize = countSize(p.keys);

			if (countSize == half_pahse + 1 && keys.length == half_pahse + 1) {
				// 扩容
				ensureCapacity(p, phase + 1);
			}

			removeAtIndex(node, p.childrens);

			insertAtIndex(findInsertIndex, leftnode, p.childrens);

			countSize = countSize(p.keys);

			if (countSize == half_pahse + 1 && keys.length == half_pahse + 1) {
				// 扩容
				ensureCapacity(p, phase + 1);
			}

			insertAtIndex(findInsertIndex + 1, rightNode, p.childrens);

			if (countSize(p.keys) > phase) {
				split(p);
			}

		}

	}

	/**
	 * 在数组的合适位置，插入元素，保证是升序排列
	 * 
	 * @param index
	 * @param o
	 * @param obj
	 */
	private void insertAtIndex(int index, Object o, Object[] obj) {
		int p = obj.length - 1;
		while (p > index) {
			obj[p] = obj[p - 1];
			p--;
		}

		obj[index] = o;
	}

	/**
	 * 在数据中删除这个元素
	 * 
	 * @param obj
	 *            被删除元素
	 * @param objArray
	 *            操作的数组
	 */
	private void removeAtIndex(Object obj, Object[] objArray) {
		int p = 0;
		boolean founder = false;
		while (p < objArray.length - 1) {
			if (objArray[p] == obj) {
				founder = true;
			}
			if (founder) {
				objArray[p] = objArray[p + 1];
			}

			p++;
		}

		objArray[objArray.length - 1] = null;

	}

	/**
	 * 在这个节点位置开始插入key value为e的元素
	 * 
	 * @param node
	 *            插入的位置
	 * @param key
	 *            插入的key
	 * @param e
	 *            插入的value
	 */
	private void insert(BLinkedNodeTree<K>.Node<K> node, K key, Object e) {

		Node<K>[] childrens = node.childrens;

		if (countSize(childrens) != 0) {// 在儿子节点里面处理
			// 找儿子节点插入的位置
			Object[] keys = node.keys;
			int findInsertIndex = findInsertIndex(key, keys);
			Node<K> inserNode = childrens[findInsertIndex];
			insert(inserNode, key, e);

		} else {
			Object[] keys = node.keys;
			int countSize = countSize(keys);
			if (countSize == 0) {
				initNode(node);
				node.keys[0] = key;
				node.values[0] = e;
				node.setLeaf(true);
			} else {

				// 先正常插入
				if (countSize == half_pahse + 1 && keys.length == half_pahse + 1) {
					// 扩容
					ensureCapacity(node, phase + 1);
				}
				// 正常插入
				int findInsertIndex = findInsertIndex(key, node.keys);

				insertAtIndex(findInsertIndex, key, node.keys);

				insertAtIndex(findInsertIndex, e, node.values);

				if (countSize(node.keys) > phase) {
					split(node);
				}
			}

		}

	}

	/**
	 * 定位元素在数组中的位置
	 * 
	 * @param o
	 * @param obj
	 * @return
	 */
	private int findPosition(Object o, Object[] obj) {
		int j = -1;
		for (int i = 0; i < obj.length; i++) {
			Object object = obj[i];
			if (object == null)
				break;
			if (object == o) {
				j = i;
				break;
			}

		}

		return j;
	}

	/**
	 * 定位元素在数组中的插入的位置
	 * 
	 * @param k
	 * @param obj
	 * @return
	 */
	private int findInsertIndex(K k, Object[] obj) {
		int i = 0;
		for (; i < obj.length; i++) {
			K object = (K) obj[i];
			if (object == null)
				break;
			if (object.compareTo(k) > 0)
				break;
		}

		return i;
	}

	/**
	 * 计算数组中非空元素的个数
	 * 
	 * @param obj
	 * @return
	 */
	private int countSize(Object[] obj) {
		if (obj == null)
			return 0;
		int j = 0;
		Object o = obj[0];
		while (o != null) {
			j++;
			if (j > obj.length - 1)
				break;
			o = obj[j];
		}
		return j;
	}

	/**
	 * 对节点的内部数据进行扩容
	 * 
	 * @param node
	 * @param capacity
	 */
	private void ensureCapacity(BLinkedNodeTree<K>.Node<K> node, int capacity) {
		if (node == null)
			throw new IllegalArgumentException();
		if (capacity < node.keys.length)
			throw new IllegalArgumentException();
		if (node.keys.length < capacity) {
			Object[] newKeys = new Object[capacity];
			Object[] newValues = new Object[capacity];
			System.arraycopy(node.keys, 0, newKeys, 0, node.keys.length);
			System.arraycopy(node.values, 0, newValues, 0, node.values.length);
			node.keys = newKeys;
			node.values = newValues;
		}
	}

	/**
	 * 在树种查找某个key的元素对应的value
	 * 
	 * @param node
	 * @param k
	 * @return
	 */
	public Object findNode(Node<K> node, K k) {
		if (node.isLeaf) {
			// 这里是叶子节点,叶子节点只能有一个值
			if (node.keys != null) {
				int findPosition = findPosition(k, node.keys);
				return node.values[findPosition];
			}

			return null;

		} else {
			// 当前节点不是叶子节点
			int i;
			for (i = 0; i < node.keys.length;) {
				K iteK = (K) node.keys[i];
				if (iteK == null)
					break;
				if (iteK.compareTo(k) <= 0) {
					i++;
				} else {
					break;
				}
			}

			if (i > node.childrens.length)
				return null;

			return findNode(node.childrens[i], k);

		}

	}

	/**
	 * 在树种查找某个key的元素对应的value
	 * 
	 * @param k
	 * @return
	 */
	public Object findNode(K k) {
		return findNode(root, k);
	}

	/**
	 * 打印树结构
	 * 
	 */
	public void printTree() {
		printNode(root);
	}

	private void printNode(Node<K> node) {
		if (node != null) {
			System.out.print("\n\t" + Arrays.toString(node.keys) + "\t是否是根节点:" + node.isRoot + "\t是否是叶子节点:"
					+ node.isLeaf + "\tid:" + node.id + "\tpid:");

			if (node.getParent() != null) {
				System.out.print(node.getParent().id);
			}

			if (node.next != null) {
				System.out.println("\tnext:" + node.next.id);
			}

			Node<K>[] childrens = node.childrens;
			if (childrens != null)
				for (int i = 0; i < childrens.length; i++) {
					Node<K> node2 = childrens[i];
					printNode(node2);
				}
		}
	}

	public static void main(String[] args) {
		
		Log4JStopWatch log4jStopWatch = new Log4JStopWatch();
		
		log4jStopWatch.start();

		BLinkedNodeTree<Integer> tree = new BLinkedNodeTree<>();

		tree.insertNode(500, "a");
		int j = 50;
		for (int i = 1; i < 10000; i++) {
			int m = i % 2 == 0 ? 1 : -1;
			int n = j + i * m;
			Character c = new Character((char) (97 + i));
			tree.insertNode(n, c);
		}
		
		log4jStopWatch.stop();
		
		System.out.println("\ninsert time:"+log4jStopWatch.getElapsedTime());
		
//		tree.printTree();
		
		log4jStopWatch.start();
		
		Object value = tree.findNode(31);
		
		log4jStopWatch.stop();
		
		System.out.println("\nfinished time:"+log4jStopWatch.getElapsedTime());

		System.out.println();

		System.out.println(value);

	}

}
