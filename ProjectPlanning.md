# **Project Planning: Network Topology Simulation with Java**

## **The Goal**

- **Purpose:** Create an application to simulate and animate in GUI for data flow in a network based on graph topology.
- **Key Functions:**
  1. Generate network nodes (routers, servers, and clients) in batch or small network or singel node step by step
  2. Simulate data packet transmission between nodes.
  3. Calculate the shortest path and optimize network efficiency based on bandwidth and latency.
  4. Test fault tolerance by disabling nodes or connections.
  5. Provide detailed network performance statistics and fault monitoring insights.

---

## **1. Core Components**

### **Node Types**

| Node Type  | Description                            | Example Properties        |
|------------|------------------------------------------|----------------------------|
| Router     | Routes data packets to destination nodes| Routing Table, Bandwidth   |
| Client     | Sends or receives data packets          | ID, Request Frequency      |
| Server     | Provides data or services               | Service Type, Bandwidth    |

### **Edge Types (Connections)**

| Edge Type  | Description                            | Properties                 |
|------------|------------------------------------------|----------------------------|
| Bandwidth  | Maximum data transmission capacity      | Mbps (Mega bits per second)|
| Latency    | Time delay between data transmission    | ms (milliseconds)          |

---

## **2. Simulation Features**

- **Data Packet Flow:** Simulate data transmission from client to server with selected routes.
- **Route Optimization:** Use **Dijkstra’s Algorithm** to find the shortest path based on latency.
- **Fault-Tolerance:** Test network resilience by disabling nodes or connections.
- **Performance Metrics:** Calculate network efficiency, including average latency and bandwidth utilization.
- **Fault Monitoring:** Continuously monitor the health of network components and detect failures in nodes or connections.
- **Statistics Module:**
  - Track simulation metrics, including:
    - Number of transmitted packets.
    - Average latency per packet.
    - Bandwidth usage across connections.
    - Fault rates and recovery times.

---

## **3. Calculations and Algorithms**

- **Shortest Path Algorithms:**
  - **Dijkstra’s Algorithm** for latency-based routing.
  - **Floyd-Warshall Algorithm** for comprehensive path evaluation.

- **Load Balancing Strategy:**
  - Distribute data load based on node-router bandwidth.
  - Prevent node overload.

- **Network Graph Validation:**
  - Ensure all nodes are connected with valid edges.

- **Fault Matrices Calculation:**
  - Maintain a matrix to track fault occurrences and their impact on the overall network efficiency.

---

## **4. Implementation Phases**

### **Phase 1: Basic Structure**

1. **Node Creation**
   - Create `Node` class and its subclasses (`RouterNode`, `ClientNode`, `ServerNode`).
   - Assign properties such as bandwidth and latency to edges between nodes.
2. **Graph Structure**
   - Create `NetworkGraph` class to store nodes and edges.
   - Implement methods to add nodes and edges to the graph.
3. **Validation**
   - Ensure nodes and edges have valid connections.

### **Phase 2: Simulation Features**

1. **Data Packet Flow**
   - Implement logic for data packet transmission between nodes.
   - Record routes taken and total transmission time for each packet.
2. **Routing Algorithm**
   - Integrate **Dijkstra’s Algorithm** for shortest path computation.
   - Evaluate network performance based on selected routes.
3. **Efficiency Metrics**
   - Calculate average latency and bandwidth utilization.
4. **Fault Monitoring Module**
   - Implement continuous health monitoring for nodes and edges.
   - Log fault events and notify simulation users.
5. **Statistics Module Integration**
   - Collect and display key metrics such as packet counts, average latency, and bandwidth usage.

### **Phase 3: Optimization and Fault Tolerance**

1. **Route Optimization**
   - Add simulation option with **Floyd-Warshall Algorithm** for larger networks.
   - Compare execution times of different algorithms.
2. **Fault Tolerance**
   - Implement node or edge failure simulation.
   - Evaluate the impact on network efficiency.
   - Maintain a fault matrix for tracking and analyzing network fault patterns.
3. **Performance Testing**
   - Test simulations for large networks (> 1000 nodes).
   - Evaluate memory usage and execution time.
4. **Statistics Reporting**
   - Provide detailed reports on network performance and fault trends.

---

## **5. Testing Strategy**

- **Unit Testing:**
  - Test each class and method to ensure they work as expected.
- **Integration Testing:**
  - Ensure the simulation runs smoothly with all components connected.
- **Performance Testing:**
  - Evaluate algorithm execution time and memory consumption for large networks.
- **Fault Injection Testing:**
  - Simulate faults to test the system’s fault detection and recovery capabilities.
- **Statistics Verification:**
  - Ensure accurate calculation and display of network metrics.

---

## **6. Continuous Improvement Guidelines**

- **Clean Code Practices:**
  - Apply **SOLID** principles in class design.
- **Performance Optimization:**
  - Explore more efficient algorithms if performance is unsatisfactory.
- **User Interaction Enhancements:**
  - Add simulation options for different scenarios through user inputs.
- **Fault Analysis Improvements:**
  - Provide visual representations of fault matrices and network health indicators.
- **Statistics Visualization:**
  - Include charts or graphs for better understanding of simulation metrics.

---

This structured project outline should provide a clear and actionable roadmap for developing your network topology simulation in Java. Let me know if you need assistance in any specific phase!

1. Graph Data Structure
   - Node storage
   - Edge management
   - Thread-safe operations
   - Immutable views

2. Path Finding
   - DFS for path existence
   - Dijkstra for shortest path
   - Path validation
   - Result representation
