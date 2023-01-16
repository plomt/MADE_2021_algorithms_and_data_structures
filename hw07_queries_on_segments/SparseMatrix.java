import java.util.Scanner;

public class SparseMatrix {


    public static int[] arrK(int left, int right) {
        int len = right - left + 1;
        int[] k = new int[len];
        k[1] = 0;
        for (int i = 2; i < len; i++) {
            k[i] = k[i - 1];
            if ((1 << k[i]) * 2 <= i) {
                k[i]++;
            }
        }
        return k;
    }

    public static int[][] sparseMatrix(int n, int[] arrValues, int pow) {
        int[][] st = new int[n][pow + 1];
        for (int k = 0; k < pow + 1; k++) {
            int diff = k > 0 ? (1 << (k - 1)) : 0;
            for (int i = 0; i < n - diff; i++) {
                if (k == 0) {
                    st[i][k] = arrValues[i];
                }
                else {
                    st[i][k] = Math.min(st[i][k - 1], st[i + (1 << (k - 1))][k - 1]);
                }
            }
        }
        return st;
    }

    public static int rmq(int[][] matrix, int left, int right, int[] arrK) {
        int j = arrK[right - left + 1];
        return Math.min(matrix[left][j], matrix[right - (1 << j) + 1][j]);
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // количество элементов в массиве
        int m = scanner.nextInt(); // количество запросов
        int a1 = scanner.nextInt(); // первый элемент массива

        int[] arrValues = new int[n];
        arrValues[0] = a1;
        int prev = a1;
        for (int i = 1; i < n; i++) {
            int value = (23 * prev + 21563) % 16714589;
            arrValues[i] = value;
            prev = value;
        }

        int[] k = arrK(0, n);

        int[][] matrix = sparseMatrix(n, arrValues, k[n]);

        int u1 = scanner.nextInt();
        int v1 = scanner.nextInt(); // первый запрос

        int prevU = u1;
        int prevV = v1;

        int prevR = prevU <= prevV ? rmq(matrix, prevU - 1, prevV - 1, k) : rmq(matrix, prevV - 1, prevU - 1, k);
        for (int i = 1; i < m; i++) {
            int newU = ((17 * prevU + 751 + prevR + 2 * i) % n) + 1;
            int newV = ((13 * prevV + 593 + prevR + 5 * i) % n) + 1;

            int r = newU <= newV ? rmq(matrix, newU - 1, newV - 1, k) : rmq(matrix, newV - 1, newU - 1, k);

            prevR = r;
            prevV = newV;
            prevU = newU;
        }

        System.out.println(prevU + " " + prevV + " " + prevR);
    }
}
