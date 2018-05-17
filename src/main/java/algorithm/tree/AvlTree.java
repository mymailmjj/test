/**
 * 
 */
package algorithm.tree;

/**
 * 二叉树 平衡
 * @author mymai
 *
 */
public class AvlTree {
	
	private BinaryTreeNode root = null;
	
	private <U extends Comparable<U>> void insertNode(BinaryTreeNode<U> node){
		
		if(root==null){
			this.root = node;
		}else{
			root.insert(node);
		}
	}
	
	
	
	private class BinaryTreeNode<U extends Comparable<U>>{
		
		private U t;  //data
		
		private BinaryTreeNode<U> leftChild;
		
		private BinaryTreeNode<U> rightChild;

		public BinaryTreeNode(U t, BinaryTreeNode<U> left, BinaryTreeNode<U> right) {
			this.t = t;
			this.leftChild = left;
			this.rightChild = right;
		}

		public BinaryTreeNode(U t) {
			this(t,null,null);
		}

		public BinaryTreeNode() {
			super();
		}
		
		/**
		 * @param insertNode
		 */
		public void insert(BinaryTreeNode<U> insertNode){
			if(insertNode==null) return;
			
			int c = this.t.compareTo(insertNode.t);
			
			if(c > 0) insertLeft(insertNode);
			else insertRight(insertNode);
			
		}
		
		/**
		 * 左边插入节点
		 * @param insertNode
		 */
		public void insertLeft(BinaryTreeNode<U> insertNode){
			if(this.leftChild==null){
				this.leftChild = insertNode;
			}else{
				this.leftChild.insert(insertNode);
			}
		}
		
		
		/**
		 * 右边插入节点
		 * @param insertNode
		 */
		public void insertRight(BinaryTreeNode<U> insertNode){
			
			if(this.rightChild==null){
				this.rightChild = insertNode;
			}else{
				this.rightChild.insert(insertNode);
			}
			
		}

		@Override
		public String toString() {
			return "BinaryTreeNode [t=" + t + ", leftChild=" + leftChild + ", rightChild=" + rightChild + "]";
		}
		
	}
	
	
	private void printAllTree(){
		printTree(root);
	}
	
	
	private <U extends Comparable<U>> void printTree(BinaryTreeNode<U> node){
		
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
	
	public static void main(String[] args) {
		
		AvlTree bt = new AvlTree();
		BinaryTreeNode<Integer> root = bt.new BinaryTreeNode<Integer>(20);
		
		BinaryTreeNode<Integer> node1 = bt.new BinaryTreeNode<Integer>(10);
		
		bt.insertNode(root);
		
		bt.insertNode(node1);
		
		bt.printAllTree();
		
		
	}
	

}
