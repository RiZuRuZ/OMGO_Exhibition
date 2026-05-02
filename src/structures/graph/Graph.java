package structures.graph;

import structures.Queue;
public class Graph {
    private int nVertices;
    private int nEdges;
    private Vertex vertexList;

    public Graph() {
        nVertices = 0;
        nEdges = 0;
        vertexList = null;
    }

    //getters
    public int getnVertices() {
        return nVertices;
    }
    
    public int getnEdges() {
        return nEdges;
    }

    public Vertex getVertexList() {
        return vertexList;
    }

    //setters
    public void setnVertices(int nVertices) {
        this.nVertices = nVertices;
    }

    public void setnEdges(int nEdges) {
        this.nEdges = nEdges;
    }

    public void setVertexList(Vertex vertexList) {
        this.vertexList = vertexList;
    }

    public void addVertex(String name){
        Vertex newVertex = new Vertex(name);
        if(vertexList == null){
            vertexList = newVertex;
        } else{
            Vertex current = vertexList;
            while(current.getNext() != null){
                current = current.getNext();
            }
            current.setNext(newVertex);
        }
        nVertices++;
    }

    public Vertex findVertex(String name){
        Vertex current = vertexList;
        while(current != null){
            if(current.getName().equals(name)){
                return current;
            }
            current = current.getNext();
        }
        return null;
    }  

    public void addEdge(String srcName, String destName, int weight, double f_weight){
        Vertex src = findVertex(srcName);
        Vertex dest = findVertex(destName);
        if (src == null || dest == null) {
            return;
        }

        Edge newEdge = new Edge(src, dest, weight, f_weight);
        if (src.getEdgeList() == null){
            src.setEdgeList(newEdge);
        } else {
            Edge current = src.getEdgeList();
            while(current.getNext() != null){
                current = current.getNext();
            }
            current.setNext(newEdge);
        }
        nEdges++;
    }

    //helper method for Lab9_1
    public void addEdge_F(String srcName, String destName, double weight_F) {
        Vertex src  = findVertex(srcName);
        Vertex dest = findVertex(destName);
        if (src == null || dest == null) return;

        Edge e1 = new Edge(src, dest, 0, weight_F);
        e1.setNext(src.getEdgeList());
        src.setEdgeList(e1);

        Edge e2 = new Edge(dest, src, 0, weight_F);
        e2.setNext(dest.getEdgeList());
        dest.setEdgeList(e2);

        nEdges += 2; // 
    }




    public void sortByNameVertices(){
        if(vertexList == null || vertexList.getNext() == null){
            return;
        }

        Vertex sortedList = null;
        Vertex current = vertexList;
        while(current != null){
            Vertex next = current.getNext();
            if(sortedList == null || current.getName().compareTo(sortedList.getName()) < 0){
                current.setNext(sortedList);
                sortedList = current;
            } else {
                Vertex sortedCurrent = sortedList;
                while(sortedCurrent.getNext() != null && sortedCurrent.getNext().getName().compareTo(current.getName()) < 0){
                    sortedCurrent = sortedCurrent.getNext();
                }
                current.setNext(sortedCurrent.getNext());
                sortedCurrent.setNext(current);
            }
            current = next;
        }
        vertexList = sortedList;
    }

    public void sortAllEdges() {
        Vertex v = vertexList;
        while (v != null) {
            v.setEdgeList(sortEdgeList(v.getEdgeList()));
            v = v.getNext();
        }
    }

    private Edge sortEdgeList(Edge head) {
        if (head == null || head.getNext() == null) return head;

        Edge sortedList = null;
        Edge current = head;

        while (current != null) {
            Edge next = current.getNext();
            if (sortedList == null ||
                current.getDestination().getName()
                    .compareTo(sortedList.getDestination().getName()) < 0) {
                current.setNext(sortedList);
                sortedList = current;
            } else {
                Edge sortedCurrent = sortedList;
                while (sortedCurrent.getNext() != null &&
                    sortedCurrent.getNext().getDestination().getName()
                                    .compareTo(current.getDestination().getName()) < 0) {
                    sortedCurrent = sortedCurrent.getNext();
                }
                current.setNext(sortedCurrent.getNext());
                sortedCurrent.setNext(current);
            }
            current = next;
        }
        return sortedList;
    }
    public void printGraph(){
        Vertex v = vertexList;
        while(v!=null){
            System.out.print(v.getName() + " -> ");
            Edge e = v.getEdgeList();
            if(e == null){
                System.out.print("none");
            }
            while(e!=null){
                System.out.print(e.getDestination().getName() + "(" + e.getWeight() + ") "+ "(" + e.getF_weight() + ") ");
                e = e.getNext();
            }
            System.out.println("");
            v = v.getNext();
        }
    }

    public void outDegree(String name){
        Vertex v = findVertex(name);
        if(v == null){
            return;
        }

        int count = 0;
        Edge e = v.getEdgeList();

        while(e!=null){
            count++;
            v.setSumWeight(v.getSumWeight() + e.getWeight());
            e = e.getNext();
        }
        v.setOutDegree(count);
    }

    public void inDegree(String name){
        Vertex target = findVertex(name);
        if(target == null){
            return;
        }

        int count = 0;
        Vertex v = vertexList;

        while(v != null){
            Edge e = v.getEdgeList();
            while(e!=null){
                if(e.getDestination() == target){ // && e.getSource() != target
                    target.setSumWeight(target.getSumWeight() + e.getWeight());
                    count++;
                }
                e = e.getNext();
            }
            v = v.getNext();
        }
        target.setInDegree(count);
    }

