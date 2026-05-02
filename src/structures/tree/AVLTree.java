package structures.tree;

public class AVLTree {
    BinarySearchTree tree;

    public AVLTree(int n){
        tree = new BinarySearchTree(n);
    }

    public BinarySearchTree getTree(){
        return tree;
    }

    public void printNode(TreeNode node){
        if(node == null) return;

        printNode(node.getLeft());
        System.out.println(node.getValue() +
            " h=" + node.getHeight() +
            " b=" + node.getBalance());
        printNode(node.getRight());
    }

    /*fail some case */
/*
    public void fillHeight(TreeNode node){
        int height = 0;
        if(node.getLeft() != null) {
            fillHeight(node.getLeft());
        }
        if(node.getRight() != null) {
            fillHeight(node.getRight());
        }

        if(node.getLeft() == null && node.getRight() == null) {
            height = 0;
        } else if(node.getLeft() == null) {
            height = node.getRight().getHeight() + 1;
        } else if(node.getRight() == null) {
            height = node.getLeft().getHeight() + 1;
        } else if(node.getRight().getHeight() > node.getLeft().getHeight()) {
            height = node.getRight().getHeight() + 1;
        } else {
            height = node.getLeft().getHeight() + 1; 
        }

        node.setHeight(height);
    }
*/
    public void fillHeight(TreeNode node){

        if(node == null)
            return;

        fillHeight(node.getLeft());
        fillHeight(node.getRight());

        int leftHeight = -1;
        int rightHeight = -1;

        if(node.getLeft() != null)
            leftHeight = node.getLeft().getHeight();

        if(node.getRight() != null)
            rightHeight = node.getRight().getHeight();

        int height = Math.max(leftHeight, rightHeight) + 1;

        node.setHeight(height);
    }

    public void fillBalanceFactor(TreeNode node) {
        if(node == null){
            return;
        }
        int leftHeight = -1; // old code: 0
        int rightHeight = -1; // old code: 0

        if(node.getLeft() != null) {
            fillBalanceFactor(node.getLeft());
            leftHeight = node.getLeft().getHeight();
        }
        if(node.getRight() != null) {
            fillBalanceFactor(node.getRight());
            rightHeight = node.getRight().getHeight();
        }
        node.setBalance(leftHeight - rightHeight);
    }
    
/* 
    public void rightRotate(TreeNode criticalNode) {
        TreeNode newRoot = criticalNode.getLeft();
        criticalNode.setLeft(newRoot.getRight());
        newRoot.setRight(criticalNode);
        this.tree.setRoot(newRoot.getValue());
    }

    public void leftRotate(TreeNode criticalNode) {
        TreeNode newRoot = criticalNode.getRight();
        criticalNode.setRight(newRoot.getLeft());
        newRoot.setLeft(criticalNode);
        this.tree.setRoot(newRoot.getValue());
    }
*/    

    public TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.getLeft();
        TreeNode T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        fillHeight(y);
        fillHeight(x);

        return x;
    }

    public TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.getRight();
        TreeNode T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        fillHeight(x);
        fillHeight(y);

        return y;
    }
    
    public TreeNode balanceTree(TreeNode node){

        if(node == null)
            return null;

        node.setLeft(balanceTree(node.getLeft()));
        node.setRight(balanceTree(node.getRight()));

        fillHeight(node);
        fillBalanceFactor(node);

        if(node.getBalance() > 1){

            if(node.getLeft().getBalance() >= 0)
                return rightRotate(node);

            else{
                node.setLeft(leftRotate(node.getLeft()));
                return rightRotate(node);
            }
        }

        if(node.getBalance() < -1){

            if(node.getRight().getBalance() <= 0)
                return leftRotate(node);

            else{
                node.setRight(rightRotate(node.getRight()));
                return leftRotate(node);
            }
        }

        return node;
    }
    
}