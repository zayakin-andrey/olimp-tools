package lib.struct;

public class FenwickTree {
    int[] data;

    public FenwickTree(int n) {
        data = new int[n];
    }

    public void set(int pos, int value) {
        int currentValue = getValue(pos);
        int delta = value - currentValue;
        for (; pos < data.length; pos |= pos + 1) {
            data[pos] += delta;
        }
    }

    public int getSum(int l, int r) {
        return get(r) - get(l - 1);
    }

    private int getValue(int pos) {
        return get(pos) - get(pos - 1);
    }

    private int get(int pos) {
        int result = 0;
        for (; pos >= 0; pos = (pos & (pos + 1)) - 1) {
            result += data[pos];
        }
        return result;
    }
}
