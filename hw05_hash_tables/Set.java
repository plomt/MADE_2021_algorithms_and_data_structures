import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Set {
    public static final int FREE_CELL = 1000000002;
    public static final int DELETED_CELL = 1000000001;
    public static final long A = 7;
    public static final int B = 1000000087;
    public static final int INITIAL_CAPACITY = 16;

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

    static class HashTable {
        int size;
        int sizeDeleted;
        int capacity;
        int[] array;

        public HashTable(int capacity) {
            this.size = 0;
            this.sizeDeleted = 0;
            this.capacity = capacity;
            this.array = new int[capacity];
            for (int i = 0; i < capacity; i++) {
                this.array[i] = FREE_CELL;
            }
        }

        public void insert(int value) {
            int idx = hashFunction(value);
            int i = 0;

            if (size + sizeDeleted < capacity / 2) {
                while (i < capacity) {
                    if (array[(idx + i) % capacity] == FREE_CELL){
                        array[(idx + i) % capacity] = value;
                        size++;
                        return;
                    }
                    else if (array[(idx + i) % capacity] == DELETED_CELL) {
                        if (exists(value, idx + i).equals("false")) {
                            sizeDeleted--;
                            size++;
                            array[(i + idx) % capacity] = value;
                        }
                        return;
                    }
                    else if (array[(idx + i) % capacity] == value) {
                        return;
                    }
                    i++;
                }
            }
            else {
                rehashingArray();
                insert(value);
            }
        }

        public void rehashingArray() {
            int tmpCapacity = capacity;
            capacity *= 2;
            HashTable tmp = new HashTable(capacity);
            for (int i = 0; i < tmpCapacity; i++) {
                if (!(array[i] == DELETED_CELL || array[i] == FREE_CELL)) {
                    int idx = hashFunction(array[i]);
                    int j = 0;
                    while (tmp.array[(idx + j) % capacity] != FREE_CELL) {
                        j++;
                    }
                    tmp.array[(j + idx) % capacity] = array[i];
                    tmp.size++;
                }
            }
            this.array = tmp.array;
            this.sizeDeleted = 0;
        }

        public String exists(int value, Integer idx) {
            if (idx == null) {
                idx = hashFunction(value);
            }
            int i = 0;
            while (i < capacity && array[(i + idx) % capacity] != FREE_CELL) {
                if (array[(i + idx) % capacity] == value) {
                    return "true";
                }
                i++;
            }
            return "false";
        }

        public void delete(int value) {
            int idx = hashFunction(value);
            int i = 0;
            while (i < capacity && array[(i + idx) % capacity] != value) {
                if (array[(i + idx) % capacity] == FREE_CELL) {
                    return;
                }
                i++;
            }
            if (array[(i + idx) % capacity] == value) {
                array[(i + idx) % capacity] = DELETED_CELL;
                sizeDeleted++;
                size--;
            }
        }

        public int hashFunction(int x) {
            return (int)(((A * Math.abs(x)) % B) % capacity);
        }
    }


    public static void main(String... args) throws IOException {
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

        HashTable table = new HashTable(INITIAL_CAPACITY);
        for (String[] el: commandList) {
            String command = el[0];
            int value = Integer.parseInt(el[1]);
            switch (command) {
                case "insert":
                    table.insert(value);
                    break;
                case "delete":
                    table.delete(value);
                    break;
                case "exists":
                    out.println(table.exists(value, null));
                    break;
                default:
                    break;
            }
        }
        out.flush();
    }
}
