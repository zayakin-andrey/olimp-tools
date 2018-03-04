package lib.struct;

public class SumSegmentTree extends SegmentTree {
    public SumSegmentTree(int n) {
        super(n);
    }

    @Override
    protected long defaultValue() {
        return 0;
    }

    @Override
    protected long merge(long a, long b) {
        return a + b;
    }
}
