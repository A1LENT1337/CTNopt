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

| Dataset | Graphs | Algorithm | Avg Time (ms) | Avg Operations | Total Cost |
| :--- | :---: | :--- | ---: | ---: | ---: |
| `small_graphs.json` | 5 | **Prim** | 0.05 | 135 | **117** |
| `small_graphs.json` | 5 | **Kruskal** | 0.02 | 51 | **117** |
| `medium_graphs.json` | 10 | **Prim** | 0.38 | 7,133 | **2,050** |
| `medium_graphs.json` | 10 | **Kruskal** | 0.11 | 671 | **2,050** |
| `large_graphs.json` | 10 | **Prim** | 1.92 | 196,020 | **6,228** |
| `large_graphs.json` | 10 | **Kruskal** | 0.44 | 4,321 | **6,228** |
| `extra_large_graphs.json`| 3 | **Prim** | 2.76 | 815,733 | **13,583** |
| `extra_large_graphs.json`| 3 | **Kruskal**| 1.01 | 10,776 | **13,583** |

---

### 4.2. Comparative Analysis

The empirical results from the benchmark are decisive and provide a clear insight into the practical performance of these specific implementations.

#### Theoretical Efficiency

* **Kruskal's Algorithm:** The theoretical complexity is $O(E \log E)$ or $O(E \log V)$. The performance is dominated by the time it takes to **sort all edges** by weight. The $Union-Find$ operations (to detect cycles) are, with optimizations, nearly constant time ($O(\alpha(V))$).
* **Prim's Algorithm:** The theoretical complexity is typically $O(E \log V)$ when implemented with a priority queue and adjacency list.

#### Practical Efficiency (Analysis of Results)

The results confirm that **Kruskal's algorithm dramatically outperforms Prim's algorithm** in this implementation, especially as the graph size increases.

* **Time Advantage:** On the `extra_large` dataset, **Kruskal is approximately 2.7 times faster** (1.01 ms) than Prim (2.76 ms).
* **Operational Discrepancy:** The difference in operations is vast. For the largest dataset, Kruskal performs **10,776** operations, while Prim performs **815,733**—a **~75-fold difference** in the core algorithmic work required.

**Why this discrepancy?**

As detailed in the previous analysis, the severe performance gap stems from the specific implementation of Prim's algorithm:

1.  **Kruskal's Efficiency:** It performs the expected $O(E \log E)$ due to efficient edge sorting and the use of the optimized **$Union-Find$** structure.
2.  **Prim's Bottleneck:** The Prim's implementation uses an **edge list** (`graph.getEdges()`) rather than an optimized adjacency list. Consequently, it is forced to **iterate over the entire set of edges ($O(E)$) on every step** of adding a new vertex (which runs $V$ times). This results in a practical time complexity of **$O(V \cdot E)$**, which is significantly slower and less scalable than Kruskal's $O(E \log E)$.

---

## 5. Conclusions

1.  **Correctness:** Both algorithms are **correct**, consistently finding the identical minimum cost for the MST.
2.  **Performance:** For the given data structure (a simple list of all edges), **Kruskal's algorithm is the unequivocally superior choice**. Its better asymptotic complexity for this structure ($O(E \log E)$) ensures far better scalability than the $O(V \cdot E)$ complexity observed in the Prim implementation.

---

## 6. Testing & Bonus Section

### Testing
Automated **JUnit** tests (`MSTAlgorithmTest.java`) confirm:
* **Cost Equivalence:** The total cost of the MST is identical for both algorithms.
* **Edge Count:** The resulting MST contains $V-1$ edges for connected graphs.

### Bonus Section (10%)
This project successfully fulfills the bonus requirement for implementing a custom graph data structure:
1.  **Custom Classes:** `graph/Graph.java` and `graph/Edge.java` are the backbone of the data model.
2.  **Integration:** Both algorithms receive the custom `Graph` object as input, demonstrating a robust and object-oriented architecture.

---

## 7. References
* GeeksforGeeks. *Prim’s vs Kruskal’s Algorithm – Comparison and Implementation.*
[https://www.geeksforgeeks.org/](https://www.geeksforgeeks.org/)
* Programiz. *Kruskal’s Algorithm.*
[https://www.programiz.com/dsa/kruskal-algorithm](https://www.programiz.com/dsa/kruskal-algorithm)
* Programiz. *Prim’s Algorithm.*
[https://www.programiz.com/dsa/prim-algorithm](https://www.programiz.com/dsa/prim-algorithm)