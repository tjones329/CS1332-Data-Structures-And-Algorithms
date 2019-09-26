import java.util.*;

/**
 * Your implementation of a binary search tree.
 *
 * @author Tyler Jones
 * @userid tjones329
 * @GTID 903283773
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        for(T o: data) {
            add(o);
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if(data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        root = addRecursive(root, data);
        size++;

    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf (no children). In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if(data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        T bstdata = getRecursive(root, data);
        if(bstdata == null) {
            throw new java.util.NoSuchElementException("the element was not found");
        } return bstdata;
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if(data == null) {
            throw new IllegalArgumentException("data can not be null");
        } return containsRecursive(root, data);
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> data = new ArrayList<T>();
        data = preorderRecursive(data, root);
        return data;
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> data = new ArrayList<T>();
        return inorderRecursive(data, root);
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> data = new ArrayList<T>();
        data = postorderRecursive(data, root);
        return data;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n). This does not need to be done recursively.
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        if (root == null) {
            return null;
        }
        List<T> levelorder = new ArrayList<T>();
        Queue<BSTNode<T>> levelorderqueue = new LinkedList<BSTNode<T>>();
        levelorderqueue.add(root);
        while(!levelorderqueue.isEmpty()) {
            BSTNode<T> removednode = levelorderqueue.remove();
            if (removednode.getLeft() != null) {
                levelorderqueue.add(removednode.getLeft());
            }
            if (removednode.getRight() != null) {
                levelorderqueue.add(removednode.getRight());
            }
            levelorder.add(removednode.getData());
        }
        return levelorder;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     *
     * @param <T> the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(
            BSTNode<T> treeRoot) {
        return true;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        BSTNode<T> node = root;
        return heightRecursive(node);
    }

    /**
     * Recursive method used to calculate the height of a tree
     *
     * @param curr the current node height being checked
     * @return an int representing the height of the tree
     *
     */
    public int heightRecursive(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }

        int leftheight = heightRecursive(curr.getLeft());
        int rightheight = heightRecursive(curr.getRight());

        if (leftheight > rightheight) {
            return leftheight + 1;
        } else {
            return rightheight + 1;
        }
    }

    /**
     * Returns the size of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the BST.
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
     * Helper method to add data to a BST
     *
     * @param current the current node we are checking
     * @param data the data we want to input into the bst
     */
    public BSTNode<T> addRecursive(BSTNode<T> current, T data) {
        if(current == null) {
            current = new BSTNode<T>(data);
        } else if(current.getData().compareTo(data) == 0) {
            return current;
        } else if(current.getData().compareTo(data) < 0) {
            current.setRight(addRecursive(current.getRight(), data));
        } else if(current.getData().compareTo(data) > 0) {
            current.setLeft(addRecursive(current.getLeft(), data));
        }
        return current;

    }

    /**
     * Helper method to get data from a BST
     *
     * @param current the current node we are checking
     * @param data the data we are trying to find
     */
    public T getRecursive(BSTNode<T> current, T data) {
        if(current == null) {
            return null;
        } else if(current.getData().compareTo(data) == 0) {
            return current.getData();
        } else if(current.getData().compareTo(data) < 0) {
            return getRecursive(current.getRight(), data);
        } else if(current.getData().compareTo(data) > 0) {
            return getRecursive(current.getLeft(), data);
        } return null;

    }

    /**
     * Helper method to see if a BST contains specific data
     *
     * @param current the current node we are checking
     * @param data the data we are checking for
     * @return boolean representing if the data was found
     */
    public boolean containsRecursive(BSTNode<T> current, T data) {
        if(current == null) {
            return false;
        } else if(current.getData().compareTo(data) == 0) {
            return true;
        } else if(current.getData().compareTo(data) < 0) {
            return containsRecursive(current.getRight(), data);
        } else if(current.getData().compareTo(data) > 0) {
            return containsRecursive(current.getLeft(), data);
        } return false;
    }

    /**
     * Helper method to get the preorder of a BST
     *
     * @param list the list we are adding to
     * @param current the current node we are at
     * @return the list with the BST data in preorder
     */

    public List<T> preorderRecursive(List<T> list, BSTNode<T> current) {
        if(current == null) {
            return list;
        }
        list.add(current.getData());
        preorderRecursive(list, current.getLeft());
        preorderRecursive(list, current.getRight());
        return list;
    }

    /**
     * Helper method to get the postorder of a BST
     *
     * @param list the list we are adding to
     * @param current the current node we are at
     * @return the list with the BST data in postorder
     */

    public List<T> postorderRecursive(List<T> list, BSTNode<T> current) {
        if(current == null) {
            return list;
        }
        postorderRecursive(list, current.getLeft());
        postorderRecursive(list, current.getRight());
        list.add(current.getData());
        return list;
    }

    /**
     * Helper method to get the inorder of a BST
     *
     * @param list the list we are adding to
     * @param current the current node we are at
     * @return the list with the BST data in inorder
     */

    public List<T> inorderRecursive(List<T> list, BSTNode<T> current) {
        if(current == null) {
            return list;
        }
        inorderRecursive(list, current.getLeft());
        list.add(current.getData());
        inorderRecursive(list, current.getRight());
        return list;
    }
}