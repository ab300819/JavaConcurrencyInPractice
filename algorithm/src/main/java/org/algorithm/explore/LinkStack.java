package org.algorithm.explore;

/**
 * <p>链表实现栈</p>
 *
 * @author mason
 * @date 2022/8/21 23:43
 */
public class LinkStack implements Stack {

    private Node top = null;

    @Override
    public boolean push(String data) {
        Node newNode = new Node(data);
        if (top == null) {
            top = newNode;
        } else {
            newNode.setNext(top);
            top = newNode;
        }
        return true;
    }

    @Override
    public String pop() {
        if (top == null) {
            return null;
        }
        Node popNode = top;
        top = top.getNext();
        return popNode.getData();
    }

    public static class Node {
        private String data;
        private Node next;

        public Node(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
