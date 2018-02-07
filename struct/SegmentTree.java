package lib.struct;

/**
 * Created by hydra on 04.03.17.
 */
public abstract class SegmentTree {
    private long[] func;
    private int size;
    protected abstract long defaultValue();
    protected abstract long merge(long a, long b);

    public SegmentTree(int n) {
        size = n;
        func = new long[4 * n + 10];
    }

    public void build(int[] initialValues) {
        build(0, initialValues.length - 1, 1, initialValues);
    }

    public long get(int l, int r) {
        return get(0, size - 1, l, r, 1);
    }

    public void set(int pos, int value) {
        set(0, size - 1, pos, 1, value);
    }

    private void set(int l, int r, int pos, int u, int value) {
        if (l == r) {
            func[u] = value;
        } else {
            int m = (l + r) >> 1;
            if (pos <= m) {
                set(l, m, pos, u << 1, value);
            } else {
                set(m + 1, r, pos, (u << 1) + 1, value);
            }
            recalc(u);
        }
    }

    private long get(int l, int r, int lf, int rg, int u) {
        if (r < lf || rg < l)
            return defaultValue();
        if (lf <= l && r <= rg)
            return func[u];
        int m = (l + r) >> 1;
        long f1 = get(l, m, lf, rg, u << 1);
        long f2 = get(m + 1, r, lf, rg, (u << 1) + 1);
        return merge(f1, f2);
    }


    private void build(int l, int r, int u, int[] initialValues) {
        if (l == r) {
            func[u] = initialValues[l];
        } else {
            int m = (l + r) >> 1;
            build(l, m, u << 1, initialValues);
            build(m + 1, r, (u << 1) + 1, initialValues);
            recalc(u);
        }
    }

    private void recalc(int u) {
        func[u] = merge(func[u << 1], func[(u << 1) + 1]);
    }
}
