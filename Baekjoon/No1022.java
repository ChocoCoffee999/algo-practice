package backjoon;

/*
수학 구현 문제
일단
r,c에 해당하는 값을 찾는 걸로
 */

import java.io.*;
import java.util.*;

public class No1022 {
    static int N, M, Max;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r1 = Integer.parseInt(st.nextToken());
        int c1 = Integer.parseInt(st.nextToken());
        int r2 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());

        N = r2 - r1 + 1;
        M = c2 - c1 + 1;
        Max = 0;
        int[][] arr = new int[N][M];

        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                arr[i][j] = calculate(r1+i, c1+j);
            }
        }

        int formatN = 0;

        while (Max>0) {
            Max/=10;
            formatN++;
        }

        String format = "%"+formatN+"d";
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                sb.append(String.format(format,arr[i][j])).append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb.toString());
    }

    private static int calculate(int r, int c) {
        int n = Math.max(Math.abs(r), Math.abs(c));
        int line = n*2+1;
        int max = line*line;

        if (r==n) {
            max-=(n-c);
        }
        else if (c==-n) {
            max-=(line-1);
            max-=(n-r);
        }
        else if (r==-n) {
            max-=(line-1)*2;
            max-=(n+c);
        }
        else {
            max-=(line-1)*3;
            max-=(n+r);
        }

        Max = Math.max(Max,max);

        return max;
    }
}
