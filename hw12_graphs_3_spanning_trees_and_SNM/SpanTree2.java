import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class SpanTree2 {

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

    static class Pair {
        int from;
        int to;

        public Pair(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }

    static class Node implements Serializable {
        Pair vertexes;
        int weight;

        public Node(int from, int to, int weight) {
            this.vertexes = new Pair(from, to);
            this.weight = weight;
        }

        public static final Comparator<Node> COMPARE_BY_WEIGHT = Comparator.comparingInt(o -> o.weight);
    }

    static class DisjointSearch {
        int[] parents;
        int[] size;

        public DisjointSearch(int n) {
            parents = new int[n];
            size = new int[n];

            for (int i = 0; i < n; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public int get(int val) {
            if (parents[val] == val)
                return val;
            return parents[val] = get(parents[val]);
        }

        public void join(int x, int y) {
            int val1 = get(x);
            int val2 = get(y);

            if (val1 == val2)
                return;

            if (size[val1] < size[val2]) {
                int tmp = val1;
                val1 = val2;
                val2 = tmp;
            }
            // Now val1's size >= val2's size

            parents[val2] = val1;
            size[val1] += size[val2];
            size[val2] = 0;
        }
    }

    public static long kruskal(ArrayList<Node> edges, DisjointSearch dsu) {
        edges.sort(Node.COMPARE_BY_WEIGHT);
        long ans = 0;
        for (Node val: edges) {
            if (dsu.get(val.vertexes.getFrom()) != dsu.get(val.vertexes.getTo())) {
                ans += val.weight;
                dsu.join(val.vertexes.getFrom(), val.vertexes.getTo());
            }
        }
        return ans;
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        DisjointSearch dsu = new DisjointSearch(n);
        ArrayList<Node> edges = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;
            int weight = scanner.nextInt();

            edges.add(new Node(from, to, weight));
        }

        long ans = kruskal(edges, dsu);
        out.println(ans);
        out.flush();
    }
}
