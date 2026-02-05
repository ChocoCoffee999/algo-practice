package Backjoon.BFS;

import java.io.*;
import java.util.*;
public class No1260 {
    static List<Integer>[] adjList;
    static Set<Integer>[] sets;
    static int N, M, S;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        adjList = new List[N+1];
        sets = new Set[N+1];
        sb = new StringBuilder();

        for (int i=1; i<=N; i++) {
            adjList[i] = new ArrayList<>();
            sets[i] = new HashSet<>();
        }

        int s, e;

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());

            if (!sets[s].contains(e)) {
                sets[s].add(e);
                sets[e].add(s);

                adjList[s].add(e);
                adjList[e].add(s);
            }
        }

        for (int i=1; i<=N; i++) {
            Collections.sort(adjList[i]);
        }

        sb.append(S);
        boolean[] visit = new boolean[N+1];
        visit[S] = true;
        dfs(S, visit);
        sb.append('\n');
        bfs();
        System.out.println(sb.toString());
    }

    private static void dfs(int c, boolean[] visit) {
        for (int n:adjList[c]) {
            if (!visit[n]) {
                sb.append(' ').append(n);
                visit[n] = true;
                dfs(n, visit);
            }
        }
    }

    private static void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visit = new boolean[N+1];

        q.add(S);
        visit[S] = true;
        sb.append(S);

        while(!q.isEmpty()) {
            int c = q.poll();
            for (int n:adjList[c]) {
                if (visit[n]) continue;
                visit[n] = true;
                q.add(n);
                sb.append(' ').append(n);
            }
        }
    }
}
