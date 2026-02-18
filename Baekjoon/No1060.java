package backjoon;

import java.io.*;
import java.util.*;

/*
수학 구현 문제
구간 [A,B] 에서 A!=B 임을 염두하자.
출력에서 맨 뒤 공백이 있어야 통과되는 부분이 있다. ex. "1 2 3 4 "

 */
public class No1060 {
    static int L, N;
    static int[] S;
    static List<Section> Sections;
    static boolean[] SectionsIsPop;
    static PriorityQueue<Node> q;
    static class Section {
        int L, R, popCount;

        Section(int L, int R) {
            this.L = L;
            this.R = R;
        }
    }

    static class Node implements Comparable<Node> {
        int n, from;
        long c;

        Node(int n, long c, int from) {
            this.n = n;
            this.c = c;
            this.from = from;
        }

        public int compareTo(Node o) {
            if (c == o.c) {
                return Integer.compare(this.n, o.n);
            }

            return Long.compare(this.c, o.c);
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        L = Integer.parseInt(br.readLine());
        S = new int[L];
        StringTokenizer st = new StringTokenizer(br.readLine());
        PriorityQueue<Integer> que = new PriorityQueue<>();
        for (int i=0; i<L; i++) {
            S[i] = Integer.parseInt(st.nextToken());
            que.add(S[i]);
        }
        N = Integer.parseInt(br.readLine());
        Sections = new ArrayList<>();

        Arrays.sort(S);

        q = new PriorityQueue<>();

        int min = 1;
        for (int i=0; i<L; i++) {
            if (S[i]-min > 1) {
                Sections.add(new Section(min, S[i] - 1));
            }
            else if (S[i]-min==1) {
                que.add(min);
            }
            min = S[i] +1;
        }

        int c=0;
        while (!que.isEmpty()) {
            sb.append(que.poll()).append(' ');
            c++;

            if (c==N)break;
        }

        if (c<N) {
            SectionsIsPop = new boolean[Sections.size()];
            for (int i = 0; i < Sections.size(); i++)
                pop(i);

            while (!q.isEmpty()) {
                c++;
                Node cur = q.poll();
                if (SectionsIsPop[cur.from]) {
                    pop(cur.from);
                }
                SectionsIsPop[cur.from]=!SectionsIsPop[cur.from];

                sb.append(cur.n).append(' ');

                if (c == N)
                    break;
            }
        }

        // 나머지 무한대 구간으로 채우기
        int last = S.length-1;
        for (int i=1; i<=N-c; i++) {
            sb.append(S[last]+i).append(' ');
        }
        System.out.println(sb);
    }

    private static void pop(int sectionNum) {
        int min = Sections.get(sectionNum).L;
        int max = Sections.get(sectionNum).R;
        int popC = Sections.get(sectionNum).popCount++;

        int first = min + popC;
        int second = max - popC;

        if (first>second)
            return;

        long c = (long) (first - min + 1) * (max - first + 1) ;
        if (first==second) {
            q.add(new Node(first, c, sectionNum));
        }
        else {
            q.add(new Node(first, c, sectionNum));
            q.add(new Node(second, c, sectionNum));
        }
    }
}
