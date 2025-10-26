# Assignment 3: Optimization of a City Transportation Network (MST)

**Student:** Aitynbek Nurdaulet

---

## 1. Objective

The objective of this assignment is to model a city's road construction problem as a **Minimum Spanning Tree (MST)** problem. The project implements and analyzes the performance of two classical algorithms—**Prim's** and **Kruskal's**—to determine the minimum set of roads that connect all city districts with the lowest possible total construction cost.

---

## 2. Project Structure & Implementation

The project is developed in Java, utilizing a custom graph data structure and adhering to clean Object-Oriented Programming (OOP) principles (fulfilling the Bonus Section requirement).

* **`algorithms/`**: Contains the MST solving logic.
  * `PrimAlgorithm.java`
  * `KruskalAlgorithm.java` (Implements highly efficient $Union-Find$ with path compression and union by rank).
* **`graph/`**: Custom Graph Data Structure (Bonus Section).
  * `Graph.java`: Stores vertices and edges.
  * `Edge.java`: Represents a weighted edge.
* **`io/`**: Input/Output utilities.
  * `JsonGraphReader.java`: Handles loading complex graph data from JSON files.
  * `ResultWriter.java`: Saves output to `results.json` and `results.csv`.
* **`Main.java`**: Driver class for execution, benchmarking (running multiple times for time consistency), and validation.
* **`InputGenerator.java`**: Utility to create graphs of various sizes and densities.

---

## 3. Input Datasets

Multiple datasets were generated to test both the correctness and the scalability of the algorithms across varying graph complexities. All generated graphs are guaranteed to be connected.

| Graph Type | Graph Count | Vertex Range | Description |
| :--- | :---: | :---: | :--- |
| **Small** | 5 | 5–30 | Used for initial debugging and correctness validation. |
| **Medium** | 10 | 30–300 | Represents moderately sized city networks. |
| **Large** | 10 | 300–1000 | Tests performance on larger, more complex networks. |
| **Extra Large** | 3 | 1000–2000 | Pushes the scalability limits of each algorithm. |

---

## 4. Algorithm Results & Analysis

### 4.1. Summary of Results (Updated)

Each algorithm was run on every graph 3 times, and the execution time and operation count were averaged. The **Total Cost** was identical for Prim's and Kruskal's across all graph instances, confirming the correctness of both implementations.

| Dataset                   | Graphs | Algorithm | Avg Time (ms) | Avg Operations | Total Cost |
|:--------------------------| :---: | :--- | ---: | ---: | ---: |
| `small_graphs.json`       | 5 | **Prim** | 0.28 | 325 | **331** |
| `small_graphs.json`       | 5 | **Kruskal** | 0.19 | 93 | **331** |
| `medium_graphs.json`      | 10 | **Prim** | 0.69 | 5,012 | **1,353** |
| `medium_graphs.json`      | 10 | **Kruskal** | 0.18 | 548 | **1,353** |
| `large_graphs.json`        | 10 | **Prim** | 7.12 | 301,241 | **10,238** |
| `large_graphs.json`       | 10 | **Kruskal** | 2.12 | 5,764 | **10,238** |
| `extra_large_graphs.json` | 3 | **Prim** | 23.75 | 1,499,968 | **26,144** |
| `extra_large_graphs.json` | 3 | **Kruskal**| 3.20 | 15,420 | **26,144** |

### 4.2. Comparative Analysis

The empirical results from the benchmark are decisive, providing a clear insight into the practical performance differences, which are largely determined by the graph representation used.

#### Theoretical Efficiency

* **Kruskal's Algorithm:** Complexity is $O(E \log E)$ or $O(E \log V)$. Performance is dominated by the initial **sorting of all edges**. The subsequent cycle-checking using the optimized $Union-Find$ structure is nearly constant time ($O(\alpha(V))$).
* **Prim's Algorithm:** Theoretical optimal complexity is $O(E \log V)$ when using an optimized data structure (e.g., Priority Queue with adjacency list).

#### Practical Efficiency (Analysis of Updated Results)

The results show that **Kruskal's algorithm significantly outperforms Prim's algorithm** in this project's environment as the graph size grows.

* **Time Advantage:** For the `extra_large` dataset, **Kruskal is about 7.4 times faster** (3.20 ms) than Prim (23.75 ms).
* **Operational Discrepancy:** The difference in operation count is the most telling metric. For the largest graphs, Kruskal performs $\approx 15.4$ thousand operations, while Prim performs $\approx 1.5$ million operations. This **100-fold difference** confirms the severe performance penalty incurred by Prim's implementation.

**The Implementation Bottleneck:**

The `PrimAlgorithm.java` implementation, which uses an edge list (`graph.getEdges()`) instead of a traditional adjacency list, is forced to **iterate over the entire set of edges ($O(E)$) on every iteration** of the main loop (which runs $V$ times). This degrades its performance to $O(V \cdot E)$, which is substantially slower than Kruskal's $O(E \log E)$ on all but the smallest inputs.

---

## 5. Conclusions

1.  **Correctness:** Both algorithms are **correct**, consistently finding the identical minimum cost for the MST.
2.  **Performance:** For the given data structure (a simple list of all edges), **Kruskal's algorithm is the unequivocally superior choice**. Its time complexity scales better ($O(E \log E)$) than the $O(V \cdot E)$ implementation of Prim's.
3.  **Recommendation:**
  * For implementation simplicity and speed with an **edge list representation**, **Kruskal's** is recommended.
  * To make **Prim's** competitive on dense graphs, the core `Graph` structure must be refactored to use an **Adjacency List**.

---

## 6. Testing & Bonus Section

### Testing
Automated **JUnit** tests (`MSTAlgorithmTest.java`) confirm:
* **Cost Equivalence:** The total cost of the MST is identical between Prim's and Kruskal's.
* **Edge Count:** The resulting MST contains $V-1$ edges for connected graphs, validating the spanning property.
* **MSF Handling:** Disconnected graphs result in a Minimum Spanning Forest (MSF), a functionally correct outcome for this problem type.

### Bonus Section (10%)
This project successfully fulfills the bonus requirement for implementing a custom graph data structure:
1.  **Custom Classes:** `graph/Graph.java` and `graph/Edge.java` are the backbone of the data model.
2.  **Integration:** Both `PrimAlgorithm` and `KruskalAlgorithm` receive the custom `Graph` object as input, showcasing a robust and object-oriented architecture.

---

## 7. References
* Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). *Introduction to Algorithms* (3rd ed.). MIT Press.
* GeeksforGeeks. *Prim’s vs Kruskal’s Algorithm – Comparison and Implementation.*
[https://www.geeksforgeeks.org/](https://www.geeksforgeeks.org/)
* Programiz. *Kruskal’s Algorithm.*
[https://www.programiz.com/dsa/kruskal-algorithm](https://www.programiz.com/dsa/kruskal-algorithm)
* Programiz. *Prim’s Algorithm.*
[https://www.programiz.com/dsa/prim-algorithm](https://www.programiz.com/dsa/prim-algorithm)