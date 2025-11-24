public class BSTree<K extends Comparable<K>> // can compare elements of type K
{
    BSTNode<K> root;

    public void print()
    {
        this.root.print();
    }

    public boolean find(K key) // will be called on a tree
    {
        if (this.root == null)
            return false;
        return this.root.find(key) != null;
    }

    private static class BSTNode<T extends Comparable<T>>
    {
        T key;
        BSTNode<T> left;
        BSTNode<T> right;

        private BSTNode(T key)
        {
            this.key = key;
        }

        private void print()            // display all keys inside the tree rooted at this
        {
            // traverse left subtree
            if(this.left != null)
                this.left.print();

            // display this key
            System.out.print(this.key + " "); // in order traversal

            // traverse right subtree
            if (this.right != null)
                this.right.print();
        }

        private BSTNode<T> find(T key)
        {
            if (this.key.equals(key))
                return this;
            else if (key.compareTo(this.key) < 0 && this.left != null)
                return this.left.find(key);
            else if (this.right != null)
                return this.right.find(key);
            return null;
        }

    }

    public static void main(String[] args)
    {
        BSTree<Integer> tree = new BSTree<Integer>(); // must be Integer not int (comparable)
        BSTNode<Integer> n = new BSTNode<Integer>(5);
        tree.root = n;
        tree.root.left = new BSTNode<Integer>(3);
        tree.root.right = new BSTNode<Integer>(8);
        tree.root.right.left = new BSTNode<Integer>(7); // larger than 5, smaller then 8

        System.out.println(tree); // displays a reference

        tree.root.print();
        System.out.println();
        tree.root.right.print();
        System.out.println();
        tree.print();
    }
}