    public void depthFirstSearch(Vertex current){
        if(current == null||current.isVisited()){
            return;
        }

        current.setVisited(true);
        System.out.print(current.getName() + " ");

        Edge e = current.getEdgeList();
        while(e != null){
            depthFirstSearch(e.getDestination());
            e = e.getNext();
        }

        //resetVisited();
    }

    public void breadthFirstSearch(Vertex start) {
        Queue<Vertex> queue = new Queue<>();

        start.setVisited(true);
        queue.enqueue(start);

        bfsRecursive(queue);
    }

    private void bfsRecursive(Queue<Vertex> queue) {
        if (queue.isEmpty()) return;

        Vertex current = queue.peek();
        queue.dequeue();
        System.out.print(current.getName() + " ");

        Edge e = current.getEdgeList();
        while (e != null) {
            Vertex neighbor = e.getDestination();
            if (!neighbor.isVisited()) {
                neighbor.setVisited(true);
                queue.enqueue(neighbor);
            }
            e = e.getNext();
        }

        bfsRecursive(queue);
    }


    //dijkstra
    public void dijkstra(Vertex start){
        Vertex v = vertexList;
        while (v != null) {
            v.setDistance(Double.MAX_VALUE);
            v.setPrev(null);
            v.setVisited(false);
            v = v.getNext();
        }

        start.setDistance(0);
        //start.setVisited(true);

        while(true){
            Vertex current = getMinUnvisited(); 
            if (current == null || current.getDistance() == Double.MAX_VALUE){
                break;
            }
            current.setVisited(true);  
            
            Edge e = current.getEdgeList();
            while(e != null){
                Vertex neighbor = e.getDestination();
                if(!neighbor.isVisited()){
                    double newDist = current.getDistance() + e.getWeight();
                    if (newDist < neighbor.getDistance()) {
                        neighbor.setDistance(newDist); // update shortest
                        neighbor.setPrev(current);     // update previous
                    }
                }
                e = e.getNext();
            }
        }
    }

    private Vertex getMinUnvisited() {
        Vertex min = null;
        Vertex v = vertexList;
        while (v != null) {
            if (!v.isVisited()) {
                if (min == null || v.getDistance() < min.getDistance()) {
                    min = v;
                }
            }
            v = v.getNext();
        }
        return min;
    }

    private void printDijkstra(String startName) {
        System.out.println("=== Dijkstra from " + startName + " ===");
        Vertex v = vertexList;
        while (v != null) {
            System.out.print(startName + " -> " + v.getName() + " : ");
            if (v.getDistance() == Double.MAX_VALUE) {
                System.out.print("unreachable  path: -");
            } else {
                System.out.print((int)v.getDistance() + "  path: ");
                printPath(v);
            }
            System.out.println();
            v = v.getNext();
        }
    }

    public void printPath(Vertex v) {
        if (v.getPrev() != null) {
            printPath(v.getPrev());
            System.out.print(" -> ");
        }
        System.out.print(v.getName());
        //g.printPath(g.findVertex(endName));
        //System.out.println(" (" + (int)g.findVertex(endName).getDistance() + ")");    #main
    }

    public void resetVisited(){
        Vertex v = vertexList;
        while(v != null){
            v.setVisited(false);
            v = v.getNext();
        }
    }


    //helper
    public Edge[] getAllEdges() {
        Edge[] temp = new Edge[nEdges];
        int count = 0;

        Vertex v = vertexList;
        while (v != null) {
            Edge e = v.getEdgeList();
            while (e != null) {

                String s = e.getSource().getName();
                String d = e.getDestination().getName();

                if (s.compareTo(d) < 0) {
                    temp[count++] = e;
                }

                e = e.getNext();
            }
            v = v.getNext();
        }

        Edge[] result = new Edge[count];
        for (int i = 0; i < count; i++) result[i] = temp[i];
        return result;
    }

    public void removeEdge(Edge target) {

        Vertex src = target.getSource();
        Vertex dest = target.getDestination();

        removeEdgeOneSide(src, target);

        Edge reverse = dest.getEdgeList();
        while (reverse != null) {
            if (reverse.getDestination() == src &&
                reverse.getF_weight() == target.getF_weight()) {
                removeEdgeOneSide(dest, reverse);
                break;
            }
            reverse = reverse.getNext();
        }

        nEdges -= 2;
    }

    private void removeEdgeOneSide(Vertex src, Edge target) {
        Edge prev = null;
        Edge curr = src.getEdgeList();

        while (curr != null) {
            if (curr == target) {
                if (prev == null) {
                    src.setEdgeList(curr.getNext());
                } else {
                    prev.setNext(curr.getNext());
                }
                return;
            }
            prev = curr;
            curr = curr.getNext();
        }
    }

    public void removeVertex(String name) {
        Vertex prev = null;
        Vertex curr = vertexList;
        while (curr != null) {
            if (curr.getName().equals(name)) {
                if (prev == null) vertexList = curr.getNext();
                else prev.setNext(curr.getNext());
                nVertices--;
                return;
            }
            prev = curr;
            curr = curr.getNext();
        }
    }
    
}
