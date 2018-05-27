/**
 * 
 */
package algorithm.tree;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * 红黑树
 * 注意红黑树旋转的规则
 * 分三种情况
 *      1. 插入节点的父节点和其叔叔节点（祖父节点的另一个子节点）均为红色的；

        2. 插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的右子节点；

        3. 插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的左子节点。
        
        
        
  * 
 * 性能测试结果：
 * 插入节点数           耗时                                            旋转次数       查询时间
 * 1000        50ms - 120ms		500      0ms
 * 10000       3000ms           5000     0ms
 * 100000      6min             60000    0ms
 * @author mymai
 *
 */
public class RedBlackTree {
	
	private RedBlackTreeNode root = null;
	
	private static AtomicInteger ll = new AtomicInteger(0);  //左旋
	
	private static AtomicInteger rr = new AtomicInteger(0);  //右旋
	
	private static AtomicInteger nodeNum = new AtomicInteger(0);  //先右后左
	
	/**
	 * 插入方法
	 * @param node
	 */
	private <U extends Comparable<U>,K> void insertNode(RedBlackTreeNode<U,K> node) {
		nodeNum.incrementAndGet();
		if (root == null) {
			this.root = node;
		} else {
			root.insert(node);
			
			//插入完毕fix
			fixAfterInsert(node);
		}
	}
	
	
	
	private <U extends Comparable<U>,K> void fixAfterInsert(RedBlackTreeNode<U,K> insertNode){
		
		if(insertNode==null) return;
		
		if(parentOf(insertNode)==null) return;
		
		if(!nodeColorIsRed(parentOf(insertNode))) return;     //如果当前节点的父亲节点不是红色，则跳过
		
		if(condition1(insertNode)){
			step1(insertNode);
		}
		
		Integer num = condition2(insertNode);
		
		if(num!=0){
			step2(insertNode,num);
		}
		
		Integer num1 = condition3(insertNode);
		
		if(num1!=0){
			step3(insertNode,num1);
		}
		
		
	}
	
	
	private <U extends Comparable<U>,K> boolean condition1(RedBlackTreeNode<U,K> insertNode){
		
		if(parentOf(insertNode)==null) return false;
		
		if(parentOf(parentOf(insertNode))==null) return false;
		
		RedBlackTreeNode<U,K> father = parentOf(insertNode);  //父亲
		
		RedBlackTreeNode<U,K> grandPa = parentOf(parentOf(insertNode));  //爷爷
		
		//父亲是红色
		if(nodeColorIsRed(father)){
			
			//左边的情况
			if(father.parentOfLeftSide()&&grandPa.rightChildNode!=null&&nodeColorIsRed(grandPa.rightChildNode)){
				return true;
			}
			
			//右边的情况
			if(father.parentOfRightSide()&&grandPa.leftChildNode!=null&&nodeColorIsRed(grandPa.leftChildNode)){
				return true;
			}
		}
		
		
		return false;
	}
	
	/**
	 * @param insertNode
	 * @return 0 不符合，1左旋，2右旋
	 */
	private <U extends Comparable<U>,K> Integer condition2(RedBlackTreeNode<U,K> insertNode){
		
		//
		boolean flag = false;
		
		if(parentOf(insertNode)==null) return 0;
		
		if(parentOf(parentOf(insertNode))==null) return 0;
		
		RedBlackTreeNode<U,K> father = parentOf(insertNode);  //父亲
		
		RedBlackTreeNode<U,K> grandPa = parentOf(parentOf(insertNode));  //爷爷
		
		//父亲是红色
		if(nodeColorIsRed(father)){
			//父亲是爷爷的左边节点,我是父亲的右侧节点
			if(insertNode.parentOfRightSide()&&father.parentOfLeftSide()){
				flag = grandPa.rightChildNode==null?true:!nodeColorIsRed(grandPa.rightChildNode);  //叔叔是黑色节点
				if(flag) return 1;
			}
			
			if(insertNode.parentOfLeftSide()&&father.parentOfRightSide()){
				flag = grandPa.leftChildNode==null?true:!nodeColorIsRed(grandPa.leftChildNode);  
				if(flag) return 2;
			} 
			
		}
		
		return 0;
		
	}
	
	
	private <U extends Comparable<U>,K> Integer condition3(RedBlackTreeNode<U,K> insertNode){
		
		boolean flag = false;
		
		if(parentOf(insertNode)==null) return 0;
		
		if(parentOf(parentOf(insertNode))==null) return 0;
		
		RedBlackTreeNode<U,K> father = parentOf(insertNode);  //父亲
		
		RedBlackTreeNode<U,K> grandPa = parentOf(parentOf(insertNode));  //爷爷
		
		//父亲是红色
		if(nodeColorIsRed(father)){
			//父亲是爷爷的左边节点,我是父亲的右侧节点
			if(insertNode.parentOfLeftSide()&&father.parentOfLeftSide()){
				flag = grandPa.rightChildNode==null?true:!nodeColorIsRed(grandPa.rightChildNode);  //叔叔是黑色节点
				if(flag) return 1;
			}
			
			if(insertNode.parentOfRightSide()&&father.parentOfRightSide()){
				flag = grandPa.leftChildNode==null?true:!nodeColorIsRed(grandPa.leftChildNode);  
				if(flag) return 2;
			} 
			
		}
		
		return 0;
		
		
	}
	
