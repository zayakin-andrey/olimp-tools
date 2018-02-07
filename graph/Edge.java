package lib.graph;

/**
 * Created by hydra on 19.03.17.
 */
public class Edge {
    public final int to;
    public final int from;
    public final int weight;

    Edge(int from, int to, int weight) {
        this.to = to;
        this.from = from;
        this.weight = weight;
    }
    Edge(int from, int to) {
        this(from, to, 0);
    }
}
