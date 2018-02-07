package lib.io;

import java.io.PrintWriter;

/**
 * Created by hydra on 29.04.17.
 */
public interface Answers {
    public static final String YES = "YES";
    public static final String NO = "NO";

    public static void outYes(PrintWriter out) {
        out.println(YES);
    }

    public static void outNo(PrintWriter out) {
        out.println(NO);
    }
}
