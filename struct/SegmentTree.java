package lib.struct;

/**
 * Created by hydra on 04.03.17.
 */
public abstract class SegmentTree {
    private int[] func;
    private int size;
    private Integer[] setValue;
    protected abstract int defaultValue();
    protected abstract int merge(int a, int b);
    private int funcLength;

    public SegmentTree(int n) {
        size = n;
        func = new int[4 * n + 10];
        setValue = new Integer[4 * n + 10];
        funcLength = func.length;
    }

    public void build(int[] initialValues) {
        build(0, initialValues.length - 1, 1, initialValues);
    }

    public int get(int l, int r) {
        return get(0, size - 1, l, r, 1);
    }

    public void set(int pos, int value) {
        set(0, size - 1, pos, 1, value);
    }

    public void setInterval(int l, int r, int value) {
        setInterval(0, size - 1, l, r, 1, value);
    }

    private void setInterval(int l, int r, int lf, int rg, int u, int value) {
        if (r < lf || rg < l)
            return;

        push(u);

        if (lf <= l && r <= rg) {
            setValue[u] = value;
            func[u] = value;
            return;
        }

        int m = (l + r) >> 1;
        setInterval(l, m, lf, rg, u << 1, value);
        setInterval(m + 1, r, lf, rg, (u << 1) + 1, value);
        recalc(u);
    }

    private void push(int u) {
        if (setValue[u] == null) {
            return;
        }

        int value = setValue[u];
        setValue[u] = null;
        int left = u << 1;
        int right = (u << 1) + 1;
        if (left < funcLength) {
            func[left] = value;
            setValue[left] = value;
            if (right < funcLength) {
                func[right] = value;
                setValue[right] = value;
            }
        }
    }

    private void set(int l, int r, int pos, int u, int value) {
        push(u);

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

    private int get(int l, int r, int lf, int rg, int u) {
        if (r < lf || rg < l)
            return defaultValue();

        push(u);

        if (lf <= l && r <= rg)
            return func[u];
        int m = (l + r) >> 1;
        int f1 = get(l, m, lf, rg, u << 1);
        int f2 = get(m + 1, r, lf, rg, (u << 1) + 1);
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
