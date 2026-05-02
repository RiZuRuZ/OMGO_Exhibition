package structures;
public class Stack<T extends Comparable<T>> {
    private Node<T> top;

    //constructor
    public Stack() {
        this.top = null;
    }

    //isEmpty
    public boolean isEmpty() {
        return this.top == null;
    }

    // X
    // O X

    // X X X
    // O X X X
    // ^ top

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(this.top);
        this.top = newNode;
    }

    public void pop() {
        if (this.isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }
        this.top = this.top.getNext();
    }

    public T peek() {
        if (this.isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        //System.out.println("Top element: " + this.top.getData());
        return this.top.getData();
    }
    
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }
        Node<T> current = this.top;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println(); // newline for better formatting
    }
}
