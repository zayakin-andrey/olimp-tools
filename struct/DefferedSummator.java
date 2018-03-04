package lib.struct;

public class DefferedSummator {
    private final long[] add;

    public DefferedSummator(int n) {
        add = new long[n];
    }

    public void add(int l, int r, long by) {
        l = Math.max(0, l);
        if (l < add.length) {
            add[l] += by;
        }
        if (r + 1 < add.length) {
            add[r + 1] -= by;
        }
    }

    public long[] getResult() {
        long[] result = new long[add.length];
        long cur = 0;
        for (int i = 0; i < add.length; i++) {
            cur += add[i];
            result[i] = cur;
        }
        return result;
    }
}
