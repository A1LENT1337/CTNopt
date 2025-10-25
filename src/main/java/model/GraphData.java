package model;

import java.util.List;

public class GraphData {
    private int id;
    private List<Integer> nodes;
    private List<JsonEdge> edges;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Integer> getNodes() { return nodes; }
    public void setNodes(List<Integer> nodes) { this.nodes = nodes; }

    public List<JsonEdge> getEdges() { return edges; }
    public void setEdges(List<JsonEdge> edges) { this.edges = edges; }

    public static class JsonEdge {
        private int from;
        private int to;
        private int weight;

        public int getFrom() { return from; }
        public void setFrom(int from) { this.from = from; }

        public int getTo() { return to; }
        public void setTo(int to) { this.to = to; }

        public int getWeight() { return weight; }
        public void setWeight(int weight) { this.weight = weight; }
    }
}