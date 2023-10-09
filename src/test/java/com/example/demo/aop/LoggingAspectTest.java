package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import static org.mockito.Mockito.*;

@SpringBootTest
class LoggingAspectTest {

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void logMethodExecutionTime() throws Throwable {
        // Arrange
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Act
        Object result = loggingAspect.logMethodExecutionTime(joinPoint);

        // Assert
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println("수행 시간: " + totalTimeMillis + "ms");

        // Verify
        verify(joinPoint, times(1)).proceed();
    }
}
