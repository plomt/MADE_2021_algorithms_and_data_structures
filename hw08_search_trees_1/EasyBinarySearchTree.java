import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class EasyBinarySearchTree {
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

        public Node(int value) {
            this.value = value;
        }
    }

    static class Tree {
        public Node root;

        public Tree() {
            root = null;
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

        public void insert(int value) {
            Node ins = new Node(value);
            if (root == null) {
                root = ins;
            }
            else {
                Node cur = root;
                Node parent;
                while(true) {
                    parent = cur;
                    if (cur.value < value) {
                        cur = cur.right;
                        if (cur == null) {
                            cur = ins;
                            parent.right = cur;
                            return;
                        }
                    }
                    else if (cur.value > value) {
                        cur = cur.left;
                        if (cur == null) {
                            cur = ins;
                            parent.left = cur;
                            return;
                        }
                    }
                    else {
                        return;
                    }
                }
            }
        }

        public void delete(int value) {
            Node cur = root;

            if (cur == null) {
                return;
            }

            Node parent = root;
            boolean isLeftChild = true;

            while(cur.value != value) {
                parent = cur;
                if (value < cur.value) {
                    isLeftChild = true;
                    cur = cur.left;
                }
                else {
                    isLeftChild = false;
                    cur = cur.right;
                }
                if (cur == null) {
                    return;
                }
            }
            if (cur.left == null && cur.right == null) {
                if (cur == root) {
                    root = null;
                }
                else if (isLeftChild) {
                    parent.left = null;
                }
                else {
                    parent.right = null;
                }
            }
            else if (cur.right == null) {
                if (cur == root) {
                    root = cur.left;
                }
                else if (isLeftChild) {
                    parent.left = cur.left;
                }
                else {
                    parent.right = cur.left;
                }
            }
            else if (cur.left == null) {
                if (cur == root) {
                    root = cur.right;
                }
                else if (isLeftChild) {
                    parent.left = cur.right;
                }
                else {
                    parent.right = cur.right;
                }
            }
            else {
                Node suc = getSuccessor(cur);
                if (cur == root) {
                    root = suc;
                }
                else if (isLeftChild) {
                    parent.left = suc;
                }
                else {
                    parent.right = suc;
                }
                suc.left = cur.left;
            }
        }

        public Node getSuccessor(Node delNode) {
            Node sucParent = delNode;
            Node suc = delNode;
            Node cur = delNode.right;
            while (cur != null) {
                sucParent = suc;
                suc = cur;
                cur = cur.left;
            }

            if (suc != delNode.right) {
                sucParent.left = suc.right;
                suc.right = delNode.right;
            }
            return suc;
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
                    tree.insert(value);
                    break;
                case "delete":
                    tree.delete(value);
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
