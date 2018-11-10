/**
 * 
 */
package datastructrue.line1;

/**
 * 跳跃表
 * @author az6367
 *
 */
public class SkipList<T extends Comparable<T>> {

	private Node<T> root = null;

	public void insert(Node<T> insertNode) {
		if (root == null) {
			root = insertNode;
		} else {

		}
	}

	static class Level {
		private int levelNum;
		private Node next;
		private int distance;
	}

	static class Node<T> {

		Level[] l;

		private T t;

		private Node back;

		public Level[] getL() {
			return l;
		}

		public void setL(Level[] l) {
			this.l = l;
		}

		public T getT() {
			return t;
		}

		public void setT(T t) {
			this.t = t;
		}

		public Node getBack() {
			return back;
		}

		public void setBack(Node back) {
			this.back = back;
		}

	}

}
