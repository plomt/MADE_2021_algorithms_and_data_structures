import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Postfix {
    public static final int START_CAPACITY = 5;

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }

        long nextLong() { return Long.parseLong(next()); }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    static class DynamicArray {
        private int size;
        private int capacity;
        private int[] array;

        public DynamicArray(int capacity) {
            this.capacity = capacity;
            this.array = new int[capacity];
            this.size = 0;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Integer get(int index) {
            if (index < 0 || index >= size) {
                return null;
            }
            return array[index];
        }


        public void add(int value) {
            if (size + 1 > capacity) {
                capacity *= 2;
                int[] newArray = new int[capacity];
                System.arraycopy(array, 0, newArray, 0, size);
                array = newArray;
            }
            array[size] = value;
            size++;
        }

        public void removeLast() {
            if (size - 1 < capacity / 4) {
                capacity /= 2;
                int[] newArray = new int[capacity];
                System.arraycopy(array, 0, newArray, 0, size);
                array = newArray;
            }
            array[size - 1] = 0;
            size--;
        }
    }

    static class Stack {
       DynamicArray array;
       int capacity;
       int top;

       public Stack(int capacity) {
           this.capacity = capacity;
           this.array = new DynamicArray(capacity);
           this.top = -1;
       }

       public void push(int value) {
           array.add(value);
           top++;
       }

       public int pop() {
           int popped = array.get(top);
           array.removeLast();
           top--;
           return popped;
       }
    }

    public static void main(String... args) {
        FastReader reader = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        Stack stack = new Stack(START_CAPACITY);
        int num1, num2, interAns;

        String str = reader.nextLine();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (ch >= '0' && ch <= '9') {
                stack.push((int)(ch - '0'));
            }
            else if (ch != ' '){
                num1 = stack.pop();
                num2 = stack.pop();
                switch (ch) {
                    case '+':
                        interAns = num2 + num1;
                        break;
                    case '-':
                        interAns = num2 - num1;
                        break;
                    case '*':
                        interAns = num2 * num1;
                        break;
                    case '/':
                        interAns = num2 / num1;
                        break;
                    default:
                        interAns = 0;
                }
                stack.push(interAns);
            }
        }
        interAns = stack.pop();
        out.println(interAns);
        out.flush();
    }
}
