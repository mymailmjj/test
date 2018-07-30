package datastructrue.tree;

import java.util.ArrayList;
import java.util.List;


/**
 * 字典树
 * 常用于搜索引擎中的输入字符联想功能
 * @author az6367
 *
 */
public class TrieTree {

	private TrieNode<Character> root = new TrieNode<Character>(null);

	public void insert(String s) {

		if (s == null)
			throw new IllegalArgumentException();

		char[] charArray = s.toCharArray();

		TrieNode<Character> temp = root;

		int i = 0;

		while (i < charArray.length) {
			// 先算要找的坐标
			char c = charArray[i];
			int index = (int) c - 97;
			TrieNode<Character>[] childNodes = temp.getChildNodes();

			if (childNodes == null) {
				childNodes = new TrieNode[26];
				temp.setChildNodes(childNodes);
			}

			TrieNode<Character> trieNode = childNodes[index];

			if (trieNode == null) {
				String prevStr = s.substring(0, i);
				TrieNode<Character> t = new TrieNode<Character>(c, prevStr);
				childNodes[index] = t;
			}

			// 检查单词是否已经结束
			if (i == charArray.length - 1) {
				childNodes[index].isWord = Boolean.TRUE;
			}

			temp = childNodes[index];

			i++;

		}

	}

	private void queryWords(TrieNode<Character> c, String str, List<String> list) {

		TrieNode<java.lang.Character>[] childNodes = c.childNodes;

		if (childNodes == null)
			return;

		for (int i = 0; i < childNodes.length; i++) {
			TrieNode<Character> trieNode = childNodes[i];
			if (trieNode != null) {
				StringBuffer strbuffer1 = new StringBuffer();
				strbuffer1.append(str);
				strbuffer1.append(trieNode.getT()); // 当前节点

				if (trieNode.isWord) {
					list.add(strbuffer1.toString());
				}

				queryWords(trieNode, strbuffer1.toString(), list);

			}
		}
	}

	private List<String> queryWords(TrieNode<Character> c) {

		if (c == null)
			throw new IllegalArgumentException();

		Character t = c.getT();

		List<String> lists = new ArrayList<String>();

		queryWords(c, c.prevStr + t.toString(), lists);

		return lists;
	}

	/**
	 * 查询，支持模糊查询
	 * 
	 * @param searchStr
	 * @return
	 */
	public List<String> searchNodeWithStr(String searchStr) {

		if (searchStr == null)
			throw new IllegalArgumentException();

		char[] charArray = searchStr.toCharArray();

		TrieNode<Character> temp = root;

		int i = 0;

		while (i < charArray.length) {
			// 先算要找的坐标
			char c = charArray[i];
			int index = (int) c - 97;
			TrieNode<Character>[] childNodes = temp.getChildNodes();

			if (childNodes == null) {
				break;
			}

			if (childNodes[index] == null)
				break;

			temp = childNodes[index];

			i++;

		}

		return queryWords(temp);

	}

	/**
	 * 查看某个关键字是否存在
	 * 
	 * @param searchStr
	 * @return
	 */
	public boolean searchNode(String searchStr) {

		if (searchStr == null)
			throw new IllegalArgumentException();

		char[] charArray = searchStr.toCharArray();

		TrieNode<Character> temp = root;

		int i = 0;

		while (i < charArray.length) {
			// 先算要找的坐标
			char c = charArray[i];
			int index = (int) c - 97;
			TrieNode<Character>[] childNodes = temp.getChildNodes();

			if (childNodes == null) {
				return false;
			}

			if (childNodes[index] == null)
				return false;

			temp = childNodes[index];

			i++;

		}

		return true;
	}

	public void deleteNode(String delStr) {

	}

	static class TrieNode<Character> {

		private String prevStr; // 当前节点到根节点的str
		private Character t; // 值
		private TrieNode<Character> childNodes[];
		private boolean isWord;

		public TrieNode(Character t) {
			this(t, false);
		}

		public TrieNode(Character t, String prevStr) {
			this.t = t;
			this.prevStr = prevStr;
		}

		public String getPrevStr() {
			return prevStr;
		}

		public void setPrevStr(String prevStr) {
			this.prevStr = prevStr;
		}

		public TrieNode(Character t, boolean isWord) {
			this.t = t;
			this.isWord = isWord;
		}

		public boolean isWord() {
			return isWord;
		}

		public void setWord(boolean isWord) {
			this.isWord = isWord;
		}

		public Character getT() {
			return t;
		}

		public void setT(Character t) {
			this.t = t;
		}

		public TrieNode<Character>[] getChildNodes() {
			return childNodes;
		}

		public void setChildNodes(TrieNode<Character>[] childNodes) {
			this.childNodes = childNodes;
		}

	}

	public static void main(String[] args) {

		TrieTree trieTree = new TrieTree();
		trieTree.insert("book");
		trieTree.insert("bookabc");
		trieTree.insert("bag");

		trieTree.insert("rabbit");
		trieTree.insert("rush");

		// boolean searchNode = trieTree.searchNode("book");

		// System.out.println(searchNode);

		List<String> searchNodeWithStr = trieTree.searchNodeWithStr("book");

		System.out.println(searchNodeWithStr);

	}

}
