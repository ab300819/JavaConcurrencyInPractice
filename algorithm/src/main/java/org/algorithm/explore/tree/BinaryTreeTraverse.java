package org.algorithm.explore.tree;

public class BinaryTreeTraverse {

    public static void main(String[] args) {

    }

    private void preOrderTraverse(BinaryTree root) {
        if (root == null) {
            return;
        }
        System.out.printf(root.getData());
        preOrderTraverse(root.getLeft());
        preOrderTraverse(root.getRight());
    }

    private void inOrderTraverse(BinaryTree root) {
        if (root == null) {
            return;
        }
        inOrderTraverse(root.getLeft());
        System.out.printf(root.getData());
        inOrderTraverse(root.getRight());
    }

}
