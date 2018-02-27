package algorithm.map;

import java.util.List;

/**
 * 
 * 模拟地铁的计价算法
 * @author cango
 *
 */
public class Main {

	public static void main(String[] args) {
		
		List<Node> list1 = MetroStation.map.get(1);
		
		List<Node> list3 = MetroStation.map.get(8);
		
		Node from = list1.get(0);
		
//		Node node13 = list.get(3);
		
		Node find = list3.get(0);
		
		NodeUtils.findMinPathToDestination(from, find);
		
	}

}
