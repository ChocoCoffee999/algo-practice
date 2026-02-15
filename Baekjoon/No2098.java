package backjoon.recheck;

import java.io.*;
import java.util.*;

public class No2098 {
    static int N, complete, max;
    static int[][] arr;
    static int[][] dp;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        complete = (1<<N)-1;
        max = 1000000*N;
        arr = new int[N][N];
        dp = new int[N][1<<N];
        StringTokenizer st;
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(travel(0, 1));
    }

    private static int travel(int city, int visit) {
        if (visit == complete) {
            if (arr[city][0]!=0) {
                return arr[city][0];
            }
            else {
                return max;
            }
        }

        if (dp[city][visit]!=0)
            return dp[city][visit];
        dp[city][visit] = max;

        for (int i=0; i<N; i++) {
            if ((visit&1<<i)==0&&arr[city][i]!=0)
                dp[city][visit] = Math.min(dp[city][visit], travel(i, visit|1<<i)+arr[city][i]);
        }

        return dp[city][visit];
    }
}
