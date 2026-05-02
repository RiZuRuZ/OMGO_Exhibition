package structures;

public class Queue<T extends Comparable<T>> {
    private Node<T> front;
    private Node<T> rear;

    //constructor
    public Queue() {
        this.front = null;
        this.rear = null;
    }

    //isEmpty
    public boolean isEmpty() {
        return this.front == null;
    }

    //enqueue
    public void enqueue(T data){
        Node<T> newNode = new Node<>(data);
        if (this.isEmpty()) {
            this.front = newNode;
            this.rear = newNode;
            return;
        }
        this.rear.setNext(newNode);
        this.rear = newNode;
    }

    //dequeue
    public void dequeue(){
        if (this.isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        this.front = this.front.getNext();
        if (this.front == null) {
            this.rear = null;
        }
    }

    //peek
    public T peek() {
        if (this.isEmpty()) {
            //System.out.println("Queue is empty");
            return null;
        }
        //System.out.println("Front element: " + this.front.getData());
        return this.front.getData();
    }

    //size
    public int size() {
        int count = 0;
        Node<T> current = this.front;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }
    
    // display
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        Node<T> current = this.front;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println(); // newline for better formatting
    }

    // sort
    public void sort() {
        if (this.isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        Node<T> current = this.front;
        while (current != null) {
            Node<T> nextNode = current.getNext();
            while (nextNode != null) {
                if (current.getData().compareTo(nextNode.getData()) > 0) {
                    // Swap data
                    T temp = current.getData();
                    current.setData(nextNode.getData());
                    nextNode.setData(temp);
                }
                nextNode = nextNode.getNext();
            }
            current = current.getNext();
        }
    }

    public void reverse() {
        if (this.isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        Node<T> prev = null;
        Node<T> current = this.front;
        Node<T> next = null;
        this.rear = this.front; // after reversal, the front will become rear
        while (current != null) {
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        this.front = prev; // after reversal, the last node will become front
    }
    

}
