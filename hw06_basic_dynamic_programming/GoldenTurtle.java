import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GoldenTurtle {

    public static final int CONST = -10_000;

    static class Indexes {
        public int x;
        public int y;
        public String direction;

        public Indexes(int x, int y, String direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    public static void goldenWay(int[][] matrix, int n, int m) {
        int[][] cash = new int[n][m];
        Indexes[][] way = new Indexes[n][m];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (row == 0 || col == 0) {
                    cash[row][col] = CONST;
                }
            }
        }

        cash[1][1] = matrix[1][1];


        for (int row = 1; row < n; row++) {
            for (int col = 1; col < m; col++) {
                if (row > 1 || col > 1) {
                    if (cash[row][col - 1] <= cash[row - 1][col]) {
                        cash[row][col] += matrix[row][col] + cash[row - 1][col];
                        way[row][col] = new Indexes(row - 1, col, "D");
                    } else {
                        cash[row][col] += matrix[row][col] + cash[row][col - 1];
                        way[row][col] = new Indexes(row, col - 1, "R");
                    }
                }
            }
        }

        int i = n - 1;
        int j = m - 1;
        ArrayList<String> stringWay = new ArrayList<>();
        while (i != 1 || j != 1) {
            stringWay.add(way[i][j].direction);
            int ni = way[i][j].x;
            j = way[i][j].y;
            i = ni;
        }
        Collections.reverse(stringWay);

        System.out.println(cash[n - 1][m - 1]);
        for (String s : stringWay) {
            System.out.print(s);
        }
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] matrix = new int[n + 1][m + 1];
        for (int row = 0; row < n + 1; row++) {
            for (int col = 0; col < m + 1; col++) {
                if (row == 0 || col == 0) {
                    matrix[row][col] = CONST;
                }
                else {
                    matrix[row][col] = scanner.nextInt();
                }
            }
        }

        goldenWay(matrix, n + 1, m + 1);
    }
}
