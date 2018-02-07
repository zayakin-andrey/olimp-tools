package lib.math;

/**
 * Created by hydra on 29.04.17.
 */
public class MiscUtils {
    public static final int MOD7 = 1_000_000_007;

    public static String getExcelString(int n) {
        if (n == 1) {
            return "A";
        }
        n--;
        long z = 0;
        int number = 0;
        while (z <= n) {
            z = z * 26 + 26;
            number++;
        }
        z = (z - 26) / 26;
        n -= z;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < number; ++i) {
            res.append((char) (n % 26 + 'A'));
            n /= 26;
        }
        return res.reverse().toString();
    }
}
