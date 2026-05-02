package structures;

public class LinkList<T extends Comparable<T>> {
    private Node<T> head;

    //constructor
    public LinkList() {
        this.head = null;
    }

    //isEmpty
    public boolean isEmpty() {
        return this.head == null;
    }

    //methods
    public void insertHead(T data){
        if (this.isEmpty()) {
            this.head = new Node<>(data);
            return;
        }
        Node<T> newNode = new Node<>(data);
        newNode.setNext(this.head);
        this.head = newNode;
    }

    public void insertEnd(T data){
        if (this.isEmpty()) {
            this.head = new Node<>(data);
            return;
        }
        Node<T> newNode = new Node<>(data);
        Node<T> current = this.head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newNode);
    }

    public void insertAfter(T data,T after_data){
        if (this.isEmpty()) {
            this.head = new Node<>(data);
            return;
        }
        Node<T> newNode = new Node<>(data);
        Node<T> current = this.head;
        while (current != null) {
            if (current.getData().equals(after_data)) {
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                return;
            }
            current = current.getNext();
        }
        System.out.println("not found: " + after_data);
    }

    public void insertBefore(T data,T before_data){
        if (this.isEmpty()) {
            this.head = new Node<>(data);
            return;
        }
        Node<T> newNode = new Node<>(data);
        //if the head node is the before_data, then insert the new node before the head node
        if (this.head.getData().equals(before_data)) {
            newNode.setNext(this.head);
            this.head = newNode;
            return;
        }
        Node<T> current = this.head;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(before_data)) {
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                return;
            }
            current = current.getNext();
        }
        System.out.println("not found: " + before_data);
    }

    public void deleteHead(){
        if (this.isEmpty()) {
            System.out.println("underflow\n");
            return;
        }
        this.head = this.head.getNext();
    }   

    public void deleteEnd(){
        if (this.isEmpty()) {
            System.out.println("underflow\n");
            return;
        }
        if (this.head.getNext() == null) {
            this.head = null;
            return;
        }
        Node<T> current = this.head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        current.setNext(null);
    }

    public void deleteNode(T data){
        if (this.isEmpty()) {
            System.out.println("underflow\n");
            return;
        }
        if (this.head.getData().equals(data)) {
            this.head = this.head.getNext();
            return;
        }
        Node<T> current = this.head;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(data)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
        System.out.println("not found: " + data);
    }

    public void display() {
        if (this.isEmpty()) {
            System.out.println("empty list\n");
            return;
        }
        Node<T> current = this.head;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }

    public void count() {
        if (this.isEmpty()) {
            System.out.println("empty list\n");
            return;
        }
        int count = 0;
        Node<T> current = this.head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        System.out.println("count: " + count);
    }

    public void search(T data) {
        if (this.isEmpty()) {
            System.out.println("empty list\n");
            return;
        }
        Node<T> current = this.head;
        int pos = 0;
        while (current != null) {
            if (current.getData().equals(data)) {
                System.out.println("found: " + data + " at node: " + pos);
                return;
            }
            current = current.getNext();
            pos++;
        }
        System.out.println("not found: " + data);
    }

    public void swap(Node<T> node1, Node<T> node2) {
        if (node1 == null || node2 == null) {
            // System.out.println("cannot swap null nodes\n");
            return;
        }
        T temp = node1.getData();
        node1.setData(node2.getData());
        node2.setData(temp);
    }

    public void bubbleSort() {
        if (this.isEmpty() || this.head.getNext() == null) {
            return;
        }
        Node<T> current;
        Node<T> next;
        for (current = this.head; current.getNext() != null; current = current.getNext()) {
            for (next = this.head; next.getNext() != null; next = next.getNext()) {
                if (next.getData().compareTo(next.getNext().getData()) > 0) {
                    T temp = next.getData();
                    next.setData(next.getNext().getData());
                    next.getNext().setData(temp);
                }
            }
        }
    }
}
