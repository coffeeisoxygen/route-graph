package com.coffeecode.domain.topology.core;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

@ExtendWith(MockitoExtension.class)
class NetGraphTest {
    @Mock
    private NetNode sourceNode;
    @Mock
    private NetNode targetNode;
    @Mock
    private NetEdge edge;

    private NetGraph graph;

    @BeforeEach
    void setUp() {
        graph = new NetGraph();
        lenient().when(sourceNode.getIdentity()).thenReturn(Identity.create("source"));
        lenient().when(targetNode.getIdentity()).thenReturn(Identity.create("target"));
        lenient().when(edge.getSource()).thenReturn(sourceNode);
        lenient().when(edge.getDestination()).thenReturn(targetNode);
    }

    @Test
    void shouldAddAndRemoveNode() {
        // When
        graph.addNode(sourceNode);

        // Then
        assertTrue(graph.containsNode("source"));
        assertEquals(1, graph.getNodes().size());

        // When
        boolean removed = graph.removeNode("source");

        // Then
        assertTrue(removed);
        assertFalse(graph.containsNode("source"));
    }

    @Test
    void shouldManageEdges() {
        // Given
        graph.addNode(sourceNode);
        graph.addNode(targetNode);

        // When
        graph.addEdge(edge);

        // Then
        assertEquals(1, graph.getEdges().size());
        assertEquals(1, graph.getNodeEdges("source").size());
    }

    @Test
    void shouldHandleConcurrentAccess() {
        // Given
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    NetNode node = mock(NetNode.class);
                    when(node.getIdentity()).thenReturn(Identity.create("node" + index));
                    graph.addNode(node);
                } finally {
                    latch.countDown();
                }
            });
        }

        // Then
        await().atMost(5, TimeUnit.SECONDS).until(() -> latch.getCount() == 0);
        assertEquals(threadCount, graph.getNodes().size());
    }
}
