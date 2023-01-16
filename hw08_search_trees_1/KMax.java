import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class KMax {
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
        int rightCnt;
        int leftCnt;

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

        public int getRightCnt(Node v) {
            if (v == null) {
                return 0;
            }
            return v.rightCnt;
        }

        public int getLeftCnt(Node v) {
            if (v == null) {
                return 0;
            }
            return v.leftCnt;
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

            p.leftCnt = q.rightCnt;
            q.rightCnt = 1 + p.leftCnt + p.rightCnt;

            fixHeight(p);
            fixHeight(q);
            return q;
        }

        public Node rotateLeft(Node q) {
            Node p = q.right;
            q.right = p.left;
            p.left = q;

            q.rightCnt = p.leftCnt;
            p.leftCnt = 1 + q.leftCnt + q.rightCnt;

            fixHeight(q);
            fixHeight(p);
            return p;
        }

        public Node balance(Node p) {
            fixHeight(p);
            int balance = getBalance(p);
            if (balance > 1) {
                if (getHeight(p.right.right) > getHeight(p.right.left)) {
                    p = rotateLeft(p);
                }
                else {
                    p.right = rotateRight(p.right);
                    p = rotateLeft(p);
                }
            }
            else if (balance < -1) {
                if (getHeight(p.left.left) > getHeight(p.left.right)) {
                    p = rotateRight(p);
                }
                else {
                    p.left = rotateLeft(p.left);
                    p = rotateRight(p);
                }
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
                node.leftCnt += 1;
                node.left = insert(node.left, value);
            }
            else if (node.value < value) {
                node.rightCnt += 1;
                node.right = insert(node.right, value);
            }
            return balance(node);
        }

        public Node delete(Node node, int value) {
            if (node == null) {
                return null;
            } else if (node.value > value) {
                node.leftCnt -= 1;
                node.left = delete(node.left, value);
            } else if (node.value < value) {
                node.rightCnt -= 1;
                node.right = delete(node.right, value);
            } else {
                if (node.left == null || node.right == null) {
                    node = (node.left == null) ? node.right : node.left;
                }
                else {
                    Node suc = getMostLeft(node.right);
                    node.value = suc.value;
                    node.rightCnt -= 1;
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

        public Node kThLargest(Node node, int k) {
            if (node == null)
                return null;

            if (node.rightCnt == k - 1) {
                return node;
            } else if (node.rightCnt >= k) {
                return kThLargest(node.right, k);
            } else {
                k -= (node.rightCnt + 1);
                return kThLargest(node.left, k);
            }
        }
    }



    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<String[]> commandList = new ArrayList<>();
        int n = scanner.nextInt();
        
        for (int i = 0; i < n; i++) {
            String[] commandType = scanner.nextLine().split(" ");
            commandList.add(commandType);
        }

        Tree tree = new Tree();
        int value;
        for (String[] el: commandList) {
            String command = el[0];
            value = Integer.parseInt(el[1]);
            if (command.equals("+1") || command.equals("1"))
                tree.root = tree.insert(tree.root, value);
            else if (command.equals("-1"))
                tree.root = tree.delete(tree.root, value);
            else
                out.println(tree.kThLargest(tree.root, value).value);
        }
        out.flush();
    }
}
