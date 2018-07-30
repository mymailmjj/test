/**
 * 
 */
package datastructrue.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * 没有完整测试
 * 测试了insert extractMin
 * @author mymai 支持的操作 insert O(1) merge O(1) extractMin
 * 
 * 
 *  decreaseKey
 *
 */
public class FibonacciHeap<T extends Comparable<T>> {

	private List<Integer> lists = new ArrayList<>();

	private boolean greatThan(Node<T> node1, Node<T> node2) {
		return node1.t.compareTo(node2.t) > 0;
	}

	public void printAll() {
		printNode(minNode);
	}

	public void printNode(Node<T> node) {
		Node<T> temp = node;

		while (temp != null) {
			printNode("当前节点", temp);
			temp = temp.getNext();
			if (temp == node)
				break;
		}
	}

	private void printNode(String str, Node<T> node) {
		System.out.println(str + node);

		// 儿子节点
		Node<T> son = node.son;
		if (son != null)
			printNode(son);

	}

	/**
	 * 插入方法
	 * 
	 * @param t
	 */
	public void insert(T t) {
		if (t == null)
			throw new IllegalArgumentException();
		Node<T> node = new Node<T>(t);
		if (minNode == null) {
			minNode = node;
		} else {
			minNode.insertLeftNode(node);

			// 比较大小，交换位置
			if (greatThan(minNode, node)) {
				minNode = node;
			}

		}

	}

	public Node<T> findMin() {
		return minNode;
	}

	private Node<T> findTailNode(Node<T> node) {
		Node<T> temp = node;

		while (temp != null) {
			temp = temp.getNext();
			if (temp == node)
				break;
		}

		return temp;
	}

	public Node<T> extractMin() {

		// 先找到最小节点的孩儿节点
		Node<T> son = minNode.getSon();

		if (son != null) {
			// 找到孩儿节点的末尾节点
			Node<T> sonTailNode = findTailNode(son);

			// 拼接到根节点
			son.prev = minNode.prev;
			minNode.prev.next = son;

			sonTailNode.next = minNode.next;
			minNode.next.prev = sonTailNode;
		}

		// 开始过滤整合
		union();

		return minNode;
	}

	private Node<T> findNodeWithFirstDegree(int degree) {

		int i = 0;

		Node<T> temp = minNode;

		while (temp.next != minNode) {
			if (temp.degree == degree)
				return temp;
			temp = temp.next;
		}

		return temp;
	}

	private void mergeNode(Node<T> node1, Node<T> node2) {
		if (node1 == null || node2 == null) {
			throw new IllegalArgumentException();
		}

		if (greatThan(node1, node2)) {
			mergeNode(node2, node1);
		} else {

			// 先把node2的前后链接起来
			node2.prev.next = node2.next;
			node2.next.prev = node2.prev;

			// 自我链接
			node2.prev = node2;
			node2.next = node2;

			// 和node1进行结合
			node2.parent = node1;
			Node<T> son = node1.son;
			if(son!=null){
				son.next.prev = node2;
				node2.next = son.next;
				son.next = node2;
				node2.prev = son;
			}else{
				node1.son = node2;
			}
			
			node1.degree++;

		}

	}

	private void union() {
		Node<T> temp = minNode;
		while (temp.next != minNode) {
			Integer degree = temp.degree;
			int indexOf = lists.indexOf(degree);
			if (indexOf != -1) {
				Node<T> indexNode = findNodeWithFirstDegree(degree);
				mergeNode(indexNode, temp);
				temp = minNode;
				lists.clear();
				continue;
			} else {
				lists.add(degree);
			}
			temp = temp.next;
		}
	}

	private Node<T> minNode;

	private int size = 0;

	static class Node<T extends Comparable<T>> {

		private int degree = 0; // 度数

		private T t;
		private Node<T> prev = this; // 前兄弟节点
		private Node<T> next = this; // 后兄弟节点

		private List<Node<T>> listNodes; // 儿子节点
		private Node<T> parent; // 父亲节点
		private Node<T> son; // 其中一个儿子节点

		private boolean mark = false;

		public boolean isMark() {
			return mark;
		}

		public void setMark(boolean mark) {
			this.mark = mark;
		}

		public Node(T t) {
			this.t = t;
		}

		public void insertLeftNode(Node<T> node) {
			if (prev != null) {
				prev.next = node;
			}

			node.prev = prev;
			if (prev != null)
				prev = node;
			node.next = this;
		}

		public int getDegree() {
			return degree;
		}

		public void setDegree(int degree) {
			this.degree = degree;
		}

		public T getT() {
			return t;
		}

		public void setT(T t) {
			this.t = t;
		}

		public Node<T> getPrev() {
			return prev;
		}

		public void setPrev(Node<T> prev) {
			this.prev = prev;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		public List<Node<T>> getListNodes() {
			return listNodes;
		}

		public void setListNodes(List<Node<T>> listNodes) {
			this.listNodes = listNodes;
		}

		public Node<T> getParent() {
			return parent;
		}

		public void setParent(Node<T> parent) {
			this.parent = parent;
		}

		public Node<T> getSon() {
			return son;
		}

		public void setSon(Node<T> son) {
			this.son = son;
		}

		@Override
		public String toString() {
			return "Node [degree=" + degree + ", " + (t != null ? "t=" + t + ", " : "")
					+ (prev != null ? "prev=" + prev.t + ", " : "") + (next != null ? "next=" + next.t + ", " : "")
					+ (listNodes != null ? "listNodes=" + listNodes + ", " : "")
					+ (parent != null ? "parent=" + parent.t + ", " : "") + (son != null ? "son=" + son.t + ", " : "")
					+ "mark=" + mark + "]";
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FibonacciHeap<Integer> roots = new FibonacciHeap();
		roots.insert(5);
		roots.insert(30);
		roots.insert(10);
		roots.insert(20);
		roots.insert(40);

		roots.printAll();

		Node<Integer> findMin = roots.findMin();

		System.out.println("findMin:" + findMin);

		Node<Integer> extractMin = roots.extractMin();

		System.out.println("extractMin:" + extractMin);

		System.out.println("----------------------------------------");
		roots.printAll();

	}

}
