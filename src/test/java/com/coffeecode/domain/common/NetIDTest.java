package com.coffeecode.domain.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import com.coffeecode.domain.validation.ValidationException;

class NetIDTest {

    @Test
    void create_ShouldGenerateUniqueNames() {
        NetID id1 = NetID.create("router");
        NetID id2 = NetID.create("router");

        assertNotEquals(id1.getName(), id2.getName());
        assertTrue(id1.getName().startsWith("router-"));
        assertTrue(id2.getName().startsWith("router-"));
    }

    @Test
    void create_WithNullType_ShouldThrow() {
        assertThrows(ValidationException.class, () -> NetID.create(null));
    }

    @Test
    void create_WithEmptyType_ShouldThrow() {
        assertThrows(ValidationException.class, () -> NetID.create(""));
    }

    @Test
    void of_WithValidInput_ShouldCreate() {
        UUID uuid = UUID.randomUUID();
        NetID id = NetID.of(uuid, "test-name");

        assertEquals(uuid, id.getId());
        assertEquals("test-name", id.getName());
    }

    @Test
    void create_ConcurrentAccess_ShouldBeThreadSafe() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                NetID.create("concurrent");
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();

        NetID lastId = NetID.create("concurrent");
        assertTrue(lastId.getName().contains("-" + (threadCount + 1)));
    }
}
