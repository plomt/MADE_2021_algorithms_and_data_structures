import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BinSearch {

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

    public static int binSearch(int[] firstArr, int size, int value) {
        int left = -1;
        int right = size;

        while (left < right - 1) {
            int mid = (left + right) / 2;
            if (firstArr[mid] < value) {
                left = mid;
            }
            else {
                right = mid;
            }
        }

        if (right == size) { // случай когда загадали больше, чем есть в массиве
            return firstArr[--right];
        }

        if (right == 0) { // случай когда загадали меньше, чем есть в массиве
            return firstArr[right];
        }

        int deltaLeft = Math.abs(value - firstArr[right - 1]);
        int deltaRight = Math.abs(value - firstArr[right]);

        return (deltaLeft <= deltaRight ? firstArr[right - 1] : firstArr[right]);
    }

    public static void main(String... args) {
        FastReader reader = new FastReader();

        int n = reader.nextInt();
        int k = reader.nextInt();

        int[] firstArr = new int[n];
        for (int i = 0; i < n; i++) {
            firstArr[i] = reader.nextInt();
        }

        int[] secondArr = new int[k];
        for (int i = 0; i < k; i++) {
            secondArr[i] = reader.nextInt();
        }

        for(int val: secondArr) {
            System.out.println(binSearch(firstArr, n, val));
        }
    }
}
