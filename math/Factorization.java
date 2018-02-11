package lib.math;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hydra on 08.04.17.
 */
public class Factorization {
    private int maxValue;
    public boolean[] isprime;
    public final ArrayList<Integer> primes;

    public Factorization(int maxValue) {
        isprime = new boolean[maxValue + 1];
        primes = new ArrayList<>();
        Arrays.fill(isprime, true);
        isprime[0] = isprime[1] = false;
        for (int i = 2; i <= maxValue; i++) {
            if (isprime[i]) {
                primes.add(i);
                for (int j = i + i; j <= maxValue; j += i)
                    isprime[j] = false;
            }
        }
    }

    public Pair[] factorize(long n) {
        ArrayList<Pair> f = new ArrayList<>();
        for (int prime : primes) {
            if ((long) prime * prime > n)
                break;
            if (n % prime == 0) {
                int power = 0;
                while (n % prime == 0) {
                    power++;
                    n /= prime;
                }
                f.add(new Pair(prime, power));
            }
        }
        if (n != 1) {
            f.add(new Pair(n, 1));
        }
        Pair[] result = new Pair[f.size()];
        int ptr = 0;
        for (Pair x : f) {
            result[ptr ++] = x;
        }
        return result;
    }

    public static class Pair {
        public long key;
        public int power;

        public Pair(long key, int power) {
            this.key = key;
            this.power = power;
        }
    }
}
