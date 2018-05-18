/**
 * 
 */
package algorithm.tree;

/**
 * 二叉树 平衡
 * 
 * @author mymai
 *
 */
public class AvlTree {

	private BinaryTreeNode root = null;

	private <U extends Comparable<U>> void insertNode(BinaryTreeNode<U> node) {

		if (root == null) {
			this.root = node;
		} else {
			root.insert(node);
		}
	}

	private <U extends Comparable<U>> int heightOf(BinaryTreeNode<U> node) {
		if(node==null) return 0;
		
		return node.height;
	}

	private class BinaryTreeNode<U extends Comparable<U>> {

		private U t; // data

		private BinaryTreeNode<U> leftChild;

		private BinaryTreeNode<U> rightChild;
		
		private BinaryTreeNode<U> parent;

		private int height = 1;

		public BinaryTreeNode(U t, BinaryTreeNode<U> left, BinaryTreeNode<U> right) {
			this.t = t;
			this.leftChild = left;
			this.rightChild = right;
		}

		public BinaryTreeNode(U t) {
			this(t, null, null);
		}

		public BinaryTreeNode() {
			super();
		}
		
		public BinaryTreeNode<U> getParent() {
			return parent;
		}

		public void setParent(BinaryTreeNode<U> parent) {
			this.parent = parent;
		}

		/**
		 * @param insertNode
		 */
		public void insert(BinaryTreeNode<U> insertNode) {
			if (insertNode == null)
				return;

			int c = this.t.compareTo(insertNode.t);
			System.out.println("当前节点："+this.t+"插入节点："+insertNode.t);
			if (c > 0) {
				insertLeft(insertNode);
				// 需要旋转的情况
				if (Math.abs(heightOf(leftChild) - heightOf(rightChild)) > 1) {
					// 在左边插入和左边的节点比较
					int d = this.leftChild.t.compareTo(insertNode.t);
					if (d > 0) {
						// 右旋
						System.out.println("进行右旋");
						rotateRight(this);
					} else {
						System.out.println("先左旋，后右旋");
						// 先左旋，后右旋
						rotateLeftRight(this);
					}
				}
			} else {
				insertRight(insertNode);
				if (Math.abs(heightOf(leftChild) - heightOf(rightChild)) > 1) {
					int d = this.rightChild.t.compareTo(insertNode.t);
					if (d < 0) {
						// 左旋
						System.out.println("进行左旋");
						rotateLeft(this);
					} else {
						System.out.println("先右旋，后左旋");
						// 先右旋，后左旋
						rotateRightLeft(this);
					}
				}
			}
			
			this.height = Math.max(this.leftChild == null ? 0 : this.leftChild.height,
					this.rightChild == null ? 0 : this.rightChild.height) + 1;

		}

		/**
		 * 左边插入节点
		 * 
		 * @param insertNode
		 */
		public void insertLeft(BinaryTreeNode<U> insertNode) {
			if (this.leftChild == null) {
				this.leftChild = insertNode;
				insertNode.setParent(this);
			} else {
				this.leftChild.insert(insertNode);
			}
			
			
		}

		/**
		 * 右边插入节点
		 * 
		 * @param insertNode
		 */
		public void insertRight(BinaryTreeNode<U> insertNode) {

			if (this.rightChild == null) {
				this.rightChild = insertNode;
				insertNode.setParent(this);
			} else {
				this.rightChild.insert(insertNode);
			}
			
		}

		/**
		 * 左旋
		 * 
		 * @param insertNode
		 */
		public void rotateLeft(BinaryTreeNode<U> p) {
			
			if (p == null)
				return;
			BinaryTreeNode<U> r = p.rightChild;
			p.rightChild = r.leftChild;
			r.leftChild = p;
			
			if(p.getParent()==null){
				root = r;
			}else if(p.getParent().rightChild==p){
				p.getParent().rightChild = r;
				r.parent = p.parent;
			}else if(p.getParent().leftChild==p){
				p.getParent().leftChild = r;
				r.parent = p.parent;
			}
			
			p.parent = r;
			
			p.height = Math.max(heightOf(p.leftChild), heightOf(p.rightChild))+1;
			
			r.height = Math.max(heightOf(r.leftChild), p.height)+1;

		}

		/**
		 * 先左旋，后右旋
		 * 
		 * @param insertNode
		 */
		public void rotateLeftRight(BinaryTreeNode<U> p) {
			rotateLeft(p.leftChild);
			rotateRight(p);

		}

		/**
		 * 右旋
		 * 旋转完毕记得重置长度
		 * @param insertNode
		 */
		public void rotateRight(BinaryTreeNode<U> p) {
			if (p == null)
				return;
			BinaryTreeNode<U> r = p.leftChild;
			p.leftChild = r.rightChild;
			r.rightChild = p;
			
			if(p.getParent()==null){
				root = r;
				root.setParent(null);
			}else if(p.getParent().rightChild==p){
				p.getParent().rightChild = r;
				r.parent = p.parent;
			}else if(p.getParent().leftChild==p){
				p.getParent().leftChild = r;
				r.parent = p.parent;
			}
			
			p.parent =r;
			
			p.height = Math.max(heightOf(p.leftChild), heightOf(p.rightChild))+1;
			
			r.height = Math.max(heightOf(r.rightChild), p.height)+1;
			
		}

		/**
		 * 先右旋 后左旋
		 * 
		 * @param insertNode
		 */
		public void rotateRightLeft(BinaryTreeNode<U> p) {
			rotateRight(p.rightChild);
			System.out.println("右旋文成"+p);
			rotateLeft(p);
		}

		@Override
		public String toString() {
			return "BinaryTreeNode [" + (t != null ? "t=" + t + ", " : "")
					+ (leftChild != null ? "leftChild=" + leftChild + ", " : "")
					+ (rightChild != null ? "rightChild=" + rightChild + ", " : "")
					+ (parent != null ? "parent=" + parent.t + ", " : "") + "height=" + height + "]";
		}

		

	}

	private void printAllTree() {
		printTree(root);
	}

	private <U extends Comparable<U>> void printTree(BinaryTreeNode<U> node) {

		if (node.t != null) {
			System.out.println("this node data is " + node);
		}

		if (node.leftChild != null) {
			printTree(node.leftChild);
		}

		if (node.rightChild != null) {
			printTree(node.rightChild);
		}

	}

	public static void main(String[] args) {

		AvlTree bt = new AvlTree();
		BinaryTreeNode<Integer> root = bt.new BinaryTreeNode<Integer>(20);

		BinaryTreeNode<Integer> node1 = bt.new BinaryTreeNode<Integer>(10);

		BinaryTreeNode<Integer> node2 = bt.new BinaryTreeNode<Integer>(5);

		BinaryTreeNode<Integer> node3 = bt.new BinaryTreeNode<Integer>(18);
		
		BinaryTreeNode<Integer> node4 = bt.new BinaryTreeNode<Integer>(13);
		
		BinaryTreeNode<Integer> node5 = bt.new BinaryTreeNode<Integer>(11);

		bt.insertNode(root);

		bt.insertNode(node1);

		bt.insertNode(node2);

		bt.insertNode(node3);
		
		System.out.println("---------------------------------");
		
		bt.insertNode(node4);
		
		bt.insertNode(node5);

		bt.printAllTree();

	}

}
