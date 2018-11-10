/**
 * 
 */
package algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * 最大流算法 包括（Ford-Fulkerson算法,Edmonds-Karp算法,Dinic算法） 这里是Dinic算法
 * 1、使用BFS对残余网络进行分层，在分层时，只要进行到汇点的层次数被算出即可停止，因为按照该DFS的规则，和汇点同层或更下一层的节点，是不可能走到汇点的。
 * 2、分完层后，从源点开始，用DFS从前一层向后一层反复寻找增广路(即要求DFS的每一步都必须要走到下一层的节点）。
 * 3、DFS过程中，要是碰到了汇点，则说明找到了一条增广路径。此时要增加总流量的值，消减路径上各边的容量，并添加反向边，即所谓的进行增广。
 * 4、DFS找到一条增广路径后，并不立即结束，而是回溯后继续DFS寻找下一个增广路径。回溯到的结点满足以下的条件： 
 * 		1) DFS搜索树的树边(u,v)上的容量已经变成0。即刚刚找到的增广路径上所增加的流量，等于(u,v)本次增广前的容量。(DFS的过程中，是从u走到更下层的v的)
 * 		2) u是满足条件 1)的最上层的节点。 
 * 5、如果回溯到源点而且无法继续往下走了，DFS结束。因此，一次DFS过程中，可以找到多条增广路径。
 * 6、DFS结束后，对残余网络再次进行分层，然后再进行DFS当残余网络的分层操作无法算出汇点的层次（即BFS到达不了汇点）时，算法结束，最大流求出。
 * 
 * 
 * @author az6367
 *
 */
public class Dinic {

	static int[][] G = new int[300][300];
	static boolean[] visited = new boolean[300];
	static int[] layer = new int[300];
	static int v;
	static int e;

	static boolean countLayer() {
		int depth = 0;
		Arrays.fill(layer, -1);
		layer[1] = 0;
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(1);
		while (!queue.isEmpty()) {
			int cur = queue.poll();
			for (int i = 1; i <= v; i++) {
				if (G[cur][i] > 0 && layer[i] == -1) {
					layer[i] = layer[cur] + 1;
					if (i == v) {
						queue.clear();
						return true;
					}
					queue.add(i);
				}
			}
		}
		return false;
	}

	static int dinic() {
		int res = 0;
		List<Integer> stack = new ArrayList<Integer>();
		while (countLayer()) {
			stack.add(1);
			Arrays.fill(visited, false);
			visited[1] = true;
			while (!stack.isEmpty()) {
				int cur = stack.get(stack.size() - 1);
				if (cur == v) {
					int minFlow = Integer.MAX_VALUE;
					int minS = Integer.MAX_VALUE;
					for (int i = 1; i < stack.size(); i++) {
						int tmps = stack.get(i - 1);
						int tmpe = stack.get(i);
						if (minFlow > G[tmps][tmpe]) {
							minFlow = G[tmps][tmpe];
							minS = tmps;
						}
					}

					// 生成残余网络
					for (int i = 1; i < stack.size(); i++) {
						int tmps = stack.get(i - 1);
						int tmpe = stack.get(i);
						G[tmps][tmpe] -= minFlow;
						G[tmpe][tmps] += minFlow;
					}

					// 退栈到minS
					while (!stack.isEmpty() && stack.get(stack.size() - 1) != minS) {
						stack.remove(stack.size() - 1);
					}

					res += minFlow;
				} else {
					int i;
					for (i = 1; i <= v; i++) {
						if (G[cur][i] > 0 && layer[i] == layer[cur] + 1 && !visited[i]) {
							visited[i] = true;
							stack.add(i);
							break;
						}
					}
					if (i > v) {
						stack.remove(stack.size() - 1);
					}
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			for (int i = 0; i < G.length; i++) {
				Arrays.fill(G[i], 0);
			}

			e = sc.nextInt();
			v = sc.nextInt();
			int s, t, c;
			for (int i = 0; i < e; i++) {
				s = sc.nextInt();
				t = sc.nextInt();
				c = sc.nextInt();
				G[s][t] += c;
			}

			System.out.println(dinic());
		}
	}

}
