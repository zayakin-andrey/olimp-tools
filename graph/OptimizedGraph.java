package lib.graph;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by hydra on 17.08.17.
 */
public class OptimizedGraph {
    private final int[] to;
    private final int[] next;
    private final int[] head;
    private final int[] weight;
    private final int vertexCount;
    private final int edgesCount;
    private int e =  0;

    public OptimizedGraph(int vertexCount, int edgesCount) {
        this.vertexCount = vertexCount;
        this.edgesCount = edgesCount;
        to = new int[edgesCount];
        next = new int[edgesCount];
        head = new int[vertexCount];
        weight = new int[edgesCount];
        Arrays.fill(head, -1);
    }

    public int vertexCount() {
        return vertexCount;
    }

    public Iterable<Edge> edges(int from) {
        return new Iterable<Edge>() {
            int e = head[from];
            @Override
            public Iterator<Edge> iterator() {
                return new Iterator<Edge>() {
                    @Override
                    public boolean hasNext() {
                        return e >= 0;
                    }

                    @Override
                    public Edge next() {
                        Edge res = new Edge(from, to[e], weight[e]);
                        e = next[e];
                        return res;
                    }
                };
            };
        };
    }

    public void addDirectEdge(int from, int to, int weight) {
        this.to[e] = to;
        this.weight[e] = weight;
        this.next[e] = head[from];
        head[from] = e++;
    }

}