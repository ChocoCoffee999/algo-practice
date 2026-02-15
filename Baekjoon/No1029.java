package backjoon.recheck;

import java.io.*;

public class No1029 {
    static int N;
    static int[][] arr;
    static int[][][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        dp = new int[N][10][1<<N];

        String in;
        for (int i=0; i<N; i++) {
            in = br.readLine();
            for (int j=0; j<N; j++) {
                arr[i][j] = in.charAt(j)-'0';
            }
        }

        System.out.println(Resell(0,0,1)+1);
    }

    private static int Resell(int artist, int price, int visit) {
        if (dp[artist][price][visit]!=0)
            return dp[artist][price][visit];
        dp[artist][price][visit] = 0;

        for (int i=0; i<N; i++) {
            if ((visit&1<<i)==0&&arr[artist][i]>=price) {
                dp[artist][price][visit] = Math.max(dp[artist][price][visit], Resell(i, arr[artist][i], visit|1<<i)+1);
            }
        }

        return dp[artist][price][visit];
    }
}
