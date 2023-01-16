import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
 
public class Condensation {
    public static final int WHITE = 0;
    public static final int GREY = 1;
    public static final int BLACK = 2;
    public static final int DEFAULT_VALUE = 0;
 
    static class Pair implements Comparable<Pair>{
        int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
 
        @Override
        public int compareTo(Pair a)
        {
            if (x == a.x) return y - a.y;
            return x - a.x;
        }
    }
 
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
 
    public static void dfsTopSort(int v, int[] used, HashMap<Integer, ArrayList<Integer>> adjList, ArrayList<Integer> ans) {
        used[v] = GREY;
        if (adjList.get(v) != null) {
            for (Integer u : adjList.get(v)) {
                if (used[u] == WHITE) {
                    dfsTopSort(u, used, adjList, ans);
                }
            }
        }
        ans.add(v);
        used[v] = BLACK;
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
        int[] usedTopSort = new int[n];
 
        int m = scanner.nextInt();
        HashMap<Integer, ArrayList<Integer>> adjList = new HashMap<>(n);
        HashMap<Integer, ArrayList<Integer>> adjListInv = new HashMap<>(n);
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1;
            int second = scanner.nextInt() - 1;
 
            if (!adjList.containsKey(first)) {
                adjList.put(first, new ArrayList<>());
            }
            adjList.get(first).add(second);
 
            if (!adjListInv.containsKey(second)) { // строим обратный граф G' к G = <V, E>
                adjListInv.put(second, new ArrayList<>());
            }
            adjListInv.get(second).add(first);
        }
 
        ArrayList<Integer> topolSortVert = new ArrayList<>(); // топологическая сортировка
        for (int i = 0; i < n; i++) {
            if (usedTopSort[i] == WHITE) {
                dfsTopSort(i, usedTopSort, adjList, topolSortVert);
            }
        }
        Collections.reverse(topolSortVert);
 
        int[] used = new int[n];
        int cnt = DEFAULT_VALUE;
        for (Integer val: topolSortVert) {
            if (used[val] == DEFAULT_VALUE) {
                cnt += 1;
                dfs(val, used, cnt, adjListInv);
            }
        }
 
        TreeSet<Pair> s = new TreeSet<>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : adjList.entrySet()) {
            int key = entry.getKey();
            for (Integer val: entry.getValue()) {
                if (used[key] != used[val]) {
                    s.add(new Pair(used[key], used[val]));
                }
            }
        }
        
        out.println(s.size());
        out.flush();
    }
}
