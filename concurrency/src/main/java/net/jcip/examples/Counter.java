package net.jcip.examples;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Counter
 * <p/>
 * Simple thread-safe counter using the Java monitor pattern
 *
 * @author Brian Goetz and Tim Peierls
 */
@Slf4j
@ThreadSafe
public final class Counter {

    @GuardedBy("this")
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE)
            throw new IllegalStateException("counter overflow");
        log.info("{}-current value:{}", Thread.currentThread().getName(), value);
        return ++value;
    }
}
