// package com.coffeecode.infrastructure.validation;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Component;

// import com.coffeecode.domain.topology.NetworkTopology;
// import com.coffeecode.infrastructure.validation.exception.NetworkValidationException;

// @Component
// public class BaseNetworkValidator implements NetworkValidator {
//     private final List<ValidationError> errors = new ArrayList<>();

//     @Override
//     public boolean isValid(NetworkTopology topology) {
//         return validate(topology).stream()
//                 .noneMatch(error -> error.getSeverity() == ValidationError.Severity.ERROR);
//     }

//     @Override
//     public List<ValidationError> validate(NetworkTopology topology) {
//         errors.clear();
//         validateNull(topology);
//         validateNodes(topology);
//         validateConnections(topology);
//         validateTopologyStructure(topology);
//         return new ArrayList<>(errors);
//     }

//     private void validateNull(NetworkTopology topology) {
//         if (topology == null) {
//             throw new NetworkValidationException("NULL_TOPOLOGY", "Topology is null");
//         }
//     }

//     private void validateNodes(NetworkTopology topology) {
//         if (topology.getNodes().isEmpty()) {
//             addError("NO_NODES", "Network has no nodes", ValidationError.Severity.ERROR);
//         }
//     }

//     private void validateConnections(NetworkTopology topology) {
//         topology.getEdges().forEach(edge -> {
//             if (!edge.isValid()) {
//                 addError("INVALID_EDGE",
//                         String.format("Invalid edge between %s and %s",
//                                 edge.getSource().getId(),
//                                 edge.getDestination().getId()),
//                         ValidationError.Severity.ERROR);
//             }
//         });
//     }

//     private void validateTopologyStructure(NetworkTopology topology) {
//         topology.getNodes().values().forEach(node -> {
//             if (node.getEdges().isEmpty()) {
//                 addError("ISOLATED_NODE",
//                         "Node " + node.getId() + " is isolated",
//                         ValidationError.Severity.WARNING);
//             }
//         });
//     }

//     private void addError(String code, String message, ValidationError.Severity severity) {
//         errors.add(new ValidationError(code, message, severity));
//     }
// }
