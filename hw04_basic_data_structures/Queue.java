import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Queue {

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

    static class DynamicArray {
        private int size;
        private int capacity;
        private int[] array;

        public DynamicArray(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.array = new int[capacity];
        }

        public void add(int value, int head, int tail) {
            if (size + 1 > capacity) {
                capacity *= 2;
                int[] newArray = new int[capacity];
                for (int i = head; i <= tail + size; i++) {
                    newArray[i - head] = array[i % size];
                }
                array = newArray;
                array[size % capacity] = value;
            }
            else {
                array[(tail + 1) % capacity] = value;
            }
            size++;
        }

        public Integer remove(int idx) {
            if (idx < 0 && idx > size - 1) {
                return null;
            }
            int tmp = array[idx];
            array[idx] = 0;
            size--;
            return tmp;
        }

    }

    static class CyclicQueue {
        DynamicArray array;
        int head;
        int tail;

        public CyclicQueue(int capacity) {
            this.array = new DynamicArray(capacity);
            head = -1;
            tail = -1;
        }

        public int next(int idx) {
            return (idx + 1) % array.capacity;
        }

        public int size() {
            return (tail + array.capacity - head) % array.capacity + 1;
        }

        public void add(int value) {
            boolean isSpinner = false; // придется разворачивать массив при добавлении нового или нет
            int idx = array.capacity;
            if (array.size == array.capacity) {
                isSpinner = true;
            }

            array.add(value, head, tail);
            if (head == -1) {
                head = next(head);
            }
            tail = isSpinner ? idx : next(tail);
            head = isSpinner ? 0 : head;
        }

        public int pop() {
            int removed = array.remove(head);
            head = next(head);
            return removed;
        }
    }

    public static void main(String... args) {
        FastScanner scanner = new FastScanner(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        CyclicQueue queue = new CyclicQueue(5);

        int m = scanner.nextInt();
        String[] operations = new String[m];
        ArrayList<Integer> values = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            String ch = scanner.next();
            operations[i] = ch;
            if (ch.equals("+")) {
                values.add(scanner.nextInt());
            }
        }

        int idx = 0;
        for (String ch: operations) {
            switch (ch) {
                case "+":
                    queue.add(values.get(idx));
                    idx++;
                    break;
                case "-":
                    out.println(queue.pop());
                    break;
                default:
                    break;
            }
        }
        out.flush();
    }
}
