package main.java;

import java.awt.Point;

public class User implements Player{
    /**
     * User's move
     * @param sign Whose move is it
     * @param coords Where to move
     * @return null if there was no move (occupied cell) or Point where the move was made
     */
    @Override
    public Point move(FIELD_STATE sign, Point coords) {
        if (!Board.isFree(coords)) { //user chose occupied field
            return null;
        }
        Board.setFieldState(coords, sign);
        return coords;
    }
}
