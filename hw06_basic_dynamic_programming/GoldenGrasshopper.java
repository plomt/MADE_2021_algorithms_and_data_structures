import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GoldenGrasshopper {


    public static void jumping(int n, int k, int[] gold) {
        ArrayList<Integer> cntGold = new ArrayList<>();
        ArrayList<Integer> way = new ArrayList<>();

        cntGold.add(gold[0]); //base

        for (int i = 1; i < n; i++) {
            int maxIdx = Integer.MIN_VALUE;
            int maxGold = Integer.MIN_VALUE;
            for (int j = k; j > 0; j--) {
                if (i - j >= 0) {
                    if (maxGold < cntGold.get(i - j)) {
                        maxGold = cntGold.get(i - j);
                        maxIdx = i - j + 1;
                        }
                    }
                }
            cntGold.add(maxGold + gold[i]);
            way.add(maxIdx);
        }

        ArrayList<Integer> result = new ArrayList<>();
        result.add(n);
        int i = way.size() - 1;
        while (i >= 0) {
            int val = way.get(i);
            result.add(val);
            i = val - 2;
        }

        System.out.println(cntGold.get(n - 1));
        System.out.println(result.size() - 1);
        Collections.reverse(result);
        for (Integer elem: result) {
            System.out.print(elem + " ");
        }
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int[] gold = new int[n];
        gold[0] = 0;
        for (int i = 0; i < n - 2; i++) {
                gold[i + 1] = scanner.nextInt();
        }
        gold[n - 1] = 0;

        jumping(n, k, gold);
    }
}
