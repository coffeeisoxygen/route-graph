package com.coffeecode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class AppTest {

    @Test
    void testMain() {
        // Mocking SwingUtilities.invokeLater
        SwingUtilities.invokeLater(Mockito.any(Runnable.class));

        // Running the main method
        App.main(new String[] {});

        // Verifying that invokeLater was called
        verify(SwingUtilities.class);
    }

    @Test
    void testMainWithException() {
        // Mocking SwingUtilities.invokeLater to throw an exception
        doThrow(new RuntimeException("Test Exception")).when(SwingUtilities.class);

        // Running the main method
        App.main(new String[] {});

        // Verifying that the error was logged
        verify(log).error(eq("Error starting application"), any(RuntimeException.class));
    }
}
