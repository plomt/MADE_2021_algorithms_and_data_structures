import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class ShortestWay {
    public static final int INF = Integer.MAX_VALUE;

    static class FastScanner {
        private BufferedReader br;
        private StringTokenizer st;

        public FastScanner(InputStreamReader reader) {
            br = new BufferedReader(reader);
        }

        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine(){
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    static class Node {
        public int dist;
        public int v;

        public Node(int v, int dist) {
            this.dist = dist;
            this.v = v;
        }
    }

    public static int[] dijkstra(int vStart, HashMap<Integer, ArrayList<Node>> adjList, int n) {
        int[] distArray = new int[n];
        Arrays.fill(distArray, INF);

        boolean[] used = new boolean[n];
        Arrays.fill(used, false);

        PriorityQueue<Node> queue = new PriorityQueue<>(n, (p1, p2) -> {
            int key1 = p1.dist;
            int key2 = p2.dist;
            return key1 - key2;
        });

        distArray[vStart] = 0;
        queue.offer(new Node(vStart, 0));

        while (!queue.isEmpty()) {
            int next = queue.poll().v;
            used[next] = true;

            if (adjList.get(next) != null) {
                for (int i = 0; i < adjList.get(next).size(); i++) {
                    Node curNode = adjList.get(next).get(i);

                    if (!used[curNode.v]) {
                        if (distArray[curNode.v] > distArray[next] + curNode.dist) {
                            queue.offer(new Node(curNode.v, distArray[next] + curNode.dist));
                            distArray[curNode.v] = distArray[next] + curNode.dist;
                        }
                    }
                }
            }
        }
        return distArray;
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        HashMap<Integer, ArrayList<Node>> adjList = new HashMap<>(n);
        for (int i = 0; i < m; i++) {
            int v1 = scanner.nextInt() - 1;
            int v2 = scanner.nextInt() - 1;
            int dist = scanner.nextInt();

            if (!adjList.containsKey(v1)) {
                adjList.put(v1, new ArrayList<>());
            }
            adjList.get(v1).add(new Node(v2, dist));

            if (!adjList.containsKey(v2)) {
                adjList.put(v2, new ArrayList<>());
            }
            adjList.get(v2).add(new Node(v1, dist));
        }

        int[] distances = dijkstra(0, adjList, n);
        for (Integer dist: distances)
            out.print(dist + " ");
        out.flush();
    }
}
