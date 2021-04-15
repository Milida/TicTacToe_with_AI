package main.java;

import java.util.Random;
import java.awt.Point;

public class EasyComputer extends Computer{
    public EasyComputer(Random rand) {
        super(rand);
    }

    /**
     * Easy level computer's move
     * @param sign Whose move it is
     * @param coords Where to
     * @return null if there was no move (occupied cell) or Point where the move was made
     */
    @Override
    public Point move(FIELD_STATE sign, Point coords) {
        return super.move(sign, coords);
    }
}
