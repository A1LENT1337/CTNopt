# 🏙️ City Transportation Network Optimization
## Minimum Spanning Tree Algorithms Analysis

**Course:** Design and Analysis of Algorithms  
**Student:** Nurdaulet Aitynbek 
**Repository:** [github.com/A1LENT1337/CTNopt](https://github.com/A1LENT1337/CTNopt)

---

## 🎯 Project Overview

This project implements and compares two fundamental algorithms for finding Minimum Spanning Trees (MST) in weighted undirected graphs representing city transportation networks. The goal is to determine the most efficient way to connect all city districts with minimal road construction costs.

### Problem Context
- **Vertices** = City districts
- **Edges** = Potential roads between districts
- **Edge Weights** = Road construction costs
- **Objective** = Connect all districts with minimum total cost

---

## 🛠️ Implementation Details

### Algorithms Implemented
1. **Prim's Algorithm** - Greedy approach building MST from starting vertex
2. **Kruskal's Algorithm** - Edge-based approach using Union-Find data structure

### Technical Stack
- **Language:** Java 23
- **Build Tool:** Maven
- **Dependencies:** Jackson (JSON processing), JUnit (testing)
- **Data Format:** JSON for input, CSV/JSON for output

### Project Architecture
````
src/
├── algorithms/ # MST algorithm implementations
├── graph/ # Graph data structures
├── model/ # Data transfer objects
├── io/ # File I/O operations
├── InputGenerator.java # Input json generator
└── Main.java # Application entry point
````


