import java.util.ArrayList;
import java.util.Scanner;

public class CountSort {
    public static final int MAX_VALUE = 101;
    
    public static void countSort(ArrayList<Integer> array) {
        int[] counterArray = new int[MAX_VALUE];

        for (int elem: array) {
            counterArray[elem] += 1;
        }

        for (int i = 0; i < MAX_VALUE; i++) {
            while (counterArray[i] > 0) {
                System.out.print(i + " ");
                counterArray[i]--;
            }
        }
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> values = new ArrayList<>();

        while (scanner.hasNext()) {
            values.add(scanner.nextInt());
        }

        countSort(values);
    }
}
