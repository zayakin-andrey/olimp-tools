package lib.struct;

import java.util.Random;

public abstract class CartesianTree<DATA> {
    private final static Random PRIORITY_GENERATOR = new Random(2517);

    private Node root;

    protected abstract DATA empty();
    protected abstract DATA clone(DATA instance);

    public void insert(Updatable<DATA> data, int key) {
        root = insert(root, new Node(data, key));
    }

    public DATA calculateData(int key) {
        SplitPair split = split(root, key);
        Updatable<DATA> result = split.left == null ? null : split.left.getUpdatable();
        DATA answer = (result == null ? empty() : result.getData());
        answer = clone(answer);
        root = merge(split.left, split.right);
        return answer;
    }

    private SplitPair split(Node root, int key) {
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

    private Node insert(Node root, Node inserting) {
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

    private Node merge(Node left, Node right) {
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

    private Node erase(Node root, int key) {
        if (root.key == key) {
            return merge(root.left, root.right);
        }
        Node result = erase(key < root.key ? root.left : root.right, key);
        update(result);
        return result;
    }

    private void update(Node result) {
        if (result == null) {
            return;
        }
        result.data.update(result.getLeftUpdatable(), result.getRightUpdatable());
    }

    private class SplitPair {
        private Node left;
        private Node right;
    }

    public class Node {
        private final int key;
        private final int priority;
        private Node left;
        private Node right;
        private final Updatable<DATA> data;

        private Node(Updatable<DATA> data, int key) {
            this.key = key;
            this.priority = PRIORITY_GENERATOR.nextInt();
            this.data = data;
        }

        private Updatable<DATA> getUpdatable() {
            return data;
        }

        DATA getLeftUpdatable() {
            return getUpdatable(left);
        }

        DATA getRightUpdatable() {
            return getUpdatable(right);
        }

        private DATA getUpdatable(Node node) {
            return node == null ? empty() : node.data.getData();
        }
    }

    public interface Updatable<T> {
        void update(T left, T right);
        T getData();
    }
}
