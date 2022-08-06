package battleship;

public class Game {
    Game(Board[] boards){
        this.start(boards);
    }

    private  void start(Board[] boards) {
        //System.out.println("\nThe game starts!\n");

        while (boards[0].board_hits > 0 && boards[1].board_hits > 0) {
                boards[1].aim(boards[0]);
                boards[0].aim(boards[1]);

            //boards[1].aim();
        }

    }
}

