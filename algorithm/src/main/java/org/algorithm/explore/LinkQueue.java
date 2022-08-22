package org.algorithm.explore;

/**
 * <p>队列链表实现</p>
 *
 * @author mason
 * @date 2022/8/22 23:25
 */
public class LinkQueue implements Queue {

    private Node top;

    @Override
    public boolean push(String data) {
        Node node = new Node(data);
        if (top == null) {
            top = node;
        } else {
            Node head = top;
            while (head.next != null) {
                head = head.next;
            }
            head.next = node;
        }
        return true;
    }

    @Override
    public String take() {
        if (top == null) {
            return null;
        }
        Node node = top;
        top = top.next;
        return node.data;
    }

    public static class Node {
        private String data;
        private Node next;

        public Node(String data) {
            this.data = data;
        }
    }
}
