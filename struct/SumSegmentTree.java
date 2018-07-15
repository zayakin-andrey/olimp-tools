package lib.struct;

public class SumSegmentTree extends SegmentTree {
    public SumSegmentTree(int n) {
        super(n);
    }

    @Override
    protected int defaultValue() {
        return 0;
    }

    @Override
    protected int merge(int a, int b) {
        return a + b;
    }
}
