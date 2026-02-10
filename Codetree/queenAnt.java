package Codetree;

import java.io.*;
import java.util.*;

public class queenAnt {
    static class Node {
        int n;
        Node pre;
        Node next;
        boolean alive;

        Node() {
            this.alive = true;
        }

        Node(int n) {
            this.n = n;
            this.alive = true;
        }

        public void add(Node next) {
            next.pre = this;
            this.next = next;
        }
    }

    static StringTokenizer st;
    static Node header;
    static HashMap<Integer, Node> map;
    static TreeSet<Integer> set;
    static int total, min;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine());

        for (int i=0; i<Q; i++) {
            st = new StringTokenizer(br.readLine());
            switch (st.nextToken()) {
                case "100": {
                    header = new Node();
                    map = new HashMap<>();
                    set = new TreeSet<>();
                    total = 0;
                    min = Integer.MAX_VALUE;
                    init();
                    break;
                }
                case "200": {
                    build(Integer.parseInt(st.nextToken()));
                    break;
                }
                case "300": {
                    demolish(Integer.parseInt(st.nextToken()));
                    break;
                }
                case "400": {
                    check(Integer.parseInt(st.nextToken()));
                    break;
                }
            }
        }
    }

    static void init() {
        List<Integer> list = new ArrayList<>();
        int N = Integer.parseInt(st.nextToken());

        for (int i=0; i<N; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(list);
        Node cur = header;
        for (int n:list) {
            Node node = new Node(n);
            map.put(n, node);
            set.add(n);
            node.pre = cur;
            cur.next = node;
            cur = cur.next;
            total++;
        }
    }

    static void build(int p) {
        total++;
        Node node = new Node(p);
        map.put(p, node);
        Integer n = set.lower(p);
        if (n != null) {
            Node pre = map.get(n);
            pre.add(node);
        } else {
            header.add(node);
        }
        set.add(p);
    }

    static void demolish(int q) {
        total--;
        Node cur = header;
        for (int i=0; i<q; i++) cur = cur.next;
        cur.alive = false;
    }

    static void check(int r) {
        if (r>=total) {
            System.out.println(0);
            return;
        }
        int first = set.first();
        int last = set.last();
        while (!map.get(first).alive)
            first = map.get(first).next.n;
        while (!map.get(last).alive)
            last = map.get(last).pre.n;
        int e = last - first;
        int s = 0;
        int mid;

        while (s<e) {
            mid = (s+e)/2;

            if (calculate(r, mid)) {
                e = mid;
            }
            else s = mid+1;
        }

        System.out.println(s);
    }

    static boolean calculate(int r, int l) {
        int c_ants = 1;
        int before = set.first();
        while (!map.get(before).alive)
            before = map.get(before).next.n;
        int sum = 0;
        for (int n: set) {
            if (map.get(n).alive) {
                sum += n - before;
                if (sum > l) {
                    c_ants++;
                    if (c_ants > r) {
                        return false;
                    }
                    sum = 0;
                }
                before = n;
            }
        }
        return true;
    }
}
