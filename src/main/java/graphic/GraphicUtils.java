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
public class GraphicUtils {

	public static List<Point> getNeibourPoint(Point p) {

		List<Point> lists = new ArrayList<Point>();

		List<Side> sides = p.getSides();

		for (int i = 0; i < sides.size(); i++) {
			Side side = sides.get(i);
			Point endPoint = side.getEndPoint();
			lists.add(endPoint);
		}

		return lists;
	}

	/**
	 * 广度优先遍历
	 * 
	 * @param point
	 */
	public static void checkNodesWideSearch(Point point) {

		if (!point.isHasVisited()) {
			System.out.println("当前结点" + point);
		}

		point.setHasVisited(true);

		// 先找出这个结点的邻点
		List<Point> neibourPoint = getNeibourPoint(point);

		// 找他相邻的结点
		for (int i = 0; i < neibourPoint.size(); i++) {

			Point point2 = neibourPoint.get(i);
			
			if(point2.isHasVisited()) continue;
			
			System.out.println("当前结点" + point2);

			point2.setHasVisited(true);
		}

		for (int i = 0; i < neibourPoint.size(); i++) {

			Point point2 = neibourPoint.get(i);

			checkNodesWideSearch(point2);

		}

	}

	/**
	 * 
	 * 深度优先遍历
	 * 
	 * @param point
	 */
	public static void checkNodeDeepSearch(Point p) {

		List<Point> neibourPoint = getNeibourPoint(p);

		for (int i = 0; i < neibourPoint.size(); i++) {

			Point point2 = neibourPoint.get(i);
			
			if(point2.isHasVisited()) continue;

			point2.setVisited(true);

			System.out.println("当前结点:" + point2);

			checkNodeDeepSearch(point2);
		}

	}

}
