package lib.math;

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
}
