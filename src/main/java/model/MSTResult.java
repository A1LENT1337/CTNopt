package model;

import graph.Edge;
import java.util.List;

public class MSTResult {
    private final String algorithmName;
    private final int vertexCount;
    private final int edgeCount;
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final double executionTime; // ИЗМЕНИЛ long на double
    private final int operationCount;

    public MSTResult(String algorithmName, int vertexCount, int edgeCount,
                     List<Edge> mstEdges, int totalCost, double executionTime, int operationCount) { // ИЗМЕНИЛ параметр
        this.algorithmName = algorithmName;
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.executionTime = executionTime;
        this.operationCount = operationCount;
    }

    public String getAlgorithmName() { return algorithmName; }
    public int getVertexCount() { return vertexCount; }
    public int getEdgeCount() { return edgeCount; }
    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public double getExecutionTime() { return executionTime; } // ИЗМЕНИЛ возвращаемый тип
    public int getOperationCount() { return operationCount; }
}