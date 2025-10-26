package io;

import model.MSTResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.util.*;

public class ResultWriter {
    private final ObjectMapper objectMapper;

    public ResultWriter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void writeJsonResults(List<MSTResult> results, String filePath) throws IOException {
        Map<String, Object> output = new HashMap<>();
        output.put("results", results);
        objectMapper.writeValue(new File(filePath), output);
    }

    public void writeCsvResults(List<MSTResult> results, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("id,algorithm_name,vertex_count,execution_time,operation_count,total_cost");

            int id = 1;
            for (MSTResult result : results) {
                writer.printf("%d,%s,%d,%d,%d,%d\n",
                        id++,
                        result.getAlgorithmName(),
                        result.getVertexCount(),
                        result.getExecutionTime(),
                        result.getOperationCount(),
                        result.getTotalCost()
                );
            }
        }
    }
}