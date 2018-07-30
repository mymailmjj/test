/**
 * 
 */
package datastructrue.line;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * 跳跃表的实现
 * 
 * @author mymai
 *
 */
public class SkipList<T extends Comparable<T>> {

	private int len = 1;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public Node<T> getHead() {
		return head;
	}

	public void setHead(Node<T> head) {
		this.head = head;
	}

	private Node<T> head = new Node<T>();

	public void printAll() {
		Node<T> temp = head;
		Optional<Node<T>> o = Optional.ofNullable(temp);
		while (o.isPresent()) {
			System.out.println(temp);
			temp = temp.l[0].next;
			o = Optional.ofNullable(temp);
		}

	}

	public void insert(Node<T> insertNode) {

		Node<T> temp = head;

		int ll = temp.levelNum - 1;

		Optional<Node<T>> o = Optional.ofNullable(temp);

		while (o.isPresent()) {
			Level level = temp.l[ll];
			Node<T> firstNode = temp;
			temp = level.next;

			o = Optional.ofNullable(temp);

			if (o.isPresent() && temp.t.compareTo(insertNode.t) > 0 && ll >= 0) {
				ll--;
				temp = firstNode;
			}

			if (!o.isPresent() && (firstNode == head || firstNode.t.compareTo(insertNode.t) < 0) && ll >= 0) {
				ll--;
				temp = firstNode;
				
			}

		}
		

		// 找到了插入的节点的前置节点
		// 进行插入
		temp.l[0].next = insertNode; // 向前插入
		insertNode.back = temp; // 设置前置关系

		this.len++;

		boolean isAddLevel = false;
		while ((isAddLevel = rannum())) {
			insertNode.addLevel();

			if (insertNode.levelNum > head.levelNum) {
				head.addLevel();
				head.l[head.levelNum - 1].next = insertNode;
			}

		}
		

	}

	static Random ran = new Random();

	static boolean rannum() {
		int nextInt = ran.nextInt(2);
		return nextInt == 1;
	}

	static class Level<T extends Comparable<T>> {
		int i; // 第几层
		Node<T> next;

		public Level(int i, Node<T> next) {
			this.i = i;
			this.next = next;
		}

		public Level(int i) {
			this.i = i;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		@Override
		public String toString() {
			return "Level [i=" + i + ", " + (next != null ? "next=" + next.t : "") + "]";
		}

	}

	static class Node<T> {

		private int levelNum = 0;

		private T t;

		// 多层
		private Level[] l;

		private Node<T> back;

		public Node(int levelNum, T t, Level[] l, Node<T> back) {
			this.levelNum = levelNum;
			this.t = t;
			this.l = l;
			this.back = back;
		}

		public Node() {
			addLevel();
		}

		public Node(T t) {
			this.t = t;
			addLevel();
		}

		public void addLevel() {
			Level level = new Level<>(this.levelNum++);
			if (l == null) {
				l = new Level[this.levelNum];
				l[0] = level;
			} else {
				Level[] l1 = new Level[this.levelNum];
				l1[this.levelNum - 1] = level;
				System.arraycopy(l, 0, l1, 0, this.levelNum - 1);
				this.l = l1;
			}

		}

		/**
		 * 获取某一层
		 * 
		 * @param i
		 * @return
		 */
		public Level getLevel(int i) {
			return l == null ? null : this.l[i];
		}

		@Override
		public String toString() {
			return "Node [" + (t != null ? "t=" + t + ", " : "levelNum=" + levelNum + ", ")
					+ (l != null ? "l=" + Arrays.toString(l) + ", " : "") + (back != null ? "back=" + back.t : "")
					+ "]";
		}

	}

	public static void main(String[] args) {

		SkipList<Integer> list = new SkipList();

		Node<Integer> node1 = new Node<Integer>(0);
		list.insert(node1);
		Node<Integer> node2 = new Node<Integer>(2);
		list.insert(node2);

	/*	Node<Integer> node3 = new Node<Integer>(4);
		list.insert(node3);*/

		/*Node<Integer> node4 = new Node<Integer>(6);
		list.insert(node4);

		Node<Integer> node5 = new Node<Integer>(8);
		list.insert(node5);*/

		list.printAll();

	}

}
