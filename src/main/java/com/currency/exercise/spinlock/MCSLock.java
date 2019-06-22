package com.currency.exercise.spinlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * MCS Lock
 *
 * @author mason
 * @see <a href="https://blog.csdn.net/dm_vincent/article/details/79783104"></a>
 */
public class MCSLock {

    public static class MCSNode {
        volatile MCSNode next;
        volatile boolean isLocked = true;
    }

    private volatile MCSNode queue;

    private final ThreadLocal<MCSNode> currentThreadNode = new ThreadLocal<>();

    private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER =
            AtomicReferenceFieldUpdater
                    .newUpdater(MCSLock.class, MCSNode.class, "queue");

    public void lock() {
        MCSNode currentNode = currentThreadNode.get();
        if (currentNode == null) {
            currentNode = new MCSNode();
            currentThreadNode.set(currentNode);
        }

        MCSNode preNode = UPDATER.getAndSet(this, currentNode);
        if (preNode != null) {
            preNode.next = currentNode;
            while (currentNode.isLocked) {

            }
        }

    }

    public void unlock() {
        MCSNode currentNode = currentThreadNode.get();
        currentThreadNode.remove();

        if (currentNode == null || currentNode.isLocked) {
            return;
        }

        if (currentNode.next == null && !UPDATER.compareAndSet(this, currentNode, null)) {
            while (currentNode.next == null) {

            }
        }

        if (currentNode.next != null) {
            currentNode.next.isLocked = false;
            currentNode.next = null;
        }
    }

}
