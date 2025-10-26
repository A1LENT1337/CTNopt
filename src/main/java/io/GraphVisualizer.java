package io;

import graph.Graph;
import graph.Edge;
import model.MSTResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GraphVisualizer {

    public static void createPerformanceChart(List<MSTResult> results, String outputPath) throws IOException {
        XYSeries primTimeSeries = new XYSeries("Prim Time");
        XYSeries kruskalTimeSeries = new XYSeries("Kruskal Time");
        XYSeries primOpsSeries = new XYSeries("Prim Operations");
        XYSeries kruskalOpsSeries = new XYSeries("Kruskal Operations");

        // Группируем по размеру графа
        Map<Integer, List<MSTResult>> bySize = new TreeMap<>();
        for (MSTResult result : results) {
            bySize.computeIfAbsent(result.getVertexCount(), k -> new ArrayList<>()).add(result);
        }

        for (Map.Entry<Integer, List<MSTResult>> entry : bySize.entrySet()) {
            int vertexCount = entry.getKey();
            List<MSTResult> algoResults = entry.getValue();

            double primTime = 0, kruskalTime = 0;
            double primOps = 0, kruskalOps = 0;
            int primCount = 0, kruskalCount = 0;

            for (MSTResult result : algoResults) {
                if ("Prim".equals(result.getAlgorithmName())) {
                    primTime += result.getExecutionTime();
                    primOps += result.getOperationCount();
                    primCount++;
                } else {
                    kruskalTime += result.getExecutionTime();
                    kruskalOps += result.getOperationCount();
                    kruskalCount++;
                }
            }

            if (primCount > 0) {
                primTimeSeries.add(vertexCount, primTime / primCount);
                primOpsSeries.add(vertexCount, primOps / primCount);
            }
            if (kruskalCount > 0) {
                kruskalTimeSeries.add(vertexCount, kruskalTime / kruskalCount);
                kruskalOpsSeries.add(vertexCount, kruskalOps / kruskalCount);
            }
        }

        // Создаем график времени
        XYSeriesCollection timeDataset = new XYSeriesCollection();
        timeDataset.addSeries(primTimeSeries);
        timeDataset.addSeries(kruskalTimeSeries);

        JFreeChart timeChart = ChartFactory.createXYLineChart(
                "MST Algorithms Execution Time",
                "Graph Size (vertices)",
                "Time (ms)",
                timeDataset
        );

        XYSeriesCollection opsDataset = new XYSeriesCollection();
        opsDataset.addSeries(primOpsSeries);
        opsDataset.addSeries(kruskalOpsSeries);

        JFreeChart opsChart = ChartFactory.createXYLineChart(
                "MST Algorithms Operation Count",
                "Graph Size (vertices)",
                "Operations",
                opsDataset
        );

        ChartUtils.saveChartAsPNG(new File(outputPath + "_time.png"), timeChart, 800, 600);
        ChartUtils.saveChartAsPNG(new File(outputPath + "_operations.png"), opsChart, 800, 600);
    }

    public static void createCostComparisonChart(List<MSTResult> results, String outputPath) throws IOException {
        XYSeries primSeries = new XYSeries("Prim");
        XYSeries kruskalSeries = new XYSeries("Kruskal");

        // Группируем по ID графа
        Map<String, MSTResult[]> byGraph = new TreeMap<>();
        for (MSTResult result : results) {
            String graphKey = result.getVertexCount() + "_" + (results.indexOf(result) / 2);
            byGraph.computeIfAbsent(graphKey, k -> new MSTResult[2]);

            if ("Prim".equals(result.getAlgorithmName())) {
                byGraph.get(graphKey)[0] = result;
            } else {
                byGraph.get(graphKey)[1] = result;
            }
        }

        int index = 1;
        for (Map.Entry<String, MSTResult[]> entry : byGraph.entrySet()) {
            MSTResult[] algoResults = entry.getValue();
            if (algoResults[0] != null && algoResults[1] != null) {
                primSeries.add(index, algoResults[0].getTotalCost());
                kruskalSeries.add(index, algoResults[1].getTotalCost());
                index++;
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(primSeries);
        dataset.addSeries(kruskalSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "MST Cost Comparison (Prim vs Kruskal)",
                "Graph Instance",
                "Total Cost",
                dataset
        );

        ChartUtils.saveChartAsPNG(new File(outputPath + "_cost.png"), chart, 800, 600);
    }
}