package com.seldom.netty.http;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/6/4 18:31
 */
public class Main {
    public Node connect(Node root) {
        if (root == null) return null;
    }
    public void connect(Node left, Node right) {

    }
}

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}
