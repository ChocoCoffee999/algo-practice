package backjoon;

import java.io.*;
import java.util.*;

public class No1113 {
    static int N, M, visitCount, max;
    static int[][] arr, waterLevel, visit;
    static Queue<Pos> que;

    static class Pos implements Comparable<Pos> {
        int r, c, d;

        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        Pos(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }

        public int compareTo(Pos o) {
            return Integer.compare(this.d, o.d);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        visitCount = 0;

        arr = new int[N][M];
        waterLevel = new int[N][M];
        visit = new int[N][M];
        que = new ArrayDeque<>();

        String in;

        for (int i=0; i<N; i++) {
            in = br.readLine();

            for (int j=0; j<M; j++) {
                arr[i][j] = in.charAt(j) - '0';

                max = Math.max(arr[i][j], max);
                waterLevel[i][j] = 10;
            }
        }

        // 가장 자리에서 확인 하기

        visitCount++;
        for (int i=0; i<N; i++) {
            check_side(i, 0);
            check_side(i, M-1);
        }

        for (int i=0; i<M; i++) {
            check_side(0, i);
            check_side(N-1, i);
        }

        bfs();

        int sum = 0;

        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (waterLevel[i][j]>0)
                    sum+=waterLevel[i][j]-arr[i][j];
            }
        }

        System.out.println(sum);
    }

    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};
    static int nr, nc;
    static void check_side(int r, int c) {
        if (visit[r][c]==visitCount) return;
        que.add(new Pos(r, c, arr[r][c]));
        visit[r][c] = visitCount;

        while (!que.isEmpty()) {
            Pos cur = que.remove();

            waterLevel[cur.r][cur.c] = 0;

            for (int d=0; d<4; d++) {
                nr = cur.r+dr[d];
                nc = cur.c+dc[d];
                if (nr>=0&&nr<N&&nc>=0&&nc<M&&visit[nr][nc]!=visitCount&&arr[nr][nc]>=cur.d) {
                    que.add(new Pos(nr, nc, arr[nr][nc]));
                    visit[nr][nc] = visitCount;
                }
            }
        }
    }

    static void bfs() {
        visitCount++;
        PriorityQueue<Pos> que = new PriorityQueue<>();
        que.add(new Pos(0, 0, waterLevel[0][0]));
        visit[0][0] = visitCount;

        while (!que.isEmpty()) {
            Pos cur = que.remove();
            if (cur.d>waterLevel[cur.r][cur.c])
                continue;
            for (int d=0; d<4; d++) {
                nr = cur.r + dr[d];
                nc = cur.c + dc[d];

                if (nr>=0&&nr<N&&nc>=0&&nc<M) {
                    if (cur.d==0) {
                        if (waterLevel[nr][nc]==0) {
                            if (visit[nr][nc]==visitCount)
                                continue;
                            visit[nr][nc] = visitCount;
                            que.add(new Pos(nr, nc, waterLevel[nr][nc]));
                        }
                        else {
                            if (waterLevel[nr][nc]>arr[cur.r][cur.c]) {
                                waterLevel[nr][nc] = arr[cur.r][cur.c];
                            que.add(new Pos(nr, nc, arr[cur.r][cur.c]));
                            }
                        }
                    }
                    else {
                        if (waterLevel[nr][nc]>cur.d) {
                            if (arr[nr][nc]>cur.d) {
                                waterLevel[nr][nc] = arr[nr][nc];
                                que.add(new Pos(nr, nc, arr[nr][nc]));
                            }
                            else {
                                waterLevel[nr][nc] = cur.d;
                                que.add(new Pos(nr, nc, cur.d));
                            }
                        }
                    }
                }
            }
        }
    }
}
