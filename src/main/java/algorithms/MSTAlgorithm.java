package algorithms;

import graph.Graph;
import model.MSTResult;

public interface MSTAlgorithm {
    MSTResult findMST(Graph graph);
    String getAlgorithmName();
}