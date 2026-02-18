package backjoon.recheck;

import java.io.*;
import java.util.*;

public class No1103 {
    static int N, M, max;
    static int[][] arr;

    static class Movement {
        int r, c, moveCount;
        long[] visit;

        Movement() {
            r = 0;
            c = 0;
            moveCount = 0;
            visit = new long[N];
        }

        Movement(int r, int c, Movement p) {
            this.r = r;
            this.c = c;
            moveCount = p.moveCount;
            visit = new long[N];

            for (int i=0; i<N; i++) {
                visit[i] = p.visit[i];
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        max = 0;

        arr = new int[N][M];

        String in;
        for (int i=0; i<N; i++) {
            in = br.readLine();

            for (int j=0; j<M; j++) {
                if (in.charAt(j) == 'H')
                    arr[i][j] = -1;
                else
                    arr[i][j] = in.charAt(j) - '0';
            }
        }

        bfs();
        if (max == Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(max);
    }

    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    private static void bfs() {
        Queue<Movement> que = new ArrayDeque<>();
        que.add(new Movement());
        int nr, nc;
        while (!que.isEmpty()) {
            Movement cur = que.poll();

            for (int d=0; d<4; d++) {
                nr = cur.r+dr[d]*arr[cur.r][cur.c];
                nc = cur.c+dc[d]*arr[cur.r][cur.c];

                if(nr>=0&&nr<N&&nc>=0&&nc<M&&arr[nr][nc]!=-1) {
                    if ((cur.visit[nr]&1L<<nc)==1) {
                        max = Integer.MAX_VALUE;
                        return;
                    }
                    else {
                        Movement next = new Movement(nr, nc, cur);
                        next.moveCount++;
                        next.visit[nr]=next.visit[nr]| 1L <<nc;

                        que.add(next);
                    }
                }
                else {
                    max = Math.max(cur.moveCount+1, max);
                }
            }
        }
    }
}
