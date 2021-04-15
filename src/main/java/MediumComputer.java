package main.java;

import java.util.Random;
import java.awt.Point;

public class MediumComputer extends Computer {
    private final FIELD_STATE[] moves = new FIELD_STATE[2];
    private Point result = null;
    private FIELD_STATE sign;

    public MediumComputer(Random rand) {
        super(rand);
    }

    /**
     * Medium level computer's move
     * @param sign current player's sign
     * @param inCoordinates can ignore now //TODO do wywalenia!!!!
     * @return Point with coordinates of move
     */
    public Point move(FIELD_STATE sign, Point inCoordinates) {
        this.sign = sign;
        Point coordinates = checkWhereToMoveToBlockOrWin();
        if (coordinates == null) { //computer can't win in this move, so makes random one
            return super.move(sign, inCoordinates);
        }
        Board.setFieldState(coordinates, sign);
        return coordinates;
    }

    /**
     * Checks where to move to win or block opponent in this move
     * @return Point with coordinates of the possible move
     */
    private Point checkWhereToMoveToBlockOrWin() {
        Point coordinates = crossCheckWhereToMoveToBlockOrWin();
        if (coordinates != null) {
            return coordinates;
        }
        return verticalAndHorizontalCheck();
    }

    /**
     * Checks where to move to win or block in cross (both diagonals)
     * @return Point with coordinates od the possible move
     */
    private Point crossCheckWhereToMoveToBlockOrWin() {
        initializeMoveOrder();
        result = checkForWinOrBlock(moves[0]); //current player sign
        if (result == null) {
            checkForWinOrBlock(moves[1]); //opponent's sign
        }
        return result;
    }

    /**
     * Make current player's move appear first and opponent's second
     */
    private void initializeMoveOrder() {
        if (sign.equals(FIELD_STATE.X)) {
            moves[0] = FIELD_STATE.X;
            moves[1] = FIELD_STATE.O;
        } else {
            moves[0] = FIELD_STATE.O;
            moves[1] = FIELD_STATE.X;
        }
    }

    private Point checkForWinOrBlock(FIELD_STATE move) { //TODO do skrócenia
        if (Board.fieldStateEquals(new Point(0, 0), move)) {
            if (Board.fieldStateEquals(new Point(1,1), move) && Board.fieldStateEquals(new Point(2, 2), FIELD_STATE.EMPTY)) {
                return new Point(2, 2);
            } else if (Board.fieldStateEquals(new Point(2, 2), move) && Board.fieldStateEquals(new Point(1, 1), FIELD_STATE.EMPTY)) {
                return new Point(1, 1);
            }
        } else if (Board.fieldStateEquals(new Point(2, 0), move)) {
            if (Board.fieldStateEquals(new Point(1, 1), move) && Board.fieldStateEquals(new Point(0, 2), FIELD_STATE.EMPTY)) {
                return new Point(0, 2);
            } else if (Board.fieldStateEquals(new Point(0, 2), move) && Board.fieldStateEquals(new Point(1, 1), FIELD_STATE.EMPTY)) {
                return new Point(1, 1);
            }
        } else if (Board.fieldStateEquals(new Point(1,1), move)) {
            if (Board.fieldStateEquals(new Point(2, 2), move) && Board.fieldStateEquals(new Point(0, 0), FIELD_STATE.EMPTY)) {
                return new Point(0, 0);
            } else if (Board.fieldStateEquals(new Point(0, 2), move) && Board.fieldStateEquals(new Point(2, 0), FIELD_STATE.EMPTY)) {
                return new Point(0, 2);
            }
        }
        return null;
    }

    public Point verticalAndHorizontalCheck() {
        initializeMoveOrder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.fieldStateEquals(new Point(i, j), FIELD_STATE.EMPTY)) { //TODO do skrócenia
                    if (Board.fieldStateEquals(new Point(j, Math.abs((i + 1) % 3)), moves[0]) && Board.fieldStateEquals(new Point(j, Math.abs((i + 2) % 3)), moves[0])) {
                        return new Point(i, j);
                    } else if (Board.fieldStateEquals(new Point(Math.abs((j + 1) % 3), i), moves[0]) && Board.fieldStateEquals(new Point(Math.abs((j + 2) % 3), i), moves[0])) {
                        return new Point(i, j);
                    } else if (Board.fieldStateEquals(new Point(j, Math.abs((i + 1) % 3)), moves[1]) && Board.fieldStateEquals(new Point(j, Math.abs((i + 2) % 3)), moves[1])) {
                        return new Point(i, j);
                    } else if (Board.fieldStateEquals(new Point(Math.abs((j + 1) % 3), i), moves[1]) && Board.fieldStateEquals(new Point(Math.abs((j + 2) % 3), i), moves[1])) {
                        return new Point(i, j);
                    }
                }
            }
        }
        return null;
    }
}
