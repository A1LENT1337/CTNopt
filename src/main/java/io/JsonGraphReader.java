package io;

import graph.Graph;
import graph.Edge;
import model.GraphCollection;
import model.GraphData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonGraphReader {
    private final ObjectMapper objectMapper;

    public JsonGraphReader() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Graph> readGraphsFromFile(String filePath) throws IOException {
        GraphCollection collection = objectMapper.readValue(new File(filePath), GraphCollection.class);
        List<Graph> graphs = new ArrayList<>();

        for (GraphData graphData : collection.getGraphs()) {
            List<Integer> vertices = graphData.getNodes();
            List<Edge> edges = new ArrayList<>();

            for (GraphData.JsonEdge jsonEdge : graphData.getEdges()) {
                edges.add(new Edge(jsonEdge.getFrom(), jsonEdge.getTo(), jsonEdge.getWeight()));
            }

            graphs.add(new Graph(vertices, edges));
        }

        return graphs;
    }
}