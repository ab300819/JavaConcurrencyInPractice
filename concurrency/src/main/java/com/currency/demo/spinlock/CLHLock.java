package com.currency.demo.spinlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * CLH Lock
 *
 * @author mason
 * @see <a href="https://blog.csdn.net/dm_vincent/article/details/79842501"></>
 */
public class CLHLock {

    private static class CLHNode {
        private volatile boolean locked = true;

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }
    }

    private volatile CLHNode tail;

    private final ThreadLocal<CLHNode> currentThreadNode = new ThreadLocal<>();

    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock() {
        CLHNode currentNode = currentThreadNode.get();

        if (currentNode == null) {
            currentNode = new CLHNode();
            currentThreadNode.set(currentNode);
        }

        CLHNode preNode = UPDATER.getAndSet(this, currentNode);

        if (preNode != null) {
            while (preNode.isLocked()) {

            }
        }
    }

    public void unlock() {
        CLHNode node = currentThreadNode.get();
        currentThreadNode.remove();

        if (node == null || !node.isLocked()) {
            return;
        }

        currentThreadNode.remove();

        if (!UPDATER.compareAndSet(this, node, null)) {
            node.setLocked(false);
        }
    }

    public static void main(String[] args) {

        final CLHLock lock = new CLHLock();

        for (int i = 1; i <= 10; i++) {
            new Thread(generateTask(lock, String.valueOf(i))).start();
        }

    }

    private static Runnable generateTask(final CLHLock lock, final String taskId) {
        return () -> {
            lock.lock();

            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }

            System.out.println(String.format("Thread %s Completed", taskId));
            lock.unlock();
        };
    }
}
