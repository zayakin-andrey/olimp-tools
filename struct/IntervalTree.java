package lib.struct;

/**
 * Created by hydra on 04.03.17.
 */
public abstract class IntervalTree<T> {
    private T[] func;
    private int size;
    protected abstract T defaultValue();
    protected abstract T merge(T a, T b);

    public IntervalTree(int n) {
        size = n;
        func = (T[]) new Object[4 * n + 10];
        for (int i = 0; i < func.length; i++) {
            func[i] = defaultValue();
        }
    }

    public T get(int l, int r) {
        return get(0, size - 1, l, r, 1);
    }

    public void set(int pos, T value) {
        set(0, size - 1, pos, 1, value);
    }

    private void set(int l, int r, int pos, int u, T value) {
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

    private T get(int l, int r, int lf, int rg, int u) {
        if (r < lf || rg < l)
            return defaultValue();
        if (lf <= l && r <= rg)
            return func[u];
        int m = (l + r) >> 1;
        T f1 = get(l, m, lf, rg, u << 1);
        T f2 = get(m + 1, r, lf, rg, (u << 1) + 1);
        return merge(f1, f2);
    }

    private void recalc(int u) {
        func[u] = merge(func[u << 1], func[(u << 1) + 1]);
    }
}
