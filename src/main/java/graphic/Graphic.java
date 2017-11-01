/**
 * 
 */
package graphic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mujjiang
 * 
 */
public class Graphic {

	private Point startPoint = null; // 图的起点

	private List<Point> pointLists = new ArrayList<Point>();

	private List<Side> sideLists = new ArrayList<Side>();

	public Graphic() {
		init();
	}

	private void init() {

		// 假设只有三个点
		Point p1 = new Point(1, 3, 5);

		startPoint = p1;

		Point p2 = new Point(2, 1, 3);

		Point p3 = new Point(3, 2, 0);

		Point p4 = new Point(4, 4, 2);
		
		Point p5 = new Point(5, 5, 4);

		pointLists.add(p1);

		pointLists.add(p2);

		pointLists.add(p3);

		pointLists.add(p4);
		
		pointLists.add(p5);

		// 第一条边
		Side s12 = new Side(p1, p2);
		
		Side s13 = new Side(p1, p3);
		
		Side s15 = new Side(p1, p5);

		Side s24 = new Side(p2, p4);

		Side s35 = new Side(p3, p5);
		
		Side s45 = new Side(p4, p5);

		sideLists.add(s12);

		sideLists.add(s13);

		sideLists.add(s15);
		
		sideLists.add(s24);
		
		sideLists.add(s35);
		
		sideLists.add(s45);

	}

	public void printAllNode() {
		/*System.out.println("****************广度优先遍历开始**********************");
		GraphicUtils.checkNodesWideSearch(startPoint);
		System.out.println("****************广度优先遍历结束**********************");*/
		
		
		System.out.println("****************深度优先遍历开始**********************");
		GraphicUtils.checkNodeDeepSearch(startPoint);
		System.out.println("****************深度优先遍历结束**********************");

	}

}
