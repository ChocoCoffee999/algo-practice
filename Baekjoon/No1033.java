package backjoon;

import java.io.*;
import java.util.*;

public class No1033 {
    static int N;
    static int[] arr;
    static List<Integer>[] graph;
    static Set<Integer> pre;
    static int[] prime = {2, 3, 5, 7};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        graph = new List[N];
        pre = new HashSet<>();

        for (int i=0; i<N; i++) {
            arr[i] = 1;
            graph[i] = new ArrayList<>();
        }

        int a, b, p, q, origin_arr_a;


        for (int i=1; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            p = Integer.parseInt(st.nextToken());
            q = Integer.parseInt(st.nextToken());
            origin_arr_a = arr[a];
            propagation(a, p*arr[b], a);
            propagation(b, q*origin_arr_a, b);

            graph[a].add(b);
            graph[b].add(a);
            pre.add(a);
            pre.add(b);
            divide();
        }

        StringBuilder sb = new StringBuilder();

        for (int n:arr) {
            sb.append(n).append(' ');
        }

        System.out.println(sb);
    }

    private static void propagation(int n, int times, int before) {
        arr[n]*=times;
        for (int next:graph[n]) {
            if (next!=before) {
                propagation(next, times, n);
            }
        }
    }

    private static void divide() {
        L:for (int pri: prime) {
            while (true) {
                for (int n: pre) {
                    if (arr[n]%pri!=0) {
                        continue L;
                    }
                }
                for (int n: pre) {
                    arr[n]/=pri;
                }
            }
        }
    }
}
