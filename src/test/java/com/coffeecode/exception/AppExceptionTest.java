
package com.coffeecode.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppExceptionTest {

    private static final Logger logger = LoggerFactory.getLogger(AppException.class);

    @Test
    public void testAppExceptionWithMessage() {
        String errorMessage = "Test error message";
        AppException exception = new AppException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());

        Logger mockLogger = Mockito.mock(Logger.class);
        mockLogger.error(errorMessage);
        verify(mockLogger).error(errorMessage);
    }

    @Test
    public void testAppExceptionWithMessageAndCause() {
        String errorMessage = "Test error message";
        Throwable cause = new Throwable("Cause of the error");
        AppException exception = new AppException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());

        Logger mockLogger = Mockito.mock(Logger.class);
        mockLogger.error(errorMessage, cause);
        verify(mockLogger).error(errorMessage, cause);
    }
}
