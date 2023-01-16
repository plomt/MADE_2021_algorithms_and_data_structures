import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ChainsMap {
    static final int INITIAL_CAPACITY = 16;
    static final float LOAD_FACTOR = 0.75f;
    static final int P = 1000000087;
    static final int A = 31;

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
        public String key;
        public String value;
        public Node next;
        public Node prev;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    static class LinkedList {
        public int size;
        public Node head;
        public Node tail;

        public void add(Node tmp) { // добавляю в конец связанного списка
            if (size == 0) {
                head = tmp;
            } else {
                tail.next = tmp;
                tmp.prev = tail;
            }
            tail = tmp;
            size++;
        }

        public boolean isDrop(String key) {
            boolean isDeleted = false;

            if (size == 0) { // проверка на пустоту
                return false;
            }
            else {
                if (head.key.equals(key)) { // удаление head за O(1)
                    if (size > 1) {
                        head = head.next;
                        head.prev = null;

                        isDeleted = true;
                    } else { // состоит ровно из одного элемента. head = tail = key
                        head = null;
                        tail = null;

                        isDeleted = true;
                    }
                } else if (tail.key.equals(key)) { // удаление tail за O(1)
                    tail = tail.prev;
                    tail.next = null;

                    isDeleted = true;
                } else { // если удаляемый не head и не tail
                    Node curr = head;

                    while (curr != tail) { // ищем нужную ноду
                        if (curr.key.equals(key)) {
                            curr.prev.next = curr.next;
                            curr.next.prev = curr.prev;

                            isDeleted = true;
                            break;
                        }
                        curr = curr.next;
                    }
                }
            }
            size = isDeleted ? size - 1 : size;
            return isDeleted;
        }

        public String exists(String key) {
            if (size == 0) { // проверка на пустоту
                return "none";
            }
            else {
                Node curr = head;
                while (curr != null) { // ищем нужную ноду
                    if (curr.key.equals(key)) {
                        return curr.value;
                    }
                    curr = curr.next;
                }
            }
            return "none";
        }
    }

    static class Map {
        public int size;
        public int capacity;
        public LinkedList[] arr;
        public double[] sizesArr;

        public Map(int capacity) {
            this.capacity = capacity;
            this.arr = new LinkedList[capacity];
            for (int i = 0; i < capacity; i++) {
                this.arr[i] = new LinkedList();
            }
            this.sizesArr = new double[capacity];
        }

        public void rehashing() {
            int oldCapacity = capacity;
            capacity *= 2;
            Map newMap = new Map(capacity);
            for (int i = 0; i < oldCapacity; i++) { // обход всех ячеек массива
                Node cur = arr[i].head;
                if (arr[i].size > 0) {
                    while (cur != null) {
                        int newIdx = hashFun(cur.key);
                        newMap.arr[newIdx].add(cur);
                        newMap.size++;
                        newMap.sizesArr[newIdx] += 1. / capacity;
                        cur = cur.next;
                    }
                }
            }
            arr = newMap.arr;
            size = newMap.size;
            sizesArr = newMap.sizesArr;
        }

        public int hashFun(String key) {
            long value = 0;
            for (int i = 0; i < key.length(); i++) {
                value = (key.charAt(i) + value * A) % P;
            }
            return (int)(value % capacity);
        }

        public void put(String key, String value) {
            Node tmp = new Node(key, value);
            int idx = hashFun(key);
            if (arr[idx].size > 0) { // ищем совпадающий ключ, чтобы заменить значение
                Node cur = arr[idx].head;
                while (cur != null) {
                    if (cur.key.equals(key)) {
                        cur.value = value;
                        return;
                    }
                    cur = cur.next;
                }
            }
            // иначе просто добавляем в конец списка
            arr[idx].add(tmp);
            sizesArr[idx] += 1. / capacity;
            size++;

            if (sizesArr[idx] > LOAD_FACTOR) { // превысили LOAD_FACTOR -> увеличиваем массив
                rehashing();
            }
        }

        public void delete(String key) {
            int idx = hashFun(key);
            if (arr[idx].isDrop(key)) {
                sizesArr[idx] -= 1. / capacity;
                size--;
            }
        }

        public String get(String key) {
            int idx = hashFun(key);
            return arr[idx].exists(key);
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

        String key, value;
        Map map = new Map(INITIAL_CAPACITY);
        for (String[] el: commandList) {
            String command = el[0];
            switch (command) {
                case "put":
                    key = el[1];
                    value = el[2];
                    map.put(key, value);
                    break;
                case "delete":
                    key = el[1];
                    map.delete(key);

                    break;
                case "get":
                    key = el[1];
                    out.println(map.get(key));
                    out.flush();
                    break;
                default:
                    break;
            }
        }
    }
}
