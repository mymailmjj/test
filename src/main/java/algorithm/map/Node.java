/**
 * 
 */
package algorithm.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 每个节点代表车站的信息
 * @author cango
 * 
 */
public class Node {

	private int lineNo; // 几号线

	private int position; // 车站编号

	private String stationName; // 车站名称

	private boolean isExchange = false;

	private boolean hasSearched = false; // 是否在该线路上已经找过，默认没有

	public boolean isHasSearched() {
		return hasSearched;
	}

	public void setHasSearched(boolean hasSearched) {
		this.hasSearched = hasSearched;
	}

	public Node(int lineNo, int position, String stationName) {
		this.lineNo = lineNo;
		this.position = position;
		this.stationName = stationName;
	}

	public Node(int lineNo, int position, String stationName, boolean isExchange) {
		this(lineNo, position, stationName);
		this.isExchange = isExchange;
	}

	private Map<Integer, Node> map = new HashMap<Integer, Node>();

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isExchange() {
		return isExchange;
	}

	public void setExchange(boolean isExchange) {
		this.isExchange = isExchange;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Map<Integer, Node> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Node> map) {
		this.map = map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		int code = 0;

		char[] charArray = stationName.toCharArray();

		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			code = (int) c * i * i;
		}

		return code;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj == this)
			return true;

		if (obj instanceof String) {
			String o = (String) obj;
			return o.equals(this.stationName);
		}

		if (obj instanceof Node) {
			Node o = (Node) obj;
			return o.stationName.equals(this.stationName);
		}

		return false;
	}

	@Override
	public String toString() {
		return "Node [lineNo="
				+ lineNo
				+ ", position="
				+ position
				+ ", "
				+ (stationName != null ? "stationName=" + stationName + ", "
						: "") + "isExchange=" + isExchange + "]";
	}

}