	private <U extends Comparable<U>,K> void step1(RedBlackTreeNode<U,K> insertNode){
		//父亲节点标记为黑色
		//标记叔叔节点为黑色
		markBlack(parentOf(parentOf(insertNode)).leftChildNode);
		markBlack(parentOf(parentOf(insertNode)).rightChildNode);
		
		RedBlackTreeNode<U,K> currentNode = parentOf(parentOf(insertNode));
		
		if(currentNode==root){
			return;
		}
		
		markRed(parentOf(parentOf(insertNode)));  //爷爷节点标记为红色
		
		//颜色提上去了
		fixAfterInsert(currentNode);
		
	}
	
	
	private <U extends Comparable<U>,K> void step2(RedBlackTreeNode<U,K> insertNode,Integer situation){
		
		if(situation!=null&&situation==1){   //需要左旋的情况
			rotateLeft(insertNode.parent);
			fixAfterInsert(insertNode.leftChildNode);
		}else{
			rotateRight(insertNode.parent);   //需要右旋的情况
			fixAfterInsert(insertNode.rightChildNode);
		}
		
	}
	
	private <U extends Comparable<U>,K> void step3(RedBlackTreeNode<U,K> insertNode,Integer situation){
		
		//父亲标记为黑色
		markBlack(parentOf(insertNode));
		
		RedBlackTreeNode<U, K> grandPa = parentOf(parentOf(insertNode));
		
		markRed(grandPa);
		
		if(situation!=null&&situation==1){ 
			rotateRight(grandPa);
			
		}else{
			rotateLeft(grandPa);
		}
		
		fixAfterInsert(grandPa);
		
	}
	
	/**
	 * 当前节点的父亲节点
	 * @param currentNode
	 * @return
	 */
	private <U extends Comparable<U>,K> RedBlackTreeNode<U,K> parentOf(RedBlackTreeNode<U,K> currentNode){
		return currentNode.parent;
	}
	
	
	/**判断当前节点的颜色是不是红色
	 * @param currentNode
	 * @return 红色返回true,否则返回false
	 */
	private <U extends Comparable<U>,K> boolean nodeColorIsRed(RedBlackTreeNode<U,K> currentNode){
		return !currentNode.isBlackNode();
	}
	
	/**
	 * 将节点标记为红色
	 * @param currentNode
	 */
	private <U extends Comparable<U>,K> void markRed(RedBlackTreeNode<U,K> currentNode){
		currentNode.setBlackNode(false);
	}
	
	/**
	 * 将节点标记为黑色
	 * @param currentNode
	 */
	private <U extends Comparable<U>,K> void markBlack(RedBlackTreeNode<U,K> currentNode){
		currentNode.setBlackNode(true);
	}
	
