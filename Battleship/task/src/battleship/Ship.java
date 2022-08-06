package battleship;

public enum Ship {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    final int size;
    final String name;

    Ship(int size, String name) {
        this.size = size;
        this.name = name;
    }
    String requestCoords() {
        return String.format("Enter the coordinates of the %s (%d cells):", name, size);
    }
}