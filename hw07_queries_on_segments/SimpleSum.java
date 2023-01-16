import java.util.Scanner;

public class SimpleSum {
    public final static long TWO_SIXTEEN = 65536;
    public final static long THIRTY = 1073741824;

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int a0 = scanner.nextInt();
        int m = scanner.nextInt();

        if (m == 0) {
            System.out.println(0);
            return;
        }

        int z = scanner.nextInt();
        int t = scanner.nextInt();
        int b0 = scanner.nextInt();

        long[] sumA = new long[n];
        int prevA = a0;
        sumA[0] = a0;
        for (int i = 1; i < n; i++) {
            int value =  (int)((x * prevA + y) % TWO_SIXTEEN);
            prevA = value;
            sumA[i] = sumA[i - 1] + value;
        }

        long[] b = new long[2 * m];
        int[] c = new int[2 * m];
        b[0] = b0;
        c[0] = b0 % n;
        for (int i = 1; i < 2 * m; i++) {
            long value = ((z * b[i - 1] + t) % THIRTY + THIRTY) % THIRTY;
            b[i] = value;
            c[i] = (int)(value % n);
        }

        long ansSum = 0;
        int minVal;
        int maxVal;
        for (int i = 0; i < m; i++){
            if (c[2 * i] < c[2 * i + 1]) {
                minVal = c[2 * i];
                maxVal = c[2 * i + 1];
            }
            else {
                minVal = c[2 * i + 1];
                maxVal = c[2 * i];
            }
            ansSum += minVal == 0 ? sumA[maxVal] : (sumA[maxVal] - sumA[minVal -1]);
        }
        System.out.println(ansSum);
    }
}
