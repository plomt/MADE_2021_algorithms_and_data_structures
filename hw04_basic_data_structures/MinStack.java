import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MinStack {

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

        public void close() throws Exception {
            br.close();
        }
    }

    static class Node {
        public int data;
        public int minData;
        public Node next;
        public Node prev;

        public Node(int data) {
            this.data = data;
            this.minData = data;
            this.next = null;
            this.prev = null;
        }
    }

    static class LinkedList {
        public Node head;
        public Node tail;
        public int size;

        public LinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        public void add(int value) {
            Node tmp = new Node(value);

            if (size == 0) {
                head = tmp;
            }
            else {
                tail.next = tmp;
                tmp.prev = tail;
                if (tmp.data > tmp.prev.minData) {
                    tmp.minData = tmp.prev.minData;
                }
            }
            tail = tmp;
            tmp.next = null;
            size++;
        }

        public void removeLast() {
            if (size > 1) {
                tail.prev.next = null;
                tail = tail.prev;
            }
            else {
                head = null;
                tail = null;
            }
            size--;
        }

        public int getMin() {
            return tail.minData;
        }
    }

    static class Stack {
        public LinkedList linkedList;

        public Stack() {
            linkedList = new LinkedList();
        }

        public void push(int value) {
            linkedList.add(value);
        }

        public void pop() {
            linkedList.removeLast();
        }

        public int getMin() {
            return linkedList.getMin();
        }
    }

    public static void main(String... args) {
        FastScanner reader = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        Stack stack = new Stack();
        int n = reader.nextInt();
        int[] operations = new int[n];
        ArrayList<Integer> values = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int t = reader.nextInt();
            operations[i] = t;
            if (t == 1) {
                values.add(reader.nextInt());
            }
        }

        int i = 0;
        for (int oper: operations) {
            if (oper == 1) { // операция добавления
                stack.push(values.get(i));
                i++;
            }
            else if (oper == 2) { // операция удаления
                stack.pop();
            }
            else { // операция минимума
                out.println(stack.getMin());
            }
        }
        out.flush();
    }
}
