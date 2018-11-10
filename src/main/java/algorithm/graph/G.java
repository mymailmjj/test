package algorithm.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class G {

	private Vo[] vs = new Vo[5];

	static class Vo {
		private char c; // 节点名称

		private int loc; // 节点的下标
		// 我的邻接节点
		private LinkedList<Vo> neigbourNodes = new LinkedList<Vo>();

		private boolean visited = false;

		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		public Vo(char c) {
			this.c = c;
		}

		public Vo(char c, int loc) {
			this.c = c;
			this.loc = loc;
		}

		public Vo(char c, int loc, LinkedList<Vo> neigbourNodes) {
			this.c = c;
			this.loc = loc;
			this.neigbourNodes = neigbourNodes;
		}

		public Vo(char c, LinkedList<Vo> hashSet) {
			this.c = c;
			this.neigbourNodes = hashSet;
		}

		public void addNeibourNode(Vo v) {
			neigbourNodes.add(v);
		}

		public char getC() {
			return c;
		}

		public void setC(char c) {
			this.c = c;
		}

		public int getLoc() {
			return loc;
		}

		public void setLoc(int loc) {
			this.loc = loc;
		}

		public LinkedList<Vo> getNeigbourNodes() {
			return neigbourNodes;
		}

		public void setNeigbourNodes(LinkedList<Vo> neigbourNodes) {
			this.neigbourNodes = neigbourNodes;
		}

		@Override
		public String toString() {
			return "Vo [c=" + c + ", loc=" + loc + ", visited=" + visited + "]";
		}

	}

	public Vo getLocNode(int index) {
		return vs[index - 1];
	}

	public void init() {

		for (int i = 0; i < vs.length; i++) {
			char n = (char) (97 + i);
			Vo v = new Vo(n, i + 1); // 第一个节点
			vs[i] = v;
		}

		// 给节点命名
		for (int i = 0; i < vs.length; i++) {
			Vo v = vs[i];
			switch (i) {
			case 0:
				// 2,3,5
				v.addNeibourNode(getLocNode(2));
				v.addNeibourNode(getLocNode(3));
				v.addNeibourNode(getLocNode(5));
				break;
			case 1:
				v.addNeibourNode(getLocNode(1));
				v.addNeibourNode(getLocNode(3));
				v.addNeibourNode(getLocNode(5));
				break;
			case 2:
				v.addNeibourNode(getLocNode(1));
				v.addNeibourNode(getLocNode(2));
				v.addNeibourNode(getLocNode(5));
				break;
			case 3:
				v.addNeibourNode(getLocNode(3));
				v.addNeibourNode(getLocNode(5));
				break;
			case 4:
				v.addNeibourNode(getLocNode(1));
				v.addNeibourNode(getLocNode(2));
				v.addNeibourNode(getLocNode(3));
				v.addNeibourNode(getLocNode(4));
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 广度优先搜索
	 */
	public void printBroadFirst() {
		
		for (int i = 0; i < vs.length; i++) {
			if(!vs[i].visited)
			print(vs[i]);
		}

	}
	
	
	private void print(Vo vo){
		vo.visited = true;
		System.out.println(vo);
		LinkedList<Vo> neigbourNodes = vo.getNeigbourNodes();
		Iterator<Vo> iterator = neigbourNodes.iterator();
		while(iterator.hasNext()){
			Vo next = iterator.next();
			if(!next.visited){
				print(next);
			}
		}
		
	}
	

	/**
	 * 深度优先搜索
	 * 
	 */
	public void deepInFirst() {

		LinkedList<Vo> lists = new LinkedList<Vo>();

		for (int i = 0; i < vs.length; i++) {
			Vo locNode = getLocNode(i + 1); // 从第一个节点开始
			Vo temp = locNode;
			if (temp.visited)
				continue;
			while (true) {
				temp.visited = true;
				LinkedList<Vo> neigbourNodes = temp.getNeigbourNodes();
				Iterator<Vo> iterator = neigbourNodes.iterator();
				boolean isAdd = false;
				while (iterator.hasNext()) {
					Vo next = iterator.next();
					if (next.visited)
						continue;
					else {
						if (!isAdd) {
							temp = next;
							isAdd = true;
						} else {
							if (!lists.contains(next))
								lists.offer(next);
						}
					}
				}

				if (lists.size() == 0)
					break;

				while (!isAdd && lists.size() > 0) {
					temp = lists.poll();
					break;
				}

			}
		}

	}
	
	public static void main(String[] args) {
		
		G g = new G();
		g.init();
		g.printBroadFirst();
		
		System.out.println("--------------------------");
		
	}

}
