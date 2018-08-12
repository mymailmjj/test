package algorithm.graph;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 无权图
 * 图的最短路径算法 
 * 提供了两种算法 detectMinDistanceB和detectMinDistance
 * 
 * @author az6367
 *
 */
public class GraphB {

	private int vnums;
	private Vertex[] vers;

	public GraphB(int vnums) {
		this.vnums = vnums;
		vers = new Vertex[vnums];
	}

	public void setVertex(int index, char name) {
		vers[index - 1] = new Vertex(index, name);
	}

	public Vertex getVertex(int i) {
		return vers[i - 1];
	}

	public void detectMinDistanceB(Vertex vs1) {

		int edges = vnums;

		for (Vertex v : vers) {
			v.setMinDistance(Integer.MAX_VALUE);
			v.visited = false;
		}

		vs1.setMinDistance(0);

		for (int i = 0; i < edges; i++) { // 遍历每种可能的边数

			for (int j = 0; j < vnums; j++) { // 每次都遍历所有的节点

				Vertex vertex = vers[j];

				if (!vertex.visited && vertex.minDistance == i) {

					vertex.visited = true;

					// 寻找当前节点的所有相邻的点
					LinkedList<Edge> neighours = vertex.getNeighours();

					Iterator<Edge> iterator = neighours.iterator();

					while (iterator.hasNext()) {
						Edge edge = (Edge) iterator.next();
						Vertex to = edge.getTo();

						if (to.minDistance == Integer.MAX_VALUE) {
							to.setMinDistance(vertex.minDistance + 1);
							to.setPrev(vertex);
						}
					}

				}

			}

		}

	}

	/**
	 * 第一种算法
	 * 
	 * @param vs
	 */
	public void detectMinDistance(Vertex vs) {

		vs.setMinDistance(0); // 初始化第一个几点的距离

		LinkedList<Vertex> container = new LinkedList<Vertex>();

		container.push(vs);

		while (!container.isEmpty()) {

			Vertex poll = container.poll();

			LinkedList neighours = poll.getNeighours();

			poll.visited = true;

			// 找他的邻接点
			Iterator iterator = neighours.iterator();

			while (iterator.hasNext()) {

				Edge nextNeighourEdge = (Edge) iterator.next();

				Vertex nextNeighorNode = nextNeighourEdge.getTo();

				if (nextNeighorNode.getMinDistance()==Integer.MAX_VALUE) {
					nextNeighorNode.setMinDistance(poll.minDistance + 1);
					nextNeighorNode.setPrev(poll);
				}

				container.push(nextNeighorNode);

			}

		}

	}

	public void printAllNode() {

		Vertex[] vers2 = this.vers;

		String printstr = "index\tname\tvisited\td\tprev";

		System.out.println(printstr);

		for (int i = 0; i < this.vnums; i++) {
			Vertex vertex = vers2[i];
			printVertext(vertex);
		}

	}

	private void printVertext(Vertex v) {
		Integer previndex = v.prev == null ? null : v.prev.index;
		String printstr = v.index + "\t" + v.name + "\t" + v.visited + "\t" + v.minDistance + "\t" + previndex;
		System.out.println(printstr);
	}

	static class Edge {

		private Vertex from;
		private Vertex to;

		public Edge(Vertex from, Vertex to) {
			this.from = from;
			this.to = to;
		}

		public Vertex getFrom() {
			return from;
		}

		public void setFrom(Vertex from) {
			this.from = from;
		}

		public Vertex getTo() {
			return to;
		}

		public void setTo(Vertex to) {
			this.to = to;
		}

	}

	static class Vertex {

		private int index;

		private char name;

		private boolean visited = false;

		private int minDistance = Integer.MAX_VALUE;

		private Vertex prev;

		private LinkedList neighours = new LinkedList();

		public Vertex(int index, char name) {
			this.index = index;
			this.name = name;
		}


		public void addNextNeighours(Vertex to) {
			Edge e = new Edge(this, to);
			neighours.add(e);
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public char getName() {
			return name;
		}

		public void setName(char name) {
			this.name = name;
		}

		public LinkedList getNeighours() {
			return neighours;
		}

		public void setNeighours(LinkedList neighours) {
			this.neighours = neighours;
		}

		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		public int getMinDistance() {
			return minDistance;
		}

		public void setMinDistance(int minDistance) {
			this.minDistance = minDistance;
		}

		public Vertex getPrev() {
			return prev;
		}

		public void setPrev(Vertex prev) {
			this.prev = prev;
		}

	}

	public static void main(String[] args) {
		GraphB g = new GraphB(6);
		g.setVertex(1, 'a');
		g.setVertex(2, 'b');
		g.setVertex(3, 'c');
		g.setVertex(4, 'd');
		g.setVertex(5, 'e');
		g.setVertex(6, 'f');

		Vertex v1 = g.getVertex(1);

		Vertex v2 = g.getVertex(2);

		Vertex v3 = g.getVertex(3);

		Vertex v4 = g.getVertex(4);

		Vertex v5 = g.getVertex(5);

		Vertex v6 = g.getVertex(6);

		v1.addNextNeighours(v6);

		v1.addNextNeighours(v5);

		v1.addNextNeighours(v2);

		v2.addNextNeighours(v1);
		
		v2.addNextNeighours(v3);
		
		v2.addNextNeighours(v5);
		
		v3.addNextNeighours(v2);
		
		v3.addNextNeighours(v4);
		
		v4.addNextNeighours(v3);

		v4.addNextNeighours(v5);

		v4.addNextNeighours(v6);

		v5.addNextNeighours(v1);

		v5.addNextNeighours(v2);

		v5.addNextNeighours(v4);
		
		v5.addNextNeighours(v6);
		
		v6.addNextNeighours(v1);
		
		v6.addNextNeighours(v4);
		
		v6.addNextNeighours(v5);

//		g.detectMinDistance(v1);

		g.detectMinDistanceB(v1);

		g.printAllNode();

	}

}
