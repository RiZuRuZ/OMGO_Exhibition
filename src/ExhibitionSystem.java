import java.util.Scanner;
import structures.tree.NameAVL;
import structures.tree.NameNode;
import structures.tree.PosAVL;
import structures.tree.PosNode;

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
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); 

            if(choice == 1) {
                showMap(Map);
            }else if(choice == 2) {
                System.out.print("Enter name: ");
                String name = sc.nextLine();

                System.out.print("Enter positions (e.g. A1 A2 A3): ");
                String input = sc.nextLine();
                String[] positions = input.split(" ");

                reserve(name, positions, Map);
            } else if(choice == 3) {
                nameTree.printTree();
            } else if(choice == 4) {
                System.out.print("Enter name to search: ");
                String name = sc.nextLine();
                NameNode result = nameTree.search(name);

                if(result == null) System.out.println("Name not found.");
                else {
                    System.out.println("Positions for " + name + ": " + result.positions);
                }
            } else if(choice == 5) {
                System.out.print("Enter position to search: ");
                String pos = sc.nextLine();

                PosNode result = posTree.searchByPosition(pos);

                if(result == null) {
                    System.out.println("Position not found.");
                } else {
                    System.out.println("Position " + pos + " reserved by: " + result.name);
                }
            } else break;
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

        System.out.println("Reservation successful for " + name + " at positions: " + String.join(", ", positions));
        return Map;
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

    private static boolean isValidPosition(String pos) {
        return pos.matches("^[A-F](1[0-2]|[1-9])$");
    }
}