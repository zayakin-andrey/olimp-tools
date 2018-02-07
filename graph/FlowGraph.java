package lib.graph;

import lib.struct.IntQueue;

import java.util.Arrays;

/**
 * Created by hydra on 29.04.17.
 */
public class FlowGraph {
    int[] to;
    int[] flows;
    int[] capacities;
    int[] next;
    int[] heads;
    int edges;
    final int vertexCount;
    int arraysCapacity;
    IntQueue queue;
    int[] pointers;
    int[] distance;

    public FlowGraph(int vertexCount, int initialCapacity) {
        this.vertexCount = vertexCount;
        heads = new int[vertexCount];
        queue = new IntQueue(vertexCount);

        Arrays.fill(heads, -1);
        edges = 0;
        arraysCapacity = initialCapacity;
        pointers = new int[vertexCount];
        distance = new int[vertexCount];
        to = new int[arraysCapacity];
        flows = new int[arraysCapacity];
        capacities = new int[arraysCapacity];
        next = new int[arraysCapacity];
    }

    public FlowGraph(int vertexCount){
        this(vertexCount, 10);
    }

    public void addFlowEdge(int from, int to, int capability) {
        addDirectEdge(from, to, capability);
        addDirectEdge(to, from, 0);
    }

    private void addDirectEdge(int from, int to, int capability) {
        enlargeArraysIfNeed();
        this.to[edges] = to;
        flows[edges] = 0;
        capacities[edges] = capability;
        next[edges] = heads[from];
        heads[from] = edges++;
    }

    private void enlargeArraysIfNeed() {
        if (edges < arraysCapacity)
            return;
        arraysCapacity *= 2;
        int[] newTo = new int[arraysCapacity];
        int[] newFlows = new int[arraysCapacity];
        int[] newCapacities = new int[arraysCapacity];
        int[] newNext = new int[arraysCapacity];
        System.arraycopy(to, 0, newTo, 0, to.length);
        System.arraycopy(flows, 0, newFlows, 0, flows.length);
        System.arraycopy(capacities, 0, newCapacities, 0, capacities.length);
        System.arraycopy(next, 0, newNext, 0, next.length);
        to = newTo;
        flows = newFlows;
        capacities = newCapacities;
        next = newNext;
    }

    public int maxFlow(int source, int destination) {
        int totalFlow = 0;
        final int inf = Integer.MAX_VALUE / 2;
        while (bfs(source, destination)) {
            while (true) {
                System.arraycopy(heads, 0, pointers, 0, pointers.length);
                int stepFlow = dfs(source, destination, inf);
                if (stepFlow == 0)
                    break;
                totalFlow += stepFlow;
            }
        }
        return totalFlow;
    }

    private int dfs(int source, int destination, int flow) {
        if (source == destination || flow == 0)
            return flow;

        for (int e = pointers[source]; e >= 0; e = next[e]) {
            pointers[source] = next[e];
            int enlarge = capacities[e] - flows[e];
            if (enlarge <= 0 || distance[to[e]] != distance[source] + 1)
                continue;

            int tempFlow = dfs(to[e], destination, Math.min(flow, enlarge));
            if (tempFlow == 0)
                continue;

            flows[e] += tempFlow;
            flows[e ^ 1] -= tempFlow;
            return tempFlow;
        }
        return 0;

    }

    private boolean bfs(int source, int destination) {
        Arrays.fill(distance, -1);
        queue.enqueue(source);
        distance[source] = 0;
        while (!queue.isEmpty()) {
            int p = queue.poll();
            for (int e = heads[p]; e >= 0; e = next[e]) {
                if (distance[to[e]] == -1 && flows[e] < capacities[e]) {
                    distance[to[e]] = distance[p] + 1;
                    queue.enqueue(to[e]);
                }
            }
        }
        return distance[destination] != -1;
    }

    public int getEdges() {
        return edges;
    }

    public int[] getFlows() {
        return flows;
    }
}
