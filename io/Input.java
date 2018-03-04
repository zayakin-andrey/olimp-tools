package lib.io;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by hydra on 28.02.17.
 */
public class Input {
    private StringTokenizer tokenizer = null;
    private BufferedReader reader;

    public Input(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String nextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String nextToken() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(nextLine());
        }
        return tokenizer.nextToken();
    }

    public String next() {
        return nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    public double nextDouble() {
        return Double.parseDouble(nextToken());
    }


    public int[][] nextIntMatrix(int n, int m) {
        int[][] result = new int[n][];
        for (int i = 0; i < m; i++) {
            result[i] = nextIntArray(m);
        }
        return result;
    }

    public int[] nextIntArray(int n, int add) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = nextInt() + add;
        }
        return result;
    }
    public int[] nextIntArray(int n) {
        return nextIntArray(n, 0);
    }
    public int[] nextDoubleArray(int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = nextInt();
        }
        return result;
    }

    public long[] nextLongArray(int n) {
        long[] result = new long[n];
        for (int i = 0; i < n; i++) {
            result[i] = nextInt();
        }
        return result;
    }

    public void readToIntArrays(int n, int[]...a) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < a.length; j++) {
                a[j][i] = nextInt();
            }
        }
    }

    public void printArray(int[] a, PrintWriter out) {
        for (int anA : a) {
            out.print(anA + " ");
        }
    }

    public void nextArrays(int n, int[] ... output) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < output.length; j++) {
                output[j][i] = nextInt();
            }
        }
    }
}
