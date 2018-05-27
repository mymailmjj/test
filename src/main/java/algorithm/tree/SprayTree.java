/**
 * 
 */
package algorithm.tree;



/**
 * 
 * 伸展树的实现
 * 有四种旋转的情况
 * 
 *      3           5         4                7
 *    2         2                5                 10
 *  1              3               6           9
 *  
 *  一字型的旋转情况同AVL树有区别
 *  旋转规则，先绕爷爷旋转，再让父亲旋转
 *  
 *  删除同二叉查找树
 *  
 * @author mymai
 *
 */
public class SprayTree {

	private SprayTreeNode root = null;

	private <U extends Comparable<U>> void insertNode(SprayTreeNode<U> node) {

		if (root == null) {
			this.root = node;
		} else {
			root.insert(node);
		}
	}

	private class SprayTreeNode<U extends Comparable<U>> implements Comparable<SprayTreeNode<U>> {

		private U t; // data

		private SprayTreeNode<U> leftChild;

		private SprayTreeNode<U> rightChild;

		private SprayTreeNode<U> parent;

		public SprayTreeNode<U> getParent() {
			return parent;
		}

		public void setParent(SprayTreeNode<U> parent) {
			this.parent = parent;
		}

		public SprayTreeNode(U t, SprayTreeNode<U> left, SprayTreeNode<U> right) {
			this.t = t;
			this.leftChild = left;
			this.rightChild = right;
		}

		public SprayTreeNode(U t) {
			this(t, null, null);
		}

		public SprayTreeNode() {
			super();
		}

		/**
		 * @param insertNode
		 */
		public void insert(SprayTreeNode<U> insertNode) {
			if (insertNode == null)
				return;

			int c = this.t.compareTo(insertNode.t);

			if (c > 0)
				insertLeft(insertNode);
			else
				insertRight(insertNode);
		}

		/**
		 * 左边插入节点
		 * 
		 * @param insertNode
		 */
		public void insertLeft(SprayTreeNode<U> insertNode) {
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
		public void insertRight(SprayTreeNode<U> insertNode) {

			if (this.rightChild == null) {
				this.rightChild = insertNode;
				insertNode.setParent(this);
			} else {
				this.rightChild.insert(insertNode);
			}

		}

		@Override
		public int compareTo(SprayTreeNode<U> o) {
			return this.t.compareTo(o.t);
		}

		@Override
		public String toString() {
			return "SprayTreeNode [" + (t != null ? "t=" + t + ", " : "")
					+ (leftChild != null ? "leftChild=" + leftChild.t + ", " : "")
					+ (rightChild != null ? "rightChild=" + rightChild.t + ", " : "")
					+ (parent != null ? "parent=" + parent.t : "") + "]";
		}
		
		

	}
	
	
	public <U extends Comparable<U>> SprayTreeNode<U> queryNode(SprayTreeNode<U> queryNode) {
		
		SprayTreeNode queryNode2 = queryNode(root,queryNode);
		
		sprayNode(queryNode2);
		
		return queryNode2;
	}

	private <U extends Comparable<U>> SprayTreeNode<U> queryNode(SprayTreeNode<U> thisNode, SprayTreeNode<U> queryNode) {

		int compareTo = thisNode.t.compareTo(queryNode.t);

		if (compareTo == 0) {
			return thisNode;
		}

		if (compareTo > 0) {
			return queryNode(thisNode.leftChild, queryNode);
		}

		if (compareTo < 0) {
			return queryNode(thisNode.rightChild, queryNode);
		}

		return null;

	}

	public <U extends Comparable<U>> SprayTreeNode<U> parentOf(SprayTreeNode<U> thisNode) {
		return thisNode != null ? thisNode.parent : null;

	}

	private <U extends Comparable<U>> void rotateRight(SprayTreeNode<U> thisNode) {

		if (thisNode == null)
			return;

		SprayTreeNode<U> parent = parentOf(thisNode);

		parent.leftChild = thisNode.rightChild;

		if (parent.leftChild != null)
			parent.leftChild.parent = parent;
		
		thisNode.rightChild = parent;

		if (parent.parent != null&&parent.parent.leftChild==parent) {
			thisNode.parent = parent.parent;
			parent.parent.leftChild = thisNode;
			parent.parent = thisNode;
		} else if(parent.parent != null&&parent.parent.rightChild==parent) {
			thisNode.parent = parent.parent;
			parent.parent.rightChild = thisNode;
			parent.parent = thisNode;
		} else {
			root.setParent(thisNode);
			root = thisNode;
			root.setParent(null);
		}

		parent = thisNode;

	}

