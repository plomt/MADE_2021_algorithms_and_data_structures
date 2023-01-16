import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class IncreasSubSeq {

    public static void incrSubSeq(int[] seq, int n) {
        int[] lastSeq = new int[n];
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            lastSeq[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; j++) {
                if (seq[j] < seq[i] && lastSeq[j] + 1 > lastSeq[i]) {
                    lastSeq[i] = lastSeq[j] + 1;
                    prev[i] = j;
                }
            }
        }

        int maxSubLength = 0;
        int starterPos = -1;
        for (int i = 0; i < n; i++) {
            if (maxSubLength < lastSeq[i]) {
                maxSubLength = lastSeq[i];
                starterPos = i;
            }
        }

        ArrayList<Integer> ansSeq = new ArrayList<>();
        int i = starterPos;
        while (i > -1) {
            ansSeq.add(seq[i]);
            i = prev[i];
        }

        System.out.println(maxSubLength);
        Collections.reverse(ansSeq);
        for (Integer val: ansSeq) {
            System.out.print(val + " ");
        }
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] seq = new int[n];

        for (int i = 0; i < n; i++) {
            seq[i] = scanner.nextInt();
        }

        incrSubSeq(seq, n);
    }
}
