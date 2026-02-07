package Codetree;

import java.io.*;
import java.util.*;

public class aiRobot {
    static int[][] arr;
    static int N, K, L;

    static class Pos {
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

        //bfs
        void move() {
            if (arr[this.r][this.c]>0) return;

            Queue<Pos> q = new ArrayDeque<>();
            q.add(new Pos(this.r, this.c, 0));
            boolean[][] v = new boolean[N+1][N+1];
            v[this.r][this.c] = true;
            int maxD=-1, nr, nc, nd, minR=-1, minC=-1;
            while (!q.isEmpty()) {
                Pos cur = q.poll();

                nd = cur.d+1;
                if (maxD!=-1&&maxD!=nd) break;
                for (int d=0; d<4; d++) {
                    nr = cur.r+dr[d];
                    nc = cur.c+dc[d];
                    if (nr>0&&nr<=N&&nc>0&&nc<=N&&arr[nr][nc]!=-1&&!v[nr][nc]&&!robotsArr[nr][nc]) {
                        if (maxD==-1) {
                            if (arr[nr][nc]>0) {
                                maxD = nd;
                                minR = nr;
                                minC = nc;

                            }
                            else if (arr[nr][nc]==0) {
                                q.add(new Pos(nr,nc,nd));
                                v[nr][nc] = true;
                            }
                        }
                        else if (maxD==nd&&arr[nr][nc]>0) {
                            if (nr<minR) {
                                minR = nr;
                                minC = nc;
                            }
                            else if (nr==minR&&nc<minC) {
                                minC = nc;
                            }
                        }
                    }
                }
            }

            if (minR==-1) return;
            robotsArr[this.r][this.c] = false;
            this.r = minR;
            this.c = minC;
            robotsArr[this.r][this.c] = true;
        }

        void clean() {
            int nr, nc, sd = 0, sum, max = 0;
            for (int d=0; d<4; d++) {
                sum = arr[this.r][this.c];
                for (int dd=0; dd<3; dd++) {
                    nr = this.r + dr[cleanD[d][dd]];
                    nc = this.c + dc[cleanD[d][dd]];

                    if (nr > 0 && nr <= N && nc > 0 && nc <= N && arr[nr][nc]>0) {
                        sum += Math.min(arr[nr][nc], 20);
                    }
                }
                if (sum>max) {
                    sd = d;
                    max = sum;
                }
            }

            if (arr[this.r][this.c]>20) {
                arr[this.r][this.c] -= 20;
            }
            else if (arr[this.r][this.c]>0) {
                arr[this.r][this.c] = 0;
            }

            for (int dd=0; dd<3; dd++) {
                nr = this.r + dr[cleanD[sd][dd]];
                nc = this.c + dc[cleanD[sd][dd]];

                if (nr > 0 && nr <= N && nc > 0 && nc <= N) {
                    if (arr[nr][nc]>20) {
                        arr[nr][nc] -= 20;
                    }
                    else if (arr[nr][nc]>0) {
                        arr[nr][nc] = 0;
                    }
                }
            }
        }

        public String toString() {
            return "r: "+this.r+", c: "+this.c;
        }
    }
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};
    static int[][] cleanD = {{3, 0, 1}, {0, 1, 2}, {1, 2, 3}, {2, 3, 0}};
    static Pos[] robots;
    static boolean[][] robotsArr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        arr = new int[N+1][N+1];
        robotsArr = new boolean[N+1][N+1];
        robots = new Pos[K];

        for (int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=1; j<=N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i=0; i<K; i++) {
            st = new StringTokenizer(br.readLine());
            robots[i] = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            robotsArr[robots[i].r][robots[i].c] = true;
        }

        for (int i=0; i<L; i++) {
            move();
            clean();
            accumulate();
            System.out.println(spread());
        }
    }

    static void move() {
        for (Pos robot:robots) robot.move();
    }

    static void clean() {
        for (Pos robot:robots) robot.clean();
    }

    static void accumulate() {
        for (int i=1; i<=N; i++) {
            for (int j=1; j<=N; j++) {
                if (arr[i][j]>0) arr[i][j]+=5;
            }
        }
    }

    static int spread() {
        int sum, nr, nc, ret=0;
        Queue<Pos> q = new ArrayDeque<>();
        for (int i=1; i<=N; i++) {
            for (int j=1; j<=N; j++) {
                if (arr[i][j]==0) {
                    sum = 0;
                    for (int d=0; d<4; d++) {
                        nr = i+dr[d];
                        nc = j+dc[d];

                        if (nr>0&&nr<=N&&nc>0&&nc<=N&&arr[nr][nc]>0) {
                            sum += arr[nr][nc];
                        }
                    }
                    sum/=10;
                    if (sum>0) {
                        q.add(new Pos(i, j, sum));
                    }
                    ret += sum;
                }
                else if (arr[i][j] > 0) {
                    ret += arr[i][j];
                }
            }
        }

        while (!q.isEmpty()) {
            Pos cur = q.poll();
            arr[cur.r][cur.c] = cur.d;
        }

        return ret;
    }
}
