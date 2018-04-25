/**
 * 
 */
package algorithm.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * B树
 * 
 * @author mymai
 *
 */
public class BTree<K extends Comparable<K>, V> {

	private int m; // m叉树

	public BTree(int m) {
		this.m = m;
	}

	/**
	 * 根节点
	 */
	private BTreeNode<K, V> root = new BTreeNode();

	private class KeyEntry<K extends Comparable<K>, V> {
		private K k;
		private V v;

		public KeyEntry(K k, V v) {
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

		@Override
		public String toString() {
			return "KeyEntry [k=" + k + ", v=" + v + "]";
		}

	}

	private class BTreeNode<K extends Comparable<K>, V> {

		private boolean isLeaf = true; // 是否是叶子节点,默认为true

		private ArrayList<KeyEntry<K, V>> keyEntryLists; // 非叶子节点的key

		private List<BTreeNode<K, V>> childs; // 儿子节点

		BTreeNode<K, V> parent; // 父亲节点

		public void put(K k, V v) {
			// 判断key的size
			int size = keyEntryLists.size();
			KeyEntry<K, V> keyEntry = new KeyEntry<K, V>(k, v);
			if (size < m) {
				insertEntry(keyEntry);
			} else {
				// 切分
				split(keyEntry);
			}
		}

		public void split(KeyEntry<K, V> keyEntry) {
			System.out.println("-----------------split------------------");
			int middle = (m + 1) / 2;
			ArrayList<KeyEntry<K, V>> cloneList = (ArrayList<KeyEntry<K, V>>) keyEntryLists.clone();
			BTreeNode<K, V> leftNode = new BTreeNode<K, V>(); // 左边的
			BTreeNode<K, V> rightNode = new BTreeNode<K, V>(); // 右边的

			leftNode.parent = this;
			rightNode.parent = this;

			childs.add(leftNode);

			childs.add(rightNode);

			int i = 0;

			Iterator<KeyEntry<K, V>> iterator = cloneList.iterator();

			while (iterator.hasNext()) {
				KeyEntry<K, V> next = iterator.next();
				if (i++ < middle) {
					leftNode.insertEntry(next);
				} else {
					rightNode.insertEntry(next);
				}
			}

			rightNode.insertEntry(keyEntry);

			i = 0;

			// 删除原来的list中的元素
			Iterator<KeyEntry<K, V>> iterator2 = keyEntryLists.iterator();
			while (iterator2.hasNext()) {
				iterator2.next();
				if (i != 0 && i != middle) {
					iterator2.remove();
				}
				i++;
			}

			System.out.println("split 后的keyEntryLists:" + keyEntryLists.toString());

			// 设置为非叶子节点
			this.isLeaf = false;

		}

		public V get(K k) {

			// 先在当前节点里面找
			V v = findMyKeys(k);

			return v;
		}

		private V findMyKeys(K k) {
			Iterator<KeyEntry<K, V>> iterator = keyEntryLists.iterator();
			while (iterator.hasNext()) {
				KeyEntry<K, V> keyEntry = iterator.next();
				if (keyEntry.k.compareTo(k) == 0) {
					return (V) keyEntry.v;
				}
			}

			return null;
		}

		public BTreeNode() {
			keyEntryLists = new ArrayList<>();
			childs = new ArrayList<>();
		}

		public BTreeNode(BTreeNode parent) {
			this.parent = parent;
		}

		/**
		 * 从子节点中找出key的节点
		 * 
		 * @param k
		 * @return
		 */
		public BTreeNode<K, V> findNodeFromChild(K k) {
			Iterator<BTreeNode<K, V>> iterator = childs.iterator();
			while (iterator.hasNext()) {
				BTreeNode<K, V> next = iterator.next();
				KeyEntry<K, V> keyentry = (KeyEntry<K, V>) next.keyEntryLists.get(0);
				if (keyentry.k.compareTo(k) == 0)
					return next;
			}
			return null;
		}

		public void insertEntry(KeyEntry<K, V> keyentry) {

			if (keyEntryLists.size() == 0) {
				keyEntryLists.add(keyentry);
			} else {
				for (int i = 0; i < keyEntryLists.size(); i++) {
					KeyEntry<K, V> keyEntry2 = keyEntryLists.get(i);
					K k = (K) keyEntry2.getK();
					if (k.compareTo(keyentry.k) >= 0) {
						// 如果当前是叶子节点，直接插入
						if (isLeaf) {
							keyEntryLists.add(i, keyentry);
							break;
						} else {
							BTreeNode<K, V> findNodeFromChild = findNodeFromChild(k);
							findNodeFromChild.insertEntry(keyentry);
						}

					} else if (k.compareTo(keyentry.k) < 0) {
						// 如果当前是叶子节点，直接插入
						if (isLeaf) {
							keyEntryLists.add(keyentry);
							break;
						} else {// 找到子节点插入
							BTreeNode<K, V> findNodeFromChild = findNodeFromChild(k);
							findNodeFromChild.insertEntry(keyentry);
						}

					}
				}
			}

			System.out.println(this.hashCode() + "插入后的list:" + keyEntryLists.toString());
		}
	}

	public void put(K k, V v) {
		root.put(k, v);
	}

	public V get(K k) {
		return root.get(k);
	}

	public static void main(String[] args) {
		BTree<Integer, String> btree = new BTree<Integer, String>(3);
		btree.put(1, "a");
		btree.put(2, "b");
		btree.put(3, "c");

		btree.put(4, "e");
		btree.put(5, "f");
		String a = btree.get(3);
		System.out.println(a);

	}

}
