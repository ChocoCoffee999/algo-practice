package Backjoon;

import java.io.*;
import java.util.*;

//위상정렬 문제
public class No1005ver2 {
    static int[] inDegree, times;
    static int N, K, W;
    static List<Integer>[] nodes;

    static class Node implements Comparable<Node> {
        int n;
        long t;

        Node(int n, long t) {
            this.n = n;
            this.t = t;
        }

        public int compareTo(Node o) {
            return Long.compare(this.t, o.t);
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int testCase = 0; testCase<T; testCase++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            inDegree = new int[N+1];
            times = new int[N+1];
            nodes = new List[N+1];

            st = new StringTokenizer(br.readLine());

            for (int i=1; i<=N; i++) {
                times[i] = Integer.parseInt(st.nextToken());
                nodes[i] = new ArrayList<>();
            }

            int x, y;
            for (int i=0; i<K; i++) {
                st = new StringTokenizer(br.readLine());

                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());

                nodes[x].add(y);

                inDegree[y]++;
            }

            W = Integer.parseInt(br.readLine());

            PriorityQueue<Node> q = new PriorityQueue<>();
            for (int i=1; i<=N; i++) {
                if (inDegree[i]==0) q.add(new Node(i, times[i]));
            }

            while (!q.isEmpty()) {
                Node cur = q.poll();

                if (cur.n == W) {
                    System.out.println(cur.t);
                    break;
                }

                for (int next: nodes[cur.n]) {
                    inDegree[next]--;
                    if (inDegree[next]==0) {
                        q.add(new Node(next, cur.t+times[next]));
                    }
                }
            }
        }
    }
}
