import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class MoveToBegin {

    public static int MAX = 100000;
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
        int priority;
        int allSize;
        int value;
        Node left;
        Node right;

        public Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
            this.allSize = 1;
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

        public int getAllSize(Node node) {
            if (node == null) {
                return 0;
            }
            return node.allSize;
        }

        public void fixAllSize(Node node) {
            node.allSize = 1 + getAllSize(node.left) + getAllSize(node.right);
        }

        public Pair split(Node v, int x) {
            if (v == null) {
                return new Pair(null, null);
            }

            Pair curPair;
            int l = getAllSize(v.left);
            if (l >= x) {
                curPair = split(v.left, x);
                v.left = curPair.t2;
                fixAllSize(v);
                return new Pair(curPair.t1, v);
            }
            else {
                curPair = split(v.right, x - l - 1);
                v.right = curPair.t1;
                fixAllSize(v);
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
                fixAllSize(t1);
                return t1;
            }
            else {
                t2.left = merge(t1, t2.left);
                fixAllSize(t2);
                return t2;
            }
        }

        public Tree insert(Node node, int pos, int val) {
            int priority = getRandomNumberUsingNextInt();
            Node newNode = new Node(val, priority);
            if (node == null) {
                return new Tree(newNode);
            }

            Pair pair = split(node, pos);
            Node mergeNode = merge(pair.t1, newNode);
            Node lastMerge = merge(mergeNode, pair.t2);
            return new Tree(lastMerge);
        }


        public void inOrder(Node node) {
            if (node != null) {
                inOrder(node.left);
                System.out.print(node.value + " ");
                inOrder(node.right);
            }
        }

        public void fromArray(int[] arr) {
            root = null;
            for (int elem: arr) {
                int priority = getRandomNumberUsingNextInt();
                Node newElem = new Node(elem, priority);
                root = merge(root, newElem);
            }
        }

        public void move(int left, int right) {
            Pair firstPair = split(root, left - 1);
            Pair secondPair = split(firstPair.t2, right - left + 1);
            Node newNode = merge(firstPair.t1, secondPair.t2);
            root = merge(secondPair.t1, newNode);
        }
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Tree tree = new Tree();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i + 1;
        }

        tree.fromArray(array);

        ArrayList<String[]> commandList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String[] commandType = scanner.nextLine().split(" ");
            commandList.add(commandType);
        }

        int left, right;
        for (String[] el: commandList) {
            left = Integer.parseInt(el[0]);
            right = Integer.parseInt(el[1]);
            tree.move(left, right);
        }
        tree.inOrder(tree.root);
    }
}
