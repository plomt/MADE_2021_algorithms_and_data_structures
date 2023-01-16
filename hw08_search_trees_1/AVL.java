import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class AVL {
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

    static class Node {
        public int value;
        Node left;
        Node right;
        int height;

        public Node(int value) {
            this.value = value;
            this.height = 1;
        }
    }

    static class Tree {
        public Node root;

        public Tree() {
            root = null;
        }

        public int getHeight(Node v) {
            if (v == null) {
                return 0;
            }
            return v.height;
        }

        public int getBalance(Node v) {
            if (v == null) {
                return 0;
            }
            return getHeight(v.right) - getHeight(v.left);
        }

        public void fixHeight(Node v) {
            if (v == null) {
                return;
            }
            int heightLeft = getHeight(v.left);
            int heightRight = getHeight(v.right);
            v.height = (Math.max(heightLeft, heightRight)) + 1;
        }

        public Node rotateRight(Node p) {
            Node q = p.left;
            p.left = q.right;
            q.right = p;
            fixHeight(p);
            fixHeight(q);
            return q;
        }

        public Node rotateLeft(Node q) {
            Node p = q.right;
            q.right = p.left;
            p.left = q;
            fixHeight(q);
            fixHeight(p);
            return p;
        }

        public Node balance(Node p) {
            fixHeight(p);
            int balance = getBalance(p);
            if (balance > 1) {
                if (getHeight(p.right.right) <= getHeight(p.right.left)) {
                    p.right = rotateRight(p.right);
                }
                p = rotateLeft(p);
            }
            else if (balance < -1) {
                if (getHeight(p.left.left) <= getHeight(p.left.right)) {
                    p.left = rotateLeft(p.left);
                }
                p = rotateRight(p);
            }
            return p;
        }

        public Node exists(int value) {
            Node cur = root;
            if (cur == null) {
                return null;
            }

            while(cur.value != value) {
                if (cur.value < value) {
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

        public Node insert(Node node, int value) {
            if (node == null) {
                return new Node(value);
            }
            else if (node.value > value) {
                node.left = insert(node.left, value);
            }
            else if (node.value < value) {
                node.right = insert(node.right, value);
            }
            return balance(node);
        }

        public Node delete(Node node, int value) {
            if (node == null) {
                return null;
            } else if (node.value > value) {
                node.left = delete(node.left, value);
            } else if (node.value < value) {
                node.right = delete(node.right, value);
            } else {
                if (node.left == null || node.right == null) {
                    node = (node.left == null) ? node.right : node.left;
                }
                else {
                    Node suc = getMostLeft(node.right);
                    node.value = suc.value;
                    node.right = delete(node.right, node.value);
                }
            }
            if (node != null) {
                node = balance(node);
            }
            return node;
        }

        public Node getMostLeft(Node node) {
            Node cur = node;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        }

        public Node prev(int value) {
            Node v = root;
            if (v == null) {
                return null;
            }
            Node res = null;

            while (v != null) {
                if (value > v.value) {
                    res = v;
                    v = v.right;

                }
                else {
                    v = v.left;
                }
            }
            return res;
        }

        public Node next(int value) {
            Node cur = root;
            if (cur == null) {
                return null;
            }
            Node suc = null;

            while (cur != null) {
                if (value < cur.value) {
                    suc = cur;
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return suc;
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
                    tree.root = tree.insert(tree.root, value);
                    break;
                case "delete":
                    tree.root = tree.delete(tree.root, value);
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
