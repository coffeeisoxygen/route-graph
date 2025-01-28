// package com.coffeecode.utils;

// import org.apache.commons.math3.ml.neuralnet.Network;

// import com.coffeecode.core.Edge;
// import com.coffeecode.core.Node;

// public class NetworkValidator {

// public static boolean validateNetworkStructure(Network network) {
// if (network == null) {
// return false;
// }

// for (Node node : network.getNodes()) {
// if (node.getConnections() == null || node.getConnections().isEmpty()) {
// return false;
// }
// }

// for (Edge edge : network.getEdges()) {
// if (edge.getSource() == null || edge.getDestination() == null) {
// return false;
// }
// }

// return true;
// }

// public static boolean validateNode(Node node) {
// return node != null && node.getId() != null;
// }

// public static boolean validateEdge(Edge edge) {
// return edge != null && edge.getSource() != null && edge.getDestination() !=
// null;
// }
// }
