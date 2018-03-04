package lib.struct;

public class Summator {
    private final long[] prefix;

    public Summator(int[] a) {
        this(toLong(a));
    }

    private static long[] toLong(int[] a) {
        long[] result = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }
        return result;
    }

    public Summator(long[] a) {
        prefix = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                prefix[i] += prefix[i - 1];
            }
            prefix[i] += a[i];
        }
    }

    public long getSum(int l, int r) {
        long result = 0;
        if (r >= 0) {
            result += prefix[r];
        }
        if (l - 1 >= 0) {
            result -= prefix[l - 1];
        }
        return result;
    }
}
