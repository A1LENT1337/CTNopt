package io;

import graph.Graph;
import graph.Edge;
import model.MSTResult;
import algorithms.PrimAlgorithm;
import algorithms.KruskalAlgorithm;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GraphVisualizer {

    private static final int IMAGE_SIZE = 800;
    private static final int MARGIN = 50;
    private static final int NODE_RADIUS = 20;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color NODE_COLOR = Color.BLUE;
    private static final Color EDGE_COLOR = Color.GRAY;
    private static final Color MST_EDGE_COLOR = Color.RED;
    private static final Color TEXT_COLOR = Color.BLACK;

    public static void visualizeGraphWithMST(Graph originalGraph, MSTResult mstResult,
                                             String algorithmName, String outputPath) {
        BufferedImage image = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);

        List<Integer> vertices = originalGraph.getVertices();
        int vertexCount = vertices.size();
        Point[] positions = calculateCircularPositions(vertexCount);

        g2d.setColor(EDGE_COLOR);
        g2d.setStroke(new BasicStroke(1));
        for (Edge edge : originalGraph.getEdges()) {
            int fromIndex = vertices.indexOf(edge.getFrom());
            int toIndex = vertices.indexOf(edge.getTo());
            if (fromIndex != -1 && toIndex != -1) {
                Point from = positions[fromIndex];
                Point to = positions[toIndex];
                g2d.drawLine(from.x, from.y, to.x, to.y);
            }
        }

        g2d.setColor(MST_EDGE_COLOR);
        g2d.setStroke(new BasicStroke(3));
        for (Edge edge : mstResult.getMstEdges()) {
            int fromIndex = vertices.indexOf(edge.getFrom());
            int toIndex = vertices.indexOf(edge.getTo());
            if (fromIndex != -1 && toIndex != -1) {
                Point from = positions[fromIndex];
                Point to = positions[toIndex];
                g2d.drawLine(from.x, from.y, to.x, to.y);
            }
        }

        g2d.setColor(NODE_COLOR);
        for (int i = 0; i < vertexCount; i++) {
            Point pos = positions[i];
            g2d.fillOval(pos.x - NODE_RADIUS, pos.y - NODE_RADIUS,
                    NODE_RADIUS * 2, NODE_RADIUS * 2);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            String label = String.valueOf(vertices.get(i));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(label);
            int textHeight = fm.getHeight();
            g2d.drawString(label, pos.x - textWidth/2, pos.y + textHeight/4);
            g2d.setColor(NODE_COLOR);
        }

        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String title = algorithmName + " MST - Cost: " + mstResult.getTotalCost();
        g2d.drawString(title, 20, 30);

        g2d.dispose();

        try {
            ImageIO.write(image, "PNG", new File(outputPath));
            System.out.println("✅ Graph visualization saved: " + outputPath);
        } catch (IOException e) {
            System.err.println("❌ Error saving visualization: " + e.getMessage());
        }
    }

    private static Point[] calculateCircularPositions(int vertexCount) {
        Point[] positions = new Point[vertexCount];
        int centerX = IMAGE_SIZE / 2;
        int centerY = IMAGE_SIZE / 2;
        int radius = Math.min(IMAGE_SIZE, IMAGE_SIZE) / 2 - MARGIN - NODE_RADIUS;

        for (int i = 0; i < vertexCount; i++) {
            double angle = 2 * Math.PI * i / vertexCount;
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            positions[i] = new Point(x, y);
        }
        return positions;
    }

    public static void generateAllVisualizations(List<Graph> graphs, String outputDir) {
        PrimAlgorithm prim = new PrimAlgorithm();
        KruskalAlgorithm kruskal = new KruskalAlgorithm();

        new File(outputDir).mkdirs();

        for (int i = 0; i < Math.min(5, graphs.size()); i++) {
            Graph graph = graphs.get(i);

            // Prim visualization
            MSTResult primResult = prim.findMST(graph);
            String primOutput = outputDir + "/prim_graph_" + (i + 1) + ".png";
            visualizeGraphWithMST(graph, primResult, "Prim", primOutput);

            // Kruskal visualization
            MSTResult kruskalResult = kruskal.findMST(graph);
            String kruskalOutput = outputDir + "/kruskal_graph_" + (i + 1) + ".png";
            visualizeGraphWithMST(graph, kruskalResult, "Kruskal", kruskalOutput);

            System.out.println("📊 Generated visualizations for graph " + (i + 1));
        }
    }
}