import algorithms.*;
import graph.Graph;
import io.JsonGraphReader;
import io.ResultWriter;
import io.GraphVisualizer;
import model.MSTResult;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            JsonGraphReader reader = new JsonGraphReader();
            ResultWriter writer = new ResultWriter();

            MSTAlgorithm prim = new PrimAlgorithm();
            MSTAlgorithm kruskal = new KruskalAlgorithm();

            List<MSTResult> allResults = new ArrayList<>();

            String[] inputFiles = {
                    "input/small_graphs.json",
                    "input/medium_graphs.json",
                    "input/large_graphs.json",
                    "input/extra_large_graphs.json"
            };

            List<String> benchmarkResults = new ArrayList<>();
            benchmarkResults.add("Dataset,Graph Count,Algorithm,Avg Time (ms),Avg Operations,Total Cost");

            for (String inputFile : inputFiles) {
                System.out.println("Processing: " + inputFile);

                try {
                    List<Graph> graphs = reader.readGraphsFromFile(inputFile);
                    Map<String, List<Double>> timeMap = new HashMap<>();
                    Map<String, List<Long>> opMap = new HashMap<>();
                    Map<String, Integer> costMap = new HashMap<>();

                    // Генерируем визуализации только для small graphs
                    if (inputFile.equals("input/small_graphs.json")) {
                        System.out.println("🎨 Generating graph visualizations...");
                        GraphVisualizer.generateAllVisualizations(graphs, "output/visualizations");
                    }

                    for (Graph graph : graphs) {
                        for (int i = 0; i < 3; i++) {
                            MSTResult primResult = prim.findMST(graph);
                            MSTResult kruskalResult = kruskal.findMST(graph);

                            if (i == 0) {
                                allResults.add(primResult);
                                allResults.add(kruskalResult);
                            }

                            timeMap.computeIfAbsent("Prim", k -> new ArrayList<>()).add(primResult.getExecutionTime());
                            timeMap.computeIfAbsent("Kruskal", k -> new ArrayList<>()).add(kruskalResult.getExecutionTime());
                            opMap.computeIfAbsent("Prim", k -> new ArrayList<>()).add((long) primResult.getOperationCount());
                            opMap.computeIfAbsent("Kruskal", k -> new ArrayList<>()).add((long) kruskalResult.getOperationCount());
                            costMap.put("Prim", primResult.getTotalCost());
                            costMap.put("Kruskal", kruskalResult.getTotalCost());

                            validateResults(primResult, kruskalResult, graph);
                        }
                    }

                    for (String algo : Arrays.asList("Prim", "Kruskal")) {
                        double avgTime = timeMap.get(algo).stream().mapToDouble(Double::doubleValue).average().orElse(0);
                        double avgOps = opMap.get(algo).stream().mapToLong(Long::longValue).average().orElse(0);
                        int cost = costMap.get(algo);

                        benchmarkResults.add(String.format("%s,%d,%s,%.2f,%.0f,%d",
                                new File(inputFile).getName(),
                                graphs.size(),
                                algo,
                                avgTime,
                                avgOps,
                                cost
                        ));
                    }

                } catch (Exception e) {
                    System.out.println("⚠️  Could not process " + inputFile + ": " + e.getMessage());
                }
            }

            new File("output").mkdirs();
            new File("output/visualizations").mkdirs(); // Создаем папку для визуализаций

            writer.writeJsonResults(allResults, "output/results.json");
            writer.writeCsvResults(allResults, "output/results.csv");
            saveBenchmarkResults(benchmarkResults, "output/benchmark.csv");

            System.out.println("✅ All graphs processed successfully!");
            System.out.println("📊 Results saved to output/ folder");
            System.out.println("🎨 Visualizations saved to output/visualizations/ folder");

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void saveBenchmarkResults(List<String> benchmarkResults, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (String line : benchmarkResults) {
                writer.write(line + "\n");
            }
            writer.close();
            System.out.println("✅ Benchmark results saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Error saving benchmark: " + e.getMessage());
        }
    }

    private static void validateResults(MSTResult prim, MSTResult kruskal, Graph graph) {
        boolean costValid = prim.getTotalCost() == kruskal.getTotalCost();
        boolean primEdgesValid = prim.getMstEdges().size() == graph.getVertexCount() - 1;
        boolean kruskalEdgesValid = kruskal.getMstEdges().size() == graph.getVertexCount() - 1;

        if (!costValid) {
            System.err.println("❌ Cost mismatch: Prim=" + prim.getTotalCost() +
                    ", Kruskal=" + kruskal.getTotalCost());
        }

        if (!primEdgesValid) {
            System.err.println("❌ Prim MST edge count incorrect: " +
                    prim.getMstEdges().size() + " (expected: " + (graph.getVertexCount() - 1) + ")");
        }

        if (!kruskalEdgesValid) {
            System.err.println("❌ Kruskal MST edge count incorrect: " +
                    kruskal.getMstEdges().size() + " (expected: " + (graph.getVertexCount() - 1) + ")");
        }

        if (costValid && primEdgesValid && kruskalEdgesValid) {
            System.out.println("✅ Validation passed for graph with " + graph.getVertexCount() + " vertices");
        }
    }
}