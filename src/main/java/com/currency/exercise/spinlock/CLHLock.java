package com.currency.exercise.spinlock;

import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class CLHLock {

    public static class CLHNode {
        private volatile boolean isLocked = true;
    }

    private volatile CLHNode tail;

    private static final ThreadLocal<CLHNode> LOCAL = new ThreadLocal<>();

    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock() {
        CLHNode node = new CLHNode();
        LOCAL.set(node);

        CLHNode preNode = UPDATER.getAndSet(this, node);

        if (preNode != null) {
            while (preNode.isLocked) {

            }
        }
    }

    public void unlock() {
        CLHNode node = LOCAL.get();

        if (!UPDATER.compareAndSet(this, node, null)) {
            node.isLocked = false;
        }
    }
}
