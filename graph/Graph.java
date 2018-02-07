package lib.graph;

import java.util.ArrayList;

/**
 * Created by hydra on 03.03.17.
 */
public class Graph {
    private final ArrayList<Edge>[] edges;
    private final int vertexCount;
    public Graph(int vertexCount) {
        this.vertexCount = vertexCount;
        edges = new ArrayList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            edges[i] = new ArrayList<>(0);
        }
    }
    public int vertexCount() {
        return vertexCount;
    }
    public Iterable<Edge> edges(int from) {
        return edges[from];
    }
    public void addEdge(int from, int to) {
        addDirectEdge(from, to);
        addDirectEdge(to, from);
    }
    public void addEdge(int from, int to, int weight) {
        addDirectEdge(from, to, weight);
        addDirectEdge(to, from, weight);
    }
    public void addDirectEdge(int from, int to) {
        edges[from].add(new Edge(from, to));
    }

    public void addDirectEdge(int from, int to, int weight) {
        edges[from].add(new Edge(from, to, weight));
    }
}
