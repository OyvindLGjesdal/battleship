package battleship;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Board {
    int board_offset = 65;
    final String FOG = "~";
    final String PLACED = "O";
    final  String HIT = "X";
    final String MISS = "M";
    final Pattern PLACED_MATCHER = Pattern.compile("O");
    final Pattern REGEX_BOAT = Pattern.compile("[XO]");
    final Pattern SEA_MATCHER = Pattern.compile("[~M]");
    String playerName;


    int board_hits = 17;

        Scanner scanner = new Scanner(System.in);
        public String[][] board = {

                // Horizontal numbered 1-10,Vertical A-J. Input starts with Vertical (F3)
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
                {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~",}
        };

        public Board(String name) {
            this.playerName = name;
            System.out.printf("%s, place your ships on the game field\n",name);
            printBoard();
            placeCoords(Ship.AIRCRAFT_CARRIER);
            placeCoords(Ship.BATTLESHIP);
            placeCoords(Ship.SUBMARINE);
            placeCoords(Ship.CRUISER);
            placeCoords(Ship.DESTROYER);

        }

        void placeCoords(Ship ship) {
            System.out.println(ship.requestCoords());
            System.out.print("> ");

            String coords = scanner.nextLine();
            String regex = "^([A-J])([1-9]|10)\\s([A-J])([1-9]|10)$";
            if (!coords.matches(regex)) {
                System.out.printf("Error! invalid input: %s try again!:\n", coords);
                placeCoords(ship);
                return;
            }
            int coord_q = coords.replaceAll(regex, "$1").charAt(0); // A
            int coord_x = Integer.parseInt(coords.replaceAll(regex, "$2")); // 1
            int coord_y = coords.replaceAll(regex, "$3").charAt(0); // B
            int coord_z = Integer.parseInt(coords.replaceAll(regex, "$4"));

            //System.out.print(coords.replaceAll(regex, "$1"));

            int max_letter = Integer.max(coord_q, coord_y);
            int min_letter = Integer.min(coord_q, coord_y);
            int max_number = Integer.max(coord_x, coord_z);
            int min_number = Integer.min(coord_x, coord_z);

            int place_length = (max_letter - min_letter) + (max_number - min_number) + 1;

            if (max_letter != min_letter && max_number != min_number) {
                System.out.print("Error! Wrong ship location! Try again:\n");
                placeCoords(ship);
                return;
            }
            if (place_length != ship.size) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.name);
                placeCoords(ship);
                return;
            }

            for (int x = min_letter; x <= max_letter; x++) {
                for (int y = min_number; y <= max_number; y++) {
                    int letter_index = x - board_offset;
                    int number_index = y - 1;
                    String top_neighbour = letter_index > 0 ? board[letter_index - 1][number_index] : null;
                    String bottom_neighbour = letter_index < 9 ? board[letter_index + 1][number_index] : null;
                    String left_neighbour = number_index > 0 ? board[letter_index][number_index - 1] : null;
                    String right_neighbour = number_index < 9 ? board[letter_index][number_index + 1] : null;
                    if (Arrays.asList(top_neighbour, left_neighbour, bottom_neighbour, right_neighbour).contains("O")) {
                        System.out.print("Error! You placed it too close to another one. Try again:\n");
                        placeCoords(ship);
                        return;
                    }
                }
            }

            for (int j = 1; j <= 10; j++) {
                for (int i = 'A'; i <= 'J'; i++) {
                    if (j >= min_number && j <= max_number && i >= min_letter && i <= max_letter) {
                        this.board[i - board_offset][j - 1] = PLACED;
                    }
                }
            }
            printBoard();
        }

    void printBoard() {
            printBoard(false);
    }

    void printBoard(boolean fog) {
          //  System.out.println();
            System.out.println("  1 2 3 4 5 6 7 8 9 10");
            for (int i = 0; i <= board.length - 1; i++) {
                System.out.printf("%c", (char) board_offset + i);
                for (int j = 0; j <= board[i].length - 1; j++) {
                    var point = board[i][j];
                    if (fog) { point = (point.equals(HIT) || point.equals(MISS)) ? point : FOG; }
                    System.out.printf(" %s",point );                }
                System.out.println();
            }
        }


    void aim(Board opponentBoard) {
            String regex = "^([A-J])([1-9]|10)$";
            String msg;
            this.printBoard(true);
            System.out.println("---------------------");
            opponentBoard.printBoard();
            System.out.printf("\n%s, it's your turn: \n\n",opponentBoard.playerName);
            System.out.print("> ");
            String target  = scanner.nextLine();
            System.out.println();

            if (!target.matches(regex))
            { System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            aim(opponentBoard);
            return;
            }
            int letterIndex = target.substring(0,1).charAt(0)-board_offset;
            int numberIndex = Integer.parseInt(target.substring(1))-1;

            if (board[letterIndex][numberIndex].equals(PLACED)) {
                board[letterIndex][numberIndex] = HIT;

               msg = "You hit a ship!\n";
               board_hits--;
               if (sink(letterIndex,numberIndex)) {msg = "You sank a ship!:\n";};

            }
            else if (board[letterIndex][numberIndex].equals(HIT)) {
                msg = "You missed!\n";
            }
            else { msg = "You missed!\n";

                board[letterIndex][numberIndex] = MISS;
            }
            printBoard(true);
            if (board_hits == 0)
                msg = "You sank the last ship. You won. Congratulations";


            String enter = "full";
            while (!enter.isEmpty()) {
                System.out.printf("%s\nPress Enter and pass the move to another player\n",msg);
                enter = scanner.nextLine();

            }



        }
        private boolean sink(int letter, int number) {
            System.out.printf("%s %s ",letter, number);
            boolean is_letter = (letter > 0 && REGEX_BOAT.matcher(board[letter-1][number]).matches()) ||
                    (letter < 9  && number < -1 && REGEX_BOAT.matcher(board[letter + 1][number]).matches());
           // System.out.printf("%s %s ",letter, number);
            return Boolean.logicalAnd(sink(letter, number, is_letter,true),sink(letter, number, is_letter,false));
        }

    private boolean sink(int letter, int number, boolean dir_is_letter, boolean increment) {
            if (letter < 0 || letter > 9 || number < 0 || number > 10 )
                return false;
        String direction1 = dir_is_letter && letter > 0 ? board[letter - 1][number] : (number > 0 ? board[letter][number - 1] : "");
        String direction2 = dir_is_letter && letter < 9 ? board[letter + 1][number] : (number < 9 ? board[letter][number + 1] : "");
        boolean next1 = true;
        boolean next2 = true;
        System.out.printf("sink %s %s %s %s %s",letter, number, dir_is_letter, direction1, direction2);

        if ((!direction1.isEmpty() && PLACED_MATCHER.matcher(direction1).matches())
                || (!direction2.isEmpty() && PLACED_MATCHER.matcher(direction2).matches())) {
            return false;
        }
         if (!increment && !direction1.isBlank()  && !SEA_MATCHER.matcher(direction1).matches()) {
            next1 = sink(dir_is_letter ? letter - 1 : letter, dir_is_letter ? number : number - 1,dir_is_letter, false);
        }
        if (increment && !direction2.isEmpty()  && !SEA_MATCHER.matcher(direction2).matches()){
            next2 = sink(dir_is_letter ? letter + 1 : letter, dir_is_letter ? number : number - 1,dir_is_letter, true);
        }

        if (!next1 && !next2) { return false;}
        return true;
    }

    }