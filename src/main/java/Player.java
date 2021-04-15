package main.java;

import java.awt.Point;

public interface Player {
    /**
     * Player's move
     * @param sign Whose move it is
     * @param coords Where to move
     * @return null if there was no move (occupied cell) or Point where the move was made
     */
    Point move(FIELD_STATE sign, Point coords);
}
