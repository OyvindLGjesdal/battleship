package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Board board1 = new Board("Player 1");
        System.out.println("Press Enter and pass the move to another player\n");
        Scanner scanner = new Scanner(System.in);
        String result = scanner.nextLine();
        if (result.isEmpty()) {

            Board board2 = new Board("Player 2");
            Board[] boards = {board1, board2};
            System.out.println("Press Enter and pass the move to another player\n");
            if (scanner.nextLine().isEmpty()) {
                new Game(boards);
            }
        }
        System.out.println("result");
    }
}
