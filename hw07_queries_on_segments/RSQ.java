import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class RSQ {

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

    public static long get(long[] sums, int i) {
        long res = 0;
        for (; i >= 0; i = (i & (i + 1)) - 1) {
            res += sums[i];
        }
        return res;
    }

    public static void add(long[] sums, int i, long x, int n) {
        for (; i < n; i = (i | (i + 1))) {
            sums[i] += x;
        }
    }

    public static void set(long[] values, long[] sums, int idx, long value, int n) {
        long diff = value - values[idx];
        values[idx] = value;
        add(sums, idx, diff, n);
    }

    public static long sum(long[] values, int left, int right) {
        if (left == 0) {
            return get(values, right);
        }
        return get(values, right) - get(values, left - 1);
    }


    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = scanner.nextInt();
        long[] values = new long[n];
        long[] sums = new long[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();

            for (int j = (i & (i + 1)); j <= i; j++) {
                sums[i] += values[j];
            }
        }

        String[] operations;
        operations = scanner.nextLine().split(" ");
        while (operations[0].length() > 0) {
            try {
                if (operations[0].equals("sum")) {
                    int left = Integer.parseInt(operations[1]);
                    int right = Integer.parseInt(operations[2]);
                    out.println(sum(sums, left - 1, right - 1));
                }
                else {
                    int idx = Integer.parseInt(operations[1]);
                    long x = Long.parseLong(operations[2]);
                    set(values, sums, idx - 1, x, n);
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
