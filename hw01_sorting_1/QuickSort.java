import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;


public class QuickSort {

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

    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            Random rand= new Random();
            int randIdx = rand.nextInt(right - left) + left;
            int pivot = array[randIdx];

            int partitionLeft = partitionIt(array, left, right, pivot)[0];
            int partitionRight = partitionIt(array, left, right, pivot)[1];

            quickSort(array, left, partitionLeft - 1);
            quickSort(array,partitionRight + 1, right);
        }
    }

    public static int[] partitionIt(int[] array, int left, int right, int pivot) {
        int leftPtr = left;
        int i = left;
        int rightPtr = right;

        while (i <= rightPtr) {
            if (array[i] < pivot) {
                swap(array, leftPtr, i);
                leftPtr++;
                i++;
            }
            else if (array[i] > pivot) {
                swap(array, i, rightPtr);
                rightPtr--;
            }
            else {
                i++;
            }
        }
        return new int[]{leftPtr, rightPtr};
    }

    public static void swap(int[] array, int left, int right) {
        int tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }

    public static void main(String... args) throws IOException {
        FastReader s = new FastReader();
        int n = s.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++)
            values[i] = s.nextInt();

        quickSort(values, 0, n - 1);

        for (int i = 0; i < n; i++) {
            System.out.print(values[i] + " ");
        }
    }
}
