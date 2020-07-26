package lib.graph;

import lib.struct.IntQueue;

import java.util.Arrays;

/**
 * Created by hydra on 08.04.17.
 */
public class ShortestPathAlgorithm {
    private ShortestPathAlgorithm() {

    }
    public static int[][] floyd(int[][] d) {
        int[][] res = d.clone();
        int n = res.length;
        for(int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int better = d[i][k] + d[k][j];
                    if (d[i][j] > better) {
                        d[i][j] = better;
                    }
                }
            }
        }
        return d;
    }

    public static long[] dijkstraHeap(Graph graph, int start) {
        return dijkstraHeap(graph, new int[] {start});
    }
    public static long[] dijkstraHeap(Graph graph, int[] start) {
        int vertexCount = graph.vertexCount();
        long INF = Long.MAX_VALUE / 4;
        long[] result = new long[vertexCount];
        Heap heap = new Heap(vertexCount);
        Arrays.fill(result, INF);
        for (int v : start) {
            result[v] = 0;
            heap.add(v, 0);
        }
        while (!heap.isEmpty()) {
            Item peek = heap.poll();
            long currentDistance = peek.distance;
            for (Edge to : graph.edges(peek.vertex)) {
                long relaxedDistance = currentDistance + to.weight;
                if (result[to.to] > relaxedDistance) {
                    result[to.to] = relaxedDistance;
                    heap.add(to.to, relaxedDistance);
                }
            }
        }
        return result;
    }

    public static int[] bfs(Graph graph, int source) {
        int vertexes = graph.vertexCount();
        int[] dist = new int[vertexes];
        Arrays.fill(dist, -1);
        IntQueue queue = new IntQueue(vertexes);
        dist[source] = 0;
        queue.enqueue(source);
        while (!queue.isEmpty()) {
            int p = queue.poll();
            for (Edge e : graph.edges(p)) {
                if (dist[e.to] == -1) {
                    dist[e.to] = dist[p] + 1;
                    queue.enqueue(e.to);
                }
            }
        }
        return dist;
    }

    private static class Item {
        long distance;
        int vertex;

        Item(long distance, int vertex) {
            this.distance = distance;
            this.vertex = vertex;
        }
    }


    private static class Heap {
        private static final int UNDEFINED = -1;
        private final Item[] heap;
        private final int[] pointer;
        private int elements;
        private Heap(int maxSize) {
            heap = new Item[maxSize + 1];
            elements = 0;
            pointer = new int[maxSize];
            Arrays.fill(pointer, UNDEFINED);
        }

        boolean isEmpty() {
            return elements == 0;
        }

        Item poll() {
            swap(1, elements);
            Item result = heap[elements--];
            pointer[result.vertex] = UNDEFINED;
            if (!isEmpty()) {
                heapify(1);
            }
            return result;
        }

        void add(int vertex, long distance) {
            if (pointer[vertex] == UNDEFINED) {
                elements++;
                heap[elements] = new Item(distance, vertex);
                pointer[vertex] = elements;
                up(elements);
            } else {
                if (distance >= heap[pointer[vertex]].distance) {
                    return;
                }
                heap[pointer[vertex]].distance = distance;
                up(pointer[vertex]);
            }
        }

        private void up(int index) {
            for (; index != 1; index >>= 1) {
                if (heap[index].distance < heap[index >> 1].distance) {
                    swap(index, index >> 1);
                } else {
                    return;
                }
            }
        }
        private void heapify(int index) {
            int leastIndex = index;
            int left = index * 2;
            if (left <= elements && heap[left].distance < heap[leastIndex].distance) {
                leastIndex = left;
            }
            int right = left + 1;
            if (right <= elements && heap[right].distance < heap[leastIndex].distance) {
                leastIndex = right;
            }
            if (index == leastIndex)
                return;

            swap(index, leastIndex);
            heapify(leastIndex);
        }

        private void swap(int a, int b) {
            pointer[heap[a].vertex] = b;
            pointer[heap[b].vertex] = a;
            Item temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;
        }
    }
}
