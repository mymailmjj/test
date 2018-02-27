/**
 * 
 */
package algorithm.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 涉及算法的所有操作
 * @author cango
 * 
 */
public class NodeUtils {

	/**
	 * 寻找从当前节点到同一条线上的最近距离，没有找到，则返回-1
	 * 
	 * @param findenode
	 * @return
	 */
	public static int caculateDistanceOnSameLine(Node fromNode, Node findenode) {

		int lineNo = findenode.getLineNo();

		int fromLineNo = fromNode.getLineNo();

		if (lineNo != fromLineNo)
			return -1;

		int findNodeIndex = findNodeFromLine(lineNo, findenode);

		if (findNodeIndex != -1) {
			return Math.abs(fromNode.getPosition() - findenode.getPosition());
		}

		return -1;
	}

	/**
	 * 从某条线上找到某个节点
	 * 
	 * @param lineNo
	 * @param findNode
	 * @return
	 */
	public static int findNodeFromLine(int lineNo, Node findNode) {

		List<Node> list = MetroStation.map.get(lineNo);
		int findNodeIndex = list.indexOf(findNode);

		return findNodeIndex;
	}

	public static int findMinPathToDestination(Node fromNode, Node destiNode) {

		// 如果已经在这个线上找过，则退出
		if (fromNode.isExchange() && fromNode.isHasSearched()) {
			return -1;
		}

		// 1.现在本条线上找，没找到，则找最近的换乘
		int index = caculateDistanceOnSameLine(fromNode, destiNode);

		if (index != -1) {
			System.out.println("在line" + fromNode.getLineNo() + "线路上找到了，从"
					+ fromNode + "到" + destiNode + "\t距离：" + index);
			fromNode.setHasSearched(true);
			return index;
		}

		System.out.println("在line" + fromNode.getLineNo() + "线路没有找到");

		// 2.从换乘开始找所有的线路
		Node exchangeNode = findNearestExchangeNode(fromNode);

		if (exchangeNode == null) {
			System.out.println("没有找到2");
		}

		System.out.println("在line" + fromNode.getLineNo() + "找到最近的交换节点"
				+ exchangeNode + "\t距离："
				+ caculateDistanceOnSameLine(fromNode, exchangeNode));

		List<Node> findAllLineThroughNode = findAllLineThroughNode(fromNode,
				exchangeNode);

		if (findAllLineThroughNode == null) {

			System.out.println("没有找到3");

		}

		Iterator<Node> iterator = findAllLineThroughNode.iterator();

		while (iterator.hasNext()) {

			Node exchangeNode2 = iterator.next();

			if (fromNode.getLineNo() == exchangeNode2.getLineNo()) {
				exchangeNode2.setHasSearched(true);
				continue;
			}

			System.out.println("在line" + exchangeNode2.getLineNo() + "线路上去找");

			return findMinPathToDestination(exchangeNode2, destiNode);
		}

		// 3.从找到的线路上遍历，找目的节点

		return -1;
	}

	public static List<Node> findAllLineThroughNode(Node fromNode, Node n) {

		List<Node> lists = new ArrayList<Node>();

		Collection<List<Node>> values = MetroStation.map.values();

		Iterator<List<Node>> iterator = values.iterator();

		while (iterator.hasNext()) {
			List<Node> list = iterator.next();
			for (Iterator<Node> iterator2 = list.iterator(); iterator2
					.hasNext();) {
				Node node = (Node) iterator2.next();
				if (!node.isHasSearched() && node.equals(n.getStationName())) {
					lists.add(node);
				}
			}
		}

		return lists;
	}

	/**
	 * 
	 * @param fromNode
	 * @param findenode
	 * @return
	 */
	public static Node findNearestExchangeNode(Node fromNode) {

		int lineNo = fromNode.getLineNo();

		List<Node> list = MetroStation.map.get(lineNo);

		int minDistance = Integer.MAX_VALUE;

		Node find = null;

		ListIterator<Node> listIterator = list.listIterator();

		while (listIterator.hasNext()) {
			Node n = listIterator.next();
			if (n.isExchange()) {
				// 求最好距离
				int d = caculateDistanceOnSameLine(fromNode, n);
				if (d < minDistance) {
					minDistance = d;
					find = n;
				}
			}
		}

		return find;
	}

}
