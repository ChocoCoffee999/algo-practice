package Codetree;

import java.io.*;
import java.util.*;

/*
시뮬 구현 문제
 */
public class deliveryService {
    static class Box implements Comparable<Box> {
        int k, r, c, w, h;

        Box(int k, int r, int c, int w, int h) {
            this.k = k;
            this.r = r;
            this.c = c;
            this.w = w;
            this.h = h;
        }

        public int compareTo(Box o) {
            return Integer.compare(this.k, o.k);
        }
    }

    static int[][] map;
    static List<Box> boxes;
    static int N, M;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N+1][N+1];
        boxes = new ArrayList<>();

        int k, r, c, w, h;

        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());

            k = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            r = bfsR(k, c, w, h);

            boxes.add(new Box(k, r, c, w, h));

        }

        Collections.sort(boxes);

        pop();
    }

    private static int bfsR(int k, int c, int w, int h) {
        for (int i=1; i<=N; i++) {
            for (int j=c; j<c+w; j++) {
                if (map[i][j]!=0) {
                    int r = i-1;
                    for (int l=r; l>r-h; l--) {
                        for (int m=c; m<c+w; m++) {
                            map[l][m] = k;
                        }
                    }
                    return r;
                }
            }
        }

        for (int i=N; i>N-h; i--) {
            for (int j=c; j<c+w; j++) {
                map[i][j] = k;
            }
        }

        return N;
    }
    static boolean[] clear;
    static HashMap<Integer, Integer> hash;
    private static void pop() {
        clear = new boolean[M+1];
        hash = new HashMap<>();
        int c = 0;
        for (Box b: boxes) {
            hash.put(b.k, c++);
        }
        c = 0;
        while (c!=M) {
            for (Box b:boxes) {
                if (clear[hash.get(b.k)]) continue;
                if (bfsCL(b.r, b.c, b.w, b.h)) {
                    check(b.r, b.c, b.w, b.h);
                    clear[hash.get(b.k)] =  true;
                    System.out.println(b.k);
                    c++;
                    break;
                }
            }
            for (Box b:boxes) {
                if (clear[hash.get(b.k)]) continue;
                if (bfsCR(b.r, b.c, b.w, b.h)) {
                    check(b.r, b.c, b.w, b.h);
                    clear[hash.get(b.k)] =  true;
                    System.out.println(b.k);
                    c++;
                    break;
                }
            }
        }
    }

    private static boolean bfsCL(int r, int c, int w, int h) {
        for (int i=c-1; i>0; i--) {
            for (int j=r; j>r-h; j--) {
                if (map[j][i]!=0) return false;
            }
        }

        for (int i=c; i<c+w; i++) {
            for (int j=r; j>r-h; j--)  {
                map[j][i] = 0;
            }
        }
        return true;
    }

    private static boolean bfsCR(int r, int c, int w, int h) {
        for (int i=c+w; i<=N; i++) {
            for (int j=r; j>r-h; j--) {
                if (map[j][i]!=0) return false;
            }
        }

        for (int i=c; i<c+w; i++) {
            for (int j=r; j>r-h; j--)  {
                map[j][i] = 0;
            }
        }
        return true;
    }

    private static void check(int r, int c, int w, int h) {
        Set<Integer> chain = new HashSet<>();

        for (int i=c; i<c+w; i++) {
            for (int j=r-h; j>0; j--) {
                if (map[j][i]!=0) {
                    chain.add(map[j][i]);
                    break;
                }
            }
        }

        for (Box b: boxes) {
            if (chain.contains(b.k)) {
                if (gravity(b)) {
                    check(b.r, b.c, b.w, b.h);
                }
            }
        }
    }

    private static boolean gravity(Box b) {
        int nr = N;
        L:for (int i=b.r+1; i<=N; i++) {
            for (int j=b.c; j<b.c+b.w; j++) {
                if (map[i][j]!=0) {
                    nr = i-1;
                    if (nr==b.r) return false;
                    break L;
                }
            }
        }

        for (int l=b.r; l>b.r-b.h; l--) {
            for (int m=b.c; m<b.c+b.w; m++) {
                map[l][m] = 0;
            }
        }

        for (int i=nr; i>nr-b.h; i--) {
            for (int j=b.c; j<b.c+b.w; j++) {
                map[i][j] = b.k;
            }
        }

        b.r = nr;

        return true;
    }
}