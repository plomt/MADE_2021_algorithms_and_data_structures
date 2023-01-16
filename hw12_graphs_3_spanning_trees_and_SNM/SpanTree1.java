import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class SpanTree1 {

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

    static class Point {
        int num;
        int x;
        int y;

        public Point(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }

        public double dist(int x, int y) {
            return (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y);
        }
    }

    public static double[] prima(int vStart, ArrayList<Point> VertexList, int n) {
        double[] distArray = new double[n];
        Arrays.fill(distArray, INF);

        boolean[] used = new boolean[n];
        Arrays.fill(used, false);

        distArray[vStart] = 0;
        double min;

        for (int i = 0; i < n; i++) {
            int next = -1;

            for (Point point: VertexList) {
                if (!used[point.num] && (next == -1 || distArray[point.num] < distArray[next]))
                    next = point.num;
            }

            if (distArray[next] == INF)
                break;

            used[next] = true;
            for (Point point: VertexList) {
                if (!used[point.num]) {
                    min = Math.min(distArray[point.num], point.dist(VertexList.get(next).x, VertexList.get(next).y));
                    distArray[point.num] = min;
                }
            }
        }
        return distArray;
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = scanner.nextInt();
        ArrayList<Point> points = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(i, x, y));
        }

        double[] arr = prima(0, points, n);
        double ans = 0;
        for (Double val: arr) {
            ans += Math.sqrt(val);
        }
        out.print(ans);
        out.flush();
    }
}
