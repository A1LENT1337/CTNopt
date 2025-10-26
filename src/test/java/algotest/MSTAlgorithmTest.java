package algotest;

import algorithms.*;
import graph.Graph;
import graph.Edge;
import model.MSTResult;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MSTAlgorithmTest {

    @Test
    void testBothAlgorithmsSameCost() {
        List<Integer> vertices = Arrays.asList(1, 2, 3, 4);
        List<Edge> edges = Arrays.asList(
                new Edge(1, 2, 1),
                new Edge(2, 3, 2),
                new Edge(3, 4, 3),
                new Edge(1, 4, 4),
                new Edge(1, 3, 5)
        );

        Graph graph = new Graph(vertices, edges);
        MSTAlgorithm prim = new PrimAlgorithm();
        MSTAlgorithm kruskal = new KruskalAlgorithm();

        MSTResult primResult = prim.findMST(graph);
        MSTResult kruskalResult = kruskal.findMST(graph);

        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost());
        assertEquals(3, primResult.getMstEdges().size()); // V-1 edges
        assertEquals(3, kruskalResult.getMstEdges().size());
    }

    @Test
    void testDisconnectedGraph() {
        List<Integer> vertices = Arrays.asList(1, 2, 3, 4);
        List<Edge> edges = Arrays.asList(
                new Edge(1, 2, 1),
                new Edge(3, 4, 2) // Нет соединения между компонентами
        );

        Graph graph = new Graph(vertices, edges);
        MSTAlgorithm prim = new PrimAlgorithm();
        MSTAlgorithm kruskal = new KruskalAlgorithm();

        assertThrows(IllegalStateException.class, () -> prim.findMST(graph));
        assertThrows(IllegalStateException.class, () -> kruskal.findMST(graph));
    }
}