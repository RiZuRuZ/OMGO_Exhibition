package structures;

public class CircularQueue<T extends Comparable<T>>  {
    private Node<T> front;
    private Node<T> rear;

    //constructor
    public CircularQueue() {
        this.front = null;
        this.rear = null;
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);

        if (front == null) {
            front = rear = newNode;
            rear.setNext(front);  // make it circular
        } else {
            rear.setNext(newNode);
            rear = newNode;
            rear.setNext(front);  // maintain circular link
        }
    }

    public void dequeue() {
        if (front == null) {
            System.out.println("Queue is empty");
            return;
        }

        if (front == rear) { // only one element
            front = rear = null;
        } else {
            front = front.getNext();
            rear.setNext(front); // maintain circular link
        }
    }

    public void sort() {
        if (front == null) {
            System.out.println("Queue is empty");
            return;
        }

        Node<T> current = front;
        do {
            Node<T> nextNode = current.getNext();
            while (nextNode != front) {
                if (current.getData().compareTo(nextNode.getData()) > 0) {
                    // Swap data
                    T temp = current.getData();
                    current.setData(nextNode.getData());
                    nextNode.setData(temp);
                }
                nextNode = nextNode.getNext();
            }
            current = current.getNext();
        } while (current != front); // loop until we come back to the front
    }

    public void display() {
        if (front == null) {
            System.out.println("Queue is empty");
            return;
        }

        Node<T> current = front;
        do {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        } while (current != front); // loop until we come back to the front
    }
}
