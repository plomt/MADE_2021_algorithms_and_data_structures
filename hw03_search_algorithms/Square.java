import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Square {
    public static final double EPS = Math.pow(10, -6);
    public static final int START_LEFT = 0; // так как данная функция всегда >= 0

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }

        long nextLong() { return Long.parseLong(next()); }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static double function(double x) {
        return x * x + Math.sqrt(x);
    }

    public static double right(double c) {
        int r = 1;
        while (function(r) <= c) {
            r *= 2;
        }
        return r;
    }

    public static int iterations(double r) {
        return (int)Math.log((r - START_LEFT) / EPS);
    }

    public static double binSearch(double c) {
        double right = right(c);
        double left = START_LEFT;
        int ITN = iterations(right);

        for (int i = 0; i < ITN; i++) {
            double mid = (left + right) / 2;
            if (function(mid) < c) {
                left = mid;
            }
            else {
                right = mid;
            }
        }
        return right;
    }

    public static void main(String... args) {
        FastReader reader = new FastReader();
        double c = reader.nextDouble();

        System.out.println(binSearch(c));
    }
}
