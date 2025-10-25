package algorithms;

import graph.Graph;
import graph.Edge;
import model.MSTResult;
import java.util.*;

public class PrimAlgorithm implements MSTAlgorithm {
    private int operationCount;

    @Override
    public MSTResult findMST(Graph graph) {
        operationCount = 0;
        long startTime = System.nanoTime();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        Set<Integer> visited = new HashSet<>();

        if (graph.getVertices().isEmpty()) {
            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1_000_000;
            return new MSTResult(getAlgorithmName(), 0, 0, mstEdges, 0, executionTime, operationCount);
        }

        // Обрабатываем все компоненты связности
        for (Integer startVertex : graph.getVertices()) {
            if (visited.contains(startVertex)) continue;

            PriorityQueue<Edge> pq = new PriorityQueue<>();
            visited.add(startVertex);
            operationCount++;

            // Добавляем все рёбра из стартовой вершины
            for (Edge edge : graph.getEdges()) {
                operationCount++;
                if (edge.getFrom() == startVertex || edge.getTo() == startVertex) {
                    pq.offer(edge);
                }
            }

            while (!pq.isEmpty() && visited.size() < graph.getVertexCount()) {
                operationCount++;
                Edge edge = pq.poll();
                int nextVertex = -1;

                if (visited.contains(edge.getFrom()) && !visited.contains(edge.getTo())) {
                    nextVertex = edge.getTo();
                } else if (visited.contains(edge.getTo()) && !visited.contains(edge.getFrom())) {
                    nextVertex = edge.getFrom();
                }

                if (nextVertex != -1) {
                    visited.add(nextVertex);
                    mstEdges.add(edge);
                    totalCost += edge.getWeight();

                    // Добавляем рёбра из новой вершины
                    for (Edge e : graph.getEdges()) {
                        operationCount++;
                        if ((e.getFrom() == nextVertex && !visited.contains(e.getTo())) ||
                                (e.getTo() == nextVertex && !visited.contains(e.getFrom()))) {
                            pq.offer(e);
                        }
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1_000_000;

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
        return "Prim";
    }
}