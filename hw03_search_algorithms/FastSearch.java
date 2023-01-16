import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FastSearch {

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

    public static int rightBinSearch(int[] arr, int size, int value) {
        int left = -1;
        int right = size;

        while (left < right - 1) {
            int mid = (left + right) / 2;
            if (arr[mid] <= value) {
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

        int n = reader.nextInt();
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = reader.nextInt();
        }

        Arrays.sort(values);

        int k = reader.nextInt();
        for (int i = 0; i < k; i++) {
            int l = reader.nextInt();
            int r = reader.nextInt();

            int right = rightBinSearch(values, n, r);
            int left = rightBinSearch(values, n, l - 1) + 1;

            System.out.print(right - left + 1 +  " ");
        }
    }
}
