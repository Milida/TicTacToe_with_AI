package main.java;

import java.awt.Point;
import java.util.Random;

public class HardComputer extends Computer {
    private int movesMade = -1;
    private Point coordinates;
    private final FIELD_STATE[] signs = {
        FIELD_STATE.X,
        FIELD_STATE.O
    };

    private final FIELD_STATE[] moves = new FIELD_STATE[2];
    private FIELD_STATE sign;

    public HardComputer(Random rand) {
        super(rand);
    }

    public void setMovesMade() {
        this.movesMade = -1;
    }

    @Override
    public Point move(FIELD_STATE sign, Point inCoordinates) {
        this.sign = sign;
        coordinates = null;
        setAmountOfMadeMoves();
        if (movesMade == 1) {
            movesMade += 2;
            return super.move(sign, inCoordinates);
        }
        Field[][] fieldsCopy = copyFields();
        whereToMove(fieldsCopy, sign, movesMade);
        if (coordinates != null) {
            Board.setFieldState(coordinates, sign);
            Point tmp = coordinates;
            movesMade += 2;
            return tmp;
        }
        movesMade += 2;
        return super.move(sign, inCoordinates);
    }

    /**
     * Sets amount of moves made when it's first move of Hard Computer
     */
    public void setAmountOfMadeMoves(){
        if (movesMade == -1) {
            movesMade = checkHowManyMovesWereMade();
        }
    }

    public int checkHowManyMovesWereMade(){
        if (sign.equals(FIELD_STATE.X)) {
            return 0;
        } else {
            return 1;
        }
    }

    public Field[][] copyFields() {
        Field[][] copyFields = new Field[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(Board.getFields()[i], 0, copyFields[i], 0, 3);
        }
        return copyFields;
    }

    private int whereToMove(Field[][] copyFields, FIELD_STATE sign, int made) {
        if (movesMade > 8 || made > 8) {
            return 0;
        }
        checkIfCanWinOrBlock(sign);
        if (coordinates != null) {
            return 10;
        }
        if(copyFields[1][1].isFree()) {
            coordinates = new Point(1, 1);
            return 10;
        }
        int result = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(copyFields[i][j].isFree()) {
                    copyFields[i][j].setState(sign);
                    result = whereToMove(copyFields, signs[(made + 1) % 2], made + 1);
                    if (result > 0) {
                        coordinates = new Point(i, j);
                        return result;
                    } else if (result < 0) {
                        coordinates = new Point(i, j);
                        return result;
                    }
                    copyFields[i][j].setState(FIELD_STATE.EMPTY);
                }
            }
        }
        return result;
    }

    private void checkIfCanWinOrBlock(FIELD_STATE sign) {
        crossCheck(sign); //koniec sprawdzania czy da się dostawić po skosach, teraz trzeba sprawdzić poziomo i pionowo, ale to można już w pętli for, poza tym to sprawdzanie po krzyżu trzeba wyrzucić do innej funkcji żeby to miało sens
        if (coordinates != null) {
            return;
        }
        verticalAndHorizontalCheck(sign);
    }

    private void crossCheck(FIELD_STATE sign) {
        initializeMoveOrder(sign);
        crossCheckForCurrentMove(moves[0]);
        crossCheckForCurrentMove(moves[1]);
    }

    private void crossCheckForCurrentMove(FIELD_STATE move) {
        if (Board.fieldStateEquals(new Point(0, 0), move)) {
            if (Board.fieldStateEquals(new Point(1, 1), move) && Board.fieldStateEquals(new Point(2, 2), FIELD_STATE.EMPTY)) {
                coordinates = new Point(2, 2);
            } else if (Board.fieldStateEquals(new Point(2, 2), move) && Board.fieldStateEquals(new Point(1, 1), FIELD_STATE.EMPTY)) {
                coordinates = new Point(1, 1);
            }
        } else if (Board.fieldStateEquals(new Point(2 ,0), move)) {
            if (Board.fieldStateEquals(new Point(1, 1), move) && Board.fieldStateEquals(new Point(0, 2), FIELD_STATE.EMPTY)) {
                coordinates = new Point(2, 0);
            } else if (Board.fieldStateEquals(new Point(0, 2), move) && Board.fieldStateEquals(new Point(1, 1), FIELD_STATE.EMPTY)) {
                coordinates = new Point(1, 1);
            }
        } else if (Board.fieldStateEquals(new Point(1, 1), move)) {
            if (Board.fieldStateEquals(new Point(2, 2), move) && Board.fieldStateEquals(new Point(0,0), FIELD_STATE.EMPTY)) {
                coordinates = new Point(0, 0);
            } else if (Board.fieldStateEquals(new Point(0, 2), move) && Board.fieldStateEquals(new Point(2 ,0), FIELD_STATE.EMPTY)) {
                coordinates = new Point(0, 2);
            }
        }
    }

    private void verticalAndHorizontalCheck(FIELD_STATE sign) {
        initializeMoveOrder(sign);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board.fieldStateEquals(new Point(j, i), FIELD_STATE.EMPTY)) {
                    checkLine(new Point(i, j), moves[0]);
                    if (coordinates == null) {
                        checkLine(new Point(j, i), moves[1]);
                        if (coordinates != null) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private void checkLine(Point coords, FIELD_STATE move) {
        if (Board.fieldStateEquals(new Point(coords.x, Math.abs((coords.y + 1) % 3)), move) && Board.fieldStateEquals(new Point(coords.x, Math.abs((coords.y + 2) % 3)), move)) {
            coordinates = coords;
        } else if (Board.fieldStateEquals(new Point(Math.abs((coords.x + 1) % 3), coords.y), move) && Board.fieldStateEquals(new Point(Math.abs((coords.x + 2) % 3), coords.y), move)) {
            coordinates = coords;
        }
    }

    public void initializeMoveOrder(FIELD_STATE state) {
        if(state.equals(FIELD_STATE.X)) {
            moves[0] = FIELD_STATE.X;
            moves[1] = FIELD_STATE.O;
        } else {
            moves[0] = FIELD_STATE.O;
            moves[1] = FIELD_STATE.X;
        }
    }
}
