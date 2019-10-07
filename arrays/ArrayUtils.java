package lib.arrays;

import java.util.Iterator;
import java.util.List;

/**
 * Created by hydra on 04.03.17.
 */
public class ArrayUtils {

    public static <T extends Comparable<T>> T max(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list can not be empty");
        }
        Iterator<T> iterator = list.iterator();
        T result = iterator.next();
        for (; iterator.hasNext(); ) {
            T current = iterator.next();
            int compare = current.compareTo(result);
            if (compare > 0) {
                result = current;
            }
        }
        return result;
    }

    public static long max(long [] a) {
        long result = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > result) {
                result = a[i];
            }
        }
        return result;
    }

    public static int max(int [] a) {
        int result = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > result) {
                result = a[i];
            }
        }
        return result;
    }

    public static int min(int [] a) {
        int result = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < result) {
                result = a[i];
            }
        }
        return result;
    }

    public static char[] reverse(char[] a) {
        int n = a.length;
        char[] result = new char[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[n - i - 1];
        }
        return result;
    }

    public static int[] reverse(int[] a) {
        int n = a.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[n - i - 1];
        }
        return result;
    }

    public static long[] reverse(long[] a) {
        int n = a.length;
        long[] result = new long[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[n - i - 1];
        }
        return result;
    }

    public static double[] reverse(double[] a) {
        int n = a.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[n - i - 1];
        }
        return result;
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static long sum(int[] cost) {
        long result = 0;
        for (int x : cost)
            result += x;
        return result;
    }

    public static long sum(long[] cost) {
        int result = 0;
        for (long x : cost)
            result += x;
        return result;
    }
}
