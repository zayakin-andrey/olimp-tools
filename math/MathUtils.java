package lib.math;

/**
 * Created by hydra on 16.04.17.
 */
public class MathUtils {
    public static long lcm64(long a, long b) {
        if (a < 0)
            a = -a;
        if (b < 0)
            b = -b;
        return a * b / gcd64(a, b);
    }

    public static long gcd64(long a, long b) {
        if (a < 0)
            a = -a;
        if (b < 0)
            b = -b;
        while (a != 0 && b != 0) {
            if (a >= b)
                a %= b;
            else
                b %= a;
        }
        return a + b;
    }

    public static int lcm(int a, int b) {
        if (a < 0)
            a = -a;
        if (b < 0)
            b = -b;
        return a * b / gcd(a, b);
    }

    public static int gcd(int a, int b) {
        if (a < 0)
            a = -a;
        if (b < 0)
            b = -b;
        while (a != 0 && b != 0) {
            if (a >= b)
                a %= b;
            else
                b %= a;
        }
        return a + b;
    }

    public static int fastPow(int a, int n, int modulo) {
        int res = 1 % modulo;
        a %= modulo;

        if (n < 0)
            throw new IllegalArgumentException("power should be non negative");

        for (;n > 0; n >>= 1) {
            if ((n & 1) != 0) {
                res = (int) ((long) res * a % modulo);
            }
            a = (int)((long) a * a % modulo);
        }
        return res;
    }

    public static int inverse(int a, int primeModulo) {
        return fastPow(a, primeModulo - 2, primeModulo);
    }
}
