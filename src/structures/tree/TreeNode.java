package structures.tree;

public class TreeNode implements Comparable<TreeNode> {
    private String name;
    private int value;
    private TreeNode left;
    private TreeNode right;
    private int height;
    private int balance;
    public TreeNode(String name,int value){
        this.value = value;
        this.name = name;
        this.left = null;
        this.right = null;
        this.height = 1;
        this.balance = 0;
    }

    @Override
    public int compareTo(TreeNode other) {
        return Integer.compare(this.value, other.value);
    }
    
    //getters
    public int getValue(){
        return this.value;
    }

    public String getName(){
        return this.name;
    }

    public TreeNode getLeft(){
        return this.left;
    }

    public TreeNode getRight(){
        return this.right;
    }

    public int getHeight() {
        return this.height;
    }

    public int getBalance() {
        return this.balance;
    }

    //setters

    public void setValue(int value){
        this.value = value;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLeft(TreeNode left){
        this.left = left;
    }

    public void setRight(TreeNode right){
        this.right = right;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setBalance(int balance) {
        this.balance = balance;
    }
}
