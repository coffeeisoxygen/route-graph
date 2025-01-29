package com.coffeecode.domain.factory.node;

import java.util.List;

import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.node.base.NetNode;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

public interface NetNodeFactory {
    NetNode createRouter(RouterNodeProperties props);

    NetNode createClient(ClientNodeProperties props);

    NetNode createServer(ServerNodeProperties props);

    List<NetNode> createBatch(NetNodeType type, int count, Object properties);
}
