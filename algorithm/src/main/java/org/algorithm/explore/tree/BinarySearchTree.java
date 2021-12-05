package org.algorithm.explore.tree;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>二叉搜索树</p>
 *
 * @author mason
 */
@Slf4j
public class BinarySearchTree {

    public static void main(String[] args) {

    }

    public static class TreeNode<T extends Comparable<T>> {
        private final T data;
        private final TreeNode<T> left;
        private final TreeNode<T> right;
        private final TreeNode<T> parent;

        public TreeNode(TreeNode<T> parent, TreeNode<T> left, TreeNode<T> right, T data) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }
}
