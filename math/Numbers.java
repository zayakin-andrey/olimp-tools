package lib.math;

import java.math.BigInteger;
import java.util.Arrays;

public class Numbers {
    public static int[] getDivisorsCount(int N) {
        N++;
        int[] d = new int[N];
        Arrays.fill(d, 1);
        boolean[] isprime = new boolean[N];
        int[] num = new int[N];
        for (int i = 1; i < N; i++) {
            num[i] = i;
        }
        Arrays.fill(isprime, true);
        for (int i = 2; i < isprime.length; i++) {
            if (isprime[i]) {
                for (int j = i; j < isprime.length; j += i) {
                    if (j > i) {
                        isprime[j] = false;
                    }
                    int pow = 0;
                    while (num[j] % i == 0) {
                        num[j] /= i;
                        pow++;
                    }
                    d[j] *= (pow + 1);
                }
            }
        }
        for (int i = 2; i < N; i++) {
            if (num[i] > 1) {
                d[i] *= 2;
            }
        }
        return d;
    }

    public static int[][] getBinomial(int n, int m, int modulo) {
        int[][] result = new int[n + 1][m + 1];
        for (int i = 0; i < n + 1; i++) {
            result[i][0] = 1 % modulo;
            for (int j = 1; j <= i; j++) {
                result[i][j] = result[i - 1][j] + result[i - 1][j - 1];
                if (result[i][j] >= modulo) {
                    result[i][j] -= modulo;
                }
            }
        }
        return result;
    }
    public static BigInteger[][] getBinomial(int n, int m) {
        BigInteger[][] result = new BigInteger[n + 1][m + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                result[i][j] = BigInteger.ZERO;
            }
        }
        for (int i = 0; i < n + 1; i++) {
            result[i][0] = BigInteger.ONE;
            for (int j = 1; j <= i; j++) {
                result[i][j] = result[i - 1][j].add(result[i - 1][j - 1]);
            }
        }
        return result;
    }
}
