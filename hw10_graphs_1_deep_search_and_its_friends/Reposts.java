import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
 
public class Reposts {
    public static final String START_NAME = "polycarp";
    public static final int DEFAULT_VALUE = 0;
    public static final int CNT_START_VALUE = 1;
 
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
 
    public static void dfs(String v, HashMap<String, Integer> used, int cnt, HashMap<String, ArrayList<String>> adjList) {
        used.put(v, cnt);
        if (adjList.get(v) != null) {
            for (String u : adjList.get(v)) {
                if (used.get(u) == DEFAULT_VALUE) {
                    cnt += 1;
                    dfs(u, used, cnt, adjList);
                }
                cnt -= 1;
            }
        }
    }
 
    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = scanner.nextInt();
        HashMap<String, Integer> used = new HashMap<>();
 
        HashMap<String, ArrayList<String>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String[] str = scanner.nextLine().toLowerCase().split(" ");
            String name1 = str[0];
            String name2 = str[2];
 
            if (!adjList.containsKey(name2)) {
                adjList.put(name2, new ArrayList<>());
            }
            adjList.get(name2).add(name1);
            used.put(name1, DEFAULT_VALUE);
            used.put(name2, DEFAULT_VALUE);
        }
 
        dfs(START_NAME, used, CNT_START_VALUE, adjList);
        int max = Integer.MIN_VALUE;
        for (Integer val : used.values()) {
            if (val > max) {
                max = val;
            }
        }
        
        out.println(max);
        out.flush();
    }
}
