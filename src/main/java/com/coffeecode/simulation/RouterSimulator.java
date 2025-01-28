// package com.coffeecode.simulation;

// package com.netsim.simulation;

// import java.util.List;

// import com.netsim.algorithms.RoutingStrategy;

// public class RouterSimulator {
//     private RoutingStrategy routingStrategy;

//     public RouterSimulator(RoutingStrategy routingStrategy) {
//         this.routingStrategy = routingStrategy;
//     }

//     public List<Node> routePacket(Node source, Node destination) {
//         return routingStrategy.findBestPath(source, destination);
//     }

//     public void processIncomingPacket(Node source, Node destination) {
//         List<Node> path = routePacket(source, destination);
//         // Logic to handle the packet flow through the determined path
//         System.out.println("Packet routed from " + source.getId() + " to " + destination.getId() + " via " + path);
//     }
// }