	/**
	 * 左旋
	 * 
	 * @param insertNode
	 */
	private <U extends Comparable<U>,K> void rotateLeft(RedBlackTreeNode<U,K> p) {
		
		ll.incrementAndGet();   //左旋加1
		
		if (p == null)
			return;
		RedBlackTreeNode<U,K> r = p.rightChildNode;
		
		if(r==null) return;
		
		p.rightChildNode = r.leftChildNode;
		if(p.rightChildNode!=null){
			p.rightChildNode.setParent(p);
		}
		r.leftChildNode = p;
		
		if(p.getParent()==null){
			root = r;
			r.setParent(null);
		}else if(p.getParent().rightChildNode==p){
			p.getParent().rightChildNode = r;
			r.parent = p.parent;
		}else if(p.getParent().leftChildNode==p){
			p.getParent().leftChildNode = r;
			r.parent = p.parent;
		}
		
		p.parent = r;

	}
	
	
	private void printRotateStatic(){
		System.out.println("插入节点数:"+nodeNum.get());
		System.out.println("ll次数："+ll.get());
		System.out.println("rr次数："+rr.get());
	}
	
	
	/**
	 * 右旋
	 * 旋转完毕记得重置长度
	 * @param insertNode
	 */
	private <U extends Comparable<U>,K> void rotateRight(RedBlackTreeNode<U,K> p) {
		
		rr.incrementAndGet();   //左旋加1
		
		if (p == null)
			return;
		RedBlackTreeNode<U,K> r = p.leftChildNode;
		
		if(p.leftChildNode==null) {
			return;
		}
		
		p.leftChildNode = r.rightChildNode;
		if(p.leftChildNode!=null){
			p.leftChildNode.setParent(p);
		}
		r.rightChildNode = p;
		
		if(p.getParent()==null){
			root = r;
			root.setParent(null);
			root.setBlackNode(true);
		}else if(p.getParent().rightChildNode==p){
			p.getParent().rightChildNode = r;
			r.parent = p.parent;
		}else if(p.getParent().leftChildNode==p){
			p.getParent().leftChildNode = r;
			r.parent = p.parent;
		}
		
		p.parent =r;
		
	}

	
	
	public <U extends Comparable<U>,K> RedBlackTreeNode<U,K> newNode(U u,K k){
		
		Entry<U,K> entry = new Entry<U, K>(u, k);
		
		return new RedBlackTreeNode<U,K>(entry);
	}
	
	private class Entry<K extends Comparable<K>,T> implements Comparable<Entry<? extends K,? extends T>>{
		private K k;
		private T value;
		@Override
		public int compareTo(Entry<? extends K,? extends T> o) {
			return this.k.compareTo(o.k);
		}
		
		
		public Entry(K k, T value) {
			this.k = k;
			this.value = value;
		}


		@Override
		public String toString() {
			return "Entry [" + (k != null ? "k=" + k + ", " : "") + (value != null ? "value=" + value : "") + "]";
		}
		
		
	}
	
	private class RedBlackTreeNode<U extends Comparable<U>,K> implements Comparable<RedBlackTreeNode>{
		
		private Entry<U,K> entry;
		
		private RedBlackTreeNode<U,K> leftChildNode;
		
		private RedBlackTreeNode<U,K> rightChildNode;
		
		private RedBlackTreeNode<U,K> parent;
		
		public RedBlackTreeNode<U, K> getParent() {
			return parent;
		}

		
		/**
		 * 当前节点的父亲节点
		 * @param currentNode
		 * @return
		 */
		private boolean parentOfLeftSide(){
			return this.parent.leftChildNode == this;
		}
		
		
		/**
		 * 当前节点的父亲节点
		 * @param currentNode
		 * @return
		 */
		private boolean parentOfRightSide(){
			return this.parent.rightChildNode == this;
		}
		
		

		public void setParent(RedBlackTreeNode<U, K> parent) {
			this.parent = parent;
		}

		private boolean REDNODE = Boolean.FALSE;
		
		private boolean blackNode = Boolean.TRUE;   //是否是黑色节点

		public RedBlackTreeNode(Entry<U, K> entry, RedBlackTreeNode<U, K> leftNode, RedBlackTreeNode<U, K> rightNode,
				boolean blackNode) {
			this.entry = entry;
			this.leftChildNode = leftNode;
			this.rightChildNode = rightNode;
			this.blackNode = blackNode;
		}
		
		
		/**
		 * 插入方法
		 * @param insertNode
		 */
		public void insert(RedBlackTreeNode<U,K> insertNode) {
			if (insertNode == null)
				return;
			
			//默认插入的为红色
			insertNode.setBlackNode(REDNODE);
			
			U t = entry.k;

			int c = this.entry.k.compareTo(insertNode.entry.k);
			
			if (c > 0) {
				insertLeft(insertNode);
				
			}else if(c <0){
				insertRight(insertNode);
			}
			
		}
		
		
		/**
		 * 左边插入节点
		 * 
		 * @param insertNode
		 */
		public void insertLeft(RedBlackTreeNode<U,K> insertNode) {
			if (this.leftChildNode == null) {
				this.leftChildNode = insertNode;
				insertNode.setParent(this);
			} else {
				this.leftChildNode.insert(insertNode);
			}
			
			
		}

