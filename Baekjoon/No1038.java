package backjoon;

import java.io.*;
import java.util.*;

public class No1038 {
    static int N;
    static int[][] dp;
    static StringBuilder sb;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        dp = new int[11][10];

        sb = new StringBuilder();

        int total = 0;

        if (10 > N) {
            System.out.println(N);
        } else {
            for (int i = 0; i < 10; i++) {
                dp[1][i] = 1;
            }
            total += 9;

            L:
            for (int i = 2; i <= 10; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < j; k++) {
                        dp[i][j] += dp[i - 1][k];
                    }
                    total += dp[i][j];
                    if (total == N) {
                        sb.append(j);
                        for (int k = 1; k < i; k++) {
                            sb.append((j - k));
                        }
                        break L;
                    } else if (total > N) {
                        sb.append(j);
                        search(i - 1, j - 1, total);
                        break L;
                    }
                }
            }
            if (!sb.isEmpty()) {
                System.out.println(sb.toString());
            }
            else {
                System.out.println(-1);
            }
        }
    }

    private static void search(int n, int f, int total) {
        for (int i=f; i>=0; i--) {
            total -= dp[n][i];
            if (total == N) {
                for (int k=0; k<n; k++) {
                    sb.append((i-1-k));
                }
                return;
            }
            if (total < N) {
                sb.append((i));
                search(n - 1, i-1, total+dp[n][i]);
                return;
            }
        }
    }
}
