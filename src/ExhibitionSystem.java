import java.util.Scanner;
import structures.tree.NameAVL;
import structures.tree.NameNode;
import structures.tree.PosAVL;

public class ExhibitionSystem {

    static String[][] map = new String[6][12];

    static NameAVL nameTree = new NameAVL();
    static PosAVL posTree = new PosAVL();

    public static void main(String[] args) {
        //initMap();

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("1. Show Map");
            System.out.println("2. Reserve");
            System.out.println("3. Show AVL");
            System.out.println("4. Search by Name");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); 

            if(choice == 1) {
                //showMap();
            }else if(choice == 2) {
                System.out.print("Enter name: ");
                String name = sc.nextLine();

                System.out.print("Enter positions (e.g. A1 A2 A3): ");
                String input = sc.nextLine();
                String[] positions = input.split(" ");

                reserve(name, positions);
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
            } else break;
        }
    }

    public static void reserve(String name, String[] positions) {

        // ✅ เช็ค format ก่อน
        for(String pos : positions) {
            if(!isValidPosition(pos)) {
                System.out.println("Invalid position: " + pos);
                return; // ❗ หยุดทันที
            }
        }

        // ✅ เช็คว่าซ้ำไหม
        for(String pos : positions) {
            if(posTree.exists(pos)) {
                System.out.println("Position " + pos + " is already reserved.");
                return;
            }
        }

        // ✅ insert จริง
        for(String pos : positions) {
            nameTree.insert(name, pos);
            posTree.insert(pos, name);
        }

        System.out.println("Reservation successful for " + name + " at positions: " + String.join(", ", positions));
    }

    private static boolean isValidPosition(String pos) {
        return pos.matches("^[A-F](1[0-2]|[1-9])$");
    }
}