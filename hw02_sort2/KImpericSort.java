import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public class KImpericSort {

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

    public static int kOrderStatistic(int[] array, int left, int right, int k) {
        int leftPtr = left;
        int rightPtr = right;
        int partitionLeft = -1;
        int partitionRight = -1;

        if (left == right) {
            return array[left];
        }

        while (rightPtr - leftPtr >= 0) {

            if (rightPtr - leftPtr == 0)
                return array[leftPtr];

            Random rand = new Random();
            int randIdx = rand.nextInt(rightPtr - leftPtr) + leftPtr;
            int pivot = array[randIdx];

            partitionLeft = partitionIt(array, leftPtr, rightPtr, pivot)[0];
            partitionRight = partitionIt(array, leftPtr, rightPtr, pivot)[1];

            if (partitionRight != partitionLeft) {
                if (k + left <= partitionRight && k + left >= partitionLeft) {
                    return pivot;
                } else if (k + left < partitionLeft) {
                    rightPtr = partitionLeft - 1;
                } else if (k + left > partitionRight) {
                    leftPtr = partitionRight + 1;
                }
            }
            else {
                if (k + left <= partitionLeft) {
                    rightPtr = partitionLeft;
                }
                else {
                    leftPtr = partitionLeft + 1;
                }
            }
        }
        return array[leftPtr];
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

    private static void swap(int[] array, int leftPtr, int rightPtr) {
        int tmp = array[leftPtr];
        array[leftPtr] = array[rightPtr];
        array[rightPtr] = tmp;
    }


    public static void main(String... args) {
        FastReader reader = new FastReader();
        int n = reader.nextInt();
        int[] clonesIQ = new int[n];

        for (int i = 0; i < n; i++) {
            clonesIQ[i] = reader.nextInt();
            }

        int m = reader.nextInt();

        for (int idx = 0; idx < m; idx++) {
            int[] array = clonesIQ.clone();
            
            int i = reader.nextInt();
            int j = reader.nextInt();
            int k = reader.nextInt();

            System.out.println(kOrderStatistic(array, i - 1, j - 1, k - 1));
        }
    }
}