	private <U extends Comparable<U>> void rotateLeft(SprayTreeNode<U> thisNode) {

		if (thisNode == null)
			return;

		SprayTreeNode<U> parent = parentOf(thisNode);

		parent.rightChild = thisNode.leftChild;

		if (parent.rightChild != null)
			parent.rightChild.parent = parent;
		
		thisNode.leftChild = parent;

		if (parent.parent != null&&parent.parent.leftChild==parent) {
			thisNode.parent = parent.parent;
			parent.parent.leftChild = thisNode;
			parent.parent = thisNode;
		} else if(parent.parent != null&&parent.parent.rightChild==parent) {
			thisNode.parent = parent.parent;
			parent.parent.rightChild = thisNode;
			parent.parent = thisNode;
		}else {
			root.setParent(thisNode);
			root = thisNode;
			root.setParent(null);
		}

		parent = thisNode;

	}

	public <U extends Comparable<U>> void sprayNode(SprayTreeNode<U> thisNode) {

		SprayTreeNode<U> parent = parentOf(thisNode);

		if (parent == root)
			return; // 已经是根节点了

		while ((parent=parentOf(thisNode)) != null) {

			SprayTreeNode<U> grandPa = parentOf(parentOf(thisNode)); // 爷爷节点

			if (grandPa == null) {

				if (parent.leftChild != null && parent.leftChild == thisNode) { // 左边节点
					rotateRight(thisNode);
				}

				if (parent.rightChild != null && parent.rightChild == thisNode) { // 左边节点
					rotateLeft(thisNode);
				}

			} else {

				if (grandPa.leftChild != null && grandPa.leftChild == parent) { // 左边节点

					if (parent.leftChild != null && parent.leftChild == thisNode) { // 左边节点
						rotateRight(thisNode.parent);
						rotateRight(thisNode);
					} else {// 之字形
						rotateLeft(thisNode);
						rotateRight(thisNode.parent);
					}

				}

				if (grandPa.rightChild != null && grandPa.rightChild == parent) { // 右边节点

					if (parent.rightChild != null && parent.rightChild == thisNode) { // 右边节点
						rotateLeft(thisNode.parent);
						rotateLeft(thisNode);
					} else {
						rotateRight(thisNode);
						rotateLeft(thisNode.parent);
					}
				}
			}

		}

	}
	
	
	private void printAllTree(){
		printTree(root);
	}
	
	
	private <U extends Comparable<U>> void printTree(SprayTreeNode<U> node){
		
		if(node.t!=null) {
			System.out.println("this node data is "+node);
		}
		
		if(node.leftChild!=null){
			printTree(node.leftChild);
		}
		
		if(node.rightChild!=null){
			printTree(node.rightChild);
		}
		
	}
	
	
	/*public static void main(String[] args) {
		
		SprayTree bt = new SprayTree();
		
		Random random = new Random();
		
		int num = 5;
		
		int[] ran = new int[num];
		
		long insertStart = System.currentTimeMillis();
		
		SprayTreeNode<Integer> queryNode = null;
		
		int i = 0;
		while(i < num){
			int a = random.nextInt(20);
			int[] ran1 = Arrays.copyOf(ran, num);
			Arrays.sort(ran1);
			int binarySearch = Arrays.binarySearch(ran1, a);
			
			if(binarySearch<0) {
				ran[i++] = a;
				SprayTreeNode<Integer> rannode = bt.new SprayTreeNode<Integer>(new Integer(a));
				bt.insertNode(rannode);
				if(i==4){
					queryNode = rannode;
				}
			}
		}
		
		long insertEnd = System.currentTimeMillis();
		
		System.out.println("插入耗时:"+(insertEnd-insertStart)+"毫秒");
			
		System.out.println("插入的数字是:"+Arrays.toString(ran));
		
		bt.printAllTree();
		
		if(queryNode!=null){
			long now = System.currentTimeMillis();
			
			System.out.println("queryNode:"+queryNode);
			
			SprayTreeNode<Integer> queryNode2 = bt.queryNode(queryNode);
			
			System.out.println("结果："+queryNode2);
			
			long end = System.currentTimeMillis();
			
			System.out.println("query耗时:"+(end-now));
		}
		
		System.out.println("-----------------------");
		bt.printAllTree();
		
	}*/
	
	
	public static void main(String[] args) {
		
		//14, 1, 3, 16, 10
		SprayTree bt = new SprayTree();
		
		SprayTreeNode<Integer> root = bt.new SprayTreeNode<Integer>(14);
		bt.insertNode(root);
		
		SprayTreeNode<Integer> node1 = bt.new SprayTreeNode<Integer>(1);
		bt.insertNode(node1);
		
		SprayTreeNode<Integer> node2 = bt.new SprayTreeNode<Integer>(3);
		bt.insertNode(node2);
		
		SprayTreeNode<Integer> node3 = bt.new SprayTreeNode<Integer>(16);
		bt.insertNode(node3);
		
		SprayTreeNode<Integer> node4 = bt.new SprayTreeNode<Integer>(10);
		bt.insertNode(node4);
		
		bt.printAllTree();
		
		SprayTreeNode<Integer> queryNode2 = bt.queryNode(node4);
		
		bt.queryNode(node3);
		
		bt.queryNode(node2);
		
		System.out.println("--------------------------------");
		
		bt.printAllTree();
		
	}

}
