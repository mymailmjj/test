/**
 * 
 */
package algorithm.tree;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 二叉树 平衡(AVL树)
 * 性能测试结果：
 * 插入节点数           耗时                                            旋转次数       查询时间
 * 1000        50ms - 120ms		500      0ms
 * 10000       3000ms           5000     0ms
 * 100000      6min             60000    0ms
 * 
 * 结论    查询时速度很快，缺点插入旋转很慢
 * @author mymai
 *
 */
public class AvlTree {

	private BinaryTreeNode root = null;
	
	private static AtomicInteger ll = new AtomicInteger(0);  //左旋
	
	private static AtomicInteger rr = new AtomicInteger(0);  //右旋
	
	private static AtomicInteger lr = new AtomicInteger(0);  //先左后右
	
	private static AtomicInteger rl = new AtomicInteger(0);  //先右后左
	
	private static AtomicInteger nodeNum = new AtomicInteger(0);  //先右后左
	
	private <U extends Comparable<U>> void insertNode(BinaryTreeNode<U> node) {
		nodeNum.incrementAndGet();
		if (root == null) {
			this.root = node;
		} else {
			root.insert(node);
		}
	}
	
	private <U extends Comparable<U>> BinaryTreeNode<U> queryNode(BinaryTreeNode<U> node) {
			return root.queryNode(node);
	}
	

	private <U extends Comparable<U>> int heightOf(BinaryTreeNode<U> node) {
		if(node==null) return 0;
		
		return node.height;
	}
	
	private class Entry<K extends Comparable<K>,V> implements Comparable<K>{
		
		private K k;
		
		private V v;

		public Entry(K k, V v) {
			this.k = k;
			this.v = v;
		}

		@Override
		public int compareTo(K o) {
			return k.compareTo(o);
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
		
		public BinaryTreeNode<U> queryNode(BinaryTreeNode<U> queryNode){
			
			int compareTo = this.t.compareTo(queryNode.t);
			
			if(compareTo==0){
				return this;
			}
			
			if(compareTo > 0){
				return this.leftChild.queryNode(queryNode);
			}
			
			if(compareTo < 0){
				return this.rightChild.queryNode(queryNode);
			}
			
			return null;
			
		}
		

		/**
		 * @param insertNode
		 */
		public void insert(BinaryTreeNode<U> insertNode) {
			if (insertNode == null)
				return;

			int c = this.t.compareTo(insertNode.t);
			if (c > 0) {
				insertLeft(insertNode);
				// 需要旋转的情况
				if (Math.abs(heightOf(leftChild) - heightOf(rightChild)) > 1) {
					// 在左边插入和左边的节点比较
					int d = this.leftChild.t.compareTo(insertNode.t);
					if (d > 0) {
						// 右旋
						rr.incrementAndGet();  //右旋加1
						rotateRight(this);
					} else {
						// 先左旋，后右旋
						lr.incrementAndGet();  //先左后右加1
						rotateLeftRight(this);
					}
				}
			} else if(c <0){
				insertRight(insertNode);
				if (Math.abs(heightOf(leftChild) - heightOf(rightChild)) > 1) {
					int d = this.rightChild.t.compareTo(insertNode.t);
					if (d < 0) {
						// 左旋
						ll.incrementAndGet();   //左旋加1
						rotateLeft(this);
					} else {
						// 先右旋，后左旋
						rl.incrementAndGet();
						rotateRightLeft(this);
					}
				}
			}else{
				//如果插入的值相同，则什么都不做
				return;
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
			if(p.rightChild!=null){
				p.rightChild.setParent(p);
			}
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
			if(p.leftChild!=null){
				p.leftChild.setParent(p);
			}
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
	
	private void printRotateStatic(){
		System.out.println("插入节点数:"+nodeNum.get());
		System.out.println("ll次数："+ll.get());
		System.out.println("rr次数："+rr.get());
		System.out.println("lr次数："+lr.get());
		System.out.println("rl次数："+rl.get());
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

	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args) {

		AvlTree bt = new AvlTree();
		BinaryTreeNode<Integer> root = bt.new BinaryTreeNode<Integer>(20);
		Random random = new Random();
		
		int num = 100000;
		
		int[] ran = new int[num];
		
		BinaryTreeNode<Integer> queryNode = null;
		
		long insertStart = System.currentTimeMillis();
		
		int i = 0;
		while(i < num){
			int a = random.nextInt(1000000);
			int[] ran1 = Arrays.copyOf(ran, num);
			Arrays.sort(ran1);
			int binarySearch = Arrays.binarySearch(ran1, a);
			
			if(binarySearch<0) {
				ran[i++] = a;
				BinaryTreeNode<Integer> rannode = bt.new BinaryTreeNode<Integer>(a);
				bt.insertNode(rannode);
				if(i==50){
					queryNode = rannode;
				}
			}
		}
		
		long insertEnd = System.currentTimeMillis();
		
		System.out.println("插入耗时:"+(insertEnd-insertStart)+"毫秒");
			
		
//		System.out.println("插入的数字是:"+Arrays.toString(ran));

//		bt.printAllTree();
		
		bt.printRotateStatic();
		
		long now = System.currentTimeMillis();
		
		BinaryTreeNode<Integer> queryNode2 = bt.queryNode(queryNode);
		
//		System.out.println("结果："+queryNode2);
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗时:"+(end-now));

	}

}
