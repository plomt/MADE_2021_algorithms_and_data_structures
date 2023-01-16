import java.util.Scanner;

public class MergeSort {
    public static long countInv = 0;

    public static void mergeSort(int[] array, int left, int right) {
        if (left + 1 >= right)
            return;
        else {
            int middle = left + (right - left) / 2;
            mergeSort(array, left, middle);
            mergeSort(array, middle, right);
            merge(array, left, middle, right);
        }
    }

    public static void merge(int[] array, int left, int middle, int right) {
        int i = left;
        int j = middle;
        int k = 0;
        int[] result = new int[right - left];

        while (i < middle && j < right) {
            if (array[i] <= array[j]) {
                result[k] = array[i];
                i++;
            }
            else {
                result[k] = array[j];
                j++;
                countInv += middle - left - (i - left);
            }
            k++;
        }
        for (;i < middle; i++, k++) {
            result[k] = array[i];
        }
        for (;j < right; j++, k++) {
            result[k] = array[j];
        }

        for (int idx = 0; idx < right - left; idx++) {
            array[left + idx] = result[idx];
        }
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
        }

        mergeSort(values, 0, n);

        System.out.print(countInv);
    }
}
