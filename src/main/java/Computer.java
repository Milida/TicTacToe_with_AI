package main.java;

import java.util.Random;
import java.awt.Point;

import static java.lang.Thread.sleep;

public abstract class Computer implements Player{
    protected Random rand;
    protected Point coordinates; //every computer has coordinates of the last move

    public Computer(Random rand) {
        this.rand = rand;
    }

    /**
     * Computer's move
     * @param sign Whose move it is
     * @param coords Where to move
     * @return null if there was no move (occupied cell) or Point where the move was made
     */
    @Override
    public Point move(FIELD_STATE sign, Point coords) { //TODO wywalenie coords!!!!
        coordinates = generateCoordinates();
        Board.setFieldState(coordinates, sign);
        waitABit();
        return coordinates;
    }

    /**
     * Randomly generates coordinates of move
     */
    private Point generateCoordinates() {
        while (true) {
            coordinates = new Point(rand.nextInt(3), rand.nextInt(3));
            if (Board.isFree(coordinates)) {
                return coordinates;
            }
        }
    }

    /**
     * Waiting to make game more realistic
     */
    public void waitABit() {
        try {
            sleep(20);
        } catch (Exception ignored) { }
    }
}