		/**
		 * 右边插入节点
		 * 
		 * @param insertNode
		 */
		public void insertRight(RedBlackTreeNode<U,K> insertNode) {

			if (this.rightChildNode == null) {
				this.rightChildNode = insertNode;
				insertNode.setParent(this);
			} else {
				this.rightChildNode.insert(insertNode);
			}
			
		}
		
		
		/**
		 * 删除方法
		 * @param insertNode
		 */
		public boolean remove(RedBlackTreeNode<U,K> removeNode) {
			
			
			
			
			
			
			
			
			
			return true;
			
		}
		
		
		
		
		public RedBlackTreeNode<U,K> queryNode(U u){
			
			int compareTo = this.entry.k.compareTo(u);
			
			if(compareTo==0){
				return this;
			}
			
			if(compareTo > 0){
				return this.leftChildNode.queryNode(u);
			}
			
			if(compareTo < 0){
				return this.rightChildNode.queryNode(u);
			}
			
			return null;
			
		}
		
		
		public RedBlackTreeNode<U,K> queryNode(RedBlackTreeNode<U,K> queryNode){
			
			int compareTo = this.entry.k.compareTo(queryNode.entry.k);
			
			if(compareTo==0){
				return this;
			}
			
			if(compareTo > 0){
				return this.leftChildNode.queryNode(queryNode);
			}
			
			if(compareTo < 0){
				return this.rightChildNode.queryNode(queryNode);
			}
			
			return null;
			
		}
		
		

		public RedBlackTreeNode(Entry<U, K> entry, RedBlackTreeNode<U, K> leftNode, RedBlackTreeNode<U, K> rightNode) {
			this(entry, leftNode, rightNode,Boolean.TRUE);
		}

		public RedBlackTreeNode(Entry<U, K> entry) {
			this(entry, null, null);
		}

		public Entry<U, K> getEntry() {
			return entry;
		}

		public void setEntry(Entry<U, K> entry) {
			this.entry = entry;
		}

		public RedBlackTreeNode<U, K> getLeftNode() {
			return leftChildNode;
		}

		public void setLeftNode(RedBlackTreeNode<U, K> leftNode) {
			this.leftChildNode = leftNode;
		}

		public RedBlackTreeNode<U, K> getRightNode() {
			return rightChildNode;
		}

		public void setRightNode(RedBlackTreeNode<U, K> rightNode) {
			this.rightChildNode = rightNode;
		}

		public boolean isBlackNode() {
			return blackNode;
		}

		public void setBlackNode(boolean blackNode) {
			this.blackNode = blackNode;
		}


		@Override
		public String toString() {
			return "RedBlackTreeNode [" + (entry != null ? "entry=" + entry + ", " : "")
					+ (blackNode?"black":"red") + "]"
					+ (parent != null ? "parent=" + parent.entry + ", " : "")
					;
		}


