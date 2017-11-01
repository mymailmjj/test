/**
 * 
 */
package graphic;

import java.util.ArrayList;
import java.util.List;

/**
 * 图的算法
 * 
 * @author mujjiang
 *
 */
public class Point {
	
	private int id;
	
	private boolean hasVisited = false;
	
	public Point(int id, int i, int j) {
		this(i,j);
		this.id = id;
	}
	
	public void setVisited(boolean visited){
		hasVisited = visited;
	}

	public boolean isHasVisited() {
		return hasVisited;
	}

	public void setHasVisited(boolean hasVisited) {
		this.hasVisited = hasVisited;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	private int i;
	
	private int j;
	
	//相邻的结点,从当前结点出发的边
	private List<Side> sides = new ArrayList<Side>();

	public Point(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	
	public void addSide(Side s){
		sides.add(s);
	}
	
	@Override
	public String toString() {
		return "Point [id=" + id + ", i=" + i + ", j=" + j +"]";
	}


	public List<Side> getSides() {
		return sides;
	}

	public void setSides(List<Side> sides) {
		this.sides = sides;
	}
	
}
