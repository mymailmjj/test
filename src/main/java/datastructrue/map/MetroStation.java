/**
 * 
 */
package datastructrue.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 数据源 模拟地铁车站的所有车站
 * @author cango
 * 
 */
public class MetroStation {

	public static Map<Integer, List<Node>> map = new HashMap<Integer, List<Node>>();

	static {
		// line1
		Node node10 = new Node(1, 0, "上海马戏城");
		Node node11 = new Node(1, 1, "人民广场",true);   //1号线换乘
		Node node12 = new Node(1, 2, "徐家汇");
		Node node13 = new Node(1, 2, "1号3");
		Node node14 = new Node(1, 2, "1号4");
		Node node15 = new Node(1, 2, "1号5");

		List<Node> lists1 = new ArrayList<Node>();

		lists1.add(node10);
		lists1.add(node11);
		lists1.add(node12);
		lists1.add(node13);
		lists1.add(node14);
		lists1.add(node15);
		
		map.put(1, lists1);

		// line2
		Node node20 = new Node(2, 0, "龙阳路");
		Node node21 = new Node(2, 1, "世纪广场");
		Node node22 = new Node(2, 2, "陆家嘴");
		Node node23 = new Node(2, 3, "人民广场",true); //2号线换乘
		Node node24 = new Node(2, 4, "2号4");
		Node node25 = new Node(2, 5, "2号5");
		Node node26 = new Node(2, 6, "2号6");

		List<Node> lists2 = new ArrayList<Node>();

		lists2.add(node20);
		lists2.add(node21);
		lists2.add(node22);
		lists2.add(node23);
		lists2.add(node24);
		lists2.add(node25);
		lists2.add(node26);
		
		map.put(2, lists2);

		// line8
		Node node80 = new Node(8, 0, "浦江镇");
		Node node81 = new Node(8, 1, "东方体育中心");
		Node node82 = new Node(8, 2, "成山路");
		Node node83 = new Node(8, 3, "耀华路");
		Node node84 = new Node(8, 4, "人民广场",true);  //8号线换乘
		Node node85 = new Node(8, 5, "8号5");

		List<Node> lists8 = new ArrayList<Node>();

		lists8.add(node80);
		lists8.add(node81);
		lists8.add(node82);
		lists8.add(node83);
		lists8.add(node84);
		lists8.add(node85);
		
		map.put(8, lists8);

	}
	
	
	public static Collection<Integer> allLineNum(){
		
		Set<Integer> keySet = map.keySet();
		
		return keySet;
	}

}
