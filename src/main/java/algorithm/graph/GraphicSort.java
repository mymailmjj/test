package algorithm.graph;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * 有向无权图 拓扑排序
 * 
 * @author az6367
 *
 */
public class GraphicSort {

	private int vnums;
	private Vertex[] vers;

	public GraphicSort(int vnums) {
		this.vnums = vnums;
		vers = new Vertex[vnums];
	}

	public void setVertex(int index, char name) {
		vers[index - 1] = new Vertex(index, name);
	}

	public Vertex getVertex(int i) {
		return vers[i - 1];
	}
	
	public void topologicalSort() {
		Vertex start = null;
		
		for (;;) {

			for (int i = 0; i < vnums; i++) {
				Vertex vertex = vers[i];
				if (!vertex.visited && vertex.indegree == 0){
					start = vertex;
					start.visited = true;
					break;
				}
				
				if(i==5) return;
				
			}

			LinkedList<Vertex> container = new LinkedList<>();

			container.push(start);

			while (!container.isEmpty()) {

				Vertex vertext = container.poll();
				
				vertext.visited = true;
				
				System.out.println("print 节点:"+vertext.index+"\t"+vertext.name);

				LinkedList<Edge> neighours = vertext.neighours;

				Iterator<Edge> iterator = neighours.iterator();

				while (iterator.hasNext()) {

					Edge nextEdge = iterator.next();

					Vertex vertex = nextEdge.getTo();

					if (--vertex.indegree == 0) {
						container.push(vertex);
					}

				}

			}

		}
	}
	

	/**
	 * 初始化图
	 */
	public void init() {
		for(int i = 0; i<this.vnums;i++){
			vers[i] = new Vertex(i+1, (char)(97+i));
		}
		
		Vertex v1 = getVertex(1);
		
		Vertex v2 = getVertex(2);
		
		Vertex v3 = getVertex(3);
		
		Vertex v4 = getVertex(4);
		
		Vertex v5 = getVertex(5);
		
		Vertex v6 = getVertex(6);

		v1.addNextNeighours(v6);

		v1.addNextNeighours(v5);

		v1.addNextNeighours(v2);

		v2.addNextNeighours(v3);

		v4.addNextNeighours(v3);

		v4.addNextNeighours(v6);

		v5.addNextNeighours(v6);

		v5.addNextNeighours(v2);

		v5.addNextNeighours(v4);

		
		
		
	}

	static class Vertex {
		private int index;
		private char name;
		private int indegree = 0;   //记录每个点的入度
		private boolean visited = false;

		private LinkedList<Edge> neighours = new LinkedList<Edge>();

		public Vertex(int index, char name) {
			this.index = index;
			this.name = name;
		}

		public Vertex(int index, char name, int indegree, LinkedList neighours) {
			this.index = index;
			this.name = name;
			this.indegree = indegree;
			this.neighours = neighours;
		}

		public void addNextNeighours(Vertex to) {
			Edge e = new Edge(this, to);
			to.indegree++;   
			neighours.add(e);
		}

		public int getIndegree() {
			return indegree;
		}

		public void setIndegree(int indegree) {
			this.indegree = indegree;
		}

		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

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

	public static void main(String[] args) {
		
		GraphicSort g = new GraphicSort(6);
		
		g.init();
		
		g.topologicalSort();

	}

}
