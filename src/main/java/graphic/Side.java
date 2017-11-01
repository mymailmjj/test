/**
 * 
 */
package graphic;

/**
 * @author mujjiang
 *
 */
public class Side {
	
	private Point startPoint;  //开始点
	
	private Point endPoint;    //结束点
	
	private boolean haveDirection = false;  //是否是有向图
	
	public Side(){
		
	}

	public Side(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		startPoint.addSide(this);
		haveDirection = false;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public String toString() {
		return "Side ["
				+ (startPoint != null ? "startPoint=" + startPoint + ", " : "")
				+ (endPoint != null ? "endPoint=" + endPoint : "") + "]";
	}
	
}
