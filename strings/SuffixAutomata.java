package lib.strings;

import java.util.Arrays;
import java.util.Iterator;

public class SuffixAutomata {

    int[][] next;
    int[] link;
    int[] len;
    int sz, last;
    int maxAlphabet;


    public SuffixAutomata(int maxAlphabet, int maxSymbols) {
        this.maxAlphabet = maxAlphabet;
        int n = maxSymbols * 2;
        next = new int[n][maxAlphabet];
        for(int i = 0; i < n; i++) {
            Arrays.fill(next[i], -1);
        }
        link = new int[n];
        len = new int[n];

        sz = last = 0;
        len[0] = 0;
        link[0] = -1;
        ++sz;
    }

    public int getSize() {
        return sz;
    }

    int[] getEdges(int source) {
        return next[source];
    }

    public Iterable<Edge> iterate(int source) {
        return new Iterable<Edge>() {
            int ptr = 0;
            @Override
            public Iterator<Edge> iterator() {
                return new Iterator<Edge>() {
                    @Override
                    public boolean hasNext() {
                        while (ptr < maxAlphabet && next[source][ptr] == -1) {
                            ptr++;
                        }
                        return ptr < maxAlphabet;
                    }

                    @Override
                    public Edge next() {
                        int symbol = ptr++;
                        return new Edge(symbol, next[source][symbol]);
                    }
                };
            }
        };
    }

    public void addSymbol(int ch) {
        int cur = sz++;
        len[cur] = len[last] + 1;
        int p;
        for (p=last; p!=-1 && next[p][ch] == -1; p=link[p])
            next[p][ch] = cur;
        if (p == -1)
            link[cur] = 0;
        else {
            int q = next[p][ch];
            if (len[p] + 1 == len[q])
               link[cur] = q;
            else {
                int clone = sz++;
                len[clone] = len[p] + 1;
                System.arraycopy(next[q], 0, next[clone], 0, maxAlphabet);
                link[clone] = link[q];
                for (; p!=-1 && next[p][ch] == q; p=link[p])
                    next[p][ch] = clone;
                link[q] = link[cur] = clone;
            }
        }
        last = cur;
    }

    public class Edge {
        public final int symbol;
        public final int vertex;

        private Edge(int symbol, int vertex) {
            this.symbol = symbol;
            this.vertex = vertex;
        }
    }
}
