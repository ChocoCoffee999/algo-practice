package backjoon;

import java.io.*;
import java.util.*;

/*
그래프
 */
public class No1135 {
    static int N;
    static Node[] deployees;

    static class Node implements Comparable<Node> {
        int timeRequired;
        List<Node> children;

        Node() {
            children = new ArrayList<>();
        }

        public int compareTo(Node o) {
            return Integer.compare(o.timeRequired, this.timeRequired);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int parent;

        deployees = new Node[N];
        st.nextToken();
        deployees[0] = new Node();

        for (int i=1; i<N; i++) {
            parent = Integer.parseInt(st.nextToken());
            deployees[i] = new Node();
            deployees[parent].children.add(deployees[i]);
            deployees[parent].timeRequired++;
        }

        calculate(deployees[0]);

        System.out.println(deployees[0].timeRequired-1);
    }

    private static void calculate(Node node) {
        for (Node child: node.children) {
            calculate(child);
        }

        Collections.sort(node.children);

        for (int i=0; i<node.children.size(); i++) {
            node.timeRequired = Math.max(node.timeRequired, node.children.get(i).timeRequired+i);
        }

        node.timeRequired++;
    }
}
