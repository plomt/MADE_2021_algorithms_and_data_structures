import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

public class BinarySearchTree {

    public static int MAX = Integer.MAX_VALUE;
    public static int MIN = 0;

    public static int getRandomNumberUsingNextInt() {
        Random random = new Random();
        return random.nextInt(MAX - MIN) + MAX;
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

    public static class Pair {
        public Node t1;
        public Node t2;

        public Pair(Node t1, Node t2) {
            this.t1 = t1;
            this.t2 = t2;
        }
    }

    public static class Node {
        int value;
        int priority;
        Node left;
        Node right;

        public Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    public static class Tree {
        public Node root;

        public Tree() {
            this.root = null;
        }

        public Tree(Node node) {
            this.root = node;
        }

        public Pair split(Node v, int x) {
            if (v == null) {
                return new Pair(null, null);
            }

            Pair curPair;
            if (v.value > x) {
                curPair = split(v.left, x);
                v.left = curPair.t2;
                return new Pair(curPair.t1, v);
            }
            else {
                curPair = split(v.right, x);
                v.right = curPair.t1;
                return new Pair(v, curPair.t2);
            }
        }

        public Node merge(Node t1, Node t2) {
            if (t1 == null) {
                return t2;
            }
            if (t2 == null) {
                return t1;
            }

            if (t1.priority > t2.priority) {
                t1.right = merge(t1.right, t2);
                return t1;
            }
            else {
                t2.left = merge(t1, t2.left);
                return t2;
            }
        }

        public Tree insert(Node node, int x) {
            int priority = getRandomNumberUsingNextInt();
            Node newNode = new Node(x, priority);
            if (node == null) {
                return new Tree(newNode);
            }
            Pair pair = split(node, x);
            Node mergeNode = merge(pair.t1, newNode);
            Node lastMerge = merge(mergeNode, pair.t2);
            return new Tree(lastMerge);
        }

        public Tree delete(Node node, int x) {
            Pair firstPair = split(node, x);
            Pair secondPair = split(firstPair.t1, x - 1);
            Node deleteMerge = merge(secondPair.t1, firstPair.t2);
            return new Tree(deleteMerge);
        }

        public Node exists(int x) {
            Node cur = root;
            if (cur == null) {
                return null;
            }

            while(cur.value != x) {
                if (cur.value < x) {
                    cur = cur.right;
                }
                else {
                    cur = cur.left;
                }
                if (cur == null) {
                    return null;
                }
            }
            return cur;
        }

        public Node next(int x) {
            if (root == null) {
                return null;
            }

            Node cur = root;
            Node ans = null;
            while (cur != null) {
                if (x < cur.value) {
                    ans = cur;
                    cur = cur.left;
                }
                else {
                    cur = cur.right;
                }
            }
            return ans;
        }

        public Node prev(int x) {
            if (root == null) {
                return null;
            }

            Node cur = root;
            Node ans = null;
            while (cur != null) {
                if (x > cur.value) {
                    ans = cur;
                    cur = cur.right;
                }
                else {
                    cur = cur.left;
                }
            }
            return ans;
        }
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<String[]> commandList = new ArrayList<>();

        String[] commandType;
        commandType = scanner.nextLine().split(" ");
        while (commandType[0].length() > 0) {
            try {
                commandList.add(commandType);
                commandType = scanner.nextLine().split(" ");
            }
            catch (NoSuchElementException | NullPointerException e) {
                break;
            }
        }

        Tree tree = new Tree();
        int value;
        for (String[] el: commandList) {
            String command = el[0];
            value = Integer.parseInt(el[1]);
            switch (command) {
                case "insert":
                    tree = tree.insert(tree.root, value);
                    break;
                case "delete":
                    tree = tree.delete(tree.root, value);
                    break;
                case "exists":
                    out.println(tree.exists(value) == null ? "false" : "true");
                    break;
                case "next":
                    out.println(tree.next(value) == null ? "none" : tree.next(value).value);
                    break;
                case "prev":
                    out.println(tree.prev(value) == null ? "none" : tree.prev(value).value);
                    break;
                default:
                    break;
            }
        }
        out.flush();
    }
}
