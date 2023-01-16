    import java.util.Scanner;
     
    public class Simple {
        public static void swap(int[] array, int idx1, int idx2) {
            int tmp = array[idx1];
            array[idx1] = array[idx2];
            array[idx2] = tmp;
        }
     
        public static void insertionSort(int[] array, int size) {
            for (int i = 1; i < size; i++) {
                int j = i - 1;
                while (j >= 0 && array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    j--;
                }
            }
        }
     
        public static void main(String... args) {
            Scanner scanner = new Scanner(System.in);
     
            int n = scanner.nextInt();
            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = scanner.nextInt();
            }
     
            insertionSort(values, n);
     
            for (int i = 0; i < n; i++) {
                System.out.print(values[i] + " ");
            }
        }
    }

