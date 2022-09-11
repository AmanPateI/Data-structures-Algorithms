import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Aman Patel
 * @version 1.0
 * @userid apatel734
 * @GTID 903379254
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are index able like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot make a tree with null data");
        }
        for (T node : data) {
            add(node);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        root = addHelper(root, data);
    }
    /**
     * finds the next location for a leaf to be added
     * @param curr BSTNode
     * @param data data to be added
     * @return added node
     */
    private BSTNode<T> addHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return curr;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        BSTNode<T> dummy = new BSTNode<T>(data);
        root = removeHelper(root, data, dummy);
        size--;
        return dummy.getData();
    }
    /**
     * removes node from bst
     * @param curr current node position
     * @param data data to be removed
     * @param dummy dummy node
     * @return dummy node that was removed
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("data is null");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        } else {
            if (curr.getLeft() != null && curr.getRight() != null) {
                BSTNode<T> d2 = new BSTNode<T>(data);
                curr.setRight(removeSuccessor(curr.getRight(), d2));
                curr.setData(d2.getData());
            } else if (curr.getLeft() != null) {
                return curr.getLeft();
            } else if (curr.getRight() != null) {
                return curr.getRight();
            } else {
                return null;
            }
        }
        return curr;
    }
    /**
     * removes bst node with successor
     * @param curr current node position
     * @param dummyNode dummy node to be removed
     * @return the node that was removed
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummyNode) {
        if (curr.getLeft() == null) {
            dummyNode.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummyNode));
        }
        return curr;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null!");
        }
        BSTNode<T> output = getHelper(root, data);
        return output.getData();
    }
    /**
     * adds data to list
     * @param curr current bst node
     * @param data value
     * @return current value
     */
    private BSTNode<T> getHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the BST!");
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(curr.getRight(), data);
        } else {
            return curr;
        }
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        return containsHelper(root, data);
    }

    /**
     * adds data to list
     * @param curr current bst node
     * @param data value
     * @return arraylist
     */
    private boolean containsHelper(BSTNode<T> curr, T data) {
        if (curr != null) {
            if (curr.getData() == data) {
                return true;
            } else if (data.compareTo(curr.getData()) < 0) {
                return containsHelper(curr.getLeft(), data);
            } else if (data.compareTo(curr.getData()) > 0) {
                return containsHelper(curr.getRight(), data);
            }
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> output = new ArrayList<T>();
        output = preOrderHelper(root, output);
        return output;
    }

    /**
     * adds data to list
     * @param curr current bst node
     * @param bstArray arraylsit of bST
     * @return arraylist
     */
    private ArrayList<T> preOrderHelper(BSTNode<T> curr, ArrayList<T> bstArray) {
        if (curr != null) {
            bstArray.add(curr.getData());
            preOrderHelper(curr.getLeft(), bstArray);
            preOrderHelper(curr.getRight(), bstArray);
        }
        return bstArray;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> output = new ArrayList<T>();
        return inOrderHelper(root, output);
    }

    /**
     * adds data to list
     * @param curr current bst node
     * @param bstArray arraylsit of bST
     * @return arraylist
     */
    private List<T> inOrderHelper(BSTNode<T> curr, ArrayList<T> bstArray) {
        if (curr != null) {
            inOrderHelper(curr.getLeft(), bstArray);
            bstArray.add(curr.getData());
            inOrderHelper(curr.getRight(), bstArray);
        }
        return bstArray;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> output = new ArrayList<T>();
        output = postOrderHelper(root, output);
        return output;
    }

    /**
     * adds output to list
     * @param curr current bst node
     * @param bstArray array of bst
     * @return array
     */
    private ArrayList<T> postOrderHelper(BSTNode<T> curr, ArrayList<T> bstArray) {
        if (curr != null) {
            postOrderHelper(curr.getLeft(), bstArray);
            postOrderHelper(curr.getRight(), bstArray);
            bstArray.add(curr.getData());
        }
        return bstArray;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> lvlOrder = new ArrayList<T>();
        LinkedList<BSTNode<T>> nodeCurr = new LinkedList<BSTNode<T>>();
        nodeCurr.add(root);
        while (!nodeCurr.isEmpty()) {
            BSTNode<T> curr = nodeCurr.removeFirst();
            lvlOrder.add(curr.getData());
            if (curr.getLeft() != null) {
                nodeCurr.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                nodeCurr.add(curr.getRight());
            }
        }
        return lvlOrder;
    }


    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightHelper(root);
    }
    /**
     * height of root
     * @param curr current bst node
     * @return heigth of the root
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return Math.max(heightHelper(curr.getRight()),
                    heightHelper(curr.getLeft())) + 1;
        }
    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Removes all elements strictly greater than the passed in data.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * pruneGreaterThan(27) should remove 37, 40, 50, 75. Below is the resulting BST
     *             25
     *            /
     *          12
     *         /  \
     *        10  15
     *           /
     *          13
     *
     * Should have a running time of O(log(n)) for balanced tree. O(n) for a degenerated tree.
     *
     * @throws java.lang.IllegalArgumentException if data is null
     * @param data the threshold data. Elements greater than data should be removed
     * @param tree the root of the tree to prune nodes from
     * @param <T> the generic typing of the data in the BST
     * @return the root of the tree with all elements greater than data removed
     */
    public static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThan(BSTNode<T> tree, T data) {
        return pruneGreaterThanHelper(tree, data);
    }
    /**
     * Hlper for pruneGreatehrThan
     * @param curr current node
     * @param data comapred data
     * @param <T> generatic typing of data in bst
     * @return left or right parent node of node
     */
    private static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThanHelper(BSTNode<T> curr, T data) {

        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(data) > 0) {
            return pruneGreaterThanHelper(curr.getLeft(), data);
        } else {
            curr.setLeft(pruneGreaterThanHelper(curr.getLeft(), data));
            curr.setRight(pruneGreaterThanHelper(curr.getRight(), data));
            return curr;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
