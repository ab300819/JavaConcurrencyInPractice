package net.jcip.examples;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author mason
 */
@Slf4j
public class CounterTest {

    @Test
    public void TestCounter() {
        Counter counter = new Counter();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    counter.increment();
                }
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            log.info("Thread pool not finish");
        }

        assertThat(counter.getValue(), equalTo(500L));
    }

}
