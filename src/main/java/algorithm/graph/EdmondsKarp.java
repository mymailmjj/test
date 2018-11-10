/**
 * 
 */
package algorithm.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * 最大流算法 包括（增广路算法 \Ford-Fulkerson算法,Edmonds-Karp算法,Dinic算法） Edmonds-Karp算法
 * 
 * @author az6367
 *
 */
public class EdmondsKarp {

	static int[][] G = new int[300][300];
	static boolean[] visited = new boolean[300];
	static int[] pre = new int[300];
	static int v;
	static int e;

	static int augmentRoute() {
		Arrays.fill(visited, false);
		Arrays.fill(pre, 0);
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(1);
		boolean flag = false;
		while (!queue.isEmpty()) {
			int cur = queue.poll();
			visited[cur] = true;
			for (int i = 1; i <= v; i++) {
				if (i == cur)
					continue;
				if (G[cur][i] != 0 && !visited[i]) {
					pre[i] = cur;
					if (i == v) {
						flag = true;
						queue.clear();
						break;
					} else
						queue.add(i);
				}
			}
		}

		if (!flag)
			return 0;

		int minFlow = Integer.MAX_VALUE;
		int tmp = v;
		while (pre[tmp] != 0) {
			minFlow = Math.min(minFlow, G[pre[tmp]][tmp]);
			tmp = pre[tmp];
		}

		tmp = v;
		while (pre[tmp] != 0) {
			G[pre[tmp]][tmp] -= minFlow;
			G[tmp][pre[tmp]] += minFlow;
			tmp = pre[tmp];
		}
		return minFlow;
	}

	public static void main(String[] args) {

		String str = "0,1,16,0,2,13,1,3,12,1,2,10,2,3,7,2,4,14,4,5,4,3,5,20";

		String[] split = str.split(",");

		e = 8;

		v = 6;

		int j = 0;

		for (int i = 0; i < G.length; i++) {
			Arrays.fill(G[i], 0);
		}

		while (j < split.length) {
			int res = 0;
			int s, t, c;
			for (int i = 0; i < e; i++) {
				s = Integer.valueOf(split[j++]);
				t = Integer.valueOf(split[j++]);
				c = Integer.valueOf(split[j++]);
				G[s][t] += c;
			}

			for (int i = 0; i < e; i++) {
				System.out.println(Arrays.toString(G[i]));
			}

			int aug;
			while ((aug = augmentRoute()) != 0) {
				res += aug;
			}

			System.out.println(res);
		}
	}

}