		@Override
		public int compareTo(RedBlackTreeNode o) {
			return this.entry.k.compareTo((U) o.entry.k);
		}
		
		
		
	}
	
	
	private void printAllTree() {
		printTree(root);
	}
	
	
	private <U extends Comparable<U>,K> K queryNode(U node) {
		return (K) root.queryNode(node);
	}
	
	
	private <U extends Comparable<U>,K> void printTree(RedBlackTreeNode<U,K> node) {

		if (node.entry != null) {
			System.out.println("this node data is " + node);
		}

		if (node.leftChildNode != null) {
			printTree(node.leftChildNode);
		}

		if (node.rightChildNode != null) {
			printTree(node.rightChildNode);
		}

	}
	
	
	/*public static void main(String[] args) {
		
		//17, 47, 10, 36, 31, 25, 11
		
		RedBlackTree redBlackTree = new RedBlackTree();
		
		RedBlackTreeNode<Integer, String> root = redBlackTree.newNode(new Integer(17), "a");
		
		redBlackTree.insertNode(root);
		
		RedBlackTreeNode<Integer, String> node1 = redBlackTree.newNode(new Integer(47), "b");
		
		redBlackTree.insertNode(node1);
		
		RedBlackTreeNode<Integer, String> node2 = redBlackTree.newNode(new Integer(10), "c");
		
		redBlackTree.insertNode(node2);
		
		RedBlackTreeNode<Integer, String> node3 = redBlackTree.newNode(new Integer(36), "d");
		
		redBlackTree.insertNode(node3);
		
		RedBlackTreeNode<Integer, String> node4 = redBlackTree.newNode(new Integer(31), "e");
		
		redBlackTree.insertNode(node4);
		
		RedBlackTreeNode<Integer, String> node5 = redBlackTree.newNode(new Integer(25), "f");
		
		redBlackTree.insertNode(node5);
		
		RedBlackTreeNode<Integer, String> node6 = redBlackTree.newNode(new Integer(11), "f");
		
		redBlackTree.insertNode(node6);
		
		RedBlackTreeNode<Integer, String> node7 = redBlackTree.newNode(new Integer(35), "i");
		
		redBlackTree.insertNode(node7);
		
		RedBlackTreeNode<Integer, String> node8 = redBlackTree.newNode(new Integer(50), "g");
		
		redBlackTree.insertNode(node8);
		
		RedBlackTreeNode<Integer, String> node9 = redBlackTree.newNode(new Integer(100), "k");
		
		redBlackTree.insertNode(node9);
		
		redBlackTree.printAllTree();
		
	}
	*/
	
	
	
/*	public static void main(String[] args) {
		
		
		RedBlackTree redBlackTree = new RedBlackTree();
		Random random = new Random();
		
		int num = 100000;
		
		int[] ran = new int[num];
		
		RedBlackTreeNode<Integer, String> queryNode = null;
		
		long insertStart = System.currentTimeMillis();
		
		int i = 0;
		while(i < num){
			int a = random.nextInt(1000000);
			int[] ran1 = Arrays.copyOf(ran, num);
			Arrays.sort(ran1);
			int binarySearch = Arrays.binarySearch(ran1, a);
			
			if(binarySearch<0) {
				ran[i++] = a;
				RedBlackTreeNode<Integer, String> rannode = redBlackTree.newNode(new Integer(a), a+"");
				redBlackTree.insertNode(rannode);
				if(i==1){
					queryNode = rannode;
				}
			}
		}
		
		long insertEnd = System.currentTimeMillis();
		
		System.out.println("插入耗时:"+(insertEnd-insertStart)+"毫秒");
			
		System.out.println("插入的数字是:"+Arrays.toString(ran));

		redBlackTree.printAllTree();
		
		redBlackTree.printRotateStatic();
		
		if(queryNode!=null){
			long now = System.currentTimeMillis();
			
			System.out.println("queryNode:"+queryNode);
			
			Object queryNode2 = redBlackTree.queryNode(queryNode.entry.k);
			
			System.out.println("结果："+queryNode2);
			
			long end = System.currentTimeMillis();
			
			System.out.println("query耗时:"+(end-now));
		}
		
		
	}*/
	
	
	
	public static void main(String[] args) {
		
		
		RedBlackTree redBlackTree = new RedBlackTree();
		TreeSet<RedBlackTreeNode<Integer, String>> redBlackMap = new TreeSet<RedBlackTreeNode<Integer, String>>();
		Random random = new Random();
		
		int num = 100000;
		
		int[] ran = new int[num];
		
		long insertStart = System.currentTimeMillis();
		
		int i = 0;
		while(i < num){
			int a = random.nextInt(1000000);
			int[] ran1 = Arrays.copyOf(ran, num);
			Arrays.sort(ran1);
			int binarySearch = Arrays.binarySearch(ran1, a);
			
			if(binarySearch<0) {
				ran[i++] = a;
				RedBlackTreeNode<Integer, String> rannode = redBlackTree.newNode(new Integer(a), a+"");
				redBlackMap.add(rannode);
			}
		}
		
		long insertEnd = System.currentTimeMillis();
		
		System.out.println("插入耗时:"+(insertEnd-insertStart)+"毫秒");
			
		System.out.println("插入的数字是:"+Arrays.toString(ran));
		
	}
		

}
