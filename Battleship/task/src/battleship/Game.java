package battleship;

public class Game {
    Game(Board[] boards){
        this.start(boards);
    }

    private  void start(Board[] boards) {
        System.out.println("\nThe game starts!\n");
        boards[0].printBoard(true);
        System.out.println("\nTake a shot!\n");
        while (boards[0].board_hits > 0) {

                boards[0].aim();
        }

    }
}

