/**
 * 
 */
package algorithm.heap;

/**
 * 左式堆 左式堆存在一个最短路径长 左式堆具备堆序性质 左子式的最短路径长不小于右边的，如果违反，则交换子式
 * 
 * @author mymai
 *
 */
public class LeftHeap<T extends Comparable<T>> {

	private Node<T> root = null;

	public Node<T> findMin() {
		return root;
	}

	public void deleteMin() {
		if (root.leftNode == null && root.rightNode == null)
			root = null;
		if (root.leftNode != null && root.rightNode != null) {
			int compare = compare(root.leftNode.u, root.rightNode.u);
			if (compare > 0) {
				Node<T> l = root.leftNode;
				root = root.rightNode;
				merge(root, l);
			} else {
				Node<T> r = root.rightNode;
				root = root.leftNode;
				merge(root, r);
			}
		} else if (root.leftNode != null) {
			root = root.leftNode;
		} else if (root.rightNode != null) {
			root = root.rightNode;
		}

	}

	public void insert(T t) {
		Node<T> node = new Node<T>(t);
		insert(node);
	}

	public void insert(Node<T> insertNode) {
		if (root == null) {
			root = insertNode;
			root.setMinHeight();
		} else {
			merge(root, insertNode);
		}

	}
	
	
	/**
	 * 在rootNode的树上找到rightNode的父亲
	 * @param h1
	 * @param rightNode
	 * @return
	 */
	private Node<T> findRightNodeParent(Node<T> h1,Node<T> rightNode) {
		if (h1 == null)
			return null;
		Node<T> temp = h1;
		while (temp.rightNode != null&&temp.rightNode != rightNode) {
			temp = temp.rightNode;
		}

		return temp;

	}
	

	private Node<T> findRightNode(Node<T> h1) {
		if (h1 == null)
			return null;
		Node<T> temp = h1;
		while (temp.rightNode != null) {
			temp = temp.rightNode;
		}

		return temp;

	}

	private int compare(T t1, T t2) {
		return t1.compareTo(t2);
	}

	public void printAllNode() {
		printNode("根节点：", root);
	}

	private void printNode(String str, Node<T> node) {
		System.out.println(str + " node value:" + node.u + "\t高度:" + node.npl);
		if (node.leftNode != null) {
			printNode("节点 " + node.u + "的左子节点:", node.leftNode);
		}

		if (node.rightNode != null) {
			printNode("节点 " + node.u + "的右子节点:", node.rightNode);
		}
	}

	private void merge(Node<T> h1, Node<T> h2) {

		int c = compare(h1.u, h2.u);

		if (c > 0) {
			merge(h2, h1);
		} else {
			// 先找出最右边的节点
			LeftHeap<T>.Node<T> r = findRightNode(h1);
			
			LeftHeap<T>.Node<T> rParent = findRightNodeParent(h1, r);

			int c1 = compare(r.u, h2.u);

			if (c1 > 0) {
				merge(h2, r);
				rParent.rightNode = h2;
			} else {
				if (r.leftNode == null) {
					r.leftNode = h2;
				} else {
					r.rightNode = h2;
				}

				r.setMinHeight();
			}

			h1.setMinHeight();

			// 这里判断交换子节点
			if (h1.leftNode != null && h1.rightNode != null && h1.leftNode.npl < h1.rightNode.npl) {
				System.out.println(h1 + "交换子节点");
				swapChildNode(h1);
			}

		}

	}

	private void swapChildNode(Node<T> node) {
		Node<T> temp = node.leftNode;
		node.leftNode = node.rightNode;
		node.rightNode = temp;

	}

	/**
	 * 左式堆的节点有一个零路径长度 每个节点到没有两个子节点的最短路径
	 * 
	 * @author mymai
	 *
	 */
	private class Node<U extends T> {

		private U u;

		private Node<U> leftNode;

		private Node<U> rightNode;

		private int npl = 0;

		public Node(U u, LeftHeap<T>.Node<U> leftNode, LeftHeap<T>.Node<U> rightNode) {
			this.u = u;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
		}

		public Node(U u) {
			this(u, null, null);
		}

		public void setMinHeight() {
			this.npl = minShortPathHeight();
		}

		/*
		 * 求当前节点的的最短路径长度 具有0个儿子或者1个儿子npl为0
		 */
		public int minShortPathHeight() {
			if (leftNode == null && rightNode == null)
				return 0;

			if (leftNode != null && rightNode != null)
				return Math.min(leftNode.npl, rightNode.npl) + 1;

			return 0;

		}

		public U getU() {
			return u;
		}

		public void setU(U u) {
			this.u = u;
		}

		public Node<U> getLeftNode() {
			return leftNode;
		}

		public void setLeftNode(Node<U> leftNode) {
			this.leftNode = leftNode;
		}

		public Node<U> getRightNode() {
			return rightNode;
		}

		public void setRightNode(Node<U> rightNode) {
			this.rightNode = rightNode;
		}

		public int getHeight() {
			return npl;
		}

		public void setHeight(int height) {
			this.npl = height;
		}

		@Override
		public String toString() {
			return "Node [" + (u != null ? "u=" + u + ", " : "")
					+ (leftNode != null ? "leftNode=" + leftNode.u + ", " : "")
					+ (rightNode != null ? "rightNode=" + rightNode.u + ", " : "") + "height=" + npl + "]";
		}

	}

	public static void main(String[] args) {
		LeftHeap<Integer> leftHeap = new LeftHeap<>();
		leftHeap.insert(20);
		leftHeap.insert(30);
		leftHeap.insert(40);
		leftHeap.insert(50);
		leftHeap.insert(60);
		leftHeap.insert(70);
		leftHeap.insert(80);

		leftHeap.insert(90);
		leftHeap.printAllNode();

		System.out.println("---------------------删除最小节点");

		System.out.println(leftHeap.findMin());

		leftHeap.deleteMin();

		leftHeap.printAllNode();

	}

}
