import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class HungryHorse {
    public static final int[] DX = {1, -1, 1, -1, 2, -2, 2, -2};
    public static final int[] DY = {2, 2, -2, -2, 1, 1, -1, -1};

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

    static class Point  {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static boolean inField(Point point, int n) {
        return point.x >= 0 && point.x < n && point.y >= 0 && point.y < n;
    }

    public static ArrayList<Point> bfs(int x1, int y1, int x2, int y2, int n) {
        boolean[][] used = new boolean[n][n];
        int[][] dist = new int[n][n];
        ArrayDeque<Point> queue = new ArrayDeque<>();
        Point[][] parents = new Point[n][n];

        dist[x1 - 1][y1 - 1] = 0;
        parents[x1 - 1][y1 - 1] = new Point(-1, -1);
        queue.add(new Point(x1 - 1, y1 - 1));
        used[x1 - 1][y1 - 1] = true;
        while (!queue.isEmpty()) {
            Point point = queue.pop();

            for (int i = 0; i < DX.length; i++) {
                Point newPoint = new Point(point.x + DX[i], point.y + DY[i]);

                if (inField(newPoint, n) && !used[newPoint.x][newPoint.y]) {
                    used[point.x + DX[i]][point.y + DY[i]] = true;
                    queue.add(newPoint);
                    dist[newPoint.x][newPoint.y] = dist[point.x][point.y] + 1;
                    parents[newPoint.x][newPoint.y] = point;
                }
            }
        }
        Point cur = new Point(x2 - 1, y2 - 1);
        ArrayList<Point> path = new ArrayList<>();
        while (cur.x != -1 & cur.y != -1) {
            path.add(cur);
            cur = parents[cur.x][cur.y];
        }
        return path;
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = scanner.nextInt();

        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();

        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();


        ArrayList<Point> path = bfs(x1, y1, x2, y2, n);
        out.println(path.size());
        Collections.reverse(path);
        for(Point val: path) {
            out.println((val.x + 1) + " " + (val.y + 1));
        }
        out.flush();
    }
}
