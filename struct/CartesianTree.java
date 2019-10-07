package lib.struct;

import java.util.Random;
import java.util.TreeSet;

public abstract class
CartesianTree<DATA extends CartesianTree.Sizable> {
    private final static Random PRIORITY_GENERATOR = new Random(2517);

    private Node root;

    protected abstract DATA empty();
    protected abstract DATA clone(DATA instance);

    public void insert(Updatable<DATA> data, int key) {
        root = insert(root, new Node(data, key));
    }


    /**
     * Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
     */
    public TreeNode ceiling(int key) {
        return ceiling(root, key, null);
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     */
    public TreeNode floor(int key) {
        return floor(root, key, null);
    }

    private TreeNode floor(Node root, int key, TreeNode bestResult) {
        if (root == null) {
            return bestResult;
        }
        if (root.key <= key) {
            bestResult = bestResult == null || bestResult.key < root.key ?
                    new TreeNode(root.data.getData(), root.key) :
                    bestResult;
        }
        if (root.key == key) {
            return bestResult;
        }
        if (root.key < key) {
            return floor(root.left, key, bestResult);
        }
        return ceiling(root.right, key, bestResult);
    }

    private TreeNode ceiling(Node root, int key, TreeNode bestResult) {
        if (root == null) {
            return bestResult;
        }
        if (root.key >= key) {
            bestResult = bestResult == null || bestResult.key > root.key ?
                    new TreeNode(root.data.getData(), root.key) :
                    bestResult;
        }
        if (root.key == key) {
            return bestResult;
        }
        if (root.key < key) {
            return ceiling(root.right, key, bestResult);
        }
        return ceiling(root.left, key, bestResult);
    }

    /**
     * returns element in 0-indexed base
     * @param index
     * @return
     */
    public TreeNode get(int index) {
        TreeSet<Integer> tree = new TreeSet<>();
        return getKth(root, index + 1);
    }

    private TreeNode getKth(Node root, int index) {
        if (root == null) {
            throw new IllegalArgumentException("tree contains less elements than index: " + index);
        }
        int leftSize = root.getLeftUpdatable().getSize();
        if (leftSize + 1 == index) {
            return new TreeNode(clone(root.data.getData()), root.key);
        }
        if (leftSize >= index) {
            return getKth(root.left, index);
        }
        return getKth(root.right, index - leftSize - 1);
    }

    public DATA calculateData(int key) {
        SplitPair split = split(root, key);
        DATA result = split.left == null ? empty() : clone(split.left.getUpdatable().getData());
        root = merge(split.left, split.right);
        return result;
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
        boolean leftErase = key < root.key;
        Node target = leftErase ? root.left : root.right;
        Node result = erase(target, key);
        if (leftErase) {
            root.left = result;
        } else {
            root.right = result;
        }
        update(root);
        return root;
    }

    private void update(Node result) {
        if (result == null) {
            return;
        }
        result.data.update(result.getLeftUpdatable(), result.getRightUpdatable());
    }

    public void erase(int key) {
        root = erase(root, key);
    }

    public class TreeNode {
        public final DATA data;
        public final int key;

        TreeNode(DATA data, int key) {
            this.data = data;
            this.key = key;
        }
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

    public interface Updatable<T> extends Sizable {
        void update(T left, T right);
        T getData();
    }

    public interface Sizable {
        int getSize();
    }
}
