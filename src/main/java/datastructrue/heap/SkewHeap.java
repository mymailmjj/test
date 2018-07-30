package datastructrue.heap;


/**
 * 斜堆 同左式堆一样，唯一的区别是合并后每次都要调换左右节点（右子式最大的节点除外）
 * 
 * @author mymai
 *
 */
public class SkewHeap<T extends Comparable<T>> {

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
	

	public void insert(T t) {
		Node<T> node = new Node<T>(t);
		insert(node);
	}

	public void insert(Node<T> insertNode) {
		if (root == null) {
			root = insertNode;
		} else {
			merge(root, insertNode);
		}

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
		System.out.println(str + " node value:" + node.u);
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
			Node<T> r = findRightNode(h1);
			
			Node<T> rParent = findRightNodeParent(h1, r);

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

			}
			
			if(h1.leftNode!=null&&h1.rightNode!=null){
				// 斜堆每次插入之后都要交换节点
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
	 * 
	 * @author mymai
	 *
	 */
	private class Node<U extends T> {

		private U u;

		private Node<U> leftNode;

		private Node<U> rightNode;

		public Node(U u, Node<U> leftNode, Node<U> rightNode) {
			this.u = u;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
		}

		public Node(U u) {
			this(u, null, null);
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

	}

	public static void main(String[] args) {
		SkewHeap<Integer> skewHeap = new SkewHeap<>();
		skewHeap.insert(20);
		skewHeap.insert(30);
		skewHeap.insert(40);
		skewHeap.insert(50);
		skewHeap.insert(60);
		skewHeap.insert(70);
		skewHeap.insert(80);
		
		skewHeap.insert(90);
		skewHeap.printAllNode();
		
		System.out.println("---------------------删除最小节点");

		System.out.println(skewHeap.findMin());

		skewHeap.deleteMin();

		skewHeap.printAllNode();

	}

}
