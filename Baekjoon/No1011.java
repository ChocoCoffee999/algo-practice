package Backjoon;

import java.io.*;
import java.util.*;

// 초등학색 수학 공식으로 이진 탐색
public class No1011 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        for (int i=0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());

            long dist = y-x;

            long s = 1, e = dist/2, mid;

            while (s<e) {
                mid = (s+e)/2;
//                System.out.println(mid);
                if (dist<=mid*mid-mid)
                    e = mid-1;
                else if (mid*mid+mid<dist)
                    s = mid+1;
                else
                    break;
            }

            mid = (s+e)/2;

            if (mid*mid<dist) {
                System.out.println(mid*2);
            }
            else
                System.out.println(mid*2-1);

        }
    }
}
