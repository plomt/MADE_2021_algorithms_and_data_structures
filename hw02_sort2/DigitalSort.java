import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DigitalSort {

    public static final int ALPHABET = 26;

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

    public static void radixSort(String[] values, int[] indexes, int k, int n, int m) {

        ArrayList<ArrayList<Integer>> positionsOfStrings = new ArrayList<>(ALPHABET);
        for (int i = 0; i < ALPHABET; i++) {
            positionsOfStrings.add(new ArrayList<>());
        }

        for (int idx = 0; idx < n; idx++) {
            positionsOfStrings.get(values[indexes[idx]].charAt(m - k) - 'a').add(indexes[idx]);
        }

        int idx = 0;
        for (int i = 0; i < ALPHABET; i++) {
            for (int j = 0; j < positionsOfStrings.get(i).size(); j++) {
                indexes[idx] = positionsOfStrings.get(i).get(j);
                idx++;
            }
        }
    }

    public static void main(String... args) {
        FastReader reader = new FastReader();
        int n = reader.nextInt(); // количество строк
        int m = reader.nextInt(); // длина строк
        int k = reader.nextInt(); // число фаз сортировки

        String[] values = new String[n];
        int[] indexes = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = reader.nextLine();
            indexes[i] = i;
        }

        for (int phase = 1; phase <= k; phase++) {
            radixSort(values, indexes, phase, n, m);
        }

        for (int idx: indexes) {
            System.out.println(values[idx]);
        }
    }
}
