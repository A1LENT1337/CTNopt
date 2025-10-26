import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class InputGenerator {

    public static void main(String[] args) throws IOException {
        new File("input").mkdirs();

        generateGraphs("input/small_graphs.json", 5, 5, 20);
        generateGraphs("input/medium_graphs.json", 10, 20, 100);
        generateGraphs("input/large_graphs.json", 10, 100, 500);
        generateGraphs("input/extra_large_graphs.json", 3, 500, 1000);

        System.out.println("✅ All input JSON files generated successfully!");
    }

    private static void generateGraphs(String filename, int count, int minVertices, int maxVertices) throws IOException {
        Random rand = new Random();
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> output = new HashMap<>();
        List<Map<String, Object>> graphs = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            int verticesCount = rand.nextInt(maxVertices - minVertices + 1) + minVertices;

            List<Integer> nodes = new ArrayList<>();
            for (int v = 1; v <= verticesCount; v++) {
                nodes.add(v);
            }

            List<Map<String, Object>> edges = new ArrayList<>();
            Set<String> edgeSet = new HashSet<>();

            for (int v = 1; v < verticesCount; v++) {
                int from = v;
                int to = v + 1;
                int weight = 1 + rand.nextInt(100);
                edges.add(createEdge(from, to, weight));
                edgeSet.add(from + "-" + to);
            }

            int extraEdges = verticesCount;
            for (int e = 0; e < extraEdges; e++) {
                int from = 1 + rand.nextInt(verticesCount);
                int to = 1 + rand.nextInt(verticesCount);
                if (from != to && !edgeSet.contains(from + "-" + to) && !edgeSet.contains(to + "-" + from)) {
                    int weight = 1 + rand.nextInt(100);
                    edges.add(createEdge(from, to, weight));
                    edgeSet.add(from + "-" + to);
                }
            }

            graphs.add(createGraph(i, nodes, edges));
        }

        output.put("graphs", graphs);
        mapper.writeValue(new File(filename), output);
        System.out.println("✅ Generated " + filename + " with " + count + " graphs");
    }

    private static Map<String, Object> createEdge(int from, int to, int weight) {
        Map<String, Object> edge = new HashMap<>();
        edge.put("from", from);
        edge.put("to", to);
        edge.put("weight", weight);
        return edge;
    }

    private static Map<String, Object> createGraph(int id, List<Integer> nodes, List<Map<String, Object>> edges) {
        Map<String, Object> graph = new HashMap<>();
        graph.put("id", id);
        graph.put("nodes", nodes);
        graph.put("edges", edges);
        return graph;
    }
}