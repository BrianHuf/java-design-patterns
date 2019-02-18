import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

class Node<T> {
    public T value;

    public Node<T> left, right, parent;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;

        left.parent = right.parent = this;
    }

    public Iterator<Node<T>> preOrder() {
        return new PreorderIterator<>(this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

class PreorderIterator<T> implements Iterator<Node<T>> {
    // https://codereview.stackexchange.com/questions/41844/iterator-for-binary-tree-pre-in-and-post-order-iterators
    private final Deque<Node<T>> stack;

    public PreorderIterator(Node<T> root) {
        stack = new ArrayDeque<>();
        stack.add(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Node<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more nodes remain to iterate");
        }

        final Node<T> node = stack.pop();

        if (node.right != null) {
            stack.push(node.right);
        }

        if (node.left != null) {
            stack.push(node.left);
        }

        return node;
    }
}

class DemoIterator {
    public static void main(String[] args) {
        Node<Integer> n1 = new Node<>(1);
        Node<Integer> n2 = new Node<>(2);
        Node<Integer> n3 = new Node<>(3, n1, n2);

        Iterator<Node<Integer>> it = n3.preOrder();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}