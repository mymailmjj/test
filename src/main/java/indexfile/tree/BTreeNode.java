/**
 * 
 */
package indexfile.tree;

import java.util.Arrays;

import indexfile.ByteInter;
import indexfile.OverFlowException;

/**
 * @author az6367
 *
 */
public class BTreeNode<Key, Value> implements ByteInter {

	Key[] keys;
	Value[] values;
	long[] childrens;
	BTreeNode<Key, Value> parent; // 父节点
	boolean isBranch = false; // 是否是非叶子节点非根节点
	private long pageId;
	private BTreeIndex<Key, Value> index;
	private long next;

	public BTreeNode(BTreeIndex<Key, Value> index) {
		this.index = index;
	}

	public BTreeNode<Key, Value> getParent() {
		return parent;
	}

	public void setParent(BTreeNode<Key, Value> parent) {
		this.parent = parent;
	}

	public BTreeIndex<Key, Value> getIndex() {
		return index;
	}

	public void setIndex(BTreeIndex<Key, Value> index) {
		this.index = index;
	}

	public long getNext() {
		return next;
	}

	public void setNext(long next) {
		this.next = next;
	}

	public BTreeNode() {
	}

	public Key[] getKeys() {
		return keys;
	}

	public void setKeys(Key[] keys) {
		this.keys = keys;
	}

	public Value[] getValues() {
		return values;
	}

	public void setValues(Value[] values) {
		this.values = values;
	}

	public boolean isBranch() {
		return isBranch;
	}

	public void setBranch(boolean isBranch) {
		this.isBranch = isBranch;
	}

	public long[] getChildrens() {
		return childrens;
	}

	public void setChildrens(long[] childrens) {
		this.childrens = childrens;
	}

	public long getPageId() {
		return pageId;
	}

	public void setPageId(long pageId) {
		this.pageId = pageId;
	}

	public void setEmpty() {
		setLeafData(createKeys(0), createValue(0));
	}

	private Key[] createKeys(int size) {
		return (Key[]) new Object[size];
	}

	private Value[] createValue(int size) {
		return (Value[]) new Object[size];
	}

	private long[] createChilds(int size) {
		return new long[size];
	}

	public void setLeafData(Key[] keys, Value[] values) {
		this.keys = keys;
		this.values = values;
	}

	public void setBrachData(Key[] keys, long[] childs) {
		this.keys = keys;
		this.childrens = childs;
	}

	public Value put(Key key, Value value) {

		if (isBranch) {
			return getLeafNode(key).put(key, value);
		}

		int idx = Arrays.binarySearch(keys, key);

		// 第一种情况是原来就已经存在了
		if (idx > 0) {
			Value orignalValue = values[idx];
			this.values[idx] = value;
			return orignalValue;
		}

		idx = -(idx + 1);

		setKeys(insertKeys(key, idx));

		setValues(insertValues(value, idx));

		this.index.storeNode(this);

		if (isOverFlow()) {
			try {
				throw new OverFlowException();
			} catch (OverFlowException e) {
				split(this);
			}
		}

		return null;
	}

	public BTreeNode<Key, Value> getLeafNode(Key key) {

		BTreeNode<Key, Value> current = this;

		while (true) {
			if (current.isBranch) {
				int idx = Arrays.binarySearch(current.keys, key);

				idx = idx < 0 ? -(idx + 1) : idx + 1;

				BTreeNode<Key, Value> child = current.getChild(idx);
				
				System.out.println("child keys:"+Arrays.toString(child.keys));
				
				if (child == null)
					throw new IllegalArgumentException("");

				current = child;

			} else {
				break;
			}
		}

		return current;
	}

	public boolean containKey(Key key) {

		if (isBranch) {
			return getLeafNode(key).containKey(key);
		} else {
			int idx = Arrays.binarySearch(keys, key);
			return idx > 0;
		}
	}

	private boolean isOverFlow() {
		return this.keys.length > this.index.getPhase();
	}

	public BTreeNode<Key, Value> getChild(int idx) {
		long pageId = this.childrens[idx];
		return this.index.loadBtreeNode(pageId);
	}

	private <T> T[] insertKeys(T insertObject, int index) {
		int newSize = this.keys == null ? 1 : this.keys.length + 1;
		T[] newT = (T[]) new Object[newSize];
		if (newSize > 1) {
			System.arraycopy(this.keys, 0, newT, 0, index);
			System.arraycopy(this.keys, index, newT, index + 1, this.keys.length - index);
			newT[index] = insertObject;
		} else {
			newT[0] = insertObject;
		}
		return newT;
	}

	private <T> T[] insertValues(T insertObject, int index) {
		int newSize = this.values == null ? 1 : this.values.length + 1;
		T[] newT = (T[]) new Object[newSize];
		if (newSize > 1) {
			System.arraycopy(this.values, 0, newT, 0, index);
			System.arraycopy(this.values, index, newT, index + 1, this.values.length - index);
			newT[index] = insertObject;
		} else {
			newT[0] = insertObject;
		}
		return newT;
	}

