/**
 * 
 */
package datastructrue.tree;

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
	
	/**
	 * 插入方法
	 * @param node
	 */
	private <U extends Comparable<U>> void insertNode(BinaryTreeNode<U> node) {
		nodeNum.incrementAndGet();
		if (root == null) {
			this.root = node;
		} else {
			root.insert(node);
		}
	}
	
	
	/**
	 * 删除方法
	 * @param node
	 */
	private <U extends Comparable<U>> void removeNode(BinaryTreeNode<U> node) {
			root.remove(node);
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
	

	private class BinaryTreeNode<U extends Comparable<U>> implements Cloneable{

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
		 * 找出最小的子节点
		 * @param removeNode
		 * @return
		 */
		private BinaryTreeNode<U> findMin(BinaryTreeNode<U> sonNode){
		
			if(sonNode.leftChild!=null){
				return findMin(sonNode.leftChild);
			}
			
			return sonNode;
		}
		

		/**
		 * 找出最大的子节点
		 * @param removeNode
		 * @return
		 */
		private BinaryTreeNode<U> findMax(BinaryTreeNode<U> sonNode){
			
			if(sonNode.rightChild!=null){
				return findMax(sonNode.rightChild);
			}
		
			return sonNode;
		}
		
		
		/**
		 * 直接删除
		 * @param removeNode
		 * @return
		 */
		@Deprecated
		public boolean removeDirect(BinaryTreeNode<U> sonNode,BinaryTreeNode<U> removeNode) {
			
			//先从子树中找到要删除的节点
			BinaryTreeNode<U> queryNode = sonNode.queryNode(removeNode);
			
			if(queryNode==null) return false;
			
			if(queryNode.leftChild!=null&&queryNode.rightChild!=null){
				if(queryNode.parent!=null){
					queryNode.parent.leftChild = queryNode.leftChild;
					queryNode.parent.rightChild = queryNode.rightChild;
				}
				queryNode.leftChild.parent = queryNode.parent;
				queryNode.rightChild.parent = queryNode.parent;
			}else if(queryNode.rightChild!=null){
				if(queryNode.parent!=null){
					queryNode.parent.rightChild = queryNode.rightChild;
				}
				queryNode.rightChild.parent = queryNode.parent;
			}else if(queryNode.leftChild!=null){
				if(queryNode.parent!=null){
					queryNode.parent.leftChild = queryNode.leftChild;
				}
				queryNode.leftChild.parent = queryNode.parent;
			}else{//左右节点都没有
				if(queryNode.parent!=null&&queryNode.parent.leftChild == queryNode){
					queryNode.parent.leftChild = null;
					
					if(queryNode.parent.rightChild==null){
						queryNode.parent.height=1;
					}
					
				}
				
				if(queryNode.parent!=null&&queryNode.parent.rightChild == queryNode){
					queryNode.parent.rightChild = null;
					
					if(queryNode.parent.leftChild==null){
						queryNode.parent.height=1;
					}
				}
			}
			
			return true;
			
		}
		
		
		private void mergeNode(BinaryTreeNode<U> targetNode){
			
			if(targetNode.parent!=null&&targetNode.parent.rightChild == targetNode){
				
				if(targetNode.leftChild==null&&targetNode.rightChild==null){
					targetNode.parent.rightChild = null;
					targetNode.parent.height = 1;
				}
				
				if(targetNode.leftChild!=null){
					targetNode.parent.rightChild = targetNode.leftChild;
					targetNode.leftChild.parent = targetNode.parent;
					targetNode.parent.height--;
				}
				
				
				if(targetNode.rightChild!=null){
					targetNode.parent.rightChild = targetNode.rightChild;
					targetNode.rightChild.parent = targetNode.parent;
					targetNode.parent.height--;
				}
			
			}
			
			
			if(targetNode.parent!=null&&targetNode.parent.leftChild == targetNode){
				
				if(targetNode.leftChild==null&&targetNode.rightChild==null){
					targetNode.parent.leftChild = null;
					targetNode.parent.height = 1;
				}
				
				if(targetNode.leftChild!=null){
					targetNode.parent.leftChild = targetNode.leftChild;
					targetNode.leftChild.parent = targetNode.parent;
					targetNode.parent.height--;
				}
				
				
				if(targetNode.rightChild!=null){
					targetNode.parent.leftChild = targetNode.rightChild;
					targetNode.rightChild.parent = targetNode.parent;
					targetNode.parent.height--;
				}
			
			}
			
		}
		
		/**
		 * 将originalnode替换为targetNode
		 * @param originalNode
		 * @param targetNode
		 */
		private void replaceNode(BinaryTreeNode<U> originalNode,BinaryTreeNode<U> targetNode){
			
			targetNode.height = originalNode.height;
			
			if(originalNode.leftChild!=null){
				targetNode.leftChild = originalNode.leftChild;
				targetNode.leftChild.parent = targetNode;
			}else{
				targetNode.leftChild = null;
			}
			
			if(originalNode.rightChild!=null){
				targetNode.rightChild = originalNode.rightChild;
				targetNode.rightChild.parent = targetNode;
			}else{
				targetNode.rightChild = null;
			}
			
			//处理父亲节点
			if(originalNode.parent!=null&&originalNode.parent.leftChild == originalNode){
				originalNode.parent.leftChild = targetNode;
				targetNode.setParent(originalNode.parent);
			}
			
			if(originalNode.parent!=null&&originalNode.parent.rightChild == originalNode){
				originalNode.parent.rightChild = targetNode;
				targetNode.setParent(originalNode.parent);
			}
			//处理根节点的情况
			if(originalNode.parent == null){
				root = targetNode;
				targetNode.setParent(null);
			}
			
		}
		
		/**
		 * 删除方法
		 * @param insertNode
		 */
		public boolean remove(BinaryTreeNode<U> removeNode) {
			
			BinaryTreeNode<U> queryNode = queryNode(removeNode);
			
			//左右节点都有
			if(queryNode.leftChild!=null&&queryNode.rightChild!=null){
				BinaryTreeNode<U> findMax = findMin(queryNode.rightChild);
				mergeNode(findMax);
				replaceNode(removeNode, findMax);
				afterRemoveFix(findMax);
			//只有左边的节点
			}else if(queryNode.leftChild!=null){
				BinaryTreeNode<U> findMinNode = findMax(queryNode.leftChild);
				mergeNode(findMinNode);
				replaceNode(removeNode, findMinNode);
				afterRemoveFix(findMinNode);
			//只有右边的节点
			}else if(queryNode.rightChild!=null){
				BinaryTreeNode<U> findMax = findMin(queryNode.rightChild);
				mergeNode(findMax);
				replaceNode(removeNode, findMax);
				afterRemoveFix(findMax);
			//没有子节点
			}else{
				mergeNode(removeNode);
			}
			
			return true;
			
		}
		
		private void afterRemoveFix(BinaryTreeNode<U> fixNode){
			
			//先重设某个点的lh  rh
			BinaryTreeNode<U> temp = fixNode;
			
			do{
				temp.height =  Math.max(temp.leftChild == null ? 0 : temp.leftChild.height,
						temp.rightChild == null ? 0 : temp.rightChild.height) + 1;
				
				if (Math.abs(heightOf(leftChild) - heightOf(rightChild)) > 1) {
					
					System.out.println("temp>2"+temp);  //TODO  这里考虑怎么旋转
					
				}
				
				temp = temp.parent;
			}while(temp!=null);
			
			//检查是否超过2
			
			//如果超过则重新旋转调整
			
		}
		
		

		/**
		 * 插入方法
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
				r.setParent(null);
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
					+ (leftChild != null ? "leftChild=" + leftChild.t + ", " : "")
					+ (rightChild != null ? "rightChild=" + rightChild.t + ", " : "")
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
	
	
	/*2, 28, 30, 5, 31, 3, 42*/
	
/*	public static void main(String[] args) {
		
		AvlTree bt = new AvlTree();
		
		BinaryTreeNode<Integer> root = bt.new BinaryTreeNode<Integer>(2);
		
		BinaryTreeNode<Integer> node1 = bt.new BinaryTreeNode<Integer>(28);
		
		BinaryTreeNode<Integer> node2 = bt.new BinaryTreeNode<Integer>(30);
		
		BinaryTreeNode<Integer> node3 = bt.new BinaryTreeNode<Integer>(5);
		
		BinaryTreeNode<Integer> node4 = bt.new BinaryTreeNode<Integer>(31);
		
		BinaryTreeNode<Integer> node5 = bt.new BinaryTreeNode<Integer>(3);
		
		BinaryTreeNode<Integer> node6 = bt.new BinaryTreeNode<Integer>(42);
		
		bt.insertNode(root);
		
		bt.insertNode(node1);
		
		bt.insertNode(node2);
		
		bt.insertNode(node3);
		
		bt.insertNode(node4);
		
		bt.insertNode(node5);
		
		bt.insertNode(node6);
		
		bt.printAllTree();
		
		bt.printRotateStatic();
		
		BinaryTreeNode<Integer> removeNode = root;
		
		if(removeNode!=null){
			long now = System.currentTimeMillis();
			
			System.out.println("queryNode:"+removeNode);
			
			BinaryTreeNode<Integer> queryNode2 = bt.queryNode(removeNode);
			
			System.out.println("结果："+removeNode);
			
			long end = System.currentTimeMillis();
			
			System.out.println("query耗时:"+(end-now));
		}
		
		bt.removeNode(removeNode);
		
		System.out.println("--------------------after remove--------------------------");
		
		bt.printAllTree();
		
	}*/

	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args) {

		AvlTree bt = new AvlTree();
		BinaryTreeNode<Integer> root = bt.new BinaryTreeNode<Integer>(20);
		Random random = new Random();
		
		int num = 10000;
		
		int[] ran = new int[num];
		
		BinaryTreeNode<Integer> queryNode = null;
		
		long insertStart = System.currentTimeMillis();
		
		int i = 0;
		while(i < num){
			int a = random.nextInt(100000);
			int[] ran1 = Arrays.copyOf(ran, num);
			Arrays.sort(ran1);
			int binarySearch = Arrays.binarySearch(ran1, a);
			
			if(binarySearch<0) {
				ran[i++] = a;
				BinaryTreeNode<Integer> rannode = bt.new BinaryTreeNode<Integer>(a);
				bt.insertNode(rannode);
				if(i==1){
					queryNode = rannode;
				}
			}
		}
		
		long insertEnd = System.currentTimeMillis();
		
		System.out.println("插入耗时:"+(insertEnd-insertStart)+"毫秒");
			
		System.out.println("插入的数字是:"+Arrays.toString(ran));

//		bt.printAllTree();
		
		bt.printRotateStatic();
		
	/*	if(queryNode!=null){
			long now = System.currentTimeMillis();
			
			System.out.println("queryNode:"+queryNode);
			
			BinaryTreeNode<Integer> queryNode2 = bt.queryNode(queryNode);
			
			System.out.println("结果："+queryNode2);
			
			long end = System.currentTimeMillis();
			
			System.out.println("query耗时:"+(end-now));
		}
		
		bt.removeNode(queryNode);
		
		System.out.println("--------------------after remove--------------------------");*/
		
//		bt.printAllTree();
		

	}

}
