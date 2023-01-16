import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
 
public class TopologicalSorting {
    public static final int WHITE = 0;
    public static final int GREY = 1;
    public static final int BLACK = 2;
    public static final int CYCLE = -1;
 
    static class FastScanner {
        private BufferedReader br;
        private StringTokenizer st;
 
        public FastScanner(InputStreamReader reader) {
            br = new BufferedReader(reader);
        }
 
        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
        public double nextDouble() {
            return Double.parseDouble(next());
        }
 
        public String nextLine(){
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
 
    public static int dfs(int v, int[] used, HashMap<Integer, ArrayList<Integer>> adjList, ArrayList<Integer> ans) {
        used[v] = GREY;
        if (adjList.get(v) != null) {
            for (Integer u : adjList.get(v)) {
                if (used[u] == WHITE) {
                    dfs(u, used, adjList, ans);
                }
                if (used[u] == GREY) {
                    return -1;
                }
            }
        }
        ans.add(v + 1);
        used[v] = BLACK;
        return 0;
    }
 
    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = scanner.nextInt();
        int[] used = new int[n];
 
        int m = scanner.nextInt();
        HashMap<Integer, ArrayList<Integer>> adjList = new HashMap<>(n);
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1;
            int second = scanner.nextInt() - 1;
 
            if (!adjList.containsKey(first)) {
                adjList.put(first, new ArrayList<>());
            }
            adjList.get(first).add(second);
        }
 
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (used[i] == WHITE) {
                int value = dfs(i, used, adjList, ans);
                if (value == CYCLE) {
                    out.println(value);
                    out.flush();
                    return;
                }
            }
        }
        Collections.reverse(ans);
        
        for (Integer val: ans) {
            out.print(val + " ");
        }
        out.flush();
    }
}
