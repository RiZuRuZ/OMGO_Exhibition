package structures.tree;

import structures.Queue;
public class BinarySearchTree {
    private TreeNode root;
    private TreeNode[] nodes;
    private int nodeCount;

    public BinarySearchTree(int n) {
        this.nodes =new TreeNode[n];
        this.root = null;
        this.nodeCount = 0;
    }

    public void addTreeNode(String name, int  value) {
        TreeNode node = new TreeNode(name, value);
        this.nodes[this.nodeCount++] = node;
    }

    public TreeNode searchByValue(int value) {
        for(int i=0; i<this.nodeCount; i++){
            if(this.nodes[i].getValue() == value) {
                return this.nodes[i];
            }
        }
        return null;
    }
    
    public boolean setRoot(int value) {
        TreeNode node = searchByValue(value);
        if(node == null) {
            return false;
        }
        this.root = node;
        return true;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public void setRootNode(TreeNode node){ // for AVL tree
        this.root = node;
    }

    public int countNodes(TreeNode root) {
        nodeCount = 0;
        if(root == null){
            nodeCount = 0;
        } else {
            nodeCount = countNodes(root.getLeft()) + countNodes(root.getRight()) + 1;
        }
        return nodeCount;
    }

    public void insertNodeToBinaryTree(TreeNode root, TreeNode node) {
        if(root == null) {
            this.root = node;
            return;
        }
        if(node.getValue() < root.getValue()) {
            if(root.getLeft() == null) {
                root.setLeft(node);
            } else {
                insertNodeToBinaryTree(root.getLeft(), node);
            }
        } else {
            if(root.getRight() == null) {
                root.setRight(node);
            } else {
                insertNodeToBinaryTree(root.getRight(), node);
            }
        }
    }

    public void preOrder(TreeNode root) {
        if(root == null) {
            return;
        }
        System.out.print(root.getValue() + " ");
        preOrder(root.getLeft());
        preOrder(root.getRight());

    }

    public void inOrder(TreeNode root) {
        if(root == null) {
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getValue() + " ");
        inOrder(root.getRight());
    }

    public void postOrder(TreeNode root) {
        if(root == null) {
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.print(root.getValue() + " ");
    }

    public void breadthFirst() {
        if(this.root == null) {
            return;
        }
        Queue<TreeNode> queue = new Queue<>();
        queue.enqueue(this.root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.peek();
            System.out.print(node.getValue() + " ");
            if(node.getLeft() != null) {
                queue.enqueue(node.getLeft());
            }
            if(node.getRight() != null) {
                queue.enqueue(node.getRight());
            }
            queue.dequeue();
        }
    }
}
