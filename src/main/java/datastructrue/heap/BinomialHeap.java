/**
 * 
 */
package datastructrue.heap;

/**
 * 
 * 二项堆
 * @author az6367
 *
 */
public class BinomialHeap<T extends Comparable<T>> {

	private Node<T> root; // ���ڵ��׽ڵ�

	public void insert(T t) {
		Node<T> insertNode = new Node<T>(t);
		insert(insertNode);
	}

	public Node<T> findMin() {
		Node<T> minNode = root;
		Node<T> temp = root;
		while (temp != null) {
			temp = temp.silbings;

			if (temp != null) {
				int c = temp.u.compareTo(minNode.u);
				if (c < 0) {
					minNode = temp;
				}
			}
		}

		return minNode;
	}

	public void insert(Node<T> insertNode) {
		if (root == null) {
			root = insertNode;
		} else {
			insertNode.silbings = root;
			root = insertNode;
		}

		checkMerge();
	}

	private void checkMerge() {

		Node<T> temp = root;
		while (temp.silbings != null) {
			Node<T> o = temp;
			temp = temp.silbings;
			if (o.degree == temp.degree) {
				merge(o, temp);
				temp = root;
			}
		}
	}

	private void merge(Node<T> h1, Node<T> h2) {

		int c = h1.u.compareTo(h2.u);

		if (c > 0) {
			merge(h2, h1);
		} else {

			BinomialHeap<T>.Node<T> leftChild = h1.leftChild;

			if (leftChild == null) {
				h1.leftChild = h2;
			} else {
				leftChild.silbings = h2;
			}

			h2.parentNode = h1;

			h1.degree++;

			if (h2 == root) {
				root = h1;
				h2.silbings = null;
			} else {
				h1.silbings = h2.silbings;
			}

		}

	}

	private class Node<U extends T> {

		private int degree = 0; // ����

		private U u;

		private Node<U> parentNode;

		private Node<U> leftChild;

		private Node<U> silbings;

		public Node(U u, Node<U> parentNode, Node<U> leftChild, BinomialHeap<T>.Node<U> silbings) {
			this.u = u;
			this.parentNode = parentNode;
			this.leftChild = leftChild;
			this.silbings = silbings;
		}

		public Node(U u) {
			this(u, null, null, null);
		}

		public U getU() {
			return u;
		}

		public void setU(U u) {
			this.u = u;
		}

		public Node<U> getParentNode() {
			return parentNode;
		}

		public void setParentNode(Node<U> parentNode) {
			this.parentNode = parentNode;
		}

		public Node<U> getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(Node<U> leftChild) {
			this.leftChild = leftChild;
		}

		public Node<U> getSilbings() {
			return silbings;
		}

		public void setSilbings(Node<U> silbings) {
			this.silbings = silbings;
		}

		public int getDegree() {
			return degree;
		}

		public void setDegree(int degree) {
			this.degree = degree;
		}

		@Override
		public String toString() {
			return "Node [degree=" + degree + ", " + (u != null ? "u=" + u + ", " : "")
					+ (parentNode != null ? "parentNode=" + parentNode.u + ", " : "")
					+ (leftChild != null ? "leftChild=" + leftChild.u + ", " : "")
					+ (silbings != null ? "silbings=" + silbings.u : "") + "]";
		}

	}

	public void printAll() {

		Node<T> temp = root;
		while (temp != null) {
			printNode("���ڵ�", temp);
			temp = temp.silbings;
		}

	}

	private void printNode(String str, Node<T> node) {
		System.out.println("�ڵ㱾��" + str + "\tnode" + node);
		if (node.leftChild != null) {
			printNode(node.u + "��left�ڵ�", node.leftChild);
		}

		if (node.silbings != null) {
			printNode(node.u + "�����ڽڵ�", node.silbings);
		}
	}

	public static void main(String[] args) {

		BinomialHeap<Integer> heap = new BinomialHeap<Integer>();

		heap.insert(40);

		heap.insert(20);

		heap.insert(30);

		heap.insert(60);

		BinomialHeap<Integer>.Node<Integer> findMin = heap.findMin();

		System.out.println("��С�ڵ㣺" + findMin);

		heap.insert(50);
		heap.insert(70);
		
		heap.insert(100);
		
//		heap.insert(200);

		heap.printAll();

	}

}
