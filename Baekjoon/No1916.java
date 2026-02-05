package Backjoon;

import java.io.*;
import java.util.*;

/*
    pq를 사용하지 않는 dijkstra가 더 빠름
 */
public class No1916 {
    static class Edge implements Comparable<Edge> {
        int e, w;

        Edge (int e, int w) {
            this.e = e;
            this.w = w;
        }

        public int compareTo(Edge o) {
            return Integer.compare(this.w, o.w);
        }
    }
    static int N, M, S, E;
    static List<Edge>[] adjList;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        StringTokenizer st;

        adjList = new List[N+1];

        for (int i=1; i<=N; i++) {
            adjList[i] = new ArrayList<>();
        }

        int s, e, w;
        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            adjList[s].add(new Edge(e, w));
//            adjList[e].add(new Edge(s, w));
        }

        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        dijkstra();
//        dijkstraPq();
    }

    private static void dijkstra() {
        int[] dists = new int[N+1];
        boolean[] v = new boolean[N+1];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[S] = 0;

        int minL, minIdx;

        for (int step=1; step<N; step++) {
            minL=Integer.MAX_VALUE;
            minIdx=-1;
            for (int i=1; i<=N; i++) {
                if (!v[i]&&minL>dists[i]) {
                    minIdx = i;
                    minL = dists[i];
                }
            }

            if (minIdx==-1) break;

            v[minIdx] = true;

            for (Edge n: adjList[minIdx]) {
                if (!v[n.e]&&dists[minIdx]+n.w<dists[n.e]) {
                    dists[n.e] = dists[minIdx]+n.w;
                }
            }
        }

        System.out.println(dists[E]);
    }

    private static void dijkstraPq() {
        PriorityQueue<Edge> q = new PriorityQueue<>();
        int[] dists = new int[N+1];
        boolean[] v = new boolean[N+1];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[S] = 0;
        q.add(new Edge(S, 0));

        while (!q.isEmpty()) {
            Edge cur = q.poll();

            if (v[cur.e]) continue;

            v[cur.e] = true;

            for (Edge n: adjList[cur.e]) {
                if (cur.w+n.w<dists[n.e]) {
                    dists[n.e] = cur.w+n.w;
                    q.add(new Edge(n.e, dists[n.e]));
                }
            }
        }

        System.out.println(dists[E]);
    }
}
