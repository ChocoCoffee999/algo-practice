package backjoon;

import java.io.*;
import java.util.*;

public class No1562 {
    static int N;
    static long[][][] dp;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        dp = new long[N][10][1<<10];

        for (int i=1; i<10; i++) {
            dp[0][i][1<<i] = 1;
        }
        for (int i=1; i<N; i++) {
            for (int j=0; j<10; j++) {
                for (int k=0; k<1024; k++) {
                    if (j-1>=0) {
                        dp[i][j-1][k|1<<(j-1)]=(dp[i][j-1][k|1<<(j-1)]+dp[i-1][j][k])%1000000000;
                    }
                    if (j+1<=9) {
                        dp[i][j+1][k|1<<(j+1)]=(dp[i][j+1][k|1<<(j+1)]+dp[i-1][j][k])%1000000000;
                    }
                }
            }
        }

        long sum = 0;
        for (int i=0; i<10; i++) {
            sum = (sum+dp[N-1][i][1023])%1000000000;
        }

        System.out.println(sum);
    }
}
