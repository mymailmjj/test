package datastructrue.heap;

/**
 * 二叉堆，可以用来排序
 * 
 * @author mymai
 *
 * @param <T>
 */
public class BinaryHeap<T extends Comparable<T>> {

	private int size = 16;

	private int ensureSize = 0;   //这里放的是里面包含的元素的个数

	private Node<T>[] cap; // 根节点

	private float loadfactor = 0.75f;

	private void ensureSize() {
		int newSize = (int) Math.floor(size * 1.5);
		Node<T>[] ncap = new Node[newSize];
		System.arraycopy(cap, 0, ncap, 0, size);
		cap = ncap;
		System.out.println("进行放大，原来的大小，" + size + "新的大小" + newSize);
		size = newSize;

	}
	
	public void decreaseKey(T t){
		int j = 0;
		for(int i =0 ;i < ensureSize;i++){
			BinaryHeap<T>.Node<T> node = cap[i];
			if(node.t==t){
				j = i;
				break;
			}
		}
		
		shiftUp(j, t);
		
	}
	
	
	private void shiftUp(int i,T t){
		
		int p = Integer.MAX_VALUE;
		while(p >0){
			p = ((i+1)>>1)-1;
			BinaryHeap<T>.Node<T> node = cap[p];
			if(node.t.compareTo(t) > 0){
				swap(p, i);
			}
			i = p;
		}
		
	}
	
	
	private void shiftDown(int i,Node<T> nodev){
		
		int p = 0;
		while(p < ensureSize){
			p = ((i+1)<<1)-1;
			BinaryHeap<T>.Node<T> node = cap[p];
			BinaryHeap<T>.Node<T> node1 = cap[p+1];
			if(node!=null&&node.t.compareTo(nodev.t) < 0){
				swap(p, i);
				i = p;
				continue;
			}
			
			if(node1!=null&&node.t.compareTo(nodev.t) < 0){
				swap(p+1, i);
				i = p+1;
			}
			i = p;
		}
		
		
	}
	
	
	

	public void insert(T t) {
		if (cap == null) {
			cap = new Node[size];
			cap[0] = new Node(t);
		} else {
			Node inserNode = new Node<T>(t); // 先加在最后的位置

			int p = ensureSize;

			int i = 0;

			while (p > 0) {
				i = p;
				p = ((p + 1) >> 1) - 1;
				Node parent = cap[p];
				int r = parent.compareTo(inserNode);
				if (r < 0){
					p = i;
					break;
				}
					
				cap[i] = cap[p];
			}

			cap[p] = inserNode;

		}

		if (++ensureSize > size * loadfactor) {
			ensureSize();
		}

	}
	
	
	public Node deleteMin(){
		if(ensureSize<1) return null;
		Node n = cap[0];
		printTree();
		cap[0] = cap[ensureSize-1];
		cap[ensureSize-1] = null;
		shiftDown(0, cap[0]);
		ensureSize--;
		return n;
		
		
	}

	public Node removeHead() {

		int i = 0;

		int half = ensureSize >> 1;

		while (i < half) {

			int left = ((i + 1) << 1) - 1;
			int right = left + 1;

			Node leftNode = cap[left];
			Node rightNode = cap[right];

			if (rightNode == null) {
				cap[i] = cap[left];
				i = left;
				cap[left] = null;
			} else {
				int compareTo = leftNode.compareTo(rightNode);
				if (compareTo > 0) {
					cap[i] = cap[left];
					i = left;
					cap[left] = null;
				} else {
					cap[i] = cap[right];
					i = right;
					cap[right] = null;
				}

			}

		}

		ensureSize--;

		return cap[0];

	}

	private void swap(int i, int j) {
		Node<T> t = cap[i];
		cap[i] = cap[j];
		cap[j] = t;
	}

	public void printTree() {
		int i = 0;
		System.out.print("(");
		while (i < ensureSize) {
			System.out.print(cap[i++]+"\t");
		}
		
		System.out.print(")");
	}

	public class Node<U extends T> implements Comparable<Node<? extends U>> {

		public U t;

		public Node(U t) {
			this.t = t;
		}

		@Override
		public String toString() {
			return "Node [" + (t != null ? "t=" + t : "") + "]";
		}

		@Override
		public int compareTo(BinaryHeap<T>.Node<? extends U> o) {
			return t.compareTo(o.t);
		}

	}

	public static void main(String[] args) {
		BinaryHeap<Integer> binaryHeap = new BinaryHeap<Integer>();
		binaryHeap.insert(1);
		binaryHeap.insert(2);
		binaryHeap.insert(3);
		binaryHeap.insert(4);
		binaryHeap.insert(5);
		binaryHeap.insert(6);
		binaryHeap.insert(7);

		for (int i = 10; i < 20; i++) {
			binaryHeap.insert(2 * i);
		}

		binaryHeap.printTree();

		System.out.println("------------------------");

		for (int i = 0; i < 15; i++) {
			BinaryHeap.Node removeHead = binaryHeap.removeHead();
			System.out.println(removeHead);
		}

		System.out.println("-----------------------------------");

		binaryHeap.printTree();
	}

}
