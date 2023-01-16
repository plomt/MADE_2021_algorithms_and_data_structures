import java.util.Scanner;

public class Levenshtein {

    public static void levenshteinDistance(String first, String second) {
        int n = first.length();
        int m = second.length();
        int[][] dp = new int[n + 1][m + 1];

        dp[0][0] = 0; //base

        for (int j = 1; j <= m; j++)
            dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
            for (int j = 1; j <= m; j++) {
                if (i > 0) {
                    if (first.charAt(i - 1) == second.charAt(j - 1))
                        dp[i][j] = dp[i - 1][j - 1];
                    else
                        dp[i][j] = Math.min(Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1), dp[i - 1][j - 1] + 1);
                }
            }
        }
        System.out.println(dp[n][m]);
    }


    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        String first = scanner.nextLine();
        String second = scanner.nextLine();

        levenshteinDistance(first, second);
    }
}
