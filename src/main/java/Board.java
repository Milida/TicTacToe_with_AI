package main.java;

import java.awt.Point;

public class Board {
    private static final Field[][] fields = new Field[3][3];

    /**
     * Resets the board as no moves were made
     */
    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = new Field();
            }
        }
    }

    /**
     * Check if given fields state is equals to expected
     * @param field Point with coordinates of field to check
     * @param state Expected state of field
     * @return true if given field is in the expected state and false if not
     */
    public static boolean fieldStateEquals(Point field, FIELD_STATE state) {
        return fields[field.x][field.y].stateEquals(state);
    }

    /**
     * Check if given field is 'free' (there's no player's sign)
     * @param field Point with coordinates of field to check
     * @return true if given field is empty ond false if not
     */
    public static boolean isFree(Point field) {
        return fields[field.x][field.y].stateEquals(FIELD_STATE.EMPTY);
    }

    /**
     * Sets given field's state as given one
     * @param field Field which state we want to change
     * @param state New state of given field
     */
    public static void setFieldState(Point field, FIELD_STATE state) {
        fields[field.x][field.y].setState(state);
    }

    public static Field[][] getFields() {
        return fields;
    }
}
