/**
 * 
 */
package datastructrue.tree1;

import java.util.HashMap;
import java.util.Map;

/**
 * 哈希树 基于质数分离算法:n个不同的质数可以“分辨”的连续整数的个数和他们的乘积相等。
 * 
 * @author az6367
 *
 */
public class HashTree<T, V> {

	private HashNode<T, V> root = new HashNode<T, V>();

	private int[] numbers = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

	public void insert(T key, V value) {
		if (key == null || value == null)
			return;

		HashNode<T, V> hashnode = new HashNode<T, V>(key, value);

		HashNode<T, V> temp = root;

		int i = 0;

		while (temp != null) {
			int h = hash(key) % numbers[i];
			hashnode.setHash(h);
			hashnode.setDepth(i);
			HashMap<Integer, HashNode<T, V>> childs = temp.getChilds();
			if (childs == null) {
				HashMap<Integer, HashNode<T, V>> lists = new HashMap<Integer, HashNode<T, V>>();
				lists.put(h, hashnode);
				temp.setChilds(lists);
				break;
			} else {
				HashNode<T, V> hashNode2 = childs.get(h);
				if (hashNode2 == null) {
					childs.put(h, hashnode);
					break;
				} else {
					temp = hashNode2;
				}
			}

			i++;
		}
	}

	public V find(T key) {

		HashNode<T, V> temp = root;

		int i = 0;

		while (temp != null) {
			int h = hash(key) % numbers[i];
			HashMap<Integer, HashNode<T, V>> childs = temp.getChilds();
			if (childs == null) {
				return null;
			} else {
				HashNode<T, V> hashNode2 = childs.get(h);
				if (hashNode2 == null) {
					return null;
				} else {
					if (key.equals(hashNode2.getT()))
						return hashNode2.getV();
					temp = hashNode2;
				}
			}

			i++;
		}

		return null;
	}

	private int hash(T key) {
		String s = key.toString();
		char[] charArray = s.toCharArray();
		int hash = 0;
		for (int i = 0; i < charArray.length; i++) {
			hash = (hash << 4) ^ (hash >> 28) ^ charArray[i];
		}
		return hash;
	}

	public void printAll() {
		print(root);
	}

	private void print(HashNode<T, V> node) {
		System.out.println("当前节点:" + node.t + "\t");
		// 打印子节点
		HashMap<Integer, HashNode<T, V>> childs = node.getChilds();
		if (childs != null) {

			for (Map.Entry<Integer, HashNode<T, V>> map : childs.entrySet()) {
				System.out.println("节点:" + node.t + "的子节点:" + map.getValue().t + "\t位置:" + map.getKey());
				print(map.getValue());
			}
		}
	}

	static class HashNode<T, V> implements Comparable<HashNode<T, V>> {

		private T t; // 节点的值

		private V v;

		private HashMap<Integer, HashNode<T, V>> childs;;

		private int depth = 0;

		private int hash;

		public HashNode(T t, V v, int hash) {
			this.t = t;
			this.v = v;
			this.hash = hash;
		}

		public HashNode() {
		}

		public HashNode(T t, V v) {
			this.t = t;
			this.v = v;
		}

		public T getT() {
			return t;
		}

		public void setT(T t) {
			this.t = t;
		}

		public HashMap<Integer, HashNode<T, V>> getChilds() {
			return childs;
		}

		public void setChilds(HashMap<Integer, HashNode<T, V>> childs) {
			this.childs = childs;
		}

		public int getHash() {
			return hash;
		}

		public void setHash(int hash) {
			this.hash = hash;
		}

		public int getDepth() {
			return depth;
		}

		public void setDepth(int depth) {
			this.depth = depth;
		}

		public V getV() {
			return v;
		}

		public void setV(V v) {
			this.v = v;
		}

		@Override
		public int compareTo(HashNode<T, V> o) {
			return this.hash - o.hash;
		}

	}

	public static void main(String[] args) {
		HashTree<Integer, Integer> hashTree = new HashTree<Integer, Integer>();
		hashTree.insert(7807, 7807);
		hashTree.insert(249, 249);
		hashTree.insert(1073, 1073);
		hashTree.insert(658, 658);
		hashTree.insert(930, 930);

		hashTree.insert(2272, 2272);
		hashTree.insert(8544, 8544);

		hashTree.insert(1878, 1878);
		hashTree.insert(8923, 8923);

		hashTree.insert(8709, 8709);

		Integer find = hashTree.find(1878);

		System.out.println(find);

		hashTree.printAll();
	}

}
