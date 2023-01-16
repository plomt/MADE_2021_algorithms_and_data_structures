import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
 
public class ConnectivityComponents {
    public static final int DEFAULT_VALUE = 0;
 
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
 
    public static void dfs(int v, int[] used, int cnt, HashMap<Integer, ArrayList<Integer>> adjList) {
        used[v] = cnt;
        if (adjList.get(v) != null) {
            for (Integer u : adjList.get(v)) {
                if (used[u] == DEFAULT_VALUE) {
                    dfs(u, used, cnt, adjList);
                }
            }
        }
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
 
            if (!adjList.containsKey(second)) {
                adjList.put(second, new ArrayList<>());
            }
            adjList.get(second).add(first);
        }
 
        int cnt = 0;
        for (int i = 0; i < used.length; i++) {
            if (used[i] == DEFAULT_VALUE) {
                cnt += 1;
                dfs(i, used, cnt, adjList);
            }
        }
        
        out.println(cnt);
        for (Integer val: used) {
            out.print(val + " ");
        }
        out.flush();
    }
}
