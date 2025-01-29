package com.coffeecode.domain.node.factory;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.BaseNodeProperties;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

@Component
public class DefaultNodeFactory implements NodeFactory {

    @Override
    public Node createNode(BaseNodeProperties props) {
        return switch (props.getType()) {
            case CLIENT -> createClientNode((ClientNodeProperties) props);
                        case SERVER -> createServerNode((ServerNodeProperties) props);
                        case ROUTER -> createRouterNode((RouterNodeProperties) props);
                    };
                }
            
                private Object createClientNode(ClientNodeProperties props) {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'createClientNode'");
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
