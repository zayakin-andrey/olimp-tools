package lib.struct;

public class FenwickTree {
    long[] data;

    public FenwickTree(int n) {
        data = new long[n];
    }

    public void set(int pos, long value) {
        long currentValue = getValue(pos);
        long delta = value - currentValue;
        for (; pos < data.length; pos |= pos + 1) {
            data[pos] += delta;
        }
    }

    public long get(int l, int r) {
        return get(r) - get(l - 1);
    }

    private long getValue(int pos) {
        return get(pos) - get(pos - 1);
    }

    private long get(int pos) {
        long result = 0;
        for (; pos >= 0; pos = (pos & (pos + 1)) - 1) {
            result += data[pos];
        }
        return result;
    }
}
