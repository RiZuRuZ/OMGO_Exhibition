package structures;

public class Node<T> {
    private T data;
    private Node<T> next;

    //----------------------------------------------------------------//
    // private Node(int data) {
    //     this.data = data;
    //     this.next = null;
    // }

    // public static Node createNode(int data) {
    //     return new Node(data);
    // }
    //----------------------------------------------------------------//
    
    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    //getters
    public T getData() {
        return this.data;
    }
    public Node<T> getNext() {
        return this.next;
    }

    //setters
    public void setData(T data) {
        this.data = data;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }

}
