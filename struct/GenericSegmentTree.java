package lib.struct;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by hydra on 04.03.17.
 */
public abstract class GenericSegmentTree<T> {
    private final T[] func;
    private final int size;
    private final T[] setValue;
    protected abstract T defaultValue();
    protected abstract T merged(T a, T b);
    protected abstract Class<T> elementClass();
    private final int funcLength;

    public GenericSegmentTree(int n) {
        size = n;
        Class<T> typeClass = elementClass();
        func = (T[]) Array.newInstance(typeClass, 4 * n + 10);
        T def = defaultValue();
        Arrays.fill(func, def);
        setValue = (T[]) Array.newInstance(typeClass, 4 * n + 10);
        funcLength = func.length;
    }

    public void build(T[] initialValues) {
        for(int i = 0; i < initialValues.length; i++) {
            if (initialValues[i] == null) {
                throw new IllegalStateException();
            }
        }
        build(0, initialValues.length - 1, 1, initialValues);
    }

    public T get(int l, int r) {
        return get(0, size - 1, l, r, 1);
    }

    public void set(int pos, T value) {
        if (value == null) throw new IllegalStateException();
        set(0, size - 1, pos, 1, value);
    }

    public void setInterval(int l, int r, T value) {
        if (value == null) throw new IllegalStateException();
        setInterval(0, size - 1, l, r, 1, value);
    }

    private void setInterval(int l, int r, int lf, int rg, int u, T value) {
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

        T value = setValue[u];
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

    private void set(int l, int r, int pos, int u, T value) {
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

    private T get(int l, int r, int lf, int rg, int u) {
        if (r < lf || rg < l)
            return defaultValue();

        push(u);

        if (lf <= l && r <= rg)
            return func[u];
        int m = (l + r) >> 1;
        T f1 = get(l, m, lf, rg, u << 1);
        T f2 = get(m + 1, r, lf, rg, (u << 1) + 1);
        return merged(f1, f2);
    }


    private void build(int l, int r, int u, T[] initialValues) {
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
        func[u] = merged(func[u << 1], func[(u << 1) + 1]);
    }
}
