package lib.algorithm;

public class BinarySearch {

    public static long getBestIndex(InputParams inputParams,
                                    Checker checker) {
        long left = inputParams.left;
        long right = inputParams.right;
        long bestIndex = inputParams.type == Type.Min ? right + 1 : left - 1;
        while (left <= right) {
            long mid = (left + right) / 2;
            if (checker.achieved(mid)) {
                if (inputParams.type == Type.Min) {
                    bestIndex = Math.min(bestIndex, mid);
                    right = mid - 1;
                } else {
                    bestIndex = Math.max(bestIndex, mid);
                    left = mid + 1;
                }
            } else {
                if (inputParams.type == Type.Min) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return bestIndex;
    }

    public interface Checker {
        boolean achieved(long mid);
    }

    public static class InputParams {
        private final long left;
        private final long right;
        private final Type type;

        public InputParams(long left, long right, Type type) {
            this.left = left;
            this.right = right;
            this.type = type;
        }
    }

    public static enum Type {
        Min,
        Max
    }
}