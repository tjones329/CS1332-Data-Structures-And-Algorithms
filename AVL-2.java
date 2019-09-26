import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Tyler Jones
 * @userid tjones329
 * @GTID 903283773
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        for (T o: data) {
            if (o == null) {
                throw new IllegalArgumentException("an element in the "
                        + "Collection was null");
            }
            add(o);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        root = addRecursive(root, data);
    }

    /**
     * Helper method to add data to an AVL
     *
     * @param current the current node we are checking
     * @param data the data we want to input into the AVL
     * @return a AVLNode representing the current node getting returned
     */
    private AVLNode<T> addRecursive(AVLNode<T> current, T data) {
        if (current == null) {
            current = new AVLNode<T>(data);
            size++;
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(addRecursive(current.getRight(), data));
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(addRecursive(current.getLeft(), data));
        }

        calculateBF(current);
        return checkForRotation(current);

    }

    /**
     *
     * @param curr the current node being rotated right
     * @return a node that is getting linked back to the tree
     */
    private AVLNode<T> rightRotation(AVLNode<T> curr) {
        AVLNode<T> tmp = curr.getLeft();
        curr.setLeft(tmp.getRight());
        tmp.setRight(curr);

        calculateBF(curr);
        calculateBF(tmp);
        return tmp;
    }

    /**
     *
     * @param curr the current node being rotated left
     * @return a node that is getting linked back to the tree
     */
    private AVLNode<T> leftRotation(AVLNode<T> curr) {
        AVLNode<T> tmp = curr.getRight();
        curr.setRight(tmp.getLeft());
        tmp.setLeft(curr);

        calculateBF(curr);
        calculateBF(tmp);
        return tmp;
    }

    /**
     * A helper method that looks at the balance factor
     * of a node and determines if a rotation is required
     * @param curr the current node we are checking
     * @return the newly rotated node if it needed to be rotated.
     */
    private AVLNode<T> checkForRotation(AVLNode<T> curr) {
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight() != null
                    && curr.getRight().getBalanceFactor() >= 1) {
                curr.setRight(rightRotation(curr.getRight()));
            }
            return leftRotation(curr);

        } else if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft() != null
                    && curr.getLeft().getBalanceFactor() <= -1) {
                curr.setLeft(leftRotation(curr.getLeft()));
            }
            return rightRotation(curr);
        }
        return curr;
    }

    /**
     * helper method to calculate
     * the balance factor of a node
     * @param curr the current node we are checking
     */
    private void calculateBF(AVLNode<T> curr) {
        if (curr != null) {
            if (curr.getLeft() != null) {
                if (curr.getRight() != null) {
                    curr.setBalanceFactor(curr.getLeft().getHeight()
                            - curr.getRight().getHeight());
                    if (curr.getLeft().getHeight()
                            > curr.getRight().getHeight()) {
                        curr.setHeight(curr.getLeft().getHeight() + 1);
                    } else {
                        curr.setHeight(curr.getRight().getHeight() + 1);
                    }
                } else {
                    curr.setHeight(curr.getLeft().getHeight() + 1);
                    curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
                }
            } else {
                if (curr.getRight() != null) {
                    curr.setHeight(curr.getRight().getHeight() + 1);
                    curr.setBalanceFactor(-1 - curr.getRight().getHeight());
                } else {
                    curr.setBalanceFactor(0);
                    curr.setHeight(0);
                }
            }
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        } else {
            AVLNode<T> dummy = new AVLNode<>(data);
            root = removeRecursive(root, data);
            size--;
            return dummy.getData();
        }
    }

    /**
     *  a helper recursive function for removing from an AVL
     * @param curr the current node being looked at
     * @param data the data we are looking for
     * @return a node representing what was removed
     */
    private AVLNode<T> removeRecursive(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("The data was"
                    + " not found");
        } else if (data.compareTo(curr.getData()) == 0) {
            return removeChildren(curr);
        } else {
            if ((data.compareTo(curr.getData())) < 0) {
                curr.setLeft(removeRecursive(curr.getLeft(), data));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(removeRecursive(curr.getRight(), data));
            }
            calculateBF(curr);
            return checkForRotation(curr);
        }
    }

    /**
     *  another helper function for remove that
     *  determines the amount of children a node has and removes accordingly
     * @param curr the current node we are removing
     * @return a node in the tree where the data was removed
     */
    private AVLNode<T> removeChildren(AVLNode<T> curr) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            return null;
        } else if (curr.getRight() == null) {
            return curr.getLeft();
        } else if (curr.getLeft() == null) {
            return curr.getRight();
        } else {
            AVLNode<T> dummy = new AVLNode<>(null);
            AVLNode<T> successor = findMax(curr.getRight(), dummy);
            curr.setRight(successor);
            dummy.setRight(curr.getRight());
            dummy.setLeft(curr.getLeft());
            calculateBF(dummy);
            checkForRotation(dummy);
            return dummy;
        }
    }

    /**
     *  another helper function for moves that finds all of the
     *  successors in the tree after the data is found
     * @param curr the current node we are looking at
     * @param dummy a dummy node that will replace the
     *              successor node with its left node
     * @return the maximum data in the tree
     */
    private AVLNode<T> findMax(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() ==  null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(findMax(curr.getLeft(), dummy));
            calculateBF(curr);
            return checkForRotation(curr);
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        T bstdata = getRecursive(root, data);
        if (bstdata == null) {
            throw new java.util.NoSuchElementException("the element "
                    + "was not found");
        }
        return bstdata;
    }

    /**
     * Helper method to get data from a BST
     *
     * @param current the current node we are checking
     * @param data the data we are trying to find
     * @return the data that was got
     */
    private T getRecursive(AVLNode<T> current, T data) {
        if (current == null) {
            return null;
        } else if (current.getData().compareTo(data) == 0) {
            return current.getData();
        } else if (current.getData().compareTo(data) < 0) {
            return getRecursive(current.getRight(), data);
        } else if (current.getData().compareTo(data) > 0) {
            return getRecursive(current.getLeft(), data);
        }
        return null;

    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        return containsRecursive(root, data);
    }

    /**
     * Helper method to see if a BST contains specific data
     *
     * @param current the current node we are checking
     * @param data the data we are checking for
     * @return boolean representing if the data was found
     */
    public boolean containsRecursive(AVLNode<T> current, T data) {
        if (current == null) {
            return false;
        } else if (current.getData().compareTo(data) == 0) {
            return true;
        } else if (current.getData().compareTo(data) < 0) {
            return containsRecursive(current.getRight(), data);
        } else if (current.getData().compareTo(data) > 0) {
            return containsRecursive(current.getLeft(), data);
        }
        return false;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> data = new ArrayList<>();
        data = deepestBranchesRecursive(data, root);
        return data;
    }

    /**
     * Helper method to get the data of the
     * deepest branches in an AVL Tree
     *
     * @param list the list we are adding to
     * @param current the current node we are at
     * @return the list with the data in the
     * deepest branches of the AVL
     */

    private List<T> deepestBranchesRecursive(List<T> list, AVLNode<T> current) {
        if (current == null) {
            return list;
        }
        list.add(current.getData());
        if (current.getBalanceFactor() == 0) {
            deepestBranchesRecursive(list, current.getLeft());
            deepestBranchesRecursive(list, current.getRight());
        } else if (current.getBalanceFactor() < 0) {
            deepestBranchesRecursive(list, current.getRight());
        } else if (current.getBalanceFactor() > 0) {
            deepestBranchesRecursive(list, current.getLeft());
        }
        return list;
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("the "
                    + "thresholds can not be null");
        } else if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("data1 can"
                    + " not be larger than data2");
        }
        List<T> sorted  = new ArrayList<>();
        sorted = inorderLeft(root, data1, data2, sorted);
        sorted = inorderRight(root.getRight(), data1, data2, sorted);
        return sorted;
    }
    /**
     * private helper method to get the data that is
     * in between the values on the left side of the tree
     * and add it to an ArrayList
     * @param curr the current node we are checking the value of
     * @param data1 the lower bound we were given
     * @param data2 the upper bound we were given
     * @param sorted the sorted list we are adding to
     * @return a list containing all values in between the data
     * on the left side of the tree
     */
    private List<T> inorderLeft(
            AVLNode<T> curr, T data1, T data2, List<T> sorted) {
        if (curr == null) {
            return sorted;
        }
        if (curr.getData().compareTo(data1) > 0) {
            if (curr.getData().compareTo(data2) < 0) {
                inorderLeft(curr.getLeft(), data1, data2, sorted);
                sorted.add(curr.getData());
            }
        }
        inorderLeft(curr.getRight(), data1, data2, sorted);
        return sorted;
    }

    /**
     * private helper method to get the data that is
     * in between the values on the right side of the tree
     * @param curr the current node we are checking the value of
     * @param data1 the lower bound we were given
     * @param data2 the upper bound we were given
     * @param sorted the sorted list we are adding to
     * @return a list containing all the data the was in between the
     * values from the right side of the tree
     */
    private List<T> inorderRight(AVLNode<T> curr
            , T data1, T data2, List<T> sorted) {
        if (curr == null) {
            return sorted;
        }
        inorderLeft(curr.getLeft(), data1, data2, sorted);
        if (curr.getData().compareTo(data1) > 0) {
            if (curr.getData().compareTo(data2) < 0) {
                sorted.add(curr.getData());
                inorderRight(curr.getRight(), data1, data2, sorted);
            }
        }
        return sorted;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}