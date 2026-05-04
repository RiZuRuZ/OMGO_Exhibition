import java.util.Scanner;
import structures.tree.NameAVL;
import structures.tree.NameNode;
import structures.tree.PosAVL;
import structures.tree.PosNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class ExhibitionSystem {

    static String[][] map = new String[6][12];

    static NameAVL nameTree = new NameAVL();
    static PosAVL posTree = new PosAVL();

    public static void main(String[] args) {
        //initMap();

        Scanner sc = new Scanner(System.in);
        int[][] Map = createMap();

        while(true) {
            System.out.println("1. Show Map");
            System.out.println("2. Reserve");
            System.out.println("3. Show AVL");
            System.out.println("4. Search by Name");
            System.out.println("5. Search by Position");
            System.out.println("6. Path Finder");
            System.out.println("7. Randomize Map");
            System.out.println("8. Pull Data");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch(choice) {
                case 1: 
                    showMap(Map);
                    break;
                case 2: {
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter positions (e.g. A1 A2 A3): ");
                    String input = sc.nextLine();
                    String[] positions = input.split(" ");

                    for (int i = 0; i < positions.length; i++) {
                        positions[i] = positions[i].toUpperCase(); // ✅ แปลงเป็นตัวใหญ่ก่อนเช็ค
                    }
                    
                    Map = reserve(name, positions, Map);
                    break;
                }
                case 3:
                    nameTree.printTree();
                    break;
                case 4: {
                    System.out.print("Enter name to search: ");
                    String name = sc.nextLine();
                    NameNode result = nameTree.search(name);

                    if(result == null) System.out.println("Name not found.");
                    else {
                        System.out.println("Positions for " + name + ": " + result.positions);
                    }
                    break;
                }
                case 5: {
                    System.out.print("Enter position to search: ");
                    String pos = sc.nextLine();

                    PosNode result = posTree.searchByPosition(pos);

                    if(result == null) {
                        System.out.println("Position not found.");
                    } else {
                        System.out.println("Position " + pos + " reserved by: " + result.name);
                    }
                    break;
                }
                case 6: {
                    System.out.print("Enter start position: ");
                    String start = sc.nextLine();
                    System.out.print("Enter end position: ");
                    String end = sc.nextLine();
                    ShortestPath(Map, start, end);
                    break;
                }
                case 7: {
                    System.out.print("Enter min value: ");
                    int min = sc.nextInt();

                    System.out.print("Enter max value: ");
                    int max = sc.nextInt();

                    randomizeMap(Map, min, max);
                    System.out.println("Map randomized!");
                    break;
                }
                case 8: {
                    String file = "data.csv";

                    Map = pullFromCSV(file, Map);
                    System.out.println("Loaded data from CSV!");
                    break;

                }
    
                case 0:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static int[][] reserve(String name, String[] positions, int[][] Map) {

        // ✅ เช็ค format ก่อน
        for(String pos : positions) {
            if(!isValidPosition(pos)) {
                System.out.println("Invalid position: " + pos);
                return Map; //หยุดทันที
            }
        }

        // ✅ เช็คว่าซ้ำไหม
        for(String pos : positions) {
            if(posTree.exists(pos)) {
                System.out.println("Position " + pos + " is already reserved.");
                return Map;
            }
        }

        // ✅ insert จริง
        for(String pos : positions) {
            nameTree.insert(name, pos);
            posTree.insert(pos, name);
            Map = addPos(Map, pos);
        }

        saveToCSV("data.csv", name, positions);

        System.out.println("Reservation successful for " + name + " at positions: " + String.join(", ", positions));
        return Map;
    }

     private static boolean isValidPosition(String pos) {
        return pos.matches("^[A-F](1[0-2]|[1-9])$");
    }

    public static int[][] createMap() {
        int rows = 16;
        int cols = 10;

        int Map[][] = new int[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                Map[i][j] = 1;
            }
        }

        return Map;
    }

    public static int[] parseCoor(String positions){
        char colChar = positions.toUpperCase().charAt(0);
        int j = colChar - 'A'; // แปลง A->0, B->1, C->2, D->3, E->4, F->5

        // 2. แยกตัวเลขที่เหลือ (1, 3, 11, ...)
        int i = Integer.parseInt(positions.substring(1));

        // 3. เข้าสูตรคำนวณ Matrix Index (จากสูตร row = 1+i+i//4 และ col = 1+j+j//2)
        // หมายเหตุ: ในสูตร Row เราใช้ (i-1) เพราะเลขแถวในภาพเริ่มที่ 1 แต่ index เริ่มที่ 0
        int matrixRow = 1 + (i-1) + ((i-1) / 4);
        int matrixCol = 1 + j + (j / 2);
        return new int[]{matrixRow, matrixCol};
    }

    public static int[][] addPos(int[][] Map, String positions) {
        int INF = Integer.MAX_VALUE;
        int[] coor = parseCoor(positions);
        Map[coor[0]][coor[1]] = INF;
        return Map;
    }

    public static void showMap(int[][] Map) {
        for(int i = 0 ; i < Map.length; i++) {
            for(int j = 0; j < Map[0].length; j++) {
                if(Map[i][j] == Integer.MAX_VALUE) System.out.print("INF ");
                else System.out.print(Map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] pullFromCSV(String filename, int[][] Map) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                // ข้าม header
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) {
                    continue;
                }

                String name = parts[0].trim();
                String[] positions = parts[1].trim().split(" ");
                Map = reserve(name, positions, Map);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return Map;
    }

    public static void saveToCSV(String filename, String name, String[] positions) {
        
        try {
            File file = new File(filename);
            boolean fileExists = file.exists();

            FileWriter fw = new FileWriter(file, true);

            if (!fileExists) {
                fw.write("name,positions\n");
            }
            // รวม position เป็น string
            String posStr = String.join(" ", positions);

            fw.write(name + "," + posStr + "\n");
            fw.close();

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }


    //อื่นๆใส่ก่อนA* นะจ๊ะจะได้ไม่งง










 





    // A* Algorithm
    public static void ShortestPath(int[][] Map, String start, String end) {

        if(!isValidPosition(start) || !isValidPosition(end)) {
            System.out.println("Invalid start or end position.");
            return;
        }

        int[] startCoor = parseCoor(start);
        int[] endCoor = parseCoor(end);

        System.out.println("Calculating shortest path from " + start + " (" + startCoor[0] + "," + startCoor[1] + ") to " + end + " (" + endCoor[0] + "," + endCoor[1] + ")...");

        int rows = Map.length;
        int cols = Map[0].length;

        if(!isInBounds(startCoor, rows, cols) || !isInBounds(endCoor, rows, cols)) {
            System.out.println("Start or end position is out of bounds.");
            return;
        }

        int[][] gScore = new int[rows][cols];
        int[][] fScore = new int[rows][cols];
        int[][] parentRow = new int[rows][cols];
        int[][] parentCol = new int[rows][cols];
        boolean[][] closedSet = new boolean[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                gScore[i][j] = Integer.MAX_VALUE;
                fScore[i][j] = Integer.MAX_VALUE;
                parentRow[i][j] = -1;
                parentCol[i][j] = -1;
            }
        }

        int startRow = startCoor[0];
        int startCol = startCoor[1];
        int endRow = endCoor[0];
        int endCol = endCoor[1];

        java.util.PriorityQueue<Node> openSet = new java.util.PriorityQueue<>();
        gScore[startRow][startCol] = 0;
        fScore[startRow][startCol] = heuristic(startCoor, endCoor);
        openSet.add(new Node(startRow, startCol, fScore[startRow][startCol]));

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while(!openSet.isEmpty()) {
            Node current = openSet.poll();

            if(closedSet[current.row][current.col]) {
                continue;
            }

            if(current.row == endRow && current.col == endCol) {
                java.util.List<int[]> path = reconstructPath(parentRow, parentCol, endRow, endCol);

                System.out.println("Shortest path found (" + (path.size() - 1) + " steps):");

                // 🔥 วาด path ลง map
                printPathMap(Map, path, startRow, startCol, endRow, endCol);

                return;
            }

            /*  ไม่เท่
            if(current.row == endRow && current.col == endCol) {
                java.util.List<int[]> path = reconstructPath(parentRow, parentCol, endRow, endCol);
                System.out.println("Shortest path found (" + (path.size() - 1) + " steps):");
                for(int[] coor : path) {
                    System.out.println("(" + coor[0] + "," + coor[1] + ")");
                }
                return;
            }
            */

            closedSet[current.row][current.col] = true;

            for(int[] direction : directions) {
                int nextRow = current.row + direction[0];
                int nextCol = current.col + direction[1];

                if(!isInBounds(new int[] {nextRow, nextCol}, rows, cols)) {
                    continue;
                }

                if(Map[nextRow][nextCol] == Integer.MAX_VALUE && !(nextRow == endRow && nextCol == endCol)) {
                    continue;
                }

                if(closedSet[nextRow][nextCol]) {
                    continue;
                }

                int tentativeG = gScore[current.row][current.col] + Map[nextRow][nextCol];

                if(tentativeG < gScore[nextRow][nextCol]) {
                    parentRow[nextRow][nextCol] = current.row;
                    parentCol[nextRow][nextCol] = current.col;
                    gScore[nextRow][nextCol] = tentativeG;
                    int[] nextCoor = {nextRow, nextCol};
                    fScore[nextRow][nextCol] = tentativeG + heuristic(nextCoor, endCoor);
                    openSet.add(new Node(nextRow, nextCol, fScore[nextRow][nextCol]));
                }
            }
        }

        System.out.println("No path found from " + start + " to " + end + ".");
    }

    private static boolean isInBounds(int[] coor, int rows, int cols) {
        return coor[0] >= 0 && coor[0] < rows && coor[1] >= 0 && coor[1] < cols;
    }

    private static int heuristic(int[] from, int[] to) {
        return Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
    }

    private static java.util.List<int[]> reconstructPath(int[][] parentRow, int[][] parentCol, int row, int col) {
        java.util.LinkedList<int[]> path = new java.util.LinkedList<>();

        while(row != -1 && col != -1) {
            path.addFirst(new int[] {row, col});
            int prevRow = parentRow[row][col];
            int prevCol = parentCol[row][col];
            row = prevRow;
            col = prevCol;
        }

        return path;
    }

    private static class Node implements Comparable<Node> {
        int row;
        int col;
        int f;

        Node(int row, int col, int f) {
            this.row = row;
            this.col = col;
            this.f = f;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }
    

    private static void printPathMap(int[][] Map, java.util.List<int[]> path,int startRow, int startCol,int endRow, int endCol) {

        int rows = Map.length;
        int cols = Map[0].length;

        char[][] display = new char[rows][cols];

        // default
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(Map[i][j] == Integer.MAX_VALUE) display[i][j] = 'X';
                else display[i][j] = '.';
            }
        }

        // ✅ วาด path ก่อน
        for(int[] p : path) {
            display[p[0]][p[1]] = '*';
        }

        // ✅ ใส่ S / E ทีหลัง (จะได้ไม่โดน * ทับ)
        display[startRow][startCol] = 'S';
        display[endRow][endCol] = 'E';

        // print
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                System.out.print(display[i][j] + " ");
            }
            System.out.println();
        }
    }

//-------------------Random----------------------

    public static void randomizeMap(int[][] Map, int min, int max) {
        Random rand = new Random();

        for(int i = 0; i < Map.length; i++) {
            for(int j = 0; j < Map[0].length; j++) {

                // ❗สุ่มเฉพาะช่องที่ไม่ใช่ INF
                if(Map[i][j] != Integer.MAX_VALUE) {
                    Map[i][j] = rand.nextInt(max - min + 1) + min;
                }

            }
        }
    }
}
