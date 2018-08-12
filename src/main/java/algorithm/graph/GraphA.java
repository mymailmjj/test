package algorithm.graph;

import java.util.Iterator;
import java.util.LinkedList;

import datastructrue.heap.BinaryHeap;

/**
 * 有向正权图
 * 图的最短路径算法
 * dijkstra算法
 * @author az6367
 * 
 * 
 *
 */
public class GraphA {

	private int vnums;
	private Vertex[] vers;

	private BinaryHeap<Vertex> heap = new BinaryHeap<>();
	
	public GraphA(int vnums) {
		this.vnums = vnums;
		vers = new Vertex[vnums];
	}

	public void setVertex(int index, char name) {
		vers[index - 1] = new Vertex(index, name);
	}

	public Vertex getVertex(int i) {
		return vers[i - 1];
	}
	
	
	private Vertex findMinDistanceUnknownVertex(){
		
		int dis = Integer.MAX_VALUE;
		
		Vertex verMin = null;
		
		for(int i= 0; i<vnums; i++){
			Vertex v = vers[i];
			if(!v.visited&&v.getMinDistance()<dis){
				dis = v.getMinDistance();
				verMin = v;
			}
		}
		
		return verMin;
		
		
	}
	
	
	/*private Vertex findMinDistanceUnknownVertex(){
		
		Node removeHead = this.heap.deleteMin();
		
		return removeHead==null?null:(Vertex) removeHead.t;
		
	}*/
	
	
	public void detectMinDistance(Vertex vs) {

		vs.setMinDistance(0); // 初始化第一个几点的距离
		
		for(int i =0; i<this.vnums;i++){
			Vertex vertex = this.vers[i];
			this.heap.insert(vertex);
		}
		
		
		for(;;){
			//dijkstra算法这里要取最小的未访问节点的距离，这里可以使用堆改进性能
			Vertex minVertex = findMinDistanceUnknownVertex();
			
			if(minVertex==null) break;
			
			System.out.println("取出的最小 距离:"+minVertex.minDistance);
			
			LinkedList<Edge> neighours = minVertex.getNeighours();
			
			minVertex.visited = true;
			
			Iterator<Edge> iterator = neighours.iterator();
			
			while (iterator.hasNext()) {

				Edge nextNeighourEdge = (Edge) iterator.next();

				Vertex nextNeighorNode = nextNeighourEdge.getTo();

				if (nextNeighorNode.visited)
					continue;

				int findWeight = nextNeighourEdge.wight;

				int nMinDistance = minVertex.minDistance + findWeight;

				if (nMinDistance < nextNeighorNode.getMinDistance()) {
					nextNeighorNode.setMinDistance(nMinDistance);
					nextNeighorNode.setPrev(minVertex);
					this.heap.decreaseKey(nextNeighorNode);
				}

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
		Integer previndex = v.prev==null?null:v.prev.index;
		String printstr = v.index + "\t" + v.name + "\t" + v.visited + "\t" + v.minDistance + "\t" + previndex;
		System.out.println(printstr);
	}

	static class Edge {

		private Vertex from;
		private int wight;
		private Vertex to;

		public Edge(Vertex from, int wight, Vertex to) {
			this.from = from;
			this.wight = wight;
			this.to = to;
		}

		public Vertex getFrom() {
			return from;
		}

		public void setFrom(Vertex from) {
			this.from = from;
		}

		public int getWight() {
			return wight;
		}

		public void setWight(int wight) {
			this.wight = wight;
		}

		public Vertex getTo() {
			return to;
		}

		public void setTo(Vertex to) {
			this.to = to;
		}

	}

	public static class Vertex implements Comparable<Vertex>{

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

		public int findWeight(Vertex to) {

			Iterator<Edge> iterator = neighours.iterator();

			while (iterator.hasNext()) {
				Edge next = (Edge) iterator.next();
				if (next.getTo() == to) {
					return next.wight;
				}
			}

			return Integer.MAX_VALUE;

		}

		public void addNextNeighours(int wight, Vertex to) {
			Edge e = new Edge(this, wight, to);
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

		//根据距离进行排序,放入到二叉堆中
		@Override
		public int compareTo(Vertex o) {
			return this.minDistance-o.minDistance;
		}

		@Override
		public String toString() {
			return "Vertex [index=" + index + ", name=" + name + ", visited=" + visited + ", minDistance=" + minDistance
					+ "]";
		}

	}

	public static void main(String[] args) {
		GraphA g = new GraphA(6);
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

		v1.addNextNeighours(3, v6);

		v1.addNextNeighours(2, v5);

		v1.addNextNeighours(1, v2);

		v2.addNextNeighours(1, v3);

		v4.addNextNeighours(3, v3);

		v4.addNextNeighours(4, v6);

		v5.addNextNeighours(2, v6);

		v5.addNextNeighours(5, v2);

		v5.addNextNeighours(8, v4);

		g.detectMinDistance(v1);
		
		g.printAllNode();

	}

}
