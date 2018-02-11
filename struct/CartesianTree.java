package lib.struct;

import java.util.Random;

public class CartesianTree {
    private final static Random PRIORITY_GENERATOR = new Random(2517);

    Node root;

    public void insert(Node inserting) {
        root = insert(root, inserting);
    }

    public long getFunction(int key) {
        SplitPair split = split(root, key);
        long result = (split.left == null ? 0 : split.left.data.getFunction());
        root = merge(split.left, split.right);
        return result;
    }

    private static SplitPair split(Node root, int key) {
        if (root == null) {
            return new SplitPair();
        }
        SplitPair split;
        if (key < root.key) {
            split = split(root.left, key);
            root.left = split.right;
            split.right = root;
        } else {
            split = split(root.right, key);
            root.right = split.left;
            split.left = root;
        }
        update(split.left);
        update(split.right);
        return split;
    }

    private static Node insert(Node root, Node inserting) {
        if (root == null) {
            return inserting;
        }
        if (inserting.priority > root.priority) {
            SplitPair split = split(root, inserting.key);
            inserting.left = split.left;
            inserting.right = split.right;
            update(inserting);
            return inserting;
        }
        boolean leftInsert = inserting.key < root.key;
        Node target = leftInsert ? root.left : root.right;
        Node result = insert(target, inserting);
        if (leftInsert) {
            root.left = result;
        } else {
            root.right = result;
        }
        update(root);
        return root;
    }

    private static Node merge(Node left, Node right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.priority > right.priority) {
            left.right = merge(left.right, right);
            update(left);
            return left;
        }
        right.left = merge(left, right.left);
        update(right);
        return right;
    }

    private static Node erase(Node root, int key) {
        if (root.key == key) {
            return merge(root.left, root.right);
        }
        Node result = erase(key < root.key ? root.left : root.right, key);
        update(result);
        return result;
    }

    private static void update(Node result) {
        if (result == null) {
            return;
        }
        result.data.update(result.getLeftUpdatable(), result.getRightUpdatable());
    }

    private static class SplitPair {
        private Node left;
        private Node right;
    }

    public static class Node {
        private final int key;
        private final int priority;
        private Node left;
        private Node right;
        private final Updatable data;

        public Node(Updatable data, int key) {
            this.key = key;
            this.priority = PRIORITY_GENERATOR.nextInt();
            this.data = data;
        }

        Updatable getLeftUpdatable() {
            return getUpdatable(left);
        }

        private Updatable getUpdatable(Node node) {
            return node == null ? null : node.data;
        }

        Updatable getRightUpdatable() {
            return getUpdatable(right);
        }
    }

    public interface Updatable<T> {
        void update(Updatable<T> left, Updatable<T> right);
        T getData();
        long getFunction();
    }
}
