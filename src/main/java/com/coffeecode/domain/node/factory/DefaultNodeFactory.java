package com.coffeecode.domain.node.factory;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.node.ClientNode;
import com.coffeecode.domain.node.Node;
import com.coffeecode.domain.node.NodeFactory;
import com.coffeecode.domain.node.NodeType;
import com.coffeecode.domain.node.RouterNode;
import com.coffeecode.domain.node.ServerNode;
import com.coffeecode.domain.node.properties.NodeProperties;

@Component
public class DefaultNodeFactory implements NodeFactory {

    @Override
    public Node createNode(NodeType type, String id, Map<String, Object> properties) {
        NodeProperties nodeProps = createProperties(properties);

        return switch (type) {
            case CLIENT -> new ClientNode(id, nodeProps.getDataRate());
            case SERVER -> new ServerNode(id, nodeProps.getCapacity(),
                    nodeProps.getProcessingPower());
            case ROUTER -> new RouterNode(id, nodeProps.getRoutingCapacity());
        };
    }

    @Override
    public List<Node> createNodes(NodeType type, int count, Map<String, Object> properties) {
        return IntStream.range(0, count)
                .mapToObj(i -> createNode(type, UUID.randomUUID().toString(), properties))
                .toList();
    }

    private NodeProperties createProperties(Map<String, Object> props) {
        return NodeProperties.builder()
                .dataRate(getDoubleProperty(props, "dataRate"))
                .capacity(getIntProperty(props, "capacity"))
                .processingPower(getDoubleProperty(props, "processingPower"))
                .routingCapacity(getIntProperty(props, "routingCapacity"))
                .build();
    }

    private Double getDoubleProperty(Map<String, Object> props, String key) {
        Object value = props.get(key);
        return value instanceof Number number ? number.doubleValue() : null;
    }

    private Integer getIntProperty(Map<String, Object> props, String key) {
        Object value = props.get(key);
        return value instanceof Number number ? number.intValue() : null;
    }
}
