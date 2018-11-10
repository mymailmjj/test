/**
 * 
 */
package indexfile.tree;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import indexfile.Page;
import indexfile.marshaller.LongMarshaller;
import indexfile.marshaller.Marshaller;
import indexfile.os.BufferedOutputStream;
import indexfile.store.PageFile;

/**
 * 这是一个B树实现
 * 
 * @author az6367
 *
 */
public class BTreeIndex<Key, Value> {

	private int phase = 4; // 树的阶

	private BTreeNode<Key, Value> root; // 根节点

	private Marshaller<Key> keyMarshaller;

	private Marshaller<Value> valueMarshaller;

	private NodeMarshaller nodeMarshaller = new NodeMarshaller();

	private long pageid;

	public BTreeIndex() {
	}

	public long getPageid() {
		return pageid;
	}

	public void setPageid(long pageid) {
		this.pageid = pageid;
	}

	public BTreeIndex(long pageid) {
		this.pageid = pageid;
	}

	public Value put(Key key, Value value) {
		return getRoot().put(key, value);
	}

	public Value get(Key key) {
		return getRoot().get(key);
	}

	public void storeNode(BTreeNode<Key, Value> treeNode) {
		System.out.println("store pageId:"+treeNode.getPageId());
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(PageFile.pageSize);
		bufferedOutputStream.seek(PageFile.pageSize * (2 + treeNode.getPageId()));
		nodeMarshaller.writePayload(bufferedOutputStream, treeNode);
		bufferedOutputStream.flush();
	}

	private BTreeNode<Key, Value> loadNode(long pageId) {

		try {
			DataInput loadPageToBuffer = PageFile.loadPageToBuffer(pageId);

			if (loadPageToBuffer == null) {
				BTreeNode<Key, Value> createNode = createNode(pageId);
				storeNode(createNode);
				return createNode;
			}

			BTreeNode<Key, Value> btreeNode = nodeMarshaller.readPayload(loadPageToBuffer);
			if (btreeNode != null) {
				btreeNode.setIndex(this);
				btreeNode.setPageId(pageId);
				return btreeNode;
			}

		} catch (Exception e) {
			e.printStackTrace();
			BTreeNode<Key, Value> createNode = createNode(pageId);
			storeNode(createNode);
			return createNode;
		}

		return null;
	}

	public BTreeNode<Key, Value> getRoot() {
		if (root != null)
			return root;

		BTreeNode<Key, Value> loadNode = loadNode(this.pageid);
		root = loadNode;
		return loadNode;
	}

	private BTreeNode<Key, Value> createNode(long pageid) {
		Page newPage = PageFile.nextEmptyNodePage(this);
		return (BTreeNode<Key, Value>) newPage.getT();
	}

	public BTreeNode<Key, Value> createNode(BTreeNode<Key, Value> parent) {
		Page newPage = PageFile.nextEmptyNodePage(this);
		BTreeNode<Key, Value> node = (BTreeNode<Key, Value>) newPage.getT();
		node.setParent(parent);
		return node;
	}

	private class NodeMarshaller {

		public BTreeNode<Key, Value> readPayload(DataInput in) throws Exception {

			BTreeNode<Key, Value> node = new BTreeNode<Key, Value>();

			try {
				int count = 0;

				count = in.readInt();

				node.keys = (Key[]) new Object[count];

				node.values = (Value[]) new Object[count];

				node.childrens = new long[count + 1];

				Key[] keys = node.getKeys();

				for (int i = 0; i < count; i++) {
					keys[i] = (Key) getKeyMarshaller().readPayload(in);
				}
				
				node.isBranch = in.readBoolean();

				// 是叶子节点
				if (node.isBranch) {

					for (int i = 0; i < count + 1; i++) {
						node.childrens[i] = LongMarshaller.instance.readPayload(in);
					}

				} else {
					for (int i = 0; i < count; i++) {
						node.values[i] = getValueMarshaller().readPayload(in);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

			return node;

		}

		public void writePayload(DataOutput os, BTreeNode<Key, Value> node) {
			System.out.println("--------------------write pageId "+node.getPageId()+" start-------------------------");
			if(node.parent!=null)
			System.out.println("parent pageId:"+node.getParent().getPageId());
			int count = node.keys.length;
			try {
				os.writeInt(count);
				System.out.println("keynum:"+count);
				// write key
				for (int i = 0; i < count; i++) {
					getKeyMarshaller().writePayload(node.keys[i], os);
					System.out.println("keys:"+Arrays.toString(node.keys));
				}

				os.writeBoolean(node.isBranch);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (node.isBranch) {
				System.out.print("\nchildrens:");
				for (int i = 0; i < count+1; i++) {
					System.out.print("\t"+(node.childrens[i]));
					LongMarshaller.instance.writePayload(node.childrens[i], os);
				}
				System.out.print("\n");
			} else {
				
				System.out.println("values:"+Arrays.toString(node.values));
				
				for (int i = 0; i < count; i++) {
					getValueMarshaller().writePayload(node.values[i], os);
				}
			}
			
			System.out.println("--------------------write pageId "+node.getPageId()+" end-------------------------");
		}

	}

	public Marshaller<Value> getValueMarshaller() {
		return valueMarshaller;
	}

	public void setValueMarshaller(Marshaller<Value> valueMarshaller) {
		this.valueMarshaller = valueMarshaller;
	}

	public Marshaller<Key> getKeyMarshaller() {
		return keyMarshaller;
	}

	public void setKeyMarshaller(Marshaller<Key> keyMarshaller) {
		this.keyMarshaller = keyMarshaller;
	}

	public BTreeNode<Key, Value> loadBtreeNode(long pageId) {
		System.out.println("load pageId:"+pageId);
		BTreeNode<Key, Value> loadNode = loadNode(pageId);
		return loadNode;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public static void main(String[] args) {
		BTreeIndex<Long, Long> tree = new BTreeIndex<>();
		tree.setKeyMarshaller(LongMarshaller.instance);
		tree.setValueMarshaller(LongMarshaller.instance);
		tree.put(1L, 100L);
		tree.put(2L, 200L);
		tree.put(3L, 300L);
		tree.put(4L, 400L);
		tree.put(5L, 500L);
		tree.put(6L, 600L);
		tree.put(7L, 700L);

		BTreeNode<Long, Long> root2 = tree.getRoot();
		System.out.println(Arrays.toString(root2.getValues()));

	}

}