	private long[] insertChild(long insertObject, int index) {
		int newSize = this.childrens == null ? 1 : this.childrens.length + 1;
		long[] newT = new long[newSize];
		if (newSize > 1) {
			System.arraycopy(this.childrens, 0, newT, 0, index);
			System.arraycopy(this.childrens, index, newT, index + 1, this.childrens.length - index);
			newT[index] = insertObject;
		} else {
			newT[0] = insertObject;
		}
		return newT;
	}

	public Value get(Key key) {
		System.out.println("查找key:"+key);
		if (isBranch) {
			return getLeafNode(key).get(key);
		} else {
			int idx = Arrays.binarySearch(keys, key);
			return idx < 0 ? null:values[idx];
		}
	}

	private void split(BTreeNode<Key, Value> node) {

		Key splitor = null;

		int pivot = node.keys.length / 2;
		
		Key[] leftKeys = null;

		Key[] rightKeys = null;

		Value[] leftValues = null;

		Value[] rightValues = null;

		long[] leftChild = null;

		long[] rightChild = null;

		

		splitor = node.keys[pivot];

		if (isBranch) {
			
			leftKeys = createKeys(pivot);

			rightKeys = createKeys(node.keys.length - pivot-1);
			
			leftChild = createChilds(pivot+1);
			
			rightChild = createChilds(node.keys.length - pivot);
			
			System.arraycopy(keys, 0, leftKeys, 0, pivot);

			System.arraycopy(keys, pivot+2, rightKeys, 0, node.keys.length - pivot-1);

			System.arraycopy(childrens, 0, leftChild, 0, pivot+1);

			System.arraycopy(childrens, pivot+1, rightChild, 0, node.keys.length - pivot);

		} else {
			
			leftKeys = createKeys(pivot);

			rightKeys = createKeys(node.keys.length - pivot);

			leftValues = createValue(pivot);

			rightValues = createValue(node.keys.length - pivot);

			System.arraycopy(keys, 0, leftKeys, 0, pivot);

			System.arraycopy(keys, pivot, rightKeys, 0, node.keys.length - pivot);
			

			System.arraycopy(values, 0, leftValues, 0, pivot);

			System.arraycopy(values, pivot, rightValues, 0, node.keys.length - pivot);

		}

		// 根据父节点情况处理
		if (parent == null) {

			BTreeNode<Key, Value> lnode = this.index.createNode(this);
			BTreeNode<Key, Value> rnode = this.index.createNode(this);

			if (isBranch) {
				lnode.setBrachData(leftKeys, leftChild);
				rnode.setBrachData(rightKeys, rightChild);
			} else {
				lnode.setLeafData(leftKeys, leftValues);
				rnode.setLeafData(rightKeys, rightValues);
				isBranch = true;
			}

			Key[] keys = createKeys(1);

			keys[0] = splitor;

			setBrachData(keys, new long[] { lnode.getPageId(), rnode.getPageId() });

			index.storeNode(lnode);

			index.storeNode(rnode);

			index.storeNode(this);

		} else {
			BTreeNode<Key, Value> rnode = this.index.createNode(parent);

			if (isBranch) {
				setBrachData(leftKeys, leftChild);
				rnode.setBrachData(rightKeys, rightChild);
			} else {
				setLeafData(leftKeys, leftValues);
				rnode.setLeafData(rightKeys, rightValues);
			}

			rnode.next = next;
			next = rnode.getPageId();
			parent.isBranch = true;
			parent.promote(splitor, rnode.getPageId());

			index.storeNode(rnode);

			index.storeNode(this);

			index.storeNode(parent);

		}

	}

	private void promote(Key key, long longs) {
		int idx = Arrays.binarySearch(keys, key);
		idx = idx < 0 ? -(idx + 1) : idx + 1;
		setBrachData(insertKeys(key, idx), insertChild(longs, idx + 1));
		if (isOverFlow()) {
			try {
				throw new OverFlowException("");
			} catch (OverFlowException e) {
				split(this);
			}
		}
	}

	private Key[] createkeys(int size) {
		return (Key[]) new Object[size];
	}

	private Value[] createValues(int size) {
		return (Value[]) new Object[size];
	}

	@Override
	public byte[] getByte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parse(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "BTreeNode [" + (keys != null ? "keys=" + Arrays.toString(keys) + ", " : "")
				+ (values != null ? "values=" + Arrays.toString(values) + ", " : "")
				+ (childrens != null ? "childrens=" + Arrays.toString(childrens) + ", " : "")
				+ (parent != null ? "parent=" + parent + ", " : "") + "isBranch=" + isBranch + ", pageId=" + pageId
				+ ", " + (index != null ? "index=" + index + ", " : "") + "next=" + next + "]";
	}
	

}
