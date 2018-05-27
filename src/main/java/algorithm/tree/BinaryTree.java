/**
 * 
 */
package algorithm.tree;


/**
 * 二叉树 查找树
 * @author mymai
 *
 */
public class BinaryTree {
	
	private BinaryTreeNode root = null;
	
	private <U extends Comparable<U>> void insertNode(BinaryTreeNode<U> node){
		
		if(root==null){
			this.root = node;
		}else{
			root.insert(node);
		}
	}
	
	
	
	private class BinaryTreeNode<U extends Comparable<U>> implements Comparable<BinaryTreeNode<U>>{
		
		private U t;  //data
		
		private BinaryTreeNode<U> leftChild;
		
		private BinaryTreeNode<U> rightChild;
		
		private BinaryTreeNode<U> parent;
		

		public BinaryTreeNode<U> getParent() {
			return parent;
		}

		public void setParent(BinaryTreeNode<U> parent) {
			this.parent = parent;
		}

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
				insertNode.setParent(this);
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
				insertNode.setParent(this);
			}else{
				this.rightChild.insert(insertNode);
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
			//只有左边的节点
			}else if(queryNode.leftChild!=null){
				BinaryTreeNode<U> findMinNode = findMax(queryNode.leftChild);
				mergeNode(findMinNode);
				replaceNode(removeNode, findMinNode);
			//只有右边的节点
			}else if(queryNode.rightChild!=null){
				BinaryTreeNode<U> findMax = findMin(queryNode.rightChild);
				mergeNode(findMax);
				replaceNode(removeNode, findMax);
			//没有子节点
			}else{
				mergeNode(removeNode);
			}
			
			return true;
			
		}
		
		
		/**
		 * 将originalnode替换为targetNode
		 * @param originalNode
		 * @param targetNode
		 */
		private void replaceNode(BinaryTreeNode<U> originalNode,BinaryTreeNode<U> targetNode){
			
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
		
		
		
		
		
		private void mergeNode(BinaryTreeNode<U> targetNode){
			
			if(targetNode.parent!=null&&targetNode.parent.rightChild == targetNode){
				
				if(targetNode.leftChild==null&&targetNode.rightChild==null){
					targetNode.parent.rightChild = null;
				}
				
				if(targetNode.leftChild!=null){
					targetNode.parent.rightChild = targetNode.leftChild;
					targetNode.leftChild.parent = targetNode.parent;
				}
				
				
				if(targetNode.rightChild!=null){
					targetNode.parent.rightChild = targetNode.rightChild;
					targetNode.rightChild.parent = targetNode.parent;
				}
			
			}
			
			
			if(targetNode.parent!=null&&targetNode.parent.leftChild == targetNode){
				
				if(targetNode.leftChild==null&&targetNode.rightChild==null){
					targetNode.parent.leftChild = null;
				}
				
				if(targetNode.leftChild!=null){
					targetNode.parent.leftChild = targetNode.leftChild;
					targetNode.leftChild.parent = targetNode.parent;
				}
				
				
				if(targetNode.rightChild!=null){
					targetNode.parent.leftChild = targetNode.rightChild;
					targetNode.rightChild.parent = targetNode.parent;
				}
			
			}
			
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
		

		@Override
		public String toString() {
			return "BinaryTreeNode [t=" + t + ", leftChild=" + leftChild + ", rightChild=" + rightChild + "]";
		}

		@Override
		public int compareTo(BinaryTreeNode o) {
			return this.t.compareTo((U) o.t);
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
		
		BinaryTree bt = new BinaryTree();
		BinaryTreeNode<Integer> root = bt.new BinaryTreeNode<Integer>(20);
		
		BinaryTreeNode<Integer> node1 = bt.new BinaryTreeNode<Integer>(10);
		
		bt.insertNode(root);
		
		bt.insertNode(node1);
		
		bt.printAllTree();
		
		
	}
	

}
