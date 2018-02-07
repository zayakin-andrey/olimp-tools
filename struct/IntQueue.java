package lib.struct;

/**
 * Created by hydra on 29.04.17.
 */
public class IntQueue {
    private int[] q;
    private int head, tail, maxSize;

    public IntQueue(int maxSize) {
        q = new int[maxSize];
        head = tail = 0;
        this.maxSize = maxSize;
    }

    public void enqueue(int value) {
        q[head++] = value;
        if (head == maxSize)
            head = 0;
    }

    public int poll() {
        int res = q[tail];
        if (++tail == maxSize)
            tail = 0;
        return res;
    }

    public int peek() {
        return q[tail];
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public void clear() {
        head = 0;
        tail = 0;
    }
}
