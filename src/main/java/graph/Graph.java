package graph;

import java.util.*;

public class Graph {
    private final List<Integer> vertices;
    private final List<Edge> edges;

    public Graph(List<Integer> vertices, List<Edge> edges) {
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
    }

    public List<Integer> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}