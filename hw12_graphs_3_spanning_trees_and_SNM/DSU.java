import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class DSU {

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

    static class DisjointSearch {
        int[] parents;
        int[] min;
        int[] max;
        int[] size;

        public DisjointSearch(int n) {
            parents = new int[n];
            min = new int[n];
            max = new int[n];
            size = new int[n];

            for (int i = 0; i < n; i++) {
                parents[i] = i;
                min[i] = i;
                max[i] = i;
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

            min[val1] = Math.min(min[val1], min[val2]);

            max[val1] = Math.max(max[val1], max[val2]);

            parents[val2] = val1;
            size[val1] += size[val2];
            size[val2] = 0;
        }
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = scanner.nextInt();
        DisjointSearch dsu = new DisjointSearch(n);

        String[] operations;
        operations = scanner.nextLine().split(" ");
        while (operations[0].length() > 0) {
            try {
                int x = Integer.parseInt(operations[1]) - 1;
                if (operations[0].equals("union")) {
                    int y = Integer.parseInt(operations[2]) - 1;
                    dsu.join(x, y);
                }
                else {
                    int val = dsu.get(x);
                    out.println((dsu.min[val] + 1) + " " + (dsu.max[val] + 1) + " " + dsu.size[val]);
                }
                operations = scanner.nextLine().split(" ");
            }
            catch (NoSuchElementException | NullPointerException e) {
                break;
            }
        }
        out.flush();
    }
}
