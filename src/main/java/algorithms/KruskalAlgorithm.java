package algorithms;

import graph.Graph;
import graph.Edge;
import model.MSTResult;
import java.util.*;

public class KruskalAlgorithm implements MSTAlgorithm {
    private int operationCount;

    @Override
    public MSTResult findMST(Graph graph) {
        operationCount = 0;
        long startTime = System.nanoTime();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        if (graph.getVertices().isEmpty()) {
            long endTime = System.nanoTime();
            double executionTime = (endTime - startTime) / 1_000_000.0; // ИЗМЕНИЛ на double
            return new MSTResult(getAlgorithmName(), 0, 0, mstEdges, 0, executionTime, operationCount);
        }

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);
        operationCount += sortedEdges.size() * (int) Math.log(sortedEdges.size());

        UnionFind uf = new UnionFind(graph.getVertices().size());

        for (Edge edge : sortedEdges) {
            operationCount++;
            int fromIndex = graph.getVertices().indexOf(edge.getFrom());
            int toIndex = graph.getVertices().indexOf(edge.getTo());

            if (fromIndex == -1 || toIndex == -1) continue;

            if (uf.find(fromIndex) != uf.find(toIndex)) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                uf.union(fromIndex, toIndex);
                operationCount += 2;
            }

            if (mstEdges.size() == graph.getVertexCount() - 1) {
                break;
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0; // ИЗМЕНИЛ на double

        return new MSTResult(
                getAlgorithmName(),
                graph.getVertexCount(),
                graph.getEdgeCount(),
                mstEdges,
                totalCost,
                executionTime,
                operationCount
        );
    }

    @Override
    public String getAlgorithmName() {
        return "Kruskal";
    }

    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}