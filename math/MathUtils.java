package lib.math;

import java.math.BigInteger;

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

    public static long fastPow(long a, long n, long modulo) {
        long res = 1 % modulo;
        a %= modulo;
        BigInteger bigModule = BigInteger.valueOf(modulo);

        if (n < 0)
            throw new IllegalArgumentException("power should be non negative");

        for (;n > 0; n >>= 1) {
            if ((n & 1) != 0) {
                res = BigInteger.valueOf(res).multiply(BigInteger.valueOf(a)).mod(bigModule).longValue();
            }
            a = BigInteger.valueOf(a).multiply(BigInteger.valueOf(a)).mod(bigModule).longValue();
        }
        return res;
    }

    public static int inverse(int a, int primeModulo) {
        return fastPow(a, primeModulo - 2, primeModulo);
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int phi(int n) {
        int result = n;
        for (int i = 2; i * i <= n; ++i)
            if (n % i == 0) {
                while (n % i == 0)
                    n /= i;
                result -= result / i;
            }
        if (n > 1)
            result -= result / n;
        return result;
    }
}
